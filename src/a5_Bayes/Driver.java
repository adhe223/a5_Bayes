package a5_Bayes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Driver {	
	private static ArrayList<Sample> samples = new ArrayList<Sample>();
	private static final int NUM_SAMPLES = 500000;
	private static Random random = new Random();
	private static ArrayList<String> varSpace = new ArrayList<String>();
	private static String command = "";
	
	public static void main(String[] args) {
		//Fill list of possible variables
		varSpace.add("B");
		varSpace.add("E");
		varSpace.add("A");
		varSpace.add("J");
		varSpace.add("M");
		
		doSamples();			//Do our initial sampling of the network
		
		//Handle the command line input: True = 1, False = 0, GivenTrue = 2, GivenFalse = -2, Don't Care = -1
		HashMap<String, Integer> inArgs = new HashMap<String, Integer>();
		HashMap<String, Integer> manualArgs = new HashMap<String, Integer>();
		boolean given = false;
	
		//Build the whole command string to be displayed later
		for (String str : args) {
			if (!str.equals("bnet")) {
				command = command + str + " ";
			}
		}
		
		for (String str : args) {
			if (!str.equals("bnet") && !str.equals("given") && !given) {
				//This is a normal argument to set
				int value;
				if (str.substring(1).equals("t")) {
					value = 1;
				} else {
					value = 0;
				}
				
				String key = str.substring(0, 1);
				inArgs.put(key, value);
			} else if (!str.equals("bnet") && !str.equals("given") && given) {
				//This is a given argument to set
				int value;
				if (str.substring(1).equals("t")) {
					value = 2;
				} else {
					value = -2;
				}
				
				String key = str.substring(0, 1);
				inArgs.put(key, value);
			} else if (str.equals("given")) {
				given = true;
			}
		}
		
		//Now add the unspecified args to our hashmap with a value of -1
		for (String str : varSpace) {
			if (!inArgs.containsKey(str)) {
				inArgs.put(str, -1);
			}
		}
		
		System.out.println("The probability of " + command + "is: " + getProb(inArgs, given));
	}
	
	public static void doSamples() {
		int rand;
		
		for (int i = 0; i < NUM_SAMPLES; i++) {
			Sample sample = new Sample();
			
			//Burglary
			rand = random.nextInt(100) + 1;	//Random number between 1-100
			if (rand <= (0.02d * 100)) {
				//True
				sample.boolB = true;
			} else {
				//False
				sample.boolB = false;
			}
			
			//Earthquake
			rand = random.nextInt(100) + 1;	//Random number between 1-100
			if (rand <= (.03d * 100)) {
				//True
				sample.boolE = true;
			} else {
				//False
				sample.boolE = false;
			}
			
			//Alarm
			rand = random.nextInt(100) + 1;	//Random number between 1-100
			if (sample.boolB && sample.boolE) {
				//TT
				if (rand <= (.97d * 100)) {
					sample.boolA = true;
				} else {
					sample.boolA = false;
				}
			} else if (sample.boolB && !sample.boolE) {
				//TF
				if (rand <= (.92d * 100)) {
					sample.boolA = true;
				} else {
					sample.boolA = false;
				}
			} else if (!sample.boolB && sample.boolE) {
				//FT
				if (rand <= (.36d * 100)) {
					sample.boolA = true;
				} else {
					sample.boolA = false;
				}
			} else {
				//FF
				if (rand <= (.03d * 100)) {
					sample.boolA = true;
				} else {
					sample.boolA = false;
				}
			}
			
			//JohnCalls
			rand = random.nextInt(100) + 1;	//Random number between 1-100
			if (sample.boolA) {
				//T
				if (rand <= (.85d * 100)) {
					sample.boolJ = true;
				} else {
					sample.boolJ = false;
				}
			} else {
				//F
				if (rand <= (.07d * 100)) {
					sample.boolJ = true;
				} else {
					sample.boolJ = false;
				}
			}
			
			//MaryCalls
			rand = random.nextInt(100) + 1;	//Random number between 1-100
			if (sample.boolA) {
				//T
				if (rand <= (.69d * 100)) {
					sample.boolM = true;
				} else {
					sample.boolM = false;
				}
			} else {
				//F
				if (rand <= (.07d * 100)) {
					sample.boolM = true;
				} else {
					sample.boolM = false;
				}
			}
			
			//Now add the sample to our collection
			samples.add(sample);
		}
	}
	
	//Takes in an array of ints of the arguments and a bool denoting whether there is a given. 1 is true, 0 is false,
	//-1 means that arg was not set, 2 means that arg was given as true, -2 means the arg was given as false. 1 and 0 are hard constraints. -1 is a wildcard
	public static double getProb(HashMap<String, Integer> args, boolean given) {
		int count = 0;
		
		if (!given) {
			for (Sample s : samples) {
				boolean match = true;
				
				//Set each variable separately depending on the command
				//B
				match = isMatch(args, s, "B");
				
				//E
				if (match) {		//If match is false we can fall through
					match = isMatch(args, s, "E");
				}
				
				//A
				if (match) {		//If match is false we can fall through
					match = isMatch(args, s, "A");
				}
				
				//J
				if (match) {		//If match is false we can fall through
					match = isMatch(args, s, "J");
				}
				
				//M
				if (match) {		//If match is false we can fall through
					match = isMatch(args, s, "M");
				}
				
				if (match) {	//If match is still true then increment the count
					count++;
				}
			}
		} else {
			int givenCount = 0;		//Counts the sample space of B
			//P(A|B) = P(A && B)/P(B)
			
			//Create a list of the given variables
			ArrayList<String> notGivens = new ArrayList<String>();
			ArrayList<String> givens = new ArrayList<String>();
			for (String str : args.keySet()) {
				if (args.get(str) == 2 || args.get(str) == -2) {
					givens.add(str);
				} else {
					notGivens.add(str);
				}
			}
			
			for (Sample s : samples) {
				//We are only concerned about events occuring within the sample space of B.
				//Enter an if block if all of the given variables match
				boolean allMatch = true;
				for (String str : givens) {
					boolean sbool = s.getCorrBool(str);
					boolean argbool = intToBool(args.get(str));
					if ((s.getCorrBool(str) != intToBool(args.get(str))) && allMatch) {		//If allMatch is false then we can fall through
						allMatch = false;
					}
				}
				
				if (allMatch) {
					//This sample space is in B. Now check the variables of A to see if they match
					boolean match = true;
					givenCount++;
					
					for (String str : notGivens) {
						if (match) {		//If match is false we can fall through
							match = isMatch(args, s, str);
						}
					}
					
					if (match) {
						//A && B is true
						count++;
					}
				}
			}
			
			return ((double)count/(double)givenCount);
		}
		
		return (((double)count) / NUM_SAMPLES);
	}
	
	//Handles our method of transcribing the args
	public static boolean intToBool (int i) {
		if (i == 1) {
			return true;
		} else if (i == 2) {
			return true;
		} else if (i == -2) {
			return false;
		} else {
			return false;
		}
	}
	
	public static boolean isMatch(HashMap<String, Integer> args, Sample s, String varToCheck) {
		boolean match = true;
		int i = args.get(varToCheck);
		
		if (i == 0 || i == 1) {		//Hard constraints. It turns out given is a hard constraint just with a different number to divide by to get prob
			if ((s.getCorrBool(varToCheck) != intToBool(i))) {
				match = false;
			}
		} else if (i == 2 || i == -2) {
			throw new RuntimeException("The given should be handled outside of isMatch(...)!");
		} else {	//-1, Wildcard
			match = true;
		} 
		
		return match;
	}
}
