/*	Zombie game
*/

package Menu;

// imports
import java.util.*;

import AudioEngine.IAudioEngine;
import Engine.IGameEngine;

import GameStates.StartGame;

import TextureEngine.ITextureEngine;
import TextureEngine.ITexture;

import Menu.IMenuWidget;
import Menu.IMenuController;

import Utility.BitmapFont;



public class MainMenuScreen extends BaseMenuScreen
{
	//
	// public methods
	//
	
	public MainMenuScreen()
	{
		super();
	}
	
	
	
	//
	// IMenuScreen methods
	//
	
	public void	Init(IMenuController menuController) throws Exception
	{
		super.Init(menuController);
		
		IGameEngine game = menuController_.GetGameController();
		game.LogMessage("MainMenuScreen::Init");
		
		// get some resources
		gfx_ = menuController_.GetGraphicsController();
		
		// load some images
		ITexture titleImage		= gfx_.LoadTexture("gfx/menu/game-title.png", 	0x00FFFFFF);
		ITexture newgameImage	= gfx_.LoadTexture("gfx/menu/newgame.png", 		0x00FFFFFF);
		ITexture optionsImage	= gfx_.LoadTexture("gfx/menu/options.png", 		0x00FFFFFF);
		ITexture scoresImage	= gfx_.LoadTexture("gfx/menu/highscores.png", 	0x00FFFFFF);
		ITexture quitImage		= gfx_.LoadTexture("gfx/menu/quitgame.png", 	0x00FFFFFF);
		
		ITexture textFont = gfx_.LoadTexture("gfx/font.png", 0x00FFFFFF);
		BitmapFont font = new BitmapFont();
		font.SetFont(textFont);
		font.SetKerning(0.45f);
		
		//		[Zombie Game]
		// [start game]		[tutorial]
		// [high scores]	[options]
		// [credits]		[quit]
		// tooltip at bottom
		
		float row1 = -0.01f;
		float row2 = -0.25f;
		float row3 = -0.5f;
		float col1 = -0.5f;
		float col2 = 0.1f;
		
		float scale=0.4f;
		float[] imageArea = null;
		
		// "tooltip" widget at the bottom, other widget actions will change the text here
		// to provide a description of the button the mouse is hovering over.
		TextWidget tooltip = new TextWidget("", font, -0.55f, -0.575f, 0.05f, 0.05f);
		AddWidget(tooltip);
				
		// title image
		ImageWidget title = new ImageWidget(titleImage, -0.5f, 0.3f, 1.0f, 1.0f);
		imageArea = title.GetAreaOnScreen();
		/*HoverTextWidgetAction titleDesc = new HoverTextWidgetAction(tooltip, 
				"Coming Soon: Zombie Game 3: The search for Zombie Game 2.",
				"Hover the mouse over a button to see a description!");
		titleDesc.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		title.AddAction(titleDesc);*/
		AddWidget(title);
		
		// newgame
		ImageWidget newGameButton = new ImageWidget(newgameImage, col1, row1, scale, scale);
		imageArea = newGameButton.GetAreaOnScreen();
		
		HoverTextWidgetAction newGameDesc = new HoverTextWidgetAction(tooltip, 
				"The fight starts here!", "");
		newGameDesc.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		newGameButton.AddAction(newGameDesc);
		
		ChangeGameStateWidgetAction startGame = new ChangeGameStateWidgetAction(new StartGame());
		startGame.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		newGameButton.AddAction(startGame);
		
		AddWidget(newGameButton);
		
		// tutorial
		//
		
		// options
		ImageWidget optionsButton = new ImageWidget(optionsImage, col2, row2, scale, scale);
		imageArea = optionsButton.GetAreaOnScreen();
		
		HoverTextWidgetAction optionsDesc = new HoverTextWidgetAction(tooltip, 
				"Configure controls and other settings.",
				"");
		optionsDesc.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		optionsButton.AddAction(optionsDesc);
		
		ChangeMenuWidgetAction changeOptionsMenu = new ChangeMenuWidgetAction(new OptionsMenuScreen());
		changeOptionsMenu.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		optionsButton.AddAction(changeOptionsMenu);
		
		AddWidget(optionsButton);
		
		// high scores
		ImageWidget scoresButton = new ImageWidget(scoresImage, col1, row2, scale, scale);
		imageArea = scoresButton.GetAreaOnScreen();
		
		HoverTextWidgetAction scoresDesc = new HoverTextWidgetAction(tooltip, 
				"View the top 10 high scores!",
				"");
		scoresDesc.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		scoresButton.AddAction(scoresDesc);
		
		ChangeMenuWidgetAction changeHighScoresMenu = new ChangeMenuWidgetAction(new HighScoresMenuScreen());
		changeHighScoresMenu.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		scoresButton.AddAction(changeHighScoresMenu);
				
		AddWidget(scoresButton);
		
		// credits
		//
		
		// quit game
		ImageWidget quitButton = new ImageWidget(quitImage, col2, row3, scale, scale);
		imageArea = quitButton.GetAreaOnScreen();
		
		HoverTextWidgetAction quitDesc = new HoverTextWidgetAction(tooltip, 
				"Go ahead and leave. See if I care.",
				"");
		quitDesc.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		quitButton.AddAction(quitDesc);
		
		QuitGameWidgetAction quitAction = new QuitGameWidgetAction();
		quitAction.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		quitButton.AddAction(quitAction);
		
		AddWidget(quitButton);
	}
	
	public void	Draw(float delta)
	{
		// draw a background
		
		
		// then draw the widgets on top
		super.Draw(delta);
	}
	
	//
	// protected members
	//
	
	protected ITextureEngine	gfx_;
	
}