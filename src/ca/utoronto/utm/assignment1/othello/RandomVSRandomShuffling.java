package ca.utoronto.utm.assignment1.othello;

import java.util.Random;

/**
 * Determine whether the first player or second player has the advantage when
 * both are playing a Random Strategy. Use the results to implement a Shuffle
 * method to determine whether the difference in probability is significant.
 * 
 * @author kim le
 *
 */
public class RandomVSRandomShuffling extends OthelloControllerRandomVSRandom {
	
	/**
	 * Run main to execute the simulation where the PlayerRandoms play 5 sets 
	 * of 10000 games against each other. Their probabilities and the means of 
	 * their probabilities are then calculated and printed to the console, before
	 * being shuffled to recalculate their means.
	 * @param args
	 */
	public static void main(String[] args) {
		
		float[] p1winrates = new float[6];
		float[] p2winrates = new float[6];
		for (int n = 0; n < 6; n++) {
			int p1wins = 0, p2wins = 0, numGames = 10000;
			for (int i = 0; i < numGames; i++){
				OthelloControllerRandomVSRandom oc = new OthelloControllerRandomVSRandom();
				char winner = oc.play();
				if (winner == OthelloBoard.P1) {
					p1wins++; 
				} else if (winner == OthelloBoard.P2) {
					p2wins++;
				}
			} 
			
			p1winrates[n] = (float) p1wins / numGames;
			p2winrates[n] = (float) p2wins / numGames;
			
			System.out.println("Trial " + n);
			System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
			System.out.println("Probability P2 wins=" + (float) p2wins / numGames+ "\n");
			
		}
		float p1winrate = 0;
		float p2winrate = 0;
		
		// add all winrates to be divided by total number of sets of games
		for (int k = 0; k < 6; k++) {
			p1winrate += p1winrates[k];
			p2winrate += p2winrates[k];
		}
		float difference = (float)p2winrate / 6 - (float)p1winrate/6;
		System.out.println("Mean before shuffling:");
		System.out.println("Mean P1 wins=" + (float) p1winrate / 6);
		System.out.println("Mean P2 wins=" + (float) p2winrate / 6);
		System.out.println("Difference in means=" + difference + "\n");
	
		
		// shuffling method talked about in Pycon video
		for (int m = 0; m < 10; m++) {
			p1winrate = 0;
			p2winrate = 0;
			for (int j = 0; j < 10; j++) {
				Random rand = new Random();
				int i = rand.nextInt(6);
				float temp = p1winrates[i];
				p1winrates[i] = p2winrates[i];
				p2winrates[i] = temp;
			}
			for (int k = 0; k < 6; k++) {
				p1winrate += p1winrates[k];
				p2winrate += p2winrates[k];
			}
			
			float newDifference = (float) p2winrate / 6 - (float) p1winrate / 6;
			System.out.println("Mean after shuffling:");
			System.out.println("Mean P1 wins=" + (float) p1winrate / 6);
			System.out.println("Mean P2 wins=" + (float) p2winrate / 6);
			System.out.println("Difference in means=" + newDifference + "\n");
		}
	}
		
}

