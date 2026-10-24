package tp1.p2.control.exceptions;

public class CommandExecuteException extends GameException{ // thrown while executing a command
	private static final long serialVersionUID = 1L;

	
	public CommandExecuteException(String Message) {
	  super(Message);
	}
	
	public CommandExecuteException(String message, Throwable cause) {
		super(message, cause);
	}
}
