// Abdullah 1085344

import java.util.Arrays;
import java.util.Scanner;

public class Encryption {

    public static void main(String[] args) {

        // default values used for algorithms
        String password = "ALERT";
        int depth = 4;

        // scanner object to take input from user
        Scanner scanner = new Scanner(System.in);
        while (true) {

            // menu presented to user
            System.out.println("\n--------------------------------------");
            System.out.println("Welcome to Encrypt Decrypt Application");
            System.out.println("--------------------------------------");
            System.out.println("1. TRANSPOSE ALGORITHM");
            System.out.println("2. RAIL FENCE ALGORITHM");
            System.out.println("3. EXIT");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 3) {
                System.out.println("Thanks for using application! Bye");
                break;
            }

            System.out.print("Enter text to encrypt: ");
            String text = scanner.nextLine();

            // based on each choice the encrypt and decrypt is done
            if (choice == 1) {
                String transposedText = transposeEncrypt(text, password);
                System.out.println("Encrypted Text: " + transposedText);
                String decryptedTranspose = transposeDecrypt(transposedText, password);
                System.out.println("Decrypted Text: " + decryptedTranspose);
            } else if (choice == 2) {
                String railFenceText = railEncrypt(text, depth);
                System.out.println("Encrypted Text: " + railFenceText);
                String decryptedRailFence = railDecrypt(railFenceText, depth);
                System.out.println("Decrypted Text: " + decryptedRailFence);
            } else {
                System.out.println("Invalid Choice!");
            }
        }
        scanner.close();
    }

    public static String railEncrypt(String text, int key) {

        // creating an array
        char[][] rail = new char[key][text.length()];
        // fill the array
        for (int i = 0; i < key; i++) {
            Arrays.fill(rail[i], '\n');
        }
        boolean dirDown = false;
        int row = 0, col = 0;
        for (int i = 0; i < text.length(); i++) {

            if (row == 0 || row == key - 1) {
                dirDown = !dirDown;
            }

            rail[row][col++] = text.charAt(i);

            if (dirDown) {
                row++;
            } else {
                row--;
            }
        }

        // creating the final string
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < text.length(); j++) {
                if (rail[i][j] != '\n') {
                    result.append(rail[i][j]);
                }
            }
        }
        return result.toString();
    }

    // rail decrypt algorithm
    public static String railDecrypt(String cipher, int key) {

        char[][] rail = new char[key][cipher.length()];

        for (int i = 0; i < key; i++) {
            Arrays.fill(rail[i], '\n');
        }

        boolean dirDown = true;

        int row = 0, col = 0;

        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0) {
                dirDown = true;
            }
            if (row == key - 1) {
                dirDown = false;
            }

            rail[row][col++] = '*';

            if (dirDown) {
                row++;
            } else {
                row--;
            }
        }

        int index = 0;
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < cipher.length(); j++) {
                if (rail[i][j] == '*' && index < cipher.length()) {
                    rail[i][j] = cipher.charAt(index++);
                }
            }
        }

        StringBuilder result = new StringBuilder();

        row = 0;
        col = 0;
        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0) {
                dirDown = true;
            }
            if (row == key - 1) {
                dirDown = false;
            }

            if (rail[row][col] != '*') {
                result.append(rail[row][col++]);
            }

            if (dirDown) {
                row++;
            } else {
                row--;
            }
        }
        return result.toString();
    }

    // The method which encrypt the text
    public static String transposeEncrypt(String text, String password) {
        char[] passwordArray = password.toCharArray();
        Arrays.sort(passwordArray);
        int numColumns = password.length();
        int numRows = (int) Math.ceil((double) text.length() / numColumns);
        char[][] grid = new char[numRows][numColumns];

        int textIndex = 0;

        for (int col = 0; col < numColumns; col++) {
            int colOrder = password.indexOf(passwordArray[col]);
            for (int row = 0; row < numRows; row++) {
                if (textIndex < text.length()) {
                    grid[row][colOrder] = text.charAt(textIndex);
                    textIndex++;
                } else {
                    break;
                }
            }
        }

        StringBuilder result = new StringBuilder();

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                result.append(grid[row][col]);
            }
        }

        return result.toString();
    }

    // The method to decrypt the text
    public static String transposeDecrypt(String ciphertext, String password) {
        char[] passwordArray = password.toCharArray();
        Arrays.sort(passwordArray);
        int numColumns = password.length();
        int numRows = (int) Math.ceil((double) ciphertext.length() / numColumns);
        char[][] grid = new char[numRows][numColumns];

        int textIndex = 0;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                if (textIndex < ciphertext.length()) {
                    grid[row][col] = ciphertext.charAt(textIndex);
                    textIndex++;
                } else {
                    break;
                }
            }
        }

        StringBuilder result = new StringBuilder();

        for (int col = 0; col < numColumns; col++) {
            int colOrder = password.indexOf(passwordArray[col]);
            for (int row = 0; row < numRows; row++) {
                result.append(grid[row][colOrder]);
            }
        }

        return result.toString();
    }
}