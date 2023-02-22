package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class ResetEncoder extends CommandBase {
    private PivotArmSubsystem p_subsystem;

    public ResetEncoder(PivotArmSubsystem p_subs){ // Reset Encoder Command
        p_subsystem = p_subs;
        addRequirements(p_subs);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
   
    }

    @Override
    public void end(boolean interrupted){ // Resets the Encoder
        p_subsystem.resetEncoder();
    }

    @Override
    public boolean isFinished(){ // Runs the end method
        return true;
    }
    
}
