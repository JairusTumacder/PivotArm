package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PivotArmSubsystem extends SubsystemBase{
    private final TalonSRX right = new TalonSRX(2);
    private final PIDController pid = new PIDController(0, 0, 0);
    private final TalonEncoder rEnc;
    private double setpoint;

    public PivotArmSubsystem(TalonEncoder enc){
        rEnc = enc;
        pid.setTolerance(5);
    }

    ////////////////////////
    //  Encoder Methods  //
    //////////////////////
    public double getEncoder(){
       return rEnc.get();
    }

    public void resetEncoder(){
        rEnc.reset();
    }

    ////////////////////////////////
    //  Set Pivot Speed Methods  //
    //////////////////////////////
    public void pivotUp(double speed){
        right.set(ControlMode.PercentOutput, speed);
    }

    public void pivotDown(double speed){
        right.set(ControlMode.PercentOutput, -speed);
    }

    /////////////////////////
    // Pivot PID Methods  //
    /////////////////////// 
    public void compareErrors(){
        double before = pid.getPositionError();
        double after = pid.getPositionError();
        if(before > 0 && after < 0){
            pid.reset();
        }
        else if(before < 0 && after > 0){
            pid.reset();
        }
        else{
            pid.calculate(getEncoder(), setpoint);
        }
    }

    public double calcP(double setpoint){
        double error = pid.calculate(getEncoder(), setpoint);
        if(error > 1){
            return 1;
        }
        else if(error < -1){
            return -1;
        }
        else{
            return error;
        }
    }

    public void pivotArmPID(){
        right.set(ControlMode.PercentOutput, calcP(setpoint));
    }

    ///////////////////////
    //  Printing Method //
    /////////////////////
    @Override
    public void periodic(){
        SmartDashboard.putNumber("Pivot Arm Encoder: ", rEnc.get());
    }


}
