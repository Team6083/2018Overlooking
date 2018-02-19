package System;

import edu.wpi.first.wpilibj.VictorSP;

public class ClimbAssembly {
	
	private static final int motor_Port = 4;
	
	private static VictorSP motor; 
	
	public static void init() {
		motor = new VictorSP(motor_Port);
	}
	
	public static void teteop() {
		
	}
}
