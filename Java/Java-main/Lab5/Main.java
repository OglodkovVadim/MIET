package Lab5;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    static Integer size = 400;

    static ArrayList<Double> ArrayListAddTotalTime = new  ArrayList<Double>();
    static ArrayList<Double> ArrayListAddMediumTime = new  ArrayList<Double>();
    static ArrayList<Double> ArrayListRemoveTotalTime = new  ArrayList<Double>();
    static ArrayList<Double> ArrayListRemoveMediumTime = new  ArrayList<Double>();

    static ArrayList<Double> LinkedListAddTotalTime = new  ArrayList<Double>();
    static ArrayList<Double> LinkedListAddMediumTime = new  ArrayList<Double>();
    static ArrayList<Double> LinkedListRemoveTotalTime = new  ArrayList<Double>();
    static ArrayList<Double> LinkedListRemoveMediumTime = new  ArrayList<Double>();

    static String path = "D:\\Programming\\Java\\Lab4\\Resourse\\log.txt";

    public static void PrintStringArray(String[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.print("\n");
    }

    public static void ReadTime() {
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(new File(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Boolean flagArrayList = false;
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            if (line.isEmpty())
                continue;
            else if (line.split(" ")[0].equals("ArrayList")) {
                flagArrayList = true;
                continue;
            }
            else if (line.split(" ")[0].equals("LinkedList")) {
                flagArrayList = false;
                continue;
            }

            String[] buf = line.split(" ");
            double value = Double.parseDouble(buf[4]) / 1000000.0;
            
            if (flagArrayList) {
                PrintStringArray(buf);
                if (buf[0].equals("Add")) {
                    if (buf[1].equals("total"))
                        ArrayListAddTotalTime.add(value);
                    else
                        ArrayListAddMediumTime.add(value);
                } 
                else {
                    if (buf[1].equals("total"))
                        ArrayListRemoveTotalTime.add(value);
                    else
                        ArrayListRemoveMediumTime.add(value);
                }
            } 
            else {
                PrintStringArray(buf);
                if (buf[0].equals("Add")) {
                    if (buf[1].equals("total"))
                        LinkedListAddTotalTime.add(value);
                    else
                        LinkedListAddMediumTime.add(value);
                } 
                else
                    if (buf[1].equals("total"))
                        LinkedListRemoveTotalTime.add(value);
                    else
                        LinkedListRemoveMediumTime.add(value);
            }
        }
        fileScanner.close();
    }

    public static DrawGraph CreateGraph(ArrayList<Double> arr, String title) {
        DrawGraph graphPanel = new DrawGraph(arr, title);
        graphPanel.setPreferredSize(new Dimension(400, 400));
    
        return graphPanel;
      }

    public static void main(String[] args) {
        ReadTime();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              JFrame frame = new JFrame("Lab 5");
              frame.setLayout(new GridLayout(2, 4));
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              frame.setSize(size * 4, size * 2);
      
              frame.add(CreateGraph(ArrayListAddTotalTime, "ArrayList add total"));
              frame.add(CreateGraph(ArrayListAddMediumTime, "ArrayList add median"));
              frame.add(CreateGraph(ArrayListRemoveTotalTime, "ArrayList remove total"));
              frame.add(CreateGraph(ArrayListRemoveMediumTime, "ArrayList remove median"));
      
              frame.add(CreateGraph(LinkedListAddTotalTime, "LinkedList add total"));
              frame.add(CreateGraph(LinkedListAddMediumTime, "LinkedList add median"));
              frame.add(CreateGraph(LinkedListRemoveTotalTime, "LinkedList remove total"));
              frame.add(CreateGraph(LinkedListRemoveMediumTime, "LinkedList remove median"));
      
              frame.setLocationRelativeTo(null);
              frame.setVisible(true);
            }
          });
    }
}
