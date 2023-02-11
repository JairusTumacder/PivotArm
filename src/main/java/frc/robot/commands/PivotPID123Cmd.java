package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class PivotPID123Cmd extends CommandBase{ // PID123 Command 
    private PivotArmSubsystem p_subsystem;
    private int steps = 0;

    public PivotPID123Cmd(PivotArmSubsystem p_subs){ // PID123 Command Constructor
        p_subsystem = p_subs;
        addRequirements(p_subs);
    }
    @Override
    public void initialize(){ // Runs the code when the command starts
        p_subsystem.limitPress();
        steps = 0;
    }
    @Override
    public void execute(){ // Runs after the command starts
        SmartDashboard.putNumber("Pivot Encoder: ", p_subsystem.getEncoder());
        switch(steps){
            case 0: 
            p_subsystem.pivotArmPID(65);
            steps++;
            break;

            case 1:
            p_subsystem.limitPress();
            steps++;
            break;

            case 2: 
            p_subsystem.pivotArmPID(130);
            steps++;
            break;
        }
    }
    @Override
    public void end(boolean interrupted){ // Runs when the command ends
        p_subsystem.limitPress();
    }
    @Override
    public boolean isFinished(){ // The command finishes when this returns false
        return steps == 3;
    }
}
