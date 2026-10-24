package tp1.p2.control.exceptions;

import tp1.p2.view.Messages;

public class NotEnoughCoinsException extends GameException{ // thrown when there are not enough suncoins to buy a plant
	private static final long serialVersionUID = 1L;

	public NotEnoughCoinsException() {
		super(Messages.NOT_ENOUGH_COINS);
	}
	
}
