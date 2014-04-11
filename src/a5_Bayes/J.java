package a5_Bayes;

import java.util.ArrayList;
import java.util.HashMap;

public class J {
	public ArrayList<String> parents = new ArrayList<String>();
	public ArrayList<String> children = new ArrayList<String>();
	public HashMap<String, Double> cpt = new HashMap<String, Double>();
	
	J() {
		parents.add("A");
		cpt.put("T", .85d);
		cpt.put("F", .07d);
	}
}
