package Lab4;

import java.util.Scanner;

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

    public void SetLimitSpeed(Integer newlimitSpeed) {
        limitSpeed = newlimitSpeed;
    }

    public void SetLimitWeight(Integer newlimitWeight) {
        limitWeight = newlimitWeight;
    }

    public void SetLimitHeight(Integer newlimitHeight) {
        limitHeight = newlimitHeight;
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

    public void UpdateSpeedLimit() {
		Scanner in = new Scanner(System.in);
		System.out.print("\033[H\033[2J");  
		System.out.print("Enter limit speed -> ");

		limitSpeed = in.nextInt();
	}

    public void UpdateWeightLimit() {
		Scanner in = new Scanner(System.in);
		System.out.print("\033[H\033[2J");  
		System.out.print("Enter limit weight -> ");
		limitWeight = in.nextInt();
	}

	public void UpdateHeightLimit() {
		Scanner in = new Scanner(System.in);
		System.out.print("\033[H\033[2J");  
		System.out.print("Enter limit height -> ");
		limitHeight = in.nextInt();
	}

}

