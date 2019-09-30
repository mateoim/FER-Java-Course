package searching.slagalica;

import java.util.Arrays;

/**
 * A class that represents a
 * configuration of {@code Slagalica}.
 *
 * @author Mateo Imbrišak
 */

public class KonfiguracijaSlagalice {

    /**
     * An array that represents
     * the current configuration.
     */
    private int[] polje;

    /**
     * Keeps the index of empty field
     * in the configuration.
     */
    private int indexOfSpace;

    /**
     * Default constructor that assigns the configuration.
     *
     * @param polje configuration to be assigned.
     */
    public KonfiguracijaSlagalice(int[] polje) {
        this.polje = polje;

        for (int i = 0; i < polje.length; i++) {
            if (polje[i] == 0) {
                indexOfSpace = i;
                break;
            }
        }
    }

    /**
     * Provides the current configuration.
     *
     * @return current configuration.
     */
    public int[] getPolje() {
        return polje;
    }

    /**
     * Provides the index of empty field
     * in the configuration.
     *
     * @return index of empty field.
     */
    public int indexOfSpace() {
        return indexOfSpace;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ret.append(polje[3 * i + j]).append(" ");
            }

            ret.deleteCharAt(ret.length() - 1);
            ret.append("\n");
        }
        ret.deleteCharAt(ret.length() - 1);

        return ret.toString().replace("0", "*");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KonfiguracijaSlagalice that = (KonfiguracijaSlagalice) o;
        return Arrays.equals(polje, that.polje);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(polje);
    }
}
