/**
 * (C) 2016 Hendrik Brandt <https://github.com/hebra/> This file is part of MeterBeat. MeterBeat is free software: you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. MeterBeat is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with MeterBeat. If not, see <http://www.gnu.org/licenses/>.
 ***/

package io.github.hebra.elasticsearch.beat.meterbeat.output;

import java.util.ArrayList;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This is the root class for serializing MeterBeat data to a JSON string which can be send to ElasticSearch, Logstash
 * or a file.
 */
@Accessors( fluent = true )
public class BeatOutput
{
	private static final Logger LOGGER = LoggerFactory.getLogger( BeatOutput.class );

	private final ObjectMapper mapper = ObjectMapperFactory.get();

	/**
	 * The timestamp of when the measurements were taken. The precision is in milliseconds. The timezone is UTC.
	 */
	@JsonProperty( "@timestamp" )
	@Setter
	@Getter
	private long timestamp = System.currentTimeMillis();

	/**
	 * Details about the Beat the message is of.
	 */
	@JsonProperty( "beat" )
	@Setter
	@Getter
	private Beat beat;

	/**
	 * The actual measurement result, value and unit.
	 */
	@JsonProperty( "power" )
	@Setter
	@Getter
	private Power power;

	/**
	 * Set to "power" by default as we collection data from power meters.
	 */
	@JsonProperty( "type" )
	@Setter
	@Getter
	private String type = "power";

	public BeatOutput()
	{
	}

	public Iterable < String > asIterable()
	{
		return Arrays.asList( beat.beatName(), type, String.valueOf( power.value() ), power.unit(), String.valueOf( timestamp ) );
	}

	public String asJson()
	{
		try
		{
			return mapper.writeValueAsString( this );
		}
		catch ( final JsonProcessingException jpEx )
		{
			LOGGER.error( jpEx.getMessage() );
		}

		return "";
	}

}
