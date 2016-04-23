/**
 * (C) 2016 Hendrik Brandt <https://github.com/hebra/>
 *
 * This file is part of MeterBeat.
 *
 * MeterBeat is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * MeterBeat is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with MeterBeat. If not, see
 * <http://www.gnu.org/licenses/>.
 ***/

package io.github.hebra.elasticsearch.beat.meterbeat.akka;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;
import io.github.hebra.elasticsearch.beat.meterbeat.config.Config;
import io.github.hebra.elasticsearch.beat.meterbeat.config.output.Output;
import io.github.hebra.elasticsearch.beat.meterbeat.config.output.elasticsearch.Elasticsearch;
import io.github.hebra.elasticsearch.beat.meterbeat.output.BeatOutput;
import io.github.hebra.elasticsearch.beat.meterbeat.output.JestClientFactory;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.PutMapping;

public class PushElasticSearchActor extends UntypedActor
{
	private static final Logger LOGGER = LoggerFactory.getLogger( PushElasticSearchActor.class );

	private final Elasticsearch output = Config.get().getOutput().getElasticsearch();

	@Override
	public void onReceive( final Object _message ) throws Exception
	{
		if ( _message != null && _message instanceof BeatOutput )
		{
			final String index = "meterbeat";

			final JestClient client = JestClientFactory.get();

			try
			{
				if ( !client.execute( new IndicesExists.Builder( index ).build() ).isSucceeded() )
				{
					client.execute( new CreateIndex.Builder( index ).build() );

					// Add a default mapping to store timestamps
					client.execute( new PutMapping.Builder( index, ( ( BeatOutput ) _message ).type(), "{\"_timestamp\":{\"enabled\" : true}}" ).build() );
				}

				client.execute( new Index.Builder( ( ( BeatOutput ) _message ).asJson() ).index( index ).type( ( ( BeatOutput ) _message ).type() ).build() );

			}
			catch ( final IOException ioEx )
			{
				LOGGER.error( "Exception while sending message to ElasticSearch: {}", ioEx.getMessage() );
			}
		}
		else
		{
			unhandled( _message );
		}

	}
}
