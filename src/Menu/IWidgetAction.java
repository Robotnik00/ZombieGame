/*	Zombie game
*/

package Menu;

// imports



public interface IWidgetAction
{
	/**
	 * Widget this action is attached to.
	 * @param parent
	 */
	public void	Init(IMenuController controller, IMenuWidget parent);
	
	/**
	 * Area this action covers. Mouse presence and clicks within this area will trigger the action.
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 */
	public void SetArea(float left, float right, float bottom, float top);
	
	/**
	 * Get the action's area.
	 * @return float[4]; [0]=left, [1]=right, [2]=bottom, [3]=top
	 */
	public float[] GetArea();
	
	/**
	 * Called every frame that the mouse is within the action's area.
	 * @param enter This function is called with true when the mouse first enters the area, and false when it exits.
	 */
	public void	OnHover(boolean enter);
	
	/**
	 * Called when a mouse click is within the action's area. Responds to any mouse button.
	 */
	public void	OnClick();
}
