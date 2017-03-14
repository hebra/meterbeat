package io.github.hebra.elasticsearch.beat.meterbeat.service;

import io.github.hebra.elasticsearch.beat.meterbeat.output.BeatOutput;

public interface OutputService
{
	public boolean isConfigured();
	
	
	public void send(BeatOutput output);
}
