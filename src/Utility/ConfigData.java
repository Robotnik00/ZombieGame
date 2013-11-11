/* Zombie Game
*/

package Utility;

// imports
import java.util.HashMap;
import java.util.*;
import java.io.*;



/**
 * Create, load, and save a simple configuration file with int, float, and string values attached to keys.
 * 
 * FIXME: return values should be Integer vs int (to handle NULL). 
 */
public class ConfigData
{
	public ConfigData()
	{
		intValues_ = new HashMap<String,Integer>();
		floatValues_ = new HashMap<String,Float>();
		stringValues_ = new HashMap<String,String>();
		filename_ = "";
	}
	
	/**
	 * Load a config file.
	 * @param filename
	 * @throws Exception
	 */
	public void	LoadFile(String filename) throws Exception
	{
		// parse a save file: ints, floats, then strings
		int numKeys, intValue;
		float floatValue;
		String stringValue;
		String line;
		
		BufferedReader fin;
		fin = new BufferedReader(new FileReader(filename));
		
		// save file name for saving later (if we were able to open the file)
		filename_ = filename;
		
		// read # of keys
		line = fin.readLine();
		numKeys = Integer.parseInt(line);
		
		// read int keys
		for (int i=0; i < numKeys; i++)
		{
			line = fin.readLine();	// key
			intValue = Integer.parseInt(fin.readLine()); // value
			intValues_.put(line, intValue);
		}
		
		// read # of float keys
		line = fin.readLine();
		numKeys = Integer.parseInt(line);
		
		// read float values
		for (int i=0; i < numKeys; i++)
		{
			line = fin.readLine();	// key
			floatValue = Float.parseFloat(fin.readLine()); // value
			floatValues_.put(line, floatValue);
		}
		
		// read # of string keys
		line = fin.readLine();
		numKeys = Integer.parseInt(line);
		
		// read string values
		for (int i=0; i < numKeys; i++)
		{
			line = fin.readLine();	// key
			stringValue = fin.readLine(); // value
			stringValues_.put(line, stringValue);
		}
		
		// end
		fin.close();
	}
	
	/**
	 * Save the loaded config file.
	 */
	public void	SaveFile() throws Exception
	{
		SaveFile(filename_);
	}
	
	/**
	 * Save the config file as a new file.
	 * @param filename
	 * @throws Exception
	 */
	public void SaveFile(String filename) throws Exception
	{
		// save a list of ints, floats, and strings
		
		BufferedWriter fout = new BufferedWriter(new FileWriter(filename));
		ArrayList<String> keys;
		
		// write # of int keys
		fout.write(intValues_.size() + "\n");
		
		// write keys and values
		keys = new ArrayList<String>(intValues_.keySet());
		for (int i=0; i < intValues_.size(); i++)
		{
			fout.write(keys.get(i) + "\n");
			fout.write(intValues_.get(keys.get(i)) + "\n");
		}
		
		// write # of float keys
		fout.write(floatValues_.size() + "\n");
		
		// write keys and values
		keys.clear();
		keys = new ArrayList<String>(floatValues_.keySet());
		for (int i=0; i < floatValues_.size(); i++)
		{
			fout.write(keys.get(i) + "\n");
			fout.write(floatValues_.get(keys.get(i)) + "\n");
		}
		
		// write # of string keys
		fout.write(stringValues_.size() + "\n");
		
		// write keys
		keys.clear();
		keys = new ArrayList<String>(stringValues_.keySet());
		for (int i=0; i < stringValues_.size(); i++)
		{
			fout.write(keys.get(i) + "\n");
			fout.write(stringValues_.get(keys.get(i)) + "\n");
		}
		
		fout.close();
	}
	
	/**
	 * Set an integer config variable. If the key is not found, it will be added with the associated value given.
	 * @param key
	 * @param value
	 */
	public void	SetIntValue(String key, int value)
		{	intValues_.put(key, value);	}
	
	/**
	 * Find an integer config variable.
	 * @param key
	 * @return
	 */
	public int GetIntValue(String key)
		{	return intValues_.get(key);	}
	
	/**
	 * Set a float config variable. If the key is not found, it will be added with the associated value given.
	 * @param key
	 * @param value
	 */
	public void	SetFloatValue(String key, float value)
		{	floatValues_.put(key, value);	}
	
	/**
	 * Find a float config variable.
	 * @param key
	 * @return
	 */
	public float GetFloatValue(String key)
		{	return floatValues_.get(key);	}
	
	/**
	 * Set a string config variable. If the key is not found, it will be added with the associated value given.
	 * @param key
	 * @param value
	 */
	public void	SetStringValue(String key, String value)
		{	stringValues_.put(key, value);	}
	
	/**
	 * Find a string config variable.
	 * @param key
	 * @return
	 */
	public String GetStringValue(String key)
		{	return stringValues_.get(key);	}
	
	//
	// protected members
	//
	
	protected String	filename_;
	
	// data values
	protected HashMap<String,Integer>	intValues_;
	protected HashMap<String,Float>		floatValues_;
	protected HashMap<String,String>	stringValues_;
}