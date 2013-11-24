package Actions;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector4f;

import Drawing.DrawText;
import Drawing.SimpleDraw;
import GameObjects.GameObject;
import GameObjects.Player;
import GameObjects.Powerup;
import TextureEngine.ITexture;

public class UpdateHUD implements Action
{
	public UpdateHUD(Player player)
	{
		this.player = player;
		/*
		GameObject background = new GameObject();
		SimpleDraw drawBackground = new SimpleDraw(player.getUniverse().getTextureEngine().LoadTexture("gfx/white.png", 1));
		background.setDrawingInterface(drawBackground);
		player.getUniverse().getHUD().addChild(background);
		background.scale(2, .2f);
		background.setLocalY(.75f);
		drawBackground.setColor(new Vector4f(0,0,0,.5f));
		*/
		
		GameObject HP = new GameObject();
		GameObject hpText = new GameObject();
		hpBarRoot = new GameObject();
		hpBar = new GameObject();
		
		DrawText hptext = new DrawText(player.getUniverse().getTextureEngine(), "gfx/font.png");
		hpText.setDrawingInterface(hptext);
		hptext.setText("HP:");
		HP.translate(-5f, 0);
		HP.scale(.2f, .2f);
		
		HP.addChild(hpText);
		HP.addChild(hpBarRoot);
		hpBarRoot.addChild(hpBar);
		
		
		ITexture hpbartex = player.getUniverse().getTextureEngine().LoadTexture("gfx/Characters/HUD/HealthBar.png", 0);
		
		SimpleDraw hpbar = new SimpleDraw(hpbartex);
		hpBar.setDrawingInterface(hpbar);
		hpbar.setScale(10, 4); 
		hpBar.translate(2.2f, 0);
		
		hpBarRoot.translate(2.2f, .4f);
		

		player.getUniverse().getHUD().addChild(HP);
		HP.scale(.25f, .25f);
		
		HP.translate(-15, 14);
		
		
		GameObject score = new GameObject();
		scoretext = new DrawText(player.getUniverse().getTextureEngine(), "gfx/font.png");
		score.setDrawingInterface(scoretext);
		player.getUniverse().getHUD().addChild(score);
		score.scale(.05f,.05f);
		score.translate(-12, 14);
		
		powerupNode = new GameObject();
		powerupNode.scale(.2f, .2f);
		powerupNode.setLocalY(.7f);
		
		
		player.getUniverse().getHUD().addChild(powerupNode);
		prevPowerups = new GameObject[0];
	}
	@Override
	public void performAction() 
	{
		String score = "score: " + player.getScore();
		scoretext.setText(score);
		
		hpBarScale = player.getHp()/player.getMaxHp();
		if(player.getHp() < 0)
		{
			hpBarScale = 0;
		}
		hpBarRoot.scale(1/prevHpBarScale, 1);
		hpBarRoot.scale(hpBarScale, 1);
		prevHpBarScale = hpBarScale;
		
		ArrayList<Powerup> powerups = new ArrayList<Powerup>();
		
		for(int i = 0; i < prevPowerups.length; i++)
		{
			powerupNode.removeChild(prevPowerups[i]);
		}
		
		prevPowerups = new GameObject[player.getPowerups().size()];
		
		int x = 0;
		int y = 0;
		for(int i = 0; i < player.getPowerups().size(); i++)
		{
			if(i >= 6)
			{
				y = 1;
				x = 6;
			}
			powerups.add(player.getPowerups().get(i));
			powerups.get(i).getRootNode().setLocalX(.7f*(i - x));
			powerups.get(i).getRootNode().setLocalY(-y*.7f);
			powerupNode.addChild(powerups.get(i).getRootNode());
			prevPowerups[i] = powerups.get(i).getRootNode();
		}
	}
	
	GameObject[] prevPowerups;
	
	Player player;
	
	GameObject hpBarRoot;
	GameObject hpBar;
	GameObject powerupNode;
	DrawText scoretext;
	
	float prevHpBarScale = 1;
	float hpBarScale = 1;
}
