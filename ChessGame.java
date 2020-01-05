import java.awt.*;
import java.io.*;
import hsa.Console;

public class ChessGame
{
    static Console c, d;
    public static void main (String[] args) throws IOException
    {
	BufferedReader br = new BufferedReader (new InputStreamReader (System.in));

	//Title Screen
	c = new Console (21, 53);
	Color db = new Color (162, 126, 79); //new color
	c.setColor (db);
	c.fillRect (1, 1, 5000, 6000);
	Font l = new Font ("Algerian", Font.PLAIN, 40); // makes a new font
	c.setFont (l); //sets font

	c.setColor (Color.white); //set new color
	c.drawString ("CHESS.JAVA", 90, 130);
	Font t = new Font ("Algerian", Font.PLAIN, 30); // new font
	c.setFont (t); //set font

	// Creates Options on the Introduction Screen
	c.drawString ("NEW GAME", 134, 215);
	c.drawString ("LOAD GAME", 128, 260);
	c.drawString ("HOW TO PLAY", 116, 305);
	c.drawString ("EXIT", 176, 348);

	Font a = new Font ("Algerian", Font.PLAIN, 18); // new font
	c.setFont (a); //set font
	c.drawString ("(Press N)", 165, 233);
	c.drawString ("(Press L)", 165, 278);
	c.drawString ("(Press H)", 165, 322);
	c.drawString ("(Press E)", 165, 367);

	String letter, bkc = "", wkc = ""; //bkc and wkc are string variables that track each King's coordinate
	int side = 0;
	ChessPiece[] [] board = new ChessPiece [9] [9];
	ChessGraphics cg = new ChessGraphics (board, c);
	do
	{
	    letter = br.readLine ();
	    if (letter.equalsIgnoreCase ("N"))
	    {
		//initializing process
		board ();
		for (int x = 1 ; x <= 8 ; x++)
		{
		    board [x] [2] = new Pawn (((char) ('A' + x - 1)) + "2", 'w');
		    board [x] [7] = new Pawn (((char) ('A' + x - 1)) + "7", 'b');
		}
		board [1] [8] = new Rook ("A8", 'b');
		board [8] [8] = new Rook ("H8", 'b');
		board [1] [1] = new Rook ("A1", 'w');
		board [8] [1] = new Rook ("H1", 'w');
		board [2] [1] = new Knight ("B1", 'w');
		board [7] [1] = new Knight ("G1", 'w');
		board [7] [8] = new Knight ("G8", 'b');
		board [2] [8] = new Knight ("B8", 'b');
		board [6] [8] = new Bishop ("F8", 'b');
		board [3] [8] = new Bishop ("C8", 'b');
		board [3] [1] = new Bishop ("C1", 'w');
		board [6] [1] = new Bishop ("F1", 'w');
		board [4] [1] = new Queen ("D1", 'w');
		board [4] [8] = new Queen ("D8", 'b');
		board [5] [1] = new King ("E1", 'w');
		board [5] [8] = new King ("E8", 'b');
		boardMaker (board, c, cg);
		side = 1;
		bkc = "E8";
		wkc = "E1";
	    }
	    else if (letter.equalsIgnoreCase ("L"))
	    {
		board ();
		side = textFileReader (board);
		boardMaker (board, c, cg);

		for (int y = 1 ; y <= 8 ; y++) //search algorithm to initialize bkc and wkc when loading
		{
		    for (int x = 1 ; x <= 8 ; x++)
		    {
			if (board [x] [y] != null && board [x] [y].getCls () == 'k')
			{
			    if (board [x] [y].getClr () == 'b')
				bkc = board [x] [y].getCoordi ();
			    else if (board [x] [y].getClr () == 'w')
				wkc = board [x] [y].getCoordi ();
			}
		    }
		}
	    }
	    else if (letter.equalsIgnoreCase ("H"))
	    {
		rules (); //rules method
	    }
	    else if (letter.equalsIgnoreCase ("E"))
	    {
		c.close ();
		System.exit (0);
	    }
	}
	while (!(letter.equalsIgnoreCase ("N") || letter.equalsIgnoreCase ("L")));

	if (side == 1)
	    System.out.println ("White side goes first.\nPlease make your move.");
	else if (side == -1)
	    System.out.println ("Black side goes first.\nPlease make your move.");

	System.out.println ("Choose a coordinate of a chess piece you want to move.");
	System.out.println ("Type s to save and exit at any time, f to forfeit, and r to open up the rules.");

	int xi, yi, xf, yf, xbk, ybk, xwk, ywk;
	xbk = bkc.charAt (0) - 'A' + 1;
	ybk = bkc.charAt (1) - '0';
	xwk = wkc.charAt (0) - 'A' + 1;
	ywk = wkc.charAt (1) - '0';
	boolean isvalid = false;
	String promo, dpt, dst;
	//dpt and dst are variables that save the information of chessboard in two coordinates
	//given by the user, so that undo method can roll back the board when it is an invalid move.
	Pawn p;
	Knight n;
	Bishop b;
	Rook r;
	Queen q;
	//Above are temporary reference variables for each chess pieces
	King bk = (King) board [xbk] [ybk], wk = (King) board [xwk] [ywk];
	//Since there are only two kings on the board and they have several special functions such as castling and check, they have their own reference variable.
	outerloop:
	do
	{
	    //Enable the user to choose to castle.
	    if (side == 1)
	    {
		System.out.println ("It is white's turn.");
		if (wk.isCheck (wkc, board))
		    System.out.println ("Your King is in check.");
	    }
	    else if (side == -1)
	    {
		System.out.println ("It is black's turn.");
		if (bk.isCheck (bkc, board))
		    System.out.println ("Your King is in check.");
	    }

	    do
	    {
		letter = br.readLine ();
		if (letter.equals ("r"))
		    rules ();
	    }
	    while (letter.equals ("r"));

	    if (letter.length () == 2 && (letter.charAt (0) >= 'A' && letter.charAt (0) <= 'H') && (letter.charAt (1) >= '1' && letter.charAt (1) <= '8'))
	    {
		xi = letter.charAt (0) - 'A' + 1;
		yi = letter.charAt (1) - '0';

		isvalid = false;

		if (board [xi] [yi] == null)
		    System.out.println ("There is no chess piece there. Choose it again.");
		else if ((board [xi] [yi].getClr () == 'b' && side == 1) || (board [xi] [yi].getClr () == 'w' && side == -1))
		    System.out.println ("Please choose your own piece.");
		else
		{
		    dpt = board [xi] [yi].getCoordi () + board [xi] [yi].getClr () + board [xi] [yi].getCls () + board [xi] [yi].getMoved ();
		    System.out.println ("Choose where you want to move this piece. Press c to change the moving piece.");
		    do
		    {
			letter = br.readLine ();

			if (letter.length () == 2 && (letter.charAt (0) >= 'A' && letter.charAt (0) <= 'H') && (letter.charAt (1) >= '1' && letter.charAt (1) <= '8'))
			{
			    xf = letter.charAt (0) - 'A' + 1;
			    yf = letter.charAt (1) - '0';

			    if (board [xf] [yf] == null)
				dst = letter + "null";
			    else
				dst = letter + board [xf] [yf].getClr () + board [xf] [yf].getCls () + board [xf] [yf].getMoved ();

			    if (board [xi] [yi].getCls () == 'p')
			    {
				p = (Pawn) board [xi] [yi];
				isvalid = p.move (letter, board);
				if (isvalid && ((letter.charAt (1) == '8' && side == 1) || (letter.charAt (1) == '1' && side == -1)))
				{
				    System.out.println ("Your pawn reached the end. Type what piece you want to promote into.");
				    System.out.println ("Knight: n  Bishop: b  Rook: r  Queen: q");
				    do
				    {
					promo = br.readLine ();
					if (promo.length () == 1 && (promo.charAt (0) == 'n' || promo.charAt (0) == 'b' || promo.charAt (0) == 'r' || promo.charAt (0) == 'q'))
					    break;
					else
					    System.out.println ("Only type in a character: n, b, r, or q.");
				    }
				    while (true);

				    p.promotion (promo.charAt (0), board);
				}
			    }
			    else if (board [xi] [yi].getCls () == 'n')
			    {
				n = (Knight) board [xi] [yi];
				isvalid = n.move (letter, board);
			    }
			    else if (board [xi] [yi].getCls () == 'b')
			    {
				b = (Bishop) board [xi] [yi];
				isvalid = b.move (letter, board);
			    }
			    else if (board [xi] [yi].getCls () == 'r')
			    {
				r = (Rook) board [xi] [yi];
				isvalid = r.move (letter, board);
			    }
			    else if (board [xi] [yi].getCls () == 'q')
			    {
				q = (Queen) board [xi] [yi];
				isvalid = q.move (letter, board);
			    }
			    else if (board [xi] [yi].getCls () == 'k')
			    {
				if (side == 1)
				    isvalid = wk.move (letter, board);
				else if (side == -1)
				    isvalid = bk.move (letter, board);

				if (isvalid && side == 1)
				    wkc = letter;
				else if (isvalid && side == -1)
				    bkc = letter;
			    }


			    if (side == 1 && wk.isCheck (wkc, board))
			    {
				isvalid = false;
				undo (dpt, dst, board);
				System.out.println ("Your King is still in Check.");
			    }
			    else if (side == -1 && bk.isCheck (bkc, board))
			    {
				isvalid = false;
				undo (dpt, dst, board);
				System.out.println ("Your King is still in Check.");
			    }

			    if (!isvalid)
				System.out.println ("That is an invalid move. Type c or a new destination.");
			}
			else if (letter.equals ("f"))
			    break outerloop;
			else if (letter.equals ("c"))
			    break;
			else
			    System.out.println ("That is not a valid destination. Type c or a new destination.");
		    }
		    while (!isvalid);

		    if (!letter.equals ("c"))
		    {
			side *= -1;
			boardMaker (board, c, cg);
		    }
		}
	    }
	    else if (letter.equals ("s"))
	    {
		textFileSaver (board, side);
		System.out.println ("Game saved. See you Next Time.");
		break;
	    }
	    else if (letter.equals ("f"))
	    {
		break;
	    }
	    else
	    {
		System.out.println ("That is not a valid option, do it again.");
	    }
	}
	while (true);

	if (letter.equals ("f") && side == 1)
	{
	    System.out.println ("Black is the winner!");
	}
	else if (letter.equals ("f") && side == -1)
	{
	    System.out.println ("White is the winner!");
	}
    }


