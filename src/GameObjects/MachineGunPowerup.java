package GameObjects;

import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class MachineGunPowerup extends GunPowerup
{

	public MachineGunPowerup(Universe universe, Player player) {
		super(universe, player);
		gun = new MachineGun(universe, player);
	}
	@Override
	public void createObject(Universe universe) 
	{
		super.createObject(universe);
		ITexture pshotgunTex = universe.getTextureEngine().LoadTexture("gfx/Powerups/machinegun_shells.png", 0);
		SimpleDraw drawPowerup = new SimpleDraw(pshotgunTex);
		
		rootNode.setDrawingInterface(drawPowerup);
		rootNode.scale(.5f, .5f);
		
	}
	@Override
	public void applyPowerup(final Player player)
	{
		MachineGunPowerup appliedPowerup = null;
		for(int i = 0; i < player.getPowerups().size(); i++)
		{
			if(player.getPowerups().get(i) instanceof MachineGunPowerup)
			{
				appliedPowerup = (MachineGunPowerup)player.getPowerups().get(i);
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
