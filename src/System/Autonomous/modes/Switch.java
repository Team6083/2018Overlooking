package System.Autonomous.modes;

import System.SuckingAssembly;
import System.UpAssembly;
import System.Autonomous.AutoEngine;

public class Switch extends AutoEngine {

	static double[] walk1 = { 146, 0, 146 };
	static double[] walk2 = {40 , 135, 40 };
	static double walk3 = 3;

	static boolean first = false;

	public static void loop() {
		switch (step) {
		case 0:
			currentStep = "Walk1";
			if ((station == 1 && switchPos == 2) || (station == 3 && switchPos == 1)) {
				walk(120);
			} else {
				walk(walk1[station - 1]);
			}
			break;
		case 1:
			currentStep = "Set Turn1";
			if((station == 1 && switchPos == 2) || (station == 3 && switchPos == 1)) {
				step = -1;
				break;
			}
			leftSpeed = 0;
			rightSpeed = 0;
			if (station == 2) {
				gyrowalker.setTargetAngle((switchPos == 1) ? -23 : 24);
			} else {
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
			UpAssembly.moveStep(1);
			nextStep();
			break;
		case 4:
			currentStep = "Raise Up";
			leftSpeed = 0;
			rightSpeed = 0;
			if (station == 2) {
				if (UpAssembly.isReachTarget())
					nextStep();
			} else {
				nextStep();
			}
			break;

		case 5:
			currentStep = "Walk2";
			walk(walk2[station - 1]);
			break;
		case 6:
			currentStep = "Set Turn 2";
			if (station != 2)
				step = 8;
			else {
				gyrowalker.setTargetAngle(0);
				nextStep();
			}
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		case 7:
			currentStep = "Turn 2";
			leftSpeed = 0;
			rightSpeed = 0;
			if (gyrowalker.getErrorAngle() < 10) {
				if (!first) {
					autoTimer.start();
					first = true;
				}
				if (autoTimer.get() > 1) {
					nextStep();
					autoTimer.start();
				}
			} else {
				autoTimer.stop();
				autoTimer.reset();
				first = false;
			}
			break;
		case 8:
			currentStep = "Put Cube";
			leftSpeed = 0;
			rightSpeed = 0;
			if (!SuckingAssembly.isPut()) {
				SuckingAssembly.put();
			} else {
				nextStep();
			}
			break;
		/*
		 * 
		 * case 8: currentStep = "stop before walk3"; if(autoTimer.get()>3) nextStep();
		 * leftSpeed = 0; rightSpeed = 0; // nextStep(); break; case 9: currentStep =
		 * "Walk3"; if (rightDistance > walk3 && leftDistance > walk3) { nextStep(); }
		 * 
		 * if (leftDistance < walk3) { leftSpeed = 0.2; } else { leftSpeed = 0; }
		 * 
		 * if (rightDistance < walk3) { rightSpeed = 0.2; } else { rightSpeed = 0; }
		 * 
		 * break;
		 */
		default:
			currentStep = "none";
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		}
	}

}
