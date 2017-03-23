/**
 * (C) 2016-2017 Hendrik Brandt <https://github.com/hebra/> This file is part of MeterBeat. MeterBeat is free software: you
 * can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. MeterBeat is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with MeterBeat. If not, see <http://www.gnu.org/licenses/>.
 ***/
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
