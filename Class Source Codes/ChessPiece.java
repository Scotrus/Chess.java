public class ChessPiece
{
    //Variables
    //Protected for inheritance
    protected String coordi;
    protected char clr;
    protected char cls;
    protected boolean moved;

    //Constructor
    public ChessPiece (String c, char clr1, char cls1)
    {
	coordi = c; //Coordinates of the board
	clr = clr1; //Colour 'b' or 'w'
	cls = cls1; //Each pieces class ('p' 'n' 'r' 'k' 'q')
	moved = false; //No piece has been moved initially
    }


    //Getters & Setters

    //Coordinate
    public String getCoordi ()
    {
	return coordi;
    }


    public void setCoordi (String sc)
    {
	coordi = sc;
    }


    //Colour
    public char getClr ()
    {
	return clr;
    }


    public void setClr (char sclr)
    {
	clr = sclr;
    }


    //Class
    public char getCls ()
    {
	return cls;
    }


    public void setCls (char scls)
    {
	cls = scls;
    }


    //Moved
    public boolean getMoved ()
    {
	return moved;
    }


    public void setMoved (boolean mo)
    {
	moved = mo;
    }


    //Methods for the game
    //Movement method (deletes where piece is and draws it in new space)
    public boolean move (String c, ChessPiece[] [] cp)
    {
	int xf = c.charAt (0) - 'A' + 1;
	int yf = c.charAt (1) - '0';

	int xi = coordi.charAt (0) - 'A' + 1;
	int yi = coordi.charAt (1) - '0';

	cp [xf] [yf] = cp [xi] [yi];
	cp [xi] [yi] = null;

	coordi = c;
	moved = true;

	return true; //A move is made only when the move is legal, returning true to main.
    }


    public boolean dChecker (String c, ChessPiece[] [] cp)  //Diagonal move checker for bishop and queen.
    {
	int xf = c.charAt (0) - 'A' + 1;
	int yf = c.charAt (1) - '0';

	int xi = coordi.charAt (0) - 'A' + 1;
	int yi = coordi.charAt (1) - '0';

	if (xf > xi && yf > yi) //Forward, Right Movement
	{
	    for (int x = xi + 1 ; x < xf ; x++)
	    {
		if (cp [x] [yi + (x - xi)] != null)
		{
		    return false;
		}
	    }
	}

	else if (xf > xi && yf < yi) //Backwards, Right Movement
	{
	    for (int x = xi + 1 ; x < xf ; x++)
	    {
		if (cp [x] [yi - (x - xi)] != null)
		{
		    return false;
		}
	    }
	}

	else if (xf < xi && yf < yi) //Backwards, Left Movement
	{
	    for (int x = xi - 1 ; x > xf ; x--)
	    {
		if (cp [x] [yi + (x - xi)] != null)
		{
		    return false;
		}
	    }
	}

	else if (xf < xi && yf > yi) //Forward, Left Movement
	{
	    for (int x = xi - 1 ; x > xf ; x--)
	    {
		if (cp [x] [yi - (x - xi)] != null)
		{
		    return false;
		}
	    }
	}

	return true;
    }


    public boolean cChecker (String c, ChessPiece[] [] cp)  //Cross movement (Horizontal/Vertical) checker for rook and queen.
    {
	int xf = c.charAt (0) - 'A' + 1;
	int yf = c.charAt (1) - '0';

	int xi = coordi.charAt (0) - 'A' + 1;
	int yi = coordi.charAt (1) - '0';

	if (xi == xf && yf > yi) //Forward Movement
	{
	    for (int y = yi + 1 ; y < yf ; y++)
	    {
		if (cp [xi] [y] != null)
		{
		    return false;
		}
	    }
	}

	else if (xi == xf && yf < yi) //Backwards Movement
	{
	    for (int y = yi - 1 ; y > yf ; y--)
	    {
		if (cp [xi] [y] != null)
		{
		    return false;
		}
	    }
	}

	else if (xi > xf && yf == yi) //Left Movement
	{
	    for (int x = xi - 1 ; x > xf ; x--)
	    {
		if (cp [x] [yi] != null)
		{
		    return false;
		}
	    }
	}

	else if (xi < xf && yf == yi) //Right Movement
	{
	    for (int x = xi + 1 ; x < xf ; x++)
	    {
		if (cp [x] [yi] != null)
		{
		    return false;
		}
	    }
	}

	return true;
    }


    public String toString ()
    {
	return "Coordinate: " + coordi + " Color: " + clr + " Class: " + cls + " Move status: " + moved;
    }
}


