package com.robocode.luc;
import robocode.*;
import robocode.util.Utils;

public class FirstBot extends AdvancedRobot{

    public void run() {
    	
        while (true) {
//        	System.out.println("getGunHeadingRadians: " + getGunHeadingRadians());
//            ahead(100);
//            turnLeft(90);
//            
//            turnRadarLeft(30);
//            turnGunLeft(30);
//            turnRadarRight(60);
//            turnGunRight(60);
//            turnRadarLeft(30);
//            turnGunLeft(60);
//            
//            execute();
//            System.out.println(Utils.normalRelativeAngle(1));
//            turnGunLeft(90);
            
//            ahead(100);
//            turnLeft(90);
//            turnGunLeft(360);
//            
//            ahead(100);
//            turnLeft(90);
//            turnGunLeft(360);
//            
//            ahead(100);
//            turnLeft(90);
//            turnGunLeft(360);
            
//            back(100);
//            turnGunRight(180);
        	
//          turnGunRight(10);
//          fire(1);
        	
        	setTurnRadarLeft(Double.MAX_VALUE);
        }
    }
 
    public void onScannedRobot(ScannedRobotEvent e) {
//    	System.out.println("getGunHeadingDegrees: " + getGunHeadingDegrees());
//    	System.out.println("getBearing: " + e.getBearing());
//    	turnGunRight(180);
//    	setTurnGunLeft(getGunHeadingDegrees() - e.getRobotHeadingDegrees());
    	fire(1);
    	
    }
    
    
    public void onHitByBullet(HitByBulletEvent e){
    	
    }
    public void onHitRobot(HitRobotEvent e){
    	
    }
    public void onHitWall(HitWallEvent e){
    	
    }
    public void onBulletHit(BulletHitEvent e){
    	
    }
    public void onBulletHitBullet(BulletHitBulletEvent e){
    	
    }
    
  
}