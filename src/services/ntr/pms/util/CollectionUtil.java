package services.ntr.pms.util;

import java.lang.reflect.Array;

/**
 * Utility class which contain helper method when dealing with arrays
 * @author NYPD
 *
 */
public class CollectionUtil{

	@SuppressWarnings("unchecked")
	public static <E> E[] getEmptyArrayIfNull(E[] array){
		
		boolean isNull = array == null;
		if(isNull) array = (E[]) Array.newInstance(array.getClass(), 0);
		
		return array;
	}
}
