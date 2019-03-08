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

* <p>
* <b>Note:</b> Giving proper comments in your program makes it more
* user friendly and it is assumed as a high quality code.</p>
*
* @author  Lucas Parada Marcelo
* @version 1.0
* @since   2019-03-07 
*/

public class RobotMark1 extends AdvancedRobot{
	
	private double energy;
	private double countMiss = 0;
	
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
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		lockRadarOnTarget(e);
		custoFire(e);
		
		if(energy != e.getEnergy()) {
			energy = e.getEnergy();
			setTurnLeftRadians(-e.getHeadingRadians());
		}
		
//		double turn = getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians();
//		setTurnRightRadians(turn);
		
		setAhead(Double.POSITIVE_INFINITY);
	}
	

	@Override
	public void onHitByBullet(HitByBulletEvent e) {
		double radians = (double)((Math.random() * Math.PI) - Math.PI/2);
		
		setTurnRightRadians(radians);
	}
	
	@Override
	public void onHitWall(HitWallEvent e){
		double radians = (double)(Math.PI + Math.random()*(Math.PI/4));

		setTurnRightRadians(radians);
	}
	
	@Override
	public void onHitRobot(HitRobotEvent e) {
		setTurnRightRadians(Math.PI/2);
	}
	
	@Override
	public void onBulletMissed(BulletMissedEvent e){
		countMiss++;
	}
	
	@Override
	public void onBulletHit(BulletHitEvent e) {
		countMiss=0;
	}
	
	
	public void custoLayout() {
		setColors(Color.BLACK, Color.RED, Color.GRAY);
		setBulletColor(Color.RED);
	}
	
	public void lockRadarOnTarget(ScannedRobotEvent e) {
		double radarTurn = getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians();
		double gunTurn = getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians();
		
		setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
		setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn));
	}
	
	public void custoFire(ScannedRobotEvent e) {
		
		double dist = e.getDistance();
		
//		fire(300/dist);
		
		if(dist>500)
			fire(0);
		else if(dist>400 || countMiss>10) 
			fire(1);
		else if(dist>200)
			fire(2);
		else
			fire(3);
		
	}

}
