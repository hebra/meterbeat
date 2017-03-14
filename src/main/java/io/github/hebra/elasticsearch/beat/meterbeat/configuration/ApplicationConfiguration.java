package io.github.hebra.elasticsearch.beat.meterbeat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import io.github.hebra.elasticsearch.beat.meterbeat.akka.SpringExtension;

@Configuration
public class ApplicationConfiguration
{
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private SpringExtension springExtension;

	@Bean
	public ActorSystem actorSystem()
	{
		ActorSystem system = ActorSystem.create( "MeterBeat", config() );
		springExtension.initialize( applicationContext );
		return system;
	}

	@Bean
	public Config config()
	{
		return ConfigFactory.load();
	}
	
}
