package ca.utoronto.utm.assignment1.othello;
import java.util.ArrayList;

/**
 * PlayerGreedy makes a move by considering all possible moves that the player
 * can make. Each move leaves the player with a total number of tokens.
 * getMove() returns the first move which maximizes the number of
 * tokens owned by this player. In case of a tie, between two moves,
 * (row1,column1) and (row2,column2) the one with the smallest row wins. In case
 * both moves have the same row, then the smaller column wins.
 * 
 * Example: Say moves (2,7) and (3,1) result in the maximum number of tokens for
 * this player. Then (2,7) is returned since 2 is the smaller row.
 * 
 * Example: Say moves (2,7) and (2,4) result in the maximum number of tokens for
 * this player. Then (2,4) is returned, since the rows are tied, but (2,4) has
 * the smaller column.
 * 
 * See the examples supplied in the assignment handout.
 * 
 * @author arnold
 *
 */

public class PlayerGreedy {
	private Othello othello;
	private char player;

	/**
	 * Constructs a new PlayerGreedy with a new Othello game.
	 * @param othello	Othello game
	 * @param player	P1 or P2
	 */
	public PlayerGreedy(Othello othello, char player) {
		this.othello = othello;
		this.player = player;
	}
	
	/**
	 * returns the first move that maximizes the number of tokens owned by this 
	 * player. In case of a tie, between two moves, (row1,column1) and 
	 * (row2,column2) the one with the smallest row wins. In case both moves 
	 * have the same row, then the smaller column wins.
	 * 
	 * @return Move(row, col)
	 */
	public Move getMove() {
		int row = getMoveHelper()[0];
		int col = getMoveHelper()[1];
		return new Move(row, col);
	}

	/**
	 * Helper function for getMove() that iterates through the board to find the
	 * move that maximizes the number of player tokens.
	 * 
	 * @return int[] Array of [row, col]
	 */
	private int[] getMoveHelper() {
		int maxFlipped = 0;
		int[] greedyCoord = new int[2];
		ArrayList<int[]> moves = new ArrayList<int[]>();
		// iterate through board 
		for (int row = 0; row < othello.ob.getDimension(); row++) {
			for (int col = 0; col < othello.ob.getDimension(); col++) {
				for (int drow = -1; drow <= 1; drow++) {
					for (int dcol = -1; dcol <= 1; dcol++) {
						int currFlipped = othello.ob.flipCheck(row, col, drow, dcol, player, 0);
						boolean currhasMove = othello.ob.playerhasMove(row - drow, col - dcol, drow, dcol, player);
						if (currFlipped > 0 && currhasMove) {
							boolean updated = false;
							int[] temp = new int[3]; // must declare here for it to be diff every time 
							temp[0] = row - drow;
							temp[1] = col - dcol;
							temp[2] = currFlipped;
							for (int i = 0; i < moves.size(); i++) {
								if (moves.get(i)[0] == row - drow && moves.get(i)[1] == col - dcol) {
									temp[2] += moves.get(i)[2];
									moves.set(i, temp);
									updated = true;
								} 
							}
							if (!updated) {
								moves.add(temp);
							}
						}
					}
				}
			}
		}
		// choose move with max flips
		for (int i = 0; i < moves.size(); i++) {
			if (moves.get(i)[2] > maxFlipped) {
				maxFlipped = moves.get(i)[2];
				greedyCoord[0] = moves.get(i)[0];
				greedyCoord[1] = moves.get(i)[1];
			} else if (moves.get(i)[2] == maxFlipped) {
				if (greedyCoord[0] > moves.get(i)[0]) {
					greedyCoord[0] = moves.get(i)[0];
					greedyCoord[1] = moves.get(i)[1];
				} else if (greedyCoord[0] == moves.get(i)[0] && greedyCoord[1] > moves.get(i)[1]) {
					greedyCoord[0] = moves.get(i)[0];
					greedyCoord[1] = moves.get(i)[1];
				}
			}
		}
		return greedyCoord;
	}
}
						