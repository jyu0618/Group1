import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

import java.util.Random;

public class Group1 extends Bot {


    // The main method starts our bot
    public static void main(String[] args) {
        new Group1().start();
    }

    // Constructor, which loads the bot config file
    Group1() {
        super(BotInfo.fromFile("Group1.json"));
    }

    // Called when a new round is started -> initialize and do some movement
    @Override
    public void run() {
        // Logic

        // Repeat while the bot is running
        while (isRunning()) {
            // Logic
            setTurnRate​(20);
            // turnGunLeft(360);
        }
    }

    // We saw another bot -> fire!
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        // Logic
        fire(1);
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
        // Logic
    }
}
