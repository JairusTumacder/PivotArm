package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TopNode extends CommandBase{
    private HighPosition high;
    private PivotMiddleCmd mid;

    public TopNode(HighPosition hposition, PivotMiddleCmd mposition){ // Add Elevator Command
        high = hposition;
        mid = mposition;
        addRequirements();
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){ // Add Elevator Command
        high.andThen(mid);
    }

    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
