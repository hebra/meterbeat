/**
 * (C) 2016 Hendrik Brandt <https://github.com/hebra/>
 *
 * This file is part of MeterBeat.
 *
 * MeterBeat is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * MeterBeat is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with MeterBeat. If not, see
 * <http://www.gnu.org/licenses/>.
 ***/

package io.github.hebra.elasticsearch.beat.meterbeat.output;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This class acts as a data container for power measurement results.
 */
@Accessors(fluent=true)
public class Power
{
	/**
	 * The measured numeric value of power consumption.
	 */
	@JsonProperty( "value" ) @Setter @Getter private BigDecimal value;

	/**
	 * The unit of measurement, by default W (Watt).
	 */
	@JsonProperty( "unit" ) @Setter @Getter private String unit = "W";

	public Power()
	{
	}

	public Power( final BigDecimal _value )
	{
		value = _value;
	}

	public Power( final String _value )
	{
		value = new BigDecimal( _value );
	}

	public Power( final long _value )
	{
		value = new BigDecimal( _value );
	}

	public Power( final double _value )
	{
		value = new BigDecimal( _value );
	}
}
