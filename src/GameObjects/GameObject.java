
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
import Geometry.AABB;
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
		transform.setIdentity();
		interpolator = new Matrix4f();
		prevTransform = new Matrix4f();
		glbTransform = new Matrix4f();
		glbInterpolator = new Matrix4f();
		
		velocity = new Vector2f();
		
		deltaX = new Vector2f();
		rotationalVelocity = 0;
		actions = new ArrayList<Action>();

		deltaScale = new Vector3f();
		deltaScale.x = 1;
		deltaScale.y = 1;
		
		parent = null;
		children = new ArrayList<GameObject>();
		drawing = null;
		proxemity = null;
		
		boundingBox = null;
		
		Matrix4f initscreen = new Matrix4f();
		initscreen.setIdentity();
		screen.transform(initscreen);
		
	}
	/**
	 * Synchronous callback from GameEngine
	 * @param deltaT period of update callback
	 * 
	 * 
	 */
	public void update(float deltaT)
	{
		prevTransform.load(transform);
		if(boundingBox != null)
		{
			boundingBox.transform(getGlobalTransform());
		}
		if(proxemity != null)
		{
			proxemity.transform(getGlobalTransform());
		}

		transform.scale(deltaScale);
		transform.rotate(deltaRot, zaxis);
		transform.translate(deltaX);
		
		deltaX.x = velocity.x;
		deltaX.y = velocity.y;
		deltaX.scale(deltaT);
		deltaRot = rotationalVelocity*deltaT;
		deltaScale.x = 1;
		deltaScale.y = 1;
		
		
		//System.out.printf("%f %f\n", getGlobalX(), getGlobalY());
		for(int i = 0; i < actions.size(); i++)
		{
			actions.get(i).performAction();
		}

		updateChildren(deltaT);
		updateThis(deltaT);
		glbTransformCalculated = false;
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
		
		interpolator.load(prevTransform);
		intDelta.x = deltaX.x;
		intDelta.y = deltaX.y;
		intDelta.scale(delta);
		

		interpolator.scale(new Vector3f(delta*deltaScale.x + 1 - delta, delta*deltaScale.y + 1 - delta, 0));
		interpolator.rotate(deltaRot*delta, zaxis);
		interpolator.translate(intDelta);
		if(proxemity == null || proxemity.intersects(screen))
		{
			if(drawing != null)
			{
	
				drawing.draw(getGlobalInterpolator()); 
	
			}
			drawChildren(delta);
		}
	}
	// draw children
	protected void drawChildren(float delta)
	{
		for(int i =0; i < children.size(); i++)
		{
			children.get(i).draw(delta);
		}
	}
	public Vector2f getDeltaX()
	{
		return deltaX;
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
		children.remove(child);
		//child.removeParent();
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
	public void setLocalX(float x)
	{
		transform.m30 = x;
		prevTransform.m30 = x;
		if(boundingBox != null)
		{
			boundingBox.transform(getGlobalTransform());
		}
	}
	public void setLocalY(float y)
	{
		transform.m31 = y;
		prevTransform.m31 = y;
		if(boundingBox != null)
		{
			boundingBox.transform(getGlobalTransform());
		}
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

	public float getXWrt(GameObject wrt)
	{
		return getTransformWrt(wrt).m30;
	}
	public float getYWrt(GameObject wrt)
	{
		return getTransformWrt(wrt).m31;
	}
	

	// get local transform (relative to parent)
	public Matrix4f getLocalTransform()
	{	
		Matrix4f clonedTransform = new Matrix4f();
		clonedTransform.load(transform);
		return clonedTransform;
	}
	public float getLocalOrientation()
	{
		Vector4f xaxis = new Vector4f(1,0,0,0);
		Matrix4f.transform(transform, xaxis, xaxis);
		return (float)Math.atan2(xaxis.y, xaxis.x);
	}

	/**
	 *
	 * 
	 * calculates the transform of the object with respect to the root node(universe)
	 * used to determine 'Actual' coordinates for drawing and collision detection
	 */
	public Matrix4f getGlobalTransform()
	{
		if(!glbTransformCalculated)
		{
			//Matrix4f glbTrans = new Matrix4f();
			glbTransform.load(transform);
			if(parent != null)
			{
				Matrix4f.mul(parent.getGlobalTransform(), glbTransform, glbTransform);
			}
		}
		//glbTransformCalculated = true;
		return glbTransform;
	}
	public Matrix4f getGlobalInterpolator()
	{
		if(!glbInterpolatorCalculated)
		{
			glbInterpolator.load(interpolator);
			if(parent != null)
			{
				Matrix4f.mul(parent.getGlobalInterpolator(), glbInterpolator, glbInterpolator);
			}
		}
		//glbInterpolatorCalculated = true;
		return glbInterpolator;
	}
	
	// gets x relative to 'Universe'
	public float getGlobalX()
	{
		return getGlobalTransform().m30;
	}
	// gets y relatice to 'Universe'
	public float getGlobalY()
	{
		return getGlobalTransform().m31;
	}

	/**
	 *
	 * 
	 * rotates object by 'angle'
	 * 
	 */	
	public void rotate(float angle)
	{
		deltaRot += angle;
	}
	public void translate(float x, float y)
	{
		deltaX.x += x;
		deltaX.y += y;
	}

	/**
	 *
	 * 
	 * sets the BoundingBox of the object for collision detections
	 * 
	 */
	public void setBoundingBox(AABB box)
	{
		box.transform(getGlobalTransform());
		boundingBox = box;
	}
	public AABB getBoundingBox()
	{
		if(boundingBox!=null)
		{
			boundingBox.transform(getGlobalTransform());
		}
		return boundingBox;
	}

	/**
	 *
	 * 
	 * sets proximity bounds for optimizing collisions
	 * 
	 */
	public void setProxemityBounds(AABB bounds)
	{
		this.proxemity = bounds;
	}
	public AABB getProxemityBounds()
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
	
	public Matrix4f getTransformWrt(GameObject wrt)
	{
		Matrix4f transformwrt = new Matrix4f();
		transformwrt.load(transform);
		if(parent != wrt)
		{
			Matrix4f.mul(parent.getTransformWrt(wrt), transformwrt, transformwrt);
		}
		
		return transformwrt;
		
		
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
		Matrix4f glbtranthis = getGlobalTransform();
		Matrix4f glbtranobj  = obj.getGlobalTransform();
		Matrix4f glbtranwrt  = wrt.getGlobalTransform();
		
		
		Matrix4f transform = new Matrix4f();
		
		glbtranthis.invert();
		
		Matrix4f.mul(glbtranthis, glbtranobj, transform);
		
		transform.invert();
		
		Matrix4f.mul(transform, glbtranwrt, transform);
				
		return transform;
	}
	

	/**
	 *
	 * sets the velocity of this object with respect to parent
	 */
	public void setTranslationalVelocity(Vector2f velocity)
	{
		//this.velocity = velocity;
		this.velocity.x = velocity.x;
		this.velocity.y = velocity.y;
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
		Matrix4f transform = parent.getGlobalTransform();
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
		return new Matrix4f(interpolator);
	}
	/**
	 *
	 * scales the object's coordinate space. Note this will consequently scale all of its children
	 */
	public void scale(float x, float y)
	{
//		Vector3f scale = new Vector3f(x,y,0);
//		transform.scale(scale);
//		
		
		deltaScale.x *= x;
		deltaScale.y *= y;
	}
	/**
	 *
	 * sets the interface that is used to draw this object
	 */
	public void setDrawingInterface(DrawObject idrawing)
	{
		drawing = idrawing;
	}
	public float getOrientation()
	{
		Vector4f xaxis = new Vector4f(1,0,0,0);
		
		
		Matrix4f.transform(transform, xaxis, xaxis);
		
		return (float)Math.atan2(xaxis.y, xaxis.x);
	}
	public float getOrientationWrt(GameObject obj)
	{
		Matrix4f transform = getTransformWrt(obj);
		Vector4f xaxis = new Vector4f(1,0,0,0);
		Matrix4f.transform(transform, xaxis, xaxis);
		
		return (float)Math.atan2(xaxis.y, xaxis.x);
		
	}
	/**
	 *
	 * gets the orientation with respect to the x axis
	 */
	public float getGlobalOrientation()
	{
		Vector4f xaxis = new Vector4f(1,0,0,0);
		Matrix4f tranform = getGlobalTransform();
		
		
		Matrix4f.transform(tranform, xaxis, xaxis);
		
		return (float)Math.atan2(xaxis.y, xaxis.x);
	}
	public Matrix4f getGlobalInterpolator(Matrix4f interpolator)
	{
		Matrix4f glbInt = new Matrix4f(interpolator);
		if(parent == null)
		{
			return glbInt;
		}
		Matrix4f.mul(parent.getGlobalInterpolator(glbInt), glbInt, glbInt);
		return glbInt;
	}

	public void setMass(float mass)
	{
		this.mass = mass;
	}
	public void setMomentOfInertia(float moment)
	{
		momentOfInertia = moment;
	}
	public float getMass()
	{
		return mass;
	}
	public float getMomentOfInertia()
	{
		return momentOfInertia;
	}
	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}
	public Entity getEntity()
	{
		return this.entity;
	}
	
	
	AABB proxemity; // if no objects in this area than don't process any children unless it is null
	AABB boundingBox; // if object in this area notify a collision
	
	// transformation matrix for 2d transformations
	Matrix4f transform; // location/orientation/scale of obj relative to parent
	Matrix4f prevTransform;
	Matrix4f interpolator; // used for interpolating between frames
	Vector2f intDelta = new Vector2f();
	
	
	// global transform
	Matrix4f glbTransform;
	Matrix4f glbInterpolator;
	
	protected boolean glbTransformCalculated = false;
	protected boolean glbInterpolatorCalculated = false;
	
	
	
	
	DrawObject drawing;
	
	
	// parent of this object
	GameObject parent;
	
	// children of this object
	ArrayList<Action> actions;
	ArrayList<GameObject> children;
	
	Vector2f velocity;
	Vector2f deltaX;
	Vector3f deltaScale;
	float rotationalVelocity;
	float deltaRot = 0;
	
	float mass = 1;
	float momentOfInertia = 1;
	
	boolean collidable = false;
	
	static final Vector3f zaxis = new Vector3f(0,0,1);
	static final AABB screen = new AABB(2,2);
	
	Entity entity = null;
}
