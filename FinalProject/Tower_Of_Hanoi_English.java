package FinalProject;
import java.util.Scanner;
import java.util.Stack;

public class Tower_Of_Hanoi_English {
    public static int N;
    public static Stack<Integer>[] tower = new Stack[4];

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        tower[1] = new Stack<Integer>();
        tower[2] = new Stack<Integer>();
        tower[3] = new Stack<Integer>();

        // Welcome screen
        System.out.println("                                                                         ***************************************");
        System.out.println("                                                                         * Welcome to the Tower of Hanoi Game! *");
        System.out.println("                                                                         ***************************************\n");
        System.out.println("Rules: Move all disks from Tower A to Tower C, obeying the following rules:");
        System.out.println("  1. Only one disk can be moved at a time.");
        System.out.println("  2. Each move consists of taking the upper disk from one of the stacks and placing it on top of another stack or on an empty rod.");
        System.out.println("  3. No disk may be placed on top of a smaller disk.");

        // Ask the user if they want to play
        System.out.println("\nDo you want to play the Tower of Hanoi game? (yes/no)");
        String playOption = scan.nextLine().toLowerCase();

        if (playOption.equals("yes")) {
            // Accepting the number of disks with input validation
            int num = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.println("\nEnter the number of disks (an integer greater than 0):");
                    num = Integer.parseInt(scan.nextLine());
                    if (num > 0) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid input. Please enter a positive integer.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                }
            }

            N = num;
            toh(num, scan);

        } else {
            System.out.println("Thank you for considering. Have a great day!");
        }

        scan.close();
    }

    public static void toh(int n, Scanner scan) {
        for (int d = n; d > 0; d--)
            tower[1].push(d);

        // Display initial information
        System.out.println("You need to complete the Tower of Hanoi in " + ((int) Math.pow(2, n) - 1) + " moves.");
        display();

        // Interactive part: User moves
        int totalMoves = (int) Math.pow(2, n) - 1;
        int moveCount;
        for (moveCount = 1; moveCount <= totalMoves; moveCount++) {
            System.out.println("Move #" + moveCount + " (Remaining Moves: " + (totalMoves - moveCount) + ")");
            int fromTower, toTower, diskNumber;

            // Get user input for disk number and target tower
            while (true) {
                try {
                    System.out.println("Enter the disk number to move (1 to " + n + "):");
                    diskNumber = Integer.parseInt(scan.nextLine());
                    if (diskNumber < 1 || diskNumber > n) {
                        System.out.println("Invalid input. Please enter a number between 1 and " + n + ".");
                        continue;
                    }

                    System.out.println("Enter the target tower (1, 2, or 3):");
                    toTower = Integer.parseInt(scan.nextLine());
                    if (toTower < 1 || toTower > 3) {
                        System.out.println("Invalid input. Please enter a number between 1 and 3.");
                        continue;
                    }

                    fromTower = findDiskTower(diskNumber);

                    if (!isValidMove(diskNumber, fromTower, toTower)) {
                        System.out.println("Invalid move. Try again.");
                        continue;
                    }

                    // Check if the chosen disk is the top disk on the source tower
                    if (tower[fromTower].peek() != diskNumber) {
                        System.out.println("Invalid move. You can only move the top disk from a tower. Try again.");
                        continue;
                    }

                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter valid integers.");
                }
            }

            // Perform the move
            moveDisk(diskNumber, fromTower, toTower);
            display();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Check if all disks are moved to the destination
            if (moveCount == totalMoves && tower[3].size() != n) {
                System.out.println("\nSorry, you didn't move all the disks to the destination tower in the correct number of moves. Game over!");
                break;
            } else if (tower[3].size() == n) {
                System.out.println("\nCongratulations! You've successfully completed the Tower of Hanoi game!");
                break;
            }
        }
    }

    public static int findDiskTower(int disk) {
        for (int i = 1; i <= 3; i++) {
            if (tower[i].contains(disk)) {
                return i;
            }
        }
        return -1; // Disk not found on any tower
    }

    public static boolean isValidMove(int disk, int fromTower, int toTower) {
        if (tower[fromTower].isEmpty()) {
            return false; // Cannot move from an empty tower
        }

        // Check if the disk to be moved is the top disk on the source tower
        if (tower[fromTower].peek() != disk) {
            return false; // Disk to be moved is not the top disk
        }

        if (tower[toTower].isEmpty() || tower[toTower].peek() > disk) {
            return true; // Valid move
        } else {
            return false; // Invalid move, placing a larger disk on top of a smaller one
        }
    }

    public static void moveDisk(int disk, int fromTower, int toTower) {
        tower[fromTower].removeElement(disk);
        tower[toTower].push(disk);
        System.out.println("Moved disk " + disk + " from Tower " + fromTower + " to Tower " + toTower);
    }

    public static void display() {
        System.out.println("  A  |  B  |  C");
        System.out.println("---------------");
        for (int i = N - 1; i >= 0; i--) {
            String d1 = " ", d2 = " ", d3 = " ";
            try {
                d1 = String.valueOf(tower[1].get(i));
            } catch (Exception e) {
            }
            try {
                d2 = String.valueOf(tower[2].get(i));
            } catch (Exception e) {
            }
            try {
                d3 = String.valueOf(tower[3].get(i));
            } catch (Exception e) {
            }
            System.out.println("  " + d1 + "  |  " + d2 + "  |  " + d3);
        }
        System.out.println("\n");
    }
}
