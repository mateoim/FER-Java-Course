package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

import java.util.Iterator;

/**
 * A class containing all example code from the last task,
 * left here for the reviewer's convenience.
 */

public class SimpleHashtableDemo {

    /**
     * Used to start the program.
     *
     * @param args nothing in this case.
     */
    public static void main(String[] args) {
        // create collection:
        SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);

        // fill data:
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5); // overwrites old grade for Ivana

        for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
        }

        System.out.println();

        for(SimpleHashtable.TableEntry<String,Integer> pair1 : examMarks) {
            for(SimpleHashtable.TableEntry<String,Integer> pair2 : examMarks) {
                System.out.printf(
                    "(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(), pair2.getValue());
            }
        }

//        System.out.println();
//
//        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
//        while(iter.hasNext()) {
//            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//            if (pair.getKey().equals("Ivana")) {
//                iter.remove(); // sam iterator kontrolirano uklanja trenutni element }
//            }
//        }

//        System.out.println();
//
//        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter2 = examMarks.iterator();
//        while (iter2.hasNext()) {
//            SimpleHashtable.TableEntry<String,Integer> pair = iter2.next();
//            if (pair.getKey().equals("Ivana")) {
//                examMarks.remove("Ivana");
//            }
//        }

        System.out.println();

        Iterator<SimpleHashtable.TableEntry<String,Integer>> iter3 = examMarks.iterator();
        while(iter3.hasNext()) {
            SimpleHashtable.TableEntry<String,Integer> pair = iter3.next();
            System.out.printf("%s => %d%n", pair.getKey(), pair.getValue()); iter3.remove();
        }
        System.out.printf("Veličina: %d%n", examMarks.size());
    }
}
