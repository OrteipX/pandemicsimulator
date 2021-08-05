/* Program Name: Immunity.java
 * Date: Aug 04, 2021
 */

package person;

public enum Immunity
{
    NO_IMMUNITY(80),
    ONE_SHOT(40),
    TWO_SHOTS(10),
    RECOVERED(1);

    private int m_value;

    public int value()
    {
        return m_value;
    }

    private Immunity(int value)
    {
        m_value = value;
    }
}
