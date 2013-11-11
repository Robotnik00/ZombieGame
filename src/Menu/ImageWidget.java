/* Zombie game
*/

package Menu;

// imports
import TextureEngine.ITexture;



/**
 * Widget for a static image.
 */
public class ImageWidget extends BaseMenuWidget
{
	//
	// public methods
	//
	
	public ImageWidget(
			ITexture image, 
			float x, float y,
			float xscale, float yscale)
	{
		super();
		image_ = image;
		x_ = x;
		y_ = y;
		xs_ = xscale;
		ys_ = yscale;
	}
	
	/**
	 * Returns the area of the image.
	 * @return float[4], [0]=left, [1]=right, [2]=bottom, [3]=top
	 */
	public float[] GetAreaOnScreen()
	{
		float[] bounds = new float[4];
		float aspect = (float)image_.GetHeight() / (float)image_.GetWidth();
		bounds[0] = x_;
		bounds[1] = x_ + xs_;
		bounds[2] = y_;
		bounds[3] = y_ + aspect * ys_;
		return bounds;
	}
	
	//
	// IMenuWidget interface methods
	//
	
	public void	Init(IMenuController menuController, IMenuScreen menu) throws Exception
	{
		super.Init(menuController,  menu);
	}
	
	public void	Quit()
	{
		super.Quit();
	}
	
	public void	Update()
	{
		super.Update();
	}
	
	public void	Draw(float delta)
	{
		image_.SetPos(x_, y_);
		image_.SetScale(xs_, ys_);
		image_.Draw();
	}
	
	
	
	//
	// protected members
	//
	
	protected ITexture			image_;
	protected float				x_, y_, xs_, ys_;
}
