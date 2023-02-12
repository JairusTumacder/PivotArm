package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;
import java.util.function.DoubleSupplier;

public class PivotArmDefault extends CommandBase { // Pivot Arm Default Command
  private final PivotArmSubsystem subs;
  private int setPoint;
  private int lastEncoder;

  public PivotArmDefault(PivotArmSubsystem subs) { // Pivot Arm Default Command Constructor
    this.subs = subs;
    this.lastEncoder = subs.getEncoder();
    addRequirements(subs);
  }

  @Override
  public void initialize() { // Sets lastEncoder to the current encoder value and setpoint is equal to the last encoder
    SmartDashboard.putNumber("Lock at: ", lastEncoder);
    setPoint = lastEncoder;
  }

  @Override
  public void execute() { // Sets the PID to the setPoint
    subs.lockPIDAtSetpoint(setPoint);
    
  }

  @Override
  public void end(boolean interrupted) { // Runs this code when the code ends
  }

  @Override
  public boolean isFinished() { // Returns true when the code is finished
    return false;
  }
}
