package com.robocode.luc;

import java.awt.Color;
import static java.lang.Math.PI;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.util.Utils;

public class LucBot extends AdvancedRobot{
	
	double dist = Double.POSITIVE_INFINITY;
	double angle = 90;
	
	@Override
	public void run() {
		setColors(Color.BLACK, Color.RED, Color.GRAY);
		setBulletColor(Color.RED);
////		turn to angle 0
		turnRight( (getHeading()<180) ?  -getHeading() : 360 - getHeading() );
//		turnGunRight(90);
//		
		setAdjustRadarForGunTurn(true);
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForRobotTurn(true);
//		setAhead(dist);
		
				
		while (true) {
			turnRadarRight(Double.MAX_VALUE);
			
////			turnRadarLeft(10);
////			turnRadarLeft(Double.MAX_VALUE);
////			System.out.println(getHeadingRadians());
//			scan();
//			
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent e) {
		
		double radarTurn = getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians();
		double gunTurn = getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians();
//		double roboTurn = e.getBearingRadians();

		double bulletPower = calcBulletPowerPower( e.getDistance(), getEnergy() );
//		double ainBotVal = aimBot(e.getHeadingRadians(), e.getDistance(), 20-(3*bulletPower), e.getVelocity(), gunTurn);
		
		aimBot(e.getHeadingRadians(), e.getDistance(), 20-(3*bulletPower), e.getVelocity(), gunTurn);
		setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
//		setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn));	
//		setTurnRight(Utils.normalRelativeAngle(roboTurn));
		
//		setAhead(dist);
		
		
		System.out.println(Math.toDegrees( e.getHeadingRadians() ));
		System.out.println(Math.toDegrees( e.getVelocity() ));
//		fire(bulletPower);
		
//		System.out.println(180+e.getBearing());
//		System.out.println(e.getHeading());
		
	}
	
	public void aimBot(double alfa, double dist, double vp, double vt, double gunTurn) {
		
		double s  = -vt/Math.abs(vt);
//		double s2 = -alfa/Math.abs(alfa);
		
		vt = Math.sqrt(vt*vt);
		
		double a = vp*vp - vt*vt,
			   b = 2*dist*vt*Math.cos(alfa),
			   c = -Math.pow(dist, 2);
		
		double time = (-b+Math.sqrt(b*b -4*a*c)) / (2*a);
		
		double dp = time*vp,
			   dt = time*vt;
		
		double angle = s*Math.acos( (dp*dp + dist*dist - dt*dt)/(2*dp*dist) );
		System.out.println(s);
		setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn + angle));	
		fire(1);
	}
	
	
	
	@Override
	public void onHitByBullet(HitByBulletEvent e) {
		setBack(500);
//		setTurnLeft(90);
//		setTurnRight((getHeading()<180) ?  -getHeading() : 360 - getHeading());
//		double radarTurn = getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians();
//		double gunTurn = getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians();
//		
//		setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
//		setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn));
		
	}
	
	@Override
	public void onHitRobot(HitRobotEvent e) {
		
//		turnRight(90);
//		ahead(100);
//		turnLeft(90);
//		ahead(100);
//		turnLeft(90);
//		ahead(100);
//		turnRight(90);
//		ahead(100);
	
//		back(100);
		dist*=-1;
		angle*=-1;
//		double radarTurn = getHeadingRadians() + e.getBearingRadians() - getRadarHeadingRadians();
//		double gunTurn = getHeadingRadians() + e.getBearingRadians() - getGunHeadingRadians();
//		
//		setTurnRadarRightRadians(Utils.normalRelativeAngle(radarTurn));
//		setTurnGunRightRadians(Utils.normalRelativeAngle(gunTurn));

//		turnGunRight(turnGunAmt);
		
		
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		// TODO Auto-generated method stub
//		super.onHitWall(event);
//		setBack(30);
		ahead(dist);
		turnRight(angle);
//		setTurnRadarLeft(angle);
//		setTurnGunLeft(angle);
	}
	
	
//	@Override
//	public void onBulletHitBullet(BulletHitBulletEvent e) {
//		
//	}
	
	private double calcBulletPowerPower(double distance, double energy) {
		
		return 1;	
//		if(distance>500 || energy<15) 
//			return 1;
//		else if(distance<500 && distance>200)
//			return 2;
//		return 3;
	}
	
	
}
