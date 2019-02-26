package org.gnuhpc.bigdata.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HasGroupsSizeX914 {
    //Method1 :HashMap计数+暴力
    public boolean hasGroupsSizeX(int[] deck) {
        if (deck==null && deck.length==0) return false;
        Map<Integer,Integer> countMap = caclCount(deck);
        return canGroup(countMap);
    }

    private boolean canGroup(Map<Integer, Integer> countMap) {
        int min = countMap.values().stream().min(Integer::compareTo).get();
        for (int i = 2; i <= min; i++) {
            int finalI = i;
            if(countMap.values().stream().allMatch(n -> n % finalI == 0))
                return true;
        }
        return false;
    }


    private Map<Integer, Integer> caclCount(int[] deck) {
        Map<Integer,Integer> res = new HashMap<>();

        for (int n: deck){
            res.put(n, res.getOrDefault(n,0)+1);
        }

        return res;
    }

    //Method 2; GCD
    public boolean hasGroupsSizeX2(int[] deck) {
        int[] count = new int[10000];
        for (int c: deck)
            count[c]++;

        int X = -1;
        for (int i = 0; i < 10000; ++i)
            if (count[i] > 0) {
                if (X == -1)
                    X = count[i];
                else
                    X = gcd(X, count[i]);
            }

        return X >= 2;
    }

    //TODO 求最大公约数
    public int gcd(int a, int b) {
        return a == 0 ? b : gcd(b%a, a);
    }

    @Test
    public void test(){
        System.out.println(hasGroupsSizeX(new int[]{1,1,2,2,2,2}));
        System.out.println(hasGroupsSizeX(new int[]{1,1,1,2,2,2,3,3}));
    }
}