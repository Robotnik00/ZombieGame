/*	Zombie Game
*/

package Menu;

import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import TextureEngine.ITextureEngine;

// imports



/**
 * Menu controller objects handle the menu stack, transitioning between menus, and providing resources to menus.
 */
public interface IMenuController
{
	/**
	 * Transition to a new menu.
	 */
	public void	ChangeMenuScreen(IMenuScreen menu) throws Exception;
	
	/**
	 * Transition to the previous menu (if any).
	 */
	public void	PreviousMenu();
	
	/**
	 * Get a handle to the game controller.
	 * @return
	 */
	public IGameEngine		GetGameController();
	
	/**
	 * Get a handle to the graphics resource manager.
	 * @return
	 */
	public ITextureEngine	GetGraphicsController();
	
	/**
	 * Get a handle to the audio resource manager.
	 * @return
	 */
	public IAudioEngine		GetAudioController();
}
