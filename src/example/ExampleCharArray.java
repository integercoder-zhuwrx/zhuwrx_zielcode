package example;

public class ExampleCharArray {
    public static void main(String[] args) {
        char[] chars = new char[]{'H', 'a', 'p', 'p', 'y', ' ', 'N', 'e', 'w', ' ', 'Y', 'e', 'a', 'r', '.'};
        //   chars  | H | a | p | p | y |   | N | e | w |   | Y | e | a | r | . |
        //          |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |
        // indices  0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  15

        {
            // Prints 3 elements in the array "chars" starting at index 6.
            int beginIndex = 6;
            int length = 3;

            //                           beginIndex + length => 6 + 3 => 9
            for (int i = beginIndex; i < beginIndex + length; i++) {
                System.out.print(chars[i]);
            }
            System.out.println();
        }
    }
}
