/* Program Name: Simulator.java
 * Date: Aug 04, 2021
 */

package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import person.Person;

public class Simulator extends JPanel
{
    private final int WIDTH = 1000;
    private final int HEIGHT = 700;
    private final int LAG_TIME = 200;
    private final int BALL_DIAM = 10;
    private final int TOTAL_DAYS = 450; // 21 days
    private int m_currentDay;
    private ArrayList<Ball> m_balls = new ArrayList<Ball>();
    private Timer m_time;

    public Simulator(ArrayList<Person> population)
    {
        m_time = new Timer(LAG_TIME, new SimulatorListener());

        for (Person person : population)
            m_balls.add(new Ball(person, BALL_DIAM, WIDTH, HEIGHT));

        //Print();

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(237, 237, 237));

        startTimer();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for (Ball ball : m_balls)
        {
            g.setColor(ball.color());
            g.fillOval(ball.xCoord(), ball.yCoord(), ball.diameter(), ball.diameter());
        }
    }

    private class SimulatorListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            for (Ball ball : m_balls)
                move(ball);

            repaint();

            incrementDay();

            if (m_currentDay > 50)
                stopTimer();
        }
    }

    public void move(Ball ball)
    {
        if (!ball.person().isAlive())
            return;

        if ((ball.xCoord() >= WIDTH - ball.diameter())
                || (ball.xCoord() <= 0))
            ball.setXIncrement(ball.xIncrement() * -1);

        if ((ball.yCoord() >= HEIGHT - ball.diameter())
                || (ball.yCoord() <= 0))
            ball.setYIncrement(ball.yIncrement() * -1);

        ball.setXCoord(ball.xCoord() + ball.xIncrement());
        ball.setYCoord(ball.yCoord() + ball.yIncrement());
    }

    // TODO: REMOVE
    private void Print()
    {
        int counter = 1;
        for (Ball ball : m_balls)
        {
            System.out.println(
                    "Person: " + counter++
                    + "\nColor: " + ball.color()
                    + "\nXPos: " + ball.xCoord()
                    + "\nYPos: " + ball.yCoord()
                    + "\nXInc: " + ball.xIncrement()
                    + "\nYInc: " + ball.yIncrement()
                    );
        }

    }

    public int width()
    {
        return WIDTH;
    }

    public int height()
    {
        return HEIGHT;
    }

    public void startTimer()
    {
        m_time.start();
    }

    public void stopTimer()
    {
        m_time.stop();
    }

    public void incrementDay()
    {
        m_currentDay += 1;
    }
}
