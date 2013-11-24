/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	IGameEngine.java
 * 		Interface to main game controller object.	
 */

package Engine;

// imports
import GameStates.IGameState;
import InputCallbacks.KeyEventListener;
import InputCallbacks.MouseEventListener;
import Utility.ConfigData;
import Utility.HighScoresManager;



/**
 * 	Interface to the gamestate controller object.
 * 
 *	These are the functions that each gamestate is allowed to use to control the overall program.
 */

public interface IGameEngine
{
	//
	// gamestates, program flow
	//
	
	/**	
	 * Changes the current gamestate.
	 * Exception thrown on failure.
	 * 
	 * @param state	IGameState 
	 */
	public void	ChangeGameState(IGameState state) throws Exception;
	
	/**
	 * Ends the game loop and cause the program to shutdown.
	 * The Quit method of the current game state will be called.
	 */
	public void	EndGameLoop();
	
	/**
	 * Request the handle to the game's configuration file.
	 */
	public ConfigData	GetGameConfig();
	
	/**
	 * Request the handle to the game's high score list.
	 */
	public HighScoresManager GetHighScores();
	
	
	//
	// information
	//
	
	/**
	 * @return the number of gamestate update calls performed in one second.
	 */
	public int	GetTickFrequency();
	
	/**
	 * @return the number of frames drawn during the last second.
	 */
	public int	GetFrameRate();
	
	/** 
	 * Check for a command line argument.
	 * @param arg String argument.
	 * @return Index of argument in the program's arguments array, or -1 if not found.
	 */
	public int	CheckArguments(String arg);
	
	/** 
	 * @return array of command line arguments.
	 */
	public String[]	GetArguments();
	
	/** 
	 * Logs a text message to the standard output stream, with an added timestamp.
	 * @param message Text string to record.
	 */
	public void	LogMessage(String message);
	
	/**
	 * Set the window's title bar text.
	 * @param str Text string.
	 */
	public void	SetWindowTitle(String str);
	
	
	//
	// Time
	//
	
	/** 
	 * @return time elapsed since the program started in milliseconds. Use for performance metrics only!
	 */
	public long	GetTime();
	
	//
	// Input
	//
	
	/**
	 * Poll mouse x position, scaled by the current drawing perspective.
	 * @return scaled mouse x position.
	 */
	public float GetMouseX();
	
	/**
	 * Poll mouse y position, scaled by the current drawing perspective.
	 * @return scaled mouse y position.
	 */
	public float GetMouseY();
	
	/**
	 * Return the last relative mouse movement (x).
	 */
	public float GetRelMouseX();
	
	/**
	 * Return the last relative mouse movement (y).
	 */
	public float GetRelMouseY();
	
	/** 
	 * Returns the mouse buttons pressed/released this frame.
	 * Positive values indicate pressed buttons, negative values are released buttons.
	 * @return mouse button events.
	 */
	public int[] GetMouseEvents();
	
	/** 
	 * Returns the keys pressed/released this frame
	 * Positive key values indicate pressed keys, negative values are released keys.
	 * @return keyboard events
	 */
	public int[] GetKeyEvents();
	
	/**
	 * Returns the character values for the key events this frame.
	 * NOTE: key codes are returned for key presses AND KEY RELEASES! 
	 * This is done so the codes line up with the events returned by GetKeyEvents().
	 * @return key codes for key events this frame.
	 */
	public char[] GetKeyCharacters();

}
