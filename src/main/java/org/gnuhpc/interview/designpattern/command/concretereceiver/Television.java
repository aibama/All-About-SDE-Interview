package org.gnuhpc.interview.designpattern.command.concretereceiver;

import org.gnuhpc.interview.designpattern.command.receiver.ElectronicDevice;

public class Television implements ElectronicDevice {
    //保存的状态
    private int volume = 0;
    private boolean isOn = false;

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void setOn(boolean flag) {
        isOn = flag;
    }

    public void on() {
        setOn(true);
        System.out.println("TV is on");
    }

    public void off() {
        setOn(false);
        System.out.println("TV is off");
    }

    public void volumeUp() {
        volume++;
        System.out.println("TV Volume is at: " + volume);
    }

    public void volumeDown() {
        volume--;
        System.out.println("TV Volume is at: " + volume);
    }
}