package System.Autonomous.modes;

import System.SuckingAssembly;
import System.UpAssembly;
import System.Autonomous.AutoEngine;

public class Switch extends AutoEngine {

	static double[] walk1 = { 135, 40, 135 };
	static double[] walk2 = { 20 , 50, 20 };
	static double walk3 = 3;

	static boolean first = false;

	public static void loop() {
		switch (step) {
		case 0:
			currentStep = "Walk1";
			walk(walk1[station - 1]);
			break;
		case 1:
			currentStep = "Set Turn1";
			if((station == 1 && switchPos == 2) || (station == 3 && switchPos == 1)) {
				step = -1;
				break;
			}
			leftSpeed = 0;
			rightSpeed = 0;
			if (station != 2) {
				gyrowalker.setTargetAngle((switchPos == 1) ? 90 : -90);
			}
			nextStep();
			break;
		case 2:
			currentStep = "Turn1";
			leftSpeed = 0;
			rightSpeed = 0;
			if (gyrowalker.getErrorAngle() < 10) {
				nextStep();
			}
			break;
		case 3:
			currentStep = "Set Raise Up";
			if(switchPos == 2) {
				UpAssembly.moveStep(1);
			}
			nextStep();
			break;
		case 4:
			currentStep = "Raise Up";
			if(UpAssembly.isReachTarget()) {
				nextStep();
			}
			break;
		case 5:
			currentStep = "walk2";
			walk(walk2[station-1]);
			break;
		case 6:
			currentStep = "Put Cube";
			leftSpeed = 0;
			rightSpeed = 0;
			if(switchPos == 2) {
				if (!SuckingAssembly.isPut()) {
					SuckingAssembly.put();
				} else {
					nextStep();
				}
			}
			else {
				nextStep();
			}
			break;
		case 8:
			currentStep = "Finished";
			break;
		default:
			currentStep = "none";
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		}
	}

}
