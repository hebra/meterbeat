package io.github.hebra.elasticsearch.beat.meterbeat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory
{
	@Autowired
	private ElasticSearchOutputService elasticSearchOutputService;

	@Autowired
	private CSVFileOutputService csvFileOutputService;

	public List < OutputService > getServices()
	{
		List < OutputService > services = new ArrayList <>();

		if ( elasticSearchOutputService != null && elasticSearchOutputService.isConfigured() )
		{
			services.add( elasticSearchOutputService );
		}

		if ( csvFileOutputService != null && csvFileOutputService.isConfigured() )
		{
			services.add( csvFileOutputService );
		}

		return services;
	}
}
