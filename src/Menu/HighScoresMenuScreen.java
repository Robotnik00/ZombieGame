/*	Zombie Game
*/

package Menu;

import Engine.IGameEngine;
import TextureEngine.ITexture;
// imports
import TextureEngine.ITextureEngine;
import Utility.BitmapFont;



public class HighScoresMenuScreen extends BaseMenuScreen
{
	public HighScoresMenuScreen()
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
		ITexture titleImage	= gfx_.LoadTexture("gfx/menu/highscores-title.png", 1);
		ITexture backImage = gfx_.LoadTexture("gfx/menu/back.png", 1);
		ITexture fontImage = gfx_.LoadTexture("gfx/font.png", 0x00FFFFFF);
		BitmapFont font = new BitmapFont();
		font.SetFont(fontImage);
		font.SetKerning(0.45f);
		
		//	[high scores]
		// (name)	(score) x10
		// 		[back]
		
		// title image
		ImageWidget title = new ImageWidget(titleImage, -0.5f, 0.3f, 1.0f, 1.0f);
		imageArea = title.GetAreaOnScreen();
		AddWidget(title);
		
		// 10 labels for high score name and the score
		// FIXME: magic number for # of high score entries
		for (int i=0; i < 10; i++)
		{
			StringConfigMenuWidget scoreName = new StringConfigMenuWidget(
					font, "score"+i+"_name", -0.5f, 0.2f-(i*0.05f), 0.05f, 0.05f);
			AddWidget(scoreName);
			
			IntConfigMenuWidget scorePoints = new IntConfigMenuWidget(
					font, "score"+i+"_points", 0.25f, 0.2f-(i*0.05f), 0.05f, 0.05f);
			AddWidget(scorePoints);
		}
		
		// temporary: text field
		TextWidget labelTextField = new TextWidget("Enter your name below:", font, -0.5f, -0.35f, 0.05f, 0.05f);
		AddWidget(labelTextField);
		TextFieldWidget textField = new TextFieldWidget(font, "score0_name", -0.5f, -0.4f, 0.05f, 0.05f);
		AddWidget(textField);
		
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