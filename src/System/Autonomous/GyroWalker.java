package System.Autonomous;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public class GyroWalker {
	Gyro gyro;
	
	private double currentSourceAngle, currentAngle;
	private double errorAngle;
	private double targetAngle;
	
	private double leftPower, rightPower;
	private double gain;
	private double maxPower;
	
	public GyroWalker(Gyro gyro) {
		this.gyro = gyro;
		leftPower = 0;
		rightPower = 0;
		targetAngle = 0;
		gain = 0.005;
		maxPower = 0.3;
	}
	
	public void calculate(double leftSetPower, double rightSetPower) {
		currentSourceAngle = gyro.getAngle();
		currentAngle = translateAngle(currentSourceAngle);
		
		double angle = currentAngle;
		if(angle > 180) {
			angle = angle - 360;
		}
		
		if(Math.abs(targetAngle)>160) {
			angle = currentAngle; 
		}
		
		errorAngle = targetAngle - angle;
		
		if(errorAngle < 20) {
			leftPower = leftSetPower + errorAngle * gain * (20 - errorAngle)/20 * 6;
			rightPower = rightSetPower - errorAngle * gain * (20 - errorAngle)/20 * 6;
		}
		else {
			leftPower = leftSetPower + errorAngle * gain;
			rightPower = rightSetPower - errorAngle * gain;
		}
		
		if(Math.abs(leftPower)>maxPower) {
            if (leftPower >= 0) {
                leftPower = maxPower;
            } else {
                leftPower = -maxPower;
            }
		}
		
		if(Math.abs(rightPower)>maxPower) {
            if (rightPower >= 0) {
            	rightPower = maxPower;
            } else {
            	rightPower = -maxPower;
            }
		}
	}
	
	public void setTargetAngle(double angle) {
		targetAngle = angle;
	}
	
	public static double translateAngle(double sourceAngle) {
		double angle = sourceAngle - (360 * (int)(sourceAngle/360));
		if(angle < 0) {
			angle = 360 + angle;
		}
		return angle;
	}
	
	public void setGain(double gain) {
		this.gain = gain;
	}
	
	public void setMaxPower(double power) {
		maxPower = power;
	}
	
	public double getcurrentSourceAngle() {
		return currentSourceAngle;
	}
	
	public double getCurrentAngle() {
		return currentAngle;
	}
	
	public double getErrorAngle() {
		return errorAngle;
	}
	
	public double getLeftPower() {
		return leftPower;
	}

	public double getRightPower() {
		return rightPower;
	}
	
	public double getTargetAngle() {
		return targetAngle;
	}
}
