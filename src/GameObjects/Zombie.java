package GameObjects;

import java.util.ArrayList;

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
	}

	@Override
	public void createObject(Universe universe) 
	{
		zombies.add(this);
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
	public void setTarget(Player target)
	{
		this.target = target;
		ai.setTarget(target);

		float i = (float)Math.random();
		if(i > .99f)
		{
			powerup = new ShotgunPowerup(universe, target);
		}
		else if(i > .985f && i < .99f)
		{
			powerup = new MachineGunPowerup(universe, target);
		}
		else if(i > .98 && i < .985)
		{
			powerup = new FlameThrowerPowerup(universe, target);
		}
		else if(i > .975 && i < .98)
		{
			powerup = new DamageMultiplier(universe, target);
		}
		else if(i > .97 && i < .975)
		{
			powerup = new Invulnerable(universe, target);
		}
		else if(i > .965 && i < .97)
		{
			powerup = new RestoreHealth(universe, target);
		}
		else
		{
			powerup = null;
		}
		
	}
	@Override
	public void damage(float dp)
	{
		super.damage(dp);
		
		// choose a random hurt sound
		int r = (int)Math.floor((Math.random()*3));
		
		if(Math.random() > .8 && sndHurt[r] != null && !dead && universe.getTime() - lastSound > 1000)
		{
			sndHurt[r].Play();
			lastSound = universe.getTime();
		}
	}

	@Override
	public void destroy() 
	{
		zombies.remove(this);
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
		dead = true;
		
		if(powerup != null)
		{
			powerup.setStartingLoc(rootNode.getLocalX(), rootNode.getLocalY());
			universe.addEntity(powerup);
		}
	}
	public void setSpeed(float speed)
	{
		ai.setSpeed(speed);
	}
	Powerup powerup = null;
	
	
	AIControl ai;
	Player target = null;
	GameObject gimble;
	
	ISound[] sndIdle;
	ISound[] sndHurt;
	ISound[] sndDeath;
	
	long lastSound = 0;
	
	boolean dead = false;
	
	// GLOBAL VARIABLES!!!
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>(); // used to keep track of number of zombies
}
