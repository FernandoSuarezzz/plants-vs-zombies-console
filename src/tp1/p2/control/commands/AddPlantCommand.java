package tp1.p2.control.commands;



import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.control.exceptions.NotEnoughCoinsException;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.logic.gameobjects.Plant;
import tp1.p2.logic.gameobjects.PlantFactory;

public class AddPlantCommand extends Command implements Cloneable {

	private int col;

	private int row;

	private String plantName;

	private boolean consumeCoins;
	

	public AddPlantCommand() {
		this(true);
	}

	public AddPlantCommand(boolean consumeCoins) {
		this.consumeCoins = consumeCoins;
	}

	private AddPlantCommand(boolean consumeCoins, String plantName, int col, int row) {
		this.consumeCoins = consumeCoins;
		this.plantName = plantName;
		this.col = col;
		this.row = row;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_ADD_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_ADD_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_ADD_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_ADD_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws GameException {
		game.checkValidPlantPosition(col, row); // the cell must be inside the board and not occupied
		Plant plant= PlantFactory.spawnPlant(this.plantName, game, this.col, this.row); // clones the plant prototype, or throws if the name is invalid
		if (consumeCoins) { // cheatPlant does not need (nor spend) suncoins
			game.tryToBuy(plant.getObjectCost()); // throws an exception if there are not enough suncoins
			game.substractCoins(plant.getObjectCost());
		}
		game.addItem(plant);
		game.update();
		return true;
	}
		
	@Override
	public Command create(String[] parameters) throws GameException {
		if(parameters.length != 4)throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		else {
			try {
				return new AddPlantCommand(consumeCoins, parameters[1], Integer.parseInt(parameters[2]), Integer.parseInt(parameters[3]));
			}catch(NumberFormatException nfe) {
				throw new CommandParseException(Messages.INVALID_POSITION.formatted(parameters[2],parameters[3]), nfe);
			}
		}
	}

}
