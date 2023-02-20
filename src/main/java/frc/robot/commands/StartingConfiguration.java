package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.LimitSwitch;

public class StartingConfiguration extends CommandBase{
    private LimitSwitch limit;

    public StartingConfiguration(LimitSwitch lswitch){ // Add Elevator Command
        limit = lswitch;
        addRequirements();
    }
    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        limit.andThen(limit); // Add Elevator Command
    }
    @Override
    public void end(boolean interrupted){

    }
    @Override
    public boolean isFinished(){
        return false;
    }
}
