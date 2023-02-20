package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;

public class PivotArmSubsystem extends SubsystemBase{ // Pivot Arm Subsystem

    ///////////////// 
   //  Variables  //
  /////////////////
    //private final WPI_TalonSRX talon =  new WPI_TalonSRX(5);
    private final CANSparkMax canspark = new CANSparkMax(14, MotorType.kBrushless);
    private final DigitalInput limitSwitch = new DigitalInput(2);
    //private final DigitalInput encoder = new DigitalInput(5);
    //private final SingleChannelEncoder sEnc = new SingleChannelEncoder(talon, encoder);
    private final RelativeEncoder rEnc;
    private final PIDController pid = new PIDController(0.07, 0, 0);
    private double before;
    //private double lastEncoder = getEncoder();


    /////////////////////////////////////////
   ///  Pivot Arm Subsystem Constructor  ///
  /////////////////////////////////////////
    public PivotArmSubsystem(){ // Instantiates the Talon Encoder variable and sets the tolerance for the PID
        canspark.setIdleMode(IdleMode.kBrake);
        canspark.setInverted(true);
        rEnc = canspark.getEncoder();
    }

    /////////////////////////
   ///  Encoder Methods  ///
  /////////////////////////
    public double getEncoder(){ // Return the Encoder Value
        //return right.getSensorCollection().getQuadraturePosition();
        //return sEnc.get();
        return rEnc.getPosition();
    }

    public void resetEncoder(){ // Resets the Encoder to a Position of 0
        //right.getSensorCollection().setQuadraturePosition(0, 5);
        //sEnc.reset();
        rEnc.setPosition(0);
    }

    /////////////////////////////////
   ///  Set Pivot Speed Methods  ///
  /////////////////////////////////
    public void pivotUp(DoubleSupplier speed){ // Pivots the arm up based on its speed
        //right.set(ControlMode.PercentOutput, pivotDeadZone(speed.getAsDouble()));
        //talon.set(pivotDeadZone(speed.getAsDouble()));
        canspark.set(pivotDeadZone(speed.getAsDouble()));
     }

    public void pivotArm(double speed){ // Pivots the arm based on its speed
        //right.set(ControlMode.PercentOutput, speed);
        //talon.set(speed);
        canspark.set(speed);
    }

    public void pivotStop(){ // Stops the Pivot Arm Motor 
        //right.set(ControlMode.PercentOutput, 0);
        //talon.stopMotor();
        canspark.stopMotor();
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
            //right.set(ControlMode.PercentOutput, pidOutput(0));
            //talon.set(pidOutput(0));
            canspark.set(pidOutput(0));
            compareErrors();
        }
        else{ // If the limit switch is pressed, stop the pivot arm and reset the encoders
            pivotStop();
            resetEncoder();
        }
    }
                
    public boolean limitHit(){ // Returns if the limit switch is pressed or not
        return limitSwitch.get();
    }

    public void lockPIDAtSetpoint(double setPoint){
        while(canspark.get() <= 0){
            setPoint = getEncoder();
        }
        pivotArmPID(setPoint);
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
        //right.set(ControlMode.PercentOutput, pidOutput(setpoint));
        //talon.set(pidOutput(setpoint));
        canspark.set(pidOutput(setpoint));
        compareErrors();
    }
    
    ////////////////////////
   ///  Printing Method ///
  ////////////////////////
  
    public void periodic(){
        SmartDashboard.putNumber("Pivot Arm Encoder: ", getEncoder()); // Prints out the encoder values
        SmartDashboard.putBoolean("Limit Switch: ", limitSwitch.get()); // Prints if the limit switch is pressed or not
        //SmartDashboard.putNumber("Lock at:", lastEncoder); // Prints out the last encoder value
    }
}