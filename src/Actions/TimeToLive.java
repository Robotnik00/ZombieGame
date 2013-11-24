package Actions;

import GameObjects.Entity;
import GameObjects.GameObject;
import GameObjects.Universe;


public class TimeToLive implements Action
{
	public TimeToLive(Entity entity, Universe universe)
	{
		this.universe = universe;
		this.entity = entity;
	}

	@Override
	public void performAction() 
	{
		if(started)
		{
			time = universe.getTime() - startTime;//universe.getGameEngine().GetTime() - startTime;
			if(time >= timeToLive)
			{
				entity.destroy();
			}
		}
	}
	public void start()
	{

		startTime = universe.getTime() - startTime;//universe.getGameEngine().GetTime(); 
		started = true;
	}
	public void addTime(long time)
	{
		this.timeToLive += time;
	}
	public void setTimeToLive(long timeToLive)
	{
		this.timeToLive = timeToLive;
	}
	public long getTimeLeft()
	{
		return timeToLive - time;
	}
	
	
	
	boolean started = false;
	
	
	long startTime;
	long time = 0;
	
	long timeToLive = 100; // default 100 ms
	
	Entity entity;
	Universe universe;
}
