package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class PivotPID123Cmd extends CommandBase{
    private PivotArmSubsystem p_subsystem;

    public PivotPID123Cmd(PivotArmSubsystem p_subs){
        p_subsystem = p_subs;
        addRequirements(p_subs);
    }
    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        SmartDashboard.putNumber("Pivot Encoder: ", p_subsystem.getEncoder());
        p_subsystem.limitPress();
        p_subsystem.pivotArmPID(2000);
        p_subsystem.limitPress();
        p_subsystem.pivotArmPID(4000);
    }
    @Override
    public void end(boolean interrupted){

    }
    @Override
    public boolean isFinished(){
        return false;
    }
}
