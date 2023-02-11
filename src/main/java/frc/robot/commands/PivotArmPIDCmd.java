package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PivotArmPIDCmd extends CommandBase{
    private PivotArmSubsystem p_subsystem;

    public PivotArmPIDCmd(PivotArmSubsystem p_subs){ // Pivot PID Constructor 
        p_subsystem = p_subs;
        addRequirements(p_subs);
    }

    @Override
    public void initialize(){
        p_subsystem.pivotStop();
    }

    @Override
    public void execute(){ // Executes and runs the Pivot Arm PID
        SmartDashboard.putNumber("Pivot Encoder:", p_subsystem.getEncoder());
        p_subsystem.lockPID();
   
    }

    @Override
    public void end(boolean interrupted){ // Ends the code when isFinished is true

    }

    @Override
    public boolean isFinished(){ // Returns true when the code is finished
        return false;
    }
}
