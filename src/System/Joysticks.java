package System;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Joysticks {
	private static Joystick joy1, joy2;// 1 is XBox, 2 is 3D Pro

	private static final int joy1_port = 0;// define ports
	private static final int joy2_port = 1;

	private static final int joy1_axis_num = 6;// define axis_num
	private static final int joy2_axis_num = 4;

	private static boolean[] avaliable = new boolean[2];

	// define axis
	public static double lx, ly, rx, ry, lt, rt;// for XBox
	public static double xa, ya, za, silder;// for 3D Pro

	private static double[] joy1_axis = new double[joy1_axis_num];// temp data for axis
	private static double[] joy2_axis = new double[joy2_axis_num];

	public static boolean a, b, x, y, lb, rb, lab, rab, back, start;// buttons for XBox
	public static boolean[] probutton = new boolean[12];// buttons for 3D Pro
	
	public static int pov;

	private static double error_range;

	public static void init() {
		avaliable[0] = true;
		avaliable[1] = false;
		if (avaliable[0])
			joy1 = new Joystick(joy1_port);
		if (avaliable[1])
			joy2 = new Joystick(joy2_port);
		SmartDashboard.putNumber("Joystick/error_range", 0.01);// default
	}

	public static void update_data() {
		error_range = SmartDashboard.getNumber("Joystick/error_range", 0.01);
		
		pov = joy1.getPOV(0);
		fix_error();
		if (avaliable[0]) {
			lx = joy1_axis[0];
			ly = joy1_axis[1];
			lt = joy1_axis[2];
			rt = joy1_axis[3];
			rx = joy1_axis[4];
			ry = joy1_axis[5];
		}

		if (avaliable[1]) {
			xa = joy2_axis[0];
			ya = joy2_axis[1];
			za = joy2_axis[2];
			silder = joy2_axis[3];
		}

		if (avaliable[0]) {
			a = joy1.getRawButton(1);
			b = joy1.getRawButton(2);
			x = joy1.getRawButton(3);
			y = joy1.getRawButton(4);
			lb = joy1.getRawButton(5);
			rb = joy1.getRawButton(6);
			back = joy1.getRawButton(7);
			start = joy1.getRawButton(8);
			lab = joy1.getRawButton(9);
			rab = joy1.getRawButton(10);
		}

		if (avaliable[1]) {
			for (int i = 0; i < 12; i++) {
				probutton[i] = joy2.getRawButton(i + 1);
			}
		}
	}
	
	public static boolean getRealeased(int number) {
		return joy1.getRawButtonReleased(number);
	}

	private static void fix_error() {
		// joy1 part
		if (avaliable[0]) {
			for (int i = 0; i < joy1_axis_num; i++) {
				if (joy1.getRawAxis(i) < 0) {
					if (joy1.getRawAxis(i) > -error_range)
						joy1_axis[i] = 0;
					else
						joy1_axis[i] = joy1.getRawAxis(i);
				} else if (joy1.getRawAxis(i) > 0) {
					if (joy1.getRawAxis(i) < error_range)
						joy1_axis[i] = 0;
					else
						joy1_axis[i] = joy1.getRawAxis(i);
				} else
					joy1_axis[i] = 0;
			}
		}
		// joy2 part
		if (avaliable[1]) {
			for (int i = 0; i < joy2_axis_num; i++) {
				if (joy2.getRawAxis(i) < 0) {
					if (joy2.getRawAxis(i) > -error_range)
						joy2_axis[i] = 0;
					else
						joy2_axis[i] = joy2.getRawAxis(i);
				} else if (joy2.getRawAxis(i) > 0) {
					if (joy2.getRawAxis(i) < error_range)
						joy2_axis[i] = 0;
					else
						joy2_axis[i] = joy2.getRawAxis(i);
				} else
					joy2_axis[i] = 0;
			}
		}
	}
}
