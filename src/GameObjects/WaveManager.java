package GameObjects;

import Actions.Action;

public class WaveManager extends Entity
{

	public WaveManager(Universe universe, Player player) 
	{
		super(universe);
		this.player = player;
		nextWave();
	}

	@Override
	public void createObject(Universe universe) 
	{
		Action waves = new Action()
		{

			@Override
			public void performAction() 
			{
				if(currentWave != null && currentWave.isDestroyed() && Zombie.zombies.size() == 0)
				{
					nextWave();
				}
			}
			
		};
		
		
		rootNode.addAction(waves);
	}
	
	
	
	public void nextWave()
	{
		if(currentWave != null)
		{
			currentWave = new Wave(universe, player, currentWave.getWaveNumber()+1);
		}
		else 
		{
			currentWave = new Wave(universe, player, 1);
		}
		
		currentWave.setHP(zombieHealth);
		currentWave.setNumSpawners(numSpawners);
		currentWave.setZombieSpeed(zombieSpeed);
		currentWave.setWaveLength(wavelength);
		
		if(maxNumberOfSpawners > numSpawners)
		{
			numSpawners++;
		}
		if(maxZombieSpeed > zombieSpeed)
		{
			zombieSpeed += .2f;
		}
		if(maxZombieHealth > zombieHealth)
		{
			zombieHealth += .2f;
		}
		wavelength += 500;
		
	}
	
	
	@Override
	public void destroy() {
		// clean this up so we can start fresh for each new game.
		currentWave = null;
		
	}
	Wave currentWave = null;
	Player player;
	
	int maxNumberOfSpawners = 6;
	float maxZombieSpeed = 2.1f;
	float maxZombieHealth = 5.0f;
	long wavelength = 30000; // first wave 30 seconds
	int numSpawners = 3;
	float zombieHealth = .5f; 
	float zombieSpeed = .8f;
}
