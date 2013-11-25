package GameObjects;

import AudioEngine.IAudioEngine;
import Drawing.TileDraw;
import Engine.IGameEngine;
import GameStates.EventListenerState;
import Geometry.AABB;
import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;

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
		ITexture tile = gfx.LoadTexture("gfx/Environment/papertile.png", 1);
		
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
		
		
	}

}
