package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import TextureEngine.ITexture;
import Actions.Action;
import Actions.TimeToLive;
import Drawing.DrawText;
import Drawing.SimpleDraw;

public class DamageMultiplier extends Powerup
{

	public DamageMultiplier(Universe u, Player player) 
	{
		super(u, player);
	}
	
	
	
	
	@Override
	public void applyPowerup(final Player player)
	{
		DamageMultiplier appliedPowerup = null;
		for(int i = 0; i < player.getPowerups().size(); i++)
		{
			if(player.getPowerups().get(i) instanceof DamageMultiplier)
			{
				appliedPowerup = (DamageMultiplier)player.getPowerups().get(i);
			}
		}
		if(appliedPowerup == null)
		{
			player.addPowerup(this);
			ttl.start();
			damageMultiplier = new Action()
			{

				@Override
				public void performAction() 
				{
					text.setText("" + ttl.getTimeLeft()/1000);
					player.getCurrentGun().setDamageMultiplier(multiplier);
				}
				
			};
			rootNode.addAction(damageMultiplier);
			
		}
		else
		{
			appliedPowerup.getTTL().addTime(timeToLive);
			universe.removeEntity(this);
		}
		
		
	}
	
	public void addTime(long time)
	{
		ttl.addTime(time);
	}
	
	@Override
	public void destroy() 
	{
		rootNode.removeAction(damageMultiplier);
		for(int i = 0; i < itsplayer.getNumGuns(); i++)
		{
			itsplayer.getGuns().get(i).setDamageMultiplier(1);
		}
		itsplayer.removePowerup(this);
	}
	
	@Override
	public void createObject(Universe universe)
	{
		super.createObject(universe);
		
		text = new DrawText(universe.getTextureEngine(), "gfx/font.png");
		GameObject textNode = new GameObject();
		textNode.setDrawingInterface(text);
		rootNode.addChild(textNode);
		text.setColor(new Vector4f(0.1f,.1f,.1f,1));
		textNode.scale(.5f, .5f);
		textNode.setLocalX(-.6f);
		textNode.setLocalY(-.4f);
		
		
		ITexture damagetex = universe.getTextureEngine().LoadTexture("gfx/Powerups/quad_damage.png", 1);
		drawPowerup = new SimpleDraw(damagetex);
		rootNode.setDrawingInterface(drawPowerup);
		
	
		ttl = new TimeToLive(this, universe);
		ttl.setTimeToLive(10000);
		rootNode.addAction(ttl);
	}	
	public TimeToLive getTTL()
	{
		return ttl;
	}
	float multiplier = 4;
	TimeToLive ttl;
	long timeToLive = 10000; // time to live in ms
	Action damageMultiplier;
	DrawText text;
}
