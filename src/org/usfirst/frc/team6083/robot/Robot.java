/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6083.robot;

import System.CubeAssembly;
import System.DriveBase;
import System.Joysticks;
import System.Lightning;
import System.RobotPower;
import System.UpAssembly;
import System.Autonomous.AutoEngine;
import System.Autonomous.GyroWalker;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Joystick stick = new Joystick(0);
	Lightning led1;

	final double disPerStep = 0.133;

	@Override
	public void robotInit() {
		AutoEngine.init();
		UpAssembly.init();
		DriveBase.init();
		CubeAssembly.init();
		RobotPower.init();
		Joysticks.init();
//		led1 = new Lightning(2);
		
		CameraServer.getInstance().addAxisCamera("axis-camera2", "axis-camera2.local");
	}

	@Override
	public void autonomousInit() {
		AutoEngine.start();
	}

	@Override
	public void autonomousPeriodic() {
		AutoEngine.loop();
	}

	@Override
	public void teleopInit() {
		
	}

	@Override
	public void teleopPeriodic() {
		DriveBase.tankDrive();
		CubeAssembly.teleop();
		Joysticks.update_data();
		UpAssembly.teleop();

		if (SmartDashboard.getBoolean("drive/reverse", false)) {
//			led1.setBrightness(1);
		} else {
//			led1.setBrightness(0);
		}
		
		
		SmartDashboard.putNumber("Up step", UpAssembly.getStep());
	}

	@Override
	public void testPeriodic() {

	}
}
