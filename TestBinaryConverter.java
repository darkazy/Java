import java.util.Scanner;

public class TestBinaryConverter {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a binary string: ");
        String binaryString = input.nextLine();

        try {
            int decimalValue = BinaryConverter.bin2Dec(binaryString);
            System.out.println("The decimal value of the binary string " + binaryString + " is " + decimalValue);
        } catch (BinaryFormatException e) {
            System.out.println(e.getMessage());
        } finally {
            input.close();
        }
    }
}

// BinaryConverter.java
class BinaryConverter {
    public static int bin2Dec(String binaryString) throws BinaryFormatException {
        for (char ch : binaryString.toCharArray()) {
            if (ch != '0' && ch != '1') {
                throw new BinaryFormatException("The string is not a valid binary string: " + binaryString);
            }
        }

        int decimalValue = 0;
        for (int i = 0; i < binaryString.length(); i++) {
            char bit = binaryString.charAt(i);
            decimalValue = decimalValue * 2 + (bit - '0');
        }
        return decimalValue;
    }
}

class BinaryFormatException extends Exception {
    public BinaryFormatException(String message) {
        super(message);
    }
}
