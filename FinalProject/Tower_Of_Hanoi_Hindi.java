package FinalProject;
import java.util.Scanner;
import java.util.Stack;

public class Tower_Of_Hanoi_Hindi {
    public static int N;
    public static Stack<Integer>[] tower = new Stack[4];

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        tower[1] = new Stack<Integer>();
        tower[2] = new Stack<Integer>();
        tower[3] = new Stack<Integer>();

        // स्वागत स्क्रीन
        System.out.println("                                                                         ***************************************");
        System.out.println("                                                                         *    टॉवर ऑफ हनोई गेम में आपका स्वागत है!   *");
        System.out.println("                                                                         ***************************************\n");
        System.out.println("नियम: सभी डिस्कों को टॉवर A से टॉवर C में ले जाएं, निम्नलिखित नियमों का पालन करें:");
        System.out.println("  1. केवल एक डिस्क को एक बार में ही हिला सकते हैं।");
        System.out.println("  2. प्रत्येक हिले में एक स्टैक से ऊपर की डिस्क को लेकर उसे किसी अन्य स्टैक पर या खाली रॉड पर रखना है।");
        System.out.println("  3. कोई डिस्क छोटे डिस्क के ऊपर नहीं रखा जा सकता।");

        // उपयोगकर्ता से पूछें कि क्या वह खेलना चाहते हैं
        System.out.println("\nक्या आप टॉवर ऑफ हनोई गेम खेलना चाहते हैं? (हाँ/नहीं)");
        String playOption = scan.nextLine().toLowerCase();

        if (playOption.equals("हाँ")) {
            // इनपुट मान्यता के साथ डिस्क की संख्या स्वीकृत करना
            int num = 0;
            boolean validInput = false;
            while (!validInput) {
                try {
                    System.out.println("\nडिस्कों की संख्या दर्ज करें (0 से अधिक कोई पूर्णांक):");
                    num = Integer.parseInt(scan.nextLine());
                    if (num > 0) {
                        validInput = true;
                    } else {
                        System.out.println("अमान्य इनपुट। कृपया सकारात्मक पूर्णांक दर्ज करें।");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("अमान्य इनपुट। कृपया एक वैध पूर्णांक दर्ज करें।");
                }
            }

            N = num;
            toh(num, scan);

        } else {
            System.out.println("आपके विचार के लिए धन्यवाद। अच्छा दिन हो!");
        }

        scan.close();
    }

    public static void toh(int n, Scanner scan) {
        for (int d = n; d > 0; d--)
            tower[1].push(d);

        // प्रारंभिक जानकारी प्रदर्शित करें
        System.out.println("आपको टॉवर ऑफ हनोई को " + ((int) Math.pow(2, n) - 1) + " कदमों में पूरा करना है।");
        display();

        // इंटरएक्टिव हिस्सा: उपयोगकर्ता की कदम
        int totalMoves = (int) Math.pow(2, n) - 1;
        int moveCount;
        for (moveCount = 1; moveCount <= totalMoves; moveCount++) {
            System.out.println("कदम #" + moveCount + " (शेष कदम: " + (totalMoves - moveCount) + ")");
            int fromTower, toTower, diskNumber;

            // उपयोगकर्ता से डिस्क नंबर और लक्ष्य टॉवर के लिए इनपुट प्राप्त करें
            while (true) {
                try {
                    System.out.println("हिलाने के लिए डिस्क नंबर दर्ज करें (1 से " + n + "):");
                    diskNumber = Integer.parseInt(scan.nextLine());
                    if (diskNumber < 1 || diskNumber > n) {
                        System.out.println("अमान्य इनपुट। कृपया 1 से " + n + " के बीच एक संख्या दर्ज करें।");
                        continue;
                    }

                    System.out.println("लक्ष्य टॉवर दर्ज करें (1, 2, या 3):");
                    toTower = Integer.parseInt(scan.nextLine());
                    if (toTower < 1 || toTower > 3) {
                        System.out.println("अमान्य इनपुट। कृपया 1 से 3 के बीच एक संख्या दर्ज करें।");
                        continue;
                    }

                    fromTower = findDiskTower(diskNumber);

                    if (!isValidMove(diskNumber, fromTower, toTower)) {
                        System.out.println("अमान्य कदम। पुनः प्रयास करें।");
                        continue;
                    }

                    // यदि चयनित डिस्क स्रोत टॉवर पर शीर्ष डिस्क नहीं है तो यह जाँचें
                    if (tower[fromTower].peek() != diskNumber) {
                        System.out.println("अमान्य कदम। आप केवल एक टॉवर से शीर्ष डिस्क को हिला सकते हैं। पुनः प्रयास करें।");
                        continue;
                    }

                    break;
                } catch (NumberFormatException e) {
                    System.out.println("अमान्य इनपुट। कृपया वैध पूर्णांक दर्ज करें।");
                }
            }

            // कदम को करें
            moveDisk(diskNumber, fromTower, toTower);
            display();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // सभी डिस्क गंतव्य में हिला दिए गए हैं क्या यह जाँचें
            if (moveCount == totalMoves && tower[3].size() != n) {
                System.out.println("\nमाफ़ करें, आपने सही कदमों में सभी डिस्कों को गंतव्य टॉवर में नहीं हिलाया है। खेल समाप्त!");
                break;
            } else if (tower[3].size() == n) {
                System.out.println("\nबधाई हो! आपने सफलतापूर्वक टॉवर ऑफ हनोई गेम पूरा किया है!");
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
        return -1; // कोई टॉवर पर डिस्क नहीं मिली
    }

    public static boolean isValidMove(int disk, int fromTower, int toTower) {
        if (tower[fromTower].isEmpty()) {
            return false; // खाली टॉवर से हिला नहीं जा सकता
        }

        // यह जाँचें कि हिलाए जाने वाली डिस्क स्रोत टॉवर पर शीर्ष डिस्क है या नहीं
        if (tower[fromTower].peek() != disk) {
            return false; // हिलाए जाने वाली डिस्क शीर्ष डिस्क नहीं है
        }

        if (tower[toTower].isEmpty() || tower[toTower].peek() > disk) {
            return true; // मान्य कदम
        } else {
            return false; // अमान्य कदम, एक छोटे डिस्क को एक बड़े पर रखना
        }
    }

    public static void moveDisk(int disk, int fromTower, int toTower) {
        tower[fromTower].removeElement(disk);
        tower[toTower].push(disk);
        System.out.println("डिस्क " + disk + " को टॉवर " + fromTower + " से टॉवर " + toTower + " में हिलाया गया");
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
