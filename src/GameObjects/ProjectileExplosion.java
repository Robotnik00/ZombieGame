package GameObjects;

import Actions.TimeToLive;

public abstract class ProjectileExplosion extends Entity
{
	

	public ProjectileExplosion(Universe universe) {
		super(universe);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		universe.removeEntity(this);
	}
}
