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
		
		createObject(universe);
		
	}
	/**
	 *  initializes the enitiy
	 * @param level
	 */
	public abstract void createObject(Universe universe);
	
	/**
	 *  returns root node of the object
	 * @return
	 */
	public GameObject getRootNode()
	{
		return rootNode;
	}
	
	
	/**
	 * free up resources used by entity
	 */
	public abstract void destroy();
	
	Universe universe;
	GameObject rootNode;
}
