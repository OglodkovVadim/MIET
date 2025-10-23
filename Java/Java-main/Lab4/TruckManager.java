package Lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TruckManager {
    public static void AddNewTruck(ArrayList<Truck> listTrucks) {
		Scanner in = new Scanner(System.in);
		String mark;
		Integer maxSpeed;
		String currentStation;
		Boolean status;

		Integer weight;
		Integer height;

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

		System.out.print("\033[H\033[2J");  
		System.out.print("Enter weight -> ");
		weight = in.nextInt();

		System.out.print("\033[H\033[2J");  
		System.out.print("Enter height -> ");
		height = in.nextInt();

		Random id = new Random();
		listTrucks.add(
			new Truck(
				(id.nextInt(10000) + 1000), 
				mark, 
				maxSpeed, 
				new Radio(
					currentStation, 
					status
				),
				weight, 
				height
			)
		);
	}
	
    public static void ShowTrucks(ArrayList<Truck> listTrucks) {   
		System.out.print("\033[H\033[2J");  
		Scanner in = new Scanner(System.in);
		if (listTrucks.isEmpty()) {
			System.out.println("There are no trucks");
		} else {
			// for (Truck truck : listTrucks) {
			// 	System.out.println("Mark: " + truck.mark);
			// 	System.out.println("Max speed: " + truck.maxSpeed);
			// 	System.out.println("Radio: ");
			// 	System.out.println("  Current station: " + truck.radio.currentStation);
			// 	System.out.println("  Status of radio: " + (truck.radio.status ? "ON" : "OFF"));
			// 	System.out.println("Weight: " + truck.weight);
			// 	System.out.println("Height: " + truck.height);
			// }
		}
		String waitInput = in.nextLine();
	}
}
