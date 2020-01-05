public class King extends ChessPiece
{
    public King (String c, char clr)
    {
	super (c, clr, 'k');
	//Calling the superclass "ChessPiece" constructor to set coordinate, the colour and class.
	//Class is set to 'k' for the King
    }


    public boolean move (String c, ChessPiece[] [] cp)
    {
	//Changing position of the king piece
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

	//Error checking for no movement
	if (xf == xi && yf == yi)
	{
	    System.out.println ("You have to move your piece.");
	    return false;
	}

	//One Space Movement Checker
	if ((xf == xi || xf == xi - 1 || xf == xi + 1) && (yf == yi || yf == yi - 1 || yf == yi + 1))
	{
	    if (!isCheck (c, cp)) //Final Space is Safe for King (King is not in check)
	    {
		//Making sure final position is not on a piece of the player's own colour
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
		System.out.println ("Your King would be in check in that position.");
		return false;
	    }
	}
	else
	{
	    System.out.println ("King may only move one space in any direction.");
	    return false;
	}
    }


    public boolean isCheck (String c, ChessPiece[] [] cp)  //checking if King would be in check in the given position
    {
	int x = c.charAt (0) - 'A' + 1;
	int y = c.charAt (1) - '0';

	//Checking for enemy pawn
	if (clr == 'b' && y >= 2) //When King is black
	{
	    if (cp [x + 1] [y - 1] != null && cp [x + 1] [y - 1].getClr () == 'w' && cp [x + 1] [y - 1].getCls () == 'p')
	    {
		return true;
	    }
	    else if (cp [x - 1] [y - 1] != null && cp [x - 1] [y - 1].getClr () == 'w' && cp [x - 1] [y - 1].getCls () == 'p')
	    {
		return true;
	    }
	}
	else if (clr == 'w' && y <= 7) //When King is white
	{
	    if (cp [x + 1] [y + 1] != null && cp [x + 1] [y + 1].getClr () == 'b' && cp [x + 1] [y + 1].getCls () == 'p')
	    {
		return true;
	    }
	    else if (cp [x - 1] [y + 1] != null && cp [x - 1] [y + 1].getClr () == 'b' && cp [x - 1] [y + 1].getCls () == 'p')
	    {
		return true;
	    }
	}

	//Checking for enemy knight
	for (int i = 1 ; i <= 8 ; i++) //Nested for-loop to find enemy's knight
	{
	    for (int j = 1 ; j <= 8 ; j++)
	    {
		if (cp [i] [j] != null && cp [i] [j].getClr () != clr && cp [i] [j].getCls () == 'n')
		{
		    if (((x == i + 2 || x == i - 2) && (y == j + 1 || y == j - 1)) || ((x == i + 1 || x == i - 1) && (y == j + 2 || y == j - 2)))
			return true; //if King is within the identified knight's attack range, return true.
		}
	    }
	}

	//Checking for diagonal direction. When enemy queen or bishop is there, it is check.
	for (int i = 1 ; x + i <= 8 && y + i <= 8 ; i++)  //forward right
	{
	    if (cp [x + i] [y + i] != null)
	    {
		//checking if there is enemy queen or bishop in a diagonal way.
		if (cp [x + i] [y + i].getClr () != clr && (cp [x + i] [y + i].getCls () == 'b' || cp [x + i] [y + i].getCls () == 'q'))
		    return true;
		//breaking out if there is my own piece or non-bishop or queen piece is on the way.
		else if ((cp [x + i] [y + i].getClr () == clr && cp [x + i] [y + i].getCls () != 'k') || (cp [x + i] [y + i].getCls () != 'b' && cp [x + i] [y + i].getCls () != 'q'))
		    break;
	    }
	}

	for (int i = 1 ; x - i >= 1 && y + i <= 8 ; i++) //forward left
	{
	    if (cp [x - i] [y + i] != null)
	    {
		if (cp [x - i] [y + i].getClr () != clr && (cp [x - i] [y + i].getCls () == 'b' || cp [x - i] [y + i].getCls () == 'q'))
		    return true;
		else if ((cp [x - i] [y + i].getClr () == clr && cp [x - i] [y + i].getCls () != 'k') || (cp [x - i] [y + i].getCls () != 'b' && cp [x - i] [y + i].getCls () != 'q'))
		    break;
	    }
	}

	for (int i = 1 ; x + i <= 8 && y - i >= 1 ; i++) //backward right
	{
	    if (cp [x + i] [y - i] != null)
	    {
		if (cp [x + i] [y - i].getClr () != clr && (cp [x + i] [y - i].getCls () == 'b' || cp [x + i] [y - i].getCls () == 'q'))
		    return true;
		else if ((cp [x + i] [y - i].getClr () == clr && cp [x + i] [y - i].getCls () != 'k') || (cp [x + i] [y - i].getCls () != 'b' && cp [x + i] [y - i].getCls () != 'q'))
		    break;
	    }
	}

	for (int i = 1 ; x - i >= 1 && y - i >= 1 ; i++) //backward left
	{
	    if (cp [x - i] [y - i] != null)
	    {
		if (cp [x - i] [y - i].getClr () != clr && (cp [x - i] [y - i].getCls () == 'b' || cp [x - i] [y - i].getCls () == 'q'))
		    return true;
		else if ((cp [x - i] [y - i].getClr () == clr && cp [x - i] [y - i].getCls () != 'k') || (cp [x - i] [y - i].getCls () != 'b' && cp [x - i] [y - i].getCls () != 'q'))
		    break;
	    }
	}

	//Checking for horizontal/vertical direction. When enemy queen or rook is there, it is check.
	for (int i = 1 ; x + i <= 8 ; i++) //rightward
	{
	    if (cp [x + i] [y] != null)
	    {
		//checking if there is enemy queen or rook in a horizontal way.
		if (cp [x + i] [y].getClr () != clr && (cp [x + i] [y].getCls () == 'r' || cp [x + i] [y].getCls () == 'q'))
		    return true;
		//breaking out if there is my own piece or non-rook or queen piece is on the way.
		else if ((cp [x + i] [y].getClr () == clr && cp [x + i] [y].getCls () != 'k') || (cp [x + i] [y].getCls () != 'r' && cp [x + i] [y].getCls () != 'q'))
		    break;
	    }
	}

	for (int i = 1 ; x - i >= 1 ; i++) //leftward
	{
	    if (cp [x - i] [y] != null)
	    {
		if (cp [x - i] [y].getClr () != clr && (cp [x - i] [y].getCls () == 'r' || cp [x - i] [y].getCls () == 'q'))
		    return true;
		else if ((cp [x - i] [y].getClr () == clr && cp [x - i] [y].getCls () != 'k') || (cp [x - i] [y].getCls () != 'r' && cp [x - i] [y].getCls () != 'q'))
		    break;
	    }
	}

	for (int i = 1 ; y + i <= 8 ; i++) //foward
	{
	    if (cp [x] [y + i] != null)
	    {
		if (cp [x] [y + i].getClr () != clr && (cp [x] [y + i].getCls () == 'r' || cp [x] [y + i].getCls () == 'q'))
		    return true;
		else if ((cp [x] [y + i].getClr () == clr && cp [x] [y + i].getCls () != 'k') || (cp [x] [y + i].getCls () != 'r' && cp [x] [y + i].getCls () != 'q'))
		    break;
	    }
	}

	for (int i = 1 ; y - i >= 1 ; i++) //backward
	{
	    if (cp [x] [y - i] != null)
	    {
		if (cp [x] [y - i].getClr () != clr && (cp [x] [y - i].getCls () == 'r' || cp [x] [y - i].getCls () == 'q'))
		    return true;
		else if ((cp [x] [y - i].getClr () == clr && cp [x] [y - i].getCls () != 'k') || (cp [x] [y - i].getCls () != 'r' && cp [x] [y - i].getCls () != 'q'))
		    break;
	    }
	}

	//If the King is safe from every single checking, return false.
	return false;
    }



    //A special move method to move King without condition in castling
    public boolean castleMove (String c, ChessPiece[] [] cp)
    {
	return super.move (c, cp);
    }
}
