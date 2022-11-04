import java.util.Arrays;

public class UltimateTicTacToe 
{
	// Class Constants
										// Current status for each ticTacToe game
	private final int GAME_NOT_OVER = 0;
	private final int DRAW          = 1;
	private final int X_WINNER      = 2;
	private final int O_WINNER      = 3;
	
	// Class Variables -- state of the object
	
	private TicTacToe board[][];
	private int       tttGameStatus[][];
	
	private boolean gameOver;		// Ultimate game over
	private boolean winnerX;		// Did X win?
	
	// Constructors -- Set the initial state
	
	public UltimateTicTacToe()
	{	
		// Set state
		
		board = intializeBoard();
		tttGameStatus = new int[ 3 ][ 3 ];
		
		gameOver = false;
		winnerX  = false;
	}
			
	// Accessor Methods -- getters -- return the state		

	// None
	
	// Mutators/Transformers -- setters -- change the state
	
	public void updateBoard( char playerTurn, int tttRow, int tttCol, int row, int col )
	{
		// Local Variables
		
		TicTacToe ticTacToe = null;
		
//		System.out.println( "Ultimate updateBoard: Player: " + playerTurn +
//				            " tttRow: " + tttRow + " tttCol: " + tttCol +
//				            " row: " + row + " col: " + col );
		
    	// Update appropriate board with appropriate player
		
		ticTacToe = board[ tttRow ][ tttCol ];
		
		ticTacToe.updateBoard( playerTurn, row, col );
	}

	public String displayBoard()
	{
		// Local Variables
		
		StringBuffer sb = new StringBuffer();
		
		int boardNumber = 0;

		// Append title of stars
		
		sb.append( TicTacToe.createTicTacToeStars() );
		
		// Append each ultimate row
		
		for ( int tttRow = 0; tttRow < board.length; tttRow++ )  // 3x3 TTT game
		{ 
			sb.append( createUltimateRow( tttRow, boardNumber ) );
			
			boardNumber += 3;
		}
		
		return sb.toString();
	}
	
	// Create formatted board to be displayed
	
	private String createUltimateRow( int tttRow, int boardNumber )
	{
		// Local Variables
	
		StringBuffer sb = new StringBuffer();

		int sq = 0;
		
		// Display 3 tictactoe rows
		
		for ( int row = 0; row < 3; row++ )
		{
			// 3 same strings containing squares 0-2 or 3-5 or 6-8
			
			sb.append( TicTacToe.createTicTacToeSquares( sq ) + 
			           TicTacToe.createTicTacToeSquares( sq ) +
			           TicTacToe.createTicTacToeSquares( sq ) +
			           "*\n" );
			
			// 3 strings containing 1st row for 3 tictactoe games
			
			sb.append( createActualTicTacToeRow( tttRow, row ) );
			
			sq += 3;
		}
		
		sb.append( createBoardTitles( boardNumber ) );
		
		return sb.toString();
	}
	
	private String createActualTicTacToeRow( int tttRow, int row )
	{
		// Local Variables
		
		TicTacToe normalTicTacToe = null;
		
		StringBuffer sb = new StringBuffer( "*|" );
		
		// Has to be loop to get 3 different TicTacToe games
		
		for ( int tttCol = 0; tttCol < board.length; tttCol++ )  // 3x3 TTT game
		{ 
			normalTicTacToe = board[ tttRow ][ tttCol ];
			
//			System.out.println( "tttRow: " + tttRow + " tttCol: " + tttCol );
//			System.out.println( normalTicTacToe.displayBoard() );
			
			sb.append( normalTicTacToe.createTicTacToeActual( row ) );
		}			
		
		// Delete last Pipe Char
		
		sb.deleteCharAt( sb.length()-1 );
		
		// Add newline char
		
		sb.append( "\n" );
		
		return sb.toString();
	}

	//Checks to see if the box is empty or not
	
	public boolean isAvailable( int tttRow, int tttCol, int row, int col )
	{
		// Local Variables
		
		TicTacToe ttt = board[ tttRow ][ tttCol ];
		
		boolean available = false;
		
		if ( ttt.isAvailable( row, col ) )
			available = true;
		
		return available;
	}
	
	// Class helper methods
	
