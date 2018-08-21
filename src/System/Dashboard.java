package System;

import System.Autonomous.AutoEngine;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class Dashboard {
	public static void init() {
		SmartDashboard.putNumber("Gyro/angle", 0);
		SmartDashboard.putNumber("Left Dis", AutoEngine.getLefttEncVal());
		SmartDashboard.putNumber("Right Dis", AutoEngine.getRightEncVal());
	}
	
	public static void teleop() {
		SmartDashboard.putNumber("Gyro/angle", AutoEngine.getAngle());
		SmartDashboard.putNumber("Left Dis", AutoEngine.getLefttEncVal());
		SmartDashboard.putNumber("Right Dis", AutoEngine.getRightEncVal());
	}
}
