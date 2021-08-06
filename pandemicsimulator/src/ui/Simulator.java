/* Program Name: Simulator.java
 * Date: Aug 04, 2021
 */

package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import person.Person;
import person.PersonStatus;
import person.VaccinationStatus;

public class Simulator extends JPanel
{
    private final int WIDTH = 1000;
    private final int HEIGHT = 700;
    private final int LAG_TIME = 200;
    private final int BALL_DIAM = 10;
    private final int TOTAL_DAYS = 450; // 21 days
    private boolean m_isFinished;
    private int m_currentDay = 0;
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
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        // TODO: REMOVE
        // int counter = 0;
        for (Ball ball : m_balls)
        {
            g.setColor(ball.color());
            g.fillOval(ball.xCoord(), ball.yCoord(), ball.diameter(), ball.diameter());
            /*
             * TODO: REMOVE
            g.drawString(
                    "X: " + String.valueOf(ball.xCoord())
                    + " Y: " + String.valueOf(ball.yCoord()), ball.xCoord(), ball.yCoord());
            System.out.println("#: " + counter
                    + "\nX: " + ball.xCoord()
                    + "\nY: " + ball.yCoord()
                    );
            counter++;
            */
        }
    }

    private class SimulatorListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            for (Ball ball : m_balls)
            {
                move(ball);
                Person p = ball.person();

                if (p.isInfected() || p.hasRecovered())
                {
                    p.incrementDaysInfected();

                    if (p.daysInfected() >= 100 && p.isAlive())
                    {
                        p.setInfected(false);
                        p.setRecovered(true);
                        p.setPersonStatus(PersonStatus.NOT_INFECTED);
                        p.setVaccinationStatus(VaccinationStatus.TWO_SHOTS);
                        ball.setColor(Color.GREEN);
                    }
                }
            }

            for (int i = 0; i < (m_balls.size() - 1); ++i)
            {
                for (int j = (i + 1); j < m_balls.size(); ++j)
                {
                    checkCollision(m_balls.get(i), m_balls.get(j));
                }
            }

            repaint();
            //stopTimer();

            incrementDay();

            if (m_currentDay > 450)
            {
                m_isFinished = true;

                int infectedPeople = 0;
                int noImmunityPeople = 0;
                int recoveredPeople = 0;
                int oneShotPeople = 0;
                int twoShotPeopl = 0;
                int deadPeople = 0;

                stopTimer();
                for (Ball b : m_balls)
                {
                    Person p = b.person();
                }
            }
        }
    }

    private void move(Ball ball)
    {
        if (!ball.person().isAlive())
            return;

        if ((ball.xCoord() >= WIDTH - (ball.diameter() + ball.MAX_PIXELS / 2))
                || (ball.xCoord() <= 0))
            ball.setXIncrement(ball.xIncrement() * -1);

        if ((ball.yCoord() >= HEIGHT - (ball.diameter() + ball.MAX_PIXELS / 2))
                || (ball.yCoord() <= 0))
            ball.setYIncrement(ball.yIncrement() * -1);

        ball.setXCoord(ball.xCoord() + ball.xIncrement());
        ball.setYCoord(ball.yCoord() + ball.yIncrement());
    }

    private void checkCollision(Ball firstBall, Ball secondBall)
    {
        if (!firstBall.person().isAlive()
                || !secondBall.person().isAlive())
            return;

        int deltaX = (int)Math.pow(firstBall.xCoord() - secondBall.xCoord(), 2);

        int deltaY = (int)Math.pow(firstBall.yCoord() - secondBall.yCoord(), 2);

        if (Math.sqrt(deltaX + deltaY) <= firstBall.MAX_PIXELS)
        {
            firstBall.setXIncrement(firstBall.xIncrement() * -1);
            firstBall.setYIncrement(firstBall.yIncrement() * -1);

            checkBounaries(firstBall);

            secondBall.setXIncrement(secondBall.xIncrement() * -1);
            secondBall.setYIncrement(secondBall.yIncrement() * -1);

            checkBounaries(secondBall);

            checkInfection(firstBall, secondBall);
        }
    }

    private void checkInfection(Ball b1, Ball b2)
    {
        Person p1 = b1.person();
        Person p2 = b2.person();

        if ((!p1.isInfected() && !p2.isInfected())
                || !p1.isAlive()
                || !p2.isAlive())
            return;

        if (p1.daysInfected() > 150)
        {
            calculateDieProbability(b1);
        }

        if (p2.daysInfected() > 150)
        {
            calculateDieProbability(b1);
        }

        if (p1.isInfected())
        {
            //p1.incrementDaysInfected();
            calculateInfection(b2);
        }
        else if (p2.isInfected())
        {
            //p2.incrementDaysInfected();
            calculateInfection(b1);
        }
    }

    private void calculateInfection(Ball ball)
    {
        Person person = ball.person();

        if (willGetInfected(person.immunityValue()))
        {
            person.setInfected(true);
            person.setPersonStatus(PersonStatus.INFECTED);
            ball.setColor(Color.RED);
        }
    }

    private void calculateDieProbability(Ball ball)
    {
        Person person = ball.person();

        if (willDie(person))
        {
            person.setPersonStatus(PersonStatus.DEAD);
            person.setAlive(false);
            ball.setColor(Color.BLACK);
        }
    }

    private boolean willGetInfected(int immunityValue)
    {
        return (int)(Math.random() * 101) > immunityValue;
    }

    private boolean willDie(Person person)
    {
        if (person.hasRecovered())
        {
            return Math.random() * 101 > 99.7;
        }
        else
        {
            if (person.vaccinationStatus() == VaccinationStatus.NO_VACCINE)
                return (int)(Math.random() * 101) > 90;
            else if (person.vaccinationStatus() == VaccinationStatus.ONE_SHOT)
                return (int)(Math.random() * 101) > 95;
            else
                return (int)(Math.random() * 101) > 99;
        }
    }

    private void checkBounaries(Ball ball)
    {
        if (ball.xCoord() <= 0)
            ball.setXCoord(ball.xCoord() + ball.diameter());

        if (ball.xCoord() > WIDTH - (ball.diameter() + ball.MAX_PIXELS / 2))
            ball.setXCoord(ball.xCoord() - ball.diameter());

        if (ball.yCoord() <= 0)
            ball.setYCoord(ball.yCoord() + ball.diameter());

        if (ball.yCoord() > WIDTH - (ball.diameter() + ball.MAX_PIXELS / 2))
            ball.setYCoord(ball.yCoord() - ball.diameter());
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
        if (m_currentDay <= 450)
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
