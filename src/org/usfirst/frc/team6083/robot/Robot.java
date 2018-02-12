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
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kBaselineAuto = "Walk To Baseline";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	Joystick stick = new Joystick(0);
	TalonSRX UPmotor;
	Lightning led1;

	ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
	GyroWalker gyrowalker;
	Encoder leftEnc, rightEnc;
	final double disPerStep = 0.133;

	Timer loopTimer = new Timer();
	int step;

	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("Walk To Baseline", kBaselineAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		Joysticks.init();
		gyro.calibrate();
		UPmotor = new TalonSRX(3);
		gyrowalker = new GyroWalker(gyro);
		leftEnc = new Encoder(0, 1);
		leftEnc.setReverseDirection(true);
		rightEnc = new Encoder(2, 3);
		DriveBase.init();
		CubeAssembly.init();
		RobotPower.init();
		led1 = new Lightning(2);
	}

	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
		SmartDashboard.putNumber("targetangle", 0);
		SmartDashboard.putNumber("gain", 0.05);
		SmartDashboard.putNumber("maxSpeed", 0.3);
		step = 0;
	}

	@Override
	public void autonomousPeriodic() {
		String autoStep = "";
		switch (m_autoSelected) {
		case kBaselineAuto:
			switch(step) {
			case 0:
				autoStep = "Walk to AutoLine";
				double speed = 0;
				if(disPerStep*-leftEnc.getDistance()<255) {
					speed = 0.3;
				}else {
					speed = 0;
					step++;
				}
				gyrowalker.setTargetAngle(0);
				gyrowalker.calculate(-speed, -speed);
				DriveBase.directControl(gyrowalker.getLeftPower(), -gyrowalker.getRightPower());
				
				break;
			default:
				autoStep = "none";
				
				break;
			}
			
			gyrowalker.setGain(SmartDashboard.getNumber("gain", 0.01));
			gyrowalker.setMaxPower(SmartDashboard.getNumber("maxSpeed", 0.3));
			SmartDashboard.putNumber("gyroWalker/errorAngle", gyrowalker.getErrorAngle());
			SmartDashboard.putNumber("gyroWalker/angle", gyrowalker.getCurrentAngle());
			SmartDashboard.putNumber("drive/leftSpeed", gyrowalker.getLeftPower());
			SmartDashboard.putNumber("drive/rightSpeed", gyrowalker.getRightPower());
			break;
		case kDefaultAuto:
		default:
			
			
			gyrowalker.setTargetAngle(SmartDashboard.getNumber("targetangle", 0));
			gyrowalker.calculate(-0, -0);
			DriveBase.directControl(gyrowalker.getLeftPower(), -gyrowalker.getRightPower());
			SmartDashboard.putNumber("Angle", gyrowalker.getCurrentAngle());
			SmartDashboard.putNumber("errorAngle", gyrowalker.getErrorAngle());
			SmartDashboard.putNumber("left_drive1", gyrowalker.getLeftPower());
			SmartDashboard.putNumber("right_drive1", gyrowalker.getRightPower());
			gyrowalker.setGain(SmartDashboard.getNumber("gain", 0.01));
			gyrowalker.setMaxPower(SmartDashboard.getNumber("maxSpeed", 0.3));
			break;
		}
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
			UPmotor.set(ControlMode.PercentOutput, stick.getRawAxis(2)/2);
		} else if (stick.getRawAxis(3) > 0.1) {
			UPmotor.set(ControlMode.PercentOutput, -stick.getRawAxis(3));
		} else {
			UPmotor.set(ControlMode.PercentOutput, 0);
		}
		
		if(SmartDashboard.getBoolean("Drive/reverse", false)) {
			led1.setBrightness(1);
		}
		else {
			led1.setBrightness(0);
		}
		
		SmartDashboard.putNumber("UP/motor", UPmotor.getMotorOutputPercent());
		SmartDashboard.putNumber("drive/gyro/angle", GyroWalker.translateAngle(gyro.getAngle()));
	}

	@Override
	public void testPeriodic() {

	}
}
