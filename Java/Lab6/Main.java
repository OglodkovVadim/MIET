package Lab6;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import java.awt.event.ActionEvent;

public class Main extends JFrame {
    String[] response = {
        "Да",
        "Нет",
        "Однозначно да",
        "Будущее неясно",
        "Может быть",
        "Скорее да",
        "Скорее нет",
        "Невозможно",
        "Никогда",
        "Узнаете"
    };

    static String repeat = ""; 

    private JLabel labelRequest = new JLabel("Введите запрос: ");
    private JLabel labelResponse = new JLabel("");
    private JTextField field = new JTextField("");
    private JButton button = new JButton("Отправить");

    private Border paddingRequest = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    private Border paddingResponse = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    
    private Border marginRequest = BorderFactory.createEmptyBorder(0, 0, 0, 0);
    private Border marginResponse = BorderFactory.createEmptyBorder(20, 20, 20, 20);

    private Border borderRequest = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
    private Border borderResponse = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK);
    
    private Border compoundBorderRequest = new CompoundBorder(marginRequest, new CompoundBorder(borderRequest, paddingRequest));
    private Border compoundBorderResponse = new CompoundBorder(marginResponse, new CompoundBorder(borderResponse, paddingResponse));
    
    Main() {
        super.setTitle("Main");
        this.setSize(700, 200);     
        
        field.setFont(new Font("Serif", Font.BOLD, 20));
        field.setColumns(20);
        field.setBorder(compoundBorderRequest);

        button.setFont(new Font("Serif", Font.BOLD, 20));
        button.addActionListener(this::buttonClick);
        
        labelRequest.setFont(new Font("Serif", Font.BOLD, 20));
        labelResponse.setFont(new Font("Serif", Font.BOLD, 20));
        labelResponse.setBorder(compoundBorderResponse);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new FlowLayout());   
        this.getContentPane().add(labelRequest);
        this.getContentPane().add(field);
        this.getContentPane().add(button);
        this.getContentPane().add(labelResponse);
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void buttonClick(ActionEvent e) {
        if (field.getText().isEmpty()) {
            labelResponse.setForeground(Color.RED);
            labelResponse.setText("Введите запрос");
        } else if (field.getText().equals(repeat)) {
            return;
        } else {
            repeat = field.getText();
            labelResponse.setForeground(Color.BLACK);
            labelResponse.setText(response[new Random().nextInt(10)]);
        }
    }
    public static void main(String[] args) {
        new Main();
    }


}