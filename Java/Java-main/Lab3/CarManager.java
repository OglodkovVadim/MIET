package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CarManager {
    public static void AddNewCar(ArrayList<Car> listCars) {
		Scanner in = new Scanner(System.in);
		String mark;
		Integer maxSpeed;
		String currentStation;
		Boolean status;

		System.out.print("\033[H\033[2J");  
		System.out.print("Enter mark -> ");
		mark = in.next();

		System.out.print("\033[H\033[2J");  
		System.out.print("Enter max speed -> ");
		maxSpeed = in.nextInt();

		System.out.print("\033[H\033[2J");  
		System.out.print("Enter current station -> ");
		currentStation = in.next();

		System.out.print("\033[H\033[2J");  
		System.out.print("Enter status (0 - ON, 1 - OFF) -> ");
		status = (in.nextInt() == 0) ? true : false;

		Random id = new Random();
		listCars.add(new Car((id.nextInt(10000) + 1000), mark, maxSpeed, new Radio(currentStation, status)));
	}
	
	public static void ShowCars(ArrayList<Car> listCars) {
		System.out.print("\033[H\033[2J");  
		Scanner in = new Scanner(System.in);

		if (listCars.isEmpty()) {
			System.out.println("There are no cars");
		} else {
			for (Car car : listCars) {
				System.out.println("Mark: " + car.mark);
				System.out.println("Max speed: " + car.maxSpeed);
				System.out.println("Radio: ");
				System.out.println("  Current station: " + car.radio.currentStation);
				System.out.println("  Status of radio: " + (car.radio.status ? "ON" : "OFF"));
			}
		}
		String waitInput = in.nextLine();
	}
}
