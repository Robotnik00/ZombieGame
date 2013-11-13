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
	
	/**
	 * Returns a vector to move this AABB to free it from a collision with the given AABB.
	 * @param other AABB to test against
	 * @return vector to move this
	 */
	public Vector2f solveCollision(AABB other)
	{
		float thisx = (tr.x - bl.x)/2;
		float thisy = (tr.y - bl.y)/2;
		float otherx = (other.tr.x - other.bl.x)/2;
		float othery = (other.tr.y - other.bl.y)/2;
		float intx=0.0f, inty=0.0f;
		
		if (thisx > otherx)	// on right
		{
			intx = other.tr.x - bl.x;
			
			// other box is too far left, no collision
			if (intx < 0.0f)
				return noCollision;
			
			if (thisy > othery)			// above
			{
				inty = other.tr.y - bl.y;
				
				if (inty < 0.0f)
					return noCollision;
			}
			else if (thisy == othery)	// same position
			{
				return new Vector2f(intx/(scale.length()), 0.0f);
			}
			else	// thisy < othery	// below
			{
				inty = tr.y - other.bl.y;
				
				if (inty > 0.0f)
					return noCollision;
			}
		}
		else if (thisx == otherx)
		{
			if (thisy > othery)
			{
				inty = other.tr.y - bl.y;
				
				if (inty < 0.0f)
					return noCollision;
				else
					return new Vector2f(0.0f, inty/(scale.length()));
			}
			else if (thisy == othery)
			{
				// same coordinates, move it to the right slightly
				return new Vector2f(0.1f, 0.0f);
			}
			else	// thisy < othery
			{
				inty = other.bl.y - tr.y;
				
				if (inty > 0.0f)
					return noCollision;
				else
					return new Vector2f(0.0f, inty/(scale.length()));
			}
		}
		else // thisx < otherx // on left
		{
			intx = other.bl.x - tr.x;
			
			if (intx > 0.0f)
				return noCollision;
			
			if (thisy > othery)			// above
			{
				inty = other.tr.y - bl.y;
				
				if (inty < 0.0f)
					return noCollision;
			}
			else if (thisy == othery)	// same position
			{
				return new Vector2f(intx/(scale.length()), 0.0f);
			}
			else	// thisy < othery	// below
			{
				inty = tr.y - other.bl.y;
				
				if (inty > 0.0f)
					return noCollision;
			}
		}
		
		// intersections on both axis and no obvious winner, 
		// return the axis with the least movement
		if (Math.abs(intx) > Math.abs(inty))
			return new Vector2f(0.0f, inty/(scale.length()));
		else
			return new Vector2f(intx/(scale.length()), 0.0f);
	}
	
	public float w, h;
	public Vector4f bl, tr;
	
	Vector4f scale;
	Matrix4f cpy;
	public static final Vector4f xaxis = new Vector4f(1,0,0,0);
	public static final Vector2f noCollision = new Vector2f(0.0f, 0.0f);
}
