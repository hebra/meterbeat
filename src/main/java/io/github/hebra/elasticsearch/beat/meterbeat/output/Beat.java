/**
 * (C) 2016-2017 Hendrik Brandt <https://github.com/hebra/> This file is part of MeterBeat. MeterBeat is free software: you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. MeterBeat is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with MeterBeat. If not, see <http://www.gnu.org/licenses/>.
 ***/
package io.github.hebra.elasticsearch.beat.meterbeat.output;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.hebra.elasticsearch.beat.meterbeat.configuration.InputDevicesConfiguration.Device;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This class acts as a data container for beat-specific information.
 */
@Accessors( fluent = true )
public class Beat
{
	private static final Logger LOGGER = LoggerFactory.getLogger( Beat.class );

	/**
	 * Name of the Beat sending the events. If the shipper name is set in the configuration file, then that value is
	 * used. If it is not set, the hostname is used.
	 */
	@JsonProperty( "name" )
	@Getter
	private String beatName;

	/**
	 * The hostname as returned by the operating system on which the Beat is running.
	 */
	@JsonProperty( "hostname" )
	@Setter
	@Getter
	private String beatHostname = "localhost";

	public Beat()
	{
		try
		{
			beatHostname( InetAddress.getLocalHost().getHostName() );
		}
		catch ( final UnknownHostException uhEx )
		{
			LOGGER.error( "Unable to get hostname for JSON output: {}", uhEx.getMessage() );
		}
	}

	public static Beat fromConfig( final Device config )
	{
		return new Beat().beatName( config.getName() );
	}

	public Beat beatName( final String _beatName )
	{
		Optional.ofNullable( _beatName ).ifPresent( name -> {
			beatName = _beatName.replaceAll( " ", "" );
		} );

		return this;
	}
}
