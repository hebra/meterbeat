package io.github.hebra.elasticsearch.beat.meterbeat.output;

import io.searchbox.client.JestClient;
import io.searchbox.client.config.HttpClientConfig;

public class JestClientFactory
{
	private static JestClient client;

	static
	{
		final io.searchbox.client.JestClientFactory factory = new io.searchbox.client.JestClientFactory();
		factory.setHttpClientConfig( new HttpClientConfig.Builder( "http://10.0.1.2:9200" ).multiThreaded( true ).build() );
		client = factory.getObject();
	}

	private JestClientFactory()
	{

	}

	public static JestClient get()
	{
		return client;
	}
}
