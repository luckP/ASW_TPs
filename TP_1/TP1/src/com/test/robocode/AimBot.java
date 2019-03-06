package com.test.robocode;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class AimBot extends Robot 
{
	private boolean onAim = false;

	public void run()
	{
		while(true)
		{
			controlGun();
		}
	}
	
	public void onScannedRobot(ScannedRobotEvent e)
	{
		fire(1);
		onAim = true;
		stop();
//		scan();
	}
	
	public void controlGun()
	{
		if(onAim)
		{
			fire(1);
			onAim = false;
			scan();
			return;
		}
		turnGunRight(45);
//		scan();
//		onAim = false;
	}
}