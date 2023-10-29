package Project1.movement;

import Project1.Project1;
import dev.robocode.tankroyale.botapi.events.*;

public class Treads {
    private Project1 body;

    public Treads(Project1 body) {
        this.body = body;
    }
    
    // what we do each turn
    public void run() {
        body.forward(100);
        body.back(100);
    }

    // found bot
    public void onScannedBot(ScannedBotEvent e) {
        // Logic
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
        body.stop();
        body.setTurnLeft(10_000);
        body.setTurnRadarLeft(10_000);
        body.turnLeft(180);
        body.forward(200);
        body.resume();
    }
}
