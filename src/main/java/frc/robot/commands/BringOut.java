package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class BringOut extends CommandBase{
    private LimitSwitch limit;

    public BringOut(LimitSwitch lswitch){ // Add two Elevator Command
        limit = lswitch;
        addRequirements();
    }
    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        limit.andThen(limit).andThen(limit); // Add two Elevator Command
    }
    @Override
    public void end(boolean interrupted){

    }
    @Override
    public boolean isFinished(){
        return false;
    }
}
