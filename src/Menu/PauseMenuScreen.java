/*	ZombieGame
*/

package Menu;

// imports
import GameStates.StartGame;
import GameStates.MenuState;
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
		
		ITexture pauseTitleImg	= gfx_.LoadTexture("gfx/menu/pause-title.png", 1);
		ITexture continueImg	= gfx_.LoadTexture("gfx/menu/continue-border.png", 1);
		ITexture optionsImg		= gfx_.LoadTexture("gfx/menu/options-border.png", 1);
		ITexture quitImg		= gfx_.LoadTexture("gfx/menu/quit-border.png", 1);
		
		ImageWidget pauseTitle = new ImageWidget(pauseTitleImg, -0.5f, 0.3f, 1.0f, 1.0f);
		AddWidget(pauseTitle);
		
		// continue button
		ImageWidget continueButton = new ImageWidget(continueImg, -0.2f, 0.0f, 0.4f, 0.4f);
		imageArea = continueButton.GetAreaOnScreen();
		
		PreviousMenuWidgetAction prevAction = new PreviousMenuWidgetAction();
		prevAction.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		continueButton.AddAction(prevAction);
		
		AddWidget(continueButton);
		
		// options button
		ImageWidget optionsButton = new ImageWidget(optionsImg, -0.2f, -0.25f, 0.4f, 0.4f);
		imageArea = optionsButton.GetAreaOnScreen();
		
		ChangeMenuWidgetAction changeOptionsMenu = new ChangeMenuWidgetAction(new OptionsMenuScreen());
		changeOptionsMenu.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		optionsButton.AddAction(changeOptionsMenu);
		
		AddWidget(optionsButton);
		
		// quit button
		ImageWidget quitButton = new ImageWidget(quitImg, -0.2f, -0.5f, 0.4f, 0.4f);
		imageArea = quitButton.GetAreaOnScreen();
		
		ChangeGameStateWidgetAction quitGame = new ChangeGameStateWidgetAction(new MenuState());
		quitGame.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		quitButton.AddAction(quitGame);
		
		AddWidget(quitButton);
	}
	
	
	
	//
	//
	//
	
	protected ITextureEngine gfx_;
}