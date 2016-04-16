package io.github.hebra.elasticsearch.beat.meterbeat.output;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.hebra.elasticsearch.beat.meterbeat.config.DeviceConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This class acts as a data container for beat-specific information.
 */
@Accessors(fluent=true)
public class Beat
{
	private static final Logger LOGGER = LoggerFactory.getLogger( Beat.class );

	/**
	 * Name of the Beat sending the events. If the shipper name is set in the configuration file, then that value is
	 * used. If it is not set, the hostname is used.
	 */
	@JsonProperty( "name" ) @Setter @Getter private String beatName;

	/**
	 * The hostname as returned by the operating system on which the Beat is running.
	 */
	@JsonProperty( "hostname" ) @Setter @Getter private String beatHostname = "localhost";

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

	public static Beat fromConfig(final DeviceConfig config)
	{
		return new Beat().beatName( config.getName() );
	}

}
