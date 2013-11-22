package GameObjects;

import TextureEngine.ITexture;
import Actions.Action;
import Actions.TimeToLive;
import Drawing.SimpleDraw;

/**
 * spawns zombies.  you can set the number of zombies to spawn, the time for each spawn,
 * and how long the spawner lasts. 
 */

public class Spawner extends Entity
{

	public Spawner(Universe universe, Player target) {
		super(universe);
		this.target = target;
	}

	@Override
	public void createObject(Universe universe) {
		// TODO Auto-generated method stub
		GameObject gimble = new GameObject();
		spawnertex = universe.getTextureEngine().LoadTexture("gfx/Environment/spawner.png", 0);
		SimpleDraw drawtree = new SimpleDraw(spawnertex);
		gimble.setDrawingInterface(drawtree);
		gimble.setRotationalVelocity(.3f);
		rootNode.addChild(gimble);
		
		universe.getBackgroundNode().addChild(rootNode);
		
		
	}

	public void startSpawning()
	{
		time = universe.getGameEngine().GetTime();
		
		Action spawnZombies = new Action()
		{

			
			@Override
			public void performAction() 
			{
				long elapsedTime = universe.getGameEngine().GetTime() - time;
				if(elapsedTime > timeperspawn)
				{
					Zombie z = new Zombie(universe);
					z.setTarget(target);
					z.setStartingLoc(rootNode.getLocalX(), rootNode.getLocalY());
					universe.addEntity(z);
					spawnedZombies++;
					time = universe.getGameEngine().GetTime();
					
				}
				if(spawnedZombies == maxZombies)
				{
					universe.getBackgroundNode().removeChild(rootNode);
				}
			}
			
			
			
			
		};
		
		rootNode.addAction(spawnZombies);
		
		
		
	}
	
	public void setTimeToLive(long ttl)
	{
		TimeToLive timtolive = new TimeToLive(this, universe);
		timtolive.setTimeToLive(ttl);
		rootNode.addAction(timtolive);
		timtolive.start();
	}
	
	@Override
	public void destroy() 
	{
		universe.getBackgroundNode().removeChild(rootNode);
		
	}
	long timeperspawn = 1000; // time in mS
	long time = 0;
	
	int spawnedZombies = 0;
	int maxZombies = 50;
	Player target;
	ITexture spawnertex;
	
}
