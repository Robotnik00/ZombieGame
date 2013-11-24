package GameObjects;

import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class FlameThrowerPowerup extends GunPowerup
{

	public FlameThrowerPowerup(Universe universe, Player player) {
		super(universe, player);
		gun = new FlameThrower(universe, player);
		gun.setPowerup(this);
	}

	@Override
	public void createObject(Universe universe) 
	{
		super.createObject(universe);
		ITexture fire = universe.getTextureEngine().LoadTexture("gfx/Powerups/flamethrower_can.png", 0);
		drawPowerup = new SimpleDraw(fire);
		
		rootNode.setDrawingInterface(drawPowerup);
		
	}	 
	
	@Override
	public void applyPowerup(final Player player)
	{
		FlameThrowerPowerup appliedPowerup = null;
		for(int i = 0; i < player.getPowerups().size(); i++)
		{
			if(player.getPowerups().get(i) instanceof FlameThrowerPowerup)
			{
				appliedPowerup = (FlameThrowerPowerup)player.getPowerups().get(i);
			}
		}
		if(appliedPowerup == null)
		{
			super.applyPowerup(player);
		}
		else
		{
			appliedPowerup.getGun().addAmmo(gun.getMaxAmmo());
		}
		
		
	}
}
