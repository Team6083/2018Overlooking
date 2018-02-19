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
		if (Joysticks.pov == 0) {
			stringtalon.set(ControlMode.PercentOutput, -1);
		} else if (Joysticks.pov == 180) {
			stringtalon.set(ControlMode.PercentOutput, 1);
		}
		else if(Joysticks.pov == 90) {
			climbmotor.set(0.5);
		}
		else if(Joysticks.pov == 270) {
			climbmotor.set(-0.5);
		}
		
		else {
			stringtalon.set(ControlMode.PercentOutput, 0);
			climbmotor.set(0);
		}
		
	}
}
