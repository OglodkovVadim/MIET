package Lab2;
public class Main
{
	public static void main(String[] args) {
		Car car = new Car("Nissan", 200, new Radio("FM", true));
		Truck truck = new Truck(10, 3, "Mercedes", 80, new Radio("Sport", false));
		DPS dps = new DPS(100, 15, 5);
		
		System.out.println(dps.Pass(car));
		System.out.println(dps.Pass(truck));
	}
}
