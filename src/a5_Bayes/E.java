package a5_Bayes;

import java.util.ArrayList;
import java.util.HashMap;

public class E {
	public ArrayList<String> parents = new ArrayList<String>();
	public ArrayList<String> children = new ArrayList<String>();
	public HashMap<String, Double> cpt = new HashMap<String, Double>();
	
	E() {
		children.add("A");
		cpt.put("T", .03d);
	}
}
