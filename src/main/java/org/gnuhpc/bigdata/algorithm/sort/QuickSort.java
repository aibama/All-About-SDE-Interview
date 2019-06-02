package org.gnuhpc.bigdata.algorithm.sort;

import org.gnuhpc.bigdata.leetcode.utils.Utils;
import org.junit.Test;

import java.util.Random;

import static org.gnuhpc.bigdata.leetcode.utils.Utils.swap;

// O(nlogn) 不稳定 , inplace sort，原始类型的一般都是QuickSort
// 总结的不错： https://segmentfault.com/a/1190000004410119
public class QuickSort {
    /*
    Method 1: 分而治之
     */
    public void quickSort(int[] numbers, int low, int high) {
        if (low < high) {
            int q = partition4(numbers, low, high);
            Utils.printArray(numbers);
            System.out.println("q="+q);
            quickSort(numbers, low, q - 1);
            quickSort(numbers, q + 1, high);
        }
    }

    /*
    1.1 遍历法
     */
    private int partition(int[] numbers, int low, int high) {
        int pivot = numbers[low];
        // Last position where puts the no_larger element.
        int pos = low;
        for(int i=low+1; i<=high; i++){
            if(numbers[i] < pivot){
                swap(numbers,++pos,i);
            }
        }
        swap(numbers,low,pos);
        return pos;
    }

    /*
    1.2 填坑法
     */
    private int partition2(int[] numbers, int low, int high) {
        //挖坑
        int pivot = numbers[low];//选第一个元素作为枢纽元
        int i = low;
        int j = high;
        while(i < j)
        {
            //之所以从后面先找是因为nums[low]已经被保存好了
            while(i < j && numbers[j] >= pivot)j--;
            //填坑,等于后面又多出一个坑
            numbers[i] = numbers[j];//从后面开始找到第一个小于pivot的元素，放到low位置
            while(i < j && numbers[i] < pivot)i++;
            //再填坑，等于前边又多出一个坑
            numbers[j] = numbers[i];//从前面开始找到第一个大于pivot的元素，放到high位置
        }
        //最后把最初的元素填上
        numbers[i] = pivot;//最后枢纽元放到low的位置
        return i;
    }

    /*
    1.3 双指针交换法；最好用，推荐记忆，从左边找一个不应该在左边的，从右边找一个不应该在右边的，然后交换
     */
    public int partition3(int[] numbers, int low, int high) {
        int pivot = numbers[low];
        //low 为pivot的idx，所以从low+1开始找
        int i = low+1;
        int j = high;
        while(i <= j){
            // 这个写法有越界的问题，输入测试案例{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
            /*while(numbers[i] < pivot && i <= j) i++;
            while(numbers[j] >= pivot && i <= j) j--;*/
            //改成这种写法后，对于上面列举的测试案例，
            // 会出现0被归到一边，左右子树不均衡，算法退化成O(N^2)
            while(i <= j && numbers[i] < pivot) i++;
            while(i <= j && numbers[j] >= pivot) j--;

            if(i <= j){
                swap(numbers,i,j);
            }
        }
        //交换j是因为它从它的有效区域探头出来了，最后来交换low和j
        swap(numbers,low,j);
        return j;
    }

    // add by Tina 双路排序
    // 返回p, 使得arr[l...p-1] < arr[p] ; arr[p+1...r] > arr[p]
    public int partition4(int[] numbers, int low, int high) {
        //加上swap这句，当数组是有序数组时，int[] arr = {1,2,3,4,5,6,7};不会退化成O(N^2)
        swap( numbers, low , (int)(Math.random()*(high-low+1))+low );
        int pivot = numbers[low];
        //low 为pivot的idx，所以从low+1开始找
        int i = low+1;
        int j = high;
        while(i <= j){
            // 注意这里的边界, 两边都不取=，这样相等的数仍然会被交换，
            // 结果是partition位置为中间，算法复杂度维持在O(NlogN)
            while(i <= j && numbers[i] < pivot) i++;
            while(i <= j && numbers[j] > pivot) j--;

            if(i <= j){
                swap(numbers,i,j);
                i++;
                j--;
            }

        }
        //交换j是因为它从它的有效区域探头出来了，最后来交换low和j
        swap(numbers,low,j);
        return j;
    }



