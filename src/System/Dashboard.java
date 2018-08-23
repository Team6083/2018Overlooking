package System;

import System.Autonomous.AutoEngine;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class Dashboard {
	
	public static void init() {
		SmartDashboard.putNumber("Gyro/angle", 0);
		SmartDashboard.putNumber("Left Dis", AutoEngine.getLefttEncVal());
		SmartDashboard.putNumber("Right Dis", AutoEngine.getRightEncVal());
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				loop();
			}
		}).start();
		
	}
	
	public static void partReady(String name) {
		SmartDashboard.putNumber(name + "/status", 0);
	}
	
	public static void partWarning(String name) {
		SmartDashboard.putNumber(name + "/status", 1);
	}
	
	public static void partError(String name) {
		SmartDashboard.putNumber(name + "/status", 2);
	}
	
	public static void loop() {
		DriverStation ds = DriverStation.getInstance();
		 
		
		SmartDashboard.putNumber("Gyro/angle", AutoEngine.getAngle());
		SmartDashboard.putNumber("Left Dis", AutoEngine.getLefttEncVal());
		SmartDashboard.putNumber("Right Dis", AutoEngine.getRightEncVal());
		SmartDashboard.putBoolean("ds/isFMSAtt", ds.isFMSAttached());
		SmartDashboard.putNumber("ds/matchTime", ds.getMatchTime());
		
		int mode;
		if(ds.isAutonomous()) {
			mode = 1;
		}
		else if(ds.isOperatorControl()) {
			mode = 2;
		}
		else if(ds.isTest()) {
			mode = 3;
		}
		else if(ds.isDisabled()) {
			mode = 0;
		}
		else {
			mode = -1;
		}
		
		SmartDashboard.putNumber("ds/mode", mode);
		
	}
}
