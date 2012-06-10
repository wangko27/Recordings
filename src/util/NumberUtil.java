package util;

import java.util.Random;

public class NumberUtil {
	
	/**
	 * Returns the next highest value after the start param
	 * that is a multiple of the mod param.
	 * 
	 * Ex. start=4, mod=5, RETURN 5
	 * Ex. start=17, mod=5 RETURN 20
	 * 
	 * @param start
	 * @param mod
	 * @return
	 */
	public static int getNextHighestModValue( int start, int mod )
	{
		int nextHighest = start;
		
		while( (nextHighest % mod) != 0 )
			nextHighest++;
		
		return nextHighest;
	}
	
	/**
	 * Returns a random number from 1 to max
	 * @param max
	 * @return
	 */
	public static int getRandomNumber( int max )
	{
		Random generator = new Random();
		
		int randomNumber = generator.nextInt(max + 1);
		
		if( randomNumber == 0 )
			randomNumber = getRandomNumber(max);
		
		return randomNumber;
	}
}
