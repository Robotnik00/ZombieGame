package GameObjects;

import Actions.Action;
import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public abstract class GunPowerup extends Powerup
{

	public GunPowerup(Universe universe, Player player)
	{
		super(universe, player);
		
	}

	@Override
	public void applyPowerup(final Player player) 
	{
		if(gun != null)
		{
			Action removeShotgun = new Action()
			{
	
				@Override
				public void performAction() {
					if(gun.getAmmo() == 0)
					{
						removePowerup(player);
					}
					
				}
				
			};
			
			gun.getRootNode().addAction(removeShotgun);
			
			
			player.addGun(gun);
			player.setGun(player.getNumGuns() - 1); 
		}
	}

	@Override
	public void removePowerup(Player player) 
	{
		super.removePowerup(player);
		
		player.removeGun(gun);
		
	}


	@Override
	public void destroy() {
		universe.removeEntity(this);
	}
	Gun gun;
}