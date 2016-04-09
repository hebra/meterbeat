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

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import io.github.hebra.elasticsearch.beat.meterbeat.device.IDevice;

public class MasterActor extends UntypedActor
{
	private final ActorRef fetchActor = getContext().actorOf( new Props( FetchActor.class ), "fetch" );

	@Override
	public void onReceive( final Object _message ) throws Exception
	{
		if ( _message instanceof IDevice )
		{
			fetchActor.tell( _message );
		}
		else
		{
			unhandled( _message );
		}
	}
}