package com.turpgames.ichigu.model.hiscores;

import java.util.List;

import com.turpgames.framework.v0.IDrawable;
import com.turpgames.framework.v0.impl.Text;
import com.turpgames.framework.v0.util.Vector;

public class Table implements IDrawable {
	private Column[] columns;
	private List<Row> rows;
	private float rowHeight;
	private float width;
	private Vector location;

	@Override
	public void draw() {
		for (Row row : rows)
			row.draw();
	}

	public static class Column {
		private Text header;
		private float width;
		private int index;
	}

	public static class Row implements IDrawable {
		private Cell[] cells;
		private int index;

		public Cell getCell(int index) {
			return cells[index];
		}

		@Override
		public void draw() {
			for (int i = 0; i < cells.length; i++)
				cells[i].draw();
		}
	}

	public static class Cell implements IDrawable {
		private IDrawable cellContent;
		private Column column;
		private Row row;

		@Override
		public void draw() {
			cellContent.draw();
		}
	}
}