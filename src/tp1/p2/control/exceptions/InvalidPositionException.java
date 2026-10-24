package tp1.p2.control.exceptions;

import tp1.p2.view.Messages;

public class InvalidPositionException extends GameException{ // thrown when adding a plant/zombie in an invalid cell
	private static final long serialVersionUID = 1L;

	private int col;
	private int row;
	public InvalidPositionException(int col, int row) {
		super(Messages.INVALID_POSITION.formatted(col,row));
		this.col=col;
		this.row=row;
	}

	protected InvalidPositionException(String message, int col, int row) {
		super(message);
		this.col=col;
		this.row=row;
	}

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

}
