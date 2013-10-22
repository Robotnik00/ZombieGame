package Geometry;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector3f;

// OBB using SAT http://www.metanetsoftware.com/technique/tutorialA.html
public class OrientedBoundingBox
{
	public OrientedBoundingBox(Vector3f[] verts)
	{
		this.verts = verts;
		
	}
	
	public boolean intersects(OrientedBoundingBox bounds)
	{
		
		
		
		
		
		return false;
	}
	
	public void transform(Matrix3f transform)
	{
		
		for(int i = 0; i < verts.length; i++)
		{
			Matrix3f.transform(transform, verts[i], verts[i]);
		}
	}
	// list of vertices of convex shape. verts[i] connects to verts[i+1] first connects to last
	Vector3f[] verts;
}
