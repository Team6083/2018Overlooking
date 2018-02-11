package System;

import org.usfirst.frc.team6083.robot.Robot;

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
		speedDown = 3.0;
	}

	public static void tankDrive() {
		if(Joysticks.rab && !lastButton) {
			reverseDrive = !reverseDrive;
			lastButton = true;
		}
		else {
			lastButton = false;
		}
		
		double left = -Joysticks.ly / speedDown;
		double right = Joysticks.ry / speedDown;
		
		if(reverseDrive) {
			double t = left;
			left = right;
			right = t;
			SmartDashboard.putBoolean("reverseDrive", true);
		}
		else {
			SmartDashboard.putBoolean("reverseDrive", false);
		}
		
		
		if (Joysticks.lb) {
			left = left * 2;
		}
		if (Joysticks.rb) {
			right = right * 2;
		}

		Lmotor1.set(left);
		Lmotor2.set(left);
		Rmotor1.set(right);
		Rmotor2.set(right);
	}

	public static void directControl(double left, double right) {
		Lmotor1.set(left);
		Lmotor2.set(left);
		Rmotor1.set(right);
		Rmotor2.set(right);
	}

	public static double getLeftPower() {
		return Lmotor1.get();
	}

	public static double getRightPower() {
		return Rmotor1.get();
	}
	
	

}
