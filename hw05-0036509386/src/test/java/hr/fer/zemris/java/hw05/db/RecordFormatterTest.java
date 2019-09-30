package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecordFormatterTest {

    @Test
    void testSingleQuery() {
        StudentRecord student = new StudentRecord("0000000003", "Bosnić",
                "Andrea", 4);

        List<StudentRecord> records = new ArrayList<>();
        records.add(student);

        List<String> result = RecordFormatter.format(records);
        List<String> expected = new ArrayList<>();

        expected.add("+============+========+========+===+");
        expected.add("| 0000000003 | Bosnić | Andrea | 4 |");
        expected.add("+============+========+========+===+");
        expected.add("Records selected: 1");

        assertEquals(expected.size(), result.size());

        for (int i = 0; i < result.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void testEmptyQuery() {
        List<StudentRecord> records = new ArrayList<>();

        List<String> result = RecordFormatter.format(records);
        List<String> expected = new ArrayList<>();

        expected.add("Records selected: 0");

        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(0), result.get(0));
    }

    @Test
    void testMultipleOutputs() {
        List<StudentRecord> records = new ArrayList<>();
        records.add(new StudentRecord("0000000002", "Bakamović", "Petra", 3));
        records.add(new StudentRecord("0000000003", "Bosnić", "Andrea", 4));
        records.add(new StudentRecord("0000000004", "Božić", "Marin", 5));
        records.add(new StudentRecord("0000000005", "Brezović", "Jusufadis", 2));

        List<String> result = RecordFormatter.format(records);
        List<String> expected = new ArrayList<>();

        expected.add("+============+===========+===========+===+");
        expected.add("| 0000000002 | Bakamović | Petra     | 3 |");
        expected.add("| 0000000003 | Bosnić    | Andrea    | 4 |");
        expected.add("| 0000000004 | Božić     | Marin     | 5 |");
        expected.add("| 0000000005 | Brezović  | Jusufadis | 2 |");
        expected.add("+============+===========+===========+===+");
        expected.add("Records selected: 4");

        assertEquals(expected.size(), result.size());

        for (int i = 0; i < result.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }
}
