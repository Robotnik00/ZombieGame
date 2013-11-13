/*	Zombie Game
*/

package Menu;

import Engine.IGameEngine;
import GameStates.IGameState;
// imports



public class ChangeGameStateWidgetAction extends BaseWidgetAction
{
	public ChangeGameStateWidgetAction(IGameState newState)
	{
		newState_ = newState;
	}
	
	public void OnClick()
	{
		try {
			IGameEngine game = menuController_.GetGameController();
			game.ChangeGameState(newState_);
		} catch (Exception e) {
			// derp
		}
	}
	
	protected IGameState newState_;
}
