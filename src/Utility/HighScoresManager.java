/*	ZombieGame
*/

package Utility;



public class HighScoresManager
{
	public HighScoresManager(int maxScores)
	{
		maxScores_ = maxScores;
		scoreNames_ = new String [maxScores_];
		scoreValues_ = new int [maxScores_];
		
		for (int i=0; i < maxScores_; i++)
		{
			scoreNames_[i] = "AAA";
			scoreValues_[i] = (i+1)*1000;
		}
	}
	
	
	/**
	 * Check if a score will make the list.
	 * @return The place in the list the score will make, or -1 if it does not make the list
	 */
	public int	CheckScorePlace(int score)
	{
		int i = 0;
		while (i < maxScores_ && score > scoreValues_[i])
			i++;
		
		return i-1;
	}
	
	
	/**
	 * Add a high score to the list.
	 */
	public void	AddScore(String name, int score)
	{
		int place = CheckScorePlace(score);
		
		if (place == -1)
			return;
		
		for (int i=0; i < place; i++)
		{
			scoreValues_[i] = scoreValues_[i+1];
			scoreNames_[i] = scoreNames_[i+1];
		}
		
		scoreValues_[place] = score;
		scoreNames_[place] = name;
	}
	
	
	/**
	 * Returns the number of high score places.
	 */
	public int GetMaxScores()
	{
		return maxScores_;
	}
	
	/**
	 * Return the config variable name for a score value
	 */
	public String GetScoreVariable(int index)
	{
		return "score"+index+"_value";
	}
	
	/**
	 * Return the config variable name for a score name
	 */
	public String GetNameVariable(int index)
	{
		return "score"+index+"_name";
	}
	
	
	/**
	 * Save high scores to a config file.
	 */
	public void SaveScores(ConfigData cfg)
	{
		for (int i=0; i < maxScores_; i++)
		{
			cfg.SetIntValue("score"+i+"_value", scoreValues_[i]);
			cfg.SetStringValue("score"+i+"_name", scoreNames_[i]);
		}
	}
	
	/**
	 * Read max score values from a config file.
	 */
	public void LoadScores(ConfigData cfg)
	{
		Integer s;
		
		for (int i=0; i < maxScores_; i++)
		{
			s = cfg.GetIntValue("score"+i+"_value");
			
			if (s == null)
				return;
			
			scoreValues_[i] = s;
			scoreNames_[i] = cfg.GetStringValue("score"+i+"_name");
		}
	}
	
	//
	//
	//
	
	protected ConfigData cfg_;
	protected int maxScores_;
	
	// the lowest score is at index zero
	protected String[]	scoreNames_;
	protected int[]		scoreValues_;
}












