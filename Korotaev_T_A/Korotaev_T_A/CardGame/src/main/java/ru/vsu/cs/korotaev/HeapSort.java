package ru.vsu.cs.korotaev;

public class HeapSort {
    private static <T> void siftDown(T[] data, int[] orderValues, int i, int n) {
        int orderValue = orderValues[i];
        T dataValue = data[i];
        while (true) {
            int childIndex = 2 * i + 1;

            if (childIndex >= n) {
                break;
            }

            if (childIndex + 1 < n && orderValues[childIndex + 1] > orderValues[childIndex]) {
                childIndex++;
            }

            if (orderValue > orderValues[childIndex]) {
                break;
            }

            orderValues[i] = orderValues[childIndex];
            data[i] = data[childIndex];
            i = childIndex;
        }
        orderValues[i] = orderValue;
        data[i] = dataValue;
    }

    public static <T> void sort(T[] data, int[] orderValues) throws Exception {
        if (data.length != orderValues.length) {
            throw new Exception("Arrays' sizes aren't equal");
        }

        int heapSize = orderValues.length;

        for (int i = heapSize / 2; i >= 0; i--) {
            siftDown(data, orderValues, i, heapSize);
        }

        while (heapSize > 1) {
            int tmp = orderValues[heapSize - 1];
            orderValues[heapSize - 1] = orderValues[0];
            orderValues[0] = tmp;

            T tmpObj = data[heapSize - 1];
            data[heapSize - 1] = data[0];
            data[0] = tmpObj;

            heapSize--;
            siftDown(data, orderValues, 0, heapSize);
        }
    }
}
