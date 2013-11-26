package GameObjects;

//import Utility.Collision;
import Utility.Collision;
import Utility.CollisionDetection;
import Actions.Action;
import Drawing.DrawBoundingBox;
import Geometry.AABB;

public class KillPlayer extends Entity
{

	public KillPlayer(Universe universe) 
	{
		super(universe);
	}

	@Override
	public void createObject(final Universe universe) 
	{
		Action killEntity = new Action()
		{

			@Override
			public void performAction() 
			{
				Collision[] collisions = CollisionDetection.getCollisions(rootNode, universe.getHandle());
				for(int i = 0; i < collisions.length; i++)
				{
					if(collisions[i].collidingObject.getEntity() instanceof Player)
						collisions[i].collidingObject.getEntity().destroy();
				}
				
				
			}
			
		};
		rootNode.addAction(killEntity);
	}
	public void setBounds(AABB bounds)
	{
		rootNode.setBoundingBox(bounds);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	
}
