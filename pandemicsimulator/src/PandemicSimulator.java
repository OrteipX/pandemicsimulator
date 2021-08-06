/* Program Name: Main.java
 * Date: Aug 04, 2021
 */

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import person.*;
import ui.*;

public class PandemicSimulator extends JFrame
{
    private static int m_totalPercentage = 100;
    private int m_initialPopulationSize = 1000;
    private boolean sldPopulationSizeClicked = false;
    private boolean sldPopulationInfectedClicked = false;
    private boolean sldPopulationNoVaccineClicked = false;
    private boolean sldPopulationOneShotClicked = false;
    private boolean sldPopulationTwoShotsClicked = false;
    private boolean sldPopulationRecoveredClicked = false;
    private JFrame mainFrame;
    private JPanel pnlCenter;
    private JPanel pnlTop;
    private JPanel pnlBottom;
    private JPanel pnlLeft;
    private JPanel pnlRight;
    private JPanel pnlLeftUpper;
    private JPanel pnlLeftBottom;
    private JSlider sldPopulationSize;
    private JSlider sldPopulationInfected;
    private JSlider sldPopulationNoVaccine;
    private JSlider sldPopulationOneShot;
    private JSlider sldPopulationTwoShots;
    private JSlider sldPopulationRecovered;

    public PandemicSimulator()
    {
        mainFrame = new JFrame("Pandemic Simulator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

        mainFrame.add(buildTopPanel(), BorderLayout.NORTH);
        mainFrame.add(buildLeftPanel(), BorderLayout.WEST);


        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private JPanel buildTopPanel()
    {
        pnlTop = new JPanel(new FlowLayout());
        pnlTop.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("PANDEMIC SIMULATOR");
        lblTitle.setFont(new Font("Serif", Font.BOLD, 30));
        pnlTop.add(lblTitle);

        return pnlTop;
    }

    private JPanel buildLeftPanel()
    {
        pnlLeft = new JPanel(new GridLayout(2, 1, 0, 0));
        //pnlLeft.setPreferredSize(new Dimension(500, 600));
        pnlLeft.setBackground(Color.WHITE);

        pnlLeft.add(buildLeftUpperPanel());
        pnlLeft.add(buildLeftBottomPanel());

        return pnlLeft;
    }

    private JPanel buildLeftUpperPanel()
    {
        pnlLeftUpper = new JPanel(new GridLayout(2, 1));
        pnlLeftUpper.setBackground(Color.WHITE);

        String titleTxt = "V1.0";
        JLabel lblTitle = new JLabel("<html><div style='text-align: center;'>" + titleTxt + "</div></html>");
        JPanel lblHolder = new JPanel(new FlowLayout());
        lblHolder.add(lblTitle);
        lblHolder.setBackground(Color.WHITE);
        pnlLeftUpper.add(lblHolder);

        JButton btnAbout = new JButton("About");
        btnAbout.setPreferredSize(new Dimension(80, 30));
        btnAbout.addActionListener(ev -> {
            JFrame aboutFrame = new JFrame("About");
            aboutFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            aboutFrame.setLayout(new FlowLayout());
            aboutFrame.setSize(300, 200);
            aboutFrame.setLocationRelativeTo(null);

            JLabel lblAboutTitle = new JLabel("<html>Pandemic Simulator<br /></html>");
            aboutFrame.add(lblAboutTitle);

            JLabel lblAbout = new JLabel("<html>This software is intented to simulate......<br />Andre Melo<br />Bilal Al<br />Ramon Garcia</html>");
            aboutFrame.add(lblAbout);

            aboutFrame.setVisible(true);
        });

        JPanel btnHolder = new JPanel(new FlowLayout());
        btnHolder.setBackground(Color.WHITE);
        btnHolder.add(btnAbout);
        pnlLeftUpper.add(btnHolder);

        return pnlLeftUpper;
    }

    private JPanel buildLeftBottomPanel()
    {
        JLabel lblPopulationSize = new JLabel("Pop. Size");
        JLabel lblPopulationInfected = new JLabel("Pop. Infected");
        JLabel lblPopulationNoVaccine = new JLabel("Pop. No-Vax");
        JLabel lblPopulationOneShot = new JLabel("Pop. 1-Shot");
        JLabel lblPopulationTwoShots = new JLabel("Pop. 2-Shots");
        JLabel lblPopulationRecovered = new JLabel("Pop. Recovered");

        JLabel lblPopSizeValue = new JLabel("0", JLabel.LEFT);
        JLabel lblPopInfectedValue = new JLabel("0%", JLabel.LEFT);
        JLabel lblPopNoVaccineValue = new JLabel("0%", JLabel.LEFT);
        JLabel lblPopOneShotValue = new JLabel("0%", JLabel.LEFT);
        JLabel lblPopTwoShotsValue = new JLabel("0%", JLabel.LEFT);
        JLabel lblPopRecoveredValue = new JLabel("0%", JLabel.LEFT);

        MouseListener mouseListener = new MouseListener();

        sldPopulationSize = new JSlider(JSlider.HORIZONTAL, 0, m_initialPopulationSize, 0);
        sldPopulationSize.setName("POP_SIZE");
        sldPopulationSize.setBackground(Color.WHITE);
        sldPopulationSize.setPaintTicks(true);
        sldPopulationSize.setPaintLabels(true);
        sldPopulationSize.addChangeListener(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent ce)
                    {
                        JSlider source = (JSlider)ce.getSource();
                        String value = String.valueOf(source.getValue());
                        lblPopSizeValue.setText(value);
                    }
                });
        sldPopulationSize.addMouseListener(mouseListener);

        sldPopulationInfected = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        sldPopulationInfected.setEnabled(false);
        sldPopulationInfected.setName("POP_INFECTED");
        sldPopulationInfected.setBackground(Color.WHITE);
        sldPopulationInfected.setMajorTickSpacing(25);
        sldPopulationInfected.setMinorTickSpacing(5);
        sldPopulationInfected.setPaintTicks(true);
        sldPopulationInfected.setPaintLabels(true);
        sldPopulationInfected.addChangeListener(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent ce)
                    {
                        JSlider source = (JSlider)ce.getSource();
                        String value = String.valueOf(source.getValue());
                        lblPopInfectedValue.setText(value + "%");
                    }
                });
        sldPopulationInfected.addMouseListener(mouseListener);

        sldPopulationNoVaccine = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        sldPopulationNoVaccine.setEnabled(false);
        sldPopulationNoVaccine.setName("POP_NO_VACCINE");
        sldPopulationNoVaccine.setBackground(Color.WHITE);
        sldPopulationNoVaccine.setMajorTickSpacing(25);
        sldPopulationNoVaccine.setMinorTickSpacing(5);
        sldPopulationNoVaccine.setPaintTicks(true);
        sldPopulationNoVaccine.setPaintLabels(true);
        sldPopulationNoVaccine.addChangeListener(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent ce)
                    {
                        JSlider source = (JSlider)ce.getSource();
                        String value = String.valueOf(source.getValue());
                        lblPopNoVaccineValue.setText(value + "%");
                    }
                });
        sldPopulationNoVaccine.addMouseListener(mouseListener);

        sldPopulationOneShot = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        sldPopulationOneShot.setEnabled(false);
        sldPopulationOneShot.setName("POP_ONE_SHOT");
        sldPopulationOneShot.setBackground(Color.WHITE);
        sldPopulationOneShot.setMajorTickSpacing(25);
        sldPopulationOneShot.setMinorTickSpacing(5);
        sldPopulationOneShot.setPaintTicks(true);
        sldPopulationOneShot.setPaintLabels(true);
        sldPopulationOneShot.addChangeListener(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent ce)
                    {
                        JSlider source = (JSlider)ce.getSource();
                        String value = String.valueOf(source.getValue());
                        lblPopOneShotValue.setText(value + "%");
                    }
                });
        sldPopulationOneShot.addMouseListener(mouseListener);

        sldPopulationTwoShots = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        sldPopulationTwoShots.setEnabled(false);
        sldPopulationTwoShots.setName("POP_TWO_SHOTS");
        sldPopulationTwoShots.setBackground(Color.WHITE);
        sldPopulationTwoShots.setMajorTickSpacing(25);
        sldPopulationTwoShots.setMinorTickSpacing(5);
        sldPopulationTwoShots.setPaintTicks(true);
        sldPopulationTwoShots.setPaintLabels(true);
        sldPopulationTwoShots.addChangeListener(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent ce)
                    {
                        JSlider source = (JSlider)ce.getSource();
                        String value = String.valueOf(source.getValue());
                        lblPopTwoShotsValue.setText(value + "%");
                    }
                });
        sldPopulationTwoShots.addMouseListener(mouseListener);

        sldPopulationRecovered = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        sldPopulationRecovered.setEnabled(false);
        sldPopulationRecovered.setName("POP_RECOVERED");
        sldPopulationRecovered.setBackground(Color.WHITE);
        sldPopulationRecovered.setMajorTickSpacing(25);
        sldPopulationRecovered.setMinorTickSpacing(5);
        sldPopulationRecovered.setPaintTicks(true);
        sldPopulationRecovered.setPaintLabels(true);
        sldPopulationRecovered.addChangeListener(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent ce)
                    {
                        JSlider source = (JSlider)ce.getSource();
                        String value = String.valueOf(source.getValue());
                        lblPopRecoveredValue.setText(value + "%");
                    }
                });
        sldPopulationRecovered.addMouseListener(mouseListener);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(ev -> {
            resetData();
        });

        JButton btnGenerate = new JButton("Generate Scenario");
        btnGenerate.addActionListener(ev -> {
            generateScenario();
        });

        JPanel sliderHolder = new JPanel(new GridLayout(7, 3, 20, 20));
        sliderHolder.setBackground(Color.WHITE);

        sliderHolder.add(lblPopulationSize);
        sliderHolder.add(sldPopulationSize);
        sliderHolder.add(lblPopSizeValue);

        sliderHolder.add(lblPopulationInfected);
        sliderHolder.add(sldPopulationInfected);
        sliderHolder.add(lblPopInfectedValue);

        sliderHolder.add(lblPopulationNoVaccine);
        sliderHolder.add(sldPopulationNoVaccine);
        sliderHolder.add(lblPopNoVaccineValue);

        sliderHolder.add(lblPopulationOneShot);
        sliderHolder.add(sldPopulationOneShot);
        sliderHolder.add(lblPopOneShotValue);

        sliderHolder.add(lblPopulationTwoShots);
        sliderHolder.add(sldPopulationTwoShots);
        sliderHolder.add(lblPopTwoShotsValue);

        sliderHolder.add(lblPopulationRecovered);
        sliderHolder.add(sldPopulationRecovered);
        sliderHolder.add(lblPopRecoveredValue);

        sliderHolder.add(btnReset);
        sliderHolder.add(new JLabel());
        sliderHolder.add(btnGenerate);

        pnlLeftBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlLeftBottom.setBackground(Color.WHITE);
        pnlLeftBottom.add(sliderHolder);

        return pnlLeftBottom;
    }

    private JPanel buildBottomPanel(Simulator simulator)
    {
        pnlBottom = new JPanel(new FlowLayout());
        pnlBottom.setBackground(Color.WHITE);

        JButton btnStart = new JButton("Start");
        btnStart.setPreferredSize(new Dimension(80, 30));

        JButton btnStop = new JButton("Stop");
        btnStop.setPreferredSize(new Dimension(80, 30));

        btnStart.addActionListener(ev -> {
            simulator.startTimer();
        });

        btnStop.addActionListener(ev -> {
            simulator.stopTimer();
        });

        pnlBottom.add(btnStart);
        pnlBottom.add(btnStop);

        return pnlBottom;
    }

    private void resetData()
    {
        m_totalPercentage = 100;
        m_initialPopulationSize = 1000;
        sldPopulationSize.setMaximum(m_initialPopulationSize);
        sldPopulationInfected.setMaximum(m_totalPercentage);
        sldPopulationNoVaccine.setMaximum(m_totalPercentage);
        sldPopulationOneShot.setMaximum(m_totalPercentage);
        sldPopulationTwoShots.setMaximum(m_totalPercentage);
        sldPopulationRecovered.setMaximum(m_totalPercentage);

        sldPopulationSize.setValue(0);
        sldPopulationInfected.setValue(0);
        sldPopulationNoVaccine.setValue(0);
        sldPopulationOneShot.setValue(0);
        sldPopulationTwoShots.setValue(0);
        sldPopulationRecovered.setValue(0);

        sldPopulationSize.setEnabled(true);
        sldPopulationInfected.setEnabled(false);
        sldPopulationNoVaccine.setEnabled(false);
        sldPopulationOneShot.setEnabled(false);
        sldPopulationTwoShots.setEnabled(false);
        sldPopulationRecovered.setEnabled(false);

        sldPopulationSizeClicked = false;
        sldPopulationInfectedClicked = false;
        sldPopulationNoVaccineClicked = false;
        sldPopulationOneShotClicked = false;
        sldPopulationTwoShotsClicked = false;
        sldPopulationRecoveredClicked = false;
    }

    private void generateScenario()
    {
        ArrayList<Person> population = new ArrayList<Person>();

        // TODO
        // 600 PEOPLE
        // 100% 80% 55% 20%
        // 20% ONE SHOT
        // 25% TWO SHOTS
        // 35% NO IMMUNITY
        // 20% INFECTED

        int sum = 0;

        int populationSize = m_initialPopulationSize;

        int populationInfected = populationSize * sldPopulationInfected.getValue() / 100;
        sum += populationInfected;

        int populationNoVaccine = populationSize * sldPopulationNoVaccine.getValue() / 100;
        sum += populationNoVaccine;

        int populationOneShot = populationSize * sldPopulationOneShot.getValue() / 100;
        sum += populationOneShot;

        int populationTwoShots = populationSize * sldPopulationTwoShots.getValue() / 100;
        sum += populationTwoShots;

        int populationRecovered = populationSize - sum;
        System.out.println(populationRecovered);

        for (int i = 0; i < populationInfected; ++i)
            population.add(new Person(true, true, PersonStatus.INFECTED, VaccinationStatus.NO_VACCINE));

        for (int i = 0; i < populationNoVaccine; ++i)
            population.add(new Person(true, false, PersonStatus.NOT_INFECTED, VaccinationStatus.NO_VACCINE));

        for (int i = 0; i < populationOneShot; ++i)
            population.add(new Person(true, false, PersonStatus.NOT_INFECTED, VaccinationStatus.ONE_SHOT));

        for (int i = 0; i < populationTwoShots; ++i);
            population.add(new Person(true, false, PersonStatus.NOT_INFECTED, VaccinationStatus.TWO_SHOTS));

        for (int i = 0; i < populationRecovered; ++i)
            population.add(new Person(true, false, PersonStatus.RECOVERED, VaccinationStatus.TWO_SHOTS));

        Simulator simulator = new Simulator(population);
        JFrame simulationFrame = new JFrame("Simulator");
        simulationFrame.setLayout(new BorderLayout());
        simulationFrame.setLocationRelativeTo(null);
        simulationFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        simulationFrame.setSize(simulator.width(), simulator.height());

        pnlCenter = new JPanel(new FlowLayout());
        pnlCenter.add(simulator);
        simulationFrame.add(pnlCenter, BorderLayout.CENTER);

        simulationFrame.add(buildBottomPanel(simulator), BorderLayout.SOUTH);
        simulationFrame.pack();
        simulationFrame.setVisible(true);
    }

    private void setMaxSlidersValue(JSlider slider)
    {
        if (slider.getName().equals("POP_SIZE"))
        {
            if (slider.getValue() > (m_initialPopulationSize / 2))
            {
                sldPopulationSize.setMaximum(m_initialPopulationSize * 2);
            }

            if (sldPopulationSizeClicked)
            {
                sldPopulationInfected.setEnabled(true);
            }
        }
        else if (slider.getName().equals("POP_INFECTED"))
        {
            if(slider.isEnabled())
            {
                sldPopulationNoVaccine.setMaximum(m_totalPercentage);
                sldPopulationOneShot.setMaximum(m_totalPercentage);
                sldPopulationTwoShots.setMaximum(m_totalPercentage);
                sldPopulationRecovered.setMaximum(m_totalPercentage);
            }

            if (sldPopulationInfectedClicked)
            {
                sldPopulationInfected.setEnabled(false);
                sldPopulationNoVaccine.setEnabled(true);
                sldPopulationInfectedClicked = false;
            }
        }
        else if (slider.getName().equals("POP_NO_VACCINE"))
        {
            if (slider.isEnabled())
            {
                sldPopulationOneShot.setMaximum(m_totalPercentage);
                sldPopulationTwoShots.setMaximum(m_totalPercentage);
                sldPopulationRecovered.setMaximum(m_totalPercentage);
            }

            if (sldPopulationNoVaccineClicked)
            {
                sldPopulationNoVaccine.setEnabled(false);
                sldPopulationOneShot.setEnabled(true);
                sldPopulationNoVaccineClicked = false;
            }
        }
        else if (slider.getName().equals("POP_ONE_SHOT"))
        {
            if (slider.isEnabled())
            {
                sldPopulationTwoShots.setMaximum(m_totalPercentage);
                sldPopulationRecovered.setMaximum(m_totalPercentage);
            }

            if (sldPopulationOneShotClicked)
            {
                sldPopulationTwoShots.setEnabled(true);
                sldPopulationOneShot.setEnabled(false);
                sldPopulationOneShotClicked = false;
            }
        }
        else if (slider.getName().equals("POP_TWO_SHOTS"))
        {
            if (slider.isEnabled())
            {
                sldPopulationRecovered.setMaximum(m_totalPercentage);
            }

            if (sldPopulationTwoShotsClicked)
            {
                sldPopulationTwoShots.setEnabled(false);
                sldPopulationRecovered.setEnabled(true);
                sldPopulationTwoShotsClicked = false;
            }
        }
        else
        {
            if (slider.getValue() < m_totalPercentage && slider.isEnabled())
            {
                slider.setValue(m_totalPercentage + slider.getValue());
                sldPopulationRecovered.setEnabled(false);
                sldPopulationRecoveredClicked = false;
            }
        }
    }

    private void checkClicked(JSlider slider)
    {
        if (slider.getName().equals("POP_SIZE") && slider.isEnabled())
            sldPopulationSizeClicked = true;
        else if (slider.getName().equals("POP_NO_VACCINE") && slider.isEnabled())
            sldPopulationNoVaccineClicked = true;
        else if (slider.getName().equals("POP_INFECTED") && slider.isEnabled())
            sldPopulationInfectedClicked = true;
        else if (slider.getName().equals("POP_ONE_SHOT") && slider.isEnabled())
            sldPopulationOneShotClicked = true;
        else if (slider.getName().equals("POP_TWO_SHOTS") && slider.isEnabled())
            sldPopulationTwoShotsClicked = true;
        else if (slider.getName().equals("POP_RECOVERED") && slider.isEnabled())
            sldPopulationRecoveredClicked = true;
    }

    private class MouseListener extends MouseInputAdapter
    {
        private int m_initialValue;

        public void mouseClicked(MouseEvent e)
        {
            JSlider source = (JSlider)e.getSource();
            checkClicked(source);
        }

        public void mousePressed(MouseEvent e)
        {
            JSlider source = (JSlider)e.getSource();
            checkClicked(source);
        }

        public void mouseEntered(MouseEvent e)
        {
            JSlider source = (JSlider)e.getSource();
            if (!source.getName().equals("POP_SIZE"))
                m_initialValue = source.getValue();
        }

        public void mouseExited(MouseEvent e)
        {
            JSlider source = (JSlider)e.getSource();
            int finalValue = source.getValue();

            if (!source.getName().equals("POP_SIZE"))
            {
                if (m_initialValue != finalValue)
                {
                    if (finalValue > m_initialValue)
                        m_totalPercentage -= (finalValue - m_initialValue);
                    else
                        m_totalPercentage += (m_initialValue - finalValue);
                }
            }
            else
            {
                m_initialPopulationSize = finalValue;
            }

            setMaxSlidersValue(source);
        }
    }

    public static void main(String []args)
    {
        new PandemicSimulator();
    }
}
