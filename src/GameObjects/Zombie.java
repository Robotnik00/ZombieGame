package GameObjects;

import Actions.AIControl;
import AudioEngine.ISound;
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
		
		// blah
		try 
		{
			hurt = universe.getAudioEngine().LoadSound("snd/Zombie/ZombieHurt.wav");
			zombieDeath = universe.getAudioEngine().LoadSound("snd/Zombie/ZombieDeath.wav");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
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
		ai.setTarget(target);
	}
	@Override
	public void damage(float dp)
	{
		super.damage(dp);
		
		if(Math.random() > .5 && hurt != null)
		{
			hurt.Play();
		}
	}

	@Override
	public void destroy() 
	{
		universe.removeEntity(this);
		if(zombieDeath != null)
			zombieDeath.Play();
	}
	AIControl ai;
	Entity target = null;
	GameObject gimble;
	
	ISound hurt;
	ISound zombieDeath;
}
