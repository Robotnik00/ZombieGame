package GameObjects;

import java.util.ArrayList;

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
	}

	@Override
	public void createObject(Universe universe) 
	{
		ITexture playerTexture = universe.getTextureEngine().LoadTexture("gfx/Characters/player1.png", 0);
		// add pc control to the root node of this object
		PCControl pcc = new PCControl(this, universe.getGameEngine());
		universe.getState().addKeyEventListener(pcc);
		universe.getState().addMouseEventListener(pcc);
		// add action to the rootNode
		rootNode.addAction(pcc);
		// set mass so the object accelerates fast
		rootNode.setMass(.1f);
		rootNode.setBoundingBox(new AABB(.5f,.5f));
		rootNode.translate(startingX, startingY);
		rootNode.setCollidable(true);
		
		
		
		// create an axis to rotate the object about, and add a mouseTracker action to it.
		GameObject gimble = new GameObject();
		gimble.addAction(new MouseTracker(gimble, universe.getGameEngine()));
		SimpleDraw drawChar = new SimpleDraw(playerTexture);
		drawChar.setOrientation((float)-Math.PI/2);
		gimble.setDrawingInterface(drawChar);
		// add it to rootNode so it translates with the rootNode
		rootNode.addChild(gimble);
		
		gunNode = new GameObject();
		
		
		gunNode.setLocalX(.5f);
		gimble.addChild(gunNode);
		
		guns = new ArrayList<Gun>();
		
		HandGun gun = new HandGun(universe, this);
		addGun(gun);
		setGun(0);
		
		
		
		
		
		
		UpdateHUD hud = new UpdateHUD(this);
		
		rootNode.addAction(hud);
		
		
		
		
		
		universe.addEntity(this);
		universe.setFocus(this);
		
		
		try 
		{
			playerHurt = universe.getAudioEngine().LoadSound("snd/Player/PlayerHurt.wav");
			deathSound = universe.getAudioEngine().LoadSound("snd/Player/DeathSound.wav");
		}
		catch (Exception e) 
		{
			playerHurt = null;
			deathSound = null;
			universe.getGameEngine().LogMessage(
					"HandGun: Couldn't load 'snd/Player/PlayerHurt.wav', 'snd/Player/DeathSound.wav'");
			//e.printStackTrace();
		}
	}
	
	public void addGun(Gun gun)
	{
		guns.add(gun);
	}
	public void removeGun(Gun gun)
	{
		guns.remove(gun);
	}
	public void setGun(int i)
	{
		if(currentGun != null)
			gunNode.removeChild(currentGun.getRootNode());
		
		currentGun = guns.get(i);
		gunNode.addChild(currentGun.getRootNode());
		
	}
	
	public Gun getCurrentGun()
	{
		return currentGun;
	}
	@Override
	public void damage(float dp)
	{
		super.damage(dp);
		if(playerHurt != null)
			playerHurt.Play();
	}
	
	@Override
	public void destroy() 
	{
		universe.removeEntity(this);
		if(deathSound != null)
			deathSound.Play();
	}
	
	public Powerup getCurrentPowerup()
	{
		return currentPowerup;
	}
	
	public void addToScore(float points)
	{
		score += points;
	}
	
	public float getScore()
	{
		return score;
	}
	float score;
	
	Gun currentGun;
	GameObject gunNode;
	
	ArrayList<Gun> guns;
	GameObject hpBarRoot;
	
	
	float hpbarScale = 1;
	/**
	 * ect... 
	 */
	Powerup currentPowerup = null;
	
	ISound deathSound;
	ISound playerHurt;
}
