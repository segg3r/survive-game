package by.segg3r.services.impl;

import java.io.Serializable;
import java.util.Comparator;

public class VersionComparator implements Comparator<String>, Serializable {

	private static final long serialVersionUID = 2998489347511848656L;

	private static final String VERSION_SPLITTER_REGEX = "\\.";
	
	@Override
	public int compare(String version1, String version2) {
		String[] items1 = version1.split(VERSION_SPLITTER_REGEX);
		String[] items2 = version2.split(VERSION_SPLITTER_REGEX);

		if (items1.length == 0) {
			if (items2.length == 0) {
				return 0;
			}
			return -1;
		} else if (items2.length == 0) {
			return 1;
		}
		
		for (int i = 0; i < items1.length; i++) {
			if (i >= items2.length) {
				return 1;
			}
			
			int item1 = Integer.parseInt(items1[i]);
			int item2 = Integer.parseInt(items2[i]);
			
			int comparisonResult = Integer.compare(item1, item2);
			if (comparisonResult != 0) {
				return comparisonResult;
			}
		}
		
		if (items2.length > items1.length) {
			return -1;
		}
		
		return 0;
	}

}
