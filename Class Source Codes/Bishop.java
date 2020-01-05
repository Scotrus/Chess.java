//A subclass representing Bishop
public class Bishop extends ChessPiece
{
    public Bishop (String c, char clr)
    {
	super (c, clr, 'b');
	//Calling the superclass "ChessPiece" constructor to set coordinate, the colour and class.
	//Class is set to 'b' for the bishop.
    }


    public boolean move (String c, ChessPiece[] [] cp)
    {
	//Changing position of the bishop piece
	int xf = c.charAt (0) - 'A' + 1;
	int yf = c.charAt (1) - '0';

	int xi = coordi.charAt (0) - 'A' + 1;
	int yi = coordi.charAt (1) - '0';

	//Error checking for out of bounds movement
	if (xf > 8 || yf > 8 || xf < 1 || yf < 1)
	{
	    System.out.println ("Out of bounds.");
	    return false;
	}

	if (xf == xi && yf == yi) //Error checking for no movement
	{
	    System.out.println ("You have to move your piece.");
	    return false;
	}


	if (Math.abs (xf - xi) == Math.abs (yf - yi)) //Is it moving diagonally?
	{
	    if (dChecker (c, cp))
	    {
		//Making sure final position is not on a piece of the player's colour
		if (cp [xf] [yf] == null || clr != cp [xf] [yf].getClr ())
		{
		    return super.move (c, cp);
		}
		else
		{
		    System.out.println ("Can't land on your own piece.");
		    return false;
		}
	    }
	    else
	    {
		System.out.println ("Piece on the way.");
		return false;
	    }
	}
	else
	{
	    System.out.println ("Bishop must move diagonally.");
	    return false;
	}
    } //Move method (bishop)
} //Bishop class




