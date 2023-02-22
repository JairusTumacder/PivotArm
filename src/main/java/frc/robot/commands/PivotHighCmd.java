
package frc.robot.commands;

import frc.robot.subsystems.PivotArmSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PivotHighCmd extends CommandBase{
    private PivotArmSubsystem p_subsystem; 

    public PivotHighCmd(PivotArmSubsystem p_subs){
        p_subsystem = p_subs;
        addRequirements(p_subs);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){ // Moves the arm to encoder counts: High Position
        SmartDashboard.putNumber("Pivot Encoder:", p_subsystem.getEncoder());
        p_subsystem.pivotArmPID(0);
   
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
