package System.Autonomous;

import System.DriveBase;
import System.UpAssembly;
import System.Autonomous.modes.*;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
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
	protected static int station;

	protected static String gameData;
	protected static int switchPos, scalePos;

	protected static SendableChooser<String> m_chooser = new SendableChooser<>();
	protected static SendableChooser<String> a_chooser = new SendableChooser<>();

	protected static ADXRS450_Gyro gyro;
	protected static GyroWalker gyrowalker;
	protected static Encoder leftEnc, rightEnc;

	protected static final int leftEnc_ChA = 8;
	protected static final int leftEnc_ChB = 9;
	protected static final int rightEnc_ChA = 0;
	protected static final int rightEnc_ChB = 1;
	protected static final double disPerStep = 0.05236;

	protected static double leftSpeed;
	protected static double rightSpeed;
	protected static double leftDistance;
	protected static double rightDistance;

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
		rightEnc.setReverseDirection(false);
		SmartDashboard.putNumber("autoDelay", 0);
	}

	public static void start() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		allianceSelected = a_chooser.getSelected();
		System.out.println("Auto selected: " + m_autoSelected + " on " + allianceSelected);

		gameData = DriverStation.getInstance().getGameSpecificMessage();
		step = 0;
		leftSpeed = 0;
		rightSpeed = 0;
		switch (allianceSelected) {
		case kA1:
			station = 1;
			break;
		case kA2:
			station = 2;
			break;
		case kA3:
			station = 3;
			break;
		default:
			station = 1;
			break;
		}

		switchPos = (gameData.charAt(0) == 'L') ? 1 : 2;
		scalePos = (gameData.charAt(1) == 'L') ? 1 : 2;
		Timer.delay(SmartDashboard.getNumber("autoDelay", 0));
		// SmartDashboard.putNumber("Target Angle", 0);
	}

	public static void loop() {
		SmartDashboard.putNumber("drive/gyro/angle", GyroWalker.translateAngle(gyro.getAngle()));
		leftDistance = leftEnc.getDistance() * disPerStep;
		rightDistance = rightEnc.getDistance() * disPerStep;

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
			currentStep = "DoNothing";
			leftSpeed = 0;
			rightSpeed = 0;
			gyrowalker.setTargetAngle(0);
			// gyrowalker.setTargetAngle(SmartDashboard.getNumber("Target Angle", 0));
			break;
		}
		UpAssembly.autoLoop();
		gyrowalker.calculate(leftSpeed, rightSpeed);

		leftSpeed = gyrowalker.getLeftPower();
		rightSpeed = gyrowalker.getRightPower();
		DriveBase.directControl(leftSpeed, -rightSpeed);

		SmartDashboard.putString("currentStep", currentStep);
		SmartDashboard.putNumber("Current Angle", gyrowalker.getCurrentAngle());
		SmartDashboard.putNumber("Error Angle", gyrowalker.getErrorAngle());
		SmartDashboard.putNumber("Left Dis", leftDistance);
		SmartDashboard.putNumber("Right Dis", rightDistance);
		SmartDashboard.putNumber("Timer", autoTimer.get());
	}

	protected static void nextStep() {
		step++;
		System.out.println("Finish step:"+currentStep);
		autoTimer.stop();
		autoTimer.reset();
		System.out.println("Encoder reset on "+ leftDistance +", "+ rightDistance);
		leftEnc.reset();
		rightEnc.reset();
	}

	public static void walk(double dis) {
		if (dis > 0) {
			if (leftDistance < dis) {
				leftSpeed = 0.4;
			} else {
				leftSpeed = 0;
			}
			if (rightDistance < dis) {
				rightSpeed = 0.4;
			} else {
				rightSpeed = 0;
			}
			if (rightDistance > dis || leftDistance > dis) {
				rightSpeed = 0;
				leftSpeed = 0;
				gyrowalker.setTargetAngle(0);
				nextStep();

			}
		} else {
			if (leftDistance > dis) {
				leftSpeed = -0.4;
			} else {
				leftSpeed = 0;
			}
			if (rightDistance > dis) {
				rightSpeed = -0.4;
			} else {
				rightSpeed = 0;
			} 
			if (rightDistance < dis || leftDistance < dis) {
				rightSpeed = 0;
				leftSpeed = 0;
				gyrowalker.setTargetAngle(0);
				nextStep();

			}
		}

	}
}
