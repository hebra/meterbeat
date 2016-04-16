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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;
import io.github.hebra.elasticsearch.beat.meterbeat.output.BeatOutput;
import io.github.hebra.elasticsearch.beat.meterbeat.output.JestClientFactory;
import io.searchbox.client.JestClient;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;

public class PushElasticSearchActor extends UntypedActor
{
	private static final Logger LOGGER = LoggerFactory.getLogger( PushElasticSearchActor.class );

	@Override
	public void onReceive( final Object _message ) throws Exception
	{
		if ( _message != null && _message instanceof BeatOutput )
		{


			LOGGER.error( ((BeatOutput) _message).asJson() );



			final JestClient client = JestClientFactory.get();


			LOGGER.info( String.valueOf(client.execute(new IndicesExists.Builder("meterbeat").build()).isSucceeded() ));

			if (!client.execute(new IndicesExists.Builder("meterbeat").build()).isSucceeded())
			{
				client.execute(new CreateIndex.Builder("meterbeat").build());
			}
		}
		else
		{
			unhandled( _message );
		}

	}
}
