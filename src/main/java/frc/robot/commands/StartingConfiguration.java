package frc.robot.commands;

import javax.xml.stream.events.StartDocument;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PivotArmSubsystem;

public class StartingConfiguration extends CommandBase{
    private LimitSwitch limit;

    public StartingConfiguration(LimitSwitch lswitch){
        limit = lswitch;
        addRequirements();
    }
}
