package io.github.hebra.elasticsearch.beat.meterbeat.output;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BeatOutputTest
{
	ObjectMapper mapper = new ObjectMapper();


	private static BeatOutput getBeat()
	{
		return new BeatOutput().beat( new Beat().beatHostname( "testhost" ).beatName( "testname" ) ).power( new Power( 42 ) );
	}

	@Test
	public void validateJsonBeatHostname() throws IOException
	{
		final BeatOutput beatOutput = getBeat();

		final String jsonString = mapper.writeValueAsString( beatOutput );

		final BeatOutput parsedOutput = mapper.readValue( jsonString, BeatOutput.class );

		assertEquals( beatOutput.beat().beatHostname(), parsedOutput.beat().beatHostname() );
	}

	@Test
	public void validateJsonBeatName() throws IOException
	{
		final BeatOutput beatOutput = getBeat();

		final String jsonString = mapper.writeValueAsString( beatOutput );

		final BeatOutput parsedOutput = mapper.readValue( jsonString, BeatOutput.class );

		assertEquals( beatOutput.beat().beatName(), parsedOutput.beat().beatName() );
	}

	@Test
	public void validateJsonBeatPower() throws IOException
	{
		final BeatOutput beatOutput = getBeat();

		final String jsonString = mapper.writeValueAsString( beatOutput );

		final BeatOutput parsedOutput = mapper.readValue( jsonString, BeatOutput.class );

		assertEquals( beatOutput.power().value(), parsedOutput.power().value() );
	}

}
