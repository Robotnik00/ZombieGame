
package GameObjects;

import java.util.ArrayList;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Matrix2f;
import org.lwjgl.util.vector.Matrix3f;

import Actions.Action;
import TextureEngine.ITexture;



// other objects in the game can extend from this. 
// Example1: Cat could extend this to load a texture of a Cat
// Example2: PhysicsObject could extend this objects behavior 
//           allowing it to have velocity, mass, force ect...

// display not working yet, but math seems to work.
public class GameObject 
{
	public GameObject()
	{
		// set up default transformation matrix (loc: 0,0 rot: 0)
		transform = new Matrix3f();
		transform.setIdentity();
		
		actions = new ArrayList<Action>();
		
		parent = null;
		children = new ArrayList<GameObject>();
		texture = null; // no default texture
		boundingBox = new Rectangle();
	}
	
	// updates the obj and its children's position
	public void update()
	{
		//System.out.printf("%f %f\n", getGlobalX(), getGlobalY());
		for(int i = 0; i < actions.size(); i++)
		{
			actions.get(i).performAction();
		}
		updateChildren();
		updateThis();
	}
	
	// update children's position
	protected void updateChildren()
	{
		for(int i = 0; i < children.size(); i++)
		{
			children.get(i).update();
		}
	}
	
	// decides how to update the position of the object
	protected void updateThis()
	{
		
	}
	
	// draws this object and its children
	public void draw()
	{
		if(texture != null)
		{
			// need to think about this.
			float x = getGlobalX();
			float y = getGlobalY();			
			texture.SetPos(x/640, y/480);
			//texture.SetRotation((float)Math.atan2(y, x)); // not right. ill do it later.
			texture.SetScale(1, 1); // for now...
		}
		drawThis();
		drawChildren();
	}
	// draw children
	protected void drawChildren()
	{
		for(int i =0; i < children.size(); i++)
		{
			children.get(i).draw();
		}
	}
	
	// draws this object's texture. (not rly sure if im doing it correctly)
	protected void drawThis()
	{
		if(texture != null)
		{
			texture.Draw();
		}
	}

	// checks if obj is a root node
	public boolean isRootNode()
	{
		if(parent == null)
		{
			return true;
		}
		return false;
	}
	
	// adds this to another object
	public void setParent(GameObject parent)
	{
		this.parent = parent;
	}
	public GameObject getParent()
	{
		return this.parent;
	}
	// removes this from the parent
	public void removeParent()
	{
		parent.removeChild(this);
		parent = null;
	}
	// add child to the object
	public void addChild(GameObject child)
	{
		children.add(child);
		child.setParent(this);
	}
	// remove object from child
	public void removeChild(GameObject child)
	{
		child.removeParent();
		children.remove(child);
	}
	
	// sets x location relative to the parent
	public void setX(float x)
	{
		transform.m20 = x;
	}
	// sets y location relative to the parent
	public void setY(float y)
	{
		transform.m21 = y;
	}
	// sets the rotation about the origin of the object relative to parent
	public void setRotation(float rot)
	{
		Matrix2f rotMat = new Matrix2f();
		rotMat.m00 = (float)Math.cos(rot);
		rotMat.m01 = (float)Math.sin(rot);
		rotMat.m10 = (float)Math.sin(rot)*-1;
		rotMat.m11 = (float)Math.cos(rot);
		setRotationMatrix(rotMat);
		
	}
	// sets rotation matrix of the object relative to parent
	public void setRotationMatrix(Matrix2f rotMat)
	{
		this.transform.m00 = rotMat.m00;
		this.transform.m01 = rotMat.m01;
		this.transform.m10 = rotMat.m10;
		this.transform.m11 = rotMat.m11;
	}
	// sets 3by3 transform matrix relative to parent
	public void setTransform(Matrix3f transform)
	{
		this.transform = transform;
	}
	// get x relative to parent
	public float getLocalX()
	{
		return transform.m20;
	}
	// get y relative to parent
	public float getLocalY()
	{
		return transform.m21;
	}
	// get local rotation matrix (relative to parent)
	public Matrix2f getLocalRotationMatrix()
	{
		Matrix2f rotMat = new Matrix2f();
		rotMat.m00 = transform.m00;
		rotMat.m01 = transform.m01;
		rotMat.m10 = transform.m10;
		rotMat.m11 = transform.m11;
		return rotMat;
	}
	// get local transform (relative to parent)
	public Matrix3f getLocalTransform()
	{	
		Matrix3f clonedTransform = new Matrix3f();
		clonedTransform.load(transform);
		return clonedTransform;
	}
	
