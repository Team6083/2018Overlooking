package System.Autonomous.modes;

import System.UpAssembly;
import System.Autonomous.AutoEngine;

public class Baseline extends AutoEngine {
	
	private static final double baseLineDis = 100;// In inch

	public static void loop() {
		switch (step) {
		case 0:
			currentStep = "Set Raise Up";
			// UpAssembly.moveStep(1);
			nextStep();
			break;
		case 1:
			currentStep = "Raise Up";
			if (UpAssembly.isReachTarget())
				nextStep();
			break;
		case 2:
			currentStep = "Go foward";
			if (rightDistance > baseLineDis && leftDistance > baseLineDis) {
				nextStep();
			}
			
			if (leftDistance < baseLineDis) {
				leftSpeed = 0.2;
			} else {
				leftSpeed = 0;
			}

			if (rightDistance < baseLineDis) {
				rightSpeed = 0.2;
			} else {
				rightSpeed = 0;
			}
			gyrowalker.setTargetAngle(0);
			break;
		default:
			currentStep = "none";
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		}
	}

}
