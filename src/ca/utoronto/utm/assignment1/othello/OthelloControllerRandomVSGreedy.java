package ca.utoronto.utm.assignment1.othello;

/**
 * The goal here is to print out the probability that Random wins and Greedy
 * wins as a result of playing 10000 games against each other with P1=Random and
 * P2=Greedy. What is your conclusion, which is the better strategy?
 * @author arnold
 *
 */
public class OthelloControllerRandomVSGreedy {

	/**
	 * Run main to execute the simulation and print out the two line results.
	 * Output looks like: 
	 * Probability P1 wins=.75 
	 * Probability P2 wins=.20
	 * @param args
	 */
	
	protected Othello othello;
	PlayerRandom player1;
	PlayerGreedy player2;
	
	/**
	 * Constructs a new OthelloController with a new Othello game
	 * with PlayerRandom and PlayerGreedy as the players
	 */
	public OthelloControllerRandomVSGreedy() {
		this.othello = new Othello();
		this.player1 = new PlayerRandom(this.othello, OthelloBoard.P1);
		this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);
	}
	
	/**
	 * Play the Othello game with the OthelloController. When the game is over,
	 * the method returns the winner of the game.
	 * @return 	the winner of the Othello game, either P1, P2, or EMPTY if there is a tie.
	 */
	public char play() {
		
		while (!othello.isGameOver()) {

			Move move = null;
			char whosTurn = othello.getWhosTurn();
			if (whosTurn == OthelloBoard.P1)
				move = player1.getMove();
			if (whosTurn == OthelloBoard.P2)
				move = player2.getMove();

			othello.move(move.getRow(), move.getCol());
		}
		return othello.getWinner();
	}
	
	/**
	 * Run main to play 10000 PlayerRandom against PlayerGreedy games of Othello.
	 * Prints the probability that both players win a game.
	 * @param args
	 */
	public static void main(String[] args) {
		
		int p1wins = 0, p2wins = 0, numGames = 10000;
		
		for (int i=0; i < numGames; i++){
			OthelloControllerRandomVSGreedy oc = new OthelloControllerRandomVSGreedy();
			char winner = oc.play();
			if (winner == OthelloBoard.P1) {
				p1wins++; 
			} else if (winner == OthelloBoard.P2) {
				p2wins++;
			}
		}
		
		System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
		System.out.println("Probability P2 wins=" + (float) p2wins / numGames);
	}
}
