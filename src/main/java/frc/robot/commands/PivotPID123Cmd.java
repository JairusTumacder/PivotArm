package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class PivotPID123Cmd extends CommandBase{ // PID123 Command 
    private PivotArmSubsystem p_subsystem;

    public PivotPID123Cmd(PivotArmSubsystem p_subs){ // PID123 Command Constructor
        p_subsystem = p_subs;
        addRequirements(p_subs);
    }
    @Override
    public void initialize(){ // Runs the code when the command starts

    }
    @Override
    public void execute(){ // Runs after the command starts
        SmartDashboard.putNumber("Pivot Encoder: ", p_subsystem.getEncoder());
        p_subsystem.limitPress();
        p_subsystem.pivotArmPID(2000);
        p_subsystem.limitPress();
        p_subsystem.pivotArmPID(4000);
    }
    @Override
    public void end(boolean interrupted){ // Runs when the command ends

    }
    @Override
    public boolean isFinished(){ // The command finishes when this returns false
        return false;
    }
}
