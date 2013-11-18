/*	ZombieGame
*/

package Menu;

// imports
import TextureEngine.ITextureEngine;
import TextureEngine.ITexture;
import Utility.BitmapFont;



public class PauseMenuScreen extends BaseMenuScreen
{
	public PauseMenuScreen()
	{
		super();
	}
	
	
	public void	Init(IMenuController menuController) throws Exception
	{
		super.Init(menuController);
		
		float[] imageArea;
		
		// get resources
		gfx_ = menuController_.GetGraphicsController();
		
		ITexture pauseTitle = gfx_.LoadTexture("gfx/menu/pause.png", 1);
	}
	
	
	
	//
	//
	//
	
	protected ITextureEngine gfx_;
}