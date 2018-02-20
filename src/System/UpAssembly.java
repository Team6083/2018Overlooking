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
	
	private static final int[] set_steps = {0, -3000, -5800};
	private static int steps_index;
	
	private static int targetStep;
	
	public static void init() {
		UPmotor = new TalonSRX(3);
		UpEnc = new Encoder(enc_ChA,enc_ChB);
		UpEnc.reset();
		gain = 0.005;
		targetStep = 0;
		steps_index = 0;
	}
	
	public static void teleop() {
		if (Joysticks.probutton[0]) {
		UPmotor.set(ControlMode.PercentOutput, Joysticks.lt * 0.75);
			targetStep = UpEnc.get();
		} else if (Joysticks.probutton[1]) {
			UPmotor.set(ControlMode.PercentOutput, -Joysticks.rt);
			targetStep = UpEnc.get();
		} else {
			UPmotor.set(ControlMode.PercentOutput, calculateSpeed(targetStep));
		}
		
		if(Joysticks.probutton[6] && Joysticks.getRealeased(6) && steps_index > 0) {
			steps_index--;
			targetStep = set_steps[steps_index];
		}
		else if(Joysticks.probutton[7] && Joysticks.getRealeased(7) && steps_index < set_steps.length - 1) {
			steps_index++;
			targetStep = set_steps[steps_index];
		}
		dashboard();
	}
	
	public static void autoLoop() {
		UPmotor.set(ControlMode.PercentOutput, calculateSpeed(targetStep));
		
		dashboard();
	}
	
	public static void dashboard() {
		SmartDashboard.putNumber("Up/Enc", UpEnc.get());
		SmartDashboard.putNumber("Up/targetStep", targetStep);
		SmartDashboard.putNumber("Up/motorOutPut", UPmotor.getMotorOutputPercent());
	}
	
	private static double calculateSpeed(int target) {
		double speed = 0;
		
		speed = (target - UpEnc.get()) * gain;
		
		if(Math.abs(speed)>0.5) {
			speed = (speed>0)?0.7:-0.7;
		}
		
		return speed;
	}
	
	public static void setTarget(int step) {
		targetStep = step;
	}
	
	public static void moveStep(int move) {
		int index = steps_index + move;
		
		if(index >= 0 && index < set_steps.length) {
			steps_index = index;
			setTarget(set_steps[steps_index]);
		}
	}
	
	public static boolean isReachTarget() {
		return (Math.abs(UpEnc.get() - targetStep) < 50)?true:false;
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
