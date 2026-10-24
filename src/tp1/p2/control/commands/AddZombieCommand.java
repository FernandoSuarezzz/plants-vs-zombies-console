package tp1.p2.control.commands;

import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.logic.gameobjects.Zombie;
import tp1.p2.logic.gameobjects.ZombieFactory;

public class AddZombieCommand extends Command {

	private int zombieIdx;

	private int col;

	private int row;

	public AddZombieCommand() {

	}

	private AddZombieCommand(int zombieIdx, int col, int row) {
		this.zombieIdx = zombieIdx;
		this.col = col;
		this.row = row;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_ADD_ZOMBIE_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_ADD_ZOMBIE_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_ADD_ZOMBIE_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_ADD_ZOMBIE_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws GameException{
		game.checkValidZombiePosition(col, row); // the cell must be free (suns do not block it)
		Zombie z= ZombieFactory.spawnZombie(this.zombieIdx, game, this.col, this.row);	// clones the zombie prototype
		game.addItem(z);
		game.update();
		return true;
	}

	@Override
	public Command create(String[] parameters) throws GameException{
		if(parameters.length!=4)throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		else {
			try {
				return new AddZombieCommand(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]));
			}catch(NumberFormatException nfe) {
				throw new CommandParseException(Messages.INVALID_COMMAND, nfe);
			}
		}
	}

}