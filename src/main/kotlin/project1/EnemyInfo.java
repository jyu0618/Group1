package project1;

public class EnemyInfo {
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
