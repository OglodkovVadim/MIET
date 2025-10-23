package Lab4;
public class Truck extends Car
{
    public Integer weight;
    public Integer height;
    
    public Truck(Integer id, String mark, Integer maxSpeed, Radio radio, 
                 Integer weight, Integer height) {
        super(id, mark, maxSpeed, radio);
        this.weight = weight;
        this.height = height;
    }
    
    public Integer GetId() {
        return this.id;
    }

    public void SetWeight(Integer weigth) {
        this.weight = weigth;
    }

    public void SetHeight(Integer heigth) {
        this.height = heigth;
    }

    @Override
    public String toString() {
        return 
            id + " " 
            + mark + " " 
            + maxSpeed + " " 
            + radio.currentStation + " " 
            + radio.status + " " 
            + weight + " " 
            + height + "\n"; 
    }
}