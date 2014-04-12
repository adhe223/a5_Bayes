package a5_Bayes;

public class Sample {
	public boolean boolB;
	public boolean boolE;
	public boolean boolA;
	public boolean boolJ;
	public boolean boolM;
	
	public boolean getCorrBool(String var) {
		if (var.equals("B")) {
			return boolB;
		}
		else if (var.equals("E")){
			return boolE;
		}
		else if (var.equals("A")){
			return boolA;
		}
		else if (var.equals("J")){
			return boolJ;
		}
		else if (var.equals("M")){
			return boolM;
		} else {
			throw new RuntimeException(var + " is not a valid var!");
		}
	}
	
}
