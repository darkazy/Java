import java.util.Scanner;

public class ArrayEquality {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[][] m1 = new int[3][3];
        int[][] m2 = new int[3][3];

        System.out.print("Enter list1 (9 integers for 3x3 array):");
        fillArray(scanner, m1); 
        System.out.print("Enter list2 (9 integers for 3x3 array):");
        fillArray(scanner, m2);

        boolean areIdentical = equals(m1, m2);
        if (areIdentical) {
            System.out.print("Arrays are identical.");
        }
        else {
            System.out.print("Arrays are not identical.");
        }
        scanner.close();
    }

    public static void fillArray(Scanner scanner, int[][] array) {
        for (int i =0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                array[i][j] = scanner.nextInt();
            }
        }
    }

    public static boolean equals(int[][] m1, int[][] m2) {
        if (m1.length != m2.length || m1[0].length != m2[0].length) {
        return false;
        }
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[i].length; j++) {
                if (m1[i][j] != m2[i][j]) {
                    return false;
                }
            }
        }
        
        return true;
    }
}