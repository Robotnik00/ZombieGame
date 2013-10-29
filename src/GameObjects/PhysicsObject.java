// not using anymore. use Physics in Package Actions

package GameObjects;


import org.lwjgl.util.vector.Vector2f;

/**
 * Example of how to extend GameObject and add stuff to its callback
 * 
 *  
 */
@Deprecated // love doing this
public class PhysicsObject extends GameObject
{
	public PhysicsObject()
	{
		super();
		translationalVelocity = new Vector2f();
		rotationalVelocity = 0;
		appliedForce = new Vector2f();
		appliedTorque = 0;
		
	}
	
	// updates the object's position based on velocity and applied force.
	/*@Override
	protected void updateThis(float deltaT)
	{
		// rotational stuff
		float frictionTorque = rotationalVelocity * rotationalFrictionConstant;
		float effectiveTorque = appliedTorque - frictionTorque;
		float deltaRotVel = effectiveTorque / momentOfInertia * deltaT;
		rotationalVelocity += deltaRotVel;
		float deltaAngle = rotationalVelocity * deltaT;
		rotate(deltaAngle);
		//
		
		// translational stuff
		Vector2f frictionForce = new Vector2f(translationalVelocity);
		frictionForce.scale(frictionConstant);
		Vector2f effectiveForce = new Vector2f();
		
		Vector2f.sub(appliedForce, frictionForce, effectiveForce);
		
		Vector2f deltaV = new Vector2f(effectiveForce);
		deltaV.scale(deltaT/mass);
		Vector2f.add(deltaV, translationalVelocity, translationalVelocity);
		Vector2f deltaX = new Vector2f(translationalVelocity);
		deltaX.scale(deltaT);
		this.translate(deltaX.x, deltaX.y);
		//
	}*/
	
	public void setAppliedForce(Vector2f force)
	{
		this.appliedForce = force;
	}
	public void applyForce(Vector2f force)
	{
		Vector2f.add(force, appliedForce, appliedForce);
	}
	public void removeForce(Vector2f force)
	{
		Vector2f.sub(appliedForce, force, appliedForce);
	}
	public void setAppliedTorque(float torque)
	{
		appliedTorque = torque;
	}
	public void applyTorque(float torque)
	{
		appliedTorque += torque;
	}
	public void removeTorque(float torque)
	{
		appliedTorque -= torque;
	}
	public void setTranslationalVelocity(Vector2f velocity)
	{
		this.translationalVelocity = velocity;
	}
	public void setRotationalVelocity(float rotVel)
	{
		this.rotationalVelocity = rotVel;
	}
	
	public float getRotationalVelocity()
	{
		return rotationalVelocity;
	}
	public Vector2f getTranslationalVelocirt()
	{
		return translationalVelocity;
	}
	public Vector2f getAppliedForce()
	{
		return appliedForce;
	}
	public float getAppliedTorque()
	{
		return appliedTorque;
	}
	
	
	Vector2f translationalVelocity;
	Vector2f appliedForce;
	
	float appliedTorque;
	float rotationalVelocity;
	
	float mass = 1;
	float momentOfInertia = 1;
	
	float frictionConstant = 1;
	float rotationalFrictionConstant = 1;
}
