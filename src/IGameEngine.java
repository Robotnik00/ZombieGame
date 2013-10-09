/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	IGameEngine.java
 * 		Interface to main game controller object.	
 */

// imports



/*	IGameEngine
 * 
 * 	Interface to the gamestate controller object.
 * 
 *	These are the functions that each gamestate is allowed to use to control the overall program.
 */


public interface IGameEngine
{
	//
	// gamestates, program flow
	//
	
	// Changes the current gamestate.
	// Exception thrown on failure.
	public void	ChangeGameState(IGameState state);
	
	// Ends the game loop and cause the program to shutdown
	public void	EndGameLoop();
	
	//
	// information
	//
	
	// Returns the estimated frames per second based on the number of frames drawn during the last tick.
	public int	GetFrameRate();
	
	// Check for a command line argument. Returns the index of the argument if found, or -1 if not found.
	public int	CheckArguments(String arg);
	
	// Returns the list of command line argument strings. 
	public String[]	GetArguments();
	
	// Log a text message to the standard output stream, with an added timestamp
	public void	LogMessage(String message);
	
	//
	// Time
	//
	
	// Returns the time elapsed since the program started in milliseconds.
	public long	GetTime();
	
	//
	// Input
	//
	
	public int GetMouseX();
	public int GetMouseY();
	
	public boolean	IsKeyPressed(int key);
}
