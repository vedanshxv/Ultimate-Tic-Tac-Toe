//Vedansh Vaidhyanathan CS2336.003
/*Analysis: The problem for this project was to create a functioning Ultimate Tic-Tac-Toe game which is different from original Tic-Tac-Toe
 * because of a new rule that has been added: A player can only place a mark on the board determined by the position of the opponent's
 * last placed mark. The addition of this rule makes it so that the game is not just about winning a single game, but strategically 
 * managing nine squares at once.
 * 
 * Design (How to solve it): To solve the problem, I began working on the TicTacToe class which contained fundamental boolean methods that were 
 * used to test many things. I first worked on the method that would display the board which the user would play on. This was done by instantiating
 * a blank board with empty spaces. I used the scanner to take in the user input and made use of methods like isAvailable to make sure it was valid. 
 * Then I worked on boolean methods which would be used to make sure the game operated correctly. For example, the check row and column methods were 
 * created to see if the latest move created a draw. Some of the other methods were implemented to check whether the game would resume playing based 
 * on the last move. There is also a list of available moves shown which are based on the empty boxes available in the current tic tac toe board. 
 * Lastly, the The UltimateTicTacToe class contained methods which operated in the same way as methods in the TicTacToe class but were used for 
 * the whole ultimate board. For the AI vs Player and AI vs AI versions of the game, I used the run configurations dialog in eclipse so that when the user
 * would input -P -AI, the Player vs AI version would run, and where inputting -AI would run the AI vs AI version.
 * 

 */

import java.util.*;

public class TicTacToeGame
{
	// Class Constants
	
	public static final int PLAYER_VS_PLAYER = 0;
	public static final int PLAYER_VS_AI     = 1;
	public static final int AI_VS_AI         = 2;
	
	// Class Methods
	
	public void playTicTacToe( int playerTypes )
	{
		//playNormalTicTacToe( playerTypes );
		playUltimateTicTacToe( playerTypes );
	}

