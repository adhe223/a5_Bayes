package a5_Bayes;

import java.util.ArrayList;
import java.util.HashMap;

public class M {
	public ArrayList<String> parents = new ArrayList<String>();
	public ArrayList<String> children = new ArrayList<String>();
	public HashMap<String, Double> cpt = new HashMap<String, Double>();
	
	M() {
		parents.add("A");
		cpt.put("T", .69d);
		cpt.put("F", .02d);
	}
}