    public static void delay (int millisecs)  // Delay Method
    {
	try
	{
	    Thread.currentThread ().sleep (millisecs);
	}
	catch (InterruptedException e)
	{
	}
    }


    public static void board ()  //board method for initialization of graphics screen
    {
	c.clear ();
	//Making the Chess Board
	Font l = new Font ("Algerian", Font.PLAIN, 30); // makes a new font
	c.setFont (l); //sets font
	c.drawString ("CHESS.JAVA", 130, 20);
	Color lb = new Color (235, 223, 184); //Making new colors for chess Board
	Color db = new Color (162, 126, 79); //Making new colors for chess Board
	Color outside = new Color (112, 87, 54);
	c.setColor (outside); //sets color
	c.fillRect (33, 33, 383, 383); //draws outter rectangle of the Chess Board
	int counter = 0;
	for (int i = 63 ; i <= 343 ; i += 40) //Nested for Loop to make and color the board
	{
	    for (int j = 63 ; j <= 343 ; j += 40)
	    {
		if (counter % 2 == 0)
		    c.setColor (lb);
		else
		    c.setColor (db);
		c.fillRect (j, i, 40, 40);
		counter++;
	    }
	    counter++;
	}
	Font normal = new Font ("Times New Roman", Font.PLAIN, 28); //Makes a new font
	c.setFont (normal); //sets font
	c.setColor (Color.white); //sets the color white
	counter = 0;
	int count = 0;
	for (int i = 73 ; i < 383 ; i += 40)
	{
	    c.drawString ((char) ('A' + counter++) + "", i, 57);
	    c.drawString ((char) ('A' + count++) + "", i, 408);
	}
	int negcounter = 375;
	for (int k = 1 ; k < 9 ; k++)
	{
	    c.drawString (k + "", 42, negcounter);
	    c.drawString (k + "", 390, negcounter);
	    negcounter -= 41;
	}
    }


