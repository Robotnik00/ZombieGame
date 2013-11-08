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
		for(int i = 0; i < normals.length; i++)
		{
			normals[i] = new Vector4f();
		}
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
		float line1x1 = line1[0].length();
		if(Vector4f.dot(line1[0], axis) < 0)
		{
			line1x1 *= -1;
		}
		float line1x2 = line1[1].length();
		if(Vector4f.dot(line1[1], axis) < 0)
		{
			line1x2 *= -1;
		}
		float line2x1 = line2[0].length();
		if(Vector4f.dot(line2[0], axis) < 0)
		{
			line2x1 *= -1;
		}
		float line2x2 = line2[1].length();
		if(Vector4f.dot(line2[1], axis) < 0)
		{
			line2x2 *= -1;
		}
		
		float magnitude = 0;
		if(line1x2 < line2x1 || line1x1 > line2x2)
		{
			return null;
		}
		else if(line1x1 >= line2x1 && line1x2 <= line2x2)
		{
			magnitude = line1x2 - line1x1;
		}
		else if(line1x1 <= line2x1 && line1x2 <= line2x2)
		{
			magnitude = line1x2 - line2x1;
		}
		else if(line1x1 >= line2x1 && line1x2 >= line2x2)
		{
			magnitude = line2x2 - line1x1;
		}
		else if(line1x1 <= line2x1 && line1x2 >= line2x2)
		{
			magnitude = line2x2 - line2x1;
		}
		
		Vector4f outof = new Vector4f(axis);
		outof.normalise();
		outof.scale(magnitude);
		
		
		return outof;
	}
	
	
	
	
	
	
	
	
	// a projected 2d point will be a line segment defined by two points. This function projects the 
	// the shape onto an axis and returns the resultant line segment.
	private Vector4f[] projectShape(Vector4f[] verts, Vector4f axis)
	{
		Vector4f[] projectedPoints = new Vector4f[2];
		projectedPoints[0] = projectPoint(verts[0], axis);
		projectedPoints[1] = new Vector4f(projectedPoints[0]);

		float maxMagnitude = projectedPoints[1].length();
		if(Vector4f.dot(projectedPoints[1], axis) < 0)
		{
			maxMagnitude *= -1;
		}
		
		float minMagnitude = maxMagnitude;
		
		
		for(int i = 1; i < verts.length; i++)
		{
			Vector4f projectedPoint = projectPoint(verts[i], axis);
			
			float magnitude = projectedPoint.length();
			if( Vector4f.dot(axis, projectedPoint) < 0)
			{
				magnitude *= -1;
			}
			
			//System.out.printf("%f %f\n", projectedPoint.x, projectedPoint.y);
			if(magnitude > maxMagnitude)
			{
				projectedPoints[1] = projectedPoint;
				maxMagnitude = magnitude;
			}
			else if(magnitude < minMagnitude)
			{
				projectedPoints[0] = projectedPoint;
				minMagnitude = magnitude;
			}
		}

		//System.out.printf("%f %f\n", projectedPoints[0].x, projectedPoints[0].y);
		//System.out.printf("%f %f\n\n", projectedPoints[1].x, projectedPoints[1].y);
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
		for(int i = 0; i < verts.length; i++)
		{
			verts[i].w = 1;
			Matrix4f.transform(transform, verts[i], verts[i]);
			verts[i].w = 0;
		}
	}
	// list of vertices of convex shape. verts[i] connects to verts[i+1] first connects to last
	Vector4f[] verts;
	Vector4f[] normals;
}
