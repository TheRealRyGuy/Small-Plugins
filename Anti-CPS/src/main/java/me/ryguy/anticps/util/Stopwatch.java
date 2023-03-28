package me.ryguy.anticps.util;

public class Stopwatch {

    public long startTime;
    public long stopTime;

    public void start() {
        this.startTime = System.nanoTime();
    }
    public void stop() {
        this.stopTime = System.nanoTime();
    }
    public long getTime() {
        return (this.stopTime - this.startTime);
    }

}
