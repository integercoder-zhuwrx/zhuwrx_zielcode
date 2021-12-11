import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class ListBuilder<T> {
    public abstract List<T> buildList();
}

class IterableListBuilder<T> extends ListBuilder<T> {
    private Iterable<T> iterable;

    public IterableListBuilder(Iterable<T> iterable) {
        this.iterable = iterable;
    }

    @Override
    public List<T> buildList() {
        List<T> list = new ArrayList<>();
        for (T element : iterable) {
            list.add(element);
        }
        return list;
    }
}

class ArrayListBuilder<T> extends ListBuilder<T> {
    private T[] array;

    public ArrayListBuilder(T[] array) {
        this.array = array;
    }

    @Override
    public List<T> buildList() {
        return Arrays.asList(array);
    }
}

class IntArrayListBuilder extends ListBuilder<Integer> {
    private int[] array;

    public IntArrayListBuilder(int[] array) {
        this.array = array;
    }

    @Override
    public List<Integer> buildList() {
        List<Integer> list = new ArrayList<>();
        for (int element : array) {
            list.add(element);
        }
        return list;
    }
}

class StringListBuilder extends ListBuilder<Character> {
    private String text;

    public StringListBuilder(String text) {
        this.text = text;
    }

    @Override
    public List<Character> buildList() {
        List<Character> list = new ArrayList<>();
        for (Character element : text.toCharArray()) {
            list.add(element);
        }
        return list;
    }
}

public class ListDisplayer<T> {
    private ListBuilder<T> listBuilder;
    private final String elementLabel;
    private final String indexLabel;
    private String pad = " ";
    private int elementMinLength = -1;
    private String separator = "█";
    private String pointer = "↑";
    private boolean displaySeparatorLines = true;

    public ListDisplayer(ListBuilder<T> listBuilder, String elementLabel, String indexLabel) {
        this.listBuilder = listBuilder;
        this.elementLabel = elementLabel;
        this.indexLabel = indexLabel;
    }

    public static <T> ListDisplayer<T> from(Iterable<T> iterable,
                                            String elementLabel,
                                            String indexLabel) {
        return new ListDisplayer<T>(new IterableListBuilder<T>(iterable), elementLabel, indexLabel);
    }

    public static ListDisplayer<Character> from(String text,
                                                String elementLabel,
                                                String indexLabel) {
        return new ListDisplayer<>(new StringListBuilder(text), elementLabel, indexLabel);
    }

    public static ListDisplayer<Character> from(String text) {
        return from(text, "", "");
    }

    public static <T> ListDisplayer<T> from(T[] array,
                                            String elementLabel,
                                            String indexLabel) {
        return new ListDisplayer<T>(new ArrayListBuilder<T>(array), elementLabel, indexLabel);
    }

    public static ListDisplayer<Integer> from(int[] array,
                                              String elementLabel,
                                              String indexLabel) {
        return new ListDisplayer<Integer>(new IntArrayListBuilder(array), elementLabel, indexLabel);
    }

    public void setPad(String pad) {
        this.pad = pad;
    }

    public void setElementMinLength(int elementMinLength) {
        this.elementMinLength = elementMinLength;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void setPointer(String pointer) {
        this.pointer = pointer;
    }

    public void setDisplaySeparatorLines(boolean displaySeparatorLines) {
        this.displaySeparatorLines = displaySeparatorLines;
    }

    public static <T> void display(Iterable<T> iterable,
                                   int beginIndex,
                                   int endIndex,
                                   String elementLabel,
                                   String indexLabel) {
        var ld = from(iterable, elementLabel, indexLabel);
        ld.display(beginIndex, endIndex);
    }

    private int getLabelMaxWidth() {
        int elementLabelLength = elementLabel.length();
        int indexLabelLength = indexLabel.length();
        return Math.max(elementLabelLength, indexLabelLength);
    }

    private String getAdjustedLabel(String label) {
        int width = getLabelMaxWidth();
        if (width == 0) {
            return "";
        } else {
            return String.format("%" + width + "s  ", label);
        }
    }

    private String getAdjustedElementLabel() {
        return getAdjustedLabel(elementLabel);
    }

    private String getAdjustedIndexLabel() {
        return getAdjustedLabel(indexLabel);
    }

    private String getAdjustedEmptyLabel() {
        return getAdjustedLabel("");
    }

    private void displayList(List<T> list, int beginIndex) {
        StringBuilder dataRowBuilder = new StringBuilder();
        StringBuilder pipeRowBuilder = new StringBuilder();
        StringBuilder indexRowBuilder = new StringBuilder();

        dataRowBuilder.append(getAdjustedElementLabel());
        pipeRowBuilder.append(getAdjustedEmptyLabel());
        indexRowBuilder.append(getAdjustedIndexLabel());

        Runnable addSeparatorAndPointer = () -> {
            dataRowBuilder.append(separator);
            pipeRowBuilder.append(pointer);
        };

        int currentIndex = beginIndex;
        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            String formattedElement =
                    elementMinLength > 0
                            ? String.format("%" + elementMinLength + "s", element)
                            : String.format("%s", element);
            formattedElement = pad + formattedElement + pad;
            int formattedElementLength = formattedElement.length();
            String spaces = " ".repeat(formattedElementLength);
            String formattedIndex = String.format("%" + (-formattedElementLength - 1) + "s", currentIndex);
            addSeparatorAndPointer.run();
            dataRowBuilder.append(formattedElement);
            pipeRowBuilder.append(spaces);
            indexRowBuilder.append(formattedIndex);
            currentIndex += 1;
        }
        addSeparatorAndPointer.run();
        indexRowBuilder.append(currentIndex);

        conditionallyDisplaySeparatorLines(indexRowBuilder.length());
        System.out.println(dataRowBuilder);
        System.out.println(pipeRowBuilder);
        System.out.println(indexRowBuilder);
        conditionallyDisplaySeparatorLines(indexRowBuilder.length());
    }

    private void conditionallyDisplaySeparatorLines(int length) {
        if (displaySeparatorLines) {
            System.out.println("-".repeat(length));
        }
    }

    private List<T> getElementList() {
        return listBuilder.buildList();
    }

    public void display(int beginIndex, int endIndex) {
        var list = getElementList().subList(beginIndex, endIndex);
        displayList(list, beginIndex);
    }

    public void display(int beginIndex) {
        int endIndex = getElementList().size();
        display(beginIndex, endIndex);
    }

    public void display() {
        int beginIndex = 0;
        int endIndex = getElementList().size();
        display(beginIndex, endIndex);
    }
}
