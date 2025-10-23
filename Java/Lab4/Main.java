package Lab4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import javax.annotation.processing.Filer;

import java.time.*;


public class Main
{
	public static Integer size = 10;

	public static String GenerateString()  {
		int leftLimit = 48;
		int rightLimit = 122;
		int targetStringLength = 10;
		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1)
		.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		.limit(targetStringLength)
		.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

	}

	public static void GenerateArrayList(ArrayList<Car> arrayListCar, LogWriter logWriter, Integer _size) throws IOException  {
		Long startTotal = System.nanoTime();
		for (int i = 0; i < _size; i++) {
			Long startCurrent = System.nanoTime();
			arrayListCar.add(
				new Car(
					new Random().nextInt(1000000),
					GenerateString(),
					new Random().nextInt(300),
					new Radio(
						GenerateString(), 
						new Random().nextBoolean()
					)
				)
			);
			Long endCurrent = System.nanoTime();
		}
		Long endTotal = System.nanoTime();
		logWriter.WriteLog("ArrayList size = " + _size + "\n");
		logWriter.WriteLog("Add total time = " + (endTotal - startTotal) + "\n");
		logWriter.WriteLog("Add median time = " + (endTotal - startTotal) / _size + "\n");
	}

	public static void GenerateLinkedList(LinkedList<Car> linkedListCar, LogWriter logWriter, Integer _size) throws IOException  {
		Long startTotal = System.nanoTime();
		for (int i = 0; i < _size; i++) {
			// Long startCurrent/ = System.nanoTime();
			linkedListCar.add(
				new Car(
					new Random().nextInt(1000000),
					GenerateString(),
					new Random().nextInt(300),
					new Radio(
						GenerateString(), 
						new Random().nextBoolean()
					)
				)
			);
			// Long endCurrent = System.nanoTime();
		}
		Long endTotal = System.nanoTime();
		logWriter.WriteLog("LinkedList size = " + _size + "\n");
		logWriter.WriteLog("Add total time = " + (endTotal - startTotal) + "\n");
		logWriter.WriteLog("Add median time = " + (endTotal - startTotal) / _size + "\n");
	}

	public static void RemoveArrayList(ArrayList<Car> arrayListCar, LogWriter logWriter, Integer _size) throws IOException {
		Long startTotal = System.nanoTime();
		arrayListCar.remove(new Random().nextInt(_size));
		Long endTotal = System.nanoTime();

		// logWriter.WriteLog("Remove, ID = " + Integer.toString(new Random().nextInt(10000) + 1000) + "\n");
		logWriter.WriteLog("Remove total time = " + (endTotal - startTotal) + "\n");
		logWriter.WriteLog("Remove median time = " + (endTotal - startTotal) + "\n\n");
	}

	public static void RemoveLinkedList(LinkedList<Car> linkedListCar, LogWriter logWriter, Integer _size) throws IOException {
		Long startTotal = System.nanoTime();
		linkedListCar.remove(new Random().nextInt(_size));
		Long endTotal = System.nanoTime();

		// logWriter.WriteLog("Remove, ID = " + Integer.toString(new Random().nextInt(10000) + 1000) + "\n");
		logWriter.WriteLog("Remove total time = " + (endTotal - startTotal) + "\n");
		logWriter.WriteLog("Remove median time = " + (endTotal - startTotal) + "\n\n");
	}

	public static void main(String[] args) throws IOException {
		String userName;
		Boolean logStatus;
		Properties props = new Properties();
		Integer countError = 0;
		
		props.load(new FileInputStream(new File("D:\\Programming\\Java\\Lab4\\Resourse\\config.ini")));
		logStatus = Boolean.parseBoolean(props.getProperty("log_status"));
		userName = props.getProperty("user_name");

		LogWriter logWriter = new LogWriter(logStatus);

		try {
			for (int i = 0; i < 5; i++) {
				// logWriter.WriteLog(userName + ": starting at " + LocalTime.now() + "\n");/

				ArrayList<Car> arrayListCar = new ArrayList<>();
				LinkedList<Car> linkedListCar = new LinkedList<>();

				GenerateArrayList(arrayListCar, logWriter, size);
				// CarManager.ShowCars(arrayListCar);
				RemoveArrayList(arrayListCar, logWriter, size);

				GenerateLinkedList(linkedListCar, logWriter, size);
				// CarManager.ShowCars(linkedListCar);
				RemoveLinkedList(linkedListCar, logWriter, size);

				size = size * 10;
			}
		} catch (Exception e) {
			logWriter.WriteLog("Error: " + e.getMessage() + "\n");
			countError++;
		}
	}
} 
