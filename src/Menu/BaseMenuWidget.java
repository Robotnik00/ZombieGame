/*	Zombie Game
*/

package Menu;

// imports
import java.util.*;

import Engine.IGameEngine;
import TextureEngine.ITextureEngine;
import AudioEngine.IAudioEngine;



/**
 * Holds common widget behavior: 
 * - managing references to menu controllers
 * - adding and triggering actions 
 */
public class BaseMenuWidget implements IMenuWidget
{
	//
	// public methods
	//
	
	public BaseMenuWidget()
	{
		menuController_ = null;
		parentMenu_ = null;
		mousePrev_ = false;
		
		actions_ = new ArrayList<IWidgetAction>();
		
		game_ = null;
		sfx_ = null;
		gfx_ = null;
	}
	
	
	
	//
	// IMenuWidget interface methods
	//
	
	public void	Init(IMenuController menuController, IMenuScreen menu) throws Exception
	{
		menuController_ = menuController;
		parentMenu_ = menu;
		
		// get resource stuff
		game_ = menuController_.GetGameController();
		gfx_ = menuController_.GetGraphicsController();
		sfx_ = menuController_.GetAudioController();
		
		// initialize widget actions
		for (int i=0; i < actions_.size(); i++)
		{
			actions_.get(i).Init(menuController_, this);
		}
	}
	
	public void	Quit()
	{
		//
	}
	
	public void	Update()
	{
		IWidgetAction actn;
		float mx, my;
		boolean triggerNow=false;
		boolean triggerPrev=false;
		boolean mouseNow=false;
		
		mx = game_.GetMouseX();
		my = game_.GetMouseY();
		
		// dispatch action calls
		for (int i=0; i < actions_.size(); i++)
		{
			actn = actions_.get(i);
			
			// we remember the previous mouse coords, not the previous result,
			// because results vary on the number and location of actions.
			// these are cheap enough to re-calculate.
			triggerNow = CheckActionArea(actn, mx, my);
			triggerPrev = CheckActionArea(actn, xprev_, yprev_);
			
			if (triggerPrev == false && triggerNow == true)
			{
				// mouse entered the action's area
				actn.OnHover(true);
			}
			else if (triggerPrev == true && triggerNow == false)
			{
				// mouse left the area
				actn.OnHover(false);
			}
			
			// check if clicking too, any button
			mouseNow = CheckMouseButtonDown(game_.GetMouseEvents());
			if (triggerNow == true && mousePrev_ == false && mouseNow == true)
				actn.OnClick();
		}
		
		xprev_ = mx;
		yprev_ = my;
		mousePrev_ = mouseNow;
	}
	
	public void	Draw(float delta)
	{
		//
	}
	
	public void	AddAction(IWidgetAction action)
	{
		actions_.add(action);
	}
	
	
	
	//
	// protected members
	//
	
	protected IMenuController			menuController_;
	protected IMenuScreen				parentMenu_;
	protected IGameEngine				game_;
	protected ITextureEngine			gfx_;
	protected IAudioEngine				sfx_;
	
	protected ArrayList<IWidgetAction>	actions_;
	
	protected float						xprev_,yprev_;
	protected boolean					mousePrev_;
	
	
	
	//
	// protected methods
	//
	
	// FIXME: this can be static, but scope issues
	protected boolean	CheckActionArea(IWidgetAction a, float x, float y)
	{
		float[] area = a.GetArea();
		return (x >= area[0] && x < area[1] && y >= area[2] && y < area[3]);
	}
	
	// returns true if there are any mouse button down events
	protected boolean	CheckMouseButtonDown(int[] mouseEvents)
	{
		boolean ret = false;
		
		for (int i=0; i < mouseEvents.length; i++)
		{
			if (mouseEvents[i] > 0)
				ret = true;
		}
		
		return ret;
	}
	
}
