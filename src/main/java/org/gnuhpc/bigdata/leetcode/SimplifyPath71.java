package org.gnuhpc.bigdata.leetcode;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class SimplifyPath71 {
    public String simplifyPath(String path) {
        Deque<String> stack = new LinkedList<>();
        Set<String> skip = new HashSet<>(Arrays.asList("..", ".", ""));
        for (String dir : path.split("/")) {
            if (dir.equals("..") && !stack.isEmpty()) stack.pop();
            else if (!skip.contains(dir)) stack.push(dir);
        }
        String res = "";
        for (String dir : stack) res = "/" + dir + res; //TODO 利用遍历获取 倒序stack序列
        return res.isEmpty() ? "/" : res;
    }

    @Test
    public void test(){
        assertEquals(simplifyPath("/home//gnuhpc/"), "/home/gnuhpc");
        assertEquals(simplifyPath("/.."), "/");
        assertEquals(simplifyPath("/"), "/");
        assertEquals(simplifyPath("/a/./b/../../c/"), "/c");
        assertEquals(simplifyPath("../"), "/");
    }
}
