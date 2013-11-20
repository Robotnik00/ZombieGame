package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import Actions.Action;
import Drawing.DrawText;
import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public abstract class GunPowerup extends Powerup
{

	public GunPowerup(Universe universe, Player player)
	{
		super(universe, player);
		
	}

	@Override
	public void createObject(Universe universe)
	{
		super.createObject(universe);
		
		text = new DrawText(universe.getTextureEngine(), "font1.bmp");
		GameObject textNode = new GameObject();
		textNode.setDrawingInterface(text);
		rootNode.addChild(textNode);
		text.setColor(new Vector4f(0.1f,.1f,.1f,1));
		textNode.scale(.5f, .5f);
		textNode.setLocalX(-.6f);
		textNode.setLocalY(-.4f);
		
	}
	
	@Override
	public void applyPowerup(final Player player) 
	{
		if(gun != null)
		{
			Action removeShotgun = new Action()
			{
				
				@Override
				public void performAction() 
				{
					text.setText("" + gun.getAmmo());
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
	
	DrawText text;

	@Override
	public void destroy() {
		universe.removeEntity(this);
	}
	Gun gun;
}