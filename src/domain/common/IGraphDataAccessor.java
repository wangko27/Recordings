package domain.common;

import java.util.Collection;
import java.util.Map;

import exception.RecordingException;

/**
 * this interface provides methods for gathering graphable data
 * @author Chris
 *
 */
public interface IGraphDataAccessor
{

	/**
	 * generates a Map which contains each possible value of fieldName, and the number of
	 * occurrences of that value that appear in the database
	 *  
	 * @param fieldName the name of the field to search
	 * @return a Map representing the number of beans that have each value of the column
	 * @throws RecordingException 
	 */
	public Map<Comparable<?>, Integer> groupItemsByCount(String fieldName) throws RecordingException;

	/**
	 * retrieves all of the items in a database
	 * @return a Collection of the items
	 */
	public Collection<?> getAllItems();
	
	/**
	 * gets the type of the bean that this data accessor is handling 
	 * @return a Class<?> representing the bean's class 
	 */
	public Class<?> getDataClass(); 
	
	/**
	 * gets a map of beans, where each key represents the number of times each value in the "variableColumn" contains the "commonValue" in the "commonValueColumn"
	 * @param commonValueColumn the column that each value in the map must have
	 * @param commonValue the value of the column
	 * @param variableColumn the column that will be counted
	 * @return a map suitable for generating a frequency graph. 
	 * @throws RecordingException
	 */
	public Map<Comparable<?>, Integer> groupItemsByCommonValue(String commonValueColumn, Comparable<?> commonValue, String variableColumn) throws RecordingException; 	
	
	/**
	 * returns the display name of the data class 
	 * @return the display name of the data class
	 */
	public String dataClassString(); 
		
}
