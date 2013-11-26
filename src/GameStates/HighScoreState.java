/*	ZombieGame
*/

package GameStates;

import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import Menu.IMenuScreen;
import TextureEngine.ITextureEngine;

// imports
import Menu.IMenuController;
import Menu.IMenuScreen;
import Menu.GameOverMenuScreen;


public class HighScoreState implements IGameState, IMenuController
{
	public HighScoreState(int score)
	{
		score_ = score;
	}
	
	/** 
	 * Initialize the gamestate and pass references to game resource managers.
	 * Exception should be thrown on failure.
	 * 
	 * @param gfx ITextureEngine
	 * @param snd IAudioEngine
	 * @param game IGameEngine
	 */
	public void	Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game) throws Exception
	{
		gfx_ = gfx;
		snd_ = snd;
		game_ = game;
		
		// setup one screen, high score enter screen
		currentMenu_ = new GameOverMenuScreen(score_);
		currentMenu_.Init(this);
	}
	
	/**
	 * Called when the gamestate is destroyed, or when the program exits.
	 */
	public void Quit()
	{
		//
	}
	
	/** 
	 * Signal the gamestate to update the game logic.
	 * Called at a constant rate by the GameEngine.
	 */
	public void	Update()
	{
		currentMenu_.Update();
	}
	
	/** 
	 * Signal the gamestate to update the screen.
	 * @param delta interpolation value in between frames, ranging from [0.0,1.0)
	 */
	public void	Draw(float delta)
	{
		currentMenu_.Draw(delta);
	}
	
	/**
	 * Return if the state is paused
	 * FIXME: this is a hack
	 */
	public boolean GetPaused()
	{
		return false;
	}
	
	
	
	//
	// IMenuController
	//
	
	public void	ChangeMenuScreen(IMenuScreen menu) throws Exception
	{
		// nope
	}
	
	public void	PreviousMenu()
	{
		// save score, change to main menu state with high score screen.
		
	}
	
	public IGameEngine		GetGameController()
	{
		return game_;
	}
	
	public ITextureEngine	GetGraphicsController()
	{
		return gfx_;
	}
	
	public IAudioEngine		GetAudioController()
	{
		return snd_;
	}
	
	
	//
	//
	//
	
	protected IAudioEngine snd_;
	protected ITextureEngine gfx_;
	protected IGameEngine game_;
	
	protected int score_;
	protected IMenuScreen currentMenu_;
}