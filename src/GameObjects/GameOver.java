package GameObjects;

import Drawing.DrawText;
import GameStates.HighScoreState;

public class GameOver extends Entity
{

	public GameOver(Universe universe, Player p) {
		super(universe);
		DrawText gameovertext = new DrawText(universe.getTextureEngine(), "gfx/font.png");
		rootNode.setDrawingInterface(gameovertext);
		gameovertext.setText("Game Over!");
		gameovertext.setScale(.3f, .3f);
		rootNode.setLocalX(-.9f);
		rootNode.setLocalY(-.15f);
		universe.getHUD().addChild(rootNode);
		this.player = p;
	}

	@Override
	public void createObject(Universe universe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		universe.getHUD().removeChild(rootNode);
		
		try {
			universe.getGameEngine().ChangeGameState(new HighScoreState(player.score));
		} catch (Exception e) {
			System.out.println("GameOver::destroy: Couldn't change to high score state.");
		}
		
		
	}
	Player player;

}
