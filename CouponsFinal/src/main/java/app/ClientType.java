package app;

import java.util.HashMap;
import java.util.Map;

public enum ClientType {
	
	Administrator(1),Company(2),Customer(3);
	
	private int value;
	private static Map<Integer,ClientType> map = new HashMap<>();
	
	private ClientType(int value) {
		//allows  Category Types to have values
		this.value = value;
	}
	
	static {
		//puts Category Types & their values inside a hashMap, value=key
		for (ClientType clientType : ClientType.values()) {
			map.put(clientType.value, clientType);
		}
	}

	public static ClientType valueOf(int value) {
		//Receives value/key returns its ClientType
		return (ClientType) map.get(value);
	}

	public int getValue() {
		return value;
	}

}