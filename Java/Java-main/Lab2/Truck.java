package Lab2;
public class Truck extends Car
{
    public Integer weight;
    public Integer height;
    
    public Truck(Integer weight, Integer height, String mark, Integer maxSpeed, Radio radio) {
        super(mark, maxSpeed, radio);
        this.weight = weight;
        this.height = height;
    }
}