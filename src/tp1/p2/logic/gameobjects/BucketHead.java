package tp1.p2.logic.gameobjects;

import tp1.p2.view.Messages;

public class BucketHead extends Zombie {
	
	private static final int BUCKETHEAD_ENDURANCE = 8;
	private static final int BUCKETHEAD_SPEED = 4;

	public BucketHead() {
		super();
	}
		
	@Override
	int getEndurance() {
		return BUCKETHEAD_ENDURANCE;
	}
	
	@Override
	String getObjectSymbol() {
		return Messages.BUCKET_HEAD_ZOMBIE_SYMBOL;
	}
	
	@Override
	int getZombieSpeed() {
		return BUCKETHEAD_SPEED;
	}

	@Override
	String getObjectName() {
		return Messages.BUCKET_HEAD_ZOMBIE_NAME;
	}

	
}
