package zielcode;

public class Test {
    public static void main(String[] args) {
        String eingabe = args[0];
        String wertStelle = "01247";
        String a = "";
        for (int i = 0; i < 6; i++) {
            a = a + wertStelle + "-";
        }
        //System.out.println(a);
        //System.out.println(eingabe);
        char b = '|';
        String c = "";
        for (int i = 0; i < eingabe.length(); i++) {
            if (eingabe.charAt(i) != b) {
                c += a.charAt(i);
            }
        }
        //System.out.println(c);
        int[] ziffern = new int[c.length()];
        int d = 0;
        for (int i = 0; i < c.length(); i++) {
            ziffern[d] = Integer.parseInt(c.charAt(i) + "");
            d++;
        }
        //System.out.println(Arrays.toString(ziffern));
        int[] ziffern1 = new int[6];
        d = 0;
        for (int i = 0; i < ziffern1.length; i++) {
            if (ziffern.length % 2 == 0) {
                ziffern1[i] = ziffern[d] + ziffern[d + 1];
                d = d + 2;
            } else {
                ziffern1[i] = ziffern[i];
                ziffern1[ziffern1.length - 1] = ziffern[ziffern.length - 1] + ziffern[ziffern.length - 2];
            }
        }
        //System.out.println(Arrays.toString(ziffern1));
        int summe = 0;
        for (int i = 0; i < 5; i++) {
            summe = summe + ziffern1[i]; // summe +=ziffern1[i];
        }
        int prüf = 10 - (summe % 10);
        if (prüf != ziffern[ziffern.length - 1] && summe != 10) {
            System.out.println("Falscher Code");
        } else {
            for (int i = ziffern1.length - 2; i >= 0; i--) {
                System.out.print(ziffern1[i]);
            }
        }
    }
}