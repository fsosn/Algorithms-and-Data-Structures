package pl.edu.pw.ee;

import pl.edu.pw.ee.services.Sorting;

public class InsertionSort implements Sorting {

    @Override
    public void sort(double[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Nums array cannot be null!");
        }

        insertionSort(nums);
    }

    private void insertionSort(double[] nums) {
        for (int i = 0; i < nums.length; i++) {
            double currentValue = nums[i];
            int j = i - 1;

            while (j >= 0 && currentValue < nums[j]) {
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = currentValue;
        }
    }
}
