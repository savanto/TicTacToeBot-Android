package com.savanto.tictactoebot;

/**
 * Defines a single given position on a 3x3 TicTacToe board, as
 * well as all possible transformations (rotation, reflection) of
 * the given position, by the position of x's and o'x.
 */
public class Board
{
	/**
	 * 		R O T A T I O N     CW 90 DEG
	 *
	 * 	R	x x . 	. o x	. . .	. . .
	 * 	E	o . . 	. . x	. . o	x . .
	 * 	F	. . . 	. . .	. x x	x o .
	 * 	L
	 *	E
	 *		-----------------------------
	 *	C
	 * 	T
	 * 	I	. . .	. . .	. x x	x o .
	 * 	O	o . .	. . x	. . o	x . .
	 * 	N	x x .	. o x	. . .	. . .
	 *
	 * 		R O T A T I O N    CCW 90 DEG
	 *
	 * Each unique Board represents one of eight possible TicTacToe boards
	 * that can be obtained by rotation and reflection. A Board and all of
	 * its possible transformations will be stored in two longs:
	 * one for x's, one for o's.
	 * A final Board (game over) may have at most 5 x's or 5 o's at any one time,
	 * but since we don't need to store final boards, we can store up to penultimate
	 * boards with a maximum of 4 of each mark.
	 * Hence, each mark needs only 32 bits to uniquely store a possible Board with
	 * all transformations.
	 */

	// The
}
