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
		else if(maxZombieSpeed > zombieSpeed)
		{
			zombieSpeed += .1f;
		}
		else if(maxZombieHealth > zombieHealth)
		{
			zombieHealth += .2f;
		}
		else if(maxZombieSpeed > zombieSpeed)
		{
			zombieSpeed += .2f;
		}
		wavelength += 500;
		
	}
	
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	Wave currentWave = null;
	Player player;
	
	int maxNumberOfSpawners = 5;
	float maxZombieSpeed = 2.1f;
	float maxZombieHealth = 5.0f;
	long wavelength = 30000; // first wave 30 seconds
	int numSpawners = 1;
	float zombieHealth = .5f; 
	float zombieSpeed = .8f;
}
