package com.savanto.tictactoebot;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
	private static final String X = "X";
	private static final String O = "O";
	private static final long THINK_TIME = 500;

	private CellView[] cells = new CellView[Board.CELLS];
	private TextView status;
	private Board curBoard;
	private int x, o;
	private int turn;
	private static final Random rng = new Random();
	private int players;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);

		this.players = 1;

		this.turn = 0;
		this.x = 0;
		this.o = 0;
		this.curBoard = new Board(this.x, this.o, this.turn);
		this.curBoard.generate();

		// Set up game board cells to listen for clicks
		SquareLayout s = (SquareLayout) this.findViewById(R.id.board);
		for (int i = 0; i < SBoardOLD.CELLS; i++)
		{
			CellView cell = (CellView) s.getChildAt(i); 
			cell.setOnClickListener(new CellClickListener());
			this.cells[cell.getIndex()] = cell;
		}

		// New game button
		Button newGame = (Button) this.findViewById(R.id.new_game);
		newGame.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO
				MainActivity.this.onCreate(null);
			}
		});

		// Game status label
		this.status = (TextView) this.findViewById(R.id.status);
		this.updateStatus(false);
	}



	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private class CellClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			// Set correct mark based on current turn
			CellView cell = (CellView) v;
			switch (MainActivity.this.turn)
			{
				case 0:	// X to move
					cell.setText(MainActivity.X);
					MainActivity.this.x |= (1 << cell.getIndex());
					MainActivity.this.turn = 1;
					break;
				case 1:	// O to move
					cell.setText(MainActivity.O);
					MainActivity.this.o |= (1 << cell.getIndex());
					MainActivity.this.turn = 0;
					break;
			}
			// Prevent further marks on this cell.
			cell.setMarked(true);

			// Get the next Board in the game tree.
			Board child = MainActivity.this.curBoard.getChild(MainActivity.this.x, MainActivity.this.o);

			// If child move not found, set error and disable further gameplay.
			// This should not normally happen.
			if (child == null)
			{
				Toast.makeText(MainActivity.this, "No such child", Toast.LENGTH_SHORT).show();
				for (int i = 0; i < Board.CELLS; i++)
					MainActivity.this.cells[i].setClickable(false);
				MainActivity.this.updateStatus(R.string.error);
				return;
			}

			// Otherwise set the current board to the child.
			MainActivity.this.curBoard = child;

			// Check for victory
			if (MainActivity.this.curBoard.isGameOver())
			{
				// Tie?
				if ((MainActivity.this.x | MainActivity.this.o) == Board.TIE)
					MainActivity.this.updateStatus(R.string.tie);
				// Victory?
				else
				{
					switch (MainActivity.this.turn)
					{
						case 0:	// X was to move -> O victory
							MainActivity.this.updateStatus(R.string.o_victory);
							break;
						case 1:	// O was to move -> X victory
							MainActivity.this.updateStatus(R.string.x_victory);
							break;
					}
				}
				// Disable game board from further playing.
				for (int i = 0; i < Board.CELLS; i++)
					MainActivity.this.cells[i].setClickable(false);
			}
			else	// No victory, update turn status
			{
				switch (MainActivity.this.turn)
				{
					case 0:	// X to move
						MainActivity.this.updateStatus(R.string.x_to_move);
						break;
					case 1:	// O to move
						MainActivity.this.updateStatus(R.string.o_to_move);
						break;
				}

				// If playing against a computer opponent
				if (MainActivity.this.players < 2)
				{
					// Set cells to be temporarily unclickable, while the computer
					// makes a move.
					for (int i = 0; i < Board.CELLS; i++)
						MainActivity.this.cells[i].setClickable(false);

					// Pick a computer move at random from available moves.
					child = MainActivity.this.curBoard.pickChild(MainActivity.rng);//, MainActivity.this.x, MainActivity.this.o);

					// If child move not found, set error and disable further gameplay.
					// This should not normally happen.
					if (child == null)
					{
						Toast.makeText(MainActivity.this, "Comp has no children", Toast.LENGTH_SHORT).show();
						for (int i = 0; i < Board.CELLS; i++)
							MainActivity.this.cells[i].setClickable(false);
						MainActivity.this.updateStatus(R.string.error);
						return;
					}

					// Otherwise, update graphic board with computer's chosen move.
					// Find the cell which will receive the mark based on new move
					// and all necessary transformations.
					int diff = MainActivity.this.curBoard.diff(child, MainActivity.this.x, MainActivity.this.o);
					Toast.makeText(MainActivity.this, Integer.toString(diff), Toast.LENGTH_SHORT).show();
					int index;
					for (index = -1; diff > 0; diff >>= 1)
						index++;
					cell = MainActivity.this.cells[index];
					switch (MainActivity.this.turn)
					{
						case 0:	// X to move
							cell.setText(MainActivity.X);
							MainActivity.this.x |= (1 << cell.getIndex());
							MainActivity.this.turn = 1;
							break;
						case 1:	// O to move
							cell.setText(MainActivity.O);
							MainActivity.this.o |= (1 << cell.getIndex());
							MainActivity.this.turn = 0;
							break;
					}
					cell.setMarked(true);

					// Set current board to the new board.
					MainActivity.this.curBoard = child;

					// Check for victory
					if (MainActivity.this.curBoard.isGameOver())
					{
						// Tie?
						if ((MainActivity.this.x | MainActivity.this.o) == Board.TIE)
							MainActivity.this.updateStatus(R.string.tie);
						// Victory?
						else
						{
							switch (MainActivity.this.turn)
							{
								case 0:	// X was to move -> O victory
									MainActivity.this.updateStatus(R.string.o_victory);
									break;
								case 1:	// O was to move -> X victory
									MainActivity.this.updateStatus(R.string.x_victory);
									break;
							}
						}
						// Game board already disabled, no need to do it again.
					}
					else	// No victory, update turn status
					{
						switch (MainActivity.this.turn)
						{
							case 0:	// X to move
								MainActivity.this.updateStatus(R.string.x_to_move);
								break;
							case 1:	// O to move
								MainActivity.this.updateStatus(R.string.o_to_move);
								break;
						}
						// Re-enable game board for further moves by
						// making unmarked cells clickable.
						for (int i = 0; i < Board.CELLS; i++)
						{
							if (! MainActivity.this.cells[i].isMarked())
								MainActivity.this.cells[i].setClickable(true);
						}
					}
				}
			}

//			// Responding computer move
//			Board next = MainActivity.this.curBoard.pickChild(MainActivity.rng, MainActivity.this.x, MainActivity.this.o);
////			Toast.makeText(MainActivity.this, Boolean.toString(next == null), Toast.LENGTH_LONG).show();
//			if (next != null)
//			{
//			}
		}
	}

	private void updateStatus(int resid)
	{
		this.status.setText(resid);
	}

	private void updateStatus(boolean victory)
	{
		int status = 0;

		switch (this.turn)
		{
			case 0:	// X to move
				if (victory)
					status = R.string.o_victory;
				else
					status = R.string.x_to_move;
				break;
			case 1:	// O to move
				if (victory)
					status = R.string.x_victory;
				else
					status = R.string.o_to_move;
				break;
		}
		this.status.setText(status);
	}
}
