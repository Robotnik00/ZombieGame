/*	Zombie Game
*/

package Menu;

// imports
import Engine.IGameEngine;



public class QuitGameWidgetAction extends BaseWidgetAction
{
	public QuitGameWidgetAction()
	{
		//
	}
	
	public void OnClick()
	{
		IGameEngine game = menuController_.GetGameController();
		//game.LogMessage("QuitGameWidgetAction::OnClick");
		game.EndGameLoop();
	}
}
