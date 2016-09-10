package app.util;

import java.util.List;

public class ListUtil {

	public static <T> boolean isValid(List<T> list){
		if(list == null || list.isEmpty()){
			return false;
		}else{
			return true;
		}

	}
}
