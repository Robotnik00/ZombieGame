package GameObjects;

/**
 * an Entity is anything in the game that gets drawn/update by the game engine.
 * 
 *
 */
public abstract class Entity 
{	
	public Entity(Universe universe)
	{
		this.universe = universe;
		rootNode = new GameObject();
		rootNode.setEntity(this);
		createObject(universe);
	}
	/**
	 *  initializes the enitiy
	 * @param level
	 */
	public abstract void createObject(Universe universe);
	
	public void damage(float dp)
	{
		hp -= dp;
		if(hp < 0)
		{
			destroy();
		}
	}
	
	
	public void setStartingLoc(float x, float y)
	{
		startingX = x;
		startingY = y;
		rootNode.setLocalX(x);
		rootNode.setLocalY(y);
	}
	/**
	 *  returns root node of the object
	 * @return
	 */
	public GameObject getRootNode()
	{
		return rootNode;
	}
	
	public Universe getUniverse()
	{
		return universe;
	}
	
	public float getHp()
	{
		return hp;
	}
	public float getMaxHp()
	{
		return maxHp;
	}
	
	/**
	 * free up resources used by entity
	 */
	public abstract void destroy();
	
	Universe universe;
	GameObject rootNode;
	
	float startingX;
	float startingY;
	
	float hp = 1;
	float maxHp = 1;
}
