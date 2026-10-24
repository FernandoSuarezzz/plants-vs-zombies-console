package tp1.p2.control.exceptions;


public class RecordException extends GameException{ // error reading/writing the record file
	private static final long serialVersionUID = 1L;

	public RecordException(String ErrorMessage) {
		super(ErrorMessage);
	}
}
