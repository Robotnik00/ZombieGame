/*	Zombie Game
*/

package Menu;

// imports



public class ChangeMenuWidgetAction extends BaseWidgetAction
{
	public ChangeMenuWidgetAction(IMenuScreen newScreen)
	{
		newScreen_ = newScreen;
	}
	
	public void OnClick()
	{
		try {
			menuController_.ChangeMenuScreen(newScreen_);
		} catch (Exception e) {
			// derp
		}
	}
	
	protected IMenuScreen newScreen_;
}
