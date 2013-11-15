package GameObjects;

import org.lwjgl.util.vector.Vector2f;

public abstract class Projectile extends Entity
{
	public Projectile(Universe universe)
	{
		super(universe);
	}

	public void inflictDamage(Entity entity)
	{
		entity.damage(dp);
	}
	
	public void setVelocity(Vector2f velocity)
	{
		rootNode.setTranslationalVelocity(velocity);
	}
	
	float dp = .5f;
}
