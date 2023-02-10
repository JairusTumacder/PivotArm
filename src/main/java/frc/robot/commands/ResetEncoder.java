package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;
import edu.wpi.first.wpilibj.Timer;

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
    public void execute(){ // Executes and resets the encoder
        p_subsystem.resetEncoder();
   
    }

    @Override
    public void end(boolean interrupted){ // Runs this code when it is finished 

    }

    @Override
    public boolean isFinished(){ // Ends the code when isFinished is true 
        return true;
    }
    
}
