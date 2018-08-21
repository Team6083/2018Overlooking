/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6083.robot;

import System.ClimbAssembly;
import System.Dashboard;
import System.SuckingAssembly;
import System.DriveBase;
import System.Joysticks;
import System.RobotPower;
import System.UpAssembly;
import System.Autonomous.AutoEngine;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	final double disPerStep = 0.133;
	Servo servo = new Servo(6);

	@Override
	public void robotInit() {
		AutoEngine.init();
		UpAssembly.init();
		DriveBase.init();
		SuckingAssembly.init();
		RobotPower.init();
		Joysticks.init();
		ClimbAssembly.init();
		Dashboard.init();
		//Initialize all system
		
		CameraServer.getInstance().addAxisCamera("axis-camera1", "axis-camera1.local");
	}

	@Override
	public void autonomousInit() {
		SmartDashboard.putString("CurrentStep", "deploy claw");
		servo.set(0);
		Timer.delay(1);
		servo.set(1);
		Timer.delay(0.2);
		servo.set(0);
		Timer.delay(0.2);
		servo.set(1);
		Timer.delay(0.2);
		servo.set(0);
		Timer.delay(0.2);
		servo.set(1);
		//Push rope to release claw
		System.out.println("Claw deployed");
		
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
		Dashboard.teleop();
		DriveBase.tankDrive();
		SuckingAssembly.teleop();
		Joysticks.update_data();
		UpAssembly.teleop();
		ClimbAssembly.teteop();
		
		if(Joysticks.a) {
			servo.set(0);
		}
		else {
			servo.set(1);
		}
	}

	@Override
	public void testPeriodic() {

	}
}
