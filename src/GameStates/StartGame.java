
package GameStates;


import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import Actions.CollidablePhysics;
import AudioEngine.IAudioEngine;
import Drawing.CameraView;
import Drawing.DrawText;
import Drawing.SimpleDraw;
import Drawing.TileDraw;
import Engine.IGameEngine;
import GameObjects.Box;
import GameObjects.Car;
import GameObjects.ExampleEntity;
import GameObjects.ExampleLevel;
import GameObjects.GameObject;
import GameObjects.HandGunProjectile;
import GameObjects.MachineGunPowerup;
import GameObjects.PhysicsBox;
import GameObjects.Player;
import GameObjects.Powerup;
import GameObjects.ShotgunPowerup;
import GameObjects.Spawner;
import GameObjects.TreeLeaves;
//import GameObjects.StaticBox;
import GameObjects.Zombie;
import Geometry.AABB;
import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;

/// example of making a scene. Creates ExampleObject and adds action to it

/**
 * StartGame a sandbox state showing how to create objects using nodes
 * 
 * try and reach the end of the level. :P
 */
public class StartGame extends EventListenerState
{
	public StartGame()
	{
		super();
		paused=false;
	}
	
	//
	// IGameState interface functions
	//
	
	@Override
	public void Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game) throws Exception 
	{
		super.Init(gfx, snd, game);
		// initialize level
		level = new ExampleLevel(gfx, snd, game, this);
		// scale it down
		level.scaleUniverse(.3f);
		
		// create entity
//		entity = new ExampleEntity(level);
		Player player = new Player(level);

		// changed to add objects at a random distance and angle
		float dist, angle;
		float minDistance = 2.0f;
		
		for(int i = 0; i < 50; i++)
		{
			Box sb = new Box(level, player);
			
			dist = (float)Math.random() * 50.0f + minDistance;
			angle = (float)Math.random() * 2.0f * (float)Math.PI;
			
			sb.setStartingLoc(
				(float)(Math.cos(angle)*dist), 
				(float)(Math.sin(angle)*dist)
			);
			
			if(Utility.CollisionDetection.getCollisions(sb.getRootNode(), level.getHandle()).length > 0)
			{
				level.removeEntity(sb);
			}
		}
		for(int i = 0; i < 5; i++)
		{
			PhysicsBox b = new PhysicsBox(level, player);
			//b.setStartingLoc((float)(Math.random()-.5)*5, (float)(Math.random()-.5)*5);
			
			dist = (float)Math.random() * 5.0f + minDistance;
			angle = (float)Math.random() * 2.0f * (float)Math.PI;
			
			b.setStartingLoc(
				(float)(Math.cos(angle)*dist), 
				(float)(Math.sin(angle)*dist)
			);
			
			
			if(Utility.CollisionDetection.getCollisions(b.getRootNode(), level.getHandle()).length > 0)
			{
				level.removeEntity(b);
			}
		}

		for(int i = 0; i < 0; i++)
		{
			Zombie z1 = new Zombie(level);
			
			dist = (float)Math.random() * 100.0f + minDistance;
			angle = (float)Math.random() * 100.0f * (float)Math.PI;
			
			z1.setStartingLoc(
				(float)(Math.cos(angle)*dist), 
				(float)(Math.sin(angle)*dist)
			);
			
			//z1.setStartingLoc((float)(Math.random()-.5)*20, (float)(Math.random()-.5)*20);
			z1.setTarget(player);			
			if(Utility.CollisionDetection.getCollisions(z1.getRootNode(), level.getHandle()).length > 0)
			{
				level.removeEntity(z1);
			}
		}
		
		Powerup p1 = new ShotgunPowerup(level, player);
		p1.setStartingLoc(2f, 2f);
		level.addEntity(p1);
		
		Powerup p2 = new MachineGunPowerup(level, player);
		p2.setStartingLoc(-2, 2);
		level.addEntity(p2);
		
		
		Car car = new Car(level);
		car.setStartingLoc(-2, -2);
		car.setColor(new Vector4f(1,.2f	,.1f,1));
		level.addEntity(car);
		
		
		Spawner s = new Spawner(level, player);
		s.setStartingLoc(5, 5);
		s.startSpawning();
		
		TreeLeaves tree = new TreeLeaves(level);
		tree.setStartingLoc(-3, 0);
	}
	
	@Override
	public void Quit() 
	{
		level.destroy();
	}

	@Override
	public void Update() 
	{
		super.Update();
		game.SetWindowTitle(""+game.GetFrameRate());
		
		level.update();	
	}

	@Override
	public void Draw(float delta) 
	{
		level.draw(delta);
	}
	
	public boolean GetPaused()
	{
		return paused;
	}
	
	//
	// IMenuController interface functions
	//
	
	
	
	
	//
	//
	//
	
	ExampleEntity entity;
	ExampleLevel level;	
	boolean paused;
}
