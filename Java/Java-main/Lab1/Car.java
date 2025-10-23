package Lab1;
public class Car
{
    protected String mark;
    protected Integer maxSpeed;
    protected Radio radio;
    
    public Car(String mark, Integer maxSpeed, Radio radio) {
        this.mark = mark;
        this.maxSpeed = maxSpeed;
        this.radio = radio;
    }
    public void Move() {
        System.out.println("Move...");
    }
}