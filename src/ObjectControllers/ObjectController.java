package ObjectControllers;

import Engine.IGameEngine;
import GameObjects.PhysicsObject;

// an abstract class for controlling PhysicsObjects
public abstract class ObjectController 
{
	public ObjectController(PhysicsObject obj, IGameEngine eng)
	{
		this.obj = obj;
		this.eng = eng;
	}
	
	// generally this will controll objects by applying external forces to them
	public abstract void controlObject();
	
	PhysicsObject obj; 
	IGameEngine eng; 
}
