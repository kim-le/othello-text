package ca.utoronto.utm.assignment1.othello;

/**
 * Move class has move objects that are used to play the Othello game
 * @author arnold
 *
 */

public class Move {
	private int row, col;
	
	/**
	 * Initialize a new Move object
	 * @param row 
	 * @param col
	 *
	 */
	public Move(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Get the row value of a Move object
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Get the column value of a Move object
	 * @return col
	 */
	public int getCol() {
		return col;
	}
	
	
	/**
	 * Overrides the equals() method to compare two Move objects
	 * @param object from Object class
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Move move = (Move) obj;
		return row == move.getRow() && col == move.getCol();
	}
	
	/**
	 * Overrides the hashCode() method to compare the hashCode of Move objects
	 * @return the hashCode of the Move object.
	 */
	@Override // if overriding equals, must override hashCode
	public int hashCode() {
		int result = 17;
		result = 31 + result + row;
		result = 31 + result + col;
		return result;
	}

	/**
	 *
	 * @return the String representation of the Move object
	 */
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
}
