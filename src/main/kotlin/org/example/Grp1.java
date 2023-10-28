package org.example;

import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Grp1 extends Bot {

    int turnDirection = 1; // clockwise (-1) or counterclockwise (1)
    ArrayList<Integer> enemyList = new ArrayList<Integer>();
    HashMap<Integer, EnemyInfo> enemyInfoList = new HashMap<Integer, EnemyInfo>();
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
        int enemyId = e.getScannedBotId();
        if (!(enemyList.contains(enemyId))) {
            enemyList.add(enemyId);
            EnemyInfo enemyInfo = new EnemyInfo(enemyId);
            enemyInfoList.put(enemyId, enemyInfo);
        }
        EnemyInfo enemy = enemyInfoList.get(enemyId);
        enemy.updateInfo(e.getX(), e.getY(), e.getEnergy(), e.getDirection(), e.getSpeed());

        id = getClosest();
        if (id != 0) {
            EnemyInfo closest = enemyInfoList.get(id);
            double closestX = closest.x;
            double closestY = closest.y;

            /* print closest
            System.out.println(id);
            System.out.println(closestX);
            System.out.println(closestY);
            */

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
    public void onBotDeath(BotDeathEvent e) {
        // Removes dead enemy from list
        super.onBotDeath(e);
        enemyList.remove(enemyList.indexOf(e.getVictimId()));
    }

    @Override
    public void onHitBot(HitBotEvent e) {
        int enemyId = e.getVictimId();
        if (!(enemyList.contains(enemyId))) {
            enemyList.add(enemyId);
            EnemyInfo enemyInfo = new EnemyInfo(enemyId);
            enemyInfoList.put(enemyId, enemyInfo);
        }
        EnemyInfo enemy = enemyInfoList.get(enemyId);
        enemy.updateInfo(e.getX(), e.getY(), e.getEnergy());
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
            EnemyInfo enemy = enemyInfoList.get(id);
            double distance = distanceTo(enemy.x, enemy.y);
            if (distance < closestDistance || closestDistance == 0) {
                closest = id;
                closestDistance = distance;
            }  
        }
        return closest;
    }

    private class EnemyInfo {
        public int id;
        public double x;
        public double y;
        public double e;
        public double d;
        public double s;

        EnemyInfo(int id){
            this.id = id;
        };

        public void updateInfo(double x, double y, double energy){
            this.x = x;
            this.y = y;
            this.e = energy;
        };
        
        public void updateInfo(double x, double y, double energy, double direction, double speed){
            this.x = x;
            this.y = y;
            this.e = energy;
            this.d = direction;
            this.s = speed;
        };
        
    }
}
