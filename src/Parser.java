import java.io.*;
import java.util.Scanner;

public class Parser {

    //Create a BST tree of Integer type
    private BST<Integer> mybst = new BST<>();

    public Parser(String filename) throws IOException {
        process(new File(filename));
    }

    //This file process all inputs into a String array. It removes redundant spaces for each input command
    //It assumes only one command per line/no more than two words per line
    public void process(File input) throws IOException {
        int z = 0; //counter for lines with commands in input file
        try (Scanner fileReader = new Scanner(input)) {
            while (fileReader.hasNextLine()) {
                if (!fileReader.nextLine().isEmpty()) {
                    z++;
                }
            }
        } //counts lines with commands -- necessary for creating the size of the array
        catch(FileNotFoundException e) {
            System.out.println("File does not exist");
        } //case that input file doesn't exist


        String[][] content = new String[z][2]; //string to hold the file contents
        //This part reads the inputs from the file
        try (Scanner fileReader = new Scanner(input)) {

            for (int i = 0; fileReader.hasNextLine(); i++) {
                Scanner line = new Scanner(fileReader.nextLine());
                int j;
                if (!line.hasNext()) {
                    i--;
                    continue;
                } //If there is nothing in the line, it is skipped
                for (j = 0; line.hasNext(); j++) {
                    if (j == 2) {
                        writeToFile("Invalid Command", "./result.txt");
                        content[i] = null;
                        i--;
                    } //If there are more than two words on one line, the command will be considered invalid and the array in content overwritten with value null
                    else {
                        content[i][j] = line.next();
                    }
                } //inputs on the same line will be added to the array
                line.close();
            } //each line will be an element in an array of arrays
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
        } //catch for the case the input file does not exist

        //the following clears the result file if it has already been in use
        try {
            clearFile("./result.txt");
        } catch (IOException e) {
            System.out.println("File does not exist or error in clearing file. If file doesn't exist, it will be created after this is printed.");
        }

        for (String[] strings : content) {
            operate_BST(strings);
        } //This method calls the operate_BST method to run the commands

    }

    //This method determines the incoming command and operates on the binary search tree
    //It will write operations to the file as directed, and only works if the second value in the array is an Integer object -- parser must be changed for another type of object
    public void operate_BST(String[] command) throws IOException {
        int input;
        switch (command[0]) {
            case "insert":
                try {
                    input = Integer.parseInt(command[1]);
                    if (mybst.search(input, mybst.getRoot()) == null) {
                        mybst.insert(input, mybst.getRoot());
                        writeToFile("insert " + input, "./result.txt");
                    }
                    else {
                        writeToFile("insert failed", "./result.txt");
                    } //writes to file that insert failed if the value is already in the tree
                } //converts the input value to an Integer to be processed in BST
                catch (NumberFormatException e) {
                    writeToFile("Invalid Command", "./result.txt");
                } //error case -- the input is not of the right format
                break;
            case "search":
                try {
                    input = Integer.parseInt(command[1]); //converts the input value to an Integer to be processed in BST
                    if (mybst.search(input, mybst.getRoot()) == null){
                        writeToFile("search failed", "./result.txt");
                    } //If the value is not found, "search failed" is written to the output file
                    else {
                        writeToFile("found " + input, "./result.txt");
                    }  //If the value is found, it is written to the output file
                }
                catch (NumberFormatException e) {
                    writeToFile("Invalid Command", "./result.txt");
                }//error case -- the input is not of the right format
                break;
            case "remove":
                try {
                    input = Integer.parseInt(command[1]); //converts the input value to an Integer to be processed in BST
                    if (mybst.remove(input) != null) {
                        writeToFile("remove " + input, "./result.txt");
                    } //if the value is successfully removed, thus will be written to the output file
                    else {
                        writeToFile("remove failed", "./result.txt");
                    } //otherwise, it will write remove failed
                }
                catch (NumberFormatException e) {
                    writeToFile("Invalid Command", "./result.txt");
                }//error case -- the input is not of the right format
                break;
            case "print":
                try {
                    mybst.print();
                }
                catch (IOException e) {
                    writeToFile("Print failed", "./result.txt");
                }
                break;
            case "isEmpty":
                if (mybst.isEmpty()) {
                    writeToFile("Empty Tree", "./result.txt");
                }
                else {
                    writeToFile("Tree is Not Empty", "./result.txt");
                }
                break;
            //default case for Invalid Command
            default:
                writeToFile("Invalid Command: " + command[0], "./result.txt");
        }
    }

    // Implement the writeToFile method
    // Generate the result file
    public void writeToFile(String content, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                System.out.println("Error in creating the file.");
            }
        } //If the file doesn't already exist, a new one will be created

        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write(content + "\n");
            writer.close();
        } //creates a fileWriter to write to the file and writes to the file
        catch (IOException e) {
            System.out.println("Error in writing to file.");
        } //Exception if the filewriter fails
    }

    public void clearFile(String filename) throws IOException {
        File file = new File(filename);
        if (file.exists()) {
            try (FileWriter writer = new FileWriter(filename)) {
                writer.close();
            } //This opens and closes the fileWriter to clear the file it is to write to
            catch (IOException e) {
                System.out.println("Error in clearing the file");
            }
        }
    }

}
