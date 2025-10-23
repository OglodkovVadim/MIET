package Lab3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;
import java.time.*;


public class Main
{
	public static void main(String[] args) throws IOException {
		FileWriter fw = null;
		Properties props = new Properties();
		LogWriter logWriter = new LogWriter(true);
		try {
			String userName;
			Boolean logStatus;
			
			props.load(new FileInputStream(new File("Lab3/Resourse/config.ini")));
			logStatus = Boolean.parseBoolean(props.getProperty("log_status"));
			userName = props.getProperty("user_name");

			logWriter.SetStatus(logStatus);
			logWriter.WriteLog(userName + ": starting at " + LocalTime.now() + "\n");
				
			Scanner in = new Scanner(System.in);
			DPS dps = new DPS(100, 15, 5);
			
			ArrayList<Car> listCars = new ArrayList<>();
			ArrayList<Truck> listTrucks = new ArrayList<>();

			Integer mainSwitch = 0;
			Integer addItemSwitch = 0;
			Integer showItemsSwitch = 0;
			Integer updateDPSSwitch = 0;
			Integer dataBaseSwitch = 0;

			while (true) {
				MenuPrinter.MainMenu(userName);
				mainSwitch = in.nextInt();
				switch (mainSwitch) {
					case 0: // exit
						logWriter.WriteLog(userName + " finished at " + LocalTime.now() + "\n");
						System.exit(0);
						in.close();
						break;
					case 1: // add car or truck
						MenuPrinter.AddItemMenu();
						addItemSwitch = in.nextInt();
						switch (addItemSwitch) {
							case 0: 
								break;
							case 1: 
								CarManager.AddNewCar(listCars);
								break;
							case 2: 
								TruckManager.AddNewTruck(listTrucks);
								break;
						}
						break;
					case 2: // show cars or trucks
						MenuPrinter.ShowItemsMenu();
						showItemsSwitch = in.nextInt();
						switch (showItemsSwitch) {
							case 0: 
								break;
							case 1:
								CarManager.ShowCars(listCars);
								break;
							case 2:
								TruckManager.ShowTrucks(listTrucks);
								break;
						}
						break;
					case 3: // update dps
						MenuPrinter.UpdateDPSMenu();
						updateDPSSwitch = in.nextInt();
						switch (updateDPSSwitch) {
							case 0:
								break;
							case 1:
								dps.UpdateSpeedLimit();;
								break;
							case 2:
								dps.UpdateWeightLimit();
								break;
							case 3:
								dps.UpdateHeightLimit();
								break;
						}
						break;
					case 4: // read or write data base
						MenuPrinter.DataBaseMenu();
						dataBaseSwitch = in.nextInt();
						switch (dataBaseSwitch) {
							case 0: 
								break;
							case 1:
								logWriter.WriteLog("Reading from db at " + LocalTime.now() + "\n");
								DataBaseManager.ReadFromDataBase(listCars, listTrucks, logWriter);
								break;
							case 2:
								logWriter.WriteLog("Writing to db at " + LocalTime.now() + "\n");
								DataBaseManager.WriteToDataBase(listCars, listTrucks, true, logWriter);
								break;
							case 3:
								logWriter.WriteLog("Changing in db at " + LocalTime.now() + "\n");
								DataBaseManager.ChangeItemDB(logWriter);
								break;
						}
						break;
				}
			}
		} catch (IOException e) {
			logWriter.WriteLog("Error with reading config file at " + LocalTime.now() + "\n");
		} catch (InputMismatchException e) {
			logWriter.WriteLog("Error input at " + LocalTime.now() + "\n");
		}
	}
} 
