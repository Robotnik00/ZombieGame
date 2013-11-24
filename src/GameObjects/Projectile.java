package GameObjects;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import Actions.Action;
import Actions.TimeToLive;

public abstract class Projectile extends Entity
{
	public Projectile(Universe universe, Player player)
	{
		super(universe);
		this.player = player;
		
	}

	public void inflictDamage(Entity entity)
	{
		if(entity.isDestroyable())
		{
			InGameText text = new InGameText(universe);
				
			text.scaleText(.2f);
			int dscore = (int)Math.round((dp + randomizeScore*Math.random())*100);
			text.setText("" + dscore);
			text.setColor(new Vector4f(0,1,0,1));
			TimeToLive ttl = new TimeToLive(text, universe);
			ttl.setTimeToLive(500);
			text.getRootNode().setTranslationalVelocity(new Vector2f(0,.5f));
			text.setStartingLoc(entity.getRootNode().getLocalX(), entity.getRootNode().getLocalY());
			text.getRootNode().addAction(ttl);
			universe.addEntity(text);
			ttl.start();
			player.addToScore(dscore);
			
			
			
			
			if(entity instanceof Zombie)
			{
				BloodSplatter splatter = new BloodSplatter(universe);
				
				splatter.setStartingLoc(entity.getRootNode().getLocalX(), entity.getRootNode().getLocalY());
			}
			
	
			entity.damage(dp + (float)(Math.random()*randomizeDamage));
			
		}
	}

	
	public void setTimeToLive(long ttl)
	{
		
		timetolive = new TimeToLive(this, universe);
		timetolive.setTimeToLive(ttl);
		rootNode.addAction(timetolive);
		timetolive.start();
	}
	public void setVelocity(Vector2f velocity)
	{
		rootNode.setTranslationalVelocity(velocity);
	}
	
	public void setDP(float dp)
	{
		this.dp = dp;
	}
	public float getDP()
	{
		return dp;
	}
	Player player;
	float randomizeDamage = 0;
	float randomizeScore = 1;
	float dp = .2f;
	long timeToLive = 1000;
	TimeToLive timetolive;
}