	private void playUltimateTicTacToe( int playerTypes )
	{
		// Local Constants
		
		final int CAN_CHOOSE_BOARD = -99;
		
		// Local Variables
		
		Scanner in = new Scanner(System.in);
		char playerTurn = 'X';
		boolean gameOver = false;
		int board = -99;
		int square = 0;
		int tttRow = 0;
		int tttCol = 0;
		int row = 0;
		int col = 0;
		String prompt = "";
		boolean validMove = false;
		String message = null;

		if ( playerTypes == PLAYER_VS_PLAYER )
			message = "Player versus Player";
		else if ( playerTypes == PLAYER_VS_AI )
			message = "Player versus Computer";
		else // if ( playerTypes == AI_VS_AI )
			message = "Computer versus Computer";
	
		System.out.println( "\n ----- Welcome to Ultimate Tic-Tac-Toe (" + message + ") -----\n" );		
			
		// Instantiate the game of Ultimate TicTacToe
		
		UltimateTicTacToe uTicTacToe = new UltimateTicTacToe();

		// Keep playing until game is over, a winner or a draw

		do
		{
			// Display the board
			
			System.out.println( uTicTacToe.displayBoard() );
			
			validMove = false;
			
			do
			{
				// Prompt user for valid board and valid square #
				
				if ( board == CAN_CHOOSE_BOARD ) // Can only happen for player X
				{
					if ( playerTypes != AI_VS_AI )
					{
						prompt = "Player " + playerTurn + ": Enter board [ 0-8 ]: ";
						board = requestIntegerFromUser( prompt, 0, 8 );
					}
					else
					{
						board = generateBoardRandomNumber();
						System.out.println( "\nPlayer X (Computer) chooses board " + board + "\n" );
					}
					
					// Now convert board # to row and col #
					
					if ( board >= 0 && board <= 2 )
						tttRow = 0;
					else if ( board >= 3 && board <= 5 )
						tttRow = 1;
					else
						tttRow = 2;
					tttCol = board % 3;
				}
				else
				{
					board = square;
					
					// Now convert board # to row and col #
					
					if ( board >= 0 && board <= 2 )
						tttRow = 0;
					else if ( board >= 3 && board <= 5 )
						tttRow = 1;
					else
						tttRow = 2;
					tttCol = board % 3;
					
					if ( uTicTacToe.checkIfBoardFull( tttRow, tttCol ) )
					{
						if ( playerTypes == AI_VS_AI )
						{
							// Randomly choose board #
							
							board = generateBoardRandomNumber();
							
							System.out.println( "\nPlayer " + playerTurn + " (Computer) chooses board " + board + "\n" );							
						}				
						else if ( playerTypes == PLAYER_VS_AI && playerTurn == 'O' )
						{
							// Randomly choose board #
							
							board = generateBoardRandomNumber();
							
							System.out.println( "\nPlayer O (Computer) chooses board " + board + "\n" );
						}
						else
						{
							// Allow current player to choose board
							
							prompt = "Player " + playerTurn + ": Enter board [ 0-8 ]: ";
							board = requestIntegerFromUser( prompt, 0, 8 );
						}
						
						// Now convert board # to row and col #
						
						if ( board >= 0 && board <= 2 )
							tttRow = 0;
						else if ( board >= 3 && board <= 5 )
							tttRow = 1;
						else
							tttRow = 2;
						tttCol = board % 3;
					}
				}

				if ( playerTypes == PLAYER_VS_PLAYER )
				{
					System.out.println( uTicTacToe.displayListValidMoves( playerTurn, tttRow, tttCol, board ) );
	
					prompt = "Player " + playerTurn + ": Enter square [ 0-8 ]: ";
					square = requestIntegerFromUser( prompt, 0, 8 );	
				}
				else if ( playerTypes == AI_VS_AI )
				{
					// Randomly choose square #
					
					square = generateSquareRandomNumber();
					
					System.out.println( "\nPlayer " + playerTurn + 
							" (Computer) chooses square " + square + " in board " + board + "\n" );
				}
				else if ( playerTypes == PLAYER_VS_AI && playerTurn == 'O' )
				{
					// Randomly choose square #
					
					square = generateSquareRandomNumber();
					
					System.out.println( "\nPlayer O (Computer) chooses square " + square + " in board " + board + "\n" );
				}

				else
				{
					System.out.println( uTicTacToe.displayListValidMoves( playerTurn, tttRow, tttCol, board ) );
					
					prompt = "Player " + playerTurn + ": Enter square [ 0-8 ]: ";
					square = requestIntegerFromUser( prompt, 0, 8 );	
				}
				
				// Now convert square # to row and col #
				
				if ( square >= 0 && square <= 2 )
					row = 0;
				else if ( square >= 3 && square <= 5 )
					row = 1;
				else
					row = 2;
				col = square % 3;
				

				if ( uTicTacToe.isAvailable( tttRow, tttCol, row, col ) )
				{
					uTicTacToe.updateBoard( playerTurn, tttRow, tttCol, 
							                            row,    col     );
					validMove = true;
				}
				else
				{
					System.out.println( "\nInvalid turn -- Square for that board: " + 
				               square + " is already used!" );
					
				}
	
			} while ( !validMove );

			// After every valid move, must check to see if individual tic-tac-toe has a winner, and if so,
			//   then does UltimateTicTacToe have a winner
			
			if ( uTicTacToe.checkUltimateWinner( playerTurn, tttRow, tttCol ) )
			{
				// Display the board
				
				System.out.println( "\n" + uTicTacToe.displayBoard() );
				
				//System.out.println( uTicTacToe.displayGameStatus() );
				
				String message1 = "";
				
				if ( playerTypes == AI_VS_AI )
					message1 = " (Computer) ";
				
				else if ( playerTypes == PLAYER_VS_AI && playerTurn == 'O' )
					message1 = " (Computer) ";			
				
				System.out.println( "Congratulations! Player " + playerTurn + message1 +
						" has won! Thanks for playing." );
				gameOver = true;
			}
			else if ( uTicTacToe.checkDraw( tttRow, tttCol ) )
			{
				// Display the board
				
				System.out.println( "\n" + uTicTacToe.displayBoard() );
				
				System.out.println( "It's a draw! Thanks for playing." );	
				gameOver = true;
			}
			
			if ( playerTurn == 'X' )
				playerTurn = 'O';
			else
				playerTurn = 'X';
			
		} while ( !gameOver );
	}
	
	private int generateBoardRandomNumber()
	{
		// Local Variables
		
		int randomNumber = 0;

        int min = 0;
        int max = 8;
        
        int range = max - min + 1;
  
        // Generate random numbers within specified range
        
        randomNumber = (int) (Math.random() * range) + min;
            
		// Return random number
        
        return randomNumber;
	}
	
	private int generateSquareRandomNumber()
	{
		// Local Variables
		
		int randomNumber = 0;

        int min = 0;
        int max = 8;
        
        int range = max - min + 1;
  
        // Generate random numbers within specified range
        
        randomNumber = (int) (Math.random() * range) + min;
            
		// Return random number
        
        return randomNumber;		
	}
	
