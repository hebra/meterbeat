/**
 * (C) 2016 Hendrik Brandt <https://github.com/hebra/> This file is part of MeterBeat. MeterBeat is free software: you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. MeterBeat is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with MeterBeat. If not, see <http://www.gnu.org/licenses/>.
 ***/

package io.github.hebra.elasticsearch.beat.meterbeat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import io.github.hebra.elasticsearch.beat.meterbeat.akka.SpringExtension;
import io.github.hebra.elasticsearch.beat.meterbeat.configuration.InputDevicesConfiguration;

@SpringBootApplication
public class MeterBeat implements CommandLineRunner
{
	@Autowired
	private ActorSystem actorSystem;

	@Autowired
	private SpringExtension springExtension;

	@Autowired
	private InputDevicesConfiguration inputDevicesConfiguration;

	private static final Logger log = LoggerFactory.getLogger( MeterBeat.class );

	public static void main( String[] args )
	{
		SpringApplication.run( MeterBeat.class, args );
	}

	@Override
	public void run( String... args ) throws Exception
	{
		log.info( "Starting MeterBeat" );

		final ActorRef master = actorSystem.actorOf( springExtension.props( "masterActor" ), "master" );

		inputDevicesConfiguration.getDevices().parallelStream().forEach( deviceConfig -> {

			try
			{
				master.tell( deviceConfig.getType().getHandlerClass().newInstance().config( deviceConfig ), ActorRef.noSender() );
			}
			catch ( final IllegalAccessException | InstantiationException iEx )
			{
				log.error( iEx.getMessage() );
				iEx.printStackTrace();
			}
		} );
	}

}
