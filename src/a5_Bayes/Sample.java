package a5_Bayes;

public class Sample {
	public boolean boolB;
	public boolean boolE;
	public boolean boolA;
	public boolean boolJ;
	public boolean boolM;
	
	public boolean getCorrBool(String var) {
		if (var == "B") {
			return boolB;
		}
		else if (var == "E"){
			return boolE;
		}
		else if (var == "A"){
			return boolA;
		}
		else if (var == "J"){
			return boolJ;
		}
		else {
			return boolM;
		}
	}
	
}
