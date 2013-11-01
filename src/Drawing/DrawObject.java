package Drawing;

import org.lwjgl.util.vector.Matrix4f;

/**
*
* used to draw a GameObject
*/
public interface DrawObject
{
	public void draw(Matrix4f interpolator);
}
