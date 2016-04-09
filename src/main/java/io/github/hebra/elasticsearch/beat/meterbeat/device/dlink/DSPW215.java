/**
 * (C) 2016 Hendrik Brandt <https://github.com/hebra/>
 *
 * This file is part of MeterBeat.
 *
 * MeterBeat is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * MeterBeat is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with MeterBeat. If not, see
 * <http://www.gnu.org/licenses/>.
 ***/

package io.github.hebra.elasticsearch.beat.meterbeat.device.dlink;

import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.SecureRandom;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.hebra.elasticsearch.beat.meterbeat.config.DeviceConfig;
import io.github.hebra.elasticsearch.beat.meterbeat.device.IDevice;
import lombok.Cleanup;
import lombok.Setter;

public class DSPW215 implements IDevice
{
	private final SecureRandom secureRandom = new SecureRandom();

	private static final Logger LOGGER = LoggerFactory.getLogger( DSPW215.class );

	@Setter
	private DeviceConfig config;

	@Override
	public String fetchData()
	{
		final String url = config.getBaseURL() + "/my_cgi.cgi?" + ( new BigInteger( 130, secureRandom ).toString( 32 ) );

		String tWatt = null;

		try
		{

			final URL obj = new URL( url );
			final HttpURLConnection con = ( HttpURLConnection ) obj.openConnection();

			con.setConnectTimeout( 5000 );

			// add reuqest header
			con.setRequestMethod( "POST" );
			con.setRequestProperty( "User-Agent", USER_AGENT );
			con.setRequestProperty( "Accept-Language", "en-US,en;q=0.5" );

			final String urlParameters = "request=create_chklst";

			// Send post request
			con.setDoOutput( true );
			@Cleanup final DataOutputStream wr = new DataOutputStream( con.getOutputStream() );
			wr.writeBytes( urlParameters );
			wr.flush();

			final int responseCode = con.getResponseCode();

			final String response = IOUtils.toString( con.getInputStream(), Charsets.US_ASCII );



			//MeasurementResult.of();

			// thermal
			// power on off
			// power consumption in watt

			tWatt = response.split( "Meter Watt: ", 2 )[ 1 ].trim();

			System.out.println( config.getName() );
			System.out.println( response );
			System.out.println( tWatt );

		}
		catch ( final SocketTimeoutException stEx )
		{
			LOGGER.error( "Timeout when trying to read from {}: {}", config.getBaseURL(), stEx.getMessage(), stEx );
		}
		catch ( final IOException ioEx )
		{
			LOGGER.error( ioEx.getMessage(), ioEx );
		}

		return tWatt;

	}
}
