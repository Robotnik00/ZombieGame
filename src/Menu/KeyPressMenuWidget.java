/* Zombie game
*/

package Menu;

// imports
import org.lwjgl.input.Keyboard;

import TextureEngine.ITexture;
import Utility.BitmapFont;



/**
 * This widget waits for a key press and records that value in an int config variable.
 */
public class KeyPressMenuWidget extends IntConfigMenuWidget
{
	//
	// public methods
	//
	
	public KeyPressMenuWidget(
			BitmapFont font, 
			String variable,
			float x, float y,
			float xscale, float yscale)
	{
		super(font, variable, x, y, xscale, yscale);
		
		hasFocus_ = false;
		clickArea_ = new float[4];
		clickArea_[0] = 0.0f;
		clickArea_[1] = 0.0f;
		clickArea_[2] = 0.0f;
		clickArea_[3] = 0.0f;
	}
	
	
	
	//
	// IMenuWidget interface methods
	//
	
	public void	Init(IMenuController menuController, IMenuScreen menu) throws Exception
	{
		super.Init(menuController,  menu);
	}
	
	public void	Quit()
	{
		super.Quit();
	}
	
	public void	Update()
	{
		super.Update();
		
		CalculateClickArea(Keyboard.getKeyName(lastValue_));
		
		// if the user has clicked on this widget, request focus and wait for a key press
		if (!hasFocus_)
		{
			// wait until clicked on
			if (CheckArea(clickArea_, game_.GetMouseX(), game_.GetMouseY()) 
			&&	CheckMouseButtonDown(game_.GetMouseEvents(), 1))
			{
				// request focus and wait for a keypress
				parentMenu_.RequestFocus(this);
				hasFocus_ = true;
			}
		}
		else
		{
			// wait for a keypress 
			int[] keyPresses = game_.GetKeyEvents();
			
			if (keyPresses.length > 0)
			{
				// release focus
				parentMenu_.ReleaseFocus();
				hasFocus_ = false;
				
				// get the first key value
				game_.GetGameConfig().SetIntValue(variable_, keyPresses[0]);
				lastValue_ = keyPresses[0];
				
				// recalculate clickable area
				CalculateClickArea(Keyboard.getKeyName(lastValue_));
			}
		}
	}
	
	public void	Draw(float delta)
	{
		//super.Draw(delta);
		
		if (hasFocus_)
		{
			// "Press a key"
			font_.SetPosition(x_, y_);
			font_.SetScale(xs_, ys_);
			font_.DrawString("Press a Key");
		}
		else
		{
			// draw int value as a key
			font_.SetPosition(x_, y_);
			font_.SetScale(xs_, ys_);
			font_.DrawString(Keyboard.getKeyName(lastValue_));
		}
	}
	
	
	
	//
	// protected members
	//
	
	protected boolean			hasFocus_;
	protected float[]			clickArea_;
	
	
	// calculates the clickable area of a string, does not consider newlines or tabs
	protected void	CalculateClickArea(String s)
	{
		clickArea_[0] = x_;
		clickArea_[1] = x_ + xs_ * s.length() * (1.0f - font_.GetKerning());
		clickArea_[2] = y_;
		clickArea_[3] = y_ + ys_;
	}
}