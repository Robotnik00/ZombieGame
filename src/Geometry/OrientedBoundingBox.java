package Geometry;

import org.lwjgl.util.glu.Project;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
/**
*
* OBB using SAT http://www.metanetsoftware.com/technique/tutorialA.html
* Used in collision detection as the bounds of a single node
*/

public class OrientedBoundingBox
{
	public OrientedBoundingBox(Vector4f[] verts)
	{
		normals = new Vector4f[verts.length];
		float avgX = 0;
		float avgY = 0;
		for(int i = 0; i < normals.length; i++)
		{
			normals[i] = new Vector4f();
			avgX += verts[i].x;
			avgY += verts[i].y;
		}
		avgX /= verts.length;
		avgY /= verts.length;
		center = new Vector4f();
		center.x = avgX;
		center.y = avgY;
		this.verts = verts;
	}
	
	// two shapes intersect if you cant find an axis such that the two projected shapes 
	// dont overlap.  Furthermore, if the shape does intersect, than the smallest overlap
	// is the vector that defines how much to translate to exit the intersection.
	public Vector4f intersects(OrientedBoundingBox bounds)
	{
		generateNormals();
		
		Vector4f[] otherVerts = bounds.getVerts();
		Vector4f[] otherNormals = bounds.getNormals();
		
		
		
		Vector4f[] projectedShape1 = projectShape(verts, normals[0]);
		Vector4f[] projectedShape2 = projectShape(otherVerts, normals[0]);
		
		Vector4f minIntersection = lineOverlap(projectedShape1, projectedShape2, normals[0]);
		for(int i = 1; i < normals.length; i++)
		{
			projectedShape1 = projectShape(verts, normals[i]);
			projectedShape2 = projectShape(otherVerts, normals[i]);
			Vector4f intersection = lineOverlap(projectedShape1, projectedShape2, normals[i]);
			if(intersection == null)
			{
				return null;
			}
			else if(intersection.length() <= minIntersection.length())
			{

				minIntersection.x = intersection.x;
				minIntersection.y = intersection.y;
			}			
		}
		for(int i = 0; i < otherNormals.length; i++)
		{	
			projectedShape1 = projectShape(verts, otherNormals[i]);
			projectedShape2 = projectShape(otherVerts, otherNormals[i]);
			Vector4f intersection = lineOverlap(projectedShape1, projectedShape2, otherNormals[i]);
			if(intersection == null)
			{
				return null;
			}
			else if(intersection.length() <= minIntersection.length())
			{

				minIntersection.x = intersection.x;
				minIntersection.y = intersection.y;
			}	
		}
		
		//System.out.printf("\n%f %f \n", projectedShape[0].x, projectedShape[0].y);
		//System.out.printf("%f %f \n", projectedShape[1].x, projectedShape[1].y);
		
		
		return minIntersection;
	}

	
	private Vector4f lineOverlap(Vector4f[] line1, Vector4f[] line2, Vector4f axis)
	{
		float line1dot1 = Vector4f.dot(axis, line1[0]);
		float line1dot2 = Vector4f.dot(axis, line1[1]);
		float line2dot1 = Vector4f.dot(axis, line2[0]);
		float line2dot2 = Vector4f.dot(axis, line2[1]);
		Vector4f out = new Vector4f();
		
		if(line1dot2 < line2dot1 || line1dot1 > line2dot2)
		{
			return null;
		}
		else if(line1dot1 >= line2dot1 && line1dot2 <= line2dot2)
		{
			out.x = line1[1].x - line1[0].x;
			out.y = line1[1].y - line1[0].y;
		}
		else if(line1dot1 <= line2dot1 && line1dot2 <= line2dot2)
		{
			out.x = line1[1].x - line2[0].x;
			out.y = line1[1].y - line2[0].y;
		}
		else if(line1dot1 >= line2dot1 && line1dot2 >= line2dot2)
		{
			out.x = line2[1].x - line1[0].x;
			out.y = line2[1].y - line1[0].y;
		}
		else if(line1dot1 <= line2dot1 && line1dot2 >= line2dot2)
		{
			out.x = line2[1].x - line2[0].x;
			out.y = line2[1].y - line2[0].y;
		}
		
		
		return out;
	}
	
	
	
	
	
	
	
	
	// a projected 2d point will be a line segment defined by two points. This function projects the 
	// the shape onto an axis and returns the resultant line segment.
	private Vector4f[] projectShape(Vector4f[] verts, Vector4f axis)
	{
		Vector4f[] projectedPoints = new Vector4f[2];
		projectedPoints[0] = projectPoint(verts[0], axis);
		projectedPoints[1] = new Vector4f(projectedPoints[0]);

		float maxDotprod = Vector4f.dot(projectedPoints[1], axis);
		
		float minDotprod = maxDotprod;
		
		for(int i = 1; i < verts.length; i++)
		{
			Vector4f projectedPoint = projectPoint(verts[i], axis);
			
			float dotprod = Vector4f.dot(axis, projectedPoint);
			
			//System.out.printf("%f %f\n", projectedPoint.x, projectedPoint.y);
			if(dotprod > maxDotprod)
			{
				projectedPoints[1] = projectedPoint;
				maxDotprod = dotprod;
			}
			else if(dotprod < minDotprod)
			{
				projectedPoints[0] = projectedPoint;
				minDotprod = dotprod;
			}
		}
		
		
		
		return projectedPoints;
	}
	
	// projects a single point onto an axis
	private Vector4f projectPoint(Vector4f point, Vector4f axis)
	{
		double dp = Vector4f.dot(point, axis);
		Vector4f possiblePoint = new Vector4f();
		possiblePoint.x = (float)dp/(axis.x*axis.x + axis.y*axis.y)*axis.x;
		possiblePoint.y =  (float)dp/(axis.x*axis.x + axis.y*axis.y)*axis.y;
		return possiblePoint;
	}
	
	private void generateNormals()
	{
		normals[0].y = verts[verts.length - 1].x - verts[0].x;
		normals[0].x = verts[0].y - verts[verts.length-1].y;
		normals[0].normalise();
		for(int i = 1; i < verts.length; i++)
		{
			normals[i].y = verts[i-1].x - verts[i].x;
			normals[i].x = verts[i].y - verts[i-1].y;
			normals[i].normalise();
			
		}
	}
	
	public Vector4f[] getNormals()
	{
		generateNormals();
		return normals;
	}
	
	public Vector4f[] getVerts()
	{
		return verts;
	}
	
	
	
	
	
	
	public void transform(Matrix4f transform)
	{
		center.x = 0;
		center.y = 0;
		for(int i = 0; i < verts.length; i++)
		{
			verts[i].w = 1;
			Matrix4f.transform(transform, verts[i], verts[i]);
			verts[i].w = 0;
			center.x += verts[i].x;
			center.y += verts[i].y;
		}
		center.x /= verts.length;
		center.y /= verts.length;
	}
	// list of vertices of convex shape. verts[i] connects to verts[i+1] first connects to last
	Vector4f[] verts;
	Vector4f[] normals;

	Vector4f center;
	
	static final Vector4f xaxis = new Vector4f(1,0,0,0);
	static final Vector4f yaxis = new Vector4f(0,1,0,0);
}

