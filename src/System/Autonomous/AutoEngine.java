package System.Autonomous;

import System.DriveBase;
import System.UpAssembly;
import System.Autonomous.modes.*;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoEngine {
	protected static final String kDoNithing = "Do nothing";
	protected static final String kBaseline = "Walk To Baseline";
	protected static final String kSwitch = "Switch";
	protected static final String kScale = "Scale";
	protected static String m_autoSelected;
	
	protected static final String kA1 = "A1";
	protected static final String kA2 = "A2";
	protected static final String kA3 = "A3";
	protected static String allianceSelected;
	
	protected static String gameData;
	
	protected static SendableChooser<String> m_chooser = new SendableChooser<>();
	protected static SendableChooser<String> a_chooser = new SendableChooser<>();
	
	protected static ADXRS450_Gyro gyro;
	protected static GyroWalker gyrowalker;
	protected static Encoder leftEnc, rightEnc;
	
	protected static final int leftEnc_ChA = 0;
	protected static final int leftEnc_ChB = 1;
	protected static final int rightEnc_ChA = 8;
	protected static final int rightEnc_ChB = 9;
	protected static final double disPerStep = 0.05236;
	
	protected static double leftSpeed;
	protected static double rightSpeed;
	
	protected static int step;
	protected static String currentStep = "";
	protected static Timer autoTimer = new Timer();
	
	public static void init() {
		m_chooser.addDefault("Do nothing", kDoNithing);
		m_chooser.addObject("Baseline", kBaseline);
		m_chooser.addObject("Switch", kSwitch);
		m_chooser.addObject("Scale", kScale);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		a_chooser.addDefault("A1", kA1);
		a_chooser.addObject("A2", kA2);
		a_chooser.addObject("A3", kA3);
		SmartDashboard.putData("Auto point choices", a_chooser);
		
		gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
		gyro.calibrate();
		gyrowalker = new GyroWalker(gyro);
		leftEnc = new Encoder(leftEnc_ChA, leftEnc_ChB);
		leftEnc.setReverseDirection(true);
		
		rightEnc = new Encoder(rightEnc_ChA, rightEnc_ChB);
	}

	public static void start() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		allianceSelected = a_chooser.getSelected();
		System.out.println("Auto selected: " + m_autoSelected+" on " + allianceSelected);
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		step = 0;
		leftSpeed = 0;
		rightSpeed = 0;
	}

	public static void loop() {
		SmartDashboard.putNumber("drive/gyro/angle", GyroWalker.translateAngle(gyro.getAngle()));
		switch (m_autoSelected) {
		case kSwitch:
			Switch.loop();
			break;
		case kScale:
			Scale.loop();
			break;
		case kBaseline:
			Baseline.loop();
			break;
		case kDoNithing:
		default:
			
			break;
		}
		UpAssembly.autoLoop();
		DriveBase.directControl(leftSpeed, rightSpeed);
		
		SmartDashboard.putString("currentStep", currentStep);
		SmartDashboard.putNumber("Current Angle", gyrowalker.getCurrentAngle());
		SmartDashboard.putNumber("Error Angle", gyrowalker.getErrorAngle());
		SmartDashboard.putNumber("Left Dis", leftEnc.getDistance() * disPerStep);
		SmartDashboard.putNumber("Right Dis", rightEnc.getDistance() * disPerStep);
	}
	
	protected static void nextStep() {
		step++;
		autoTimer.stop();
		autoTimer.reset();
	}
}
