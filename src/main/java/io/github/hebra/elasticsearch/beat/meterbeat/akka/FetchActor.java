/**
 * (C) 2016 Hendrik Brandt <https://github.com/hebra/>
 *
 * This file is part of MeterBeat.
 *
 * MeterBeat is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * MeterBeat is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with MeterBeat. If not, see
 * <http://www.gnu.org/licenses/>.
 ***/

package io.github.hebra.elasticsearch.beat.meterbeat.akka;

import java.util.concurrent.TimeUnit;

import akka.actor.UntypedActor;
import akka.util.Duration;
import io.github.hebra.elasticsearch.beat.meterbeat.device.IDevice;

public class FetchActor extends UntypedActor
{

	@Override
	public void onReceive( final Object _message ) throws Exception
	{
		if ( _message instanceof IDevice )
		{
			final IDevice device_ = ( IDevice ) _message;

			device_.fetchData();

			final Duration tDuration = Duration.create( 5L, TimeUnit.SECONDS );
			getContext().system().scheduler().scheduleOnce( tDuration, getSelf(), _message );

		}
		else
		{
			unhandled( _message );
		}
	}

}