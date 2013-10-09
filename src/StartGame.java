import java.util.concurrent.CopyOnWriteArrayList;

// I think this is how I do this... 
public class StartGame implements IGameState
{
	public StartGame()
	{
		gameObjs = new CopyOnWriteArrayList<GameObject>();
	}
	@Override
	public void Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game) {
		// TODO Auto-generated method stub
		this.gfx  = gfx;
		this.snd  = snd;
		this.game = game;
	}

	@Override
	public void Quit() {
		// TODO Auto-generated method stub
		
	}

	// maybe this need to have delta not Draw()?
	@Override
	public void Update() {
		// TODO Auto-generated method stub
		for(int i = 0; i < gameObjs.size(); i++)
		{
			gameObjs.get(i).update(1/25); // 25 fps?
		}
	}

	@Override
	public void Draw(float delta) {
		// TODO Auto-generated method stub
		for(int i = 0; i < gameObjs.size(); i++)
		{
			gameObjs.get(i).draw(); // draw asap
		}
	}

	ITextureEngine	gfx;
	IAudioEngine	snd;
	IGameEngine		game;
	
	CopyOnWriteArrayList<GameObject> gameObjs;
	
}
