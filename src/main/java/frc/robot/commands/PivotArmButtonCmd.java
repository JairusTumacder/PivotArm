package frc.robot.commands;

import frc.robot.subsystems.PivotArmSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PivotArmButtonCmd extends CommandBase{
    private PivotArmSubsystem p_subsystem;
    
    public PivotArmButtonCmd(PivotArmSubsystem p_subs){
        p_subsystem = p_subs;
        addRequirements(p_subs);
    }

    @Override
    public void initialize(){
   
    }

    @Override
    public void execute(){ // Moves the arm to a certain encoder count
        SmartDashboard.putNumber("Pivot Encoder:", p_subsystem.getEncoder());
        if(p_subsystem.getEncoder() < 0){
            p_subsystem.pivotArm(0.2);
        }
        else if(p_subsystem.getEncoder() > 0){
            p_subsystem.pivotArm(-0.2);
        }
        else{
            //p_subsystem.pivotStop();
        }
   
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){ // Runs the end command when the encoder is greater than
        if(p_subsystem.getEncoder() > 0){ 
            return true;
        } 
        else{ 
            return false;
        }
    }
}
