package System.Autonomous.modes;

import System.UpAssembly;
import System.Autonomous.AutoEngine;

public class Switch extends AutoEngine {
	
	static double[] walk1 = {150,100,150};
	static double[] walk2 = {23,54,23};
	
	static boolean first = false;
	
	public static void loop() {
		switch (step) {
		case 0:
			currentStep = "Set Raise Up";
			UpAssembly.moveStep(1);
			nextStep();
			break;
		case 1:
			currentStep = "Raise Up";
			if (UpAssembly.isReachTarget())
				nextStep();
			break;
		case 2:
			currentStep = "Walk1";
			if (rightDistance > walk1[station-1] && leftDistance > walk1[station-1]) {
				nextStep();
				
			}
			
			if (leftDistance < walk1[station-1]) {
				leftSpeed = 0.4;
			} else {
				leftSpeed = 0;
			}

			if (rightDistance < walk1[station-1]) {
				rightSpeed = 0.4;
			} else {
				rightSpeed = 0;
			}
			gyrowalker.setTargetAngle(0);
			break;
		case 3:
			currentStep = "Set Turn1";
			leftSpeed = 0;
			rightSpeed = 0;
			gyrowalker.setTargetAngle((switchPos == 1)?-90:90);
			nextStep();
			break;
		case 4:
			currentStep = "Turn1";
			leftSpeed = 0;
			rightSpeed = 0;
			if(gyrowalker.getErrorAngle() < 10) {
				nextStep();
			}
			break;
		case 5:
			currentStep = "Walk2";
			if (rightDistance > walk2[station-1] && leftDistance > walk2[station-1]) {
				nextStep();
				
			}
			
			if (leftDistance < walk2[station-1]) {
				leftSpeed = 0.4;
			} else {
				leftSpeed = 0;
			}

			if (rightDistance < walk2[station-1]) {
				rightSpeed = 0.4;
			} else {
				rightSpeed = 0;
			}
			break;
		case 6:
			currentStep = "Set Turn 2";
			if(station != 2) step = -1;
			else {
				gyrowalker.setTargetAngle(0);
			}
			leftSpeed = 0;
			rightSpeed = 0;
			nextStep();
			break;
		case 7:
			currentStep = "Turn 2";
			leftSpeed = 0;
			rightSpeed = 0;
			if(gyrowalker.getErrorAngle() < 10) {
				if(!first) {
					autoTimer.start();
					first = true;
				}
				if(autoTimer.get() > 3) {
					nextStep();
				}
			}
			else {
				autoTimer.stop();
				autoTimer.reset();
				first = false;
			}
			break;
		default:
			currentStep = "none";
			leftSpeed = 0;
			rightSpeed = 0;
			break;
		}
	}

}
