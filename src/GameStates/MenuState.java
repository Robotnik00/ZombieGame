/*	ZombieGame
*/

package GameStates;

// imports

import java.util.*;

import org.lwjgl.input.Keyboard;

import AudioEngine.IAudioEngine;
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
		
		// initialize the menu only once:
		// FIXME: menu screens can create other menu screen (as part of a menu transition action, for example),
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
		
		if (menuStack_.size() == 0)
		{
			game_.LogMessage("MenuState::PreviousMenu: WARNING: Stack size zero!");
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
		
		// create initial menu here
		currentMenu_ = new MainMenuScreen();
		currentMenu_.Init(this);
	}
	
	public void Quit()
	{
		//
	}
	
	public void	Update()
	{
		int[] keys = game_.GetKeyEvents();
		
		for (int i=0; i < keys.length; i++)
		{
			// quit if escape is pressed, but not if there are any widgets currently in focus.
			// *cough*TextFieldWidget*cough*
			if (keys[i] == Keyboard.KEY_ESCAPE)
			{
				if (currentMenu_.IsFocused() == false)
					game_.EndGameLoop();
			}
		}
		
		currentMenu_.Update();
	}
	
	public void	Draw(float delta)
	{
		gfx_.ClearScreen();
		
		// draw a background
		menuBackground_.SetPos(-1.0f, -1.0f);
		menuBackground_.SetScale(2.0f, 2.0f);
		menuBackground_.Draw();
		
		currentMenu_.Draw(delta);
	}
	
	
	
	//
	// protected methods
	//
	
	protected IGameEngine		game_;
	protected ITextureEngine	gfx_;
	protected IAudioEngine		sfx_;
	
	protected ITexture			menuBackground_;
	
	protected Stack<IMenuScreen>	menuStack_;
	protected IMenuScreen			currentMenu_;
}

















