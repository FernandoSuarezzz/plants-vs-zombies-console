package tp1.p2.control.exceptions;

import tp1.p2.view.Messages;

public class NotCatchablePositionException extends InvalidPositionException{ // thrown when there is no sun to catch in a cell
	private static final long serialVersionUID = 1L;

	public NotCatchablePositionException(int col, int row) {
		super(Messages.NO_CATCHABLE_IN_POSITION.formatted(col, row), col, row);
	}

}
