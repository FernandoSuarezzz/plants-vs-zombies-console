package tp1.p2.control.commands;


import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.NotCatchablePositionException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class CatchCommand extends Command {

	private static boolean caughtSunThisCycle;

	private int col;

	private int row;

	
	public CatchCommand() {
		
	}
	
	@Override
	protected void newCycleStarted() {
		caughtSunThisCycle = false;
	}

	private CatchCommand(int col, int row) {
		this.col = col;
		this.row = row;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_CATCH_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_CATCH_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_CATCH_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_CATCH_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws GameException {
		if(!caughtSunThisCycle) {
			game.tryToCatchObject(col, row); // throws if there is no sun in that cell
			caughtSunThisCycle=true; // only one sun can be caught per cycle
		}else {
			throw new CommandExecuteException(Messages.SUN_ALREADY_CAUGHT);
		}	
		return true;
	}

	@Override
	public Command create(String[] parameters) throws GameException {
		Command c = new CatchCommand();
		if(parameters.length!=3)throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		else {
			try {
				c=new CatchCommand(Integer.parseInt(parameters[1]),Integer.parseInt(parameters[2]));
			}catch(NumberFormatException nfe) {
				throw new CommandParseException(Messages.INVALID_POSITION.formatted(parameters[1],parameters[2]), nfe);
			}
			return c;
		}	
	}

}