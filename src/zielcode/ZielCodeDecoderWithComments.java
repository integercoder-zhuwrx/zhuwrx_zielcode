package zielcode;

public class ZielCodeDecoderWithComments {
    public static void main(String[] args) {
        //     Segment Indices  0     1     2     3     4     5     6
        //                      ↓     ↓     ↓     ↓     ↓     ↓     ↓
        //                      01247-01247-01247-01247-01247-01247-
        //                      -·--·-··----··----·--·--·-·---·--·--
        String /* */ barcode = "|·||·|··||||··||||·||·||·|·|||·||·||"; // 811424
        //                      ↑     ↑     ↑     ↑     ↑     ↑     ↑
        //      String Indices  0     6     12    18    24    30    36
        //
        //  values  █ 0 █ 1 █ 2 █ 4 █ 7 █ - █ 0 █ 1 █ 2 █ 4 █ 7 █ - █ 0 █ 1 █ 2 █ 4 █ 7 █ - █ 0 █ 1 █ 2 █ 4 █ 7 █ - █ 0 █ 1 █ 2 █ 4 █ 7 █ - █ 0 █ 1 █ 2 █ 4 █ 7 █ - █
        // barcode  █ | █ · █ | █ | █ · █ | █ · █ · █ | █ | █ | █ | █ · █ · █ | █ | █ | █ | █ · █ | █ | █ · █ | █ | █ · █ | █ · █ | █ | █ | █ · █ | █ | █ · █ | █ | █
        //          ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑
        // indices  0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36

        int segmentLength = 6;

        //  segmentCount => 6                 segmentLength => 6
        int segmentCount = barcode.length() / segmentLength;
        //                 barcode.length() => 36

        //                             segmentCount => 6
        String[] segments = new String[segmentCount];
        //     segments  █|·||·|█··||||█··||||█·||·||█·|·|||█·||·||█
        //               ↑      ↑      ↑      ↑      ↑      ↑      ↑
        // segmentIndex  0      1      2      3      4      5      6

        //                  segmentCount => 6
        for (int i = 0; i < segmentCount; i++) {
            //                   segmentLength => 6
            int startIndex = i * segmentLength;
            //                          segmentLength => 6
            int endIndex = startIndex + segmentLength;
            // i startIndex endIndex
            // 0          0        6
            // 1          6       12
            // 2         12       18
            // 3         18       24
            // 4         24       30
            // 5         30       36
            String segment = barcode.substring(startIndex, endIndex);
            segments[i] = segment;
        }

        int[] values = new int[]{0, 1, 2, 4, 7};
        // letter values  █ 0 █ 1 █ 2 █ 4 █ 7 █
        //                ↑   ↑   ↑   ↑   ↑   ↑
        //       indices  0   1   2   3   4   5

        //                        segmentCount => 6
        int[] allDigits = new int[segmentCount];
        //                                        segmentCount => 6
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
            allDigits[segmentIndex] = bitSum;
        }

        // all digits  █ 8 █ 1 █ 1 █ 4 █ 2 █ 4 █
        //             ↑   ↑   ↑   ↑   ↑   ↑   ↑
        //    indices  0   1   2   3   4   5   6

        //                     allDigits.length => 6
        //                     allDigits.length - 1 => 6 - 1
        //                                          => 5
        //  checkDigitIndex => 5
        int checkDigitIndex = allDigits.length - 1;

        int valueDigitsSum = 0;
        for (int digitIndex = 0; digitIndex < checkDigitIndex; digitIndex++) {
            int digit = allDigits[digitIndex];
            valueDigitsSum = valueDigitsSum + digit;
        }
        //                             valueDigitsSum => 8 + 1 + 1 + 4 + 2
        //                                            => 16
        //                             valueDigitsSum % 10 => 16 % 10
        //                                                 => 6
        //  calculatedCheckDigit => 10 - (6)
        //                       => 4
        int calculatedCheckDigit = 10 - (valueDigitsSum % 10);
        //                                    checkDigitIndex => 5
        if (calculatedCheckDigit == allDigits[checkDigitIndex]) {
            //                    checkDigitIndex - 1 => 5 - 1
            //                                        => 4
            for (int digitIndex = checkDigitIndex - 1; digitIndex >= 0; digitIndex--) {
                System.out.print(allDigits[digitIndex]);
            }
        } else {
            System.out.println("False code");
        }
        System.out.println();
        // Program output should be: 24118
    }
}
