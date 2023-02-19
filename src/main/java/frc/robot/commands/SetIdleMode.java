package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class SetIdleMode extends CommandBase{
    private PivotArmSubsystem p_subs;

    public SetIdleMode(PivotArmSubsystem subs){
        p_subs = subs;
        addRequirements(subs);
    }
    @Override
    public void initialize(){

    }
}
