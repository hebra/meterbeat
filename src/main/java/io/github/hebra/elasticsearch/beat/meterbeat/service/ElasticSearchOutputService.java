/**
 * (C) 2016-2017 Hendrik Brandt <https://github.com/hebra/> This file is part of MeterBeat. MeterBeat is free software:
 * you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. MeterBeat is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with MeterBeat. If not, see <http://www.gnu.org/licenses/>.
 ***/
package io.github.hebra.elasticsearch.beat.meterbeat.service;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.github.hebra.elasticsearch.beat.meterbeat.output.BeatOutput;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;
import io.searchbox.indices.mapping.PutMapping;
import lombok.Getter;
import lombok.Setter;

@Service
@Configuration
@Component
@ConfigurationProperties( "output.elasticsearch" )
public class ElasticSearchOutputService implements OutputService
{
	private static final Logger log = LoggerFactory
			.getLogger( ElasticSearchOutputService.class );

	@Getter
	@Setter
	private int worker = 1;

	@Getter
	@Setter
	private String index = "meterbeat";

	@Getter
	@Setter
	private String path = "";

	@Autowired
	private JestClient jestClient;

	@Override
	public boolean isConfigured()
	{
		return jestClient != null && StringUtils.isNoneEmpty( index );
	}

	@PostConstruct
	private void postConstruct()
	{
		/*
		 * Create the index (if not exist) in a synchronized fashion right after service was constructed to avoid future
		 * parallel index creation exceptions from elasticsearch.
		 */

		synchronized ( jestClient )
		{
			try
			{
				if ( !jestClient
						.execute( new IndicesExists.Builder( index ).build() )
						.isSucceeded() )
				{
					jestClient.execute(
							new CreateIndex.Builder( index ).build() );
					
					PutMapping putMapping = new PutMapping.Builder(
					        index,
					        "power",
					        "{ \"power\" : { \"properties\" : { " + 
					        		"\"timestamp\" : {\"type\" : \"date\", \"store\" : \"yes\"}, " + 
					        		"\"type\" : {\"type\" : \"string\", \"store\" : \"yes\"}, " + 
					        		"\"power\" : {\"properties\" : { \"value\" : {\"type\" : \"double\", \"store\" : \"yes\"},\"unit\" : {\"type\" : \"string\", \"store\" : \"yes\"}}}, " + 
					        		"\"beat\"  : {\"properties\" : { \"name\" : {\"type\" : \"string\", \"store\" : \"yes\"},\"hostname\" : {\"type\" : \"string\", \"store\" : \"yes\"}} } } }}"
					).build();
					jestClient.execute(putMapping);
					
					log.info( "Index '{}' created.", index );
				}
			}
			catch ( IOException ioEx )
			{
				log.error( ioEx.getMessage() );
			}
		}
	}

	@Override
	public void send( BeatOutput output )
	{
		try
		{
			jestClient.execute( new Index.Builder( output.asJson() )
					.index( index ).type( output.type() ).build() );
		}
		catch ( IOException ioEx )
		{
			log.error( "Error while sending beat output for host '{}' to ElasticSearch: {}", output.beat().beatName(), ioEx.getMessage() );
		}

	}
}
