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

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import io.github.hebra.elasticsearch.beat.meterbeat.output.BeatOutput;
import io.github.hebra.elasticsearch.beat.meterbeat.service.ServiceFactory;

@Component
@Scope("prototype")
public class PushOutputActor extends UntypedActor
{
	private static final Logger log = LoggerFactory.getLogger( PushOutputActor.class );

	@Autowired
	private ActorSystem actorSystem;

	@Autowired
	private ServiceFactory serviceFactory;

	@Override
	public void onReceive( final Object message ) throws Exception
	{
		if ( message != null && message instanceof BeatOutput )
		{
			LoggerFactory.getLogger( this.getClass() ).error( "{}", message );

			serviceFactory.getServices().forEach( service -> {
				service.send((BeatOutput) message);
				
//					Optional.ofNullable( device.fetchData() ).ifPresent( watt -> {
//						log.error( watt );
//					});
				
				
			} );

//			final String index = "meterbeat";
//
//			final JestClient client = JestClientFactory.get();
//
//			try
//			{
//				if ( !client.execute( new IndicesExists.Builder( index ).build() ).isSucceeded() )
//				{
//					client.execute( new CreateIndex.Builder( index ).build() );
//
//					// Add a default mapping to store timestamps
//					client.execute( new PutMapping.Builder( index, ( ( BeatOutput ) _message ).type(), "{\"_timestamp\":{\"enabled\" : true}}" ).build() );
//				}
//
//				client.execute( new Index.Builder( ( ( BeatOutput ) _message ).asJson() ).index( index ).type( ( ( BeatOutput ) _message ).type() ).build() );
//
//			}
//			catch ( final IOException ioEx )
//			{
//				LOGGER.error( "Exception while sending message to ElasticSearch: {}", ioEx.getMessage() );
//			}
		}
		else
		{
			unhandled( message );
		}

	}
}
