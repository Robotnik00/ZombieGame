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
	public void draw(float deltaT) {
				
		Vector2f velocity = new Vector2f(obj.getTranslationalVelocity());
		float rotVelocity = obj.getRotationalVelocity();
		
		Matrix4f interpolator = obj.getInterpolator();
		
		velocity.scale(deltaT);
		rotVelocity *= deltaT;
		
		interpolator.rotate(rotVelocity, new Vector3f(0,0,1));
		interpolator.translate(velocity);
		interpolator = obj.getGlobalTransform(interpolator);

		tex.Draw(interpolator);
		
		
	}
	ITexture tex;
	GameObject obj;
}
