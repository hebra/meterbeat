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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.searchbox.client.JestClient;

public class JestClientFactory
{
	private static final Logger LOGGER = LoggerFactory.getLogger( io.searchbox.client.JestClientFactory.class );

	private static JestClient client;

	static
	{
//		if (config == null || config.getOutput() == null || config.getOutput().getElasticsearch() == null)
//		{
//			LOGGER.warn( "Elasticsearch output not configured." );
//		}
//		else
//		{
//			final Elasticsearch elasticsearch = config.getOutput().getElasticsearch();
//			final io.searchbox.client.JestClientFactory factory = new io.searchbox.client.JestClientFactory();
//
//			final HttpClientConfig.Builder configBuilder = new HttpClientConfig.Builder( elasticsearch.getHosts() );
//
//			// protocol
//
//			if (elasticsearch.getUsername() != null && elasticsearch.getPassword() != null)
//			{
//				configBuilder.defaultCredentials( elasticsearch.getUsername(), elasticsearch.getPassword() );
//			}
//
//
//
//			factory.setHttpClientConfig( new HttpClientConfig.Builder( "http://10.0.1.2:9200" ).multiThreaded( true ).build() );
//			client = factory.getObject();
//
//
//
//		}



	}

	private JestClientFactory()
	{

	}

	public static JestClient get()
	{
		return client;
	}
}
