package GameObjects;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import Actions.Physics;
import Actions.ProjectileAction;
import Actions.TimeToLive;
import Drawing.SimpleDraw;
import Geometry.AABB;

public class Flame extends Projectile
{

	public Flame(Universe universe, Player player) {
		super(universe, player);
		hp = 100;
		dp = .01f;
	}

	@Override
	public void createObject(Universe universe) {
		// TODO Auto-generated method stub
		SimpleDraw drawProjectile = new SimpleDraw(universe.getTextureEngine().LoadTexture("gfx/Projectiles/FireBall.png", 0));
		drawProjectile.setScale(.15f, .15f);
		rootNode.setDrawingInterface(drawProjectile);
		rootNode.setBoundingBox(new AABB(.1f, .1f));
		rootNode.addAction(new ProjectileAction(this, universe.getHandle()));
		rootNode.addAction(new Physics(rootNode,universe.getGameEngine()));
		rootNode.setMass(.4f);
	}
	@Override
	public void inflictDamage(Entity entity)
	{
		if(entity.isDestroyable())
		{
			if(hits > 10 && !(entity instanceof Player))
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
				hits = 0;
				if(entity instanceof Zombie)
				{
					BloodSplatter splatter = new BloodSplatter(universe);
					
					splatter.setStartingLoc(entity.getRootNode().getLocalX(), entity.getRootNode().getLocalY());
				}
				hits = 0;
				
			}
			
			if(Math.random() > .95)
			{

				Fire fire = new Fire(universe, entity);
				TimeToLive ttlfire = new TimeToLive(fire, universe);
				ttlfire.setTimeToLive(200);
				fire.setStartingLoc((float)(Math.random()-.5)*.4f, (float)(Math.random()-.5)*.4f);
				fire.getRootNode().addAction(ttlfire);
				ttlfire.start();
			}
			
			hits++;
	
			entity.damage(dp + (float)(Math.random()-.5)*randomizeDamage);
			
		}
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		universe.removeEntity(this);
	}

	int hits = 0;
	
}

