package Actions;

import org.lwjgl.util.vector.Vector2f;

import Engine.GameEngine;
import Engine.IGameEngine;
import GameObjects.GameObject;
import GameStates.IGameState;
/**
*
* gives an object Physics like behavior
*/

// used to make objects accelerate and decelerate 
public class Physics implements Action
{
	public Physics(GameObject obj, IGameEngine eng)
	{
		this.obj = obj;
		appliedForce = new Vector2f();
		appliedTorque = 0;

		this.eng = eng;
		deltaT = (float)1/eng.GetTickFrequency(); 
	}
	@Override
	public void performAction() 
	{		

		obj.setRotationalVelocity(obj.getRotationalVelocity() + deltaRotVel);
		float frictionTorque = obj.getRotationalVelocity() * rotationalFrictionConstant;
		float effectiveTorque = appliedTorque - frictionTorque;
		deltaRotVel = effectiveTorque / obj.getMomentOfInertia() * deltaT;
		
		// translational stuff
		Vector2f.add(deltaV, obj.getTranslationalVelocity(), obj.getTranslationalVelocity());
		Vector2f frictionForce = new Vector2f(obj.getTranslationalVelocity());
		frictionForce.scale(frictionConstant);
		Vector2f effectiveForce = new Vector2f();
		
		Vector2f.sub(appliedForce, frictionForce, effectiveForce);
		
		deltaV = new Vector2f(effectiveForce);
		deltaV.scale(deltaT/obj.getMass());
		
		
	}
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

	public void setFrictionConstant(float friction)
	{
		this.frictionConstant = friction;
	}
	public void setRotationalFrictionConstant(float friction)
	{
		this.rotationalFrictionConstant = friction;
	}
	public float getRotationalFrictionConstant()
	{
		return rotationalFrictionConstant;
	}
	public float getFrictionConstant()
	{
		return frictionConstant;
	}
	public Vector2f getAppliedForce()
	{
		return appliedForce;
	}
	public float getAppliedTorque()
	{
		return appliedTorque;
	}
	Vector2f appliedForce;
	Vector2f deltaV = new Vector2f();
	
	float appliedTorque;
	float deltaRotVel = 0;
	
	float frictionConstant = 1;
	float rotationalFrictionConstant = 1;
	GameObject obj;
	
	float deltaT;
	
	IGameEngine eng;
}
