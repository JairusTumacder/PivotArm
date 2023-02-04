package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.TalonEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PivotArmSubsystem extends SubsystemBase{ // Pivot Arm Subsystem

    //////////////////
    //  Variables  //
    ////////////////
    private final TalonSRX right = new TalonSRX(4);
    private double kp;
    private double ki;
    private double kd;
    private final PIDController pid = new PIDController(kp, ki, kd);
    private final TalonEncoder tEnc;
    private double setpoint;


    ////////////////////////////////////////
    //  Pivot Arm Subsystem Constructor  //
    //////////////////////////////////////
    public PivotArmSubsystem(TalonEncoder enc){ // Instantiates the Talon Encoder variable
        tEnc = enc;
        pid.setTolerance(5);
    }

    ////////////////////////
    //  Encoder Methods  //
    //////////////////////
    public double getEncoder(){ // Return the Encoder
       return right.getSensorCollection().getQuadraturePosition();
    }

    public void resetEncoder(){ // Resets the Encoder to a Position of 0
        right.getSensorCollection().setQuadraturePosition(0, 5);
    }

    ////////////////////////////////
    //  Set Pivot Speed Methods  //
    //////////////////////////////
    public void pivotUp(DoubleSupplier speed){ // Pivots the arm up based on its speed
        right.set(ControlMode.PercentOutput, speed.getAsDouble());
    }

    public void pivotDown(double speed){ // Pivots the arm down based on its speed
        right.set(ControlMode.PercentOutput, speed);
    }

    public void pivotStop(){
        right.set(ControlMode.PercentOutput, 0);
    }

    public double pivotDeadZone(double speed){ // Sets a deadzone on the joystick and arm so when the speed is less than a certain value it will stop the motors
        if(speed < 0.1)
            return 0;
        else{
            return speed;
        }
    }

    /////////////////////////
    // Pivot PID Methods  //
    /////////////////////// 
    public void compareErrors(){ // Resets the Integral Term if it reaches a certain limit
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

    public double calcP(double setpoint){ // Calculates the value of the Porportional Term by multiplying the error (setpoint - encoder) by the kP constantSets the limit of the error
        double error = pid.getPositionError();  // Sets the limits of the setpoint
        if(pid.atSetpoint()){
            return 0;
        }
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

    public double calcD(double setpoint){ // Calculates the value of the Derivative Term by multiplying the error rate by the kD constant
        double error = pid.getPositionError();
        if(error > setpoint){
            return error;
        }
        else if(error < setpoint){
            return error;
        }
        else{
            return pid.calculate(kd, setpoint); 
        }
        
    }

    public void pivotArmPID(double setpoint){ // Outputs the PID speed to the motors
        right.set(ControlMode.PercentOutput, calcP(setpoint));
        compareErrors();
    }
    
    ///////////////////////
    //  Printing Method //
    /////////////////////
    @Override
    public void periodic(){ // Prints and edits kp, ki, and kd so you do not have to redeploy and edit code
        SmartDashboard.putNumber("Pivot Arm Encoder: ", getEncoder());
        kp = SmartDashboard.getNumber("kP", 0);
        SmartDashboard.putNumber("kP", kp);
        ki = SmartDashboard.getNumber("kI", 0);
        SmartDashboard.putNumber("kI", ki);
        kd = SmartDashboard.getNumber("kD", 0);
        SmartDashboard.putNumber("kD", kd);
    }


}
