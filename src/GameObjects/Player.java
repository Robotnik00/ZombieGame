package GameObjects;

import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import Actions.Action;
import Actions.MouseTracker;
import Actions.PCControl;
import Actions.UpdateHUD;
import AudioEngine.ISound;
import Drawing.DrawBoundingBox;
import Drawing.DrawText;
import Drawing.SimpleDraw;
import Geometry.AABB;
import TextureEngine.ITexture;

public class Player extends Entity
{

	public Player(Universe universe)
	{
		super(universe);
		powerups = new ArrayList<Powerup>();
	}

	@Override
	public void createObject(Universe universe) 
	{
		destroyable = true;
		ITexture playerTexture = universe.getTextureEngine().LoadTexture("gfx/Characters/player1.png", 0);
		// add pc control to the root node of this object
		PCControl pcc = new PCControl(this, universe.getGameEngine());
		universe.getState().addKeyEventListener(pcc);
		universe.getState().addMouseEventListener(pcc);
		// add action to the rootNode
		rootNode.addAction(pcc);
		// set mass so the object accelerates fast
		rootNode.setMass(.1f);
		rootNode.setBoundingBox(new AABB(.2f,.2f));
		rootNode.translate(startingX, startingY);
		rootNode.setCollidable(true);
		
		
		
		// create an axis to rotate the object about, and add a mouseTracker action to it.
		GameObject gimble = new GameObject();
		gimble.addAction(new MouseTracker(gimble, universe.getGameEngine()));
		drawChar = new SimpleDraw(playerTexture);
		drawChar.setOrientation((float)-Math.PI/2);
		drawChar.setScale(.5f, .5f);
		gimble.setDrawingInterface(drawChar);
		// add it to rootNode so it translates with the rootNode
		rootNode.addChild(gimble);
		
		gunNode = new GameObject();
		
		
		gunNode.setLocalX(.3f);
		gunNode.setLocalY(-.05f);
		gimble.addChild(gunNode);
		
		guns = new ArrayList<Gun>();
		
		Gun gun = new HandGun(universe, this);
		addGun(gun);
		setGun(0);
		
		UpdateHUD hud = new UpdateHUD(this);
		
		universe.getHUD().addAction(hud);
		
		universe.addEntity(this);
		universe.setFocus(this);
		
		
		deathSound = new ISound[4];
		playerHurt = new ISound[3];
		
		deathSound[0]=null;
		deathSound[1]=null;
		deathSound[2]=null;
		deathSound[3]=null;
		
		playerHurt[0]=null;
		playerHurt[1]=null;
		playerHurt[2]=null;
		
		try 
		{
			//splayerHurt = universe.getAudioEngine().LoadSound("snd/player/PlayerHurt.wav");
			//String filename = "player_dies" + (int)(Math.random()*4+1) + ".wav";
			//deathSound = universe.getAudioEngine().LoadSound("snd/player/" + filename);
			
			for (int i=0; i < 4; i++)
				deathSound[i] = universe.getAudioEngine().LoadSound("snd/Player/player_dies"+(i+1)+".wav");
			for (int i=0; i < 3; i++)
				playerHurt[i] = universe.getAudioEngine().LoadSound("snd/Player/player_hurt"+(i+1)+".wav");
		}
		catch (Exception e) 
		{
			playerHurt = null;
			deathSound = null;
			universe.getGameEngine().LogMessage("Couldn't load player sounds.");
		}
	}
	
	public void addGun(Gun gun)
	{
		guns.add(gun);
	}
	public void removeGun(Gun gun)
	{
		if(gun == currentGun)
		{
			setGun(guns.size()-1);
		}
		guns.remove(gun);
		
	}
	public void setGun(int i)
	{
		if(currentGun != null)
		{
			gunNode.removeChild(currentGun.getRootNode());
			if(currentGun.getPowerup() != null)
			{
				currentGun.getPowerup().getDrawInterface().setColor(new Vector4f(0,0,0,.5f));
			}
		}
		
		currentGun = guns.get(i);
		
		if(currentGun.getPowerup() != null)
		{
			currentGun.getPowerup().getDrawInterface().setColor(new Vector4f(0,1,0,1));
			currentGun.getPowerup().getDrawInterface().setBlend(.3f);
		}
		
		gunNode.addChild(currentGun.getRootNode());
		currentGun.select();
	}
	
	public Gun getCurrentGun()
	{
		return currentGun;
	}
	public int getNumGuns()
	{
		return guns.size();
	}
	public ArrayList<Gun> getGuns()
	{
		return guns;
	}
	@Override
	public void damage(float dp)
	{
		if(!invulnerable)
		{
			super.damage(dp);
			
			// FIXME: stop player sound from playing more than once
			int s = (int)(Math.random()*3.0);
			if(playerHurt[s] != null && Math.random() > .5f && universe.getTime() - lastSound > 1000)
			{
				playerHurt[s].Play();
				lastSound = universe.getTime();
			}
		}
	}
	
	@Override
	public void destroy() 
	{
		universe.getHandle().removeChild(rootNode);
		destroyed = true;
		
		int s = (int)(Math.random()*3.0);
		if(deathSound[s] != null)
			deathSound[s].Play();
		
		GameObject deadBody = new GameObject();
		SimpleDraw drawbody = new SimpleDraw(universe.getTextureEngine().LoadTexture("gfx/Characters/player_dead1.png", 0x00ffffff));
		drawbody.setScale(0.75f, 0.75f);	// make the dead body sprite a little smaller
		deadBody.setDrawingInterface(drawbody);
		universe.getBackgroundNode().addChild(deadBody);
		deadBody.setLocalX(rootNode.getLocalX());
		deadBody.setLocalY(rootNode.getLocalY());
		
		for(int i = 0; i < 10; i++)
		{
			BloodSplatter b = new BloodSplatter(universe);
			b.setStartingLoc((float)(rootNode.getLocalX()+Math.random()*.5f), (float)(rootNode.getLocalY()+Math.random()*.5f));	
		}
	}
	
	public void addToScore(int points)
	{
		score += points;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void addPowerup(Powerup p)
	{
		
		p.getRootNode().setBoundingBox(null);
		powerups.add(p);
	}
	public void removePowerup(Powerup p)
	{
		powerups.remove(p);
	}
	public ArrayList<Powerup> getPowerups()
	{
		return powerups;
	}
	public SimpleDraw getDrawingInterface()
	{
		return drawChar;
	}
	public void setInvulnerable(boolean inv)
	{
		invulnerable = inv;
	}
	public boolean getInvulnerable()
	{
		return invulnerable;
	}
	public void setWave(Wave w)
	{
		currentWave = w;
	}
	public Wave getWave()
	{
		return currentWave;
	}
	
	int score;
	
	Gun currentGun;
	GameObject gunNode;
	
	ArrayList<Gun> guns;
	GameObject hpBarRoot;
	
	
	float hpbarScale = 1;
	/**
	 * ect... 
	 */
	ArrayList<Powerup> powerups;
	
	ISound[] deathSound;
	ISound[] playerHurt;
	
	SimpleDraw drawChar;
	
	boolean invulnerable = false;
	
	long lastSound = 0;
	Wave currentWave;
}
