package Lab1;
public class DPS
{
    private Integer limitSpeed;
    private Integer limitWeight;
    private Integer limitHeight;
    
    public DPS(Integer limitSpeed, Integer limitWeight, Integer limitHeight) {
        this.limitSpeed = limitSpeed;
        this.limitWeight = limitWeight;
        this.limitHeight = limitHeight;
    }

    public Boolean Pass(Car car) {
        if (car.maxSpeed > limitSpeed)
            return false;
        return true;
    }

    public Boolean Pass(Truck car) {
        if (car.maxSpeed > limitSpeed)
            return false;
        else if (car.weight > limitWeight)
            return false;
        else if (car.height > limitHeight)
            return false;    
        return true;
    }
}

