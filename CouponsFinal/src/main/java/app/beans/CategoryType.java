package app.beans;

import java.util.HashMap;
import java.util.Map;

public enum CategoryType {

	sport(1), food(2), vacation(3), electronics(4), spa(5);

	private int value;
	private static Map<Integer, CategoryType> map = new HashMap<>();

	private CategoryType(int value) {
		//allows  Category Types to have values
		this.value = value;
	}

	static {
		//puts Category Types & their values inside a hashMap, value=key
		for (CategoryType categoryType : CategoryType.values()) {
			map.put(categoryType.value, categoryType);
		}
	}

	public static CategoryType valueOf(int value) {
		//Receives value/key returns its CategoryType
		return (CategoryType) map.get(value);
	}

	public int getValue() {
		return value;
	}

}