    public static void rules ()
    {
	d = new Console (30, 115);
	//Creates the Rules Page in the game
	Color db = new Color (162, 126, 79); //new color
	d.setColor (db);
	d.fillRect (1, 1, 5000, 6000);
	d.setColor (Color.white);
	Font t = new Font ("Algerian", Font.PLAIN, 30); // new font
	d.setFont (t); //set font
	d.drawString ("HOW TO PLAY", 357, 45);
	Font normal = new Font ("Times New Roman", Font.PLAIN, 18); // creates a new font
	d.setFont (normal); // sets Font
	d.drawString ("1. The objective of the game is capture (Checkmate) the opposing team's King. It must not be allowed to escape. ", 10, 75);
	d.drawString ("2. Each piece as their own special way of moving.", 10, 105);
	d.drawString ("- Pawns move 1 space forwards or diagonally if they're attacking (first move they can move 2 spaces)", 25, 135);
	d.drawString ("- Knights can move 2 spaces forwards,backwards,sideways, and then a 90 degree move angle of the first move(L shape)", 25, 165);
	d.drawString ("- Knights are the only piece that can jump over other pieces (move even if pieces in the way)", 25, 195);
	d.drawString ("- Queens can move in any direction with no restrictions on how much spaces they can move at time", 25, 225);
	d.drawString ("- Kings can move in any direction but only one space at a time", 25, 255);
	d.drawString ("- Bishops can only move diagonally on the color  it's placed any amount of spaces", 25, 285);
	d.drawString ("- So if any bishop is on the white squares it stays on white same thing if placed on black", 25, 315);
	d.drawString ("- Rooks can move an unlimited amount of spaces at a time but only forwards,backwards,sideways", 25, 345);
	d.drawString ("3. Each chess piece can take another opponent, Pawns take opponents if they're diagonally in front of them", 10, 375);
	d.drawString ("- Every other piece takes them by going into them", 25, 405);
	d.drawString ("4. Promotion takes place when your pawn reaches the opposite end of the board. The pawn can promote into", 10, 435);
	d.drawString ("- Queen, Rook, Knight, Bishop", 25, 465);
	d.drawString ("5. Castling is when neither the king or the rooks chosen has moved previously", 10, 495);
	d.drawString ("- There are no pieces between the King and the chosen rook", 25, 525);
	d.drawString ("- The King can't be in check", 25, 555);
	d.drawString ("- King moves two spaces towards the rook and the rook moves beside the king on the closer side to the middle of the board", 25, 585);
    }


