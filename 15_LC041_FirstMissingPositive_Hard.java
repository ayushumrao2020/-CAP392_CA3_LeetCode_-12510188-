
class Solution {
    public int firstMissingPositive(int[] nums) {

        int i = 0;

        // Step 1: Put every number at its correct position
        while (i < nums.length) {

            int correctIndex = nums[i] - 1;

            if (nums[i] > 0 &&
                nums[i] <= nums.length &&
                nums[i] != nums[correctIndex]) {

                int temp = nums[i];
                nums[i] = nums[correctIndex];
                nums[correctIndex] = temp;

            } else {
                i++;
            }
        }

        // Step 2: Find the missing number
        for (int j = 0; j < nums.length; j++) {

            if (nums[j] != j + 1) {
                return j + 1;
            }
        }

        return nums.length + 1;
    }
}