	// gets rotation relative to the 'Universe' origin
	public Matrix2f getGlobalRotationMatrix()
	{
		Matrix2f rotMat = getLocalRotationMatrix();
		
		if(parent != null)
		{
			Matrix2f.mul(parent.getGlobalRotationMatrix(), rotMat, rotMat);
		}
		
		return rotMat;
	}
	// gets location relative to the 'Universe' origin
	public Matrix3f getGlobalTransform()
	{
		Matrix3f glbTrans = getLocalTransform();
		if(parent != null)
		{
			Matrix3f.mul(parent.getGlobalTransform(), glbTrans, glbTrans);
		}
		
		
		return glbTrans;
	}
	
	// gets x relative to 'Universe'
	public float getGlobalX()
	{
		return getGlobalTransform().m20;
	}
	// gets y relatice to 'Universe'
	public float getGlobalY()
	{
		return getGlobalTransform().m21;
	}
	
	// rotate object by an angle
	public void rotate(float angle)
	{
		Matrix2f rotMat = new Matrix2f();
		rotMat.m00 = (float)Math.cos(angle);
		rotMat.m01 = (float)Math.sin(angle);
		rotMat.m10 = (float)Math.sin(angle)*-1;
		rotMat.m11 = (float)Math.cos(angle);
		Matrix2f.mul(rotMat, getLocalRotationMatrix(), rotMat);
		setRotationMatrix(rotMat);
		normalizeRotationMatrix();
	}
	// translate object by (x,y)
	public void translate(float x, float y)
	{
		transform.m20 += x;
		transform.m21 += y;
	}
	
	// normalizes rotation matrix.  This is important, because is prevents 
	// round-off errors in the rotation matrix from compounding.
	public void normalizeRotationMatrix()
	{
		Matrix2f rotMat = getLocalRotationMatrix();
		float determinate = rotMat.determinant();
		rotMat.m00 /= determinate;
		rotMat.m01 /= determinate;
		rotMat.m10 /= determinate;
		rotMat.m11 /= determinate;
		setRotationMatrix(rotMat);
	}
	public void setBoundingBox(Rectangle box)
	{
		boundingBox = box;
	}
	public Rectangle getBoundingBox()
	{
		return boundingBox;
	}
	// checks if this object is a child of obj
	public boolean isChildOf(GameObject obj)
	{
		if(obj == parent)
		{
			return true;
		}
		else if(parent == null)
		{
			return false;
		}
		return parent.isChildOf(obj); // is obj parent of parent?
	}
	
	// this will be called in the update loop if this is colliding with obj
	public void processCollision(GameObject obj)
	{
		if(!isChildOf(obj)) // if object is colliding with a parent of itself than do nothing
		{
			processChildrenCollisions(obj);
			processThisCollision(obj);
		}
	}
	// process all of the children's collisions
	protected void processChildrenCollisions(GameObject obj)
	{
		for(int i = 0; i < children.size(); i++)
		{
			children.get(i).processCollision(obj);
		}
	}
	// process this object's collision 
	protected void processThisCollision(GameObject obj)
	{
		
	}
	// used to check if an object is colliding with another object
	// an object is colliding with another object if any of its children's boundingBox 
	// intersects with any of obj's children's bounding box
	protected boolean isCollidingWith(GameObject obj)
	{
		
		return false;
	}
	public void addAction(Action action)
	{
		actions.add(action);
	}
	public void removeAction(Action action)
	{
		actions.remove(action);
	}
	
	
	Rectangle boundingBox;
	
	// transformation matrix for 2d transformations
	Matrix3f transform; // location/orientation/scale of obj relative to parent
	
	
	// I think this is used to draw objects...
	ITexture texture;
	
	// parent of this object
	GameObject parent;
	
	// children of this object
	ArrayList<Action> actions;
	ArrayList<GameObject> children;
	
}
