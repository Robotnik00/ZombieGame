package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import Utility.CollisionDetection;

import Actions.Action;
import Actions.TimeToLive;
import Drawing.DrawText;

public class Wave extends Entity
{

	public Wave(Universe universe, Player player, int n) {
		super(universe);
		this.player = player;
		waveNumber = n;
		
		// create some boxes
		float dist;
		float minDistance = .5f;
		float angle;
		for(int i = 0; i < 5; i++)
		{
			PhysicsBox b = new PhysicsBox(universe, player);
			//b.setStartingLoc((float)(Math.random()-.5)*5, (float)(Math.random()-.5)*5);
			
			dist = (float)Math.random() * 5.0f + minDistance;
			angle = (float)Math.random() * 2.0f * (float)Math.PI;
			
			b.setStartingLoc(
				(float)(Math.cos(angle)*dist), 
				(float)(Math.sin(angle)*dist)
			);
			
			
			if(Utility.CollisionDetection.getCollisions(b.getRootNode(), universe.getHandle()).length > 0)
			{
				universe.removeEntity(b);
			}
		}
		
	}

	@Override
	public void createObject(final Universe universe) 
	{
		drawTimeToStart = new DrawText(universe.getTextureEngine(), "font.png");

		drawTimeToStartNode = new GameObject();
		drawTimeToStartNode.setDrawingInterface(drawTimeToStart);
		drawTimeToStart.setText("Next Wave ");
		drawTimeToStart.setScale(.3f, .3f);
		drawTimeToStartNode.setLocalX(-.85f);
		drawTimeToStartNode.setLocalY(0f);
		drawTimeToStart.setColor(new Vector4f(0,0,0,.5f));
		timeCreated = universe.getTime();
		
		universe.getHUD().addChild(drawTimeToStartNode);
		
		Action updateTimeToStart = new Action()
		{

			@Override
			public void performAction() 
			{
				long time = universe.getTime() - timeCreated;
				if(time > 15000)
				{
					universe.getHUD().removeChild(drawTimeToStartNode);
					startWave();
				}
				if(time < 14000)
				{
					
					drawTimeToStart.setText("Next Wave \n    " + (15000 - time)/1000);
				}
				else if(time > 14000)
				{
					drawTimeToStart.setText("   GO!\n");
				}
				
			}
			
		};
		
		drawTimeToStartNode.addAction(updateTimeToStart);
		
	}
	
	public void startWave()
	{

		ttl = new TimeToLive(this, universe);
		ttl.setTimeToLive(waveLength);
		rootNode.addAction(ttl);
		ttl.start();
				
		universe.addEntity(this);
		
		player.setWave(this);
		
		System.out.printf("%d\n", numSpawners);
		for(int i = 0; i < numSpawners; i++)
		{
			Spawner s = new Spawner(universe, player);
			s.setStartingLoc((float)(Math.random()-.5)*10, (float)(Math.random()-.5)*10);
			s.setNumZombies(zombiesPerSpawner);
			s.setZombieSpeed(zombieSpeed);
			s.setZombieHealth(zombieHealth);
			s.startSpawning();
			s.setTimeToLive(waveLength);
			
			if(CollisionDetection.getCollisions(s.getRootNode(), universe.getHandle()).length > 0)
			{
				s.destroy(); // don't create them on top of eachother
			}
		}
		
		
		
		
		
	}
	
	public void setNumSpawners(int num)
	{
		numSpawners = num;
	}
	public void setZombieHealth(float hp)
	{
		zombieHealth = hp;
	}
	public void setZombieSpeed(float speed)
	{
		zombieSpeed = speed;
	}
	public void setSpawnerSpeed(long speed)
	{
		spawnerSpeed = speed;
	}
	public void setWaveLength(long wl)
	{
		waveLength = wl;
	}
	public int getWaveNumber()
	{
		return waveNumber;
	}
	public long getTimeLeft()
	{
		return ttl.getTimeLeft();
	}
	
	@Override
	public void destroy() 
	{
		destroyed = true;
		universe.removeEntity(this);
		
		
	}
	
	int numSpawners = 1;
	
	long spawnerSpeed = 1000;
	
	float zombieHealth = .5f;
	float zombieSpeed = 1f;
	
	long waveLength = 30000; // default wave is 30 secs
	long timeToStart = 10000;
	int zombiesPerSpawner = 100;
	
	
	
	int waveNumber = 1;
	long timeCreated;
	
	TimeToLive ttl;
	
	Wave nextWave;
	Player player;
	DrawText drawTimeToStart;
	GameObject drawTimeToStartNode;
	

}
