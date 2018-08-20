package System;

import System.Autonomous.AutoEngine;
import edu.wpi.first.wpilibj.smartdashboard.*;

public class Dashboard {
	public static void init() {
		SmartDashboard.putNumber("Gyro/angle", 0);
	}
	
	public static void teleop() {
		SmartDashboard.putNumber("Gyro/angle", AutoEngine.getTranslateAngle());
		SmartDashboard.putNumber("Left Dis", 0);
		SmartDashboard.putNumber("Right Dis", 0);
	}
}