    //it reads in the board and calls appropriate graphics command and draw it on the console window
    public static void boardMaker (ChessPiece[] [] cp, Console c, ChessGraphics cg)
    {
	Color lb = new Color (235, 223, 184);
	Color db = new Color (162, 126, 79);
	board ();
	for (int i = 1 ; i <= 8 ; i++)
	{
	    for (int j = 1 ; j <= 8 ; j++)
	    {
		if (cp [i] [j] == null)
		{
		    if (i % 2 == j % 2)
			c.setColor (db);
		    else
			c.setColor (lb);
		    c.fillRect (23 + i * 40, 383 - j * 40, 40, 40);
		}
		//White Pawn
		else if (cp [i] [j].getCls () == 'p' && cp [i] [j].getClr () == 'w')
		{
		    cg.drawWPawn (i, j);
		}
		//Black Pawn
		else if (cp [i] [j].getCls () == 'p' && cp [i] [j].getClr () == 'b')
		{
		    cg.drawBPawn (i, j);
		}
		//Black Rook
		else if (cp [i] [j].getCls () == 'r' && cp [i] [j].getClr () == 'b')
		{
		    cg.drawBRook (i, j);
		}
		//White Rook
		else if (cp [i] [j].getCls () == 'r' && cp [i] [j].getClr () == 'w')
		{
		    cg.drawWRook (i, j);
		}
		//Black Knight
		else if (cp [i] [j].getCls () == 'n' && cp [i] [j].getClr () == 'b')
		{
		    cg.drawBKnight (i, j);
		}
		//White Knight
		else if (cp [i] [j].getCls () == 'n' && cp [i] [j].getClr () == 'w')
		{
		    cg.drawWKnight (i, j);
		}
		//Black Bishop
		else if (cp [i] [j].getCls () == 'b' && cp [i] [j].getClr () == 'b')
		{
		    cg.drawBBishop (i, j);
		}
		//White Bishop
		else if (cp [i] [j].getCls () == 'b' && cp [i] [j].getClr () == 'w')
		{
		    cg.drawWBishop (i, j);
		}
		//Black Queen
		else if (cp [i] [j].getCls () == 'q' && cp [i] [j].getClr () == 'b')
		{
		    cg.drawBQueen (i, j);
		}
		//White Queen
		else if (cp [i] [j].getCls () == 'q' && cp [i] [j].getClr () == 'w')
		{
		    cg.drawWQueen (i, j);
		}
		//White King
		else if (cp [i] [j].getCls () == 'k' && cp [i] [j].getClr () == 'w')
		{
		    cg.drawWKing (i, j);
		}
		//Black King
		else if (cp [i] [j].getCls () == 'k' && cp [i] [j].getClr () == 'b')
		{
		    cg.drawBKing (i, j);
		}
	    }
	}
    }


