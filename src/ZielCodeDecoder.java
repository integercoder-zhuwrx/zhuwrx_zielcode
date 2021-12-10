public class ZielCodeDecoder {
    public static void main(String[] args) {
        //     Segment Indices  0     1     2     3     4     5     6
        //                      |     |     |     |     |     |     |
        //                      01247-01247-01247-01247-01247-01247-
        //                      -·--·-··----··----·--·--·-·---·--·--
        String /* */ barcode = "|·||·|··||||··||||·||·||·|·|||·||·||"; // 811424
        //                      |     |     |     |     |     |     |
        //      String Indices  0     6     12    18    24    30    36
        //
        //  values  | 0 | 1 | 2 | 4 | 7 | - | 0 | 1 | 2 | 4 | 7 | - | 0 | 1 | 2 | 4 | 7 | - | 0 | 1 | 2 | 4 | 7 | - | 0 | 1 | 2 | 4 | 7 | - | 0 | 1 | 2 | 4 | 7 | - |
        // barcode  | - | · | - | - | · | - | · | · | - | - | - | - | · | · | - | - | - | - | · | - | - | · | - | - | · | - | · | - | - | - | · | - | - | · | - | - |
        //          |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |
        // indices  0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36

        int segmentLength = 6;

        //  segmentCount => 6                 segmentLength => 6
        int segmentCount = barcode.length() / segmentLength;
        //                 barcode.length() => 36

        //                             segmentCount => 6
        String[] segments = new String[segmentCount];
        //     segments  | Segment 0 | Segment 1 | Segment 2 | Segment 3 | Segment 4 | Segment 5 |
        //               |           |           |           |           |           |           |
        // segmentIndex  0           1           2           3           4           5           6

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
        //     values  | 0 | 1 | 2 | 4 | 7 |
        //             |   |   |   |   |   |
        // valueIndex  0   1   2   3   4   5

        //                     segmentCount => 6
        int[] digits = new int[segmentCount];
        //                                        segmentCount => 6
        for (int segmentIndex = 0; segmentIndex < segmentCount; segmentIndex++) {
            String segment = segments[segmentIndex];
            int sum = 0;
            for (int valueIndex = 0; valueIndex < values.length; valueIndex++) {
                char bit = segment.charAt(valueIndex);
                if (bit != '|') {
                    int value = values[valueIndex];
                    sum = sum + value;
                }
            }
            digits[segmentIndex] = sum;
        }

        //     digits  | 8 | 1 | 1 | 4 | 2 | 4 |
        //             |   |   |   |   |   |   |
        // digitIndex  0   1   2   3   4   5   6

        //                                    digits.length => segmentCount => 6
        for (int digitIndex = 0; digitIndex < digits.length; digitIndex++) {
            int digit = digits[digitIndex];
            System.out.print(digit);
        }
        System.out.println();
        // Program output: 811424
    }
}
