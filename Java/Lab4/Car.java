package Lab4;
public class Car
{
    protected Integer id;
    protected String mark;
    protected Integer maxSpeed;
    protected Radio radio;

    public Car(Integer id, String mark, Integer maxSpeed, Radio radio) {
        this.id = id;
        this.mark = mark;
        this.maxSpeed = maxSpeed;
        this.radio = radio;
    }

    public void Move() {
        System.out.println("Move...");
    }

    public void SetId(Integer id) {
        this.id = id;
    }
    public void SetMark(String newMark) {
        mark = newMark;
    }
    public void SetMaxSpeed(Integer newMaxSpeed) {
        maxSpeed = newMaxSpeed;
    }
    public Integer GetId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return id + " " + mark + " " + maxSpeed + " " + radio.currentStation + " " + radio.status + "\n";
    }
    
}