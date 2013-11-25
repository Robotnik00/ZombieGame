package Actions;

import GameObjects.GameObject;
import GameObjects.Projectile;
import Utility.Collision;
import Utility.CollisionDetection;

public class ProjectileAction implements Action
{
	public ProjectileAction(Projectile projectile, GameObject universe)
	{
		this.projectile = projectile;
		this.universe = universe;
	}

	@Override
	public void performAction() 
	{
		Collision[] collisions = CollisionDetection.getCollisions(projectile.getRootNode(), universe);
		
		if(collisions.length > 0)
		{
			projectile.inflictDamage(collisions[0].collidingObject.getEntity());
			projectile.damage(projectile.getDP());
		}
	}
	Projectile projectile;
	GameObject universe;
}
