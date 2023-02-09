package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class LockPIDCmd extends CommandBase{
    private PivotArmSubsystem p_subsystem;
    private final DoubleSupplier joystickspeed;
    private double speed;

    public LockPIDCmd(PivotArmSubsystem p_subs, DoubleSupplier joystickspeed){
        p_subsystem = p_subs;
        this.joystickspeed = joystickspeed;
        addRequirements(p_subs);
    }

    @Override
    public void initialize(){
    
    }

    @Override
    public void execute(){
        new PivotArmJoystickCmd(p_subsystem, joystickspeed);
        speed = joystickspeed.getAsDouble();
        
    }

    @Override
    public void end(boolean interrupted){
        p_subsystem.lockPID();
    }

    @Override
    public boolean isFinished(){
        return speed == 0;
    }
}
