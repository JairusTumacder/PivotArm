package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;

public class PivotArmSubsystem extends SubsystemBase{ // Pivot Arm Subsystem

    ///////////////// 
   //  Variables  //
  /////////////////
    private final CANSparkMax canspark = new CANSparkMax(14, MotorType.kBrushless);
    private final DigitalInput limitSwitch = new DigitalInput(2);
    private final RelativeEncoder rEnc;
    private final PIDController pid = new PIDController(0.07, 0, 0);
    private double before;


    /////////////////////////////////////////
   ///  Pivot Arm Subsystem Constructor  ///
  /////////////////////////////////////////
    public PivotArmSubsystem(){ // Pivot Arm Constructor
        canspark.setIdleMode(IdleMode.kBrake);
        canspark.setInverted(true);
        rEnc = canspark.getEncoder();
    }

    /////////////////////////
   ///  Encoder Methods  ///
  /////////////////////////
    public double getEncoder(){ // Method for returning the encoder value of the arm
        return rEnc.getPosition();
    }

    public void resetEncoder(){ // Method to reset the encoder value of the arm
        rEnc.setPosition(0);
    }

    /////////////////////////////////
   ///  Set Pivot Speed Methods  ///
  /////////////////////////////////
    public void pivotUp(DoubleSupplier speed){ // Method for manual control of the arm
        canspark.set(pivotDeadZone(speed.getAsDouble()));
     }

    public void pivotArm(double speed){ // Method for speed based control of the arm
        canspark.set(speed);
    }

    public void pivotStop(){ // Method for stopping the arm
        canspark.set(0);
    }

    public double pivotDeadZone(double speed){ // Method for the deadzone of the arm
        if(Math.abs(speed) < 0.1){
            return 0;
        }
        else{
            return speed;
        }
    }

    public boolean isTucked(){ // Method for returning a limit switch boolean
        return !limitSwitch.get();
    }

    public void tuckIn(){ // Method for tucking the arm in
        if(isTucked()){
            // pivotArmPID(0);
            pivotArm(-0.01);
        }
        else{
            pivotStop();
        }
    }

    public void setIdleMode(){ // Method for setting the idle mode to kCoast
        canspark.setIdleMode(IdleMode.kCoast);
    }
        
    //////////////////////////
   /// Pivot PID Methods  ///
  //////////////////////////
    public void compareErrors(){ // Method for the Integral Term: Reset
        double after = pid.getPositionError();
        if(before > 0 && after < 0){
            pid.reset();
        }
        else if(before < 0 && after > 0){
            pid.reset();
        }
        before = pid.getPositionError(); 
    }

    public double pidOutput(double setpoint){ // Method for the Porportional Term: 
        double error = pid.calculate(getEncoder(), setpoint);
        if(pid.atSetpoint()){
            return 0;
        }
        if(error > 0.01){
            return 0.01;
        }
        else if(error < -0.01){
            return -0.01;
        }
        else{
            return error;
        }
    }

    public void pivotArmPID(double setpoint){ // Method for the output speed using PID
        double e = pid.calculate(setpoint);
        SmartDashboard.putNumber("Error: ", e);
        canspark.set(pidOutput(setpoint));
        compareErrors();
    }
    
    ////////////////////////
   ///  Printing Method ///
  ////////////////////////
  
    public void periodic(){ // 
        SmartDashboard.putNumber("Pivot Arm Encoder: ", getEncoder());
        SmartDashboard.putBoolean("Limit Switch: ", limitSwitch.get());
        if(!isTucked()){
            pivotStop();
        }
    }
}