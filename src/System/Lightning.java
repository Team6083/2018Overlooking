package System;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Lightning {
	private final double maxCurrent = 1.5;	
	private TalonSRX light;
	
	public Lightning(int CANId) {
		light = new TalonSRX(CANId);
	}
	
	public void setBrightness(double percent) {
		percent = (percent>1) ? 1:percent;
		percent = (percent<0) ? 0:percent;
		light.set(ControlMode.PercentOutput, 0.2*percent);
	}
	
	public double getBrightness() {
		return light.getMotorOutputPercent();
	}
	
}