    /*
    Method 2 : 三路
     */
    private void quickSort3Way(int[] numbers, int left, int right) {
        if (left >= right) return;
        int[] pivots = parition3way(numbers, left, right);
        int lt = pivots[0];
        int gt = pivots[1];
        // recursive sort
        quickSort3Way(numbers, left, lt - 1);
        quickSort3Way(numbers, gt + 1, right);
    }

    /*
        a[l,lt-1] < pivot
        a[lt, i-1] = pivot
        a[i,gt] = unseen
        a[gt+1, r] > pivot
     */

    // 注意：在把i和lt交换时, i可以increment (因为我们知道a[lt]==pivot),
    // 但是i和gt交换时, i不能increment: 因为a[gt]不知道多大, 所以i位置要继续检查.

    public int[] parition3way(int[] numbers, int l, int r) {
        swap( numbers, l , (int)(Math.random()*(r-l+1))+l );
        int pivot = numbers[l];
        int lt = l, gt = r;
        int i = l;

        while (i<=gt) {
            if (numbers[i] < pivot)
                swap(numbers, i++, lt++);
            else if (numbers[i] > pivot)
                swap(numbers, i, gt--);
            else // numbers[i]==  pivot
                i++;
        }

        return new int[] { lt, gt };
    }

    // add by Tina
    // 与上面方法在边界值含以上有所区别
    private void quickSort3Way2(int[] numbers, int left, int right) {
        if (left >= right) return;
        int[] pivots = parition3way(numbers, left, right);
        int lt = pivots[0];
        int gt = pivots[1];
        // recursive sort
        quickSort3Way(numbers, left, lt);
        quickSort3Way(numbers, gt, right);
    }

    // add by Tina
    // 思路简介：
    // 我们通常可以用进行到中间状态来描述这个算法
    // pivot = numbers[l] //可以是经过随机挑选然后交换的
    // numbers[l+1,lt]<pivot, numbers[lt+1,i-1] == v,numbers[gt,r] > v
    // numbers[i,gt-1]待判断
    public int[] parition3way2(int[] numbers, int l, int r) {
        swap( numbers, l , (int)(Math.random()*(r-l+1))+l );
        int pivot = numbers[l];
        int lt = l, gt = r+1;
        int i = l+1;

        while (i<gt) {
            if (numbers[i] < pivot){
                swap(numbers, i, lt);
                i++;lt++;}
            else if (numbers[i] > pivot){
                swap(numbers, i, gt-1);
                gt--;}
            else // numbers[i] ==  pivot
                i++;
        }

        return new int[] { lt, gt };
    }

    /*
    Method3 : 遍历版本， 没什么大意义，仅有帮助理解之作用
     */
    public void quickSortIterative (int arr[], int l, int h)
    {
        // Create an auxiliary stack
        int[] stack = new int[h-l+1];

        // initialize top of stack
        int top = -1;

        // push initial values of l and h to stack
        stack[++top] = l;
        stack[++top] = h;

        // Keep popping from stack while is not empty
        while (top >= 0)
        {
            // Pop h and l
            h = stack[top--];
            l = stack[top--];

            // Set pivot element at its correct position
            // in sorted array
            int p = partition(arr, l, h);

            // If there are elements on left side of pivot,
            // then push left side to stack
            if (p-1 > l)
            {
                stack[++top] = l;
                stack[++top] = p - 1;
            }

            // If there are elements on right side of pivot,
            // then push right side to stack
            if (p+1 < h)
            {
                stack[++top] = p + 1;
                stack[++top] = h;
            }
        }
    }


    private void sort(int[] arr) {
        quickSort(arr,0,arr.length-1);
//        quickSort3Way(arr,0,arr.length-1);

        //quickSortIterative(arr,0,arr.length-1);
    }

    @Test
    public void test() {
//        int[] arr = Utils.generateRandomArray(8, 0, 100);
        //int[] arr = {3,2,8,5,6,4,1};
        //int[] arr = {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int[] arr = {1,2,3,4,5,6,7};
//        int[] arr = {52, 14, 71, 62, 76, 30, 6, 74};
        Utils.printArray(arr);
        sort(arr);
        Utils.printArray(arr);
    }

}
