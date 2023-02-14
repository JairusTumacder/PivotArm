
package frc.robot;

import frc.robot.commands.PivotArmButtonCmd;
import frc.robot.commands.PivotArmJoystickCmd;
import frc.robot.commands.PivotStartCmd;
import frc.robot.commands.PivotMiddleCmd;
import frc.robot.commands.PivotHighCmd;
import frc.robot.commands.ResetEncoder;
import frc.robot.subsystems.PivotArmSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final PivotArmSubsystem p_subsystem = new PivotArmSubsystem();

  private final Joystick joystick1 = new Joystick(0);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {

    new JoystickButton(joystick1, 1).onTrue(new PivotArmButtonCmd(p_subsystem)); // Button for setting it to a certain encoder
    new JoystickButton(joystick1, 2).onTrue(new PivotStartCmd(p_subsystem)); // Button for the starting position
    new JoystickButton(joystick1, 3).onTrue(new PivotMiddleCmd(p_subsystem)); // Button for the middle position
    new JoystickButton(joystick1, 4).onTrue(new PivotHighCmd(p_subsystem)); // Button for the high position
    new JoystickButton(joystick1, 5).onTrue(new ResetEncoder(p_subsystem)); // Button for reseting the encoder position
    new JoystickButton(joystick1, 6).toggleOnTrue(new PivotArmJoystickCmd(p_subsystem, () -> joystick1.getY())); // Button for driving the motor using the joystick

  }

  public Command getAutonomousCommand() {
    return null;
  }
}
