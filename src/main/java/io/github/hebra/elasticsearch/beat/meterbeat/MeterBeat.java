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

package io.github.hebra.elasticsearch.beat.meterbeat;

import java.io.File;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import io.github.hebra.elasticsearch.beat.meterbeat.akka.MasterActor;
import io.github.hebra.elasticsearch.beat.meterbeat.config.Config;

public class MeterBeat
{
	private static final Logger LOGGER = LoggerFactory.getLogger( MeterBeat.class );

	public static void main( final String[] args ) throws Exception
	{
		LOGGER.info( "Starting MeterBeat" );

		final File configFile = Paths.get( "conf", "meterbeat.yml" ).toFile();

		final ObjectMapper mapper = new ObjectMapper( new YAMLFactory() );
		final Config config = mapper.readValue( configFile, Config.class );

		final ActorSystem _system = ActorSystem.create( "MeterBeat" );

		final ActorRef master = _system.actorOf( new Props( MasterActor.class ), "master" );

		config.getInput().getDevices().parallelStream().forEach( deviceConfig -> {

			try
			{
				master.tell( deviceConfig.getType().getHandlerClass().newInstance().config( deviceConfig ) );
			}
			catch ( final IllegalAccessException
					| InstantiationException iEx )
			{
				LOGGER.error( iEx.getMessage() );
				iEx.printStackTrace();
			}
		} );
	}
}
