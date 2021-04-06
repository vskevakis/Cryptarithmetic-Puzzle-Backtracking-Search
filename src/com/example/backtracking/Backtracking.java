package com.example.backtracking;

import java.util.Scanner;

public class Backtracking {
    /* Creating a Node[] array that contains the unique letters of our expression */
    private void parseString(String string, Node[] assignedChars) {
        for (int i=0; i<string.length(); i++) {
            char letter = string.charAt(i);
            if (Character.isLetter(letter) && !(contains(assignedChars, letter))){
                assignedChars[i].c = string.charAt(i);
                assignedChars[i].v = -1;
            }
        }
        return;
    }

    /* Checks if Node[] contains a letter */
    private boolean contains(Node[] assignedChars, char letter) {
        for (int i=0; i < assignedChars.length; i++) {
            if (assignedChars[i].c == letter) {
                return true;
            }
        }
        return false;
    }

    private boolean solve(String puzzle, Node[] assignedChars, int mode) {
        // Solve statement, if all chars are assigned
        if (isEmpty(assignedChars)) {
            return checkSolution(puzzle, assignedChars);
        }
        Node node = findFirstUnassigned(assignedChars);
        char character = node.c; // Found it
        // Try all unassigned digits
        for (int digit = 0; digit <= mode; digit++) {
            if (assignLetterToDigit(node, digit, assignedChars)) {
                if (solve(puzzle, assignedChars, mode)) {
                    return true; //We solved the crypto
                }
                unassignLetterToDigit(node, character, digit, assignedChars);
            }

        }
        return false;
    }

    private Node findFirstUnassigned(Node[] assignedChars) {
        for (int i = 0; i < assignedChars.length; i++) {
            if (assignedChars[i].v == -1) {
                return assignedChars[i];
            }
        }
        return null;
    }

    private boolean assignLetterToDigit(Node node, int digit, Node[] assignedChars) {
        if (digit == -1) return false; //If letter or digit is null -> return false
        boolean letter_exists = false;
        for (int i = 0; i < assignedChars.length; i++) {
            if (assignedChars[i].v == digit) {
                return false;
            }
        }
        for (int i = 0; i < assignedChars.length; i++) {
            if (assignedChars[i] == node) {
                assignedChars[i].setData(node.c, digit);
                return true;
            }
        }
        return false;
    }

    public void unassignLetterToDigit(Node node, char character, int digit, Node[] assignedChars) {
        for (int i = 0; i < assignedChars.length; i++) {
            if (assignedChars[i].v == digit)
                assignedChars[i].setData(character, -1);
        }
    }

    /* Check arithmetics for a valid solution */
    private boolean checkSolution(String puzzle, Node[] assignedChars) {
        int number1 = 0;
        int number2 = 0;
        int number3 = 0;
        boolean triggerplus = false;
        boolean triggerequal = false;
        for (int i = 0; i < puzzle.length(); i++) {
            if (puzzle.charAt(i) == '+') {
                triggerplus = true;
            }
            /* Can add minus and multiplacation here */
            else if (puzzle.charAt(i) == '=') {
                triggerequal = true;
            }
            else if (puzzle.charAt(i) == ' ') {
                //Do nothing
            }
            else {
                int newnumber = 0; // Find number that is assigned
                for (int j = 0; j < assignedChars.length; j++) {
                    if (assignedChars[j].c == puzzle.charAt(i)) {
                        newnumber = assignedChars[j].v;
                    }
                }
                if (newnumber == -1) {
                    System.out.println("-1 Found");
                    return false;
                }
                /*Notice: <<Integer.valueOf(String.valueOf(number1) + String.valueOf(newnumber))>>
                 * concats the intergers */
                if (triggerequal) {
                    //Add number to number 1
                    number3 = Integer.valueOf(String.valueOf(number3) + String.valueOf(newnumber));
                }
                else if (triggerplus) {
                    //Add number to number 2
                    number2 = Integer.valueOf(String.valueOf(number2) + String.valueOf(newnumber));
                }
                else {
                    number1 = Integer.valueOf(String.valueOf(number1) + String.valueOf(newnumber));
                }
            }
        }
        // After assigning all the numbers try and validate the arithmetic solution
        if (number1 + number2 == number3) {
            System.out.println(number1 + " + " + number2 + " = " + number3);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isEmpty(Node[] assignedChars) {
        for (int i = 0; i < assignedChars.length; i++) {
            if (assignedChars[i].v == -1) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Backtracking backtracking = new Backtracking();

//        String puzzle = "TO + TO = FOR";
//        int mode = 6; //Mode can be 6 or 10
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Enter puzzle: ");
        String puzzle = myObj.nextLine();  // Read user input
        int mode = 0;
        while (mode != 6 && mode != 10) {
            System.out.print("Enter mode (10 or 6): ");
            mode = myObj.nextInt();  // Read user input
        }

        //Printing info
        System.out.print(puzzle);
        if (mode == 6) {
            System.out.println(" (in hex mode)");
        } else {
            System.out.println(" (in dec mode)");
        }

        int puzzlen = puzzle.length();
        Node[] tempArr = new Node[puzzlen];

        //Instantiate tempArr
        for (int i = 0; i < tempArr.length; i++) {
            tempArr[i] = new Node(' ', -1);
        }

        backtracking.parseString(puzzle, tempArr);

        /* Noob proccess to remove empty cells from tempArr
         * and create our assignedChars array */
        int elem = 0;
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i].c != ' ' && tempArr[i].c != '+' && tempArr[i].c != '=')
                elem++;
        }

        Node[] assignedChars = new Node[elem]; //This is our real array

        int j = 0;
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i].c != ' ' && tempArr[i].c != '+' && tempArr[i].c != '=') {
                assignedChars[j] = new Node(tempArr[i].c, tempArr[i].v);
                j++;
            }
        }

        if (backtracking.solve(puzzle, assignedChars, mode)) {
            System.out.println("Solved!");
        } else {
            System.out.println("Can't solve it :(");
        }
    }
}
