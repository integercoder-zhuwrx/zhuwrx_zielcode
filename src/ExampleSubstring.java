public class ExampleSubstring {
    public static void main(String[] args) {
        printSeparatorLine();

        String text = "Happy New Year.";
        println("String text = \"Happy New Year.\";");

        var displayer = ListDisplayer.from(text, "characters", "indices");

        printSeparatorLine();

        displayer.display();

        printSeparatorLine();

        {
            System.out.println("Create a substring beginning at index 6 and ending at index 9.");
            println();
            println("text.substring(6, 9)");
            println();
            displayer.display(6, 9);
            println();

            String result = text.substring(6, 9);
            printValue("substring", result);
            printValue("   length", result.length());

            println();
            println("Please note that the length of the substring is equal to endIndex - beginIndex.");
            println("Which in this case, 9 - 6 = 3.");
        }

        printSeparatorLine();

        {
            System.out.println("Create a substring beginning at index 5 to the end of the string.");
            println();
            println("text.substring(5)");
            println();
            displayer.display(5);
            println();

            String result = text.substring(5);
            printValue("substring", result);
            printValue("   length", result.length());

            println();
            println("Please note that the length of the substring is equal to endIndex - beginIndex.");
            println("Which in this case, 15 - 5 = 10.");
        }

        printSeparatorLine();
    }

    private static void println() {
        System.out.println();
    }

    private static void println(Object o) {
        System.out.println(o);
    }

    private static void printSeparatorLine() {
        println();
        println("-".repeat(100));
        println();
    }

    private static void printValue(String label, Object result) {
        if (result instanceof String) {
            System.out.printf("%s: [%s] <-- The value of the string is inside the brackets.\n", label, result);
        } else {
            System.out.printf("%s: %s\n", label, result);
        }
    }
}
