package com.savanto.tictactoebot;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

public class CellView extends TextView
{
	private int index;
	private boolean marked;

	public CellView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		// Get StyleAttributes properly.
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CellView);

		// Get cell mask
		this.index = ta.getInt(R.styleable.CellView_index, 0);

		// Destroy the TypedArray
		ta.recycle();
	}

	public int getIndex()
	{
		return this.index;
	}

	public boolean isMarked()
	{
		return this.marked;
	}

	public void setMarked(boolean mark)
	{
		this.marked = mark;
	}
}
