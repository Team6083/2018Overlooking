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
	private double maxEdit;
	
	public GyroWalker(Gyro gyro) {
		this.gyro = gyro;
		leftPower = 0;
		rightPower = 0;
		targetAngle = 0;
		gain = 0.004;
		maxPower = 0.6;
		maxEdit = 0.2;
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
		//translate angle to -180~180
		
		errorAngle = targetAngle - angle;
		//calculate the difference between target angle and current angle
		
		double editPower = 0;
		
		if(Math.abs(errorAngle) < 20) {
		//make robot still move if the errorAngle is too small
			editPower = errorAngle * gain * (20 - errorAngle)/20 * 10;
		}
		else {
			editPower =  errorAngle * gain;
		}
		//calculate output diff
		
		if(editPower>maxEdit) {
			editPower = (editPower > 0)?maxEdit:-maxEdit;
		}
		//limit the max output diff
		
		leftPower = leftSetPower + editPower;
		rightPower = rightSetPower - editPower;
		//calculate final output
		
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
		//limit max output
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
