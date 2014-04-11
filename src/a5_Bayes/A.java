package a5_Bayes;

import java.util.ArrayList;
import java.util.HashMap;

public class A {
	public ArrayList<String> parents = new ArrayList<String>();
	public ArrayList<String> children = new ArrayList<String>();
	public HashMap<String, Double> cpt = new HashMap<String, Double>();
	
	A() {
		parents.add("B");
		parents.add("E");
		children.add("J");
		children.add("M");
		cpt.put("TT", .97d);
		cpt.put("TF", .92d);
		cpt.put("FT", .36d);
		cpt.put("FF", .03d);
	}
}