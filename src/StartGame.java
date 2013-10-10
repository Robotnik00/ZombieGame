import java.util.concurrent.CopyOnWriteArrayList;


// I think this is how I do this... 
// This is the state when the user actually starts playing the game, not when the user 
// launches the game.
public class StartGame implements IGameState
{
	public StartGame()
	{
		gameObjs = new CopyOnWriteArrayList<GameObject>();
		GameObject obj1 = new GameObject();
		GameObject obj2 = new GameObject();
		
		gameObjs.add(obj1);
		obj1.addChild(obj2); // makes obj2's coors relative to obj1
		obj1.setX(5); // sets obj1 to 5 units right of 'Universe' origin because no 'parent'
		obj2.setX(3); // sets obj2 to 3 units right of obj1
		
		
		
		obj1.setRotation((float)Math.PI/2); // test rotation
		obj2.setRotation((float)Math.PI/2); 
	
		obj1.normalizeRotationMatrix();
		obj2.normalizeRotationMatrix();
		
		System.out.printf("\n---------------testing transforms---------------\n");
		System.out.printf("obj1: \n%s\n", obj1.getGlobalTransform().toString());
		System.out.printf("obj2: \n%s", obj2.getGlobalTransform().toString());
		System.out.printf("---------------testing transforms---------------\n\n");
		
	}
	@Override
	public void Init(ITextureEngine gfx, IAudioEngine snd, IGameEngine game) {
		// TODO Auto-generated method stub
		this.gfx  = gfx;
		this.snd  = snd;
		this.game = game;
		game.EndGameLoop(); // quits immediately.
	}

	@Override
	public void Quit() {
		// TODO Auto-generated method stub
		
	}

	// maybe this need to have delta for adjustable update frequency?
	@Override
	public void Update() {
		// TODO Auto-generated method stub]
		
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
	
	ITexture test;
	
	CopyOnWriteArrayList<GameObject> gameObjs;
	
}