package frc.robot;
 

import frc.robot.commands.MidPosition;
import frc.robot.commands.PivotArmButtonCmd;
import frc.robot.commands.PivotHighCmd;
import frc.robot.commands.PivotLowCmd;
import frc.robot.commands.TopNodeCaseStatement;
import frc.robot.commands.TuckedIn;
import frc.robot.commands.ZeroPosition;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.PivotArmSubsystem;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final PivotArmSubsystem p_subsystem = new PivotArmSubsystem(); 
  private final ElevatorSubsystem elevator = new ElevatorSubsystem();
  private final TuckedIn tucked = new TuckedIn(p_subsystem);
  private final AddressableLED led = new AddressableLED(0);
  private final AddressableLEDBuffer ledbuffer = new AddressableLEDBuffer(60);

  //private final Joystick joystick1 = new Joystick(0);
  private final XboxController xController = new XboxController(0);

  public RobotContainer() {
   // p_subsystem.setDefaultCommand(new PivotArmJoystickCmd(p_subsystem, () -> xController.getLeftY()));
   led.setLength(ledbuffer.getLength());
    configureBindings();
  }

  public void yellow(){
    for(var i = 0; i < ledbuffer.getLength(); i++){
      ledbuffer.setHSV(i, 60, 100, 100);
    }
    led.setData(ledbuffer);
    led.start();
  }

  public void purple(){
    for(var i = 0; i < ledbuffer.getLength(); i++){
      ledbuffer.setHSV(i, 300, 100, 50);
    }
    led.setData(ledbuffer);
    led.start();
  }

  private void configureBindings() {
    new JoystickButton(xController, 1).onTrue(new PivotHighCmd(p_subsystem));
    new JoystickButton(xController, 2).onTrue(tucked); // Button for the starting position
    new JoystickButton(xController, 3).onTrue(new TopNodeCaseStatement(p_subsystem, elevator)); // Button for the middle position
    new JoystickButton(xController, 4).onTrue(new PivotLowCmd(p_subsystem)); // Button for the high position
    new JoystickButton(xController, 5).onTrue(new MidPosition(elevator)); 
    new JoystickButton(xController, 6).onTrue(new ZeroPosition(elevator)); // Button for driving the motor using the joystick
    new JoystickButton(xController, 7).whileTrue(new PivotArmButtonCmd(p_subsystem, .3));
    new JoystickButton(xController, 8).whileTrue(new PivotArmButtonCmd(p_subsystem, -.3));
    new JoystickButton(xController, 9).onTrue(new InstantCommand(() -> yellow())); // Button for setting the led to yellow
    new JoystickButton(xController, 10).onTrue(new InstantCommand(() -> purple())); // Button for setting the led to purple
  }

  

  public Command getAutonomosCommand() {
    return null;
  }
}