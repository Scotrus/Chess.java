//A subclass representing pawn
public class Pawn extends ChessPiece
{
    public Pawn (String c, char clr)
    {
	super (c, clr, 'p');
	//calling the superclass(ChessPiece) constructor to set coordinate, colour, and class.
	//Class is set to 'p', representing pawn, as a default.
    }


    public boolean move (String c, ChessPiece[] [] cp)
    {
	//Changing coordinates in String format into usable integer index
	int xf = c.charAt (0) - 'A' + 1;
	int yf = c.charAt (1) - '0';

	int xi = coordi.charAt (0) - 'A' + 1;
	int yi = coordi.charAt (1) - '0';

	char movetype; //a variable to determine if the pawn is doing forward 'f',diagonal 'd', or two squares forward 't' type move.

	if (xf > 8 || yf > 8 || xf < 1 || yf < 1) //error checking step to see if the chess piece goes out of the board
	{
	    System.out.println ("This is an invalid move. Chess piece can't go off the board."); //Error trap explains the rule.
	    return false; //This false/true return lets the main method know whether to call move() again or not.
	}

	if (xf == xi && yf == yi) //if the user doesn't move the piece, error trap it.
	{
	    System.out.println ("You have to move your piece.");
	    return false;
	}

	//Move algorithm if the piece is white.
	if (clr == 'w')
	{
	    if ((xf == xi + 1 || xf == xi - 1) && yf == yi + 1) //diagonal move
		movetype = 'd';
	    else if (xf == xi && yf == yi + 1) //one square forward move
		movetype = 'f';
	    else if (xf == xi && yf == yi + 2 && moved == false) //two squares forward move
		movetype = 't';
	    else //if it is none of the moves above, it is an illegal move. End of method.
	    {
		System.out.println ("This is an invalid move.");
		System.out.println ("Pawn can only move one square forward, forward diagonal, or two squares forward on its initial move.");
		return false;
	    }

	    if (movetype == 'd') //checks if there is an enemy piece in a diagonal way.
	    {
		if (cp [xf] [yf] != null && cp [xf] [yf].getClr () == 'b') //legal move
		{
		    return super.move (c, cp);
		}
		else //illegal move
		{
		    System.out.println ("This is an invalid move.");
		    System.out.println ("For a pawn to move diagonally, it should get an enemy chess piece when it moves.");
		    return false;
		}
	    }
	    else if (movetype == 'f') //checks if the destination is vacant.
	    {
		if (cp [xf] [yf] == null)
		{
		    return super.move (c, cp);
		}
		else
		{
		    System.out.println ("This is an invalid move.");
		    System.out.println ("For a pawn to move forward, there shouldn't be any chess piece at the destination.");
		    return false;
		}
	    }
	    else //checks if the way is vacant
	    {
		if (cp [xf] [yf] == null && cp [xf] [yf - 1] == null)
		{
		    return super.move (c, cp);
		}
		else
		{
		    System.out.println ("This is an invalid move.");
		    System.out.println ("For a pawn to move two squares forward, there shouldn't be any chess piece on its way.");
		    return false;
		}
	    }
	}
	//When the piece is black.
	else
	{
	    if ((xf == xi + 1 || xf == xi - 1) && yf == yi - 1) //diagonal move
		movetype = 'd';
	    else if (xf == xi && yf == yi - 1) //one square forward move
		movetype = 'f';
	    else if (xf == xi && yf == yi - 2 && moved == false) //two squares forward move
		movetype = 't';
	    else //if it is none of the moves above, it is an illegal move. End of method.
	    {
		System.out.println ("This is an invalid move.");
		System.out.println ("Pawn can only move one square forward, forward diagonal, or two squares forward on its initial move.");
		return false;
	    }

	    if (movetype == 'd') //checks if there is an enemy piece in a diagonal way.
	    {
		if (cp [xf] [yf] != null && cp [xf] [yf].getClr () == 'w') //legal move
		{
		    return super.move (c, cp);
		}
		else //illegal move
		{
		    System.out.println ("This is an invalid move.");
		    System.out.println ("For a pawn to move diagonally, it should get an enemy chess piece when it moves.");
		    return false;
		}
	    }
	    else if (movetype == 'f') //checks if the destination is vacant.
	    {
		if (cp [xf] [yf] == null)
		{
		    return super.move (c, cp);
		}
		else
		{
		    System.out.println ("This is an invalid move.");
		    System.out.println ("For a pawn to move forward, there shouldn't be any chess piece at the destination.");
		    return false;
		}
	    }
	    else //checks if two squares in front of the pawn is vacant
	    {
		if (cp [xf] [yf] == null && cp [xf] [yf + 1] == null)
		{
		    return super.move (c, cp);
		}
		else
		{
		    System.out.println ("This is an invalid move.");
		    System.out.println ("For a pawn to move two squares forward, there shouldn't be any chess piece on its way.");
		    return false;
		}
	    }
	}
    }


    //This method changes a pawn object into other class that user wants when pawn reaches the other end of the board.
    //Implementing the promotion rule to the program
    public void promotion (char ncls, ChessPiece[] [] cp)
    {
	int x = coordi.charAt (0) - 'A' + 1;
	int y = coordi.charAt (1) - '0';

	if (ncls == 'n') //When user wants to promote to knight
	{
	    cp [x] [y] = new Knight (coordi, clr);
	}
	else if (ncls == 'b') //When user wants to promote to bishop
	{
	    cp [x] [y] = new Bishop (coordi, clr);
	}
	else if (ncls == 'r') //When user wants to promote to rook
	{
	    cp [x] [y] = new Rook (coordi, clr);
	}
	else if (ncls == 'q') //When user wants to promote to queen
	{
	    cp [x] [y] = new Queen (coordi, clr);
	}
    }
}


