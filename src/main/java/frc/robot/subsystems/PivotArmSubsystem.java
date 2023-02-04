package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import javax.swing.JPopupMenu.Separator;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.TalonEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PivotArmSubsystem extends SubsystemBase{ // Pivot Arm Subsystem

    //////////////////
   //  Variables  ///
  //////////////////
    private final TalonSRX right = new TalonSRX(4);
    private double kp;
    private double ki;
    private double kd;
    private final PIDController pid = new PIDController(0.0005, 0.05, 0.000006);
    private final TalonEncoder tEnc;
    private double setpoint;


    ////////////////////////////////////////
   //  Pivot Arm Subsystem Constructor  ///
  ////////////////////////////////////////
    public PivotArmSubsystem(TalonEncoder enc){ // Instantiates the Talon Encoder variable
        tEnc = enc;
        pid.setTolerance(5);
    }

    ////////////////////////
   //  Encoder Methods  ///
  ////////////////////////
    public double getEncoder(){ // Return the Encoder Value
       return right.getSensorCollection().getQuadraturePosition();
    }

    public void resetEncoder(){ // Resets the Encoder to a Position of 0
        right.getSensorCollection().setQuadraturePosition(0, 5);
    }

    ////////////////////////////////
   //  Set Pivot Speed Methods  ///
  ////////////////////////////////
    public void pivotUp(DoubleSupplier speed){ // Pivots the arm up based on its speed
        right.set(ControlMode.PercentOutput, speed.getAsDouble());
    }

    public void pivotArm(double speed){ // Pivots the arm based on its speed
        right.set(ControlMode.PercentOutput, speed);
    }

    public void pivotStop(){ // Stops the Pivot Arm Motor 
        right.set(ControlMode.PercentOutput, 0);
    }

    /////////////////////////
   // Pivot PID Methods  ///
  ///////////////////////// 
    public void compareErrors(){ // Resets the Integral Term if it reaches a certain limit
        double before = pid.getPositionError(); 
        double after = pid.getPositionError();
        if(before > 0 && after < 0){ // If the error changes from a positive to a negative, reset the previous error and the I term
            pid.reset();
        }
        else if(before < 0 && after > 0){ // If the error changes from a negative to a positive, reset the previous error and the I term
            pid.reset();
        }
        else{ // If everything else fails, calculate the error
            pid.calculate(getEncoder(), setpoint);
        }
    }

    public double calcP(double setpoint){ // Calculates the value of the Porportional Term by multiplying the error (setpoint - encoder) by the kP constantSets the limit of the error
        double error = pid.calculate(getEncoder(), setpoint);
        if(pid.atSetpoint()){ // If the PID is at the setpoint, return a value of 0
            return 0;
        }
        if(error > 0.5){ // If the error is greater than a limit of 0.2, return a value of 0.2
            return 0.5;
        }
        else if(error < -0.5){ // If the error is less than a limit of -0.2, return a value of -0.2
            return -0.5;
        }
        else{ // If everything else fails, return the error 
            return error;
        }
    }

    public double calcD(double setpoint){ // Calculates the value of the Derivative Term by multiplying the error rate by the kD constant
        double before = pid.getPositionError();
        double after = pid.getPositionError();
        if((after - before) > 0.5){ // If the error rate (error - previous error) is greater than a limit of 0.2, return a value of 0.2
            return 0.5;
        }
        else if((after - before) < -0.5){ // If the error rate is less than a limit of -0.2, return a value of -0.2
            return -0.5;
        }
        else{ // If everything else fails, return the error
            return pid.calculate(getEncoder(), setpoint); 
        }
        
    }

    public void pivotArmPID(double setpoint){ // Outputs the PID speed to the motors
        right.set(ControlMode.PercentOutput, calcP(setpoint));
        compareErrors();
    }
    
    ///////////////////////
   //  Printing Method ///
  ///////////////////////
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
