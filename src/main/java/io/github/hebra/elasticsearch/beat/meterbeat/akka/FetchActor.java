/**
 * (C) 2016-2017 Hendrik Brandt <https://github.com/hebra/> This file is part of MeterBeat. MeterBeat is free software:
 * you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version. MeterBeat is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with MeterBeat. If not, see <http://www.gnu.org/licenses/>.
 ***/

package io.github.hebra.elasticsearch.beat.meterbeat.akka;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import io.github.hebra.elasticsearch.beat.meterbeat.configuration.InputDevicesConfiguration;
import io.github.hebra.elasticsearch.beat.meterbeat.device.IDevice;
import io.github.hebra.elasticsearch.beat.meterbeat.output.Beat;
import io.github.hebra.elasticsearch.beat.meterbeat.output.BeatOutput;
import io.github.hebra.elasticsearch.beat.meterbeat.output.Power;
import scala.concurrent.duration.Duration;

@Component
@Scope( "prototype" )
public class FetchActor extends UntypedActor
{
	@Autowired
	private ActorSystem actorSystem;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private SpringExtension springExtension;

	@Autowired
	private InputDevicesConfiguration inputDevicesConfiguration;

	private ActorRef pushActor;

	@Override
	public void onReceive( final Object message ) throws Exception
	{
		if ( pushActor == null )
		{
			pushActor = actorSystem.actorOf(
					springExtension.props( "pushOutputActor" ),
					"push-".concat( UUID.randomUUID().toString() ) );

		}

		if ( message instanceof IDevice )
		{
			final IDevice device = ( IDevice ) message;

			Optional.ofNullable( device.fetchData() ).ifPresent( watt -> {

				BeatOutput output = new BeatOutput();
				context.getAutowireCapableBeanFactory().autowireBean( output );

				pushActor
						.tell( output.beat( Beat.fromConfig( device.config() ) )
								.power( new Power( watt ) ), getSelf() );
			} );

			actorSystem.scheduler().scheduleOnce(
					Duration.create( inputDevicesConfiguration.getPeriod(),
							TimeUnit.SECONDS ),
					getSelf(), message, actorSystem.dispatcher(), null );

		}
		else
		{
			unhandled( message );
		}
	}

}
