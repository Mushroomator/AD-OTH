import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Excercise03 {

    public static void main(String[] args) {
        //for(int n = 10000; n < 100000; n += 10000) {
            int n = 10;
            int[] testArr = new int[]{ 7, 12, 17, 34, 23, 18, 17, 43, 7, 34};
            System.out.println("\n\nAnzahl: " + n);
            int[] a = initArray(n);
            System.out.print("Heap Sort:\t");
            LocalDateTime start = LocalDateTime.now();
            heapSort(a, 0, a.length - 1);
            LocalDateTime ende = LocalDateTime.now();
            Long dauer = ChronoUnit.MILLIS.between(start, ende);
            System.out.println("Dauer " + dauer + " ms");
            System.out.print("Count Sort:\t");
            start = LocalDateTime.now();
            countSort(testArr, testArr.length, n);
            ende = LocalDateTime.now();
            dauer = ChronoUnit.MILLIS.between(start, ende);
            System.out.println("Dauer " + dauer + " ms");
            System.out.print("Map Sort:\t");
            start = LocalDateTime.now();
            //mapSort(a, a.length, 1.25D);
            ende = LocalDateTime.now();
            dauer = ChronoUnit.MILLIS.between(start, ende);
            System.out.println("Dauer " + dauer + " ms");
        //}

    }

    public static int[] initArray(int n) {
        int[] a = new int[n];

        for(int i = 0; i < n; ++i) {
            a[i] = (int)(Math.random() * 9000.0D) + 1000;
        }

        return a;
    }

    public static void printArray(int[] a) {
        int[] var4 = a;
        int var3 = a.length;

        for(int var2 = 0; var2 < var3; ++var2) {
            int i = var4[var2];
            System.out.print(i + " ");
        }

        System.out.print("\n");
    }

    public static void countSort(int[] a, int n, int k) {
        int schritte = 0;
        int[] c = new int[k + 1];

        int i;
        for(i = 0; i <= k; ++i) {
            c[i] = 0;
            ++schritte;
        }

        for(i = 0; i < n; ++i) {
            ++c[a[i]];
            ++schritte;
        }

        for(i = 1; i < k; ++i) {
            c[i] += c[i - 1];
            ++schritte;
        }

        int[] b = new int[n];

        for(i = 0; i < n; ++i) {
            b[c[a[i]] - 1] = a[i];
            ++schritte;
        }

        System.out.print(schritte + " Schritte\t");
    }

    /*
    public static void mapSort(int[] a, int n, double c) {
        int schritte = 0;
        int newn = (int)((double)n * c);
        int j = 0;
        int[] bin = new int[newn];
        int max = -2147483648;
        int min = 2147483647;

        int i;
        for(i = 0; i < newn; ++i) {
            bin[i] = -1;
        }

        for(i = 0; i < n; ++i) {
            if (a[i] < min) {
                min = a[i];
            }

            ++schritte;
            if (a[i] > max) {
                max = a[i];
            }

            ++schritte;
        }

        double dist = (double)(max - min) / (double)(newn - 1);

        for(i = 0; i < n; ++i) {
            int t = (int)((double)(a[i] - min) / dist);
            int insert = a[i];
            int left = false;
            if (bin[t] != -1 && insert <= bin[t]) {
                left = true;
            }

            while(bin[t] != -1) {
                int h;
                if (left) {
                    if (insert > bin[t]) {
                        h = bin[t];
                        bin[t] = insert;
                        insert = h;
                        ++schritte;
                    }

                    if (t > 0) {
                        --t;
                    } else {
                        left = false;
                    }
                } else {
                    if (insert <= bin[t]) {
                        h = bin[t];
                        bin[t] = insert;
                        insert = h;
                        ++schritte;
                    }

                    if (t < newn - 1) {
                        ++t;
                    } else {
                        left = true;
                    }
                }
            }

            bin[t] = insert;
        }

        for(i = 0; i < newn; ++i) {
            if (bin[i] != -1) {
                a[j++] = bin[i];
            }
        }

        System.out.print(schritte + " Schritte\t");
    }
    Excercise03
     */

    public static void heapSort(int[] a, int f, int l) {
        int schritte = 0;
        buildHeap(a, f, l);

        for(int i = l; i > f; --i) {
            int h = a[f];
            a[f] = a[i];
            a[i] = h;
            heapify(a, f, i - 1, f);
            schritte += 3;
        }

        System.out.print(schritte + " Schritte\t");
    }

    public static void buildHeap(int[] a, int f, int l) {
        int n = l - f + 1;

        for(int i = f + (n - 2) / 2; i >= f; --i) {
            heapify(a, f, l, i);
        }

    }

    public static void heapify(int[] a, int f, int l, int root) {
        int left = f + (root - f) * 2 + 1;
        int right = f + (root - f) * 2 + 2;
        int largest;
        if (left <= l && a[left] > a[root]) {
            largest = left;
        } else {
            largest = root;
        }

        if (right <= l && a[right] > a[largest]) {
            largest = right;
        }

        if (largest != root) {
            int h = a[root];
            a[root] = a[largest];
            a[largest] = h;
            heapify(a, f, l, largest);
        }

    }
}