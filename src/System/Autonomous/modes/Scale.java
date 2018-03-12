package System.Autonomous.modes;

import System.SuckingAssembly;
import System.UpAssembly;
import System.Autonomous.AutoEngine;

public class Scale extends AutoEngine {

	public static void loop() {
		switch (step) {
		case 0:
			currentStep = "Walk1";
			walk(100);
			gyrowalker.setTargetAngle(0);
			break;
		case 1:
			currentStep = "Turn";
			leftSpeed = 0;
			rightSpeed = 0;
			if(station == 1&&scalePos==1) {
				gyrowalker.setTargetAngle(12);
				nextStep();
			}
			else if(station == 3&&scalePos==2){
				gyrowalker.setTargetAngle(-12);
				nextStep();
			}else {
				gyrowalker.setTargetAngle(0);
				walk(70);
			}
			break;
		case 2:
			currentStep = "Walk2";
			if(!((station == 1&&scalePos==1)||(station == 3&&scalePos==2)))  {
				step=-1;
			}
			walk(170);
			break;
		case 3:
			currentStep = "Set Raise Up";
			UpAssembly.moveStep(2);
			nextStep();
			break;
		case 4:
			currentStep = "Raise Up";
			leftSpeed = 0;
			rightSpeed = 0;
				if (UpAssembly.isReachTarget()) nextStep();
			break;
		case 5:
			currentStep = "Put Cube";
			leftSpeed = 0;
			rightSpeed = 0;
			if(!SuckingAssembly.isPut()) {
				SuckingAssembly.put();
			}else {
				nextStep();
			}
			break;
		default:
			currentStep = "none";
			break;
		}
	}

}
