
package frc.robot.commands;

import frc.robot.subsystems.PivotArmSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class PivotPID3Cmd extends CommandBase{
    private PivotArmSubsystem p_subsystem; 

    public PivotPID3Cmd(PivotArmSubsystem p_subs){ // Pivot PID Constructor 
        p_subsystem = p_subs;
        
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){ // Executes and runs the Pivot Arm PID
        SmartDashboard.putNumber("Pivot Encoder:", p_subsystem.getEncoder());
        p_subsystem.pivotArmPID(1000);
   
    }

    @Override
    public void end(boolean interrupted){ // Ends the code when isFinished is true

    }

    @Override
    public boolean isFinished(){ // Returns true when the code is finished
        return false;
    }
}
