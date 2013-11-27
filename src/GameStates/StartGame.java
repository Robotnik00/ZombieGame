
package GameStates;

import java.util.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import Actions.CollidablePhysics;
import AudioEngine.IAudioEngine;
import AudioEngine.ISound;
import Drawing.CameraView;
import Drawing.DrawText;
import Drawing.SimpleDraw;
import Drawing.TileDraw;
import Engine.GameEngine;
import Engine.IGameEngine;
import GameObjects.Box;
import GameObjects.Car;
import GameObjects.DamageMultiplier;
import GameObjects.Entity;
import GameObjects.ExampleEntity;
import GameObjects.ExampleLevel;
import GameObjects.GameObject;
import GameObjects.HandGunProjectile;
import GameObjects.Invulnerable;
import GameObjects.Level;
import GameObjects.MachineGunPowerup;
import GameObjects.PhysicsBox;
import GameObjects.Player;
import GameObjects.Powerup;
import GameObjects.ShotgunPowerup;
import GameObjects.Spawner;
import GameObjects.TreeLeaves;
import GameObjects.Universe;
import GameObjects.Wall;
import GameObjects.Wave;
import GameObjects.WaveManager;
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
			music_.Play();
			player.setControls(game.GetGameConfig());
			player.resetState();
			snd.SetVolume(game.GetGameConfig().GetFloatValue("sound_volume"));
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
		level = new Level(gfx, snd, game, this);
		// scale it down
		level.scaleUniverse(.3f);
		
		// create entity
		player = new Player(level);
		player.setControls(game.GetGameConfig());
		
		Entity wave = new WaveManager(level, player);
		level.addEntity(wave);
	
		// update this
		snd.SetVolume(game.GetGameConfig().GetFloatValue("sound_volume"));
		
		music_ = snd.LoadSound("snd/misc/ingame_music3.wav");
		music_.Loop();
	}
	
	@Override
	public void Quit() 
	{
		level.destroy();
		music_.Stop();
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
					
					music_.Pause();
					
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
	Player player;
	Universe level;	
	
	// pause stuff
	protected boolean paused;
	protected Stack<IMenuScreen>	menuStack_;
	protected IMenuScreen			currentMenu_;
	protected ISound				music_;
}
