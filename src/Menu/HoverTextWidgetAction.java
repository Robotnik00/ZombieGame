/*	Zombie game
*/

package Menu;

// imports



public class HoverTextWidgetAction extends BaseWidgetAction
{
	//
	// public methods
	//
	
	/**
	 * HoverTextWidgetAction will change the text of a TextWidget when the action is triggered.
	 * @param target TextWidget
	 * @param hoverText Text to apply when the mouse hovers over the action area.
	 * @param exitHoverText Text to apply when the mouse leaves the action area (set to null to not change).
	 */
	public HoverTextWidgetAction(TextWidget target, String hoverText, String exitHoverText)
	{
		target_ = target;
		hoverText_ = hoverText;
		exitHoverText_ = exitHoverText;
	}
	
	
	
	//
	// IWidgetAction methods
	//
	
	public void	OnHover(boolean enter)
	{
		if (target_ == null)
			return;
		
		if (enter && hoverText_ != null)
		{
			target_.SetText(hoverText_);
		}
		else if (!enter && exitHoverText_ != null)
		{
			target_.SetText(exitHoverText_);
		}
	}
	
	
	
	//
	// protected members
	//
	
	protected TextWidget	target_;
	protected String		hoverText_;
	protected String		exitHoverText_;
}
