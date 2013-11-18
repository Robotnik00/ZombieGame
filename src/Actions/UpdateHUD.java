package Actions;

import Drawing.DrawText;
import Drawing.SimpleDraw;
import GameObjects.GameObject;
import GameObjects.Player;
import TextureEngine.ITexture;

public class UpdateHUD implements Action
{
	public UpdateHUD(Player player)
	{
		this.player = player;
		GameObject HP = new GameObject();
		GameObject hpText = new GameObject();
		hpBarRoot = new GameObject();
		hpBar = new GameObject();
		
		DrawText hptext = new DrawText(player.getUniverse().getTextureEngine(), "font1.png");
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
		hpBar.translate(1.9f, 0);
		
		hpBarRoot.translate(2.2f, .4f);
		

		player.getUniverse().getHUD().addChild(HP);
		HP.scale(.25f, .25f);
		
		HP.translate(-15, 12);
		
		
		GameObject score = new GameObject();
		scoretext = new DrawText(player.getUniverse().getTextureEngine(), "font1.png");
		score.setDrawingInterface(scoretext);
		player.getUniverse().getHUD().addChild(score);
		score.scale(.05f,.05f);
		score.translate(-12, 12);
		
		
		
	}
	@Override
	public void performAction() 
	{
		String score = "score: " + (float)Math.round(player.getScore()*1000)/1000f;
		scoretext.setText(score);
		
		hpBarScale = player.getHp()/player.getMaxHp();
		hpBarRoot.scale(1/prevHpBarScale, 1);
		hpBarRoot.scale(hpBarScale, 1);
		prevHpBarScale = hpBarScale;
	}
	Player player;
	
	GameObject hpBarRoot;
	GameObject hpBar;
	DrawText scoretext;
	
	float prevHpBarScale = 1;
	float hpBarScale = 1;
}
