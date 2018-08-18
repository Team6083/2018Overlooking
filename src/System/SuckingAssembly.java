package System;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SuckingAssembly {

	private static final int motor1_Port = 4;
	private static final int power1_Port = 2;

	private static final int motor2_Port = 5;
	private static final int power2_Port = 13;

	public static double speed = 0.5;
	public static double stopCurrent = 17;// auto stop the claw when the current run over

	private static VictorSP motor1, motor2;
	private static RobotPower power1, power2;

	private static Timer collectTimer = new Timer();
	private static Timer putTimer = new Timer();
	private static double collectTime = 5;
	private static double puttime = 1;

	private static boolean collect = false;
	private static boolean put_start = false, put_finish = false;

	public static void init() {
		motor1 = new VictorSP(motor1_Port);
		motor2 = new VictorSP(motor2_Port);
		power1 = new RobotPower(power1_Port);
		power2 = new RobotPower(power2_Port);
	}

	public static void teleop() {
		if (Joysticks.lab) {
			// put cube
			motor1.set((power1.getPortCurrent() > stopCurrent) ? 0 : 0.4);
			motor2.set((power2.getPortCurrent() > stopCurrent) ? 0 : -0.4);
		} else if (Joysticks.x) {
			// suck cube without current limit
			motor1.set((power1.getPortCurrent() > stopCurrent) ? 0 : -speed);
			motor2.set((power2.getPortCurrent() > stopCurrent) ? 0 : speed);
		} else if (Joysticks.y && !collect) {
			// start suck cube for 5 seconds
			collect = true;
			collectTimer.start();
		} else if (collect) {
			// suck cube
			motor1.set((power1.getPortCurrent() > stopCurrent) ? 0 : -speed);
			motor2.set((power2.getPortCurrent() > stopCurrent) ? 0 : speed);
			SmartDashboard.putNumber("lightStatus", 1);
		} else {
			motor1.set(0);
			motor2.set(0);
		}

		if (collectTimer.get() > collectTime || power1.getPortCurrent() > stopCurrent || power2.getPortCurrent() > stopCurrent || Joysticks.x) {
			collectTimer.stop();
			collectTimer.reset();
			collect = false;
			// stop sucking if the time expired or current run over
		}

		SmartDashboard.putNumber("Cube/current1", power1.getPortCurrent());
		SmartDashboard.putNumber("Cube/current2", power2.getPortCurrent());
	}
	
	public static void stop() {
		motor1.set(0);
		motor2.set(0);
	}

	public static void put() {
		if (!put_start) {
			putTimer.start();
			put_start = true;
		}
		double timer = putTimer.get();
		motor1.set(timer < puttime ? speed : 0);
		motor2.set(timer < puttime ? -speed : 0);
		put_finish = timer < puttime ? false : true;
	}

	public static boolean isPut() {
		return (put_finish) ? true : false;
	}
}
