package org.usfirst.frc.team2412.robot.commands;

public class InTakeDown extends CommandBase {
	public InTakeDown()
	{ 
		requires(InTakeUpDown);} 
//The Initialize command initializes the counter in the above subsystem then starts the motor moving.
//It then tests the counter value in the isFinished() method waiting for it to count the limit switch changing.
//When it does, the arm stops.
//By using a hardware counter, a switch that might close then open very quickly can still be caught by the program.
		
	public void execute()
	{ 
		InTakeUpDown.InTakeDown();
		}
	@Override protected boolean isFinished()
	{
		return InTakeUpDown.limitSwitchDown(); 
		}
}
//isFinished()
//{return false;}