    //it reads in the 2D array and saves information into a text file
    public static void textFileSaver (ChessPiece[] [] cp, int side) throws IOException
    {
	PrintWriter pr = new PrintWriter (new FileWriter ("savedgame.txt"));
	for (int y = 1 ; y <= 8 ; y++)
	{
	    for (int x = 1 ; x <= 8 ; x++)
	    {
		if (cp [x] [y] == null)
		    pr.println ("empty");
		else
		    pr.println (cp [x] [y].getCoordi () + cp [x] [y].getClr () + cp [x] [y].getCls () + cp [x] [y].getMoved ());
	    }
	}
	pr.println (side);
	pr.close ();
    }


    //it reads in the text file and create appropriate chess piece objects on the board.
    public static int textFileReader (ChessPiece[] [] cp) throws IOException
    {
	try //Exception handling to start a new game if a user tries to load a file without saving.
	{
	    BufferedReader fr = new BufferedReader (new FileReader ("savedgame.txt"));
	}
	catch (FileNotFoundException fnfe)  //if file is not found, initialize the board.
	{
	    for (int i = 1 ; i <= 8 ; i++)
	    {
		cp [i] [2] = new Pawn (((char) ('A' + i - 1)) + "2", 'w');
		cp [i] [7] = new Pawn (((char) ('A' + i - 1)) + "7", 'b');
	    }
	    cp [1] [8] = new Rook ("A8", 'b');
	    cp [8] [8] = new Rook ("H8", 'b');
	    cp [1] [1] = new Rook ("A1", 'w');
	    cp [8] [1] = new Rook ("H1", 'w');
	    cp [2] [1] = new Knight ("B1", 'w');
	    cp [7] [1] = new Knight ("G1", 'w');
	    cp [7] [8] = new Knight ("G8", 'b');
	    cp [2] [8] = new Knight ("B8", 'b');
	    cp [6] [8] = new Bishop ("F8", 'b');
	    cp [3] [8] = new Bishop ("C8", 'b');
	    cp [3] [1] = new Bishop ("C1", 'w');
	    cp [6] [1] = new Bishop ("F1", 'w');
	    cp [4] [1] = new Queen ("D1", 'w');
	    cp [4] [8] = new Queen ("D8", 'b');
	    cp [5] [1] = new King ("E1", 'w');
	    cp [5] [8] = new King ("E8", 'b');
	    return 1;
	}

	BufferedReader fr = new BufferedReader (new FileReader ("savedgame.txt"));
	for (int y = 1 ; y <= 8 ; y++)
	{
	    for (int x = 1 ; x <= 8 ; x++)
	    {
		String info = fr.readLine ();

		if (info.equals ("empty"))
		    cp [x] [y] = null;
		else
		{
		    String crd = info.substring (0, 2);
		    char clr = info.charAt (2);
		    char cls = info.charAt (3);
		    if (cls == 'p')
		    {
			cp [x] [y] = new Pawn (crd, clr);
			if (info.charAt (4) == 't')
			    cp [x] [y].setMoved (true);
		    }
		    else if (cls == 'n')
		    {
			cp [x] [y] = new Knight (crd, clr);
			if (info.charAt (4) == 't')
			    cp [x] [y].setMoved (true);
		    }
		    else if (cls == 'b')
		    {
			cp [x] [y] = new Bishop (crd, clr);
			if (info.charAt (4) == 't')
			    cp [x] [y].setMoved (true);
		    }
		    else if (cls == 'r')
		    {
			cp [x] [y] = new Rook (crd, clr);
			if (info.charAt (4) == 't')
			    cp [x] [y].setMoved (true);
		    }
		    else if (cls == 'q')
		    {
			cp [x] [y] = new Queen (crd, clr);
			if (info.charAt (4) == 't')
			    cp [x] [y].setMoved (true);
		    }
		    else if (cls == 'k')
		    {
			cp [x] [y] = new King (crd, clr);
			if (info.charAt (4) == 't')
			    cp [x] [y].setMoved (true);
		    }
		}
	    }
	}
	return Integer.parseInt (fr.readLine ());
    }


