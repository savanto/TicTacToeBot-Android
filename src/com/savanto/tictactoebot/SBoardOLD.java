//package com.savanto.tictactoebot;
//
//import java.util.ArrayList;
//
//public class SBoardOLD
//{
//	private int board;
//	private ArrayList<SBoardOLD> children = new ArrayList<SBoardOLD>();
//	public static int tot = 0;
//
//	public SBoardOLD()
//	{
//		this(0);
//	}
//
//	public SBoardOLD(int board)
//	{
//		this.board = board;
//	}
//
//	public void generate(int x, int o, int turn)
//	{
//		SBoardOLD.tot++;
//		// Check for game over conditions
//		// 1. Winner with a row/col/diag filled
//		// Diagonals
//		if ((x & SBoardOLD.DIAG1) == SBoardOLD.DIAG1 || (o & SBoardOLD.DIAG1) == SBoardOLD.DIAG1)
//			return;
//		if ((x & SBoardOLD.DIAG2) == SBoardOLD.DIAG2 || (o & SBoardOLD.DIAG2) == SBoardOLD.DIAG2)
//			return;
//		// Rows, cols
//		for (int i = 0, row, col; i < SBoardOLD.SIZE; i++)
//		{
//			row = SBoardOLD.ROW << (i * SBoardOLD.SIZE);
//			if ((x & row) == row || (o & row) == row)
//				return;
//			col = SBoardOLD.COL << i;
//			if ((x & col) == col || (o & col) == col)
//				return;
//		}
//
//		int board = x | o;
//		// 2. Tie: no victory found, check if board is full.
//		if (board == SBoardOLD.TIE)
//			return;
//
//		/////////////////////////////////////////////////////
//		// Generate child boards recursively by traversing
//		// this board and checking all cells to find empty ones.
//		SBoardOLD child;
//		for (int pos = 1, n = 0, m = 0, t = 0; pos < (1 << SBoardOLD.CELLS); pos <<= 1)
//		{
//			if ((board & pos) != 0)
//				return;
//
//			switch (turn)
//			{
//				case 0:	// X to move
//					n = x | pos;
//					m = o;
//					t = 1;
//					break;
//				case 1:	// O to move
//					n = x;
//					m = o | pos;
//					t = 0;
//					break;
//			}
//			child = new SBoardOLD(board | pos);
//			this.children.add(child);
//			child.generate(n, m, t);
//		}
//	}
//
//	private static final int SIZE = 3;
//	public static final int CELLS = SIZE * SIZE;
//
//	private static final int ROW = 7;		// 000 000 111
//	private static final int COL = 73;		// 001 001 001
//	private static final int DIAG1 = 273;	// 100 010 001
//	private static final int DIAG2 = 84;	// 001 010 100
//	private static final int TIE = 511;		// 111 111 111
//}
