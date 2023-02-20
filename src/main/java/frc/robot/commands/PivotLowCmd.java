package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class PivotLowCmd extends CommandBase{
    private PivotArmSubsystem p_subs;

    public PivotLowCmd(PivotArmSubsystem subs){
        p_subs = subs;
        addRequirements(subs);
    }

}
