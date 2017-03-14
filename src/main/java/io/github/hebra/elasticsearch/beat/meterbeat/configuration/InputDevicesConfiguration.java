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
	
/*	
	input:
		  # In seconds, defines how often to poll devices
		  period: 5

		  devices:
		    - name: Sample Plug 1
		      type: DSPW215
		      baseurl: http://10.0.0.2
		    - name: Sample Plug 2
		      type: DSPW215
		      baseurl: http://10.0.0.3

*/
	
	public static class Device {
		
		@Getter @Setter
		private String name;

		@Getter @Setter
		private DeviceType type;
		
		@Getter @Setter
		private String baseurl;
	}
	
}
