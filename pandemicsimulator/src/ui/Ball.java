/* Program Name: Ball.java
 * Date: Aug 04, 2021
 */

package ui;

import java.awt.*;
import person.Immunity;
import person.Person;

public class Ball
{
    private final int MAX_PIXELS = 5;
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
                if (person.immunityStatus() == Immunity.NO_IMMUNITY.value())
                {
                    m_color = Color.BLUE;
                }
                else if (person.immunityStatus() == Immunity.ONE_SHOT.value())
                {
                    m_color = Color.CYAN;
                }
                else if (person.immunityStatus() == Immunity.TWO_SHOTS.value())
                {
                    m_color = Color.YELLOW;
                }
                else if (person.immunityStatus() == Immunity.RECOVERED.value())
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

            if (m_xCoord >= 0 && (m_xCoord <= containerWidth - m_diameter))
                break;
        }

        while (true)
        {
            m_yCoord = (int)(Math.random() * containerHeight);

            if (m_yCoord >= 0 && (m_yCoord <= containerWidth - m_diameter))
                break;
        }

        while (true)
        {
            m_xIncrement = (int)(Math.random() * (MAX_PIXELS + 1));
            m_yIncrement = (int)(Math.random() * (MAX_PIXELS + 1));

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
