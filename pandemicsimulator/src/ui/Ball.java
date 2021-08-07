/* Program Name: Ball.java
 * Date: Aug 04, 2021
 */

package ui;

import java.awt.*;
import person.*;

public class Ball
{
    public final int MAX_PIXELS = 5;
    private int m_diameter;
    private int m_xCoord;
    private int m_yCoord;
    private int m_xIncrement;
    private int m_yIncrement;
    private Color m_color;
    private Person m_person;

    public Ball(
            Person person,
            int diameter,
            int containerWidth,
            int containerHeight
            )
    {
        m_person = person;

        m_diameter = diameter;

        if (person.isAlive())
        {
            if (person.isInfected())
            {
                m_color = Color.RED;
            }
            else
            {
                if (person.vaccinationStatus() == VaccinationStatus.NO_VACCINE)
                {
                    m_color = Color.BLUE;
                }
                else if (person.vaccinationStatus() == VaccinationStatus.ONE_SHOT)
                {
                    m_color = Color.CYAN;
                }
                else if (person.vaccinationStatus() == VaccinationStatus.TWO_SHOTS)
                {
                    m_color = Color.YELLOW;
                }
                else if (person.personStatus() == PersonStatus.RECOVERED)
                {
                    m_color = Color.GREEN;
                }
            }
        }
        else
        {
            m_color = Color.BLACK;
        }

        while (true)
        {
            m_xCoord = (int)(Math.random() * containerWidth);

            if (m_xCoord >= m_diameter && (m_xCoord <= (containerWidth - 2 * m_diameter)))
                break;
        }

        while (true)
        {
            m_yCoord = (int)(Math.random() * containerHeight);

            if (m_yCoord >= m_diameter && (m_yCoord <= (containerHeight - 2 * m_diameter)))
                break;
        }

        while (true)
        {
            int inversePoint = 1;

            if ((int)(Math.random() * 2) == 1)
                inversePoint *= -1;

            m_xIncrement = (int)(Math.random() * (MAX_PIXELS + 1)) * inversePoint;
            m_yIncrement = (int)(Math.random() * (MAX_PIXELS + 1)) * inversePoint;

            if (m_xIncrement != 0 && m_yIncrement != 0)
                break;
        }
    }

    public Person person()
    {
        return m_person;
    }

    public int diameter()
    {
        return m_diameter;
    }

    public void setDiameter(int diameter)
    {
        m_diameter = diameter;
    }

    public int xCoord()
    {
        return m_xCoord;
    }

    public void setXCoord(int xCoord)
    {
        m_xCoord = xCoord;
    }

    public int yCoord()
    {
        return m_yCoord;
    }

    public void setYCoord(int yCoord)
    {
        m_yCoord = yCoord;
    }

    public int xIncrement()
    {
        return m_xIncrement;
    }

    public void setXIncrement(int xIncrement)
    {
        m_xIncrement = xIncrement;
    }

    public int yIncrement()
    {
        return m_yIncrement;
    }

    public void setYIncrement(int yIncrement)
    {
        m_yIncrement = yIncrement;
    }

    public Color color()
    {
        return m_color;
    }

    public void setColor(Color color)
    {
        m_color = color;
    }
}
