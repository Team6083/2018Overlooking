package System.Autonomous.modes;

import System.SuckingAssembly;
import System.UpAssembly;
import System.Autonomous.AutoEngine;

public class Switch extends AutoEngine {

	static double[] walk1 = { 135, 40, 135 };
	static double[] walk2 = { 20 , 50, 40 };

	public static void loop() {
		switch (step) {
		case 0:
			currentStep = "Walk1";
			walk(walk1[station - 1]);
			break;
		case 1:
			currentStep = "Set Turn1";
			if((station == 1 && switchPos == 2) || (station == 3 && switchPos == 1) || switchPos == 0) {
				step = -1;
				break;
			}
			leftSpeed = 0;
			rightSpeed = 0;
			if (station != 2) {
				gyrowalker.setTargetAngle((switchPos == 1) ? 90 : -90);
			}
			else {
				gyrowalker.setTargetAngle(0);
			}
			nextStep();
			break;
		case 2:
			currentStep = "Turn1";
			leftSpeed = 0;
			rightSpeed = 0;
			if (gyrowalker.getErrorAngle() < 15 || (autoTimer.get() > 2 && gyrowalker.getErrorAngle() < 40)) {
				nextStep();
			}
			break;
		case 3:
			currentStep = "Set Raise Up";
			leftSpeed = 0;
			rightSpeed = 0;
			UpAssembly.moveStep(1);
			nextStep();
			break;
		case 4:
			currentStep = "Raise Up";
			leftSpeed = 0;
			rightSpeed = 0;
			if(UpAssembly.isReachTarget()) {
				nextStep();
			}
			break;
		case 5:
			currentStep = "walk2";
			walk(walk2[station-1]);
			if(autoTimer.get()>1.5) {
				leftSpeed = 0;
				rightSpeed = 0;
				nextStep();
			}
			break;
		case 6:
			currentStep = "Wait before put";
			if(autoTimer.get()>0.8) {
				nextStep();
			}
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		case 7:
			currentStep = "Put Cube";
			leftSpeed = 0;
			rightSpeed = 0;
			if (!SuckingAssembly.isPut()) {
				SuckingAssembly.put();
			} else {
				nextStep();
			}
			break;
		case 8:
			currentStep = "Finished";
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		default:
			currentStep = "none";
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		}
	}

}
