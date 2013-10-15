package ObjectControllers;

import GameObjects.PhysicsObject;
import GameStates.IGameState;

// an abstract class for controlling PhysicsObjects
public abstract class ObjectController 
{
	public ObjectController(PhysicsObject obj, IGameState eng)
	{
		this.obj = obj;
		this.eng = eng;
	}
	
	// generally this will controll objects by applying external forces to them
	public abstract void controlObject();
	
	PhysicsObject obj; 
	IGameState eng; 
}
