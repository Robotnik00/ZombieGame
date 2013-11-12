/*	Zombie Game
*/

package Menu;

// imports
import GameStates.MenuState;
import Engine.IGameEngine;
import TextureEngine.ITextureEngine;
import AudioEngine.IAudioEngine;




/**
 * Interface to a GUI menu.
 * Menus store a list of MenuWidgets, which the user can interact with on screen. 
 */

public interface IMenuScreen
{
	/**
	 * Initialize this menu screen with the resources it needs.
	 * @param menuController Menu resource and controller class.
	 */
	public void	Init(IMenuController menuController) throws Exception;
	
	/**
	 * Returns whether this menu screen has been initialized already
	 */
	public boolean IsInitialized();
	
	/**
	 * Clean up menu state, destroy the menu.
	 */
	public void	Quit();
	
	/**
	 * MenuScreens are stored on a stack;
	 * This function is called when this menuscreen is about to be restored from the menu stack.
	 */
	public void	Pop();
	
	/**
	 * MenuScreens are stored on a stack; this function is called when this menuscreen is about to be pushed onto
	 * the menu stack. This usually happens when transitioning to a new menu.
	 * 
	 * When this is called, keep the menu screen and its widgets in a "drawable" state; the MenuState may need to draw
	 * (but not update) the menu screen during transitions.
	 */
	public void	Push();
	
	/**
	 * Called on each program update.
	 */
	public void	Update();
	
	/**
	 * Called on each frame refresh.
	 * @param delta Interpolation value, ranges [0,1) indicating the time between update calls.
	 */
	public void	Draw(float delta);
	
	/**
	 * A menu widget can request focus from the menu screen, 
	 * meaning that for a time only it will receive update calls.
	 * All widgets will continue to receive draw calls, however.
	 */
	public void	RequestFocus(IMenuWidget widget);
	
	/**
	 * Releases focus from any widgets that are focused.
	 */
	public void	ReleaseFocus();
}
























