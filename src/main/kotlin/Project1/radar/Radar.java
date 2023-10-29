package Project1.radar;

import Project1.EnemyInfo;
import Project1.Project1;
import dev.robocode.tankroyale.botapi.events.*;

public class Radar {
    private Project1 body;
    // private boolean lockOn = false;

    public Radar(Project1 body) {
        this.body = body;
    }
    

    // what we do each turn
    public void run() {
        body.setTurnRadarLeft(10_000);
    }

    // found bot
    public void onScannedBot(ScannedBotEvent e) {
        // Get Id
        int enemyId = e.getScannedBotId();
        EnemyInfo enemyInfo = new EnemyInfo(enemyId);
        enemyInfo.updateInfo(e.getX(), e.getY(), e.getEnergy(), e.getDirection(), e.getSpeed());
        body.updateEnemyInfo(enemyInfo);

        if (body.getRadarTurnRemaining() == 0) {
            body.setTurnRadarLeft(10_000);
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
        body.setTurnRadarLeft(10_000);
    }
}
