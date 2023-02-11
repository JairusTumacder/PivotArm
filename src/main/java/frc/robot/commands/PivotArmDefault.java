package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class PivotArmDefault extends CommandBase { // Pivot Arm Default Command

  private final PivotArmSubsystem subs;
  private int lastEncoder;
  private int setPoint;

  public PivotArmDefault(PivotArmSubsystem subs) { // Pivot Arm Default Command Constructor
    this.subs = subs;
    addRequirements(subs);
  }

  @Override
  public void initialize() { // Sets lastEncoder to the current encoder value and setpoint is equal to the last encoder
    SmartDashboard.putNumber("Lock at:", lastEncoder);
    lastEncoder = subs.getEncoder();
    setPoint = lastEncoder;
  }

  @Override
  public void execute() { // Sets the PID to the setPoint
    subs.pivotArmPID(subs.pidOutput(SmartDashboard.getNumber("Lock at: ", lastEncoder)));
  }

  @Override
  public void end(boolean interrupted) { // Runs this code when the code ends
  }

  @Override
  public boolean isFinished() { // Returns true when the code is finished
    return false;
  }
}
