package Utility;

import org.lwjgl.util.vector.Vector2f;

import GameObjects.GameObject;

public class Collision
{
	public Collision(GameObject colObject, Vector2f escapeVector)
	{
		this.collidingObject = colObject;
		this.escapeVector = escapeVector;
	}
	public GameObject collidingObject;
	public Vector2f escapeVector;
}
