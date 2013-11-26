package Drawing;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import GameObjects.GameObject;
import TextureEngine.ITexture;
/**
*
* draws a texture in a tiled array centered at the GameObject
*/
// it may be more efficient to make a 2d array of GameObjects and add them to 
// universe. 
public class TileDraw implements DrawObject
{
	public TileDraw(ITexture tex)
	{
		this.tex = tex;
		nextRow = new Vector2f(-cols, 1);
		scale = new Vector3f();
		translation = new Vector2f();
	}
	
	
	
	@Override
	public void draw(Matrix4f interpolator) {

		interpolator.translate(new Vector2f(-cols/2, -rows/2));
		
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				tex.Draw(interpolator);
				
				interpolator.translate(xaxis);
			}
			interpolator.translate(nextRow);
		}
		
		
	}
	public void setRows(int rows)
	{
		this.rows = rows;
	}
	public void setCols(int cols)
	{
		this.cols = cols;
		nextRow = new Vector2f(-cols, 1);
	}
	public void setScale(float scalex, float scaley)
	{
		this.scalex = scalex;
		this.scaley = scaley;
	}
	public void setOffset(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	float x, y;
	float scalex = 1, scaley = 1;
	Vector3f scale;
	Vector2f translation;
	ITexture tex;
	int rows = 4;
	int cols = 4;
	Vector2f nextRow;
	static final Vector2f xaxis = new Vector2f(1,0);
}
