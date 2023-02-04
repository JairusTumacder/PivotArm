package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class PivotArmJoystickCmd extends CommandBase{
    private PivotArmSubsystem p_subsystem;
    private final DoubleSupplier speed;

    public PivotArmJoystickCmd(PivotArmSubsystem p_subs, DoubleSupplier speed){ // Command Constructor
        p_subsystem = p_subs;
        this.speed = speed;
        addRequirements(p_subs);
    }

    @Override
    public void initialize(){ // Resets the TalonSRX Encoder
    }

    @Override
    public void execute(){ // Executes and sets the pivot up based on its speed
        p_subsystem.pivotUp(speed);
   
    }

    @Override
    public void end(boolean interrupted){ // Runs code when ends

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
