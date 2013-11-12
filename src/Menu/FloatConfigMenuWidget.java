/*	Zombie Game
*/

package Menu;

// imports
import Engine.IGameEngine;
import Utility.ConfigData;
import Utility.BitmapFont;



/**
 * Display a float variable from the config.
 */
public class FloatConfigMenuWidget extends BaseMenuWidget
{
	public FloatConfigMenuWidget(
			BitmapFont font, 
			String variable, float valueScale, String displayFormat,
			float x, float y, 
			float xscale, float yscale)
	{
		variable_ = variable;
		font_ = font;
		valueScale_ = valueScale;
		displayFormat_ = displayFormat;
		x_ = x;
		y_ = y;
		xs_ = xscale;
		ys_ = yscale;
		lastValue_ = 0.0f;
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
		
		// get and store the variable's value
		lastValue_ = game_.GetGameConfig().GetFloatValue(variable_);
	}
	
	public void	Draw(float delta)
	{
		font_.SetPosition(x_, y_);
		font_.SetScale(xs_, ys_);
		font_.DrawString(String.format(displayFormat_, lastValue_*valueScale_));
	}
	
	
	
	//
	// protected members
	//
	
	protected BitmapFont	font_;
	protected String		variable_;
	protected float			valueScale_;
	protected String		displayFormat_;
	protected float			x_,y_,xs_,ys_;
	
	protected float			lastValue_;
}
