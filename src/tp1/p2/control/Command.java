package tp1.p2.control;

import static tp1.p2.view.Messages.error;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import tp1.p2.control.commands.AddPlantCheatCommand;
import tp1.p2.control.commands.AddPlantCommand;
import tp1.p2.control.commands.AddZombieCommand;
import tp1.p2.control.commands.CatchCommand;
import tp1.p2.control.commands.ExitCommand;
import tp1.p2.control.commands.HelpCommand;
import tp1.p2.control.commands.ListPlantsCommand;
import tp1.p2.control.commands.ListZombiesCommand;
import tp1.p2.control.commands.NoneCommand;
import tp1.p2.control.commands.ResetCommand;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.control.commands.ShowRecordCommand;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
/**
 * Represents a user action in the game.
 *
 */
public abstract class Command {

	/* @formatter:off */
	private static final List<Command> AVAILABLE_COMMANDS = Arrays.asList(
			new AddPlantCommand(),
			new ListPlantsCommand(),
			new ResetCommand(),
			new HelpCommand(),
			new ExitCommand(),
			new NoneCommand(),
			new ListZombiesCommand(),
			new AddZombieCommand(),
			new AddPlantCheatCommand(),
			new CatchCommand(),
			new ShowRecordCommand()
		);
	/* @formatter:on */
	
	public Command() {
		this(false);
	}

	public Command(boolean isDefault) {
		if (isDefault) {
			defaultCommand=this;
		}
	}

	private static Command defaultCommand;

	public static Command parse(String[] commandWords) throws GameException { // throws CommandParseException
		if (commandWords.length == 1 && commandWords[0].isEmpty()) {
			return defaultCommand;
		}

		for (Command command : AVAILABLE_COMMANDS) {
			if (command.matchCommand(commandWords[0])) { // find which command it is
				return command.create(commandWords); // each command parses its own parameters
			}
		}
		throw new CommandParseException(Messages.UNKNOWN_COMMAND);
	}

	protected static Iterable<Command> getAvailableCommands() {
		return Collections.unmodifiableList(AVAILABLE_COMMANDS);
	}
	

	public static void newCycle() {
		for(Command c : AVAILABLE_COMMANDS) {
			c.newCycleStarted();
		}
	}

	private boolean isDefaultAction() {
		return Command.defaultCommand == this;
	}

	private boolean matchCommand(String token) {
		String shortcut = getShortcut();
		String name = getName();
		return shortcut.equalsIgnoreCase(token) || name.equalsIgnoreCase(token) || (isDefaultAction() && "".equals(token));
	}

	/**
	 * Execute the command.
	 * 
	 * @param game Where to execute the command.
	 * 
	 * @return {@code true} if game board must be printed {@code false} otherwise.
	 * 	 * 
	 * @throws GameException if there is an error executing the command
	 */
	public abstract boolean execute(GameWorld game) throws GameException; // throws CommandExecuteException

	public Command create(String[] parameters) throws GameException { // throws CommandParseException
		if (parameters.length != 1) {
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
		}
		return this;
	}
	/**
	 * Notifies the {@link Command} that a new cycle has started.
	 */
	protected void newCycleStarted() {}
	
	abstract protected String getName();

	abstract protected String getShortcut();

	abstract public String getDetails();

	abstract public String getHelp();
	
}