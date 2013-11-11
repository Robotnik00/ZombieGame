/*	Zombie game
*/

package Menu;

import GameStates.MenuState;
import AudioEngine.IAudioEngine;
import Engine.IGameEngine;
import TextureEngine.ITextureEngine;

// imports



public interface IMenuWidget
{
	/**
	 * Initialize this widget with resource engines and a parent menu.
	 */
	public void	Init(IMenuController menuController, IMenuScreen menu) throws Exception;
	
	/**
	 * Clean up widget resources.
	 */
	public void	Quit();
	
	/**
	 * Update the widget.
	 * If this widget is focused, only it will receive update calls.
	 */
	public void	Update();
	
	/**
	 * Draw the widget.
	 * This is called for all widgets, even those out of focus.
	 * @param delta Represents the time in between update calls, range [0,1).
	 */
	public void	Draw(float delta);
	
	/**
	 * Add actions to this widget.
	 */
	public void	AddAction(IWidgetAction action);
}
