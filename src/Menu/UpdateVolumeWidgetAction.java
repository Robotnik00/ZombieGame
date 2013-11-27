/*	Zombie Game
*/

package Menu;

// imports
import Engine.IGameEngine;
import Utility.ConfigData;



public class UpdateVolumeWidgetAction extends BaseWidgetAction
{
	public UpdateVolumeWidgetAction()
	{
		//
	}
	
	public void	OnClick()
	{
		ConfigData cfg = menuController_.GetGameController().GetGameConfig();
		
		float f = cfg.GetFloatValue("sound_volume");
		
		menuController_.GetAudioController().SetVolume(f);
	}
	
}