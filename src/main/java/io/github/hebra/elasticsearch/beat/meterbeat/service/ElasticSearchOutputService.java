package io.github.hebra.elasticsearch.beat.meterbeat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import akka.actor.ActorSystem;
import io.github.hebra.elasticsearch.beat.meterbeat.output.BeatOutput;

@Service
public class ElasticSearchOutputService implements OutputService
{
	@Autowired
	private ActorSystem actorSystem;

	@Override
	public boolean isConfigured()
	{
		return true;
	}

	@Override
	public void send(BeatOutput output)
	{
		// TODO Auto-generated method stub
		
	}

}
