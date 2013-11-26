/*	Zombie Game
*/

package Menu;

import Engine.IGameEngine;
import GameStates.StartGame;
import TextureEngine.ITexture;
// imports
import GameStates.MenuState;
import TextureEngine.ITextureEngine;
import Utility.BitmapFont;



public class GameOverMenuScreen extends BaseMenuScreen
{
	public GameOverMenuScreen(int score)
	{
		super();
		score_ = score;
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
		game_ = menuController_.GetGameController();
		
		// determine if high score makes the list,
		// if so, add text field with variable crap,
		// if not, just display the score
		
		int scoreRank = game_.GetHighScores().CheckScorePlace(score_);
		
		// load menu graphics
		ITexture background		= gfx_.LoadTexture("gfx/Environment/game-over2.png", 1);
		ITexture backImage 		= gfx_.LoadTexture("gfx/menu/back.png", 0x00FFFFFF);
		
		ITexture fontImage = gfx_.LoadGrayscaleFont("gfx/font.png", true);
		BitmapFont font = new BitmapFont();
		font.SetFont(fontImage);
		font.SetKerning(0.45f);
		
		// [place] [score]
		// Enter your name:
		// hello
		// [quit]
		
		// background image (covers lines)
		ImageWidget back = new ImageWidget(background, -1.25f, -0.75f, 3.0f, 3.0f);
		AddWidget(back);
		
		// display score text
		TextWidget scoreDisplay = new TextWidget("Score: "+score_, font, -0.95f, 0.575f, 0.1f, 0.1f);
		AddWidget(scoreDisplay);
		
		// Enter your name text widget, focused by default
		if (scoreRank > -1)
		{
			// enter name text
			TextWidget nameText = new TextWidget(
				"Rank: "+(game_.GetHighScores().GetMaxScores()-scoreRank)+"\n\nEnter your name:",
				font,
				-0.95f, 0.5f,
				0.05f, 0.05f
				);
			
			AddWidget(nameText);
			
			// enter name widget
			
			// use clicks on this, they enter their name, and it will update the high score manager & config file
			NameEntryFieldWidget nameEntry = new NameEntryFieldWidget(
					font, game_.GetHighScores(), score_, -0.95f, 0.36f, 0.05f, 0.05f);
			AddWidget(nameEntry);
		}
		
		// back button
		ImageWidget backButton = new ImageWidget(backImage, -0.8f, -0.575f, 0.2f, 0.2f);
		imageArea = backButton.GetAreaOnScreen();
		
		MenuState m = new MenuState();
		ChangeGameStateWidgetAction menuState = new ChangeGameStateWidgetAction(m);
		menuState.SetArea(imageArea[0], imageArea[1], imageArea[2], imageArea[3]);
		backButton.AddAction(menuState);
		
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
	protected IGameEngine		game_;
	protected int score_;
}