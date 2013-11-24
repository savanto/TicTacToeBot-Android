//package com.savanto.tictactoebot;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//import android.util.Log;
//
//public class BoardOLD
//{
//	private final int x, o;
//	private final int turn;
//	private ArrayList<Board> children;
//	private static Board[][] gen = new Board[512][512];
//
////	public Board()
////	{
////		this(0, 0, 0);
////	}
////
////	public Board(int turn)
////	{
////		this(0, 0, turn);
////	}
//
//	public BoardOLD(int x, int o, int turn)
//	{
//		this.x = x;
//		this.o = o;
//		this.turn = turn;
//		this.children = new ArrayList<Board>();
//	}
//
//	private BoardOLD(int x, int o, int turn, ArrayList<Board> children)
//	{
//		this.x = x;
//		this.o = o;
//		this.turn = turn;
//		this.children = children;
//	}
//
//	public void generate()
//	{
//		// Check for game over conditions
//		// 1. Winner with a row/col/diag filled
//		// Diagonals
//		if ((this.x & Board.DIAG1) == Board.DIAG1 || (this.o & Board.DIAG1) == Board.DIAG1)
//			return;
//		if ((this.x & Board.DIAG2) == Board.DIAG2 || (this.o & Board.DIAG2) == Board.DIAG2)
//			return;
//		// Rows, cols
//		for (int i = 0, row, col; i < Board.SIZE; i++)
//		{
//			row = Board.ROW << (i * Board.SIZE);
//			if ((this.x & row) == row || (this.o & row) == row)
//				return;
//			col = Board.COL << i;
//			if ((this.x & col) == col || (this.o & col) == col)
//				return;
//		}
//
//		// 2. Tie: no victory found, check if board is full.
//		if ((this.x | this.o) == Board.TIE)
//			return;
//
//		/////////////////////////////////////////////////////
//		// Generate child boards recursively by traversing
//		// this board and checking all cells to find empty ones.
//		for (int pos = 1, x = 0, o = 0, turn = 0; pos < (1 << SBoard.CELLS); pos <<= 1)
//		{
//			// Cell must be empty for move to be possible
//			if (((this.x | this.o) & pos) == 0)
//			{
//				// Check which player will take the cell.
//				switch (this.turn)
//				{
//					case 0:	// X to move
//						x = this.x | pos;
//						o = this.o;
//						turn = 1;
//						break;
//					case 1:	// O to move
//						x = this.x;
//						o = this.o | pos;
//						turn = 0;
//						break;
//				}
//				// Check for identical/symmetrical Boards
//				Board child = Board.symmetrical(x, o);
//				// If no Boards found, make a new one.
//				if (child == null)
//				{
//					child = new Board(x, o, turn);
//					Board.gen[x][o] = child;
//					child.generate();
//				}
//				this.children.add(child);
//			}
//		}
//	}
//
//	private static int rotate90(int n)
//	{
//		int r = 0;
//	    r |= (n & (Board.A | Board.F)) >> 2;
//	    r |= (n & Board.B) >> 4;
//	    r |= (n & Board.C) >> 6;
//	    r |= (n & (Board.D | Board.I)) << 2;
//	    r |= (n & Board.E);
//	    r |= (n & Board.G) << 6;
//	    r |= (n & Board.H) << 4;
//	    return r;
//	}
//
//	private static Board rotate90(Board b)
//	{
//		return new Board(Board.rotate90(b.x), Board.rotate90(b.o), b.turn, b.children);
//	}
//
//	private static int rotate180(int n)
//	{
//		return Board.rotate90(Board.rotate90(n));
//	}
//
//	private static Board rotate180(Board b)
//	{
//		return new Board(Board.rotate180(b.x), Board.rotate180(b.o), b.turn, b.children);
//	}
//
//	private static int rotate270(int n)
//	{
//		return Board.rotate90(Board.rotate180(n));
//	}
//
//	private static Board rotate270(Board b)
//	{
//		return new Board(Board.rotate270(b.x), Board.rotate270(b.o), b.turn, b.children);
//	}
//
//	private static int reflectYaxis(int n)
//	{
//		int r = 0;
//		r |= (n & (Board.COL << 2)) >> 2;
//		r |= (n & (Board.COL << 1));
//		r |= (n & Board.COL) << 2;
//		return r;
//	}
//
//	private static Board reflectYaxis(Board b)
//	{
//		return new Board(Board.reflectYaxis(b.x), Board.reflectYaxis(b.o), b.turn, b.children);
//	}
//
//	private static int reflectXaxis(int n)
//	{
//		int r = 0;
//		r |= (n & (Board.ROW << 6)) >> 6;
//		r |= (n & (Board.ROW << 3));
//		r |= (n & (Board.ROW)) << 6;
//		return r;
//	}
//
//	private static Board reflectXaxis(Board b)
//	{
//		return new Board(Board.reflectXaxis(b.x), Board.reflectXaxis(b.o), b.turn, b.children);
//	}
//
//	private static Board symmetrical(int x, int o)
//	{
//		// Existence of identical Board
//		if (Board.gen[x][o] != null)
//			return Board.gen[x][o];
//		// Reflection over Y-axis
//		int n = Board.reflectYaxis(x);
//		int m = Board.reflectYaxis(o);
//		if (Board.gen[n][m] != null)
//			return Board.gen[n][m];
//		// Reflection over Y-axis and 90 degree rotation
//		n = Board.rotate90(n);
//		m = Board.rotate90(m);
//		if (Board.gen[n][m] != null)
//			return Board.gen[n][m];
//		// Reflection over X-axis
//		n = Board.reflectXaxis(x);
//		m = Board.reflectXaxis(o);
//		if (Board.gen[n][m] != null)
//			return Board.gen[n][m];
//		// Reflection over X-axis and 90 degree rotation
//		n = Board.rotate90(n);
//		m = Board.rotate90(m);
//		if (Board.gen[n][m] != null)
//			return Board.gen[n][m];
//		// Rotation by 90, 180, 270 degrees
//		for (int i = 0; i < 3; i++)
//		{
//			x = Board.rotate90(x);
//			o = Board.rotate90(o);
//			if (Board.gen[x][o] != null)
//				return Board.gen[x][o];
//		}
//		// No similar Boards found.
//		return null;
//	}
//
//	public boolean isGameOver()
//	{
//		return this.children.size() == 0 ? true : false;
//	}
//
//	public Board getChild(int x, int o)
//	{
//		final int x_reflectedY = Board.reflectYaxis(x);
//		final int o_reflectedY = Board.reflectYaxis(o);
//
//		final int x_reflectedX = Board.reflectXaxis(x);
//		final int o_reflectedX = Board.reflectXaxis(o);
//
//		final int x_reflectedY_rotated90 = Board.rotate90(x_reflectedY);
//		final int o_reflectedY_rotated90 = Board.rotate90(o_reflectedY);
//
//		final int x_reflectedX_rotated90 = Board.rotate90(x_reflectedX);
//		final int o_reflectedX_rotated90 = Board.rotate90(o_reflectedX);
//
//		final int x_rotated90 = Board.rotate90(x);
//		final int o_rotated90 = Board.rotate90(o);
//		final int x_rotated180 = Board.rotate90(x_rotated90);
//		final int o_rotated180 = Board.rotate90(o_rotated90);
//		final int x_rotated270 = Board.rotate90(x_rotated180);
//		final int o_rotated270 = Board.rotate90(o_rotated180);
//
//		Board child;
//		for (int i = 0; i < this.children.size(); i++)
//		{
//			child = this.children.get(i);
//			Log.d("TIC", Integer.toString(child.x) + " " + Integer.toString(child.o));
//			if (child.x == x && child.o == o)
//				return child;
//			else if (child.x == x_reflectedY && child.o == o_reflectedY)
//				return child;//Board.reflectYaxis(child);
//			else if (child.x == x_reflectedX && child.o == o_reflectedX)
//				return child;//Board.reflectXaxis(child);
//			else if (child.x == x_reflectedY_rotated90 && child.o == o_reflectedY_rotated90)
//				return child;//Board.reflectYaxis(Board.rotate270(child));
//			else if (child.x == x_reflectedX_rotated90 && child.o == o_reflectedX_rotated90)
//				return child;//Board.reflectXaxis(Board.rotate270(child));
//			else if (child.x == x_rotated90 && child.o == o_rotated90)
//				return child;//Board.rotate270(child);
//			else if (child.x == x_rotated180 && child.o == o_rotated180)
//				return child;//Board.rotate180(child);
//			else if (child.x == x_rotated270 && child.o == o_rotated270)
//				return child;//Board.rotate90(child);
//		}
//		return null;
//	}
//
////	private boolean isEqual(Board rhs)
////	{
////		return this.x == rhs.x && this.o == rhs.o;
////	}
////
//	public Board pickChild(Random rng)//, int x, int o)
//	{
//		int n = this.children.size();
//		if (n > 0)
//			return this.children.get(rng.nextInt(n));
//		return null;
//	}
////		if (this.x == x && this.o == o)
////			return child;
////
////		int x_reflectedY = Board.reflectYaxis(x);
////		int o_reflectedY = Board.reflectYaxis(o);
////
////		if (this.x == x_reflectedY && this.o == o_reflectedY)
////			return Board.reflectYaxis(child);
////
////		int x_reflectedX = Board.reflectXaxis(x);
////		int o_reflectedX = Board.reflectXaxis(o);
////
////		if (this.x == x_reflectedX && this.o == o_reflectedX)
////			return Board.reflectXaxis(child);
////
////		int x_reflectedY_rotated90 = Board.rotate90(x_reflectedY);
////		int o_reflectedY_rotated90 = Board.rotate90(o_reflectedY);
////
////		if (this.x == x_reflectedY_rotated90 && this.o == o_reflectedY_rotated90)
////			return Board.reflectYaxis(Board.rotate270(child));
////
////		int x_reflectedX_rotated90 = Board.rotate90(x_reflectedX);
////		int o_reflectedX_rotated90 = Board.rotate90(o_reflectedX);
////
////		if (child.x == x_reflectedX_rotated90 && child.o == o_reflectedX_rotated90)
////			return Board.reflectXaxis(Board.rotate270(child));
////
////		int x_rotated90 = Board.rotate90(x);
////		int o_rotated90 = Board.rotate90(o);
////
////		if (child.x == x_rotated90 && child.o == o_rotated90)
////			return Board.rotate270(child);
////
////		int x_rotated180 = Board.rotate90(x_rotated90);
////		int o_rotated180 = Board.rotate90(o_rotated90);
////
////		if (child.x == x_rotated180 && child.o == o_rotated180)
////			return Board.rotate180(child);
////
////		int x_rotated270 = Board.rotate90(x_rotated180);
////		int o_rotated270 = Board.rotate90(o_rotated180);
////
////		if (child.x == x_rotated270 && child.o == o_rotated270)
////			return Board.rotate90(child);
////
////		return null;
////	}
//
//	private int diff(Board b)
//	{
//		int diff = (this.x | this.o) ^ (b.x | b.o);
//		for (int n = Board.A; n > 0; n >>= 1)
//		{
//			if (diff == n)
//				return diff;
//		}
//		return 0;
//	}
//
//	public int getDiff(Board child)
//	{
//		// Produce all children symmetrical to the selected child
//		// that are exactly one move away from the parent.
//		ArrayList<Integer> sym = new ArrayList<Integer>();
//		int diff = this.diff(child);
//		if (diff > 0)
//			sym.add(diff);
//		diff = this.diff(Board.reflectYaxis(child));
//		if (diff > 0)
//			sym.add(diff);
//		diff = this.diff(Board.reflectXaxis(child));
//		if (diff > 0)
//			sym.add(diff);
//		diff = this.diff(Board.rotate90(Board.reflectYaxis(child)));
//		if (diff > 0)
//			sym.add(diff);
//		diff = this.diff(Board.rotate90(Board.reflectXaxis(child)));
//		if (diff > 0)
//			sym.add(diff);
//		diff = this.diff(Board.rotate90(child));
//		if (diff > 0)
//			sym.add(diff);
//		diff = this.diff(Board.rotate180(child));
//		if (diff > 0)
//			sym.add(diff);
//		diff = this.diff(Board.rotate270(child));
//		if (diff > 0)
//			sym.add(diff);
//
//		int m = sym.size();
//		if (m > 1)
//			return
//	}
//
////	public int diff(Board b, int x, int o)
////	{
////		Log.d("TIC", Integer.toString(this.x) + " " + Integer.toString(this.o) + "; " + Integer.toString(b.x) + " " + Integer.toString(b.o));
////		int diff = (this.x | this.o) ^ (b.x | b.o);
////
////		if (b.x == x && b.o == o)
////			return diff;
////
////		int x_reflectedY = Board.reflectYaxis(x);
////		int o_reflectedY = Board.reflectYaxis(o);
////
////		if (b.x == x_reflectedY && b.o == o_reflectedY)
////			return Board.reflectYaxis(diff);
////
////		int x_reflectedX = Board.reflectXaxis(x);
////		int o_reflectedX = Board.reflectXaxis(o);
////
////		if (b.x == x_reflectedX && b.o == o_reflectedX)
////			return Board.reflectXaxis(diff);
////
////		int x_reflectedY_rotated90 = Board.rotate90(x_reflectedY);
////		int o_reflectedY_rotated90 = Board.rotate90(o_reflectedY);
////
////		if (b.x == x_reflectedY_rotated90 && b.o == o_reflectedY_rotated90)
////			return Board.reflectYaxis(Board.rotate270(diff));
////
////		int x_reflectedX_rotated90 = Board.rotate90(x_reflectedX);
////		int o_reflectedX_rotated90 = Board.rotate90(o_reflectedX);
////
////		if (b.x == x_reflectedX_rotated90 && b.o == o_reflectedX_rotated90)
////			return Board.reflectXaxis(Board.rotate270(diff));
////
////		int x_rotated90 = Board.rotate90(x);
////		int o_rotated90 = Board.rotate90(o);
////
////		if (b.x == x_rotated90 && b.o == o_rotated90)
////			return Board.rotate270(diff);
////
////		int x_rotated180 = Board.rotate90(x_rotated90);
////		int o_rotated180 = Board.rotate90(o_rotated90);
////
////		if (b.x == x_rotated180 && b.o == o_rotated180)
////			return Board.rotate180(diff);
////
////		int x_rotated270 = Board.rotate90(x_rotated180);
////		int o_rotated270 = Board.rotate90(o_rotated180);
////
////		if (b.x == x_rotated270 && b.o == o_rotated270)
////			return Board.rotate90(diff);
////
////		return 0;
////	}
//
//	private static final int SIZE = 3;
//	public static final int CELLS = SIZE * SIZE;
//
//	private static final int ROW = 7;	// 000 000 111
//	private static final int COL = 73;	// 001 001 001
//	private static final int DIAG1 = 273;// 100 010 001
//	private static final int DIAG2 = 84;// 001 010 100
//	public static final int TIE = 511;	// 111 111 111
//	private static final int A = 1 << 8;// 100 000 000
//	private static final int B = 1 << 7;// 010 000 000
//	private static final int C = 1 << 6;// 001 000 000
//	private static final int D = 1 << 5;// 000 100 000
//	private static final int E = 1 << 4;// 000 010 000
//	private static final int F = 1 << 3;// 000 001 000
//	private static final int G = 1 << 2;// 000 000 100
//	private static final int H = 1 << 1;// 000 000 010
//	private static final int I = 1;		// 000 000 001
//
//}