	private String createBoardTitles( int boardNumber )
	{
		//   |  Board #?  |;
		
		// Local Variables
		
		String formattedStr = "";
		
		formattedStr += TicTacToe.createTicTacToeStars();
		
		// Create each row of the appropriate 

		formattedStr += "*|";
		
		for ( int count = 1; count <= 3; count++ )
		{
			formattedStr += "  Board #" +  boardNumber + " |*|";
			
			boardNumber++;
		}	
		
		// Remove last Pipe char
		
		formattedStr = formattedStr.substring( 0, formattedStr.length()-1 );
		
		// Add new line
		
		formattedStr += "\n";
		
		formattedStr += TicTacToe.createTicTacToeStars();
		
		// Add newline char
		
		//formattedStr += "\n";
		
		return formattedStr;
	}
	
	private TicTacToe[][] intializeBoard()
	{
		// Local Variables
		
		TicTacToe board[][] = null;
		
		// Instantiate board
		
		board = new TicTacToe[ 3 ][ 3 ];
		
		// Initialize board
		
		for ( int row = 0; row < board.length; row++ )
		{
			for ( int col = 0; col < board[ row ].length; col++ )
			{
				board[ row ][ col ] = new TicTacToe();
			}			
		}
		
		return board;
	}
	
	//Checks to see if move created a winner for that row
	
    private boolean checkRow( char playerTurn, int tttRow )
    {
    	// Local Variables
    	
    	TicTacToe ttt = null;
    	boolean winner = false;
    	 
    	for ( int tttCol = 0; tttCol < 3; tttCol++ )
    	{
    		if ( tttGameStatus[ tttRow ][ tttCol ] == GAME_NOT_OVER ||
    			 tttGameStatus[ tttRow ][ tttCol ] == DRAW             )
    			return false;
    		
    		else if ( playerTurn == 'X' && tttGameStatus[ tttRow ][ tttCol ] !=  X_WINNER )
    			return false;
    		
    		else if ( playerTurn == 'O' && tttGameStatus[ tttRow ][ tttCol ] !=  O_WINNER )
    			return false;  		
    	}
    	
    	return true;
    }
	    
	//Checks to see if move crated a winner for that col
    
    private boolean checkCol( char playerTurn, int uCol )
    {
    	// Local Variables
    	
    	TicTacToe ttt = null;    	
    	boolean winner = false;
    	 
    	for ( int uRow = 0; uRow < 3; uRow++ )
    	{
    		if ( tttGameStatus[ uRow ][ uCol ] == GAME_NOT_OVER ||
       			 tttGameStatus[ uRow ][ uCol ] == DRAW             )
       			return false;
       		
       		else if ( playerTurn == 'X' && tttGameStatus[ uRow ][ uCol ] !=  X_WINNER )
       			return false;
       		
       		else if ( playerTurn == 'O' && tttGameStatus[ uRow ][ uCol ] !=  O_WINNER )
       			return false;  	
    	}
    	
    	return true;
    }	    
    
    public String displayGameStatus()
    {
    	StringBuffer sb = new StringBuffer( "\n...tttGameStatus...\n" );
    	
    	for ( int row = 0; row < tttGameStatus.length; row++ )
    	{
        	for ( int col = 0; col < tttGameStatus[ row ].length; col++ )
        	{
        		sb.append( tttGameStatus[ row ][ col ] + " " );
        	}   		
        	sb.append( "\n" );
    	}
    
    	return sb.toString();
    }
	    
	public String displayInitialListValidMoves( char playerTurn )
	{
		// Local Variables
		
		StringBuffer sb = new StringBuffer( "\n---List of Valid Moves (Squares) for player: " +
		                                    playerTurn + " ---\n\n" );
		
		sb.append( "All squares for all boards are valid!" );
		
		return sb.toString();
	}
	
	public String displayListValidMoves( char playerTurn, int tttRow, int tttCol, int boardNumber )
	{
		// Local Variables
		
		TicTacToe ttt = board[ tttRow ][ tttCol ];
		
		String listOfValidMoves = null;
		
		// Get list of valid squares available to play
		
		listOfValidMoves = ttt.displayListValidMoves( playerTurn, boardNumber );
		
		return listOfValidMoves;
	}
	
