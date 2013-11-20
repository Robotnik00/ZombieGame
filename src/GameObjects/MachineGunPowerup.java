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
	
}
