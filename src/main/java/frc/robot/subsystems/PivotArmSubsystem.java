package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.subsystems.TalonEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.DigitalInput;

public class PivotArmSubsystem extends SubsystemBase{ // Pivot Arm Subsystem

    ///////////////// 
   //  Variables  //
  /////////////////
    private final TalonSRX right = new TalonSRX(4);
    private final DigitalInput limitSwitch = new DigitalInput(3);
    private double kp;
    private double ki; 
    private double kd;
    private final PIDController pid = new PIDController(0.0005, 0.00001, 0.000007);
    private final TalonEncoder tEnc;
    private double before;


    /////////////////////////////////////////
   ///  Pivot Arm Subsystem Constructor  ///
  /////////////////////////////////////////
    public PivotArmSubsystem(TalonEncoder enc){ // Instantiates the Talon Encoder variable and sets the tolerance for the PID
        tEnc = enc;
        pid.setTolerance(5);
    }

    /////////////////////////
   ///  Encoder Methods  ///
  /////////////////////////
    public double getEncoder(){ // Return the Encoder Value
       return right.getSensorCollection().getQuadraturePosition();
    }

    public void resetEncoder(){ // Resets the Encoder to a Position of 0
        right.getSensorCollection().setQuadraturePosition(0, 5);
    }

    /////////////////////////////////
   ///  Set Pivot Speed Methods  ///
  /////////////////////////////////
    public void pivotUp(DoubleSupplier speed){ // Pivots the arm up based on its speed
        right.set(ControlMode.PercentOutput, pivotDeadZone(speed.getAsDouble()));
    }

    public void pivotArm(double speed){ // Pivots the arm based on its speed
        right.set(ControlMode.PercentOutput, speed);
    }

    public void pivotStop(){ // Stops the Pivot Arm Motor 
        right.set(ControlMode.PercentOutput, 0);
    }

    public double pivotDeadZone(double speed){ // Sets a deadzone for the Pivot Arm when moved by a joystick
        if(Math.abs(speed) < 0.1){ // If the absolute value of the speed is less than 0.1, return a speed of 0
            return 0;
        }
        else{ // If everything else fails, return the speed
            return speed;
        }
    }

    public void limitPress(){ // Returns whether the limit is pressed or not
        if(!limitSwitch.get()){ // If the limit switch is not pressed, runs the PID to 0
            right.set(ControlMode.PercentOutput, pidOutput(0));
        }
        else{ // If the limit switch is pressed, stop the pivot arm and reset the encoders
            pivotStop();
            resetEncoder();
        }
    }
                
    public boolean limitHit(){ // Returns if the limit switch is pressed or not
        return limitSwitch.get();
    }

    public void lockPID(){ // Locks the PID based on the encoder value
        pidOutput(getEncoder());
    }

    //////////////////////////
   /// Pivot PID Methods  ///
  //////////////////////////
    public void compareErrors(){ // Resets the Integral Term if it reaches a certain limit
        double after = pid.getPositionError();
        if(before > 0 && after < 0){ // If the error changes from a positive to a negative, reset the previous error and the I term
            pid.reset();
        }
        else if(before < 0 && after > 0){ // If the error changes from a negative to a positive, reset the previous error and the I term
            pid.reset();
        }
        before = pid.getPositionError(); 
    }

    public double pidOutput(double setpoint){ // Calculates the value of the Porportional Term by multiplying the error (setpoint - encoder) by the kP constantSets the limit of the error
        double error = pid.calculate(getEncoder(), setpoint);
        if(pid.atSetpoint()){ // If the PID is at the setpoint, return a value of 0
            return 0;
        }
        if(error > 0.5){ // If the error is greater than a limit of 0.5, return a value of 0.5
            return 0.5;
        }
        else if(error < -0.5){ // If the error is less than a limit of -0.5, return a value of -0.5
            return -0.5;
        }
        else{ // If everything else fails, return the error 
            return error;
        }
    }

    public void pivotArmPID(double setpoint){ // Outputs the PID speed to the motors
        double e = pid.calculate(setpoint);
        SmartDashboard.putNumber("Error: ", e);
        right.set(ControlMode.PercentOutput, pidOutput(setpoint));
        compareErrors();
    }
    
    ////////////////////////
   ///  Printing Method ///
  ////////////////////////
    @Override
    public void periodic(){ // Prints and edits kp, ki, and kd so you do not have to redeploy and edit code
        SmartDashboard.putNumber("Pivot Arm Encoder: ", getEncoder()); // Prints out the encoder values
        SmartDashboard.putBoolean("Limit Switch: ", limitSwitch.get()); // Prints out a boolean, returning true or false if the limit switch is pressed or not
        kp = SmartDashboard.getNumber("kP", 0);
        SmartDashboard.putNumber("kP", kp);
        ki = SmartDashboard.getNumber("kI", 0);
        SmartDashboard.putNumber("kI", ki);
        kd = SmartDashboard.getNumber("kD", 0);
        SmartDashboard.putNumber("kD", kd);
    }
}