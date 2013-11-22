package GameObjects;

import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class FlameThrowerPowerup extends GunPowerup
{

	public FlameThrowerPowerup(Universe universe, Player player) {
		super(universe, player);
		gun = new FlameThrower(universe, player);
	}

	@Override
	public void createObject(Universe universe) 
	{
		super.createObject(universe);
		ITexture fire = universe.getTextureEngine().LoadTexture("gfx/Powerups/flamethrower_can.png", 0);
		SimpleDraw drawPowerup = new SimpleDraw(fire);
		
		rootNode.setDrawingInterface(drawPowerup);
		rootNode.scale(.5f, .5f);
		
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
