package hr.fer.zemris.java.hw05.db;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDatabaseTest {

    @Test
    void testForJMBAG() {
        String[] inputArray = {"00001\tfirst\tstudent\t4", "00002\tsecond\tstudent\t3", "00003\tthird\tstudent\t5"};
        List<String> input = new ArrayList<>(Arrays.asList(inputArray));

        StudentDatabase database = new StudentDatabase(input);

        assertEquals(new StudentRecord("00002", "second", "student", 3),
                database.forJMBAG("00002"));
    }

    @Test
    void testFilterFalse() {
        String[] inputArray = {"00001\tfirst\tstudent\t4", "00002\tsecond\tstudent\t3", "00003\tthird\tstudent\t5"};
        List<String> input = new ArrayList<>(Arrays.asList(inputArray));

        StudentDatabase database = new StudentDatabase(input);

        List<StudentRecord> ret = database.filter((r) -> false);

        assertTrue(ret.isEmpty());
    }

    @Test
    void testFilterTrue() {
        String[] inputArray = {"00001\tfirst\tstudent\t4", "00002\tsecond\tstudent\t3", "00003\tthird\tstudent\t5"};
        List<String> input = new ArrayList<>(Arrays.asList(inputArray));

        StudentDatabase database = new StudentDatabase(input);

        List<StudentRecord> ret = database.filter((r) -> true);

        assertFalse(ret.isEmpty());

        List<StudentRecord> expected = new ArrayList<>();
        expected.add(new StudentRecord("00001", "first", "student", 4));
        expected.add(new StudentRecord("00002", "second", "student", 3));
        expected.add(new StudentRecord("00003", "third", "student", 5));

        assertEquals(expected, ret);
    }
}
