package org.usfirst.frc.team2412.robot.subsystems;

import org.usfirst.frc.team2412.robot.RobotMap;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.command.Subsystem;

public class LiftSubsystem extends Subsystem {

	// other vars that are useful

	double inchOffset = 19; // this is the offset for the lift, as it doesnt go lower than the top hatch,
							// and this makes the robot go to x inches above the ground

	double topLimit = 100; // this will prevent the robot from going too high

	double encoderOffset = 0;

	// inches
	double outputGearRadius = 0.75;
	double outputGearCircumference = outputGearRadius * 2 * Math.PI;
	double pullyRatio = 1; // one inch on string moves the lift up x inches

	// rotations
	double gearboxRatio = 9.52; // how many motor rotations are one output rotation

	double motorRotationsToInches = outputGearCircumference * pullyRatio / gearboxRatio;

	CANSparkMax liftMotorLeader = RobotMap.liftMotors[0]; // Motors from RobotMap
	CANPIDController PIDController = liftMotorLeader.getPIDController();
	CANEncoder motorEncoder = liftMotorLeader.getEncoder();

	double P = 0.01;
	double I = 0;
	double D = 0;

	public LiftSubsystem() {
		PIDController.setP(P);
		PIDController.setI(I);
		PIDController.setD(D);
		PIDController.setOutputRange(-1, 1);
	}

	@Override
	protected void initDefaultCommand() {

	}

	public double getRotationsFromInch(double inches) {
		return inches / motorRotationsToInches;
	}

	public void liftUp() {
		// if (motorEncoder.getPosition() + encoderOffset > topLimit) {
		// 	resetTop();
		// 	liftMotorLeader.set(0);
		// 	return;
		// }
		liftMotorLeader.set(0.5);
		System.out.println("Lifted Up");
	}

	public void liftDown() {
		// if (motorEncoder.getPosition() + encoderOffset < 1) {
		// 	liftMotorLeader.set(0);
		// 	resetBottom();
		// 	return;
		// }
		liftMotorLeader.set(-0.5);
		System.out.println("Lifted Down");
	}

	public void liftStop() {
		liftMotorLeader.set(0.0);
	}

	public void goToInch(double inches) {
		if (RobotMap.DEBUG_MODE) {
			System.out.println("Lift is going to " + inches + " inches from the ground.");
		}
		PIDController.setReference(getRotationsFromInch(inches - inchOffset) + encoderOffset, ControlType.kPosition);
	}

	public void resetBottom() {
		encoderOffset = motorEncoder.getPosition();
		PIDController.setReference(encoderOffset, ControlType.kPosition);
	}

	public void resetTop() {
		encoderOffset = topLimit - motorEncoder.getPosition();
		PIDController.setReference(topLimit - encoderOffset, ControlType.kPosition);
	}

	public double getInches() {
		return inchOffset * motorRotationsToInches * (motorEncoder.getPosition() + encoderOffset);
	}
}
