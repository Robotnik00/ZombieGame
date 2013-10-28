package Drawing;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import GameObjects.GameObject;
import TextureEngine.ITexture;

public class SimpleDraw implements DrawObject
{
	public SimpleDraw(GameObject obj, ITexture tex)
	{
		this.obj = obj;
		this.tex = tex;
	}
	
	@Override
	public void draw(float deltaT) {
		// TODO Auto-generated method stub.
		Matrix4f interpolator = obj.getGlobalTransform(obj.getInterpolator());
		Vector4f velocity = obj.getGlobalVelocity();
		Vector2f velocity2d = new Vector2f();
		velocity2d.x = velocity.x;
		velocity2d.y = velocity.y;
		
		velocity2d.scale(deltaT);
		interpolator.translate(velocity2d);
		
		tex.Draw(interpolator);
	}
	ITexture tex;
	GameObject obj;
}
