package GameObjects;

import AudioEngine.IAudioEngine;
import Drawing.SimpleDraw;
import Drawing.TileDraw;
import Engine.IGameEngine;
import GameStates.EventListenerState;
import GameStates.IGameState;
import Geometry.AABB;
import TextureEngine.ITexture;
import TextureEngine.ITextureEngine;

/**
 * 
 * example of how to make a level
 *
 */

// Notes:
// When making a level use gameobjects to optimize drawing
// this example makes a tree with 1 level which is effectively an array, 
// however, larger scenes may make use of multilevel trees in order to be more 
// efficient.

public class ExampleLevel extends Universe
{

	public ExampleLevel(ITextureEngine gfx, IAudioEngine snd, IGameEngine game, EventListenerState state) {
		super(gfx, snd, game, state);
	}

	@Override
	protected void buildUniverse() 
	{
		// load the tile texture
		ITexture tile = gfx.LoadTexture("image3.bmp", 1);
		
		// create a tile drawing class for drawing a portion of the level.
		TileDraw tileTexture = new TileDraw(tile);
		// draw an 8x8 array of tiles.
		tileTexture.setRows(8);
		tileTexture.setCols(8);
		// make a 30x30 array of 8x8 arrays totaling 57,600 tiles!
		for(int i = -15; i < 15; i++)
		{
			for(int j = -15; j < 15; j++)
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
	}
}
