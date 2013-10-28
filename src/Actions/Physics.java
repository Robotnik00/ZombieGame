package Actions;

import org.lwjgl.util.vector.Vector2f;

import Engine.GameEngine;
import Engine.IGameEngine;
import GameObjects.GameObject;
import GameStates.IGameState;

// makes a GameObject behave like a PhysicsObject. This is a better way to do it.
public class Physics implements Action
{
	// takes in a deltaT so that if the update rate changes objects will still move at same speed
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
		//if(eng instanceof GameEngine) // blah
		//{
		//		float tdeltaT =(float)((GameEngine)this.eng).GetFrameRate();
		//		if(tdeltaT > 0)
		//		{
		//			deltaT = 1/tdeltaT;
		//		}
		//}
		
		
		float frictionTorque = obj.getRotationalVelocity() * rotationalFrictionConstant;
		float effectiveTorque = appliedTorque - frictionTorque;
		float deltaRotVel = effectiveTorque / momentOfInertia * deltaT;
		obj.setRotationalVelocity(obj.getRotationalVelocity() + deltaRotVel);
		//float deltaAngle = obj.getRotationalVelocity() * deltaT;
		//obj.addRotationalVelocity(deltaAngle);
		//obj.rotate(deltaAngle);
		
		
		// translational stuff
		Vector2f frictionForce = new Vector2f(obj.getTranslationalVelocity());
		frictionForce.scale(frictionConstant);
		Vector2f effectiveForce = new Vector2f();
		
		Vector2f.sub(appliedForce, frictionForce, effectiveForce);
		
		Vector2f deltaV = new Vector2f(effectiveForce);
		deltaV.scale(deltaT/mass);
		Vector2f.add(deltaV, obj.getTranslationalVelocity(), obj.getTranslationalVelocity());
		
		//Vector2f deltaX = new Vector2f(obj.getTranslationalVelocity());
		//deltaX.scale(deltaT);
		//obj.translate(deltaX.x, deltaX.y);
		
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

	public void setMass(float mass)
	{
		this.mass = mass;
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
	public float getMass()
	{
		return mass;
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
	
	float appliedTorque;
	
	float mass = 1f;
	float momentOfInertia = 1;
	
	float frictionConstant = 1;
	float rotationalFrictionConstant = 1;
	GameObject obj;
	
	float deltaT;
	
	IGameEngine eng;
}
