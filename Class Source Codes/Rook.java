//A subclass representing Rook
public class Rook extends ChessPiece
{
    public Rook (String c, char clr)
    {
	super (c, clr, 'r');
	//Calling the superclass "ChessPiece" constructor to set coordinate, the colour and class.
	//Class is set to 'r' for the rook.
    }


    public boolean move (String c, ChessPiece[] [] cp)
    {
	//Changing position of the rook piece
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

	if (xi == xf || yf == yf) //Is it moving horizontally/vertically?
	{
	    if (cChecker (c, cp))
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
	    System.out.println ("Rook must move horizontally/vertically.");
	    return false;
	}
    } //Move method (rook)


    //Castling method
    public boolean castle (King k, ChessPiece[] [] cp)
    {
	//King cannot be in check before and after castling
	if (k.isCheck (k.getCoordi (), cp))
	{
	    System.out.println ("Your King is in check.");
	    return false;
	}
	else if (k.getMoved () == false && moved == false) //Seeing if both King and Rook has moved before
	{
	    //Seeing if user is trying to short castle (white side)
	    if (coordi.equals ("H1") && (cp [6] [1] == null && cp [7] [1] == null) && !(k.isCheck ("G1", cp)))
	    {
		super.move ("F1", cp); //Moving Rook
		return k.castleMove ("G1", cp); //Moving King
	    }

	    //Seeing if user is trying to long castle (white side)
	    else if (coordi.equals ("A1") && (cp [2] [1] == null && cp [3] [1] == null && cp [4] [1] == null) && !(k.isCheck ("C1", cp)))
	    {
		super.move ("D1", cp); //Rook
		return k.castleMove ("C1", cp); //King
	    }

	    //Seeing if user is trying to short castle (black side)
	    else if (coordi.equals ("H8") && (cp [6] [8] == null && cp [7] [8] == null) && !(k.isCheck ("G8", cp)))
	    {
		super.move ("F8", cp); //Rook
		return k.castleMove ("G8", cp); //King
	    }

	    //Seeing if user is trying to long castle (black side)
	    else if (coordi.equals ("A8") && (cp [2] [8] == null && cp [3] [8] == null && cp [4] [8] == null) && !(k.isCheck ("C8", cp)))
	    {
		super.move ("D8", cp); //Rook
		return k.castleMove ("C8", cp); //King
	    }
	    else
	    {
		//Reasons for not castling
		System.out.println ("Can't castle, look for pieces in the way. See if final position of King is in check");
		return false;
	    }
	}
	else
	{
	    System.out.println ("King or rook has already moved, can't castle.");
	    return false;
	}
    } //Castle method
} //Rook Class

