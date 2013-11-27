/*	ZombieGame
*/

package GameStates;

// imports

import java.util.*;

import org.lwjgl.input.Keyboard;

import AudioEngine.IAudioEngine;
import AudioEngine.ISound;
import Engine.IGameEngine;
import TextureEngine.ITextureEngine;
import TextureEngine.ITexture;
import Menu.IMenuController;
import Menu.IMenuScreen;
import Menu.IMenuWidget;
import Menu.MainMenuScreen;
import Engine.GameEngine;





/**
 * The menu gamestate.
 * This class handles all the menu screens. 
 * @author Jacob
 */

public class MenuState implements IGameState, IMenuController
{
	//
	public MenuState()
	{
		menuStack_ = new Stack<IMenuScreen>();
		currentMenu_ = null;	// initialize to a menu or something.
		refresh_ = false;
	}
	
	//
	// IMenuController interface methods
	//
	
	public void	ChangeMenuScreen(IMenuScreen menu) throws Exception
	{
		// FIXME: setup transition stuff here!
		
		// notify the current menu and push it onto the stack
		currentMenu_.Push();
		menuStack_.push(currentMenu_);
		
		// setup new menu
		currentMenu_ = menu;
		
		// FIXME: initialize the menu only once:
		// menu screens can create other menu screen (as part of a menu transition action, for example),
		// and sometimes these menus are initialized twice because they are fed into this function each time a
		// transition is performed.
		// Option A: have menus clear their state on each init, before setting up again
		// Option B: menus keep track of if they have initialized, and that signals this function to not init again
		// I chose option B.
		if (currentMenu_.IsInitialized() == false)
			currentMenu_.Init(this);
	}
	
	public void	PreviousMenu()
	{
		// FIXME: setup transition stuff here!
		
		//game_.LogMessage("MenuState::PreviousMenu");
		//GameEngine.DumpStackTrace(Thread.currentThread().getStackTrace(), null);
		
		if (menuStack_.size() == 0)
		{
			//game_.LogMessage("MenuState::PreviousMenu: WARNING: Stack size zero!");
			game_.EndGameLoop();
			return;
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
		return game_;
	}
	
	public ITextureEngine	GetGraphicsController()
	{
		return gfx_;
	}
	
	public IAudioEngine		GetAudioController()
	{
		return sfx_;
	}
	
	//
	// IGameState interface methods
	//
	
	public void	Init(ITextureEngine gfx, IAudioEngine sfx, IGameEngine game) throws Exception
	{
		gfx_ = gfx;
		sfx_ = sfx;
		game_ = game;
		
		// menus use a normalized ortho perspective
		float ratio = (float)gfx_.GetScreenHeight() / (float)gfx_.GetScreenWidth();
		gfx_.SetOrthoPerspective(-1.0f, 1.0f, -ratio, ratio);
		
		menuBackground_ = gfx_.LoadTexture("gfx/menu/menu-background4-lines.png", 1);//0x00FFFFFF);
		
		music_ = sfx_.LoadSound("snd/misc/menu_music.wav");
		music_.Loop();
		
		// create initial menu here
		currentMenu_ = new MainMenuScreen();
		currentMenu_.Init(this);
	}
	
	public void Quit()
	{
		music_.Stop();
	}
	
	public void	Update()
	{
		
		int[] keys = game_.GetKeyEvents();
		
		for (int i=0; i < keys.length; i++)
		{
			if (keys[i] == Keyboard.KEY_SPACE)
			{
				int randomScore = (int)(Math.random()*10000.0f);
				try {
					game_.ChangeGameState(new HighScoreState(randomScore));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		//refresh_ = true;
		
		currentMenu_.Update();
	}
	
	public void	Draw(float delta)
	{
		//if (refresh_ == false)
		//	return;
		
		//gfx_.ClearScreen();
		
		// draw a background
		menuBackground_.SetPos(-1.0f, -1.0f);
		menuBackground_.SetScale(2.0f, 2.0f);
		menuBackground_.Draw();
		
		currentMenu_.Draw(delta);
		
		//refresh_ = false;
	}
	
	public boolean GetPaused()
	{
		return false;
	}
	
	
	//
	// protected methods
	//
	
	protected IGameEngine		game_;
	protected ITextureEngine	gfx_;
	protected IAudioEngine		sfx_;
	
	protected ITexture			menuBackground_;
	protected ISound			music_;

	protected boolean			refresh_;
	
	protected Stack<IMenuScreen>	menuStack_;
	protected IMenuScreen			currentMenu_;
}

