    //this method rewinds the chess board after one move.
    public static void undo (String i, String f, ChessPiece[] [] cp)
    {
	String crd1 = i.substring (0, 2);
	int xi = crd1.charAt (0) - 'A' + 1;
	int yi = crd1.charAt (1) - '0';
	char clr1 = i.charAt (2);
	char cls1 = i.charAt (3);
	if (cls1 == 'p')
	{
	    cp [xi] [yi] = new Pawn (crd1, clr1);
	    if (i.charAt (4) == 't')
		cp [xi] [yi].setMoved (true);
	}
	else if (cls1 == 'n')
	{
	    cp [xi] [yi] = new Knight (crd1, clr1);
	    if (i.charAt (4) == 't')
		cp [xi] [yi].setMoved (true);
	}
	else if (cls1 == 'b')
	{
	    cp [xi] [yi] = new Bishop (crd1, clr1);
	    if (i.charAt (4) == 't')
		cp [xi] [yi].setMoved (true);
	}
	else if (cls1 == 'r')
	{
	    cp [xi] [yi] = new Rook (crd1, clr1);
	    if (i.charAt (4) == 't')
		cp [xi] [yi].setMoved (true);
	}
	else if (cls1 == 'q')
	{
	    cp [xi] [yi] = new Queen (crd1, clr1);
	    if (i.charAt (4) == 't')
		cp [xi] [yi].setMoved (true);
	}
	else if (cls1 == 'k')
	{
	    cp [xi] [yi] = new King (crd1, clr1);
	    if (i.charAt (4) == 't')
		cp [xi] [yi].setMoved (true);
	}

	String crd2 = f.substring (0, 2);
	int xf = crd2.charAt (0) - 'A' + 1;
	int yf = crd2.charAt (1) - '0';
	if (f.length () != 6)
	{
	    char clr2 = f.charAt (2);
	    char cls2 = f.charAt (3);

	    if (cls2 == 'p')
	    {
		cp [xf] [yf] = new Pawn (crd2, clr2);
		if (f.charAt (4) == 't')
		    cp [xf] [yf].setMoved (true);
	    }
	    else if (cls2 == 'n')
	    {
		cp [xf] [yf] = new Knight (crd2, clr2);
		if (f.charAt (4) == 't')
		    cp [xf] [yf].setMoved (true);
	    }
	    else if (cls2 == 'b')
	    {
		cp [xf] [yf] = new Bishop (crd2, clr2);
		if (f.charAt (4) == 't')
		    cp [xf] [yf].setMoved (true);
	    }
	    else if (cls2 == 'r')
	    {
		cp [xf] [yf] = new Rook (crd2, clr2);
		if (f.charAt (4) == 't')
		    cp [xf] [yf].setMoved (true);
	    }
	    else if (cls2 == 'q')
	    {
		cp [xf] [yf] = new Queen (crd2, clr2);
		if (f.charAt (4) == 't')
		    cp [xf] [yf].setMoved (true);
	    }
	    else if (cls2 == 'k')
	    {
		cp [xf] [yf] = new King (crd2, clr2);
		if (f.charAt (4) == 't')
		    cp [xf] [yf].setMoved (true);
	    }
	}
	else
	{
	    cp [xf] [yf] = null;
	}

    }
}


