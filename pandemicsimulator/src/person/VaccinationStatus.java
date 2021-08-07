/* Program Name: VacinationStatus.java
 * Date: Aug 05, 2021
 */

package person;

public enum VaccinationStatus
{
    UNDEF("UNDEF"),
    UNDEF_REC("UNDEF_REC"),
    NO_VACCINE("NO_VACCINE"),
    ONE_SHOT("ONE_SHOT"),
    TWO_SHOTS("TWO_SHOTS");

    private String m_value;

    public String value()
    {
        return m_value;
    }

    private VaccinationStatus(String value)
    {
        m_value = value;
    }
}
