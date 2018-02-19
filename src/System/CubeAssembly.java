package System;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CubeAssembly {
	
	private static final int motor1_Port = 4;
	private static final int power1_Port = 2;
	
	private static final int motor2_Port = 5;
	private static final int power2_Port = 13;
	
	public static double speed = 0.5;
	public static double currentLimit = 17;
	
	private static VictorSP motor1, motor2;
	private static RobotPower power1, power2;
	
	private static Timer collectTimer = new Timer();
	private static double collectTime = 5;
	
	private static boolean collect = false;
	
	public static void init() {
		motor1 = new VictorSP(motor1_Port);
		motor2 = new VictorSP(motor2_Port);
		power1 = new RobotPower(power1_Port);
		power2 = new RobotPower(power2_Port);
	}
	
	public static void teleop() {
		if(Joysticks.lab) {
			//Put Cube
			motor1.set((power1.getPortCurrent()>currentLimit)?0:0.4);
			motor2.set((power2.getPortCurrent()>currentLimit)?0:-0.4);
		}
		else if(Joysticks.x) {
			motor1.set((power1.getPortCurrent()>currentLimit)?0:-speed);
			motor2.set((power2.getPortCurrent()>currentLimit)?0:speed);
		}
		else if(Joysticks.y && !collect) {
			collect = true;
			collectTimer.start();
		}
		else if(collect) {
			//Collect Cube
			motor1.set((power1.getPortCurrent()>currentLimit)?0:-speed);
			motor2.set((power2.getPortCurrent()>currentLimit)?0:speed);
			SmartDashboard.putNumber("lightStatus", 1);
		}
		else {
			motor1.set(0);
			motor2.set(0);
		}
		
		if(collectTimer.get() > collectTime || power1.getPortCurrent()>currentLimit || power2.getPortCurrent()> currentLimit || Joysticks.x) {
			collectTimer.stop();
			collectTimer.reset();
			collect = false;
		}
		
		SmartDashboard.putNumber("Cube/current1", power1.getPortCurrent());
		SmartDashboard.putNumber("Cube/current2", power2.getPortCurrent());
	}
	
	public static void open() {
		motor1.set((power1.getPortCurrent()>currentLimit)?0:0.6);
		motor2.set((power2.getPortCurrent()>currentLimit)?0:-0.6);
	}
	
	public static void stop() {
		motor1.set(0);
		motor2.set(0);
	}
}
