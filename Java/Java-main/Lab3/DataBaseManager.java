package Lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DataBaseManager {
	public static String fileName = "Lab3/Resourse/data_base.txt";
	public static void ShowListCarsTrucks(ArrayList<Car> listCars, ArrayList<Truck> listTrucks) {
		System.out.print("\033[H\033[2J"); 
		for (Car car : listCars) {
			System.out.println(car);
		}
		for (Truck truck : listTrucks) { 
			System.out.println(truck);
		}
	}
	
	public static void ReadFromDataBase(
		ArrayList<Car> listCars, 
		ArrayList<Truck> listTrucks, 
		LogWriter logWriter
    ) throws IOException {

		FileWriter fw = null;
		try {
			String str;
			Integer counter;
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((str = br.readLine()) != null) {
				String[] readLine = str.split(" ");
				if (readLine.length == 5) {
					listCars.add(
						new Car(
							Integer.parseInt(readLine[0]),
							readLine[1],
							Integer.parseInt(readLine[2]),
							new Radio(
								readLine[3], 
								Boolean.parseBoolean(readLine[4])
							)
						)
					);
				}
				else if (readLine.length == 7) {
					listTrucks.add(
						new Truck(
							Integer.parseInt(readLine[0]),
							readLine[1],
							Integer.parseInt(readLine[2]),
							new Radio(
								readLine[3], 
								Boolean.parseBoolean(readLine[4])
							),
							Integer.parseInt(readLine[5]),
							Integer.parseInt(readLine[6])
						)
					);
				}
			}
			
			br.close();
		} catch (IOException e) {
			logWriter.WriteLog("Error with reading at " + LocalTime.now() + "\n");
		}
	}

	public static void WriteToDataBase(
		ArrayList<Car> listCars, 
		ArrayList<Truck> listTrucks,
		Boolean isAppend,
		LogWriter logWriter
	) throws IOException {
		FileWriter fw = null;
		try {
			Random id = new Random();
			fw = new FileWriter(fileName, isAppend);
			for (Car car : listCars) {
				fw.write(
					car.GetId() + " "
					+ car.mark + " " 
					+ car.maxSpeed + " "
					+ car.radio.currentStation + " "
					+ car.radio.status + "\n");
			}
			for (Truck truck : listTrucks) {
				fw.write(
					truck.GetId() + " "
					+ truck.mark + " " 
					+ truck.maxSpeed + " "
					+ truck.radio.currentStation + " "
					+ truck.radio.status + " "
					+ truck.weight + " "
					+ truck.height + "\n"
				);
			}
			fw.close();
		} catch (IOException e) {
			logWriter.WriteLog("Error with writing at " + LocalTime.now() + "\n");
			System.out.println(e);
		}
	}

	public static void ChangeItemDB(LogWriter logWriter) throws IOException {
		ArrayList<Car> bufListCar = new ArrayList<>();
		ArrayList<Truck> bufListTruck = new ArrayList<>();
		DataBaseManager.ReadFromDataBase(bufListCar, bufListTruck, logWriter);

		Scanner in = new Scanner(System.in);

		ShowListCarsTrucks(bufListCar, bufListTruck);
		System.out.print("Enter id -> ");
		Integer chooseId = in.nextInt();

		for (Car car : bufListCar) {
			if (car.GetId().intValue() == chooseId.intValue()) {
				MenuPrinter.ChangeCarMenu();
				Integer changeItemSwitch = in.nextInt();
				switch (changeItemSwitch) {
					case 0:
						break;
					case 1:
						System.out.print("Enter new mark -> ");
						car.SetMark(in.next());
						break;
					case 2:
						System.out.print("Enter new max speed -> ");
						car.SetMaxSpeed(in.nextInt());
						break;
					case 3:
						System.out.print("Enter current station -> ");
						car.radio.SetCurrenStation(in.next());
						break;
					case 4:
						System.out.print("Enter new radio status (0 - ON, 1 - OFF) -> ");
						car.radio.SetStatus(Boolean.parseBoolean(in.next()));
						break;
					case 5:
						bufListCar.remove(car);
						break;
				}
				break;
			}
		}

		for (Truck truck : bufListTruck) {
			if (truck.GetId().intValue() == chooseId.intValue()) {
				MenuPrinter.ChangeTruckMenu();
				Integer changeItemSwitch = in.nextInt();
				switch (changeItemSwitch) {
					case 0:
						break;
					case 1:
						System.out.print("Enter new mark -> ");
						truck.SetMark(in.next());
						break;
					case 2:
						System.out.print("Enter new max speed -> ");
						truck.SetMaxSpeed(in.nextInt());
						break;
					case 3:
						System.out.print("Enter current station -> ");
						truck.radio.SetCurrenStation(in.next());
						break;
					case 4:
						System.out.print("Enter new radio status (0 - ON, 1 - OFF) -> ");
						truck.radio.SetStatus(Boolean.parseBoolean(in.next()));
						break;
					case 5:
						System.out.print("Enter new weigth -> ");
						truck.SetWeight(in.nextInt());
						break;
					case 6:
						System.out.print("Enter new height -> ");
						truck.SetHeight(in.nextInt());
						break;
					case 7:
						bufListTruck.remove(truck);
						break;
				}
				break;
			}
		}
		
		DataBaseManager.WriteToDataBase(bufListCar, bufListTruck, false, logWriter);
	}
}
