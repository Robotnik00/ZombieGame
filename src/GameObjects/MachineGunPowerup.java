package GameObjects;

import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class MachineGunPowerup extends GunPowerup
{

	public MachineGunPowerup(Universe universe, Player player) {
		super(universe, player);
		gun = new MachineGun(universe, player);
		gun.setPowerup(this);
	}
	@Override
	public void createObject(Universe universe) 
	{
		super.createObject(universe);
		ITexture machinegun = universe.getTextureEngine().LoadTexture("gfx/Powerups/machinegun_shells.png", 1);
		drawPowerup = new SimpleDraw(machinegun);
		
		rootNode.setDrawingInterface(drawPowerup);
		
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
