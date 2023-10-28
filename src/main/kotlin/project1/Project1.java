package project1;

import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Project1 extends Bot {

    int turnDirection = 1; // clockwise (-1) or counterclockwise (1)
    ArrayList<Integer> enemyList = new ArrayList<Integer>();
    HashMap<Integer, EnemyInfo> enemyInfoList = new HashMap<Integer, EnemyInfo>();
    int id = 0;

    // The main method starts our bot
    public static void main(String[] args) {
        new Project1().start();
    }

    // Constructor, which loads the bot config file
    Project1() {
        super(BotInfo.fromFile("Grp1.json"));
    }

    // Called when a new round is started -> initialize and do some movement
    @Override
    public void run() {
        // Logic
    }

    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        // Logic
    }

    @Override
    public void onBotDeath(BotDeathEvent e) {
        // Removes dead enemy from list
        super.onBotDeath(e);
        enemyList.remove(enemyList.indexOf(e.getVictimId()));
    }

    @Override
    public void onHitBot(HitBotEvent e) {
        // Logic
    }

    // We were hit by a bullet -> turn perpendicular to the bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Logic
    }

    // Override the event handler for hitting a wall
    @Override
    public void onHitWall(HitWallEvent e) {
        // Logic
    }
}
