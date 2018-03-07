package System.Autonomous.modes;

import System.UpAssembly;
import System.Autonomous.AutoEngine;

public class Baseline extends AutoEngine {
	
	private static final double baseLineDis = -120;// In inch

	public static void loop() {
		switch (step) {
		case 0:
			currentStep = "Set Raise Up";
			 UpAssembly.moveStep(1);
			nextStep();
			break;
		case 1:
			currentStep = "Raise Up";
			if (UpAssembly.isReachTarget())nextStep();
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		case 2:
			currentStep = "Go foward";
			walk(baseLineDis);
			break;
		default:
			currentStep = "none";
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		}
	}

}
