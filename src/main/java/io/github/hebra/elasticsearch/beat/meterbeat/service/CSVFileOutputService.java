/**
 * (C) 2016-2017 Hendrik Brandt <https://github.com/hebra/> This file is part of MeterBeat. MeterBeat is free software: you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. MeterBeat is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with MeterBeat. If not, see <http://www.gnu.org/licenses/>.
 ***/
package io.github.hebra.elasticsearch.beat.meterbeat.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.github.hebra.elasticsearch.beat.meterbeat.output.BeatOutput;
import lombok.Getter;
import lombok.Setter;

@Service
@Configuration
@Component
@ConfigurationProperties( "output.csv" )
public class CSVFileOutputService implements OutputService
{
	private static final Logger log = LoggerFactory
			.getLogger( CSVFileOutputService.class );

	@Getter
	@Setter
	private String path;

	@Getter
	@Setter
	private String filename;

	@Getter
	@Setter
	private Character delimiter = ',';

	@Getter
	@Setter
	private Character quote;

	@Getter
	@Setter
	private boolean enable;

	@Autowired
	private ApplicationContext applicationContext;

	private Path outputPath;

	@Autowired
	public CSVFileOutputService( ApplicationContext applicationContext )
	{
	}

	@PostConstruct
	private void init()
	{
		if ( isConfigured() )
		{
			outputPath = Paths.get( path, filename );

			if ( !Files.exists( outputPath ) )
			{
				try
				{
					Files.createFile( outputPath );
				}
				catch ( IOException ioEx )
				{
					log.error( "Unable to create file: {}", ioEx.getMessage() );
				}
			}
		}
	}

	@Override
	public boolean isConfigured()
	{
		return path != null && filename != null && enable;
	}

	@Override
	public synchronized void send( BeatOutput output )
	{
		if ( isEnable() )
		{

			try (PrintWriter writer = new PrintWriter(
					new FileOutputStream( outputPath.toFile(), true ) ))
			{
				CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader()
						.withDelimiter( delimiter )
						.withQuote( quote )
						.withQuoteMode( QuoteMode.NON_NUMERIC )
						.withSkipHeaderRecord( true );

				CSVPrinter csvFilePrinter = new CSVPrinter( writer,
						csvFileFormat );
				csvFilePrinter.printRecord( output.asIterable() );
				csvFilePrinter.close();
			}
			catch ( IOException ioEx )
			{
				log.error( ioEx.getMessage() );
			}
		}
	}
}
