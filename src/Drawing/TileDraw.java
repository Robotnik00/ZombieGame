package Drawing;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import GameObjects.GameObject;
import TextureEngine.ITexture;

public class TileDraw implements DrawObject
{
	public TileDraw(GameObject obj, ITexture tex)
	{
		this.tex = tex;
		this.obj = obj;
	}
	
	
	
	@Override
	public void draw(float deltaT) {
		// TODO Auto-generated method stub
		
		Matrix4f interpolator = obj.getGlobalTransform(obj.getInterpolator());
		Vector4f velocity = obj.getGlobalVelocity();
		Vector2f velocity2d = new Vector2f();
		velocity2d.x = velocity.x;
		velocity2d.y = velocity.y;
		
		velocity2d.scale(deltaT);
		interpolator.translate(velocity2d);
		interpolator.translate(new Vector2f(-cols/2, -rows/2));
		
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				tex.Draw(interpolator);
				interpolator.translate(new Vector2f(1, 0));
			}
			interpolator.translate(new Vector2f(-cols, 1));
		}
		
		
	}
	ITexture tex;
	GameObject obj;
	int rows = 10;
	int cols = 10;
}
