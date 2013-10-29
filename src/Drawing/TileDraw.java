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
		
		Matrix4f interpolator = obj.getInterpolator();
		
		
		Vector2f velocity = new Vector2f(obj.getTranslationalVelocity());
		float rotVelocity = obj.getRotationalVelocity();
		
		velocity.scale(deltaT);
		rotVelocity *= deltaT;
		
		interpolator.rotate(rotVelocity, new Vector3f(0,0,1));
		interpolator.translate(velocity);
		
		
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
	int rows = 15;
	int cols = 20;
}
