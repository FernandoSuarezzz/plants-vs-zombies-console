package tp1.p2.control.exceptions;

public class CommandParseException extends GameException{
	private static final long serialVersionUID = 1L;

	public CommandParseException(String unknownCommand) { // thrown when a command cannot be parsed
		super(unknownCommand);
	}
	public CommandParseException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
