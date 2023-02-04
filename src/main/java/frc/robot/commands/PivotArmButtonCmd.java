package frc.robot.commands;

import frc.robot.subsystems.PivotArmSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PivotArmButtonCmd extends CommandBase{ // Pivot Arm Button Command
    private PivotArmSubsystem p_subsystem;
    
    public PivotArmButtonCmd(PivotArmSubsystem p_subs){ // Pivot Arm Button Constructor
        p_subsystem = p_subs;
        addRequirements(p_subs);
    }

    @Override
    public void initialize(){ // Runs the code when the command 
   
    }

    @Override
    public void execute(){ // Executes the code and drives the TalonSRX motor to the desired encoder of 200
        SmartDashboard.putNumber("Pivot Encoder:", p_subsystem.getEncoder());
        if(p_subsystem.getEncoder() < 3000){
            p_subsystem.pivotDown(0.2);
        }
        else if(p_subsystem.getEncoder() > 3000){
            p_subsystem.pivotDown(-0.2);
        }
        else{
            //p_subsystem.pivotStop();
        }
   
    }

    @Override
    public void end(boolean interrupted){ // Ends the code when the code is finished 

    }

    @Override
    public boolean isFinished(){
        if(p_subsystem.getEncoder() > 3000){
            return true;
        } 
        else{
            return false;
        }
    }
}
