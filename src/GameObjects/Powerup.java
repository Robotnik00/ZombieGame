package GameObjects;

import Actions.Action;
import Geometry.AABB;

public abstract class Powerup extends Entity
{

	public Powerup(Universe u, Player player)
	{
		super(u);
		
		this.itsplayer = player;
		
		
		
		Action givePlayerPowerup = new Action()
		{

			@Override
			public void performAction() 
			{
				if(rootNode.getBoundingBox() != null && itsplayer.getRootNode().getBoundingBox().intersects(rootNode.getBoundingBox()))
				{
					System.out.printf("hey\n");
					

					universe.getHandle().removeChild(rootNode);
					itsplayer.addPowerup(ref);
					applyPowerup(itsplayer);
				}
			}
			
		};
		rootNode.addAction(givePlayerPowerup);
	}

	
	
	@Override
	public void createObject(Universe universe)
	{
		rootNode.setBoundingBox(new AABB(1,1));

	}
	
	public abstract void applyPowerup(Player player);
	public void removePowerup(Player player)
	{
		player.removePowerup(this);
	}
	
	Player itsplayer;
	Powerup ref = this;
}
