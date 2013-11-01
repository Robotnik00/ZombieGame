package Drawing;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import GameObjects.GameObject;
import TextureEngine.ITexture;
/**
*
* draws a basic texture at the location of the GameObject
*/
public class SimpleDraw implements DrawObject
{
	public SimpleDraw(GameObject obj, ITexture tex)
	{
		this.obj = obj;
		this.tex = tex;
	}
	
	@Override
	public void draw(Matrix4f interpolator) 
	{
		
		interpolator.translate(center);
		tex.Draw(interpolator);
		
		
	}
	Vector2f center = new Vector2f(-.5f,-.5f);
	ITexture tex;
	GameObject obj;
}
