package com.test.robocode;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Infected extends Robot
{
	
	public void run() {
		
		
         setBodyColor(Color.black);
         setGunColor(Color.red);
         setRadarColor(Color.red);
         setAdjustGunForRobotTurn(false);
         setAdjustRadarForRobotTurn(false);
       

		
		while(true) {
			// La idea de este robot , es que el enemigo gaste energia mientras 
			// nos  movemos por el escenario, y asi evitamos que nos reste energia
			// mientras que la de el disminuye, y cada cierto tiempo, es bueno atacar.
			// Solo es cuestion de tiempo antes de que el enemigo sea derrotado :)
			ahead(500);
			turnLeft(90);
			turnGunLeft(12);
			ahead(500);
			turnLeft(90);
			turnGunLeft(12);
			ahead(500);
			turnLeft(90);
			turnGunLeft(12);
			ahead(500);
			turnLeft(90);
			turnGunRight(360);
			turnGunLeft(360);
			
					}
	}

	
	public void onScannedRobot(ScannedRobotEvent e) {
		
		fire(3);
		if (e.getEnergy() <15) {
			// si la energia(vida) del robot escaneado es menor a 15 , se dispara un proyectil con daño 9
			fire(9);
	}

	}
		
	
    
	
	public void onHitByBullet(HitByBulletEvent e) {
		// Yo evito poner còdigo aca porque no quiero que interrumpa el recorrido de mi robot
		  
	}

   
     
     public void onHitRobot(HitRobotEvent e){
	if (e.getBearing() >-90 && e.getBearing() <90)
	  // si cuando choca un robot este se encuentra al frente dispara un proyectil con daño de 5
	  fire(5);
	if (e.getEnergy() < 15) {
		turnGunRight(360);
		fire (10);
}
	  

}
    
	
	
	public void onHitWall(HitWallEvent e) {
		
							turnLeft(180);
		
	}	
 
}