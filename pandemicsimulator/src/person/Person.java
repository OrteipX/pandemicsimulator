/* Program Name: Person.java
 * Date: Aug 04, 2021
 */

package person;

public class Person
{
    private boolean m_isAlive;
    private boolean m_isInfected;
    private int m_immunityStatus;

    public Person()
    {
    }

    public Person(boolean isAlive, boolean isInfected)
    {
        m_isAlive = isAlive;
        m_isInfected = isInfected;
    }

    public Person(
            boolean isAlive,
            boolean isInfected,
            Immunity immunityStatus
            )
    {
        m_isAlive = isAlive;
        m_isInfected = isInfected;
        m_immunityStatus = immunityStatus.value();
    }

    public boolean isAlive()
    {
        return m_isAlive;
    }

    public void setAlive(boolean isAlive)
    {
        m_isAlive = isAlive;
    }

    public boolean isInfected()
    {
        return m_isInfected;
    }

    public void setInfected(boolean isInfected)
    {
        m_isInfected = isInfected;
    }

    public int immunityStatus()
    {
        return m_immunityStatus;
    }

    public void setImmunityStatus(Immunity immunityStatus)
    {
        m_immunityStatus = immunityStatus.value();
    }
}
