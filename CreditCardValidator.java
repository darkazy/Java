import java.util.Scanner;

public class CreditCardValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a credit card number: ");
        long creditCardNumber = scanner.nextLong();
        if (isValid(creditCardNumber)) {
            System.out.println("Valid credit card number.");
        }
        else {
            System.out.println("Invalid credit card number.");
        }
    }
    
    public static boolean isValid(long number) {
        return (sumOfDoubleEvenPlace(number) + sumOfOddPlace(number)) % 10 == 0;
        }

    public static int sumOfDoubleEvenPlace(long number) {
        int sum = 0;
        String numStr = Long.toString(number);
        for (int i = numStr.length() - 2; i >= 0; i -= 2) {
            sum += getDigit(Character.getNumericValue(numStr.charAt(i)) * 2);
        }
        return sum;
    }

    public static int getDigit(int number) {
        if (number < 10) {
            return number;
            }
            else{
                return number / 10 + number % 10;
            }
        }

    public static int sumOfOddPlace(long number) {
        int sum = 0;
        String numStr = Long.toString(number);
        for (int i = numStr.length() - 1; i >= 0; i -= 2) {
            sum += Character.getNumericValue(numStr.charAt(i));
        }
        return sum;
    }

    public static boolean prefixMatched(long number, int d) {
        return getPrefix(number, getSize(d)) == d;
    }

    public static int getSize(long d) {
        return String.valueOf(d).length();
    }

    public static long getPrefix(long number, int k) {
        String numStr = Long.toString(number);
        if (k > numStr.length()) {
            return number;
        }
        return Long.parseLong(numStr.substring(0, k));
    }
}

