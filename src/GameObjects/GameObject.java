
package GameObjects;

import java.util.ArrayList;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

//import com.sun.corba.se.impl.activation.ORBD;

import Actions.Action;
import Drawing.DrawObject;
import TextureEngine.ITexture;




/**
 * other objects can use these as building blocks for more complicated geometry
 * basically a single GameObject is a node in a 'SceneGraph' http://en.wikipedia.org/wiki/Scene_graph
 * 
 */

public class GameObject 
{
	public GameObject()
	{
		// set up default transformation matrix (loc: 0,0 rot: 0)
		transform = new Matrix4f();
		interpolator = new Matrix4f();
		transform.setIdentity();
		
		velocity = new Vector2f();
		rotationalVelocity = 0;
		actions = new ArrayList<Action>();
		
		
		parent = null;
		children = new ArrayList<GameObject>();
		texture = null; // no default texture
		drawing = null;
		proxemity = null;
		
		boundingBox = null;
		
	}
	/**
	 * Synchronous callback from GameEngine
	 * @param deltaT period of update callback
	 * 
	 * 
	 */
	public void update(float deltaT)
	{
		//boundingBox.transform(getGlobalTransform());
		interpolator.load(transform);
		float x = getGlobalX();
		float y = getGlobalY();
		
		if(boundingBox != null)
		{
			boundingBox.setLocation((int)x, (int)y);
		}
		if(proxemity != null)
		{
			proxemity.setLocation((int)x, (int)y);
		}
		
		//System.out.printf("%f %f\n", getGlobalX(), getGlobalY());
		for(int i = 0; i < actions.size(); i++)
		{
			actions.get(i).performAction();
		}

		
		Vector2f deltaX = new Vector2f(velocity);
		deltaX.scale(deltaT);
		
		transform.translate(deltaX);
		
		rotate(rotationalVelocity*deltaT);
		
		updateChildren(deltaT);
		updateThis(deltaT);
	}
	
	// update children's position
	protected void updateChildren(float deltaT)
	{
		for(int i = 0; i < children.size(); i++)
		{
			children.get(i).update(deltaT);
		}
	}
	
