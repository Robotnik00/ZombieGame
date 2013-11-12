/*	Zombie Game
*/

package Menu;

// imports
import Engine.IGameEngine;
import Utility.ConfigData;



public class AdjustFloatConfigWidgetAction extends BaseWidgetAction
{
	public AdjustFloatConfigWidgetAction(
			String variable, float adjust, float upperBound, float lowerBound)
	{
		variable_ = variable;
		adjust_ = adjust;
		upperBound_ = upperBound;
		lowerBound_ = lowerBound;
	}
	
	public void	OnClick()
	{
		ConfigData cfg = menuController_.GetGameController().GetGameConfig();
		float f = cfg.GetFloatValue(variable_);
		
		f += adjust_;
		if (f > upperBound_)
			f = upperBound_;
		if (f < lowerBound_)
			f = lowerBound_;
		
		cfg.SetFloatValue(variable_, f);
	}
	
	//
	// protected members
	//
	
	protected float		upperBound_;
	protected float		lowerBound_;
	protected float		adjust_;
	protected String	variable_;
}