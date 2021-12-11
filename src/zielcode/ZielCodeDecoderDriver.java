package zielcode;

import displayer.ListDisplayer;

import java.util.Scanner;

public class ZielCodeDecoderDriver {
    public static void main(String[] args) {
        //24118
        //|·||·|··||||··||||·||·||·|·|||·||·||
        //false code
        //||·|·|··||||··||||·||·||·|·|||·||·||
        //22222
        //·|·|||·|·|||·|·|||·|·|||·|·||||||··|

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Ziel Barcode: ");
        String barcode = scanner.nextLine();
        int[] digits = decodeBarcode(barcode);
        if (digits != null) {
            for (int i = 0; i < digits.length; i++) {
                System.out.print(digits[i]);
            }
            System.out.println();
        } else {
            System.out.println("False Code");
        }
    }

    private static int[] decodeBarcode(String barcode) {
        //↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧
        var barcodeDisplayer = ListDisplayer.from(barcode, "barcode letters", "indices");
        barcodeDisplayer.display();
        //↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥

        int segmentLength = 6;
        int segmentCount = barcode.length() / segmentLength;
        String[] segments = new String[segmentCount];

        //↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧
        var segmentsDisplayer = ListDisplayer.from(segments, "segments", "indices");
        segmentsDisplayer.setElementMinLength(6);
        segmentsDisplayer.setPad("");
        segmentsDisplayer.display();
        //↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥

        for (int i = 0; i < segmentCount; i++) {
            int startIndex = i * segmentLength;
            int endIndex = startIndex + segmentLength;
            String segment = barcode.substring(startIndex, endIndex);
            segments[i] = segment;
            segmentsDisplayer.display();
        }

        int[] values = new int[]{0, 1, 2, 4, 7};
        int[] allDigits = new int[segmentCount];

        //↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧
        var valuesDisplayer = ListDisplayer.from(values, "letter values", "indices");
        valuesDisplayer.display();
        var allDigitsDisplayer = ListDisplayer.from(allDigits, "all digits", "indices");
        allDigitsDisplayer.display();
        //↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥

        for (int segmentIndex = 0; segmentIndex < segmentCount; segmentIndex++) {
            String segment = segments[segmentIndex];
            int bitSum = 0;
            for (int valueIndex = 0; valueIndex < values.length; valueIndex++) {
                char bit = segment.charAt(valueIndex);
                if (bit != '|') {
                    int value = values[valueIndex];
                    bitSum = bitSum + value;
                }
            }
            if (bitSum == 11) {
                bitSum = 0;
            }
            allDigits[segmentIndex] = bitSum;

            //↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧↧
            allDigitsDisplayer.display();
            //↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥↥
        }

        int checkDigitIndex = allDigits.length - 1;
        int valueDigitsSum = 0;
        for (int digitIndex = 0; digitIndex < checkDigitIndex; digitIndex++) {
            int digit = allDigits[digitIndex];
            valueDigitsSum = valueDigitsSum + digit;
        }

        System.out.println("valueDigitsSum: " + valueDigitsSum);

        int calculatedCheckDigit = 10 - (valueDigitsSum % 10);
        if (calculatedCheckDigit == 10) {
            calculatedCheckDigit = 0;
        }

        System.out.println("calculatedCheckDigit: " + calculatedCheckDigit);

        if (calculatedCheckDigit == allDigits[checkDigitIndex]) {
            int[] valueDigits = new int[segmentCount - 1];
            int valueDigitIndex = 0;
            for (int digitIndex = checkDigitIndex - 1; digitIndex >= 0; digitIndex--) {
                valueDigits[valueDigitIndex] = allDigits[digitIndex];
                valueDigitIndex = valueDigitIndex + 1;
            }
            return valueDigits;
        } else {
            return null;
        }
    }
}
