/* Program Name: Person.java
 * Date: Aug 04, 2021
 */

package person;

public class Person
{
    private boolean m_isAlive;
    private boolean m_isInfected;
    private boolean m_hasRecovered;
    private int m_id;
    private int m_immunityValue;
    private int m_daysInfected = 0;
    private PersonStatus m_perStatus;
    private VaccinationStatus m_vacStatus;

    public Person()
    {
    }

    public Person(
            boolean isAlive,
            boolean isInfected,
            PersonStatus perStatus,
            VaccinationStatus vacStatus
            )
    {
        m_isAlive = isAlive;
        m_isInfected = isInfected;
        m_perStatus = perStatus;
        m_vacStatus = vacStatus;

        setImmunityValue(vacStatus);
    }

    public int id()
    {
        return m_id;
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

    public boolean hasRecovered()
    {
        return m_hasRecovered;
    }

    public void setRecovered(boolean hasRecovered)
    {
        m_hasRecovered = hasRecovered;
    }

    public int daysInfected()
    {
        return m_daysInfected;
    }

    public void incrementDaysInfected()
    {
        m_daysInfected += 1;
    }

    public PersonStatus personStatus()
    {
        return m_perStatus;
    }

    public void setPersonStatus(PersonStatus status)
    {
        m_perStatus = status;
    }

    public VaccinationStatus vaccinationStatus()
    {
        return m_vacStatus;
    }

    public void setVaccinationStatus(VaccinationStatus status)
    {
        m_vacStatus = status;
    }

    public int immunityValue()
    {
        return m_immunityValue;
    }

    private void setImmunityValue(VaccinationStatus status)
    {
        switch (status)
        {
            case NO_VACCINE:
                m_immunityValue = 20;
                break;
            case ONE_SHOT:
                m_immunityValue = 60;
                break;
            case TWO_SHOTS:
                m_immunityValue = 90;
                break;
        }
    }
}
