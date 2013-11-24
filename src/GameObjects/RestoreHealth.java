package GameObjects;

import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class RestoreHealth extends Powerup
{

	public RestoreHealth(Universe u, Player player) {
		super(u, player);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void createObject(Universe universe) 
	{
		super.createObject(universe);
		ITexture health = universe.getTextureEngine().LoadTexture("gfx/Powerups/RestoreHealth.png", 1);
		SimpleDraw drawPowerup = new SimpleDraw(health);
		
		rootNode.setDrawingInterface(drawPowerup);
	}
	@Override
	public void applyPowerup(Player player) {
		
		player.setHP(player.getMaxHp());
		itsplayer.removePowerup(this);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	
}
