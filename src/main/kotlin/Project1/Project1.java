package Project1;

import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

import java.util.ArrayList;
import java.util.HashMap;

import Project1.cannon.Cannon;
import Project1.movement.Treads;
import Project1.radar.Radar;

public class Project1 extends Bot {
    ArrayList<Integer> enemyList = new ArrayList<Integer>();
    HashMap<Integer, EnemyInfo> enemyInfoList = new HashMap<Integer, EnemyInfo>();
    protected static Cannon cannon;
    protected static Treads treads;
    protected static Radar radar;

    // The main method starts our bot
    public static void main(String[] args) {
        new Project1().start();
    }

    // Constructor, which loads the bot config file
    Project1() {
        super(BotInfo.fromFile("Project1.json"));
    }

    // Called when a new round is started -> initialize and do some movement
    @Override
    public void run() {
        // Logic
        setAdjustRadarForGunTurn(false);
        setAdjustRadarForBodyTurn(false);
        setAdjustGunForBodyTurn(false);
        cannon = new Cannon(this);
        treads = new Treads(this);
        radar = new Radar(this);

        while (isRunning()) {
            radar.run();
            cannon.run();
            treads.run();
        }
    }

    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        // Logic
        treads.onScannedBot(e);
        radar.onScannedBot(e);
        cannon.onScannedBot(e);
    }

    @Override
    public void onBotDeath(BotDeathEvent e) {
        // Removes dead enemy from list
        super.onBotDeath(e);
        enemyList.remove(enemyList.indexOf(e.getVictimId()));
        treads.onBotDeath(e);
        radar.onBotDeath(e);
        cannon.onBotDeath(e);
    }

    @Override
    public void onHitBot(HitBotEvent e) {
        // Logic
        int enemyId = e.getVictimId();
        if (!(enemyList.contains(enemyId))) {
            enemyList.add(enemyId);
            EnemyInfo enemyInfo = new EnemyInfo(enemyId);
            enemyInfoList.put(enemyId, enemyInfo);
        }
        EnemyInfo enemy = enemyInfoList.get(enemyId);
        enemy.updateInfo(e.getX(), e.getY(), e.getEnergy());

        treads.onHitBot(e);
        radar.onHitBot(e);
        cannon.onHitBot(e);
    }

    // We were hit by a bullet -> turn perpendicular to the bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Logic
        treads.onHitByBullet(e);
        radar.onHitByBullet(e);
        cannon.onHitByBullet(e);
    }

    // Override the event handler for hitting a wall
    @Override
    public void onHitWall(HitWallEvent e) {
        // Logic
        treads.onHitWall(e);
        radar.onHitWall(e);
        cannon.onHitWall(e);
    }

    public void updateEnemyInfo(EnemyInfo e) {
        int enemyId = e.getId();
        if (!(enemyList.contains(enemyId))) {
            enemyList.add(enemyId);
            EnemyInfo enemyInfo = new EnemyInfo(enemyId);
            enemyInfoList.put(enemyId, enemyInfo);
        }
        EnemyInfo enemy = enemyInfoList.get(enemyId);
        enemy.updateInfo(e.getX(), e.getY(), e.getEnergy(), e.getDirection(), e.getSpeed());
    }

    public EnemyInfo getClosest() {
        EnemyInfo closest = null;
        double closestDistance = 0;
        for (int id: enemyList) {
            EnemyInfo enemy = enemyInfoList.get(id);
            double distance = distanceTo(enemy.x, enemy.y);
            if (distance < closestDistance || closestDistance == 0) {
                closest = enemy;
                closestDistance = distance;
            }  
        }
        return closest;
    }
}
