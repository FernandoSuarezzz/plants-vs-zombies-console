package tp1.p2.control.commands;


import tp1.p2.control.Command;
import tp1.p2.control.Level;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class ResetCommand extends Command {

	private Level level;

	private long seed;
	

	public ResetCommand() {
	}

	public ResetCommand(Level level, long seed) {
		this.level = level;
		this.seed = seed;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_RESET_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_RESET_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_RESET_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_RESET_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws GameException {
		if(level==null){
			game.reset();
		}else {
			game.reset(level,seed);
		}
		game.printIni();
		return true; // the board must be printed again
	}

	@Override
	public Command create(String[] parameters) throws GameException {
		if (parameters.length == 1) {
			return new ResetCommand();
		}
		if (parameters.length != 3) {
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
		Level l = Level.valueOfIgnoreCase(parameters[1]);
		if (l == null) {
			throw new CommandParseException(Messages.ALLOWED_LEVELS);
		}
		try {
			return new ResetCommand(l, Long.parseLong(parameters[2]));
		} catch (NumberFormatException nfe) {
			throw new CommandParseException(Messages.SEED_NOT_A_NUMBER_ERROR.formatted(parameters[2]), nfe);
		}
	}
}