	public boolean checkUltimateWinner( char playerTurn, int tttRow, int tttCol )
	{
		// Local Variables
		
		TicTacToe normalTTT = null;
		boolean winner = true;
		
		//System.out.println( "\nUltimate checkUltimateWinner - playerTurn: " + playerTurn + 
		//		              " tttRow: " + tttRow + " tttCol: " + tttCol );
    	
		//System.out.println( displayGameStatus() );
		
		// To determine a winner, must see if there is a TicTacToe winner, and if so,
		//    then check to see if there is an UltimateTicTacToe winner
		//    If this ttt game was not a winner, then ultimate cannot have a winner
		
		normalTTT = board[ tttRow ][ tttCol ];
		if ( normalTTT.checkWinner( playerTurn ) )  // Did this turn win this ttt game?
		{
			// Update game status
					
			if ( playerTurn == 'X' )			
				tttGameStatus[ tttRow ][ tttCol ] = X_WINNER;
			else
				tttGameStatus[ tttRow ][ tttCol ] = O_WINNER;
			
			// Check rows
			
	    	for ( int uRow = 0; uRow < 3; uRow++ )
	    	{
	    		if ( this.checkRow( playerTurn, uRow ) )
	    			return true;
	    	}
	    	
	    	//System.out.println( "checkUltimateWinner -- but rows is NOT a winner!" );
			
			// Check columns
			
	    	for ( int uCol = 0; uCol < 3; uCol++ )
	    	{
	    		if ( this.checkCol( playerTurn, uCol ) )
	    			return true;
	    	}

	    	//System.out.println( "checkUltimateWinner -- but cols is NOT a winner!" );

	    	// Check Diagonals
	    	
	    	if ( this.checkDiagonals( playerTurn ) )
	    		return true;
	    	
	    	//System.out.println( "checkUltimateWinner -- but diags is NOT a winner!" );
		}
    	
		return false;
	}
	
	public boolean checkDiagonals( char playerTurn )
	{
		// Local Variables
		
		boolean result = checkDiagonalsHelper( playerTurn );
		
		if ( result )
		{
			this.gameOver = true;
			
			if ( playerTurn == 'X' )
				this.winnerX = true;
			else
				this.winnerX = false;
		}
		
		return result;
	}
	
	private boolean checkDiagonalsHelper( char playerTurn )
	{
		// Local Variables
		
		TicTacToe ttt = null;
		int lookingFor = O_WINNER;
		
		if ( playerTurn == 'X' )
			lookingFor = X_WINNER;			
		
	    if ( lookingFor == tttGameStatus[0][0] )
	    {
	    	if ( tttGameStatus[0][0] == tttGameStatus[1][1] && tttGameStatus[ 1 ][ 1 ] ==  tttGameStatus[2][2] )
	    		return true;
	    
			if ( tttGameStatus[2][0] == tttGameStatus[1][1] && tttGameStatus[ 1 ][ 1 ] == tttGameStatus[0][2] )
				return true;
	    }
		
		return false;
	}
	
	public boolean checkDraw( int tttRow, int tttCol )
	{
		// Local Variables
		
		boolean ultimateResult = false;
		
		// Check to see if individual tic tac toe was a draw
		
		if ( checkDrawHelper( tttRow, tttCol ) )
		{	
			// Now check to see if Ultimate tic-tac-toe was a draw
			
			for ( int row = 0; row < tttGameStatus.length; row++ )
			{
				for ( int col = 0; col < tttGameStatus[ row ].length; col++ )
				{
					if ( tttGameStatus[ row ][ col ] == GAME_NOT_OVER )
						return false;
				}
			}
			
			ultimateResult = true;
		}
		
		return ultimateResult;
	}
	
	private boolean checkDrawHelper( int tttRow, int tttCol )
	{
		// Local Variables
		
		TicTacToe normalTTT = null;

		// Get tic tac toe game
		
		normalTTT = board[ tttRow ][ tttCol ];
		
		// Check to see if it is a draw
		
		if ( normalTTT.checkDraw() )
		{			
			tttGameStatus[ tttRow ][ tttCol ] = DRAW;
			
			return true;
		}

		return false;
	}
	
	public boolean checkIfBoardFull( int tttRow, int tttCol )
	{
		// Local Variables
		
		TicTacToe ttt = null;
		boolean full = false;
		
		ttt = board[ tttRow ][ tttCol ];
		
		if ( ttt.checkIfBoardFull() )
			full = true;
		
		return full;
	}
}