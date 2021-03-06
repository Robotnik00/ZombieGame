package GameObjects;

import org.lwjgl.util.vector.Vector4f;

import AudioEngine.IAudioEngine;
import Drawing.TileDraw;
import Engine.IGameEngine;
import GameStates.EventListenerState;
import Geometry.AABB;
import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;
import Utility.Collision;
import Utility.CollisionDetection;

public class Level extends Universe
{

	public Level(ITextureEngine gfx, IAudioEngine snd, IGameEngine game,
			EventListenerState state) {
		super(gfx, snd, game, state);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void buildUniverse() 
	{

		// load the tile texture
		ITexture tile = gfx.LoadTexture("gfx/Environment/grass.png", 1);
		
		// create a tile drawing class for drawing a portion of the level.
		TileDraw tileTexture = new TileDraw(tile);
		// draw an 8x8 array of tiles.
		tileTexture.setRows(8);
		tileTexture.setCols(8);
		// make a 30x30 array of 8x8 arrays totaling 57,600 tiles!
		for(int i = -5; i < 5; i++)
		{
			for(int j = -5; j < 5; j++)
			{
				// create a tileArrayNode
				GameObject tileObj = new GameObject();
				// set it to draw an array of tiles
				tileObj.setDrawingInterface(tileTexture);
				// translate it to line up with other tiles
				tileObj.translate(i*8, j*8);
				// set its proxemity so it doesn't get drawn unless it needs to. 
				// (without this game would not be playable).
				tileObj.setProxemityBounds(new AABB(8,8));
				// add it to the background
				backgroundNode.addChild(tileObj);
			}
		}
		for(int i = 0; i < 20; i++)
		{
			
			Wall w1 = new Wall(this);
			w1.setStartingLoc(-10, -10 + i);
			addEntity(w1);
			Wall w2 = new Wall(this);
			w2.setStartingLoc(10, 10 - i);
			addEntity(w2);
			Wall w3 = new Wall(this);
			w3.setStartingLoc(-10 + i, 10);
			addEntity(w3);
			Wall w4 = new Wall(this);
			w4.setStartingLoc(10 - i, -10);
			addEntity(w4);
			
			
		}
		KillPlayer k1 = new KillPlayer(this);
		k1.setBounds(new AABB(1,20));
		k1.setStartingLoc(-10.5f, 0);
		addEntity(k1);
		KillPlayer k2 = new KillPlayer(this);
		k2.setBounds(new AABB(1,20));
		k2.setStartingLoc(10.5f, 0);
		addEntity(k2);
		KillPlayer k3 = new KillPlayer(this);
		k3.setBounds(new AABB(20,1));
		k3.setStartingLoc(0, 10.5f);
		addEntity(k3);
		KillPlayer k4 = new KillPlayer(this);
		k4.setBounds(new AABB(20, 1));
		k4.setStartingLoc(0, -10.5f);
		addEntity(k4);
		
		GraveYard g = new GraveYard(this);
		g.setStartingLoc(5, -6f);
		//addEntity(g);

		for(int i = -4; i < 5; i++)
		{
			Gate gate = new Gate(this);
			gate.setStartingLoc(-10, (float)i * .31f + 1f);
			addEntity(gate);
			Collision[] c = CollisionDetection.getCollisions(gate.getRootNode(), getHandle());
			for(int j = 0; j < c.length; j++)
			{
				c[j].collidingObject.getEntity().destroy();
			}
		}
		

		for(int i = -4; i < 5; i++)
		{
			Gate gate = new Gate(this);
			gate.setStartingLoc(10, (float)i * .31f + 1);
			addEntity(gate);
			Collision[] c = CollisionDetection.getCollisions(gate.getRootNode(), getHandle());
			for(int j = 0; j < c.length; j++)
			{
				c[j].collidingObject.getEntity().destroy();
			}
		}
		
		
		Building b1 = new Building(this);
		b1.setStartingLoc(-7, 5);
		b1.setBlend(.5f);
		b1.setColor(new Vector4f(.2f,.2f,.4f,1));
		Building b2 = new Building(this);
		b2.setStartingLoc(0, 5);
		b2.setBlend(.5f);
		b2.setColor(new Vector4f(.2f,.4f,.2f,1));
		Building b3 = new Building(this);
		b3.setStartingLoc(7, 5);
		b3.setBlend(.5f);
		b3.setColor(new Vector4f(.4f,.2f,.2f,1));
		for(int i = -25; i < 25; i++)
		{
			RoadSegment r = new RoadSegment(this);
			r.setStartingLoc(i*3, 1f);
		}
		for(int i = 0; i < 20; i++)
		{
			TreeLeaves t = new TreeLeaves(this);
			t.setStartingLoc((float)(Math.random())*10-10, (float)(Math.random())*10-10);
			
			t.getRootNode().setBoundingBox(new AABB(3,3));
			if(CollisionDetection.getCollisions(t.getRootNode(), getHandle()).length > 0)
			{
				t.destroy();
			}
			t.getRootNode().setBoundingBox(new AABB(.65f,.65f));
		}
		

	}
}
