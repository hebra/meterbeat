/**
 * (C) 2016-2017 Hendrik Brandt <https://github.com/hebra/> This file is part of MeterBeat. MeterBeat is free software: you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. MeterBeat is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with MeterBeat. If not, see <http://www.gnu.org/licenses/>.
 ***/
package io.github.hebra.elasticsearch.beat.meterbeat.configuration;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties( "input" )
public class InputDevicesConfiguration
{
	@Getter
	@Setter
	private long period;
	

	@Getter @Setter
	private List < Device > devices;
	
	
	@PostConstruct
	private void init()
	{
		LoggerFactory.getLogger( this.getClass() ).error( "{}", period );
		LoggerFactory.getLogger( this.getClass() ).error( "{}", devices );
	}
	
	public static class Device {
		
		@Getter @Setter
		private String name;

		@Getter @Setter
		private DeviceType type;
		
		@Getter @Setter
		private String baseurl;
	}
	
}
