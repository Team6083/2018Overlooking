package System;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBase {

	private static final int Lmotor1_Port = 2;
	private static final int Lmotor2_Port = 3;
	private static final int Rmotor1_Port = 0;
	private static final int Rmotor2_Port = 1;

	private static VictorSP Lmotor1, Lmotor2;
	private static VictorSP Rmotor1, Rmotor2;

	private static double speedDown;
	private static boolean reverseDrive = false;
	private static boolean lastButton = false;
	
	public static void init() {
		Lmotor1 = new VictorSP(Lmotor1_Port);
		Lmotor2 = new VictorSP(Lmotor2_Port);
		Rmotor1 = new VictorSP(Rmotor1_Port);
		Rmotor2 = new VictorSP(Rmotor2_Port);
		speedDown = 2.0;
		SmartDashboard.putBoolean("drive/reverse", false);
		SmartDashboard.putNumber("drive/leftSpeed", 0);
		SmartDashboard.putNumber("drive/rightSpeed", 0);
		Dashboard.putReady("drive");
	}
	
	public static void arcadeDrive() {
		if(Joysticks.b && (Joysticks.b!=lastButton)) {
			reverseDrive = !reverseDrive;
		}
		
		lastButton = Joysticks.b;
		
		double left = Joysticks.xa - Joysticks.ya;
		double right = Joysticks.xa + Joysticks.ya;
		left = left / speedDown;
		right = right / speedDown;
		
		Lmotor1.set(left);
		Lmotor2.set(left);
		Rmotor1.set(right);
		Rmotor2.set(right);
	}

	public static void tankDrive() {
		if(Joysticks.b && (Joysticks.b!=lastButton)) {
			reverseDrive = !reverseDrive;
		}
		
		lastButton = Joysticks.b;
		
		double left = -Joysticks.ly / speedDown;
		double right = Joysticks.ry / speedDown;
		
		if(reverseDrive) {
			double t = left;
			left = right;
			right = t;
			SmartDashboard.putBoolean("drive/reverse", true);
		}
		else {
			SmartDashboard.putBoolean("drive/reverse", false);
		}
		
		reverseDrive = SmartDashboard.getBoolean("drive/reverse", reverseDrive);
		
		
		if (Joysticks.lab) {
			left = left * 2;
		}
		if (Joysticks.rab) {
			right = right * 2;
		}

		Lmotor1.set(left);
		Lmotor2.set(left);
		Rmotor1.set(right);
		Rmotor2.set(right);
		SmartDashboard.putNumber("drive/leftSpeed", left);
		SmartDashboard.putNumber("drive/rightSpeed", right);
	}

	public static void directControl(double left, double right) {
		Lmotor1.set(left);
		Lmotor2.set(left);
		Rmotor1.set(right);
		Rmotor2.set(right);
		SmartDashboard.putNumber("drive/leftSpeed", left);
		SmartDashboard.putNumber("drive/rightSpeed", right);
	}

	public static double getLeftPower() {
		return Lmotor1.get();
	}

	public static double getRightPower() {
		return Rmotor1.get();
	}
	
	

}
