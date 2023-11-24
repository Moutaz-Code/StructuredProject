//Sayf El Sayed 1085581

import java.io.*;
import java.util.Scanner;
public class TicTacToeGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        boolean isSavedGamePresent = false;
        char[][] lastSavedGame = new char[3][3];

        while (isRunning) {
            char[][] board = {
                    {' ', ' ', ' '},
                    {' ', ' ', ' '},
                    {' ', ' ', ' '}
            };

            System.out.println("\nWelcome to Tic-Tac-Toe");
            System.out.println("Select an option:");
            System.out.println("1. New Game");
            System.out.println("2. Load Game");
            System.out.println("3. Exit Game");
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    startNewGame(scanner, board);
                    isSavedGamePresent = true;
                    break;
                case 2:
                    if (isSavedGamePresent) {
                        loadLastSavedGame(lastSavedGame);
                        printBoard(lastSavedGame);
                    } else {
                        System.out.println("There is no saved game.\n");
                    }
                    break;
                case 3:
                    isRunning = false;
                    System.out.println("Exiting the game. Thank you for playing!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }

            if (choice == 1 && isSavedGamePresent) {
                copyBoard(lastSavedGame, board);
            }
        }

        scanner.close();
    }

    private static void startNewGame(Scanner scanner, char[][] board) {
        System.out.println("\nChoose game mode:");
        System.out.println("1. One Player");
        System.out.println("2. Two Players");
        System.out.print("Enter your choice: ");
        int gameMode = scanner.nextInt();
        scanner.nextLine();

        switch (gameMode) {
            case 1:
                startOnePlayerGame(scanner, board);
                break;
            case 2:
                startTwoPlayersGame(scanner, board);
                break;
            default:
                System.out.println("Invalid choice. Starting as One Player by default.");
                startOnePlayerGame(scanner, board);
                break;
        }
    }

    private static void startOnePlayerGame(Scanner scanner, char[][] board) {
        char player = 'X';
        boolean isGameFinished = false;
        int moves = 0;

        while (!isGameFinished) {
            printBoard(board);

            if (player == 'X') {
                System.out.println("\nPlayer X, it's your turn.");
                int row, col;
                do {
                    System.out.print("Enter row (1-3): ");
                    row = scanner.nextInt() - 1;
                } while (row < 0 || row > 2);

                do {
                    System.out.print("Enter column (1-3): ");
                    col = scanner.nextInt() - 1;
                } while (col < 0 || col > 2);

                if (isValidMove(board, row, col)) {
                    board[row][col] = player;
                    moves++;
                    if (checkWin(board, player)) {
                        printBoard(board);
                        System.out.println("Player X wins!");
                        isGameFinished = true;
                    } else if (moves == 9) {
                        printBoard(board);
                        System.out.println("It's a draw!");
                        isGameFinished = true;
                    } else {
                        player = 'O';
                    }
                } else {
                    System.out.println("Invalid move. Please try again.");
                }
            } else {
                System.out.println("\nAI \"O\" is making a move...");
                int randomRow, randomCol;
                do {
                    randomRow = (int) (Math.random() * 3);
                    randomCol = (int) (Math.random() * 3);
                } while (!isValidMove(board, randomRow, randomCol));
                board[randomRow][randomCol] = player;
                moves++;
                if (checkWin(board, player)) {
                    printBoard(board);
                    System.out.println("AI \"O\" wins!");
                    isGameFinished = true;
                } else if (moves == 9) {
                    printBoard(board);
                    System.out.println("It's a draw!");
                    isGameFinished = true;
                } else {
                    player = 'X';
                }
            }

            if (isGameFinished) {
                System.out.print("Do you want to save the game (yes/no)? ");
                String choice = scanner.next();
                if (choice.equalsIgnoreCase("yes")) {
                    saveGame(board);
                }
            }
        }
    }

    private static void startTwoPlayersGame(Scanner scanner, char[][] board) {
        char player = 'X';
        boolean isGameFinished = false;
        int moves = 0;

        while (!isGameFinished) {
            printBoard(board);

            System.out.println("Player " + player + ", it's your turn.");
            int row, col;
            do {
                System.out.print("Enter row (1-3): ");
                row = scanner.nextInt() - 1;
            } while (row < 0 || row > 2);

            do {
                System.out.print("Enter column (1-3): ");
                col = scanner.nextInt() - 1;
            } while (col < 0 || col > 2);

            if (isValidMove(board, row, col)) {
                board[row][col] = player;
                moves++;
                if (checkWin(board, player)) {
                    printBoard(board);
                    System.out.println("Player " + player + " wins!");
                    isGameFinished = true;
                } else if (moves == 9) {
                    printBoard(board);
                    System.out.println("It's a draw!");
                    isGameFinished = true;
                } else {
                    player = (player == 'X') ? 'O' : 'X';
                }
            } else {
                System.out.println("Invalid move. Please try again.");
            }

            if (isGameFinished) {
                System.out.print("Do you want to save the game (yes/no)? ");
                String choice = scanner.next();
                if (choice.equalsIgnoreCase("yes")) {
                    saveGame(board);
                }
            }
        }
    }

    private static void printBoard(char[][] board) {
        System.out.println("\n  1   2   3");
        System.out.println("1 " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        System.out.println("  ---------");
        System.out.println("2 " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        System.out.println("  ---------");
        System.out.println("3 " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
    }

    private static boolean isValidMove(char[][] board, int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    private static boolean checkWin(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    private static void saveGame(char[][] board) {
        try (FileWriter writer = new FileWriter("saved_game.txt");
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    bufferedWriter.write(board[row][col]);
                }
                bufferedWriter.newLine();
            }
            System.out.println("Game saved successfully.\n");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving the game.");
        }
    }

    private static void loadLastSavedGame(char[][] board) {
        try (FileReader reader = new FileReader("saved_game.txt");
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            boolean isGameLoaded = false;
            for (int row = 0; row < 3; row++) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    isGameLoaded = true;
                    for (int col = 0; col < 3; col++) {
                        board[row][col] = line.charAt(col);
                    }
                }
            }

            if (!isGameLoaded) {
                System.out.println("There is no saved game.");
            } else {
                System.out.println("Last saved game:");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading the game.");
        }
    }

    private static int getUserChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
        return choice;
    }

    private static void copyBoard(char[][] source, char[][] destination) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, source[i].length);
        }
    }
}
