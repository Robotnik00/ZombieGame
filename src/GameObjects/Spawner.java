package GameObjects;

import javax.swing.text.Utilities;

import org.lwjgl.util.vector.Vector4f;

import TextureEngine.ITexture;
import Actions.Action;
import Actions.TimeToLive;
import Drawing.DrawText;
import Drawing.SimpleDraw;
import Geometry.AABB;

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
		rootNode.setBoundingBox(new AABB(.5f,.5f));
		universe.getBackgroundNode().addChild(rootNode);
		
		drawNumZombies = new DrawText(universe.getTextureEngine(), "gfx/font.png");
		GameObject text = new GameObject();
		text.setDrawingInterface(drawNumZombies);
		//rootNode.addChild(text); // uncomment to draw number of zombies left to spawn
		drawNumZombies.setScale(.4f, .4f);
		text.setLocalX(-.4f);
		text.setLocalY(-.2f);
		drawNumZombies.setColor(new Vector4f(.5f,0,.4f,1));
	}

	public void startSpawning()
	{
		time = universe.getTime();
		
		Action spawnZombies = new Action()
		{

			
			@Override
			public void performAction() 
			{
				long elapsedTime = universe.getTime() - time;
				if(timtolive == null)
				{
					drawNumZombies.setText("" + (maxZombies - spawnedZombies));
				}
				else 
				{
					drawNumZombies.setText("" + timtolive.getTimeLeft()/1000);
				}
				
				if(elapsedTime > timeperspawn)
				{
					
					if(Utility.CollisionDetection.getCollisions(rootNode, universe.getHandle()).length == 0 && Zombie.zombies.size() < maxZombiesInGame)
					{
						Zombie z = new Zombie(universe);
						z.setTarget(target);
						z.setStartingLoc(rootNode.getLocalX(), rootNode.getLocalY());
						z.setHP(zombieHealth);
						z.setMaxHp(zombieHealth);
						z.setSpeed(zombieSpeed);
						
						universe.addEntity(z);
						spawnedZombies++;
						
					}
					time = universe.getTime();
					
				}
				if(spawnedZombies == maxZombies)
				{
					universe.getBackgroundNode().removeChild(rootNode);
				}
			}
			
			
			
			
		};
		
		rootNode.addAction(spawnZombies);
		
		
		
	}
	public void setNumZombies(int num)
	{
		maxZombies = num;
	}
	
	public void setTimeToLive(long ttl)
	{
		timtolive = new TimeToLive(this, universe);
		timtolive.setTimeToLive(ttl);
		rootNode.addAction(timtolive);
		timtolive.start();
	}
	
	public void setZombieHealth(float hp)
	{
		zombieHealth = hp;
	}
	public void setZombieSpeed(float speed)
	{
		zombieSpeed = speed;
	}
	public void setTimePerSpawn(long time)
	{
		timeperspawn = time;
	}
	
	@Override
	public void destroy() 
	{
		universe.getBackgroundNode().removeChild(rootNode);
		
	}
	float zombieHealth = 1;
	float zombieSpeed = 1;
	
	
	TimeToLive timtolive;
	long timeperspawn = 1000; // time in mS
	long time = 0;
	
	int spawnedZombies = 0;
	int maxZombies = 50;
	Player target;
	ITexture spawnertex;
	DrawText drawNumZombies;
	
	static int maxZombiesInGame = 50;
}
