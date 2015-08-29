package java.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMessageManager {
	public static void main(String args[]) {
		HashMap<String, String> messages = new HashMap<String, String>();
		messages.put("A", "A");
		messages.put("B", "B");
		messages.put("A", "A");

		List<String> itemsmac = new ArrayList<String>();
		itemsmac.add("lsdfsdf");
		itemsmac.add("sdfas");
		
		String[] obString=new String[2];
		itemsmac.toArray(obString);

		System.out.println(obString[0]);
	}
}
