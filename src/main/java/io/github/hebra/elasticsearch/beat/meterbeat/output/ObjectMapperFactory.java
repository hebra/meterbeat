package io.github.hebra.elasticsearch.beat.meterbeat.output;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.hebra.elasticsearch.beat.meterbeat.MeterBeat;

public class ObjectMapperFactory
{
	private ObjectMapperFactory()
	{
	}

	/**
	 * Get a new instance of an {@link ObjectMapper} with default {@link MeterBeat} configuration.
	 *
	 * @return {@link ObjectMapper}
	 */
	public static ObjectMapper get()
	{
		return new ObjectMapper();
	}
}
