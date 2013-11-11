/* Zombie game
*/

package Menu;

// imports
import TextureEngine.ITexture;
import Utility.BitmapFont;



/**
 * Widget for a static image.
 */
public class TextWidget extends BaseMenuWidget
{
	//
	// public methods
	//
	
	public TextWidget(
			String text,
			BitmapFont font, 
			float x, float y,
			float xscale, float yscale)
	{
		super();
		text_ = text;
		font_ = font;
		x_ = x;
		y_ = y;
		xs_ = xscale;
		ys_ = yscale;
	}
	
	/**
	 * Change the text to display.
	 */
	public void	SetText(String text)
	{
		text_ = text;
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
		// nothing to save
		super.Quit();
	}
	
	public void	Update()
	{
		// update actions
		super.Update();
	}
	
	public void	Draw(float delta)
	{
		font_.SetPosition(x_, y_);
		font_.SetScale(xs_, ys_);
		font_.DrawString(text_);
	}
	
	
	
	//
	// protected members
	//
	
	protected String			text_;
	protected BitmapFont		font_;
	protected float				x_, y_, xs_, ys_;
}