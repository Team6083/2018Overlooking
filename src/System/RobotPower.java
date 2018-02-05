package System;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class RobotPower {
	private static PowerDistributionPanel pdp;
	private static final int PDP_CANID = 1;
	private static boolean inited = false;
	
	private int devicePort;
	
	public static void init() {
		if(!inited) {
			pdp = new PowerDistributionPanel(PDP_CANID);
		}
	}
	
	public RobotPower(int port) {
		devicePort = port;
	}
	
	public double getPortCurrent() {
		return pdp.getCurrent(devicePort);
	}
	
	public static double getTotalVoltage() {
		return pdp.getVoltage();
	}
	
	public static double getTotalCurrent() {
		return pdp.getTotalCurrent();
	}
}
