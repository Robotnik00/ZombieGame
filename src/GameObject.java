import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.util.vector.Matrix2f;
import org.lwjgl.util.vector.Matrix3f;

// other objects in the game can extend from this. Example: Cat could extend this to load a texture of a Cat
// not functional yet. Need to brush up on some Matrix maths...
public abstract class GameObject 
{
	public GameObject()
	{
		// set up default tranformation matrix (loc: 0,0 rot: 0)
		transform = new Matrix3f();
		transform.m00 = 1; transform.m11 = 1; transform.m22 = 1;
		
		
		parent = null;
		children = new CopyOnWriteArrayList<GameObject>();
		texture = null;
	}
	public void update(float deltaT)
	{
		if(texture != null)
		{
			// need to think about this.
//			texture.SetPos(getGlobalX(), getGlobalY());
//			texture.SetRotation(r);
//			texture.SetScale(sx, sy);
		}
		updateChildren(deltaT);
		updateThis(deltaT);
	}
	
	protected void updateChildren(float deltaT)
	{
		for(int i = 0; i < children.size(); i++)
		{
			children.get(i).update(deltaT);
		}
	}
	
	// decides how to update the position of the object
	protected abstract void updateThis(float deltaT);
	
	
	public void draw()
	{
		drawChildren();
		drawThis();
	}
	protected void drawChildren()
	{
		for(int i =0; i < children.size(); i++)
		{
			children.get(i).draw();
		}
	}
	
	// how to draw this.
	protected void drawThis()
	{
		if(texture != null)
		{
			texture.Draw();
		}
	}

	public boolean isRootNode()
	{
		if(parent == null)
		{
			return true;
		}
		return false;
	}
	public void setParent(GameObject parent)
	{
		this.parent = parent;
	}
	public void removeParent()
	{
		parent = null;
	}
	public void addChilde(GameObject child)
	{
		children.add(child);
		child.setParent(this);
	}
	public void removeChild(GameObject child)
	{
		child.removeParent();
		children.remove(child);
	}
	
	
	public void setX(float x)
	{
		transform.m02 = x;
	}
	public void setY(float y)
	{
		transform.m12 = y;
	}
	public void setRotation(float rot)
	{
		transform.m00 = (float)Math.cos(rot);
		transform.m01 = (float)Math.sin(rot);
		transform.m10 = (float)Math.sin(rot)*-1;
		transform.m11 = (float)Math.cos(rot);
	}
	public void setTransform(Matrix3f transform)
	{
		this.transform = transform;
	}
	public float getLocalX()
	{
		return transform.m02;
	}
	public float getLocalY()
	{
		return transform.m12;
	}
	public Matrix2f getRotationMatrix()
	{
		Matrix2f rotMat = new Matrix2f();
		rotMat.m00 = transform.m00;
		rotMat.m01 = transform.m01;
		rotMat.m10 = transform.m10;
		rotMat.m11 = transform.m11;
		return rotMat;
	}
	public Matrix3f getTransform()
	{
		return transform;
	}
	
	
	Matrix3f transform; // location/orientation/scale of obj 
	
	
	GLTexture texture;
	
	GameObject parent;
	
	CopyOnWriteArrayList<GameObject> children;
}
