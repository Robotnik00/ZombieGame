/*	Zombie Game
*/

package Menu;

import Engine.IGameEngine;
import TextureEngine.ITexture;
// imports
import TextureEngine.ITextureEngine;
import Utility.BitmapFont;



public class Tutorial3MenuScreen extends BaseMenuScreen
{
	public Tutorial3MenuScreen()
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
		
		ITexture wpnHealth			= gfx_.LoadTexture("gfx/Powerups/RestoreHealth.png", 1);
		ITexture wpnInvulnerable	= gfx_.LoadTexture("gfx/Powerups/invulnerability.png", 1);
		ITexture wpnQuad			= gfx_.LoadTexture("gfx/Powerups/quad_damage.png", 1);
		
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
			"Powerups can greatly enhance your arsenal!\n" +
			"\n" +
			"Health restore acts instantly and\n" +
			"resets your health to 100%!\n" +
			"\n" +
			"Other powerups last for 10 seconds and\n" +
			"only one of them can be active at a time.\n" +
			"\n\n\n\n\n\n" +
			"Health     Invulnerability    Quad\n" +
			"Restore                       Damage",
			font,
			-0.5f, 0.2f,
			0.045f, 0.045f
			);
		AddWidget(tutorialText);
		
		// weapon powerup icons
		float row=-0.33f;
		ImageWidget imgShotgun = new ImageWidget(wpnHealth, -0.5f, row, 0.2f, 0.2f);
		AddWidget(imgShotgun);
		ImageWidget imgMachine = new ImageWidget(wpnInvulnerable, -0.125f, row, 0.2f, 0.2f);
		AddWidget(imgMachine);
		ImageWidget imgFlame = new ImageWidget(wpnQuad, 0.23f, row, 0.2f, 0.2f);
		AddWidget(imgFlame);
		
		// back button
		ImageWidget backButton = new ImageWidget(backImage, -0.30f, -0.55f, 0.2f, 0.2f);
		imageArea = backButton.GetAreaOnScreen();
		
		PreviousMenuWidgetAction prevAction = new PreviousMenuWidgetAction();
		prevAction.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		backButton.AddAction(prevAction);
		
		AddWidget(backButton);
		
		// next button
		//ImageWidget nextButton = new ImageWidget(nextImage, 0.10f, -0.55f, 0.2f, 0.2f);
		//imageArea = nextButton.GetAreaOnScreen();
		
		//ChangeMenuWidgetAction nextAction = new ChangeMenuWidgetAction(new Tutorial2MenuScreen());
		//nextAction.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		//nextButton.AddAction(nextAction);
		
		//AddWidget(nextButton);
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