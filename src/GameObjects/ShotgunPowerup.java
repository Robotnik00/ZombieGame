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
	}

	@Override
	public void createObject(Universe universe) 
	{
		super.createObject(universe);
		ITexture pshotgunTex = universe.getTextureEngine().LoadTexture("gfx/Powerups/shotgun_shell.png", 0);
		SimpleDraw drawPowerup = new SimpleDraw(pshotgunTex);
		
		rootNode.setDrawingInterface(drawPowerup);
		rootNode.scale(.5f, .5f);
		
	}
}
