package System;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.VictorSP;

public class ClimbAssembly {
	
	private static final int climbmotor_Port = 7;
	private static final int stringtalon_Port = 2;
	
	private static VictorSP climbmotor;	
	private static TalonSRX stringtalon;
	public static void init() {
		climbmotor = new VictorSP(climbmotor_Port);
		stringtalon = new TalonSRX(stringtalon_Port);
	}
	
	public static void teteop() {
		if (Joysticks.probutton[0]) {
			stringtalon.set(ControlMode.PercentOutput, -1);
		} else if (Joysticks.probutton[1]) {
			stringtalon.set(ControlMode.PercentOutput, 1);
		}
		else if(Joysticks.probutton[2]) {
			climbmotor.set(-0.25);
		}
		else if(Joysticks.pov == 270) {
			climbmotor.set(0.25);
		}
		
		else {
			stringtalon.set(ControlMode.PercentOutput, 0);
			climbmotor.set(0);
		}
		
	}
}
