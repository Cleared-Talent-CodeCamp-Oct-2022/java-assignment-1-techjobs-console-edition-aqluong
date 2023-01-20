import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by (type 'x' to quit):", actionChoices);

            if (actionChoice == null) {
                break;
            } else if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term:");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    // This is on line 97 in JobData.java and around 23:00 in the Part 2 video
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                    // this is where part 2 of the Assignment takes place but in JobData.java line 71
                    // https://www.youtube.com/watch?v=YEa3TaqYO0U&list=PLSNijfo6mLReVIWdj2iA5hc4AvryGtT8x&index=3 @ 9.00min
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        int i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            if (in.hasNextInt()) {
                choiceIdx = in.nextInt();
                in.nextLine();
            } else {
                String line = in.nextLine();
                boolean shouldQuit = line.equals("x");
                if (shouldQuit) {
                    return null;
                }
            }

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while (!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    // Implement printJobs Here!!! Look at book example for "PRECISE" format

    // Refer back to lecture about ArrayList and HashMap using that format.
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {

        if (someJobs.size() > 0) {
            // Need to nest like line 11 String, String defines the value 'TYPES' for map's <key, value>
            // 3.4.1.2 for-each declare the iterator variable "someJob
            for (HashMap<String, String> someJob : someJobs) {
                // Needed this out to look like the example
                System.out.println("\n*****");
                // Reference to https://education.launchcode.org/java-web-development/chapters/control-flow-and-collections/hashmap.html line 38-41
                for (Map.Entry<String, String> data : someJob.entrySet()) {
                    System.out.println(data.getKey() + ": " + data.getValue());
                }
                System.out.println("*****");
                // needed the new line to look like the example but on the next line.
            }
        } else {
            System.out.println("No Results");
        }

//        System.out.println("printJobs is not implemented yet");
    }
}

