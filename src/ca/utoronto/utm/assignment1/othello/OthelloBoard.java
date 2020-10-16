package ca.utoronto.utm.assignment1.othello;

/**
 * Keep track of all of the tokens on the board. This understands some
 * interesting things about an Othello board, what the board looks like at the
 * start of the game, what the players tokens look like ('X' and 'O'), whether
 * given coordinates are on the board, whether either of the players have a move
 * somewhere on the board, what happens when a player makes a move at a specific
 * location (the opposite players tokens are flipped).
 * 
 * Othello makes use of the OthelloBoard.
 * 
 * @author arnold
 *
 */
public class OthelloBoard {
	
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
	private int dim = 8;
	private char[][] board;

	/**
	 * Constructs a new OthelloBoard with dimension dim, and sets all spots
	 * on the board to EMPTY except for the starting 4 spots in the middle,
	 * laid out in the way that Othello games start
	 * @param dim	dimension of the game
	 */
	public OthelloBoard(int dim) {
		this.dim = dim;
		board = new char[this.dim][this.dim];
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				this.board[row][col] = EMPTY;
			}
		}
		int mid = this.dim / 2;
		this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
		this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
	}

	/**
	 * Returns the dimension of the OthelloBoard game
	 * @return P2 or P1, the opposite of player
	 */
	public int getDimension() {
		return this.dim;
	}

	/**
	 * Returns the opposite player
	 * @param player either P1 or P2
	 * @return P2 or P1, the opposite of player
	 */
	public static char otherPlayer(char player) {
		if (player == OthelloBoard.P1) {
			return OthelloBoard.P2;
		} else if(player == OthelloBoard.P2) {
			return OthelloBoard.P1;
		} 
		return EMPTY;
	}	

	/**
	 * Returns the player at (row, col), EMPTY if no player exists at (row, col)
	 * or if (row, col) is an invalid move.
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {
		if (validCoordinate(row, col)) {
			return this.board[row][col];
		} else {
			return EMPTY;
		}
	}

	/**
	 * Returns whether (row, col) is a valid coordinate on the OthelloBoard
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return whether (row,col) is a position on the board. Example: (6,12) is not
	 *         a position on the board.
	 */
	private boolean validCoordinate(int row, int col) {
		if (row < this.dim && row >= 0 && col < this.dim && col >= 0) {
				return true;
			} return false;
	}

	/**
	 * Check if there is an alternation of P1 next to P2, starting at (row,col) in
	 * direction (drow,dcol). That is, starting at (row,col) and heading in
	 * direction (drow,dcol), you encounter a sequence of at least one P1 followed
	 * by a P2, or at least one P2 followed by a P1. The board is not modified by
	 * this method. Why is this method important? If
	 * alternation(row,col,drow,dcol)==P1, then placing P1 right before (row,col),
	 * assuming that square is EMPTY, is a valid move, resulting in a collection of
	 * P2 being flipped.
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1, if there is an alternation P2 ...P2 P1, or P2 if there is an
	 *         alternation P1 ... P1 P2 in direction (dx,dy), EMPTY if there is no
	 *         alternation
	 */
	private char alternation(int row, int col, int drow, int dcol) {
		if (drow == 0 && dcol == 0) {
			return EMPTY;
		}
		if (this.board[row][col] == EMPTY) {
			return EMPTY;
		}
		if (!validCoordinate(row - drow, col - dcol)) { //can place token behind
			return EMPTY;
		}
		if (!validCoordinate(row + drow, col + dcol)) {
			return EMPTY;
		}
		char curr = this.board[row][col];
		char next = this.board[row + drow][col + dcol];
		if (next == curr) {
			return alternation(row + drow, col + dcol, drow, dcol);
			
		} else {
			return next;
		} 
	}
	

	/**
	 * flip all other player tokens to player, starting at (row,col) in direction
	 * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
	 * result in a flip to OOOO
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   the row direction, in {-1,0,1}
	 * @param dcol   the col direction, in {-1,0,1}
	 * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               flip to.
	 * @return the number of other player tokens actually flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	private int flip(int row, int col, int drow, int dcol, char player) { 
		if (drow == 0 && dcol == 0) {
			return -1;
		} 
		return flipHelper(row, col, drow, dcol, player, 0);
	}
	
	/**
	 * Helper function to flip other player tokens starting at (row,col) in direction
	 * (drow, dcol)on OthelloBoard.
	 * 
	 * @param row   	starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col   	starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   	the row direction, in {-1,0,1}
	 * @param dcol   	the col direction, in {-1,0,1}
	 * @param player 	Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               	flip to.
	 * @param flipped	number of flipped tokens, starting at 0
	 * @return the number of other player tokens actually flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	private int flipHelper(int row, int col, int drow, int dcol, char player, int flipped) {
		char curr = this.board[row][col];
		if (!validCoordinate(row, col)) {
			return -1;
		}
		if (curr == player) {
			return flipped;
		} else if (curr == otherPlayer(player)) {
			this.board[row][col] = player;
			if (validCoordinate(row + drow, col + dcol)) {
				return flipHelper(row + drow, col + dcol, drow, dcol, player, flipped + 1);
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}
	
	/**
	 * check how many other player tokens can be flipped to player, starting at 
	 * (row,col) in direction (drow, dcol). Example: 
	 * 
	 * @param row    	starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    	starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   	the row direction, in {-1,0,1}
	 * @param dcol   	the col direction, in {-1,0,1}
	 * @param player	Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               	flip to.
	 * @param flipped	number of flipped tokens, starting at 0
	 * @return the number of other player tokens that can be flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	public int flipCheck(int row, int col, int drow, int dcol, char player, int flipped) {
		if (drow == 0 && dcol == 0) {
			return -1;
		} 
		
		if (!validCoordinate(row, col)) {
			return -1;
		}
		char curr = this.board[row][col];
		if (curr == player) {
			return flipped;
		} else if (curr == otherPlayer(player)) {
			if (validCoordinate(row + drow, col + dcol)) {
				return flipCheck(row + drow, col + dcol, drow, dcol, player, flipped + 1);
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

	/**
	 * Return which player has a move (row,col) in direction (drow,dcol).
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1,P2,EMPTY
	 */
	private char hasMove(int row, int col, int drow, int dcol) { // create getter to access private method
		if (drow == 0 && dcol == 0) {
			return EMPTY;
		}
		if (!validCoordinate(row + drow, col + dcol)) {
			return EMPTY;
		}
		if (!validCoordinate(row, col)) {
			return EMPTY;
		}
		char start = this.board[row][col];
		if (start != EMPTY) {
			return EMPTY;
		}
		return alternation(row + drow, col + dcol, drow, dcol);
	}
	
	/**
	 * Return if a player has a move (row,col) in direction (drow,dcol).
	 * 
	 * @param row  		starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  		starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow 		the row direction, in {-1,0,1}
	 * @param dcol 		the col direction, in {-1,0,1}
	 * @param player	the player to be checked for a move at (row, col)
	 * @return true, false
	 */
	// public getter method to call on private method
	public boolean playerhasMove(int row, int col, int drow, int dcol, char player) {
		return hasMove(row, col, drow, dcol) == player || hasMove(row, col, drow, dcol) == BOTH;
	} 

	/**
	 * 
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
	public char hasMove() {
		int p1 = 0;
		int p2 = 0;
		if (getCount(P1) == 0) { // no moves can be made if no opponent tokens
			return EMPTY;
		} else if (getCount(P2) == 0) {
			return EMPTY;
		}
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				for (int drow = -1; drow <= 1; drow++) {
					for (int dcol = -1; dcol <= 1; dcol++) {
						if (hasMove(row, col, drow, dcol) == P1) {
							p1++;
						} else if (hasMove(row, col, drow, dcol) == P2) {
							p2++;
						} 
					}
				}
			}
		}
		if (p1 > 0 && p2 > 0) {
			return BOTH;
		} else if (p1 > 0 && p2 == 0) {
			return P1;
		} else if (p2 > 0 && p1 == 0) {
			return P2;
		} else {
			return EMPTY;
		}
	}

	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * making appropriate modifications to the board. Nothing is changed if this is
	 * not a valid move.
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	public boolean move(int row, int col, char player) {
		if (this.board[row][col] != EMPTY) {
			return false;
		} 
		boolean flipped = false;
		if (hasMove() == otherPlayer(player)) {
			return false;
		}
		int flip_return = 0;
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				if (hasMove(row, col, drow, dcol) == player || hasMove(row, col, drow, dcol) == BOTH) {
					flip_return = flip(row + drow, col + dcol, drow, dcol, player);
					if (flip_return != -1) {
						flipped = true;
					}
				}
			}
		} 
		if (flipped){
				this.board[row][col] = player;
		}
		return flipped;
	}

	/**
	 * Return the number of player tokens are on the OthelloBoard
	 * @param player P1 or P2
	 * @return the number of tokens on the board for player
	 */
	public int getCount(char player) {
		int count = 0;
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				if (this.board[row][col] == player) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information. DO NOT MODIFY THIS!!
	 */
	public String toString() {
		/**
		 * See assignment web page for sample output.
		 */
		String s = "";
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';

		s += " +";
		for (int col = 0; col < this.dim; col++) {
			s += "-+";
		}
		s += '\n';

		for (int row = 0; row < this.dim; row++) {
			s += row + "|";
			for (int col = 0; col < this.dim; col++) {
				s += this.board[row][col] + "|";
			}
			s += row + "\n";

			s += " +";
			for (int col = 0; col < this.dim; col++) {
				s += "-+";
			}
			s += '\n';
		}
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';
		return s;
	}

	/**
	 * A quick test of OthelloBoard. Output is on assignment page.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloBoard ob = new OthelloBoard(8);
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));
		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				ob.board[row][col] = P1;
			}
		}
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));

		// Should all be blank
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				if (row == 0 || col == 0) {
					ob.board[row][col] = P2;
				}
			}
		}
		System.out.println(ob.toString());

		// Should all be P2 (O) except drow=0,dcol=0
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		// Can't move to (4,4) since the square is not empty
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));

		ob.board[4][4] = EMPTY;
		ob.board[2][4] = EMPTY;

		System.out.println(ob.toString());

		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("hasMove at (4,4) in above direction =" + ob.hasMove(4, 4, drow, dcol));
			}
		}
		System.out.println("who has a move=" + ob.hasMove());
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
		System.out.println(ob.toString());

	}
}
