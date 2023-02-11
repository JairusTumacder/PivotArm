package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class PivotArmDefault extends CommandBase {

  private final PivotArmSubsystem subs;
  private int lastEncoder;
  private int setPoint;

  public PivotArmDefault(PivotArmSubsystem subs) {
    this.subs = subs;
    lastEncoder = subs.getEncoder();
    addRequirements(subs);
  }

  @Override
  public void initialize() {
    setPoint = lastEncoder;
  }

  @Override
  public void execute() {
    subs.lockPIDatSetpoint(lastEncoder);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
