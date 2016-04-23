/**
 * (C) 2016 Hendrik Brandt <https://github.com/hebra/> This file is part of MeterBeat. MeterBeat is free software: you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. MeterBeat is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with MeterBeat. If not, see <http://www.gnu.org/licenses/>.
 ***/

package io.github.hebra.elasticsearch.beat.meterbeat.config;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.hebra.elasticsearch.beat.meterbeat.config.input.Input;
import io.github.hebra.elasticsearch.beat.meterbeat.config.logging.Logging;
import io.github.hebra.elasticsearch.beat.meterbeat.config.output.Output;
import lombok.Getter;
import lombok.Synchronized;

public class Config
{
	private static volatile Config instance;

	@JsonProperty( "input" )
	@Getter
	private Input input;

	@JsonProperty( "output" )
	@Getter
	private Output output;

	@JsonProperty( "logging" )
	@Getter
	private Logging logging;

	private Config()
	{
		instance = this;
	}

	@Synchronized
	public static Config get()
	{
		return Optional.ofNullable( instance ).orElse( new Config() );
	}

}
