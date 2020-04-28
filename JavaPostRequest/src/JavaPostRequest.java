import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JavaPostRequest {

    // Different text colours in console
    enum Color{
        //Color reset
        RESET("\033[0m"),
        RED_UNDERLINED("\033[4;31m"),     // RED
        YELLOW_UNDERLINED("\033[4;33m"),  // YELLOW
        GREEN_UNDERLINED("\033[4;36m");    // GREEN
        private final String code;

        Color(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println("==================================");
        System.out.println("===Sanity check app is started===");

        // Get path to project root folder and print it
        String rootFolderPth = System.getProperty("user.dir");
        System.out.println("Current project root folder is: " + rootFolderPth);

        // Check if Tests folder exist in project root folder
        if ((Files.exists(Paths.get(rootFolderPth + "/Tests"))) &&
                (Files.exists(Paths.get((rootFolderPth + "/Tests/RequestsParams"))))) {

            // Get path to /Tests/RequestsParams folder in project root folder
            String testsFolderPth = rootFolderPth + "/Tests/RequestsParams";

            // Set date format for date and time
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            // Get current date and time and add it like additional info in LOG file name
            Date date = new Date();

            // Create File obj. with for test results
            File logFileTestResults = new File(rootFolderPth + "/Tests/LogFileTestResults_" + date + "_.txt");

            // Crete BufferedWriter to write to LOG file and set true for append mode
            BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFileTestResults, true));

            // Count all files from Tests folder from current root folder
            File testFiles = new File(testsFolderPth + "/");

            // Counter for all files in Tests folder from root folder
            int fileCount = testFiles.list().length;

            // Create LOG file with the output for test results and print it
            logFileTestResults.createNewFile();
            System.out.println("Log file created: " + logFileTestResults);


            // Check if test files exists in Tests folder in project root folder
            // Pint and log message no test files for execution and quit
            // OR Print abd log the number of files from Tests folder from root folder
            if (fileCount == 0) {
                System.out.println("No test request files for execution in:" + testsFolderPth);
                logWriter.write("No test request files for execution in:" + testsFolderPth);
                logWriter.close();
                System.exit(0);
            } else {
                // Loop to repeat all tests if you want
                String yesOrNo;

                // Counter for tests
                int testsNumber = 1;

                // Read file from Tests folder from current root folder
                File testFile = new File(testsFolderPth);
                String[] testFilesList = testFile.list();

                System.out.print("Test files: " + fileCount + " ");
                logWriter.write("Test files: " + fileCount + " ");

                // Print the names of files from Tests folder from root folder
                System.out.println(Arrays.toString(testFilesList));
                logWriter.write(Arrays.toString(testFilesList) + "\n");

                do {
                    // BufferedReader using System.in for the answer "Are you want to repeat all tests"
                    BufferedReader yesNoBR;
                    // Loop for all test files
                    for (String name : testFilesList) {
                        // Buffer reader to read test file content
                        BufferedReader fileBR = new BufferedReader(new FileReader(testsFolderPth + "/" + name));

                        // Read files with parameters line by line int's one line
                        String currentLineStr;

                        // Counter to read parameters from files
                        int paramNum = 0;

                        // Array to keep all parameters from files
                        String[] inputParamsArray = new String[4];

                        // Declare URL for connection to API URL: Root API endpoint + Transaction typ
                        URL apiURL;

                        // Declare response code
                        int responseCode;

                        // Buffer reader to read response code
                        BufferedReader responseCodeBR = null;

                        // Response row numbers
                        int responseRowNum = 1;

                        System.out.println("---------------------------------");
                        logWriter.write("---------------------------------" + "\n");
                        System.out.println("Test-" + testsNumber + " file: " + name + " STARTED");
                        logWriter.write("Test-" + testsNumber + " file: " + name + " STARTED" + "\n");

                        // Read parameters from file
                        while ((currentLineStr = fileBR.readLine()) != null) {
                            inputParamsArray[paramNum] = currentLineStr;
                            if (paramNum == 0) {
                                System.out.println("Root API endpoint(param-1): " + inputParamsArray[paramNum]);
                                logWriter.write("Root API endpoint(param-1): " + inputParamsArray[paramNum] + "\n");
                            }
                            if (paramNum == 1) {
                                System.out.println("Transaction type(param-2): " + inputParamsArray[paramNum]);
                                logWriter.write("Transaction type(param-2): " + inputParamsArray[paramNum] + "\n");
                            }
                            if (paramNum == 2) {
                                System.out.println("Authorization (param-3): " + inputParamsArray[paramNum]);
                                logWriter.write("Authorization (param-3): " + inputParamsArray[paramNum] + "\n");
                            }
                            if (paramNum == 3) {
                                System.out.println("Transaction body(param-3): " + inputParamsArray[paramNum]);
                                logWriter.write("Transaction body(param-3): " + inputParamsArray[paramNum] + "\n");
                            }
                            paramNum++;
                        }

                        // Initialized URL for connection to API URL: Root API endpoint + Transaction type
                        apiURL = new URL(inputParamsArray[0] + inputParamsArray[1]);

                        // Connect to the Root API endpoint with the specific Transaction type
                        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

                        // Transaction Type HARDCODED!
                        connection.setRequestMethod("POST");

                        // Transaction header HARDCODED!
                        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        connection.setRequestProperty("Authorization", inputParamsArray[2]);

                        // Set it to true if you want to send (output) a request body
                        connection.setDoOutput(true);

                        // OutputStream to add body to request
                        OutputStream bodyOutStr = connection.getOutputStream();

                        // Create OutputStream to add the body to request encoded into bytes using UTF-8
                        OutputStreamWriter bodyOutStrWr = new OutputStreamWriter(bodyOutStr, "UTF-8");

                        // Add OutputStream content(body) to request
                        bodyOutStrWr.write(inputParamsArray[3]);

                        // Flushing a OutputStreamWriter
                        bodyOutStrWr.flush();

                        // Close a OutputStreamWriter
                        bodyOutStrWr.close();

                        // Close a OutputStream
                        bodyOutStr.close();

                        connection.connect();

                        // Get response code
                        responseCode = connection.getResponseCode();

                        // Print and log response code
                        System.out.print(Color.YELLOW_UNDERLINED);
                        System.out.println("Transaction Status Codes is: " + responseCode);
                        System.out.print(Color.RESET);
                        logWriter.write("Transaction Status Codes is: " + responseCode + "\n");

                        // Check response code and print and log the body content
                        if (responseCode == 200) {
                            responseCodeBR = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String strCurrentLine;
                            while ((strCurrentLine = responseCodeBR.readLine()) != null) {
                                System.out.println("Transaction response Row-" + responseRowNum + " is: " + strCurrentLine);
                                logWriter.write("Transaction response Row-" + responseRowNum + " is: " + strCurrentLine + "\n");

                                // Check for APPROVED transaction without Json object
                                if (strCurrentLine.contains("\"status\":\"approved\"")) {
                                    System.out.print(Color.GREEN_UNDERLINED);
                                    System.out.println("Transaction: APPROVED");
                                    System.out.print(Color.RESET);
                                    logWriter.write("Transaction: APPROVED" + "\n");
                                }
                                responseRowNum++;
                            }
                        } else {
                            responseCodeBR = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                            String strCurrentLine;
                            while ((strCurrentLine = responseCodeBR.readLine()) != null) {
                                System.out.println("Transaction response Row-" + responseRowNum + " is: " + strCurrentLine);
                                logWriter.write("Transaction response Row-" + responseRowNum + " is: " + strCurrentLine + "\n");
                                responseRowNum++;
                            }
                            System.out.print(Color.RED_UNDERLINED);
                            System.out.println("Transaction: NOT APPROVED");
                            System.out.print(Color.RESET);
                            logWriter.write("Transaction: NOT APPROVED" + "\n");
                        }
                        responseRowNum = 1;

                        connection.disconnect();

                        System.out.println("Test-" + testsNumber + " file: " + name + " FINISHED");
                        logWriter.write("Test-" + testsNumber + " file: " + name + " FINISHED" + "\n");
                        System.out.println("---------------------------------");
                        logWriter.write("---------------------------------" + "\n");
                        // Increase test numbers
                        testsNumber++;
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    testsNumber = 1;
                    System.out.println("==================================");
                    logWriter.write("==================================" + "\n");
                    System.out.println("===Sanity check app finished all tests===");
                    logWriter.write("===Sanity check app finished all tests===" + "\n");
                    System.err.println("PRESS Y or y if you want to repeat or PRESS any key to quit");

                    // Get the answer "Are you want to repeat all tests"
                    yesNoBR = new BufferedReader(new InputStreamReader(System.in));

                    // Tup the answer "Are you want to repeat all tests" in lower case
                    yesOrNo = yesNoBR.readLine().toLowerCase();

                    // Check the answer and print and log the answer
                    if (yesOrNo.equals("y")) {
                        System.err.println("===Sanity check app repeat all tests at: " + date + "===");
                        logWriter.write("===Sanity check app repeat all tests at: " + date + "===" + "\n");
                    }

                    //  Add a little enough time out 3 seconds
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (yesOrNo.equals("y"));

                // Print and log that "Sanity check app is CLOSED"
                System.out.println("===Sanity check app is CLOSED===");
                logWriter.write("===Sanity check app is CLOSED===" + "\n");
                // Close logWrite to finish to write the log!
                logWriter.close();
            }
        } else {
            // Print if needed folders not exist
            System.out.println("The folder: " + rootFolderPth + "/Tests" + " NOT EXIST!");
            System.out.println("AND/OR!");
            System.out.println("The folder: " + rootFolderPth + "/Tests/RequestsParams" + " NOT EXIST!");
        }
    }
}
