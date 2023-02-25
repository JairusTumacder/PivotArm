package frc.robot.subsystems;

import com.ctre.phoenix.led.RainbowAnimation;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LED extends SubsystemBase{
    private AddressableLED led;
    private AddressableLEDBuffer ledbuffer;
    private int rainbowFirstPixelHue;

    public LED(){
        led = new AddressableLED(0);
        ledbuffer = new AddressableLEDBuffer(60);
        led.setLength(ledbuffer.getLength());
        for (var i = 0; i < ledbuffer.getLength(); i++){
            ledbuffer.setHSV(i, 0, 100, 100);
        }
        led.setData(ledbuffer);
        led.start();
    }

    @Override
    public void periodic(){

    }
}
