package org.example;

import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Grp1 extends Bot {

    static String lastTarget;
    static double lastDistance;
    int turnDirection = 1; // clockwise (-1) or counterclockwise (1)
    ArrayList<Integer> enemyList = new ArrayList<Integer>();
    HashMap<Integer, Double> enemyX = new HashMap<Integer, Double>();
    HashMap<Integer, Double> enemyY = new HashMap<Integer, Double>();
    int id = 0;

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
        setAdjustRadarForGunTurn(false);
        setAdjustRadarForBodyTurn(false);
        setAdjustGunForBodyTurn(false);
        

        // Repeat while the bot is running
        while (isRunning()) {
            setTurnRadarLeft(10_000);
            forward(100);
            back(100);
        }
    }

    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        // Logic
        if (!(enemyList.contains(e.getScannedBotId()))) {
            enemyList.add(e.getScannedBotId());
        }
        enemyX.put(e.getScannedBotId(), e.getX());
        enemyY.put(e.getScannedBotId(), e.getY());

        id = getClosest();
        if (id != 0) {
            double closestX = enemyX.get(id);
            double closestY = enemyY.get(id);

            System.out.println(id);
            System.out.println(closestX);
            System.out.println(closestY);

            var bearingFromGun = gunBearingTo(closestX, closestY);
            setTurnGunLeft(bearingFromGun);
                // If it is close enough, fire!
            if (Math.abs(bearingFromGun) <= 3) {
                fire(3);
            }
        }
        // Generates another scan event if we see a bot.
        // We only need to call this if the gun (and therefore radar)
        // are not turning. Otherwise, scan is called automatically.
        if (getRadarTurnRemaining() == 0) {
            setTurnRadarLeft(10_000);
        } 
    }

    @Override
    public void onBotDeath(BotDeathEvent bot) {
        // Removes dead enemy from list
        super.onBotDeath(bot);
        enemyList.remove(enemyList.indexOf(bot.getVictimId()));
    }

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
        stop();
        setTurnLeft(10_000);
        setTurnRadarLeft(10_000);
        turnLeft(180);
        forward(200);
        resume();
        setTurnRadarLeft(10_000);
    }

    private int getClosest() {
        int closest = 0;
        double closestDistance = 0;
        for (int id: enemyList) {
            double distance = distanceTo(enemyX.get(id), enemyY.get(id));
            if (distance < closestDistance || closestDistance == 0) {
                closest = id;
                closestDistance = distance;
            }  
        }
        return closest;
    }
}

/*
 * package org.example;

import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Grp1 extends Bot {

    static String lastTarget;
    static double lastDistance;
    int turnDirection = 1; // clockwise (-1) or counterclockwise (1)
    ArrayList<Integer> enemyList = new ArrayList<Integer>();
    HashMap<Integer, Double> enemyX = new HashMap<Integer, Double>();
    HashMap<Integer, Double> enemyY = new HashMap<Integer, Double>();

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
        setAdjustRadarForGunTurn(false);
        setAdjustRadarForBodyTurn(false);
        setAdjustGunForBodyTurn(false);
        int id = 0;

        // Repeat while the bot is running
        while (isRunning()) {
            setTurnLeft(10_000);
            setMaxSpeed(20);
            setTurnRadarLeft(10_000);
            setForward(10_000);
            id = getClosest();
            if (id != 0) {
                var bearingFromGun = gunBearingTo(enemyX.get(id), enemyY.get(id));
                setTurnGunLeft(bearingFromGun);
                 // If it is close enough, fire!
                if (Math.abs(bearingFromGun) <= 3 && getGunHeat() == 0) {
                    fire(Math.min(3 - Math.abs(bearingFromGun), getEnergy() - .1));
                }
            }
        }
    }

    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        // Logic
        if (!(enemyList.contains(e.getScannedBotId()))) {
            enemyList.add(e.getScannedBotId());
        }
        enemyX.put(e.getScannedBotId(), e.getX());
        enemyY.put(e.getScannedBotId(), e.getX());
    }

    @Override
    public void onHitBot(HitBotEvent e) {
        // Turn gun to the bullet direction
        var direction = directionTo(e.getX(), e.getY());
        var gunBearing = normalizeRelativeAngle(direction - getGunDirection());
        turnGunLeft(gunBearing);

         // If it is close enough, fire!
        if (Math.abs(gunBearing) <= 3 && getGunHeat() == 0) {
            fire(Math.min(3 - Math.abs(gunBearing), getEnergy() - .1));
        }
        if (gunBearing == 0) {
            rescan();
        } 
    }

    // We were hit by a bullet -> turn perpendicular to the bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        var bearing = calcBearing(e.getBullet().getDirection());

        // Turn 90 degrees to the bullet direction based on the bearing
        stop()
        turnLeft(90 - bearing);
        forward(75);
    }

    // Override the event handler for hitting a wall
    @Override
    public void onHitWall(HitWallEvent e) {
        setTurnLeft(10_000);
        turnLeft(180);
        forward(200);
    }

    private int getClosest() {
        int closest = 0;
        double closestDistance = 0;
        for (int id: enemyList) {
            double distance = distanceTo(enemyX.get(id), enemyY.get(id));
            if (distance < closestDistance || closestDistance == 0) {
                closest = id;
                closestDistance = distance;
            }  
        }
        return closest;
    }
}
 */