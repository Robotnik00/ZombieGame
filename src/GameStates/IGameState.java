/*	Zombie Game
 *	2013 Authors
 *
 * 	License?
 * 
 * 	IGameState.java
 * 		Interface to game state/game mode	
 */

// imports



/*	IGameState
 *
 * 	The top-level gameplay code will be contained in a GameState class. This interface must be implemented by all
 * 	gamestate classes.
 * 
 * 	Gamestates are managed by a GameEngine class, which controls the frequency of game logic updates and screen
 * 	updates. Game resource managers (ITextureEngine, IAudioEngine, etc.) are created by the GameEngine class and passed
 * 	to each gamestate class upon creation. A gamestate can use the GameEngine to change gamestates with the appropriate
 * 	methods, in order to switch gameplay modes (eg "menu" gamestate directs GameEngine to change state to the actual
 * 	"gameplay" state when the Start Game menu item is selected). 
 * 
 * 	Init() and Quit() are called at gamestate creation and deletion.
 * 
 * 	Update() is called at a constant 25 ticks/second.
 * 
 * 	Draw() is called as many times as possible within the time leftover, after Update() finishes. A delta is provided
 * 	to Draw, which represents the time inbetween ticks, expressed as a float with the range [0,1). Gamestates should
 * 	be able to separate drawing and update logic, and interpolate inbetween frames for smoother animation on faster
 * 	computers.
 */
package GameStates;

import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import TextureEngine.ITextureEngine;

public interface IGameState
{
	// Initialize the gamestate and pass references to game resource managers.
	// Exception should be thrown on failure.
	public void	Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game);
	
	// Called when the gamestate is destroyed.
	public void	Quit();
	
	// Called at a constant rate by the GameEngine.
	public void	Update();
	
	// Called many times per update, or none at all if the game is running slowly (highly unlikely).
	//	delta	- interpolation value in between frames
	public void	Draw(float delta);
}











