package GameObjects;

import Actions.AIControl;
import Drawing.SimpleDraw;
import Geometry.AABB;
import TextureEngine.ITexture;

public class Zombie extends Entity
{

	public Zombie(Universe universe)
	{
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createObject(Universe universe) 
	{
		String textureName = "gfx/Characters/zombie" + ((int)(Math.random()*3+1)) + ".png";
		System.out.printf("%s\n", textureName);
		ITexture zombieTexture = universe.getTextureEngine().LoadTexture(textureName, 0);
		rootNode.setBoundingBox(new AABB(.5f,.5f));
		rootNode.setProxemityBounds(new AABB(1,1));
		rootNode.setCollidable(true);
		
		gimble = new GameObject();
		SimpleDraw drawZombie = new SimpleDraw(zombieTexture);
		drawZombie.setOrientation((float)-Math.PI/2);
		gimble.setDrawingInterface(drawZombie);
		ai = new AIControl(rootNode, this, universe.getHandle(), universe.getGameEngine());
		rootNode.addAction(ai);
		
		rootNode.addChild(gimble);
		rootNode.setMass(.1f);
		universe.addEntity(this);
		
	}
	
	public void setOrientation(float angle)
	{
		gimble.rotate(angle);
	}
	public float getOrientation()
	{
		return gimble.getOrientation();
	}
	public void setTarget(Entity target)
	{
		this.target = target;
		ai.setTarget(target.getRootNode());
	}
	

	@Override
	public void destroy() 
	{
		rootNode = null;
	}
	AIControl ai;
	Entity target = null;
	GameObject gimble;
}
