// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.PivotArmButtonCmd;
import frc.robot.commands.PivotArmDefault;
import frc.robot.commands.PivotArmJoystickCmd;
import frc.robot.commands.PivotPID123Cmd;
import frc.robot.commands.PivotStartCmd;
import frc.robot.commands.PivotMiddleCmd;
import frc.robot.commands.PivotHighCmd;
import frc.robot.commands.ResetEncoder;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.PivotArmSubsystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.TalonEncoder;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private TalonEncoder tEnc;
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final PivotArmSubsystem p_subsystem = new PivotArmSubsystem(tEnc);
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private final Joystick joystick1 = new Joystick(0);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    //p_subsystem.setDefaultCommand(new PivotArmJoystickCmd(p_subsystem, () -> joystick1.getY())); // When the joystick moves up and down, move the pivot arm in the same direction
    //p_subsystem.setDefaultCommand(new PivotArmPIDCmd(p_subsystem));
    p_subsystem.setDefaultCommand(new PivotArmDefault(p_subsystem));
    configureBindings();
  }
  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    new Trigger(m_exampleSubsystem::exampleCondition)
        .onTrue(new ExampleCommand(m_exampleSubsystem));

    new JoystickButton(joystick1, 1).onTrue(new PivotArmButtonCmd(p_subsystem)); // When the joystick button is toggled, runs the Pivot Arm Button Command
    new JoystickButton(joystick1, 2).onTrue(new PivotStartCmd(p_subsystem)); // When the joystick's second button is toggled, run the Pivot PID Command
    new JoystickButton(joystick1, 3).onTrue(new PivotMiddleCmd(p_subsystem)); // When the joystick's third button is toggled, run the Pivot PID2 Command
    new JoystickButton(joystick1, 4).onTrue(new PivotHighCmd(p_subsystem)); // When the joystick's fourth button is toggled, run the Pivot PID3 Command
    new JoystickButton(joystick1, 5).onTrue(new PivotPID123Cmd(p_subsystem)); // When the joystick's fifth button is toggled, run the Pivot PID123 Command
    new JoystickButton(joystick1, 6).onTrue(new ResetEncoder(p_subsystem)); // When the joystick's sixth button is toggled, reset the encoders
    new JoystickButton(joystick1, 7).toggleOnTrue(new PivotArmJoystickCmd(p_subsystem, () -> joystick1.getY())); // While the joystick's seventh button is pressed, drive the motors using the joystick
    //new JoystickButton(joystick1, 7).whileFalse(new PivotArmPIDCmd(p_subsystem)); // When the joystick's eighth button is released, lock the PID based on the encoder
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
