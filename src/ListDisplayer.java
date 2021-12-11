import java.util.ArrayList;
import java.util.List;

public class ListDisplayer<T> {
    private final List<T> elements = new ArrayList<T>();
    private final String elementLabel;
    private final String indexLabel;

    public ListDisplayer(List<T> list, String elementLabel, String indexLabel) {
        this.elementLabel = elementLabel;
        this.indexLabel = indexLabel;
        elements.addAll(list);
    }

    public static <T> ListDisplayer<T> from(Iterable<T> iterable,
                                            String elementLabel,
                                            String indexLabel) {
        List<T> list = new ArrayList<>();
        for (T element : iterable) {
            list.add(element);
        }
        return new ListDisplayer<>(list, elementLabel, indexLabel);
    }

    public static ListDisplayer<Character> from(String text,
                                                String elementLabel,
                                                String indexLabel) {
        List<Character> list = new ArrayList<>();
        for (Character element : text.toCharArray()) {
            list.add(element);
        }
        return new ListDisplayer<>(list, elementLabel, indexLabel);
    }

    public static ListDisplayer<Character> from(String text) {
        return from(text, "", "");
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

        Runnable addPipe = () -> {
            String pipe = "|";
            dataRowBuilder.append(pipe);
            pipeRowBuilder.append(pipe);
        };

        int currentIndex = beginIndex;
        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            String formattedElement = String.format(" %s ", element);
            int formattedElementLength = formattedElement.length();
            String spaces = " ".repeat(formattedElementLength);
            String formattedIndex = String.format("%" + (-formattedElementLength - 1) + "s", currentIndex);
            addPipe.run();
            dataRowBuilder.append(formattedElement);
            pipeRowBuilder.append(spaces);
            indexRowBuilder.append(formattedIndex);
            currentIndex += 1;
        }
        addPipe.run();
        indexRowBuilder.append(currentIndex);

        System.out.println(dataRowBuilder);
        System.out.println(pipeRowBuilder);
        System.out.println(indexRowBuilder);
    }

    public void display(int beginIndex, int endIndex) {
        var list = elements.subList(beginIndex, endIndex);
        displayList(list, beginIndex);
    }

    public void display(int beginIndex) {
        int endIndex = elements.size();
        display(beginIndex, endIndex);
    }

    public void display() {
        int beginIndex = 0;
        int endIndex = elements.size();
        display(beginIndex, endIndex);
    }
}
