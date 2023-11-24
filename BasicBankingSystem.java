import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BasicBankingSystem {
    static int[] AccountNumbers;
    static String[] FirstNames, LastNames, Genders, Passwords;
    static double[] AccountBalances;
    static int UniversalArrayIndex = 0; //My attempt at keeping track of different users
    static String AdminPassword = "admin12345";
    public static void ArrayInitialization(int capacity) { // Access for Admin account only
        AccountNumbers = new int[capacity];
        FirstNames = new String[capacity];
        LastNames = new String[capacity];
        Genders = new String[capacity];
        Passwords = new String[capacity];
        AccountBalances = new double[capacity];
    }
    public static boolean IsValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]).{12,}$";
        return Pattern.compile(regex).matcher(password).matches();
    }
    public static String BankMoneySum(){
        double sum = 0;
        String state;
        if (AccountNumbers != null){
            for (int i = 0; i<AccountNumbers.length; i++) {
                sum += AccountBalances[i];
            }
            state = Double.toString(sum);
        }else{
            state = "All accounts are empty.";
        }
        return state;
    }
    public static void DispUserStats() {
        Scanner ip = new Scanner(System.in);
        System.out.print("\nPlease enter the account number of the desired user:");
        boolean UserFound = false;
        int UserInput = 0;
        do {
            try {
                UserInput = ip.nextInt();

                for (int accountNumber : AccountNumbers) {
                    if (UserInput == accountNumber) {
                        UserFound = true;
                        break;
                    }
                }

                if (!UserFound) {
                    System.out.print("\nAccount not found. Please enter a valid account number:");
                }

            } catch (java.util.InputMismatchException e) {
                System.out.print("\nPlease enter a valid account number:");
                ip.next(); // Clear the invalid input
            }
        } while (!UserFound);
        int UserArrayIndex = 0; // Array Index for currently selected user
        for (int i = 0; i < AccountNumbers.length; i++) {
            if (AccountNumbers[i] == UserInput) {
                UserArrayIndex = i;
            }
        }

        System.out.print("\nSelected User Account: " + AccountNumbers[UserArrayIndex]);
        System.out.print("\n\t-Full Name:" + FirstNames[UserArrayIndex].trim() + " " + LastNames[UserArrayIndex].trim());
        System.out.print("\n\t-Gender: " + Genders[UserArrayIndex]);
        System.out.print("\n\t-Current Account Funds: " + AccountBalances[UserArrayIndex] + "\n");

    }
    public static void AdminLogIn(){ //Log in method
        Scanner ip = new Scanner(System.in);
        System.out.println("Enter Admin password to gain access: ");
        String PassInput = ip.nextLine();
        if (!Objects.equals(PassInput, AdminPassword)){
            System.out.println("WRONG PASSWORD. SHUTTING DOWN.");
            System.exit(0);
        }
        int UserChoice;

        boolean LoopCondition = true;
        do{
            System.out.print("\nWelcome, Admin.\n");
            System.out.print("""
                    Choose an option:
                    1: Initialize User Array Size
                    2: View System Stats
                    3: View Specific User Stats
                    4: Exit\s""");
            try{
                UserChoice = Integer.parseInt(ip.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Enter valid input.\n");
                continue;
            }
            switch(UserChoice){
                case 1:
                    System.out.println("Please enter user array size:");
                    try{
                        try{
                            ArrayInitialization(Integer.parseInt(ip.nextLine()));
                        }catch(NegativeArraySizeException e){
                            System.out.println("Enter valid input.\n");
                            continue;
                        }
                    }catch(NumberFormatException e){
                        System.out.println("Enter valid input.\n");
                        continue;
                    }
                    System.out.println("New array size set.");
                    break;
                case 2:
                    String ArraySize = AccountNumbers != null? Integer.toString(AccountNumbers.length) : "0";
                    System.out.print("\nCurrent System Stats: ");
                    System.out.print("\nThere are currently " + ArraySize + " possible account users.");
                    System.out.print("\nTotal money the bank holds: " + BankMoneySum() +"\n");
                    break;
                case 3:
                    DispUserStats();
                    break;
                case 4:
                    LoopCondition = false;
                    break;
                default:
                    System.out.println("ERROR: Please enter a number from the options.\n");

            }
        }while(LoopCondition);
    }
    public static void LogIn(){ // TODO start login things

        System.out.print("\nPlease enter your account number:");
        boolean UserFound = true;
        int UserInput = 0;
        do {
            Scanner ip = new Scanner(System.in);
            try {
                UserInput = ip.nextInt();

                for (int accountNumber : AccountNumbers) {
                    if (UserInput == accountNumber) {
                        UserFound = false;
                        break;
                    }
                }

                if (UserFound) {
                    System.out.print("\nAccount not found. Please enter a valid account number:");
                }

            } catch (java.util.InputMismatchException e) {
                System.out.print("\nPlease enter a valid account number:");

            }

        } while (UserFound);
        int UserArrayIndex = 0; // Array Index for currently selected user
        for (int i = 0; i < AccountNumbers.length; i++) {
            if (AccountNumbers[i] == UserInput) {
                UserArrayIndex = i;
            }
        }
        // password check loop below
        do {
            Scanner ip = new Scanner(System.in);
            System.out.println("Enter account password to gain access:");
            String PassInput = ip.nextLine();
            if (!Objects.equals(PassInput, Passwords[UserArrayIndex])){
                System.out.println("WRONG PASSWORD.");
            }else{
                break;
            }
        }while(true);
        System.out.println("Welcome, "+FirstNames[UserArrayIndex].trim() + " " + LastNames[UserArrayIndex].trim()+".");
        boolean MenuLoop = true;

        do {
            Scanner ip = new Scanner(System.in);
            System.out.print("\n\t-Current Account Funds: " + AccountBalances[UserArrayIndex] + "\n");
            int MenuChoice;
            System.out.println("""

                    Please enter a number for your choice:
                    1: Deposit money
                    2: Withdraw money
                    3: Transfer money to other account
                    4: Exit""");

            try {
                MenuChoice = Integer.parseInt(ip.nextLine());
                // MenuChoice = Integer.parseInt(ip.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Please enter a number.\n");
                continue;
            }
            switch (MenuChoice) {
                case 1:
                    double Deposit;
                    System.out.print("Please enter amount to deposit: ");
                    try { //check for non number input
                        Deposit = Double.parseDouble(ip.nextLine());
                        if (Deposit < 0) {
                            // this gets caught in the catch block
                            System.out.println("Only positive numbers.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: Please enter a valid number.\n");
                        continue;
                    }
                    AccountBalances[UserArrayIndex] += Deposit;
                    System.out.println("Deposit successful!");
                    break;
                case 2:
                    double Withdrawal;
                    System.out.print("Please enter amount to withdraw: ");
                    try { //check for non number input
                        Withdrawal = Double.parseDouble(ip.nextLine());
                        if (Withdrawal < 0) {
                            // this gets caught in the catch block
                            System.out.println("Only positive numbers.");
                            continue;
                        }
                        if (Withdrawal > AccountBalances[UserArrayIndex]) {
                            System.out.println("ERROR: Insufficient account funds.\n");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("ERROR: Please enter a valid number.\n");
                        continue;
                    }
                    AccountBalances[UserArrayIndex] -= Withdrawal;
                    System.out.println("Withdrawal successful!");
                    break;
                case 3:
                    FundTransfer(UserArrayIndex);
                    break;
                case 4:
                    System.out.println("Signing out!\n");
                    MenuLoop = false;
                    break;
                default:
                    System.out.println("ERROR: Please enter a number from the options.\n");
            }
        }while(MenuLoop);
        //TODO
        // -delete account (with confirmation message)

    }
    public static void FundTransfer(int UserArrayIndex) {
        do {
            Scanner ip = new Scanner(System.in);
            System.out.println("\nPlease enter the account you want to transfer to: ");
            boolean UserFound = true;
            int UserInput;
            int SecondUserArrayIndex = 0;
            do {
                try {
                    UserInput = ip.nextInt();

                    for (int i = 0; i < AccountNumbers.length; i++) {
                        if (UserInput == AccountNumbers[i]) {
                            UserFound = false;
                            SecondUserArrayIndex = i;
                            break;
                        }else{
                            System.out.print("\nAccount not found. Please try again:");
                        }
                    }
                    ip.nextLine();
                } catch (java.util.InputMismatchException e) {
                    System.out.print("\nPlease enter a valid account number:");
                    ip.next(); // Clear the invalid input
                }

            } while (UserFound);

            double Transferral;
            System.out.print("Please enter the amount to be transferred: ");
            try { //check for non number input
                Transferral = Double.parseDouble(ip.nextLine());
                if (Transferral < 0) {
                    System.out.println("Only positive numbers.");
                    continue;
                } else if (Transferral > AccountBalances[UserArrayIndex]) {
                    System.out.println("ERROR: Insufficient account funds.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Please enter a valid number.\n");
                continue;
            }
            AccountBalances[UserArrayIndex] -= Transferral;
            AccountBalances[SecondUserArrayIndex] += Transferral;
            System.out.println("Transfer successful!");
            break;
        }while(true);



    }
    public static int AccNumGen() {
        boolean DupeState;
        int FullAccNum;
        do {
            int FirstDigit = (int) (Math.random() * 9) + 1;
            int RemainingDigits = (int) (Math.random() * 99999);
            FullAccNum = FirstDigit * 100000 + RemainingDigits;
            DupeState = AccNumDupeCheck(FullAccNum);
        } while (DupeState);


        return FullAccNum;
    }
    public static boolean AccNumDupeCheck(int num){
        for (int i = 0; i < UniversalArrayIndex; i++) {
            if (AccountNumbers[i] == num) {
                return true;
            }
        }
        return false;
    }
    public static void SignUp(){
        Scanner ip = new Scanner(System.in);
        System.out.print("Please enter your first name: ");
        FirstNames[UniversalArrayIndex] = ip.nextLine();
        System.out.print("Please enter your last name: ");
        LastNames[UniversalArrayIndex] = ip.nextLine();
        System.out.print("Please enter your gender: ");
        Genders[UniversalArrayIndex] = ip.nextLine();
        do{
            System.out.print("Please create a password: ");
            System.out.print("""
                    \tRules:
                    \t\t-Must contain at least 1 Uppercase letter.
                    \t\t-Must contain at least 1 Lowercase letter.
                    \t\t-Must contain at least 1 Number.
                    \t\t-Must contain at least 1 Special Symbol.
                    \t\t-Must be at least 12 characters long.
                    """);
            String PasswordAttempt = ip.nextLine();

            if (IsValidPassword(PasswordAttempt)) {
                Passwords[UniversalArrayIndex] = PasswordAttempt;
                break;
            } else {
                System.out.println("Password is invalid.");
            }
        }while(true);

        AccountNumbers[UniversalArrayIndex] = AccNumGen();
        System.out.println("Your new account number is: " + AccountNumbers[UniversalArrayIndex]);
        System.out.println("Access the Log in screen to enter your new account! ");
        UniversalArrayIndex++;


    }
    public static void main(String[] args){
        Scanner ip = new Scanner(System.in);
        do{  //main loop
            int MenuChoice;
            System.out.println("""


                    ----------------NEB----------------
                    Welcome to the National Emirates Bank!

                    Please enter a number for your choice:
                    1: Log-in
                    2: Sign-up
                    3: Exit""");

            try { //check for non number input
                MenuChoice = Integer.parseInt(ip.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Please enter a number.\n");
                continue;
            }
            //result from menu choice, including non menu numbers and secret admin view
            switch (MenuChoice) {
                case 1:
                    LogIn();
                    break;
                case 2:
                    SignUp();
                    break;
                case 3:
                    System.out.println("Thank you for using NEB banking services. Goodbye!");
                    System.exit(0);
                case 4:
                    AdminLogIn();
                    break;
                default:
                    System.out.println("ERROR: Please enter a number from the options.\n");
            }

        }while(true);

    }
}
