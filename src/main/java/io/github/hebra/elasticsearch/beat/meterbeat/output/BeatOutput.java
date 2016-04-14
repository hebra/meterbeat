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

package io.github.hebra.elasticsearch.beat.meterbeat.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This is the root class for serializing MeterBeat data to a JSON string which can be send to ElasticSearch, Logstash
 * or a file.
 */
@Accessors(fluent=true)
public class BeatOutput
{
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

}
