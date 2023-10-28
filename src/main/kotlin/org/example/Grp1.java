package org.example;

import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

public class Grp1 extends Bot {


    // The main method starts our bot
    public static void main(String[] args) {
        new Grp1().start();
    }

    // Constructor, which loads the bot config file
    Grp1() {
        super(BotInfo.fromFile("Grp1.json"));
    }

    // Called when a new round is started -> initialize and do some movement
    @Override
    public void run() {
        // Logic

        // Repeat while the bot is running
        while (isRunning()) {
            // Logic
            turnGunLeft(360);
        }
    }

    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        // Logic
        var bearingFromGun = gunBearingTo(e.getX(), e.getY());
        var distance = distanceTo(e.getX(), e.getY());

        // Turn the gun toward the scanned bot
        turnGunLeft(bearingFromGun);

        // If it is close enough, fire!
        if (Math.abs(bearingFromGun) <= 3 && getGunHeat() == 0) {
            fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
        }

        // Generates another scan event if we see a bot.
        // We only need to call this if the gun (and therefore radar)
        // are not turning. Otherwise, scan is called automatically.
        if (bearingFromGun == 0) {
            rescan();
        } 
        
        if(distance<200)
        {
           fire(3.5);
        }
        else if(distance<500)
        {
           fire(2.5);
        }
        else if(distance<800)
        {
           fire(1.5);
        }
        else
        {
           fire(0.5);
        }
    }

    // We have hit another bot -> turn to face bot, fire hard, and ram it again!
    @Override
    public void onHitBot(HitBotEvent e) {

    }

    // We were hit by a bullet -> turn perpendicular to the bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Logic
    }

    // Override the event handler for hitting a wall
    @Override
    public void onHitWall(HitWallEvent e) {
            turnLeft(180);
    }
}
