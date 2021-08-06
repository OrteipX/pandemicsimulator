/* Program Name: PersonStatus.java
 * Date: Aug 04, 2021
 */

package person;

public enum PersonStatus
{
    DEAD("DEAD"),
    INFECTED("INFECTED"),
    NOT_INFECTED("NOT_INFECTED"),
    RECOVERED("RECOVERED");

    private String m_value;

    public String value()
    {
        return m_value;
    }

    private PersonStatus(String value)
    {
        m_value = value;
    }
}
