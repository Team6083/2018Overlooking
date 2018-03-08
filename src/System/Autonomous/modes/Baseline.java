package System.Autonomous.modes;

import System.UpAssembly;
import System.Autonomous.AutoEngine;

public class Baseline extends AutoEngine {
	
	private static final double baseLineDis = -120;// In inch

	public static void loop() {
		switch (step) {
		/*case 0:
			currentStep = "Set Raise Up";
			 UpAssembly.moveStep(1);
			nextStep();
			break;
		case 1:
			currentStep = "Raise Up";
			if (UpAssembly.isReachTarget())nextStep();
			leftSpeed = 0;
			rightSpeed = 0;
			break;*/
		case 0:
			currentStep = "Go forward";
			if(station!=2) {
				walk(baseLineDis);
				step=-1;
			}else {
				gyrowalker.setTargetAngle(switchPos==1?-155:155);
			}
			nextStep();
			break;
			case 1:
			currentStep = "Turn1";
			leftSpeed = 0;
			rightSpeed = 0;
			if(gyrowalker.getErrorAngle() < 10) {
				nextStep();
			}
			break;
			case 2:
				currentStep = "walk";
				walk(135);
		default:
			currentStep = "none";
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		}
	}

}
