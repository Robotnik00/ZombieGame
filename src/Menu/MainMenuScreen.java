/*	Zombie game
*/

package Menu;

// imports
import java.util.*;

import AudioEngine.IAudioEngine;
import Engine.IGameEngine;

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
		
		// get some resources
		gfx_ = menuController.GetGraphicsController();
		
		// load some images
		ITexture titleImage		= gfx_.LoadTexture("gfx/menu/game-title.png", 1);
		ITexture newgameImage	= gfx_.LoadTexture("gfx/menu/newgame.png", 1);
		ITexture optionsImage	= gfx_.LoadTexture("gfx/menu/options.png", 1);
		ITexture scoresImage	= gfx_.LoadTexture("gfx/menu/highscores.png", 1);
		ITexture quitImage		= gfx_.LoadTexture("gfx/menu/quitgame.png", 1);
		
		ITexture textFont = gfx_.LoadTexture("font1.png", 0);
		BitmapFont font = new BitmapFont();
		font.SetFont(textFont);
		font.SetKerning(0.45f);
		
		//		[Zombie Game]
		// [start game]		[options]
		// [high scores]	[quit]
		// tooltip at bottom
		
		float scale=0.5f;
		float[] imageArea = null;
		
		// "tooltip" widget at the bottom, other widget actions will change the text here
		// to provide a description of the button the mouse is hovering over.
		TextWidget tooltip = new TextWidget(
				"Hover the mouse over a button for a description!",
				font, -1.0f, -0.75f, 0.05f, 0.05f);
		AddWidget(tooltip);
		
		// text at the top
		AddWidget(new TextWidget("These are placeholder graphics! Fix them soon!", font, -1.0f, 0.7f, 0.05f, 0.05f));
				
		// title image
		ImageWidget title = new ImageWidget(titleImage, -0.5f, 0.3f, 1.0f, 1.0f);
		imageArea = title.GetAreaOnScreen();
		HoverTextWidgetAction titleDesc = new HoverTextWidgetAction(tooltip, 
				"Coming Soon: Zombie Game 3: The search for Zombie Game 2.",
				"Hover the mouse over a button to see a description!");
		titleDesc.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		title.AddAction(titleDesc);
		AddWidget(title);
		
		// newgame
		ImageWidget newGameButton = new ImageWidget(newgameImage, -0.75f, -0.1f, scale, scale);
		imageArea = newGameButton.GetAreaOnScreen();
		HoverTextWidgetAction newGameDesc = new HoverTextWidgetAction(tooltip, 
				"The fight begins here! (eventually!)",
				"Hover the mouse over a button to see a description!");
		newGameDesc.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		newGameButton.AddAction(newGameDesc);
		AddWidget(newGameButton);
		
		// options
		ImageWidget optionsButton = new ImageWidget(optionsImage, 0.25f, -0.1f, scale, scale);
		imageArea = optionsButton.GetAreaOnScreen();
		HoverTextWidgetAction optionsDesc = new HoverTextWidgetAction(tooltip, 
				"Configure game controls and audio/video settings.",
				"Hover the mouse over a button to see a description!");
		optionsDesc.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		optionsButton.AddAction(optionsDesc);
		AddWidget(optionsButton);
		
		// high scores
		ImageWidget scoresButton = new ImageWidget(scoresImage, -0.75f, -0.5f, scale, scale);
		imageArea = scoresButton.GetAreaOnScreen();
		HoverTextWidgetAction scoresDesc = new HoverTextWidgetAction(tooltip, 
				"View the Top 10 high scores!",
				"Hover the mouse over a button to see a description!");
		scoresDesc.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		scoresButton.AddAction(scoresDesc);
		AddWidget(scoresButton);
		
		// quit game
		ImageWidget quitButton = new ImageWidget(quitImage, 0.25f, -0.5f, scale, scale);
		imageArea = quitButton.GetAreaOnScreen();
		
		HoverTextWidgetAction quitDesc = new HoverTextWidgetAction(tooltip, 
				"Go ahead and leave. See if I care.",
				"Hover the mouse over a button to see a description!");
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