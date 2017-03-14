/**
 * (C) 2016 Hendrik Brandt <https://github.com/hebra/> This file is part of MeterBeat. MeterBeat is free software: you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. MeterBeat is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with MeterBeat. If not, see <http://www.gnu.org/licenses/>.
 ***/

package io.github.hebra.elasticsearch.beat.meterbeat.config.output.elasticsearch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

public class Elasticsearch
{

	SimpleDateFormat dateFormat = new SimpleDateFormat( "YYYY.MM.dd" );

	@JsonProperty( "hosts" )
	@Getter
	private List<String> hosts;

	@JsonProperty( "protocol" )
	@Getter
	private String protocol;

	@JsonProperty( "username" )
	@Getter
	private String username;

	@JsonProperty( "password" )
	@Getter
	private String password;

	@JsonProperty( "index" )
	private String index;

	@JsonProperty( "path" )
	@Getter
	private String path;

	@JsonProperty( "max_retries" )
	@Getter
	private final int maxRetries = 3;

	@JsonProperty( "timeout" )
	@Getter
	private final int timeout = 10;

	/**
	 * Get the Elasticsearch index name with a daily pattern
	 *
	 * @return the index name
	 */
	public String getIndex()
	{
		return ( index == null ? "meterbeat" : index ).concat( "-" ).concat( dateFormat.format( new Date() ) );
	}

}
