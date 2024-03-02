package FinalProject;

import java.util.Scanner;

public class GameLauncher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Welcome screen and language selection
        System.out.println("                                                            *******************************************************************");
        System.out.println("                                                            *             Tower of Hanoi Game / टॉवर ऑफ हैनोई गेम               *");
        System.out.println("                                                            *    Developed by Pawan & Manish / विकसित किया गया है पवन और मनीष    *");
        System.out.println("                                                            *******************************************************************");

        System.out.println("\nChoose a language / कृपया भाषा का चयन करें:");
        System.out.println("1. हिंदी के लिए");
        System.out.println("2. Press 2 for English");

        int languageChoice = 0;
        while (languageChoice != 1 && languageChoice != 2) {
            System.out.print("Enter your choice: ");
            try {
                languageChoice = Integer.parseInt(scanner.nextLine());
                if (languageChoice != 1 && languageChoice != 2) {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        if (languageChoice == 1) {
            System.out.println("\nआपने हिंदी का चयन किया है. टॉवर ऑफ हैनोई गेम शुरू हो रहा है...");
            Tower_Of_Hanoi_Hindi.main(args); // Run the Hindi program
        } else if (languageChoice == 2) {
            System.out.println("\nYou have chosen English. Tower of Hanoi game is starting...");
            Tower_Of_Hanoi_English.main(args); // Run the English program
        }
    }
}
