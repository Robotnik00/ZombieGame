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
		float thisx = (tr.x - bl.x)/2 + bl.x;
		float thisy = (tr.y - bl.y)/2 + bl.y;
		float otherx = (other.tr.x - other.bl.x)/2 + other.bl.x;
		float othery = (other.tr.y - other.bl.y)/2 + other.bl.y;
		float dx=0.0f, dy=0.0f;
		
		if (!intersects(other))
			return noCollision;
		
		/*
		           tr.x
		        1|2|3
		        -+-+- tr.y
		        4|5|6
		   bl.y -+-+-
		        7|8|9
		      bl.x
		*/
		
		if (thisy > other.tr.y)
		{
			dy = other.tr.y - bl.y;
			
			// case 1: upper left corner
			if (thisx < other.bl.x)
			{
				dx = -(tr.x - other.bl.x);
			
				//System.out.println("Case 1: dx="+dx+", dy="+dy);
				
				if (Math.abs(dx) > Math.abs(dy))
					return new Vector2f(0.0f, dy);
				else
					return new Vector2f(dx, 0.0f);
			}
			
			// case 3: upper right corner
			if (thisx > other.tr.x)
			{
				dx = other.tr.x - bl.x;
				
				//System.out.println("Case 3: dx="+dx+", dy="+dy);
				
				if (Math.abs(dx) > Math.abs(dy))
					return new Vector2f(0.0f, dy);
				else
					return new Vector2f(dx, 0.0f);
			}
			
			// case 2: center top
			//System.out.println("Case 2: dx="+dx+", dy="+dy);
			return new Vector2f(0.0f, dy);	
		}
		
		if (thisy < other.bl.y)
		{
			dy = -(tr.y - other.bl.y);
			
			// case 7: lower left corner
			if (thisx < other.bl.x)
			{
				dx = -(tr.x - other.bl.x);
				
				//System.out.println("Case 7: dx="+dx+", dy="+dy);
				
				if (Math.abs(dx) > Math.abs(dy))
					return new Vector2f(0.0f, dy);
				else
					return new Vector2f(dx, 0.0f);
			}
			
			// case 9: lower right corner
			if (thisx > other.tr.x)
			{
				dx = other.tr.x - bl.x;
				
				//System.out.println("Case 9: dx="+dx+", dy="+dy);
				
				if (Math.abs(dx) > Math.abs(dy))
					return new Vector2f(0.0f, dy);
				else
					return new Vector2f(dx, 0.0f);
			}
			
			// case 8: center bottom
			//System.out.println("Case 8: dx="+dx+", dy="+dy);
			return new Vector2f(0.0f, dy);	
		}
		
		// case 4: center left
		if (thisx < other.bl.x)
		{
			dx = -(tr.x - other.bl.x);
			//System.out.println("Case 4: dx="+dx+", dy="+dy);
			return new Vector2f(dx, 0.0f);
		}
		
		// case 6: center right
		if (thisx > other.tr.x)
		{
			dx = other.tr.x - bl.x;
			//System.out.println("Case 6: dx="+dx+", dy="+dy);
			return new Vector2f(dx, 0.0f);
		}
		
		// case 5: do nothing!
		//System.out.println("Case 5: dx="+dx+", dy="+dy);
		return noCollision;
	}
	
	public float w, h;
	public Vector4f bl, tr;
	
	Vector4f scale;
	Matrix4f cpy;
	public static final Vector4f xaxis = new Vector4f(1,0,0,0);
	public static final Vector2f noCollision = new Vector2f(0.0f, 0.0f);
}
