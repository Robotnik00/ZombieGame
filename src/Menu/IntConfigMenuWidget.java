/*	Zombie Game
*/

package Menu;

// imports
import Engine.IGameEngine;
import Utility.ConfigData;
import Utility.BitmapFont;



/**
 * Display an integer variable from the config.
 */
public class IntConfigMenuWidget extends BaseMenuWidget
{
	public IntConfigMenuWidget(
			BitmapFont font, 
			String variable,
			float x, float y, 
			float xscale, float yscale)
	{
		variable_ = variable;
		font_ = font;
		x_ = x;
		y_ = y;
		xs_ = xscale;
		ys_ = yscale;
		lastValue_ = 0;
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
		lastValue_ = game_.GetGameConfig().GetIntValue(variable_);
	}
	
	public void	Draw(float delta)
	{
		font_.SetPosition(x_, y_);
		font_.SetScale(xs_, ys_);
		font_.DrawString(String.format("% 8d",lastValue_));
	}
	
	
	
	//
	// protected members
	//
	
	protected BitmapFont	font_;
	protected String		variable_;
	protected float			x_,y_,xs_,ys_;
	
	protected int			lastValue_;
}
