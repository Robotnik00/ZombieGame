/*	Zombie Game
*/

package Menu;

import Engine.IGameEngine;
import TextureEngine.ITexture;
// imports
import TextureEngine.ITextureEngine;
import Utility.BitmapFont;



public class OptionsMenuScreen extends BaseMenuScreen
{
	public OptionsMenuScreen()
	{
		super();
	}
	
	
	
	//
	// IMenuScreen interface methods
	//
	
	public void	Init(IMenuController menuController) throws Exception
	{
		super.Init(menuController);
		
		float[] imageArea;
		
		// get resources
		gfx_ = menuController_.GetGraphicsController();
		
		// load menu graphics
		ITexture titleImage		= gfx_.LoadTexture("gfx/menu/options-title.png", 1);
		ITexture backImage		= gfx_.LoadTexture("gfx/menu/back.png", 1);
		ITexture incVolume		= gfx_.LoadTexture("gfx/menu/rightarrow.png", 0);
		ITexture decVolume		= gfx_.LoadTexture("gfx/menu/leftarrow.png", 0);
		
		ITexture fontImage = gfx_.LoadTexture("font1.png", 0);
		BitmapFont font = new BitmapFont();
		font.SetFont(fontImage);
		font.SetKerning(0.45f);
		
		//	[options]
		// (controls)
		// (sound volume)
		// (video settings?)
		
		// title image
		ImageWidget title = new ImageWidget(titleImage, -0.5f, 0.3f, 1.0f, 1.0f);
		imageArea = title.GetAreaOnScreen();
		AddWidget(title);
		
		// controls
		TextWidget labelControls = new TextWidget(
				"Controls: (click key to set)\nMove Up\nMove Down\nMove Left\nMove Right\nAction", 
				font, -0.5f, 0.15f, 0.05f, 0.05f);
		AddWidget(labelControls);
		
		// control settings
		KeyPressMenuWidget keyUpButton = new KeyPressMenuWidget(
				font, "move_up",	0.3f, 0.1f, 0.05f, 0.05f);
		AddWidget(keyUpButton);
		
		KeyPressMenuWidget keyDownButton = new KeyPressMenuWidget(
				font, "move_down", 	0.3f, 0.05f, 0.05f, 0.05f);
		AddWidget(keyDownButton);
		
		KeyPressMenuWidget keyLeftButton = new KeyPressMenuWidget(
				font, "move_left", 	0.3f, 0.0f, 0.05f, 0.05f);
		AddWidget(keyLeftButton);
		
		KeyPressMenuWidget keyRightButton = new KeyPressMenuWidget(
				font, "move_right", 0.3f, -0.05f, 0.05f, 0.05f);
		AddWidget(keyRightButton);
		
		KeyPressMenuWidget keyAction = new KeyPressMenuWidget(
				font, "action", 	0.3f, -0.1f, 0.05f, 0.05f);
		AddWidget(keyAction);
		
		// sound volume tag
		TextWidget soundVolumeLabel = new TextWidget("Sound:\nVolume", font, -0.5f, -0.25f, 0.05f, 0.05f);
		AddWidget(soundVolumeLabel);
		
		// sound volume display
		FloatConfigMenuWidget volumeDisplay = new FloatConfigMenuWidget(
				font, "sound_volume", 100.0f, "%.0f",
				0.3f, -0.3f, 0.05f, 0.05f);
		AddWidget(volumeDisplay);
		
		// Sound volume adjuster images
		ImageWidget incVolumeButton = new ImageWidget(incVolume, 0.4f, -0.3f, 0.05f, 0.05f);
		imageArea = incVolumeButton.GetAreaOnScreen();
		AdjustFloatConfigWidgetAction incVolumeControl = 
				new AdjustFloatConfigWidgetAction("sound_volume", 0.1f, 1.0f, 0.0f);
		incVolumeControl.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		incVolumeButton.AddAction(incVolumeControl);
		AddWidget(incVolumeButton);
		
		ImageWidget decVolumeButton = new ImageWidget(decVolume, 0.25f, -0.3f, 0.05f, 0.05f);
		imageArea = decVolumeButton.GetAreaOnScreen();
		AdjustFloatConfigWidgetAction decVolumeControl = 
				new AdjustFloatConfigWidgetAction("sound_volume", -0.1f, 1.0f, 0.0f);
		decVolumeControl.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		decVolumeButton.AddAction(decVolumeControl);
		AddWidget(decVolumeButton);
		
		// back button
		ImageWidget backButton = new ImageWidget(backImage, -0.10f, -0.5f, 0.2f, 0.2f);
		imageArea = backButton.GetAreaOnScreen();
		
		PreviousMenuWidgetAction prevAction = new PreviousMenuWidgetAction();
		prevAction.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		backButton.AddAction(prevAction);
		
		AddWidget(backButton);
	}
	
	public void	Draw(float delta)
	{
		// draw a background
		
		
		// then draw the widgets on top
		super.Draw(delta);
	}
	
	
	
	//
	//
	//
	
	protected ITextureEngine	gfx_;
}