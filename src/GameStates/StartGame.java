
package GameStates;

import java.util.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import Actions.CollidablePhysics;
import AudioEngine.IAudioEngine;
import Drawing.CameraView;
import Drawing.DrawText;
import Drawing.SimpleDraw;
import Drawing.TileDraw;
import Engine.GameEngine;
import Engine.IGameEngine;
import GameObjects.Box;
import GameObjects.Car;
import GameObjects.DamageMultiplier;
import GameObjects.ExampleEntity;
import GameObjects.ExampleLevel;
import GameObjects.GameObject;
import GameObjects.HandGunProjectile;
import GameObjects.Invulnerable;
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
import Menu.IMenuController;
import Menu.IMenuScreen;
import Menu.PauseMenuScreen;

/// example of making a scene. Creates ExampleObject and adds action to it

/**
 * StartGame a sandbox state showing how to create objects using nodes
 * 
 * try and reach the end of the level. :P
 */
public class StartGame extends EventListenerState implements IMenuController
{
	public StartGame()
	{
		super();
		paused=false;
		menuStack_ = new Stack<IMenuScreen>();
	}
	
	//
	// IMenuController interface functions
	//
	
	public void	ChangeMenuScreen(IMenuScreen menu) throws Exception
	{
		// FIXME: setup transition stuff here!
		
		// notify the current menu and push it onto the stack
		currentMenu_.Push();
		menuStack_.push(currentMenu_);
		
		// setup new menu
		currentMenu_ = menu;
		
		if (currentMenu_.IsInitialized() == false)
			currentMenu_.Init(this);
	}
	
	public void	PreviousMenu()
	{
		// FIXME: setup transition stuff here!
		
		if (menuStack_.size() == 0)
		{
			// unpause
			paused = false;
			currentMenu_ = null;
			return;	// don't mess with the menu stack after this
		}
		
		// kill current menu
		currentMenu_.Quit();
		currentMenu_ = null;
		
		// notify stack menu to restore stuff
		currentMenu_ = menuStack_.pop();
		currentMenu_.Pop();
	}
	
	public IGameEngine		GetGameController()
	{
		return game;
	}
	
	public ITextureEngine	GetGraphicsController()
	{
		return gfx;
	}
	
	public IAudioEngine		GetAudioController()
	{
		return snd;
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
		
		DamageMultiplier d = new DamageMultiplier(level, player);
		level.addEntity(d);
		d.setStartingLoc(1, -3);
		DamageMultiplier d2 = new DamageMultiplier(level, player);
		level.addEntity(d2);
		d2.setStartingLoc(2, -3);
		
		Invulnerable i = new Invulnerable(level, player);
		i.setStartingLoc(.5f, 0);
		level.addEntity(i);
		
	}
	
	@Override
	public void Quit() 
	{
		level.destroy();
	}

	@Override
	public void Update()
	{
		// don't call the event listeners unless the game is unpaused
		//super.Update();
		
		game.SetWindowTitle(""+game.GetFrameRate());
		
		if (paused)
		{
			// update pause screen menus
			currentMenu_.Update();
		}
		else
		{
			// check if ESC was pressed, if so pause
			int[] keyEvents = game.GetKeyEvents();
			
			for (int i=0; i < keyEvents.length; i++)
			{
				if (keyEvents[i] == Keyboard.KEY_ESCAPE)
				{
					// pause the game
					paused = true;
					
					menuStack_.clear();
					
					// create the pause menu
					currentMenu_ = new PauseMenuScreen();
					
					try {
						currentMenu_.Init(this);
					} catch (Exception e) {
						game.LogMessage("StartGame::Update: Couldn't initialize PauseMenuScreen: "+e.getMessage());
						paused = false;
						currentMenu_ = null;
					}
					
				}
			}
			
			// if still not paused, then update
			if (!paused)
			{
				super.Update();
				level.update();
			}
		}
	}

	@Override
	public void Draw(float delta) 
	{
		if (paused)
		{
			level.draw(0.0f);
			
			// "grey-out" the game
			gfx.SetDrawColor(0.0f, 0.0f, 0.0f, 0.5f);
			gfx.DrawRectangle(-1.0f, -1.0f, 1.0f, 1.0f);
			
			// draw menu on top of game
			currentMenu_.Draw(delta);
		}
		else
		{
			level.draw(delta);
		}
	}
	
	public boolean GetPaused()
	{
		return paused;
	}
	
	
	
	//
	//
	//
	
	ExampleEntity entity;
	ExampleLevel level;	
	
	// pause stuff
	protected boolean paused;
	protected Stack<IMenuScreen>	menuStack_;
	protected IMenuScreen			currentMenu_;
}
