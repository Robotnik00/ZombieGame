/*	ZombieGame
*/

package GameStates;

// imports

import java.util.*;

import org.lwjgl.input.Keyboard;

import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import TextureEngine.ITextureEngine;
import Menu.IMenuController;
import Menu.IMenuScreen;
import Menu.IMenuWidget;
import Menu.MainMenuScreen;


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
		currentMenu_.Init(this);
		
	}
	
	public void	PreviousMenu()
	{
		// FIXME: setup transition stuff here!
		
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
			if (keys[i] == Keyboard.KEY_ESCAPE)
				game_.EndGameLoop();
		}
		
		currentMenu_.Update();
	}
	
	public void	Draw(float delta)
	{
		gfx_.ClearScreen();
		currentMenu_.Draw(delta);
	}
	
	
	
	//
	// protected methods
	//
	
	protected IGameEngine		game_;
	protected ITextureEngine	gfx_;
	protected IAudioEngine		sfx_;
	
	protected Stack<IMenuScreen>	menuStack_;
	protected IMenuScreen			currentMenu_;
}

















