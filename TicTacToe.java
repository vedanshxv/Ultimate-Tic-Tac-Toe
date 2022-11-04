import java.util.*;

public class TicTacToe 
{
	// Class Constants
	
	public static char PLAYER_X = 'X';
	public static char PLAYER_O = 'O';
	public static char EMPTY    = ' ';

	// Class Variables -- state of the object
	
	private char board[][];
	private boolean gameOver;
	private boolean winnerX;
	private boolean draw;
	
	// Constructors -- Set the initial state
	
	public TicTacToe()
	{
		board = intializeBoard();
		
		gameOver = false;
		winnerX  = false;
		
		intializeBoard();
	}
			
	// Accessor Methods -- getters -- return the state		

	public boolean isGameOver()
	{
		return (gameOver || draw);
	}
	
	public boolean didPlayerXWin()
	{
		return winnerX;
	}
	
	public boolean isDraw()
	{
		return draw;
	}
	
	// Mutators/Transformers -- setters -- change the state
	
	public void updateBoard( char playerX, int row, int col )
	{
		// Update board
		
		//System.out.println( "Normal TicTacToe updateBoard: Player: " + playerX +
	    //        " row: " + row + " col: " + col );
		
		board[ row ][ col ] = playerX;		
	}
	
	@Override
	public String toString()
	{
		return "Normal TTT: " + Arrays.toString( board );
	}
	
	//Checks to see if the box is empty or not
	public boolean isAvailable( int row, int col )
	{
		boolean available = false;
				
		if ( board[ row ][ col ] == EMPTY )
			available = true;
		
		return available;
	}
		
	//Checks to see if move created a winner for that row
	
    protected boolean checkRow( char playerTurn, int row )
    {
    	boolean winner = false;
    	 
    	for ( int col = 0; col < 3; col++ )
    	{
    		if ( board[ row ][ col ] != playerTurn )
    			return false;
    	}
    	
    	return true;
    }
	    
	//Checks to see if move created a winner for that col
    
    protected boolean checkCol( char playerTurn, int col )
    {
    	boolean winner = false;
    	 
    	for ( int row = 0; row < 3; row++ )
    	{
    		if ( board[ row ][ col ] != playerTurn )
    			return false;
    	}
    	
    	return true;
    }	    
    
    public boolean checkWinner( char playerTurn )
    {
    	this.gameOver = false;
    	
    	//System.out.println( "TicTacToe begin checkWinner - gameOver: " + 
        //                     gameOver + "X win: " + winnerX );

    	if ( this.checkWinnerHelper( playerTurn ) )
    	{
    		this.gameOver = true;
    		
    		this.winnerX = false;
    		if ( playerTurn == 'X' )
        		this.winnerX = true;   			
    	}
    	
      	//System.out.println( "TicTacToe end checkWinner - gameOver: " + 
        //        gameOver + "X win: " + winnerX );
    	
    	return this.gameOver;
    }
	    
	private boolean checkWinnerHelper( char playerTurn )
	{
		// Check rows
		
    	for ( int row = 0; row < 3; row++ )
    	{
    		if ( checkRow( playerTurn, row ) )
    			return true;
    	}
		
		// Check cols
		
    	for ( int col = 0; col < 3; col++ )
    	{
    		if ( checkCol( playerTurn, col ) )
    			return true;
    	}	
 
    	// Check Diagonals
    	
	    if ( board[0][0] == playerTurn )
	    	if ( board[0][0] == board[1][1] && board[ 1 ][ 1 ] ==  board[2][2] )
	    		return true;
	    
		if ( board[2][0] == playerTurn )
			if ( board[2][0] == board[1][1] && board[ 1 ][ 1 ] == board[0][2] )
				return true;
		
		return false;
	}
	
	public boolean checkDraw()
	{
		this.draw = false;
		this.gameOver = false;
		
		if ( this.checkDrawHelper() )
		{
			this.gameOver = true;
			this.draw = true;
		}
		
		return this.draw;
	}
	
	private boolean checkDrawHelper()
	{
		// Search for an empty cell
		
		for ( int row = 0; row < board.length; row++ )
		{
			for ( int col = 0; col < board[ row ].length; col++ )
			{
				if ( board[ row ][ col ] == EMPTY )
					return false;
			}
		}
		
		// If there are no empty cells, then it is a draw
		
		this.draw = true;
		this.gameOver = true;
		
		return true;
	}
	
	public boolean checkIfBoardFull()
	{	
		// Search for an empty cell
		
		for ( int row = 0; row < board.length; row++ )
		{
			for ( int col = 0; col < board[ row ].length; col++ )
			{
				if ( board[ row ][ col ] == EMPTY )
					return false;
			}
		}
		
		return true;
	}
	
