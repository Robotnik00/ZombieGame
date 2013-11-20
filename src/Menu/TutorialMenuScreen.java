/*	Zombie Game
*/

package Menu;

import Engine.IGameEngine;
import TextureEngine.ITexture;
// imports
import TextureEngine.ITextureEngine;
import Utility.BitmapFont;



public class TutorialMenuScreen extends BaseMenuScreen
{
	public TutorialMenuScreen()
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
		ITexture sticky			= gfx_.LoadTexture("gfx/tutorial/tutorial_all.png", 0x00FFFFFF);
		ITexture titleImage		= gfx_.LoadTexture("gfx/menu/tutorial-title.png", 1);
		ITexture backImage 		= gfx_.LoadTexture("gfx/menu/back.png", 1);
		ITexture nextImage 		= gfx_.LoadTexture("gfx/menu/next.png", 1);
		
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
		
		ImageWidget stickyNote = new ImageWidget(sticky, 0.3f, 0.0f, 1.0f, 1.0f);
		AddWidget(stickyNote);
		
		TextWidget tutorialText = new TextWidget(
			"Welcome to Zombie Game,\n" +
			"the interactive drawing!\n" +
			"\n" +
			"The goal is to survive as long as \n" +
			"you can against an endless horde \n" +
			"of zombies! \n" + 
			"\n" +
			"The basic controls are listed on the \n" +
			"sticky note (keyboard buttons can be \n" +
			"reconfigured in the options menu). \n" +
			"\n" +
			"Use the movement keys to move around,\n" +
			"aim with the mouse, and shoot with the\n" +
			"left mouse button. You can continuously\n" +
			"shoot by holding down the left button.\n",
			font,
			-0.5f, 0.2f,
			0.045f, 0.045f
			);
		AddWidget(tutorialText);
		
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
		
		ChangeMenuWidgetAction nextAction = new ChangeMenuWidgetAction(new Tutorial2MenuScreen());
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