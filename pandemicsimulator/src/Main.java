/* Program Name: Main.java
 * Date: Aug 04, 2021
 */

import java.util.ArrayList;
import person.Immunity;
import person.Person;
import ui.Ball;
import ui.Simulator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main
{
    public static void main(String []args)
    {
        ArrayList<Person> people = new ArrayList<Person>();
        people.add(new Person(true, false, Immunity.NO_IMMUNITY));
        people.add(new Person(true, false, Immunity.ONE_SHOT));
        people.add(new Person(true, false, Immunity.TWO_SHOTS));
        people.add(new Person(true, false, Immunity.RECOVERED));
        people.add(new Person(true, true));
        people.add(new Person(false, false));

        ArrayList<Person> population = new ArrayList<Person>();

        // TODO
        // 600 PEOPLE
        // 100% 80% 55% 20%
        // 20% ONE SHOT
        // 25% TWO SHOTS
        // 35% NO IMMUNITY
        // 20% INFECTED
        for (int i = 0; i < 600; ++i)
        {
            int rand = (int)(Math.random() * people.size());
            population.add(people.get(rand));
        }

        JFrame frame = new JFrame("Test");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        Simulator simulator = new Simulator(population);
        frame.setSize(simulator.width(), simulator.height());
        frame.setLocationRelativeTo(null);

        frame.getContentPane().setBackground(Color.GRAY);
        frame.add(simulator);
        frame.setResizable(false);

        frame.pack();
        frame.setVisible(true);
    }
}
