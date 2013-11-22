package Geometry;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;
import org.lwjgl.util.vector.Vector2f;

// creates a bounding box/ proximity centered at the object
public class AABB 
{
	public AABB(float w, float h)
	{
		this.w = w;
		this.h = h;
		bl = new Vector4f(-w/2.0f, -h/2.0f, 0.0f, 1.0f);
		tr = new Vector4f(w/2.0f, h/2.0f, 0.0f, 1.0f);
		scale = new Vector4f();
		//bl.w = 1;
		//tr.w = 1;
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
	
	public void simpleTransform(float x, float y)
	{
		bl.x = x - w/2.0f;
		bl.y = y - h/2.0f;
		tr.x = x + w/2.0f;
		tr.y = y + h/2.0f;
		
		System.out.println(
				"simpleTransform: blx="+bl.x+", bly="+bl.y+", trx="+tr.x+", try="+tr.y);
		scale = xaxis;
	}
	
	public boolean intersects(AABB other)
	{	
		if (bl.x > other.tr.x	// completely off right edge
		||	tr.x < other.bl.x	// " off left edge
		||	bl.y > other.tr.y	// " off top edge
		||	tr.y < other.bl.y)	// " off bottom edge
			return false;
	
		return true;
		
		/*
		if (bl.x < other.tr.x 
		&&	tr.x > other.bl.x 
		&&	bl.y < other.tr.y 
		&&	tr.y > other.bl.y)
		{
			return true;
		}
		return false;
		*/
	}
	
	/**
	 * Returns a vector to move this AABB to free it from a collision with the given AABB.
	 * @param other AABB to test against
	 * @return vector to move this
	 */
	
	public Vector2f solveCollision(AABB other)
	{
		if (!intersects(other))
			return noCollision;
		
		float thisx = (tr.x - bl.x)/2 + bl.x;
		float thisy = (tr.y - bl.y)/2 + bl.y;
		float otherx = (other.tr.x - other.bl.x)/2 + other.bl.x;
		float othery = (other.tr.y - other.bl.y)/2 + other.bl.y;
		
		float dx = otherx - thisx;
		float dy = othery - thisy;
		
		/*
		
		   1  a (dy > dx)
		  \  /
		 3 \/ 2
		   /\
		  /  \
		   4  b (dy > -dx)
		*/
		
		boolean a = (dx >= dy);
		boolean b = (-dx >= dy);
		
		if (a && b)	// case 1: top edge
		{
			dy = other.tr.y - bl.y;
			return new Vector2f(0.0f, dy/scale.length());
		}
		else if (!a && b) // case 2: right edge
		{
			dx = other.tr.x - bl.x;
			return new Vector2f(dx/scale.length(), 0.0f);
		}
		else if (a && !b)	// case 3: left edge
		{
			dx = -(tr.x - other.bl.x);
			return new Vector2f(dx/scale.length(), 0.0f);
		}
		else	// case 4: bottom edge
		{
			dy = -(tr.y - other.bl.y);
			return new Vector2f(0.0f, dy/scale.length());	
		}
		
		//return noCollision;
	}
	
	public float w, h;
	public Vector4f bl, tr;
	
	Vector4f scale;
	Matrix4f cpy;
	public static final Vector4f xaxis = new Vector4f(1,0,0,0);
	public static final Vector2f noCollision = new Vector2f(0.0f, 0.0f);
}
