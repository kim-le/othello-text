package ca.utoronto.utm.assignment1.othello;

import java.util.ArrayList;
import java.util.Random;

/**
 * PlayerRandom makes a move by first determining all possible moves that this
 * player can make, putting them in an ArrayList, and then randomly choosing one
 * of them.
 * 
 * @author arnold
 *
 */
public class PlayerRandom {
	private Random rand = new Random();
	private Othello othello;
	private char player;

	/**
	 * Constructs a new PlayerRandom with a new Othello game.
	 * @param othello	Othello game
	 * @param player	P1 or P2
	 */
	public PlayerRandom(Othello othello, char player) {
		this.othello = othello;
		this.player = player;
	}
	
	/**
	 * returns a randomly chosen move from an ArrayList of all possible moves 
	 * for player
	 * 
	 * @return Move(row, col)
	 */
	public Move getMove() {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int row = 0; row < othello.ob.getDimension(); row++) {
			for (int col = 0; col < othello.ob.getDimension(); col++) {
				for (int drow = -1; drow <= 1; drow++) {
					for (int dcol = -1; dcol <= 1; dcol++) {
						if (othello.ob.playerhasMove(row, col, drow, dcol, player)) {
							Move temp = new Move(row, col); // if player has a move at (row, col), add to ArrayList
							if (!moves.contains(temp)) { // uses equals()
								moves.add(temp);
							}
						}
					}
				}
			}
		}
		int i = rand.nextInt(moves.size());
		return moves.get(i);
	}
}
	