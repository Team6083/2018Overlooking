/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6083.robot;

import System.Joysticks;
import System.Autonomous.GyroWalker;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	Joystick stick = new Joystick(0);
	VictorSP Lmotor1, Lmotor2, Rmotor1, Rmotor2, UPmotor, Smotor1, Smotor2;

	private final static double error_range = 0.1;
	private final static double Sspeed = 0.7;
	private static double speedl, speedr, LeftY, RightY;
	ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
	GyroWalker gyrowalker;
	

	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		Joysticks.init();
		gyro.calibrate();
		Lmotor1 = new VictorSP(0);
		Lmotor2 = new VictorSP(1);
		Rmotor1 = new VictorSP(2);
		Rmotor2 = new VictorSP(3);
		UPmotor = new VictorSP(4);
		Smotor1 = new VictorSP(5);
		Smotor2 = new VictorSP(6);
		gyrowalker = new GyroWalker(gyro);
	}

	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
		SmartDashboard.putNumber("targetangle", 0);
		SmartDashboard.putNumber("gain", 0.05);
		SmartDashboard.putNumber("maxSpeed", 0.2);
	}

	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
		case kCustomAuto:
			// Put custom auto code here
			break;
		case kDefaultAuto:
		default:
			// Put default auto code here
			gyrowalker.setTargetAngle(SmartDashboard.getNumber("targetangle", 0));
			gyrowalker.calculate(0.25, -0.25);
			Lmotor1.set(gyrowalker.getLeftPower());
			Lmotor2.set(gyrowalker.getLeftPower());
			Rmotor1.set(-gyrowalker.getRightPower());
			Rmotor2.set(-gyrowalker.getRightPower());
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
	public void teleopPeriodic() {
		
		Joysticks.update_data();
		
		// Remove error value
		if (stick.getRawAxis(1) <= -error_range || stick.getRawAxis(1) > error_range) { // Yaxis (left)
			LeftY = -stick.getRawAxis(1);
		} else {
			LeftY = 0;
		}

		if (stick.getRawAxis(5) <= -error_range || stick.getRawAxis(5) > error_range) { // Yaxis (right)
			RightY = -stick.getRawAxis(5);
		} else {
			RightY = 0;
		}
		speedl = LeftY / 3;
		speedr = RightY / 3;
		
		// Speed up when button pressed
		if (stick.getRawButton(5)) {
			speedl = speedl * 2;
		}
		if (stick.getRawButton(6)) {
			speedr = speedr * 2;
		}
		
		
		Lmotor1.set(speedl);
		Lmotor2.set(speedl);
		Rmotor1.set(-speedr);
		Rmotor2.set(-speedr);
		
		
		if (stick.getRawAxis(2) > 0.1) {
			UPmotor.set(stick.getRawAxis(2));
		} else if (stick.getRawAxis(3) > 0.1) {
			UPmotor.set(-stick.getRawAxis(3));
		} else {
			UPmotor.set(0);
		}

		if (stick.getRawButton(9)) {
			Smotor1.set(Sspeed);
			Smotor2.set(-Sspeed);
		} else if (stick.getRawButton(10)) {
			Smotor1.set(-Sspeed);
			Smotor2.set(Sspeed);	
		} else {
			Smotor1.set(0);
			Smotor2.set(0);
		}

		SmartDashboard.putNumber("leftmotor", Lmotor1.get());
		SmartDashboard.putNumber("rightmotor", Rmotor1.get());
		SmartDashboard.putNumber("UPmotor", UPmotor.get());
		SmartDashboard.putNumber("Smotor", Smotor1.get());
	}

	@Override
	public void testPeriodic() {

	}
}
