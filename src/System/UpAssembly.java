package System;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class UpAssembly {
	private static TalonSRX UPmotor;
	private static Encoder UpEnc;
	private static double gain;
	
	private static final int enc_ChA = 6;
	private static final int enc_ChB = 7;
	
	private static final int[] set_steps = {0,-1500, -3000};
	private static int steps_index;
	
	private static int targetStep;
	
	public static void init() {
		UPmotor = new TalonSRX(3);
		UpEnc = new Encoder(enc_ChA,enc_ChB);
		UpEnc.reset();
		gain = 0.01;
		targetStep = 0;
		steps_index = 0;
	}
	
	public static void teleop() {
		if (Joysticks.lt > 0.1) {
			UPmotor.set(ControlMode.PercentOutput, Joysticks.lt / 2);
			targetStep = UpEnc.get();
		} else if (Joysticks.rt > 0.1) {
			UPmotor.set(ControlMode.PercentOutput, -Joysticks.rt);
			targetStep = UpEnc.get();
		} else {
			UPmotor.set(ControlMode.PercentOutput, calculateSpeed(targetStep));
		}
		
		if(Joysticks.back && steps_index > 0) {
			steps_index--;
			targetStep = set_steps[steps_index];
		}
		else if(Joysticks.start && steps_index < set_steps.length - 1) {
			steps_index++;
			targetStep = set_steps[steps_index];
		}
		
		SmartDashboard.putNumber("UP/motor", UPmotor.getMotorOutputPercent());
	}
	
	public static double calculateSpeed(int target) {
		double speed = 0;
		
		speed = (UpEnc.get() - target) * gain;
		
		if(Math.abs(speed)>0.5) {
			speed = (speed>0)?0.5:-0.5;
		}
		
		return speed;
	}
	
	public static void up() {
		UPmotor.set(ControlMode.PercentOutput, -0.4);
	}
	
	public static void stop() {
		UPmotor.set(ControlMode.PercentOutput, 0);
	}
	
	public static void down() {
		UPmotor.set(ControlMode.PercentOutput, 0.3);
	}
	
	public static int getStep() {
		return UpEnc.get();
	}
	
}
