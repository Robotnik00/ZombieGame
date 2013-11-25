package GameObjects;

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
				GameObject[] collisions = CollisionDetection.getCollisions(rootNode, universe.getHandle());
				for(int i = 0; i < collisions.length; i++)
				{
					collisions[i].getEntity().destroy();
				}
				
				
			}
			
		};
		rootNode.addAction(killEntity);
		rootNode.setDrawingInterface(new DrawBoundingBox(rootNode, universe.getTextureEngine()));
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
