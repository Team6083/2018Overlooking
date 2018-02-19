package System.Autonomous.modes;

import System.UpAssembly;
import System.Autonomous.AutoEngine;

public class Baseline extends AutoEngine {
	
	private static final double baseLineDis = 250;// Require accurate data
	
	
	public static void loop() {
		switch (step) {
		case 0:
			currentStep = "Set Raise Up";
//			UpAssembly.moveStep(1);
			nextStep();
			break;
		case 1:
			currentStep = "Raise Up";
			if(UpAssembly.isReachTarget()) nextStep();
			break;
		case 2:
			currentStep = "Go foward";
			if(leftEnc.getDistance() * disPerStep < baseLineDis) {
				leftSpeed = 0.2;
			}
			else {
				leftSpeed = 0;
			}
			
			if(rightEnc.getDistance() * disPerStep < baseLineDis) {
				rightSpeed = -0.2;
			}
			else {
				rightSpeed = 0;
			}
			gyrowalker.setTargetAngle(0);
//			gyrowalker.calculate(leftSpeed, rightSpeed);
			
//			leftSpeed = gyrowalker.getLeftPower();
//			rightSpeed = gyrowalker.getRightPower();
			
			break;
		default:
			currentStep = "none";
			break;
		}
	}

}
