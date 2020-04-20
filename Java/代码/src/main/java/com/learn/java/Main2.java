package com.learn.java;

import java.util.*;

/**
 * @author xzy
 * @date 2020-04-06 20:53
 * 说明：
 */
public class Main2 {
    public static void main(String[] args) {
        int[] originalArray = new int[]{1, -2, 3, 10, -4, 7, 2, -5};
        Map<String, Integer> maxSubArray;
        maxSubArray = Main2.getMaxCrossingSubArray(originalArray, 0, originalArray.length - 1);
        maxSubArray = Main2.getMaxSubArray(originalArray, 0, originalArray.length - 1);
        System.out.println(maxSubArray.get("maxSubSum"));
    }

    /**
     * 获取包含序列中部元素的最大子序列
     *
     * @param originalArray - 原序列
     * @param low           - 序列左边界
     * @param high          - 序列右边界
     * @return - 包含原序列中部元素的最大子序列
     */
    public static Map<String, Integer> getMaxCrossingSubArray(int[] originalArray, int low, int high) {
        Map<String, Integer> maxCrossingSubArray = new HashMap<>(3);
        //如果原序列只有一个元素
        if (high == low) {
            maxCrossingSubArray.put("maxSubLeft", low);
            maxCrossingSubArray.put("maxSubRight", high);
            maxCrossingSubArray.put("maxSubSum", originalArray[low]);
            return maxCrossingSubArray;
        }
        //最大子序列和，最大子序列左边界，最大子序列右边界
        int maxSum, maxLeft, maxRight;
        //若原序列有奇数个元素，mid为序列中点元素下标；若原序列有偶数个元素，mid为序列中部靠左元素下标。
        int mid = (high + low) / 2;
        if ((high - low + 1) % 2 != 0) {
            //如果原序列有奇数个元素，最大子序列必须包含元素[mid]。
            //                             例如[ -1 , 2 , -3]
            maxSum = originalArray[mid];
            maxLeft = mid;
            maxRight = mid;

        } else {
            //如果原序列有偶数个元素，最大子序列必须包含元素[mid,mid+1]。
            //                              例如[ -1 , 2 , -3 , 4]
            maxSum = originalArray[mid] + originalArray[mid + 1];
            maxLeft = mid;
            maxRight = mid + 1;
        }

        int sum = maxSum, readNow = maxLeft - 1;
        while (readNow >= low) {
            sum += originalArray[readNow];
            if (sum > maxSum) {
                maxSum = sum;
                maxLeft = readNow;
            }
            readNow--;
        }

        sum = maxSum;
        readNow = maxRight + 1;
        while (readNow <= high) {
            sum += originalArray[readNow];
            if (sum > maxSum) {
                maxSum = sum;
                maxRight = readNow;
            }
            readNow++;
        }

        maxCrossingSubArray.put("maxSubLeft", maxLeft);
        maxCrossingSubArray.put("maxSubRight", maxRight);
        maxCrossingSubArray.put("maxSubSum", maxSum);
        return maxCrossingSubArray;
    }

    /**
     * 获取序列的最大子序列
     *
     * @param originalArray - 原序列
     * @param low           - 序列下界
     * @param high          - 序列上界
     * @return - 序列最大子序列
     */
    public static Map<String, Integer> getMaxSubArray(int[] originalArray, int low, int high) {
        Map<String, Integer> maxSubArray = new HashMap<>(3);
        //如果原序列只有一个元素
        if (high == low) {
            maxSubArray.put("maxSubLeft", low);
            maxSubArray.put("maxSubRight", high);
            maxSubArray.put("maxSubSum", originalArray[low]);
            return maxSubArray;
        }

        Map<String, Integer> leftMaxSubArray;
        Map<String, Integer> crossingMidMaxSubArray;
        Map<String, Integer> rightMaxSubArray;

        int mid = (low + high) / 2;
        if ((high - low + 1) % 2 != 0) {
            //[1 , -2 , 3 , -4 , 5 ]
            //      ^  mid
            leftMaxSubArray = getMaxSubArray(originalArray, low, mid - 1);
        } else {
            //[1 , -2 , 3 , -4]
            //     mid
            leftMaxSubArray = getMaxSubArray(originalArray, low, mid);
        }
        crossingMidMaxSubArray = getMaxCrossingSubArray(originalArray, low, high);
        rightMaxSubArray = getMaxSubArray(originalArray, mid + 1, high);

        int maxLeftSum = leftMaxSubArray.get("maxSubSum");
        int maxCrossingSum = crossingMidMaxSubArray.get("maxSubSum");
        int maxRightSum = rightMaxSubArray.get("maxSubSum");

        if (maxLeftSum >= maxCrossingSum && maxLeftSum >= maxRightSum) {
            return leftMaxSubArray;
        } else if (maxCrossingSum >= maxLeftSum && maxCrossingSum >= maxRightSum) {
            return crossingMidMaxSubArray;
        } else {
            return rightMaxSubArray;
        }
    }
}