	// decides how to update the position of the object
	protected void updateThis(float deltaT)
	{
	}
	/**
	 *
	 * 
	 * Asynchronous callback from GameEngine 
	 * Draws the object based on Drawing interface
	 */
	// draws this object and its children
	public void draw(float delta)
	{
		if(drawing != null)
		{
			// express time in same units as Physics(time seconds).
			float deltaT = delta * (1/25f); // this should prob be done in GameEngine
			
			/*Matrix4f glbInterpolator = getGlobalTransform(this.interpolator);
			
			Vector4f glbVelocity = getGlobalVelocity();
			glbVelocity.scale(deltaT);
			
			Vector2f deltaX = new Vector2f();
			deltaX.x = glbVelocity.x;
			deltaX.y = glbVelocity.y;
			
			glbInterpolator.translate(deltaX);
			
			texture.Draw(glbInterpolator);*/
			drawing.draw(deltaT);
			
		}
		drawChildren(delta);
	}
	// draw children
	protected void drawChildren(float delta)
	{
		for(int i =0; i < children.size(); i++)
		{
			children.get(i).draw(delta);
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


	/**
	 *
	 * 
	 * sets the local 4x4 matrix used for storing location/scale/rotation
	 * 
	 */
	public void setTransform(Matrix4f transform)
	{
		this.transform = transform;
	}
	// get x relative to parent
	public float getLocalX()
	{
		return transform.m30;
	}
	// get y relative to parent
	public float getLocalY()
	{
		return transform.m31;
	}

	// get local transform (relative to parent)
	public Matrix4f getLocalTransform()
	{	
		Matrix4f clonedTransform = new Matrix4f();
		clonedTransform.load(transform);
		return clonedTransform;
	}
	

	/**
	 *
	 * 
	 * calculates the transform of the object with respect to the root node(universe)
	 * used to determine 'Actual' coordinates for drawing and collision detection
	 */
	public Matrix4f getGlobalTransform(Matrix4f transform)
	{
		Matrix4f glbTrans = new Matrix4f();
		glbTrans.load(transform);
		if(parent != null)
		{
			Matrix4f.mul(parent.getGlobalTransform(parent.transform), glbTrans, glbTrans);
		}
		
		
		return glbTrans;
	}
	
	// gets x relative to 'Universe'
	public float getGlobalX()
	{
		return getGlobalTransform(transform).m30;
	}
	// gets y relatice to 'Universe'
	public float getGlobalY()
	{
		return getGlobalTransform(transform).m31;
	}

	/**
	 *
	 * 
	 * rotates object by 'angle'
	 * 
	 */	
	public void rotate(float angle)
	{
		transform.rotate(angle, new Vector3f(0,0,1));
		//normalizeRotationMatrix();
	}
	public void translate(float x, float y)
	{
		transform.m30 += x;
		transform.m31 += y;
	}

	/**
	 *
	 * 
	 * sets the BoundingBox of the object for collision detections
	 * 
	 */
	public void setBoundingBox(Rectangle box)
	{
		boundingBox = box;
	}
	public Rectangle getBoundingBox()
	{
		return boundingBox;
	}

	/**
	 *
	 * 
	 * sets proximity bounds for optimizing collisions
	 * 
	 */
	public void setProxemityBounds(Rectangle bounds)
	{
		this.proxemity = bounds;
	}
	public Rectangle getProxemityBounds()
	{
		return proxemity;
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
	

	/**
	 *
	 * 
	 * adds an action to the objects update callback
	 * 
	 */
	public void addAction(Action action)
	{
		actions.add(action);
	}
	public void removeAction(Action action)
	{
		actions.remove(action);
	}
	
	public ArrayList<GameObject> getChildren()
	{
		return children;
	}

	/**
	 *
	 * 
	 * set this and other objects can collide with this object
	 * 
	 */
	public void setCollidable(boolean collidable)
	{
		this.collidable = collidable;
	}
	public boolean getCollidable()
	{
		return collidable;
	}
	/**
	 *
	 * 
	 * gets transform that if multiplied by this transform will result in obj's transform
	 * relative to wrt's coordinate space.
	 * basicly difference in orientation and location of this object to obj with respect to wrt
	 * 
	 * 
	 */
	public Matrix4f getRelativeTransform(GameObject obj, GameObject wrt)
	{
		Matrix4f glbtranthis = getGlobalTransform(transform);
		Matrix4f glbtranobj  = obj.getGlobalTransform(transform);
		Matrix4f glbtranwrt  = wrt.getGlobalTransform(transform);
		
		
		Matrix4f transform = new Matrix4f();
		
		glbtranthis.invert();
		
		Matrix4f.mul(glbtranthis, glbtranobj, transform);
		
		transform.invert();
		
		Matrix4f.mul(transform, glbtranwrt, transform);
				
		return transform;
	}
	
	/**
	 *
	 * sets the texture for this object for drawing
	 */
	@Deprecated
	public void setTexture(ITexture tex)
	{
		this.texture = tex;
	}
	public ITexture getTexture()
	{
		return this.texture;
	}
	/**
	 *
	 * sets the velocity of this object with respect to parent
	 */
	public void setTranslationalVelocity(Vector2f velocity)
	{
		this.velocity = velocity;
	}
	/**
	 *
	 * sets rotational velocity of this object with respect to parent
	 */
	public void setRotationalVelocity(float rotationalVelocity)
	{
		this.rotationalVelocity = rotationalVelocity;
	}
	public Vector2f getTranslationalVelocity()
	{
		return velocity;
	}
	public float getRotationalVelocity()
	{
		return rotationalVelocity;
	}
	/**
	 * gets the objects velocity with respect to the world
	 * used for interpolating between frames
	 */
	public Vector4f getGlobalVelocity()
	{
		Vector4f glbVelocity = new Vector4f();
		glbVelocity.x = velocity.x;
		glbVelocity.y = velocity.y;
		
		if(parent == null)
		{
			return glbVelocity;
		}
		Matrix4f transform = parent.getGlobalTransform(parent.transform);
		transform.m30 = 0;
		transform.m31 = 0;
		Matrix4f.transform(transform, glbVelocity, glbVelocity);
		
		Vector4f.add(parent.getGlobalVelocity(), glbVelocity, glbVelocity);
		
		return glbVelocity;
	}
	/**
	 *
	 * copy of the transform matrix that can be manipulated to interpolate between frames
	 */
	public Matrix4f getInterpolator()
	{
		return interpolator;
	}
	/**
	 *
	 * scales the object's coordinate space. Note this will consequently scale all of its children
	 */
	public void scale(float x, float y)
	{
		Matrix4f scale = new Matrix4f();
		scale.scale(new Vector3f(x,y,1));
		Matrix4f.mul(scale, transform, transform);
	}
	/**
	 *
	 * sets the interface that is used to draw this object
	 */
	public void setDrawingInterface(DrawObject idrawing)
	{
		drawing = idrawing;
	}
	/**
	 *
	 * gets the orientation with respect to the x axis
	 */
	public float getGlobalOrientation()
	{
		Vector4f xaxis = new Vector4f(1,0,0,0);
		Matrix4f tranform = getGlobalTransform(this.transform);
		
		
		Matrix4f.transform(tranform, xaxis, xaxis);
		
		return (float)Math.atan2(xaxis.y, xaxis.x);
	}
	
	
	
	Rectangle proxemity; // if no objects in this area than don't process any children unless it is null
	Rectangle boundingBox; // if object in this area notify a collision
	
	// transformation matrix for 2d transformations
	Matrix4f transform; // location/orientation/scale of obj relative to parent
	Matrix4f interpolator; // used for interpolating between frames
	
	// I think this is used to draw objects...
	ITexture texture;
	
	DrawObject drawing;
	
	
	// parent of this object
	GameObject parent;
	
	// children of this object
	ArrayList<Action> actions;
	ArrayList<GameObject> children;
	
	Vector2f velocity;
	float rotationalVelocity;
			
	boolean collidable = false;
}
