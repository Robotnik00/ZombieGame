package GameObjects;

import Actions.Action;
import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class ShotgunPowerup extends GunPowerup
{

	public ShotgunPowerup(Universe universe, Player player)
	{
		super(universe, player);
		gun = new Shotgun(universe, player);
		gun.setPowerup(this);
	}

	@Override
	public void createObject(Universe universe) 
	{
		super.createObject(universe);
		ITexture pshotgunTex = universe.getTextureEngine().LoadTexture("gfx/Powerups/shotgun_shell.png", 0);
		drawPowerup = new SimpleDraw(pshotgunTex);
		
		rootNode.setDrawingInterface(drawPowerup);
		
	}	
	
	@Override
	public void applyPowerup(final Player player)
	{
		ShotgunPowerup appliedPowerup = null;
		for(int i = 0; i < player.getPowerups().size(); i++)
		{
			if(player.getPowerups().get(i) instanceof ShotgunPowerup)
			{
				appliedPowerup = (ShotgunPowerup)player.getPowerups().get(i);
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
