package Geometry;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

// creates a bounding box/ proximity centered at the object
public class AABB 
{
	public AABB(float w, float h)
	{
		this.w = w;
		this.h = h;
		bl = new Vector4f();
		tr = new Vector4f();
		scale = new Vector4f();
		bl.w = 1;
		tr.w = 1;
		cpy = new Matrix4f();
	}
	
	public void transform(Matrix4f transform)
	{

		cpy.load(transform); 
		cpy.m30 = 0;
		cpy.m31 = 0;
		Matrix4f.transform(cpy, xaxis, scale);

		
		
		
		float scalex = w * scale.length();
		float scaley = h * scale.length();
		bl.x = transform.m30 - scalex/2;
		bl.y = transform.m31 - scaley/2;
		tr.x = transform.m30 + scalex/2;
		tr.y = transform.m31 + scaley/2;
		
		
		
	}
	
	public boolean intersects(AABB other)
	{
		
		if (bl.x < other.tr.x && tr.x > other.bl.x && bl.y < other.tr.y && tr.y > other.bl.y)
		{
			return true;
		}
		return false;
	}
	
	public float w, h;
	public Vector4f bl, tr;
	Vector4f scale;
	Matrix4f cpy;
	public static final Vector4f xaxis = new Vector4f(1,0,0,0);
}
