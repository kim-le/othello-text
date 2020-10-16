package ca.utoronto.utm.assignment1.othello;

import static org.junit.Assert.assertEquals;

import java.util.Random;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 * 
 * @author arnold
 *
 */
public class Othello {
	public static final int DIMENSION = 8; // This is an 8x8 game
	private char whosTurn = OthelloBoard.P1; // P1 moves first!
	private int numMoves = 0;
	public OthelloBoard ob = new OthelloBoard(DIMENSION);

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		if (ob.hasMove() == OthelloBoard.BOTH || ob.hasMove() == this.whosTurn) {
			return this.whosTurn;
		} else if (ob.hasMove() == OthelloBoard.otherPlayer(this.whosTurn)) {
			this.whosTurn = OthelloBoard.otherPlayer(this.whosTurn);
			return this.whosTurn;
		} return OthelloBoard.EMPTY;
		
	}

	/**
	 * Attempt to make a move for P1 or P2 (depending on who's turn it is) at
	 * position row, col. A side effect of this method is modification of who's turn
	 * and the move count. 
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
		char player = getWhosTurn();
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				if (ob.get(row + drow, col + dcol) == OthelloBoard.otherPlayer(player)) {
					if (ob.move(row, col, player)) {
						this.whosTurn = OthelloBoard.otherPlayer(player);
						this.numMoves++;
						return true;
					}
				}
			}
		} 
		return false;
	}

	/**
	 * Return the number of player tokens are on the OthelloBoard
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		return ob.getCount(player);
	}
	
	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		int p1_count = getCount(OthelloBoard.P1);
		int p2_count = getCount(OthelloBoard.P2);
		if (!isGameOver()) {
			return OthelloBoard.EMPTY; 
		}
		if (p1_count > p2_count) {
			return OthelloBoard.P1;
		} else if (p1_count < p2_count) {
			return OthelloBoard.P2;
		} 
		return OthelloBoard.EMPTY;
	}

	/**
	 * Returns true if the game is over, false if there exists no moves 
	 * that can be played.
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		return getWhosTurn() == OthelloBoard.EMPTY;
	}

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return ob.toString();
	}

	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random rand = new Random();

		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while (!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if (o.move(row, col)) {
				System.out.println("makes move (" + row + "," + col + ")");
				System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
			}
		}
	}
}