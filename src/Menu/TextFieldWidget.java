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
public class TextFieldWidget extends BaseMenuWidget
{
	//
	// public methods
	//
	
	public TextFieldWidget(
			BitmapFont font, 
			String variable,
			float x, float y,
			float xscale, float yscale)
	{
		super();
		
		font_ = font;
		variable_ = variable;
		x_ = x;
		y_ = y;
		xs_ = xscale;
		ys_ = yscale;
		
		cursorCounter_ = 0;
		drawCursor_ = true;
		
		hasFocus_ = false;
		clickArea_ = new float[4];
		clickArea_[0] = 0.0f;
		clickArea_[1] = 0.0f;
		clickArea_[2] = 0.0f;
		clickArea_[3] = 0.0f;
		
		currentString_ = "";
		lastString_ = "";
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
		
		// make the cursor blink every second
		cursorCounter_++;
		if (cursorCounter_ > game_.GetTickFrequency()/2)
		{
			cursorCounter_ = 0;
			drawCursor_ = !drawCursor_;
		}
		
		// if the user has clicked on this widget, request focus and wait for a key press
		if (!hasFocus_)
		{
			// if not editing, get the value of this string
			currentString_ = game_.GetGameConfig().GetStringValue(variable_);
			
			CalculateClickArea(currentString_);
			
			// wait until clicked on
			if (CheckArea(clickArea_, game_.GetMouseX(), game_.GetMouseY()) 
			&&	CheckMouseButtonDown(game_.GetMouseEvents(), 1))
			{
				// request focus and wait for a keypress
				parentMenu_.RequestFocus(this);
				hasFocus_ = true;
				
				// remember previous string
				lastString_ = currentString_;
			}
		}
		else
		{
			// read in keypresses, add to string until a max length or enter or escape
			int[] keyPresses = game_.GetKeyEvents();
			char[] keyChars = game_.GetKeyCharacters();
			
			for (int i=0; i < keyPresses.length; i++)
			{
				// don't count key up events
				if (keyPresses[i] < 0)
					continue;
				
				if (keyPresses[i] == Keyboard.KEY_ESCAPE)
				{
					// cancel, use previous string,
					// release focus and reset.
					currentString_ = lastString_;
					
					parentMenu_.ReleaseFocus();
					hasFocus_ = false;
					
					return;
				}
				
				if (keyPresses[i] == Keyboard.KEY_RETURN)
				{
					// if the current string is empty, use the previous string 
					if (currentString_.length() == 0)
						currentString_ = lastString_;
					
					// release focus and reset.
					parentMenu_.ReleaseFocus();
					hasFocus_ = false;
					
					// update config value
					game_.GetGameConfig().SetStringValue(variable_, currentString_);
					
					// recalculate clickable area
					CalculateClickArea(currentString_);
					
					return;
				}
				
				if (keyPresses[i] == Keyboard.KEY_BACK)
				{
					// remove last character
					if (currentString_.length() > 0)
						currentString_ = currentString_.substring(0, currentString_.length()-1);
					
					continue;
				}
				
				// add to string if a valid character
				if (keyChars[i] >= 32 && keyChars[i] < 127)
					currentString_ += keyChars[i];
			}
		}
	}
	
	public void	Draw(float delta)
	{
		//super.Draw(delta);
		
		String finalString = currentString_;
		
		if (hasFocus_ && drawCursor_)
			finalString += '_';
		
		// draw int value as a key
		font_.SetPosition(x_, y_);
		font_.SetScale(xs_, ys_);
		font_.DrawString(finalString);
	}
	
	
	
	//
	// protected members
	//
	
	protected String			variable_;
	protected BitmapFont		font_;
	protected float				x_, y_, xs_, ys_;
	
	protected String			currentString_;
	protected String			lastString_;
	
	protected boolean			drawCursor_;
	protected int				cursorCounter_;
	
	protected boolean			hasFocus_;
	protected float[]			clickArea_;
	
	
	// calculates the clickable area of a string, does not consider newlines or tabs
	protected void	CalculateClickArea(String s)
	{
		clickArea_[0] = x_;
		clickArea_[1] = x_ + xs_ * (s.length()+1) * (1.0f - font_.GetKerning());
		clickArea_[2] = y_;
		clickArea_[3] = y_ + ys_;
	}
}