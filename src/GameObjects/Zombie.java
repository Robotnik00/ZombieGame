package GameObjects;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import Actions.AIControl;
import Actions.TimeToLive;
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
		destroyable = true;
		String textureName = "gfx/Characters/zombie" + ((int)(Math.random()*1+1)) + ".png";
		//System.out.printf("%s\n", textureName);
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
		
		// blah
		sndHurt = new ISound[3];
		sndIdle = new ISound[2];
		sndDeath = new ISound[1];
		
		sndHurt[0]=null; sndHurt[1]=null; sndHurt[2]=null;
		sndIdle[0]=null; sndIdle[1]=null;
		sndDeath[0]=null;
		
		try 
		{
			sndHurt[0] = universe.getAudioEngine().LoadSound("snd/Zombie/zombie_hurt1.wav");
			sndHurt[1] = universe.getAudioEngine().LoadSound("snd/Zombie/zombie_hurt2.wav");
			sndHurt[2] = universe.getAudioEngine().LoadSound("snd/Zombie/zombie_hurt3.wav");
			
			sndIdle[0] = universe.getAudioEngine().LoadSound("snd/Zombie/zombie_idle1.wav");
			sndIdle[1] = universe.getAudioEngine().LoadSound("snd/Zombie/zombie_idle2.wav");
			
			sndDeath[0] = universe.getAudioEngine().LoadSound("snd/Zombie/zombie_die1.wav");
		} 
		catch (Exception e) 
		{
			universe.getGameEngine().LogMessage("Zombie: Couldn't load zombie sounds.");
			//e.printStackTrace();
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
	public GameObject getGimble()
	{
		return gimble;
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
		
		// choose a random hurt sound
		int r = (int)Math.floor((Math.random()*3));
		
		if(Math.random() > .5 && sndHurt[r] != null)
		{
			sndHurt[r].Play();
		}
	}

	@Override
	public void destroy() 
	{
		universe.removeEntity(this);
		if(sndDeath[0] != null)
			sndDeath[0].Play();
		if(target instanceof Player)
		{
			((Player)target).addToScore(1000);
		}		InGameText text = new InGameText(universe);
		
		text.scaleText(.2f);
		int dscore = 1000;
		text.setText("" + dscore);
		text.setColor(new Vector4f(1,0,1,1));
		TimeToLive ttl = new TimeToLive(text, universe);
		ttl.setTimeToLive(500);
		text.getRootNode().setTranslationalVelocity(new Vector2f(0,.5f));
		text.setStartingLoc(rootNode.getLocalX(), rootNode.getLocalY());
		text.getRootNode().addAction(ttl);
		universe.addEntity(text);
		ttl.start();
	}
	AIControl ai;
	Entity target = null;
	GameObject gimble;
	
	ISound[] sndIdle;
	ISound[] sndHurt;
	ISound[] sndDeath;
}
