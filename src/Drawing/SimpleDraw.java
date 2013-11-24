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
	public SimpleDraw(ITexture tex)
	{
		this.tex = tex;
		offset = new Vector2f();
	}
	
	@Override
	public void draw(Matrix4f interpolator) 
	{
		interpolator.scale(new Vector3f(scalex, scaley, 0));
		interpolator.rotate(orientation, zaxis);
		interpolator.translate(center);
		interpolator.translate(offset);
		if(color != null)
		{
			tex.SetBlendColor(color.x, color.y, color.z, blend);
			tex.SetAlpha(color.w);
		}
		
		tex.Draw(interpolator);
		
		
	}
	public void setOrientation(float orientation)
	{
		this.orientation = orientation;
	}
	public void setOffset(float x, float y)
	{
		offset.x = x;
		offset.y = y;
	}
	public void setScale(float scalex, float scaley)
	{
		this.scalex = scalex;
		this.scaley = scaley;
	}
	public void setColor(Vector4f color)
	{
		this.color = color;
	}
	public void setBlend(float blend)
	{
		this.blend =  blend;
	}
	public void setTexture(ITexture tex)
	{
		this.tex = tex;
	}
	Vector4f color = null;
	float blend = 0f;
	float orientation = 0;
	float scalex = 1;
	float scaley = 1;
	Vector2f offset;
	Vector2f center = new Vector2f(-.5f,-.5f);
	static Vector3f zaxis = new Vector3f(0,0,1);
	ITexture tex;
}
