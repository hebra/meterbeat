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

package io.github.hebra.elasticsearch.beat.meterbeat.device.dlink;

import java.io.IOException;
import java.math.BigInteger;
import java.net.SocketTimeoutException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.hebra.elasticsearch.beat.meterbeat.config.DeviceConfig;
import io.github.hebra.elasticsearch.beat.meterbeat.device.IDevice;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors( fluent = true )
public class DSPW215 implements IDevice
{
	private final SecureRandom secureRandom = new SecureRandom();

	private static final Logger LOGGER = LoggerFactory.getLogger( DSPW215.class );

	@Setter
	@Getter
	private DeviceConfig config;

	@SuppressWarnings( "resource" )
	@Override
	public String fetchData()
	{
		final String url = config.getBaseURL() + "/my_cgi.cgi?" + ( new BigInteger( 130, secureRandom ).toString( 32 ) );

		try
		{
			final HttpClient client = HttpClientBuilder.create().setConnectionTimeToLive( 5, TimeUnit.SECONDS ).setConnectionReuseStrategy( new NoConnectionReuseStrategy() ).build();
			final HttpPost post = new HttpPost( url );

			post.setHeader( "User-Agent", USER_AGENT );
			post.setHeader( "Accept-Language", "en-US,en;q=0.5" );

			final List<NameValuePair> urlParameters = new ArrayList<>();
			urlParameters.add( new BasicNameValuePair( "request", "create_chklst" ) );
			post.setEntity( new UrlEncodedFormEntity( urlParameters ) );

			final HttpResponse response = client.execute( post );

			final String content = IOUtils.toString( response.getEntity().getContent(), Charsets.US_ASCII );

			EntityUtils.consume( response.getEntity() );

			return content.split( "Meter Watt: ", 2 )[ 1 ].trim();
		}
		catch ( final SocketTimeoutException stEx )
		{
			LOGGER.error( "Timeout when trying to read from {}: {}", config.getBaseURL(), stEx.getMessage(), stEx );
		}
		catch ( final IOException ioEx )
		{
			LOGGER.error( ioEx.getMessage(), ioEx );
		}

		return null;
	}
}
