package Project1.cannon;

import Project1.EnemyInfo;
import Project1.Project1;
import dev.robocode.tankroyale.botapi.events.*;

public class Cannon {
    private Project1 body;
    public Cannon(Project1 body) {
        this.body = body;
    }

    // what we do each turn
    public void run() {
    }

    // found bot
    public void onScannedBot(ScannedBotEvent e) {
        // Logic
        EnemyInfo closest = body.getClosest();
        if (closest != null) {
            var bearingFromGun = body.gunBearingTo(closest.getX(), closest.getY());
            body.setTurnGunLeft(bearingFromGun);
                // If it is close enough, fire!
            if (Math.abs(bearingFromGun) <= 3) {
                body.fire(3);
            }
        }
    }

    // bot died
    public void onBotDeath(BotDeathEvent e) {
        // Logic
    }

    // hit bot
    public void onHitBot(HitBotEvent e) {
        // Logic
    }

    // get hit by bullet
    public void onHitByBullet(HitByBulletEvent e) {
        // Logic
    }

    // hit wall
    public void onHitWall(HitWallEvent e) {
        // Logic
    }
}
