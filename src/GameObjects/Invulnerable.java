package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import Actions.Action;
import Actions.TimeToLive;
import Drawing.DrawText;
import Drawing.SimpleDraw;
import TextureEngine.ITexture;

public class Invulnerable extends Powerup
{

	public Invulnerable(Universe u, Player player) {
		super(u, player);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void applyPowerup(final Player player)
	{
		Invulnerable appliedPowerup = null;
		player.setInvulnerable(true);
		player.getDrawingInterface().setBlend(.5f);
		player.getDrawingInterface().setColor(new Vector4f(0,0,1,1));
		for(int i = 0; i < player.getPowerups().size(); i++)
		{
			if(player.getPowerups().get(i) instanceof Invulnerable)
			{
				appliedPowerup = (Invulnerable)player.getPowerups().get(i);
			}
		}
		if(appliedPowerup == null)
		{
			player.addPowerup(this);
			ttl.start();
			updateTimer = new Action()
			{

				@Override
				public void performAction() 
				{
					text.setText("" + ttl.getTimeLeft()/1000);
				}
				
			};
			rootNode.addAction(updateTimer);
			
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
		rootNode.removeAction(updateTimer);
		itsplayer.setInvulnerable(false);
		itsplayer.removePowerup(this);
		itsplayer.getDrawingInterface().setColor(new Vector4f(0,0,0,1));
		itsplayer.getDrawingInterface().setBlend(0);
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
		
		
		ITexture damagetex = universe.getTextureEngine().LoadTexture("gfx/Powerups/invulnerability.png", 1);
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
	Action updateTimer;
	DrawText text;
}

