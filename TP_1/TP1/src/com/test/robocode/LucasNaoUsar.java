package com.test.robocode;

import robocode.util.Utils;
import java.util.Random;
import robocode.*;

public class LucasNaoUsar extends AdvancedRobot{

	private Random rand = new Random();
	private int dodgeRange = 200;
	
	/**
	 * run: OakBot's default behavior
	 */
	public void run() {
		
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForRobotTurn(true);
		
		do {
	        turnRadarRightRadians(Double.POSITIVE_INFINITY);
	    } while (true);
		
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {

		double radarTurn = getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians();
		double gunTurn = getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians();
		double robotTurn = e.getBearingRadians() + Math.PI/2;

		
		setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
		setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn));
		setTurnRightRadians(Utils.normalRelativeAngle(robotTurn));
		
		
		//Move, randomly, along the circumference defined by
		//the position of the robots on the field
		if(getDistanceRemaining() == 0) {
			setAhead(rand.nextInt(2 * dodgeRange) - dodgeRange);
		}
		
		customFire(e.getDistance());
		
	}
	
	
	/**
	 * customFire: Fire a bullet considering the distance to the target
	 * @param enemyDistance Enemy distance
	 */
	public void customFire(double enemyDistance) {
		if(enemyDistance > 150)
			fire(1);
		else if(enemyDistance > 50)
			fire(2);
		else {
			fire(3);
		}
	}
}