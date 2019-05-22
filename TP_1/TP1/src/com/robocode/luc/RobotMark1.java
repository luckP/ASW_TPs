package com.robocode.luc;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

/**
* <h1>Robocode!</h1>
* <p></p>
* @author  Lucas Parada, Marcelo Silva
* @version 1.0
* @since   2019-03-07 
*/

public class RobotMark1 extends AdvancedRobot{
	
	private double energy;			//The energy the robot has
	private double countMiss = 0;	//Number of bullets missed in a row by the robot
	
	
	@Override
	public void run() {
		custoLayout();
		
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		
		while(true) {
			turnRadarRightRadians(Double.POSITIVE_INFINITY);
		}
	}
	
	/**
	 * 	onScannedRobot: Lock radar and gun in the enemy, move ahead and turn to the left if enemy's energy has changed
	 */
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		lockRadarOnTarget(e);
		custoFire(e);
		
		if(energy != e.getEnergy()) {
			energy = e.getEnergy();
			setTurnLeftRadians(-e.getHeadingRadians());
		}
		
		setAhead(Double.POSITIVE_INFINITY);
	}
	
	/**
	 * 	onHitByBullet: The robot turn to the right a random value between -PI/2 and PI/2 
	 */
	
	@Override
	public void onHitByBullet(HitByBulletEvent e) {
		double radians = ((Math.random() * Math.PI) - Math.PI/2);
		
		setTurnRightRadians(radians);
	}
	
	/**
	 * 	onHitWall: The robot turn to the right PI/4 when hit the wall
	 */
	
	@Override
	public void onHitWall(HitWallEvent e){
		double radians = (Math.PI + Math.random()*(Math.PI/4));

		setTurnRightRadians(radians);
	}
	
	/**
	 * 	onHitRobot: The robot turn to the right PI/2 when hit the enemy
	 */
	
	@Override
	public void onHitRobot(HitRobotEvent e) {
		setTurnRightRadians(Math.PI/2);
	}
	
	/**
	 * 	onBulletMissed: Used to count how many times the robot missed in a row.
	 */
	
	@Override
	public void onBulletMissed(BulletMissedEvent e){
		countMiss++;
	}
	
	/**
	 * 	onBulletHit: Reset the amount of bullets missed in a row to zero.
	 */
	
	@Override
	public void onBulletHit(BulletHitEvent e) {
		countMiss=0;
	}
	
	/**
	 * 	custoLayout: Set the color of the robot. Set the color of the bullets that the robot uses.
	 *  @return void
	 */
	
	public void custoLayout() {
		setColors(Color.BLACK, Color.RED, Color.GRAY);
		setBulletColor(Color.RED);
	}
	
	/**
	 * 	lockRadarOnTarget: Lock the radar and gun on the enemy robot.
	 * @param ScannedRobotEvent
	 * @return void
	 */
	
	public void lockRadarOnTarget(ScannedRobotEvent e) {
		double radarTurn = getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians();
		double gunTurn = getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians();
		
		setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
		setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn));
	}
	
	/**
	 * 	custoFire: Select the appropriate power according to the distance to the target
	 *  @param ScannedRobotEvent
	 *  @return void
	 */
	
	public void custoFire(ScannedRobotEvent e) {
		
		double dist = e.getDistance();
		
		if(dist>400 || countMiss>10) 
			fire(1);
		else if(dist>200)
			fire(2);
		else
			fire(3);
		
	}

}