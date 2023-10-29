package Project1;

public class EnemyInfo {
    public int id;
    public double x;
    public double y;
    public double e;
    public double d;
    public double s;

    public EnemyInfo(int id){
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

    public int getId() {
        return id;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getEnergy() {
        return e;
    }
    
    public double getDirection() {
        return d;
    }
    
    public double getSpeed() {
        return s;
    }
}
