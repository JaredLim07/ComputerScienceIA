package constella;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sort {

    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<String>();
        Sort mySort = new Sort();
        ArrayList newArray = mySort.mergeSort(names, names.size());
        System.out.println(newArray);
    }

    public ArrayList<String> mergeSort(ArrayList<String> input, int n) {
        ArrayList<String> tempArray = input;

        if (n >= 2) {
            int mid = n / 2;
            ArrayList<String> leftArray = new ArrayList<String>(Collections.nCopies(mid, "a"));
            ArrayList<String> rightArray = new ArrayList<String>(Collections.nCopies(n-mid, "a"));
            for (int i = 0; i < mid; i++) {
                leftArray.set(i, input.get(i));
            }
            for (int i = mid; i < n; i++) {
                rightArray.set(i - mid, input.get(i));
            }
            mergeSort(leftArray, mid);
            mergeSort(rightArray, n - mid);

            return merge(input, leftArray, rightArray, mid, n - mid);
        }
        return new ArrayList<String>();
    }

    public ArrayList<String> merge(ArrayList<String> input, ArrayList<String> leftArray, ArrayList<String> rightArray, int l, int r) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < l && j < r) {
            if (leftArray.get(i).compareTo(rightArray.get(j)) <= 0) {
                input.set(k++, leftArray.get(i++));
            } else {
                input.set(k++, rightArray.get(j++));
            }
        }
        while (i < l) {
            input.set(k++, leftArray.get(i++));
        }
        while (j < r) {
            input.set(k++, rightArray.get(j++));
        }
        return (input);
    }
}

