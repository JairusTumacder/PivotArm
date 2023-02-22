package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class PivotStartCmd extends CommandBase{ // Pivot PID Command 
    private PivotArmSubsystem p_subsystem; 
    private Timer timer = new Timer();
    public PivotStartCmd(PivotArmSubsystem p_subs){ // Pivot PID Constructor 
        p_subsystem = p_subs;
        addRequirements(p_subs);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){ // Tucks the arm in
        SmartDashboard.putNumber("Pivot Encoder:", p_subsystem.getEncoder());
        p_subsystem.tuckIn();
    }

    @Override
    public void end(boolean interrupted){ // Ends the code when isFinished is true
        SmartDashboard.putString("isFinished?", "yes");
        timer.reset();
        timer.start();
        while(timer.get() < 2){

        }
        p_subsystem.resetEncoder();
    }

    @Override
    public boolean isFinished(){ // 
        return p_subsystem.isTucked();
    }
}