	private int requestIntegerFromUser( String prompt, int min, int max )
	{
		// Local Variables
		
		Scanner scanner = new Scanner( System.in );
		
		int number = 0;
		boolean validInput = false;		// Assume bad input
		
		// Allow user to enter a valid integer within the range
		
		do
		{
			try 
			{
				System.out.print( "\n" + prompt );
				number = scanner.nextInt();
				scanner.nextLine();           // Consume Enter Key
				
				if ( number >= min && number <= max )
					validInput = true;
				
				else
					System.out.println( "\nInteger must be in range [" + min + "-" + max + "]!" );
			}
			catch ( InputMismatchException e )
			{
				System.out.println( "\nInvalid integer entered!" );
				scanner.nextLine();           // Consume Enter Key
			}
			
		} while ( !validInput );
		
		return number;
	}
	
	private void playNormalTicTacToe( int playerTypes )
	{
		// Local Constants
		
		final int NO_BOARD = -99;
		
		// Local Variables
		
		Scanner in = new Scanner(System.in);
		char playerTurn = 'X';
		boolean gameOver = false;
		int square = 0;
		int row = 0;
		int col = 0;
		String prompt = "";
		boolean validMove = false;

		System.out.println( "Welcome to 3x3 Tic Tac Toe." );

		// Instantiate the game of TicTacToe
		
		TicTacToe ticTacToe = new TicTacToe();

		// Keep playing until game is over, a winner or a draw

		do
		{
			// Display the board
			
			System.out.println( ticTacToe.displayBoard() );
			
			validMove = false;
			
			do
			{
				// Display list of valid moves for current player
				
				System.out.println( ticTacToe.displayListValidMoves( playerTurn, NO_BOARD ) );
				
				// Prompt user for valid square #
	
				prompt = "Player " + playerTurn + ": Enter square [ 0-8 ]: ";
				square = requestIntegerFromUser( prompt, 0, 8 );	
				
				// Now convert square # to row and col #
				
				if ( square >= 0 && square <= 2 )
					row = 0;
				else if ( square >= 3 && square <= 5 )
					row = 1;
				else
					row = 2;
				col = square % 3;
				
				// Check to see if this row and col is already taken
			
				if ( ticTacToe.isAvailable( row, col ) )
				{
					ticTacToe.updateBoard( playerTurn, row, col );
					validMove = true;
				}
				else
					System.out.println( "\nInvalid turn -- Square: " + 
				               square + " is already used!" );
	
			} while ( !validMove );

		
			// After every valid move, must check to see if game is over
			
			if ( ticTacToe.checkWinner( playerTurn ) )
			{
				// Display the board
				
				System.out.println( "\n" + ticTacToe.displayBoard() );
				
				System.out.println( "Congratulations! Player " + playerTurn +
						" has won! Thanks for playing." );
				gameOver = true;
				

			}
			else if ( ticTacToe.checkDraw() )
			{
				// Display the board
				
				System.out.println( "\n" + ticTacToe.displayBoard() );
				
				System.out.println( "It's a draw! Thanks for playing." );	
				gameOver = true;
			}
			
			if ( playerTurn == 'X' )
				playerTurn = 'O';
			else
				playerTurn = 'X';
			
		} while ( !gameOver );
	}

	// Application
	//		java TicTacToe --> 			Player versus Player (default)
	//		java TicTacToe --> -P		Player versus Player
	//		java TicTacToe --> -AI		Computer (AI) versus Computer (AI)
	//		java TicTacToe --> -P -AI	Player versus Computer (AI)
	
	public static void main( String args[] )
	{
		// Local Variables
		
		TicTacToeGame game = new TicTacToeGame();
		
		// Play the game of TicTacToe
		
		if ( args.length == 0 )
		{
			game.playTicTacToe( PLAYER_VS_PLAYER );
		}
		else if ( args.length == 1 )
		{
			if ( args[ 0 ].equals( "-P" ) )
				game.playTicTacToe( PLAYER_VS_PLAYER );	
			
			else if ( args[ 0 ].equals( "-AI" ) )
				game.playTicTacToe( AI_VS_AI );	
			
			else
				System.out.println( "Invalid command line arguments!" );
		}
		else if ( args.length == 2 )
		{
			if ( ( args[ 0 ].equals( "-P" ) && args[ 1 ].equals( "-AI" ) ) ||
				 ( args[ 0 ].equals( "AI" ) && args[ 1 ].equals( "-P"  ) ) )	
				game.playTicTacToe( PLAYER_VS_AI );	
			
			else
				System.out.println( "Invalid command line arguments!" );
		}
		else
			System.out.println( "Invalid command line arguments!" );
	}
}