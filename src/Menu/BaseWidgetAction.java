/*	Zombie game
*/

package Menu;

// imports



public class BaseWidgetAction implements IWidgetAction
{
	//
	// public methods
	//
	
	public BaseWidgetAction()
	{
		menuController_ = null;
		parentWidget_ = null;
		left_ = right_ = top_ = bottom_ = 0.0f;
	}
	
	//
	// IWidgetAction methods
	//
	
	public void	Init(IMenuController controller, IMenuWidget parent)
	{
		menuController_ = controller;
		parentWidget_ = parent;
	}
	
	public void SetArea(float left, float right, float bottom, float top)
	{
		left_ = left;
		right_ = right;
		bottom_ = bottom;
		top_ = top;
	}
	
	public float[] GetArea()
	{
		return new float[]{left_, right_, bottom_, top_};
	}
	
	// this will be overwritten
	public void	OnHover(boolean enter)
	{
		//
	}
	
	// this will be overwritten
	public void	OnClick()
	{
		//
	}
	
	
	
	//
	// protected members
	//
	
	protected IMenuController	menuController_;
	protected IMenuWidget		parentWidget_;
	
	protected float				left_, right_, bottom_, top_;
}
