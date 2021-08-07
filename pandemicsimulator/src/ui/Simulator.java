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
    private final int WIDTH = 1000 + 250;
    private final int HEIGHT = 700;
    private final int LAG_TIME = 200;
    private final int BALL_DIAM = 10;
    private final int TOTAL_DAYS = 450; // 21 days
    private int m_currentDay = 0;
    private int m_infected = 0;
    private int m_unVax = 0;
    private int m_oneShot = 0;
    private int m_twoShots = 0;
    private int m_recovered = 0;
    private int m_dead = 0;
    private ArrayList<Ball> m_balls = new ArrayList<Ball>();
    private ArrayList<Person> m_population;
    private Timer m_time;

    public Simulator(ArrayList<Person> population)
    {
        m_population = population;

        m_time = new Timer(LAG_TIME, new SimulatorListener());

        for (Person person : population)
            m_balls.add(new Ball(person, BALL_DIAM, (WIDTH - 250), HEIGHT));

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(237, 237, 237));
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        drawDashboard(g);

        for (Ball ball : m_balls)
        {
            g.setColor(ball.color());
            g.fillOval(ball.xCoord(), ball.yCoord(), ball.diameter(), ball.diameter());
        }
    }

    private void drawDashboard(Graphics g)
    {
        int startRectX = WIDTH - 250;
        int startRectY = 0;
        int rectWidth = WIDTH - 1000;
        int rectHeight = HEIGHT;

        g.setColor(Color.WHITE);
        g.fillRect(startRectX, startRectY, rectWidth, rectHeight);

        g.setColor(Color.BLACK);

        Rectangle rect = new Rectangle(startRectX, startRectY, rectWidth, rectHeight);
        Font titleFont = new Font("Serif", Font.BOLD, 24);

        // draw title
        drawCenteredStringHorizontally(
                g,
                "Dashboard",
                rect,
                titleFont
                );

        drawDashboardInfo(g, rect);
    }

    private void drawCenteredStringHorizontally(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);

        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;

        g.setFont(font);
        g.drawString(text, x, 30);
    }

    private void drawDashboardInfo(Graphics g, Rectangle rect)
    {
        Font labelFont = new Font("Serif", Font.BOLD, 14);
        Font valueFont = new Font("Serif", Font.PLAIN, 14);

        g.setFont(labelFont);
        String lblPopulation = "Population:";
        int posX = rect.x + 20;
        int posY = rect.y + 150;
        int ySpan = 30;

        g.drawString(lblPopulation, posX, posY);
        FontMetrics metrics = g.getFontMetrics(labelFont);

        g.setFont(valueFont);
        g.drawString(
                String.valueOf(m_population.size()),
                metrics.stringWidth(lblPopulation) + rect.x + 30,
                posY
                );

        g.setFont(labelFont);
        String lblDays = "Elapse Time (Days):";
        g.drawString(lblDays, posX, posY + ySpan);

        metrics = g.getFontMetrics(labelFont);

        g.setFont(valueFont);
        g.drawString(
                String.valueOf(m_currentDay / 21),
                metrics.stringWidth(lblDays) + rect.x + 30,
                posY + ySpan
                );

        final int LBL_CIRCLE_DIAM = 12;

        // draw infected info
        g.setColor(Color.RED);
        g.fillOval(posX, posY + (2 * ySpan), LBL_CIRCLE_DIAM, LBL_CIRCLE_DIAM);

        // draw no vax info
        g.setColor(Color.BLUE);
        g.fillOval(posX, posY + (3 * ySpan), LBL_CIRCLE_DIAM, LBL_CIRCLE_DIAM);

        // draw 1-shot info
        g.setColor(Color.CYAN);
        g.fillOval(posX, posY + (4 * ySpan), LBL_CIRCLE_DIAM, LBL_CIRCLE_DIAM);

        // draw 2-shot info
        g.setColor(Color.YELLOW);
        g.fillOval(posX, posY + (5 * ySpan), LBL_CIRCLE_DIAM, LBL_CIRCLE_DIAM);

        // draw recovered info
        g.setColor(Color.GREEN);
        g.fillOval(posX, posY + (6 * ySpan), LBL_CIRCLE_DIAM, LBL_CIRCLE_DIAM);

        // draw dead info
        g.setColor(Color.BLACK);
        g.fillOval(posX, posY + (7 * ySpan), LBL_CIRCLE_DIAM, LBL_CIRCLE_DIAM);

        // draw dead info
        g.setColor(Color.BLACK);
        String lblInfectedPop = "Infected:";
        String lblNoVaxPop = "Unvaccinated:";
        String lblOneShotPop = "Partially-vaccinated:";
        String lblTwoShotsPop = "Fully-vaccinated:";
        String lblRecoveredPop = "Recovered:";
        String lblDeadPop = "Dead:";

        int xSpan = 25;
        int yAdjust = 12;

        g.drawString(lblInfectedPop, (posX + xSpan), (posY + yAdjust) + (2 * ySpan));
        g.drawString(lblNoVaxPop, (posX + xSpan), (posY + yAdjust) + (3 * ySpan));
        g.drawString(lblOneShotPop, (posX + xSpan), (posY + yAdjust) + (4 * ySpan));
        g.drawString(lblTwoShotsPop, (posX + xSpan), (posY + yAdjust) + (5 * ySpan));
        g.drawString(lblRecoveredPop, (posX + xSpan), (posY + yAdjust) + (6 * ySpan));
        g.drawString(lblDeadPop, (posX + xSpan), (posY + yAdjust) + (7 * ySpan));

        int xAdjust = 25;

        ArrayList<Integer> totals = calculatePeopleNumberStatus(m_balls);

        g.setFont(labelFont);
        xSpan = metrics.stringWidth(lblInfectedPop) + xAdjust;
        g.drawString(String.valueOf(totals.get(0)), (posX + xSpan), (posY + yAdjust) + (2 * ySpan));

        xSpan = metrics.stringWidth(lblNoVaxPop) + xAdjust;
        g.drawString(String.valueOf(totals.get(1)), (posX + xSpan), (posY + yAdjust) + (3 * ySpan));

        xSpan = metrics.stringWidth(lblOneShotPop) + xAdjust;
        g.drawString(String.valueOf(totals.get(2)), (posX + xSpan), (posY + yAdjust) + (4 * ySpan));

        xSpan = metrics.stringWidth(lblTwoShotsPop) + xAdjust;
        g.drawString(String.valueOf(totals.get(3)), (posX + xSpan), (posY + yAdjust) + (5 * ySpan));

        xSpan = metrics.stringWidth(lblRecoveredPop) + xAdjust;
        g.drawString(String.valueOf(totals.get(4)), (posX + xSpan), (posY + yAdjust) + (6 * ySpan));

        xSpan = metrics.stringWidth(lblDeadPop) + xAdjust;
        g.drawString(String.valueOf(totals.get(5)), (posX + xSpan), (posY + yAdjust) + (7 * ySpan));
    }

    private ArrayList<Integer> calculatePeopleNumberStatus(ArrayList<Ball> balls)
    {
        ArrayList<Integer> totals = new ArrayList<Integer>();

        int infected = 0;
        int unVax = 0;
        int oneShot = 0;
        int twoShots = 0;
        int recovered = 0;
        int dead = 0;

        for (Ball ball : balls)
        {
            if (ball.person().isInfected())
            {
                infected += 1;

                if (ball.person().vaccinationStatus() == VaccinationStatus.NO_VACCINE)
                    unVax += 1;
                if (ball.person().vaccinationStatus() == VaccinationStatus.ONE_SHOT)
                    oneShot += 1;
                if (ball.person().vaccinationStatus() == VaccinationStatus.TWO_SHOTS)
                    twoShots += 1;
                if (!ball.person().isAlive())
                    dead += 1;
            }
            else if (ball.person().hasRecovered())
            {
                recovered += 1;
            }
        }

        m_infected = infected;
        totals.add(m_infected);
        m_unVax = unVax;
        totals.add(m_unVax);
        m_oneShot = oneShot;
        totals.add(m_oneShot);
        m_twoShots = twoShots;
        totals.add(m_twoShots);
        m_recovered = recovered;
        totals.add(m_recovered);
        m_dead = dead;
        totals.add(m_dead);

        return totals;
    }

    private void showFinalReport()
    {
        int totalPopulation = m_population.size();
        int totalInfected = m_recovered + m_infected + m_oneShot + m_twoShots + m_unVax;
        int totalUnvax = 0;
        int totalOneShot = 0;
        int totalTwoShots = 0;
        int totalInfectedRecovered = 0;
        int totalDead = 0;
        int totalDeadUnvax = 0;
        int totalDeadOneShot = 0;
        int totalDeadTwoShots = 0;

        for (Ball ball : m_balls)
        {
            Person p = ball.person();

            if (p.isAlive())
            {
                if (p.isInfected())
                {
                    if (p.vaccinationStatus() == VaccinationStatus.NO_VACCINE)
                    {
                        totalUnvax += 1;
                    }
                    else if (p.vaccinationStatus() == VaccinationStatus.ONE_SHOT)
                    {
                        totalOneShot += 1;
                    }
                    else if (p.vaccinationStatus() == VaccinationStatus.TWO_SHOTS)
                    {
                        totalTwoShots += 1;
                    }
                }
                else if (p.hasRecovered())
                {
                    totalInfectedRecovered += 1;
                }
            }
            else if (!p.isAlive())
            {
                totalDead += 1;

                if (p.vaccinationStatus() == VaccinationStatus.NO_VACCINE)
                    totalDeadUnvax += 1;
                else if (p.vaccinationStatus() == VaccinationStatus.ONE_SHOT)
                    totalDeadOneShot += 1;
                else if (p.vaccinationStatus() == VaccinationStatus.TWO_SHOTS)
                    totalDeadTwoShots += 1;
            }
        }

        JFrame reportFrame = new JFrame("Pandemic Report");
        reportFrame.setLayout(new BorderLayout());
        reportFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        reportFrame.setPreferredSize(new Dimension(400, 300));
        reportFrame.setResizable(false);

        JLabel lblTitle = new JLabel("Infected Population (%)", JLabel.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 18));
        reportFrame.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlData = new JPanel(new GridLayout(9, 2, 0, 0));

        JLabel lblInfectPop = new JLabel("  Total:", JLabel.LEFT);
        float infectedValue = totalPopulation > 0
            ? ((float)totalInfected) / totalPopulation * 100
            : 0;
        JLabel lblValueInfectPop = new JLabel(String.valueOf(Math.floor(infectedValue * 100) / 100) + " %");
        pnlData.add(lblInfectPop);
        pnlData.add(lblValueInfectPop);

        JLabel lblInfectUnvax = new JLabel("  Unvaccinated:", JLabel.LEFT);
        float infectedUnvaxValue = totalInfected > 0
            ? ((float)totalUnvax) / totalInfected * 100
            : 0;
        JLabel lblValueInfectUnvax = new JLabel(String.valueOf(Math.floor(infectedUnvaxValue * 100) / 100) + " %");
        pnlData.add(lblInfectUnvax);
        pnlData.add(lblValueInfectUnvax);

        JLabel lblInfectOneShot = new JLabel("  Partially-vaccinated:", JLabel.LEFT);
        float infectedOneShotValue = totalInfected > 0
            ? ((float)totalOneShot) / totalInfected * 100
            : 0;
        JLabel lblValueInfectOneShot = new JLabel(String.valueOf(Math.floor(infectedOneShotValue * 100) / 100) + " %");
        pnlData.add(lblInfectOneShot);
        pnlData.add(lblValueInfectOneShot);

        JLabel lblInfectTwoShots = new JLabel("  Fully-vaccinated:", JLabel.LEFT);
        float infectedTwoShotsValue = totalInfected > 0
            ? ((float)totalTwoShots) / totalInfected * 100
            : 0;
        JLabel lblValueInfectTwoShots = new JLabel(String.valueOf(Math.floor(infectedTwoShotsValue * 100) / 100) + " %");
        pnlData.add(lblInfectTwoShots);
        pnlData.add(lblValueInfectTwoShots);

        JLabel lblInfectRecovered = new JLabel("  Recovered:", JLabel.LEFT);
        float infectedRecoveredValue = totalInfected > 0
            ? ((float)totalInfectedRecovered) / totalInfected * 100
            : 0;
        JLabel lblValueInfectRecovered = new JLabel(String.valueOf(Math.floor(infectedRecoveredValue * 100) / 100) + " %");
        pnlData.add(lblInfectRecovered);
        pnlData.add(lblValueInfectRecovered);

        JLabel lblDead = new JLabel("  Dead:", JLabel.LEFT);
        float deadValue = totalInfected > 0
            ? ((float)totalDead) / totalInfected * 100
            : 0;
        JLabel lblValueDead = new JLabel(String.valueOf(Math.floor(deadValue * 100) / 100) + " %");
        pnlData.add(lblDead);
        pnlData.add(lblValueDead);

        JLabel lblDeadUnvax = new JLabel("        - Unvaccinated:", JLabel.LEFT);
        float deadUnvaxValue = totalDead > 0
            ? ((float)totalDeadUnvax) / totalDead * 100
            : 0;
        JLabel lblValueDeadUnvax = new JLabel(String.valueOf(Math.floor(deadUnvaxValue * 100) / 100) + " %");
        pnlData.add(lblDeadUnvax);
        pnlData.add(lblValueDeadUnvax);

        JLabel lblDeadOneShot = new JLabel("        - Partially-vaccinated:", JLabel.LEFT);
        float deadOneShotValue = totalDead > 0
            ? ((float)totalDeadOneShot) / totalDead * 100
            : 0;
        JLabel lblValueDeadOneShot = new JLabel(String.valueOf(Math.floor(deadOneShotValue * 100) / 100) + " %");
        pnlData.add(lblDeadOneShot);
        pnlData.add(lblValueDeadOneShot);

        JLabel lblDeadTwoShots = new JLabel("        - Fully-vaccinated:", JLabel.LEFT);
        float deadTwoShotsValue = totalDead > 0
            ? ((float)totalDeadTwoShots) / totalDead * 100
            : 0;
        JLabel lblValueDeadTwoShots = new JLabel(String.valueOf(Math.floor(deadTwoShotsValue * 100) / 100) + " %");
        pnlData.add(lblDeadTwoShots);
        pnlData.add(lblValueDeadTwoShots);

        reportFrame.add(pnlData, BorderLayout.CENTER);

        reportFrame.pack();
        reportFrame.setLocationRelativeTo(null);
        reportFrame.setVisible(true);
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
            incrementDay();

            if (m_currentDay > TOTAL_DAYS)
            {
                stopTimer();
                showFinalReport();
            }
        }
    }

    private void move(Ball ball)
    {
        if (!ball.person().isAlive())
            return;

        if ((ball.xCoord() >= (WIDTH - 250) - (ball.diameter() + ball.MAX_PIXELS / 2))
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
            calculateInfection(b2);
        }
        else if (p2.isInfected())
        {
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
            person.setInfected(true);
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

        if (ball.xCoord() > (WIDTH - 250) - (ball.diameter() + ball.MAX_PIXELS / 2))
            ball.setXCoord(ball.xCoord() - ball.diameter());

        if (ball.yCoord() <= 0)
            ball.setYCoord(ball.yCoord() + ball.diameter());

        if (ball.yCoord() > (WIDTH - 250) - (ball.diameter() + ball.MAX_PIXELS / 2))
            ball.setYCoord(ball.yCoord() - ball.diameter());
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
