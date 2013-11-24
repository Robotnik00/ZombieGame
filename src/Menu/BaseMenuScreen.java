/*	Zombie game
*/

package Menu;

// imports
import java.util.*;

import org.lwjgl.input.Keyboard;

import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import TextureEngine.ITextureEngine;
import Menu.IMenuWidget;
import Menu.IMenuController;



public class BaseMenuScreen implements IMenuScreen
{
	//
	// public methods
	//
	
	public BaseMenuScreen()
	{
		widgets_ = new ArrayList<IMenuWidget>();
		focusedWidget_ = null;
		isInitialized_ = false;
	}
	
	
	
	//
	// IMenuScreen methods
	//
	
	public void	Init(IMenuController menuController) throws Exception
	{
		menuController_ = menuController;
		isInitialized_ = true;
		
		// create widgets here in child classes
	}
	
	public boolean IsInitialized()
	{
		return isInitialized_;
	}
	
	public void	Quit()
	{
		// shutdown all widgets
		for (int i=0; i < widgets_.size(); i++)
		{
			widgets_.get(i).Quit();
		}
	}
	
	public void	Pop()
	{
		//
	}
	
	public void	Push()
	{
		//
	}
	
	public void	Update()
	{
		if (focusedWidget_ != null)
		{
			focusedWidget_.Update();
		}
		else
		{
			// if esc is pressed, go to the previous menu.
			// check for this when there are no focused widgets,
			// and before we give the widgets a chance to focus (before updating)
			int[] keys = menuController_.GetGameController().GetKeyEvents();
			
			for (int i=0; i < keys.length; i++)
			{
				if (keys[i] == Keyboard.KEY_ESCAPE)
				{
					menuController_.PreviousMenu();
					return;	// don't update the widgets if we are going back
				}
			}
			
			for (int i=0; i < widgets_.size(); i++)
			{
				widgets_.get(i).Update();
			}
		}
	}
	
	public void	Draw(float delta)
	{
		for (int i=0; i < widgets_.size(); i++)
		{
			widgets_.get(i).Draw(delta);
		}
	}
	
	public void	RequestFocus(IMenuWidget widget)
	{
		focusedWidget_ = widget;
	}
	
	public void	ReleaseFocus()
	{
		focusedWidget_ = null;
	}
	
	public boolean IsFocused()
	{
		return (focusedWidget_ != null);
	}
	
	//
	// protected members
	//
	
	protected IMenuController			menuController_;
	protected ArrayList<IMenuWidget>	widgets_;
	protected IMenuWidget				focusedWidget_;
	protected boolean					isInitialized_;
	
	
	
	//
	// protected methods
	//
	
	/**
	 * Adds a widget to the widget list, and initializes it.
	 * @param widget
	 * @throws Exception
	 */
	protected void	AddWidget(IMenuWidget widget) throws Exception
	{
		widgets_.add(widget);
		widget.Init(menuController_, this);
	}
}