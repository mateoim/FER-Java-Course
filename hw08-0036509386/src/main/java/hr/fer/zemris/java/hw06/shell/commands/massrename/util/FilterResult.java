package hr.fer.zemris.java.hw06.shell.commands.massrename.util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that holds all info
 * for filtered names.
 *
 * @author Mateo Imbrišak
 */

public class FilterResult {

    /**
     * Keeps all groups.
     */
    private List<String> groups;

    /**
     * Default constructor.
     *
     * @param file to be filtered.
     * @param pattern based on which the filter
     *                is applied.
     */
    public FilterResult(Path file, Pattern pattern) {
        groups = new ArrayList<>();

        Matcher matcher = pattern.matcher(file.getFileName().toString());

        if (matcher.matches()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                groups.add(matcher.group(i));
            }
        }
    }

    /**
     * Provides the number of groups.
     *
     * @return number of groups.
     */
    public int numberOfGroups() {
        return groups.size();
    }

    /**
     * Provides the specified group.
     *
     * @param index of the group.
     *
     * @return requested group.
     */
    public String group(int index) {
        return groups.get(index);
    }

    @Override
    public String toString() {
        return group(0);
    }
}
