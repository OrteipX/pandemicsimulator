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
    private static int m_populationSize = 100;
    private int m_populationSliderSize = 2000;
    private JFrame mainFrame;
    private JPanel pnlCenter;
    private JPanel pnlTop;
    private JPanel pnlBottom;
    private JPanel pnlLeft;
    private JPanel pnlLeftUpper;
    private JPanel pnlLeftBottom;
    private JSlider sldPopulationSize;
    private JSlider sldPopulationInfected;
    private JSlider sldPopulationNoVaccine;
    private JSlider sldPopulationOneShot;
    private JSlider sldPopulationTwoShots;
    private JSlider sldPopulationRecovered;
    private JButton btnGenerate;
    private Dimension m_dim;

    public PandemicSimulator()
    {
        m_dim = Toolkit.getDefaultToolkit().getScreenSize();

        mainFrame = new JFrame("Pandemic Simulator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(true);

        mainFrame.add(buildTopPanel(), BorderLayout.NORTH);
        mainFrame.add(buildLeftPanel(), BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);

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
        pnlLeft = new JPanel();
        pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
        //pnlLeft.setPreferredSize(new Dimension(500, 600));
        pnlLeft.setBackground(Color.WHITE);

        pnlLeft.add(buildLeftUpperPanel());
        pnlLeft.add(buildLeftBottomPanel());

        return pnlLeft;
    }

    private JPanel buildLeftUpperPanel()
    {
        pnlLeftUpper = new JPanel(new FlowLayout());
        pnlLeftUpper.setBackground(Color.WHITE);

        JButton btnAbout = new JButton("About");
        btnAbout.setPreferredSize(new Dimension(80, 30));
        btnAbout.addActionListener(ev -> {
            JFrame aboutFrame = new JFrame("About");
            aboutFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            aboutFrame.setLayout(new FlowLayout());
            aboutFrame.setSize(400, 400);
            aboutFrame.setLocationRelativeTo(null);

            JLabel lblAboutTitle = new JLabel("<html><div style='margin-top: 20px'><p>Pandemic Simulator</p></div><br /></html>");
            lblAboutTitle.setFont(new Font("Serif", Font.BOLD, 16));
            aboutFrame.add(lblAboutTitle);

            String about = "<html>"
                + "<div style='margin-top: 40px;'>"
                + "<div>"
                + "<p style='text-align: center'>This software was created with the aim of creating a"
                + "<br />simulation of a pandemic and its concealment among"
                + "<br />populations with different levels of immunity.</p>"
                + "</div>"
                + "<div style='margin-top: 40px'>"
                + "<br /><p style='text-align: center'>Abdulmuamen Al Khateb</p>"
                + "<br /><p style='text-align: center'>Andre Melo</p>"
                + "<br /><p style='text-align: center'>Bilal Al-muhtadi</p>"
                + "<br /><p style='text-align: center'>Ramon Garcia</p>"
                + "</div>"
                + "</html>";

            JLabel lblAbout = new JLabel(about);
            aboutFrame.add(lblAbout);

            aboutFrame.setVisible(true);
        });

        pnlLeftUpper.add(btnAbout);

        return pnlLeftUpper;
    }

    private JPanel buildLeftBottomPanel()
    {
        JLabel lblPopulationSize = new JLabel("Population Size:");
        JLabel lblPopulationInfected = new JLabel("Infected:");
        JLabel lblPopulationNoVaccine = new JLabel("Unvaccinated:");
        JLabel lblPopulationOneShot = new JLabel("Partially-vaccinated:");
        JLabel lblPopulationTwoShots = new JLabel("Fully-vaccinated:");
        JLabel lblPopulationRecovered = new JLabel("Recovered:");

        JLabel lblPopSizeValue = new JLabel("100", JLabel.LEFT);
        JLabel lblPopInfectedValue = new JLabel("0%", JLabel.LEFT);
        JLabel lblPopNoVaccineValue = new JLabel("0%", JLabel.LEFT);
        JLabel lblPopOneShotValue = new JLabel("0%", JLabel.LEFT);
        JLabel lblPopTwoShotsValue = new JLabel("0%", JLabel.LEFT);
        JLabel lblPopRecoveredValue = new JLabel("0%", JLabel.LEFT);

        MouseListener mouseListener = new MouseListener();

        sldPopulationSize = new JSlider(JSlider.HORIZONTAL, 100, m_populationSliderSize, 100);
        sldPopulationSize.setName("POP_SIZE");
        sldPopulationSize.setBackground(Color.WHITE);
        sldPopulationSize.setPaintTicks(true);
        sldPopulationSize.setPaintLabels(true);
        sldPopulationSize.addChangeListener(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent ce)
                    {
                        JSlider source = (JSlider)ce.getSource();
                        int pop = source.getValue();
                        String value = String.valueOf(pop);
                        lblPopSizeValue.setText(value);
                    }
                });
        sldPopulationSize.addMouseListener(mouseListener);

        sldPopulationInfected = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
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

        btnGenerate = new JButton("Generate Scenario");
        btnGenerate.setEnabled(false);
        btnGenerate.addActionListener(ev -> {
            generateScenario();
        });

        JPanel sliderHolder = new JPanel(new GridLayout(7, 3, 40, 30));
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
        pnlBottom.setBackground(Color.GRAY);

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

    public void resetData()
    {
        m_populationSize = 100;
        m_populationSliderSize = 2000;
        sldPopulationSize.setMaximum(m_populationSliderSize);

        m_totalPercentage = 100;
        sldPopulationSize.setValue(0);
        sldPopulationInfected.setValue(0);
        sldPopulationNoVaccine.setValue(0);
        sldPopulationOneShot.setValue(0);
        sldPopulationTwoShots.setValue(0);
        sldPopulationRecovered.setValue(0);
    }

    private void generateScenario()
    {
        ArrayList<Person> population = new ArrayList<Person>();

        int sum = 0;

        int populationSize = m_populationSize;

        int populationInfected = sldPopulationInfected.getValue() > 0 ? populationSize * sldPopulationInfected.getValue() / 100 : 0;
        sum += populationInfected;

        int populationNoVaccine = sldPopulationNoVaccine.getValue() > 0 ? populationSize * sldPopulationNoVaccine.getValue() / 100 : 0;
        sum += populationNoVaccine;

        int populationOneShot = sldPopulationOneShot.getValue() > 0 ? populationSize * sldPopulationOneShot.getValue() / 100 : 0;
        sum += populationOneShot;

        int populationTwoShots = sldPopulationTwoShots.getValue() > 0 ? populationSize * sldPopulationTwoShots.getValue() / 100 : 0;
        sum += populationTwoShots;

        int populationRecovered = sldPopulationRecovered.getValue() > 0 ? populationSize - sum : 0;

        for (int i = 0; i < populationInfected; ++i)
            population.add(new Person(true, true, PersonStatus.INFECTED, VaccinationStatus.UNDEF));

        for (int i = 0; i < populationNoVaccine; ++i)
            population.add(new Person(true, false, PersonStatus.NOT_INFECTED, VaccinationStatus.NO_VACCINE));

        for (int i = 0; i < populationOneShot; ++i)
            population.add(new Person(true, false, PersonStatus.NOT_INFECTED, VaccinationStatus.ONE_SHOT));

        for (int i = 0; i < populationTwoShots; ++i)
            population.add(new Person(true, false, PersonStatus.NOT_INFECTED, VaccinationStatus.TWO_SHOTS));

        for (int i = 0; i < populationRecovered; ++i)
            population.add(new Person(true, false, PersonStatus.RECOVERED, VaccinationStatus.UNDEF_REC));

        Simulator simulator = new Simulator(population);
        JFrame simulationFrame = new JFrame("Simulator");
        sldPopulationSize.setValue(population.size());
        simulationFrame.setLayout(new BorderLayout());
        simulationFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        simulationFrame.setSize(simulator.width(), simulator.height());

        pnlCenter = new JPanel(new FlowLayout());
        pnlCenter.add(simulator);

        simulationFrame.add(pnlCenter, BorderLayout.CENTER);
        simulationFrame.add(buildBottomPanel(simulator), BorderLayout.SOUTH);
        simulationFrame.pack();
        simulationFrame.setLocationRelativeTo(null);
        simulationFrame.setVisible(true);
        resetData();
    }

    private class MouseListener extends MouseInputAdapter
    {
        private int m_initialValue;
        private int m_currentPercentage;

        public void mouseEntered(MouseEvent e)
        {
            m_currentPercentage = m_totalPercentage;

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
                if (finalValue > m_initialValue)
                {
                    m_totalPercentage -= (finalValue - m_initialValue);
                }
                else
                {
                    m_totalPercentage += (m_initialValue - finalValue);
                }

                if (m_totalPercentage < 0)
                {
                    if (m_initialValue > 0 || m_totalPercentage == 0)
                    {
                        source.setValue(m_initialValue);
                    }
                    else
                    {
                        source.setValue(m_currentPercentage);
                    }

                    m_totalPercentage = 0;
                }
            }
            else
            {
                if (source.getValue() > m_populationSliderSize / 2)
                {
                    m_populationSliderSize *= 2;
                    source.setMaximum(m_populationSliderSize);
                }

                m_populationSize = finalValue;
            }

            if (m_totalPercentage <= 0)
                btnGenerate.setEnabled(true);
            else
                btnGenerate.setEnabled(false);
        }
    }

    public static void main(String []args)
    {
        new PandemicSimulator();
    }
}
