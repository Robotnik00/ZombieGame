/*	Zombie Game
*/

package Menu;

import Engine.IGameEngine;
import TextureEngine.ITexture;
// imports
import TextureEngine.ITextureEngine;
import Utility.BitmapFont;



public class Tutorial2MenuScreen extends BaseMenuScreen
{
	public Tutorial2MenuScreen()
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
		ITexture background		= gfx_.LoadTexture("gfx/menu/menu-background4-center.png", 0x00202020);
		ITexture titleImage		= gfx_.LoadTexture("gfx/menu/tutorial-title.png", 1);
		ITexture backImage 		= gfx_.LoadTexture("gfx/menu/back.png", 1);
		ITexture nextImage 		= gfx_.LoadTexture("gfx/menu/next.png", 1);
		
		ITexture wpnShotgun		= gfx_.LoadTexture("gfx/powerups/shotgun_shell.png", 1);
		ITexture wpnMachine		= gfx_.LoadTexture("gfx/powerups/machinegun_shells.png", 1);
		ITexture wpnFlame		= gfx_.LoadTexture("gfx/powerups/flamethrower_can.png", 1);
		
		ITexture fontImage = gfx_.LoadGrayscaleFont("gfx/font.png", true);
		BitmapFont font = new BitmapFont();
		font.SetFont(fontImage);
		font.SetKerning(0.45f);
		
		//		[tutorial]
		//   (text)		(sticky note)
		// 		[back]
		
		// background image (covers lines)
		ImageWidget back = new ImageWidget(background, -1.0f, -1.0f, 2.0f, 2.0f);
		AddWidget(back);
		
		// title image
		ImageWidget title = new ImageWidget(titleImage, -0.5f, 0.3f, 1.0f, 1.0f);
		imageArea = title.GetAreaOnScreen();
		AddWidget(title);
		
		//ImageWidget stickyNote = new ImageWidget(sticky, 0.3f, 0.0f, 1.0f, 1.0f);
		//AddWidget(stickyNote);
		
		TextWidget tutorialText = new TextWidget(
			"Many different weapons can be found\n" +
			"during the game. You'll need them!\n" +
			"\n" +
			"Weapons replace your starting rifle\n" +
			"and have only limited ammo. Use the \n" +
			"Action button (default = E) to switch\n" +
			"between weapons.\n" +
			"\n\n\n\n\n\n" +
			"Shotgun      Machine Gun    Flamethrower\n" +
			"",
			font,
			-0.5f, 0.2f,
			0.045f, 0.045f
			);
		AddWidget(tutorialText);
		
		// weapon powerup icons
		float row=-0.33f;
		ImageWidget imgShotgun = new ImageWidget(wpnShotgun, -0.5f, row, 0.2f, 0.2f);
		AddWidget(imgShotgun);
		ImageWidget imgMachine = new ImageWidget(wpnMachine, -0.125f, row, 0.2f, 0.2f);
		AddWidget(imgMachine);
		ImageWidget imgFlame = new ImageWidget(wpnFlame, 0.23f, row, 0.2f, 0.2f);
		AddWidget(imgFlame);
		
		// back button
		ImageWidget backButton = new ImageWidget(backImage, -0.30f, -0.55f, 0.2f, 0.2f);
		imageArea = backButton.GetAreaOnScreen();
		
		PreviousMenuWidgetAction prevAction = new PreviousMenuWidgetAction();
		prevAction.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		backButton.AddAction(prevAction);
		
		AddWidget(backButton);
		
		// next button
		ImageWidget nextButton = new ImageWidget(nextImage, 0.10f, -0.55f, 0.2f, 0.2f);
		imageArea = nextButton.GetAreaOnScreen();
		
		ChangeMenuWidgetAction nextAction = new ChangeMenuWidgetAction(new Tutorial3MenuScreen());
		nextAction.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		nextButton.AddAction(nextAction);
		
		AddWidget(nextButton);
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