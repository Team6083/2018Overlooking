/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6083.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import System.CubeAssembly;
import System.DriveBase;
import System.Joysticks;
import System.Lightning;
import System.RobotPower;
import System.Autonomous.GyroWalker;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	Joystick stick = new Joystick(0);
	TalonSRX UPmotor;
	Lightning led1;

	ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
	GyroWalker gyrowalker;
	Encoder leftEnc, rightEnc;
	final double disPerStep = 0.133;

	@Override
	public void robotInit() {
		Joysticks.init();
		gyro.calibrate();
		UPmotor = new TalonSRX(3);
		gyrowalker = new GyroWalker(gyro);
		leftEnc = new Encoder(0, 1);
		leftEnc.setReverseDirection(true);
		rightEnc = new Encoder(8, 9);
		DriveBase.init();
		CubeAssembly.init();
		RobotPower.init();
		led1 = new Lightning(2);
		
		CameraServer.getInstance().addAxisCamera("axis-camera2", "axis-camera2.local");
	}

	@Override
	public void autonomousInit() {
		
	}

	@Override
	public void autonomousPeriodic() {
		
	}

	@Override
	public void teleopInit() {
		
	}

	@Override
	public void teleopPeriodic() {
		DriveBase.tankDrive();
		CubeAssembly.teleop();
		Joysticks.update_data();

		if (stick.getRawAxis(2) > 0.1) {
			UPmotor.set(ControlMode.PercentOutput, stick.getRawAxis(2) / 2);
		} else if (stick.getRawAxis(3) > 0.1) {
			UPmotor.set(ControlMode.PercentOutput, -stick.getRawAxis(3));
		} else {
			UPmotor.set(ControlMode.PercentOutput, 0);
		}

		if (SmartDashboard.getBoolean("drive/reverse", false)) {
			led1.setBrightness(1);
		} else {
			led1.setBrightness(0);
		}

		SmartDashboard.putNumber("UP/motor", UPmotor.getMotorOutputPercent());
		SmartDashboard.putNumber("drive/gyro/angle", GyroWalker.translateAngle(gyro.getAngle()));
	}

	@Override
	public void testPeriodic() {

	}
}
