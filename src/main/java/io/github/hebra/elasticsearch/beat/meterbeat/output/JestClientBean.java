/**
 * (C) 2016-2017 Hendrik Brandt <https://github.com/hebra/> This file is part of MeterBeat. MeterBeat is free software:
 * you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. MeterBeat is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with MeterBeat. If not, see <http://www.gnu.org/licenses/>.
 ***/

package io.github.hebra.elasticsearch.beat.meterbeat.output;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import lombok.Getter;
import lombok.Setter;

@Configuration
@Component
@ConfigurationProperties( "output.elasticsearch" )
public class JestClientBean
{
	@Getter
	@Setter
	private List < String > hosts;

	@Getter
	@Setter
	private String username;

	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	private int worker = 1;

	@Getter
	@Setter
	private String index = "meterbeat";

	@Getter
	@Setter
	private String path = "";

	@Getter
	@Setter
	private int max_retries = 3;

	@Getter
	@Setter
	private int timeout = 10;

	private static final Logger log = LoggerFactory
			.getLogger( io.searchbox.client.JestClientFactory.class );

	@Bean
	public JestClient jestClient()
	{
		final JestClientFactory factory = new JestClientFactory();

		final HttpClientConfig.Builder configBuilder = new HttpClientConfig.Builder(
				hosts ).connTimeout( timeout * 1000 )
						.readTimeout( timeout * 1000 );

		if ( StringUtils.isNoneEmpty( username ) )
		{
			configBuilder.defaultCredentials( username, password );
		}

		factory.setHttpClientConfig(
				configBuilder.multiThreaded( true ).build() );

		return factory.getObject();
	}
}
