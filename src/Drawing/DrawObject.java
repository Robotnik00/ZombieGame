package Drawing;

import org.lwjgl.util.vector.Matrix4f;

/**
*
* used to draw a GameObject
*/
public interface DrawObject
{
	// called from GameObject. use the interpolator to posistion 
	// textures relative to the gameObject's location. the 
	// call the textures Draw method and pass the interpolator in.
	public void draw(Matrix4f interpolator);
}