	public String displayListValidMoves( char playerTurn, int boardNumber )
	{
		// Local Variables
		
		String message = null;
		StringBuffer sb = null;
		
		// board is only valid if UltimateTicTacToe, so for TicTacToe board is passed in with negative #
		
		message = "\n---List of Valid Moves (Squares) ";
		if ( boardNumber >= 0 )
			message += "in board " + boardNumber;
		
	    message += " for player: " + playerTurn + " ---\n\n";
		
	    sb = new StringBuffer( message );
				        
		// Search for an empty cell
		
		for ( int row = 0; row < board.length; row++ )
		{
			for ( int col = 0; col < board[ row ].length; col++ )
			{
				if ( board[ row ][ col ] == EMPTY )
					sb.append( calculateSquare( row, col ) + " " );
			}
		}
		
		return sb.toString();
	}
	
	public int calculateSquare( int row, int col )
	{
		// Local Constants
		
		final int[][] SQUARES = { { 0, 1, 2 },
				                  { 3, 4, 5 },
								  { 6, 7, 8 } };
				                
		// Local Variables
								  
		int square = 0;
		
		// Retrieve square given row and col
		
		square = SQUARES[ row ][ col ];

		return square;
	}
	
	public String displayBoard()
	{
		// Local Variables
		
		StringBuffer sb = new StringBuffer();
		
		// Create formatted board to be displayed
		
		sb.append( "***************" );
		sb.append( "\n" );
		sb.append( "*|-0-|-1-|-2-|*" );
		sb.append( "\n" );
		sb.append( "*| " + board[ 0 ][ 0 ] + " | " +
						   board[ 0 ][ 1 ] + " | " + 
		                   board[ 0 ][ 2 ] + " |*" );
		sb.append( "\n" );
		sb.append( "*|-3-|-4-|-5-|*" );
		sb.append( "\n" );
		sb.append( "*| " + board[ 1 ][ 0 ] + " | " +
		           		   board[ 1 ][ 1 ] + " | " + 
		           		   board[ 1 ][ 2 ] + " |*" );
		sb.append( "\n" );
		sb.append( "*|-6-|-7-|-8-|*" );
		sb.append( "\n" );
		sb.append( "*| " + board[ 2 ][ 0 ] + " | " +
		           		   board[ 2 ][ 1 ] + " | " + 
                           board[ 2 ][ 2 ] + " |*" );
		sb.append( "\n" );
		sb.append( "*|---|---|---|*" );
		sb.append( "\n" );
		sb.append( "***************" );
		
		
		return sb.toString();
	}

	public String displayBoardForUltimate( int row )
	{
		// Local Variables
		
		String formattedRow = "";
		
		// Create formatted board to be displayed
		
		switch ( row )
		{
			case 0: formattedRow = createTicTacToeSquares( 0 ) + "*";;
	        	 	break;
			case 2: formattedRow = createTicTacToeSquares( 3 ) + "*";;
	        		break;
			case 4: formattedRow = createTicTacToeSquares( 6 ) + "*";;
    				break;			        
			case 1: 
			case 3:
			case 5: formattedRow = createTicTacToeActual( row ) + "*";
		}
		
		return formattedRow;
	}

	public static String createTicTacToeStars()
	{	
		return "**************" + 
			   "**************" +
			   "**************" + "*\n";
	}
	
	public static String createTicTacToeSquares( int squareNumber )
	{	
		return "*|-" + squareNumber + "-|-" +
	                 (squareNumber+1) + "-|-" + 
				     (squareNumber+2) + "-|";
	}
	
	public String createTicTacToeActual( int row )
	{
		// row will be 0-2
		
		//   | X |   | O |;
		
		// Local Variables
		
		String formattedStr = "";
		
		// Create each row of the appropriate string
		
		for ( int col = 0; col < board[ row ].length; col++ )
		{
			formattedStr += " " +  board[ row ][ col ] + " |";
		}	

		formattedStr += "*|";
		
		return formattedStr;
	}
	
	// Class helper methods
		
	private char[][] intializeBoard()
	{
		// Local Variables
		
		char board[][] = null;
		
		// Instantiate board
		
		board = new char[ 3 ][ 3 ];
		
		// Initialize board
		
		for ( int row = 0; row < board.length; row++ )
		{
			for ( int col = 0; col < board[ row ].length; col++ )
			{
				board[row][col] = EMPTY;
			}			
		}
		
		return board;
	}
}