package uib.info323.twitterAWSM.utils;

import java.util.List;

public class LongConverter {

	/**
	 * @param list
	 * @return long[]
	 */
	public static long[] convertObjLongToPrimLong(List<Long> list) {
		long[] temp = new long[list.size()];
		for (int i = 0; i < list.size(); i++) {
			temp[i] = list.get(i);
		}
		return temp;
	}

}
