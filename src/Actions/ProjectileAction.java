package Actions;

import GameObjects.GameObject;
import GameObjects.Projectile;
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
		GameObject[] collisions = CollisionDetection.getCollisions(projectile.getRootNode(), universe);
		
		if(collisions.length > 0)
		{
			projectile.inflictDamage(collisions[0].getEntity());
			projectile.destroy();
		}
	}
	Projectile projectile;
	GameObject universe;
}
