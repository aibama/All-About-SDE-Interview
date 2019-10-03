package org.gnuhpc.bigdata.leetcode;

import org.gnuhpc.bigdata.datastructure.unionfind.QuickUnion;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

public class Solve130 {
    /*
    Method1 : BFS, 依次进行BFS，需要处理特殊情况
    Use BFS.
    This problem is similar to Number of Islands.
    In this problem, only the cells on the boarders can not be surrounded. So we can first merge those O's on the boarders like in Number of Islands and replace O's with 'B',
    and then scan the board and replace all O's left (if any).
     */
    int[][] dr = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    int     r, c;

    public void solve(char[][] board) {
        if (board == null || board.length == 0)
            return;
        r = board.length;
        c = board[0].length;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (isBoard(i, j) && board[i][j] == 'O') {
                    Queue<int[]> queue = new LinkedList<>();
                    board[i][j] = 'B';
                    queue.offer(new int[]{i, j});
                    while (!queue.isEmpty()) {
                        int[] point = queue.poll();
                        for (int[] d : dr) {
                            int x = d[0] + point[0];
                            int y = d[1] + point[1];
                            if (isValid(x, y) && board[x][y] == 'O') {
                                board[x][y] = 'B';
                                queue.offer(new int[]{x, y});
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == 'B')
                    board[i][j] = 'O';
                else if (board[i][j] == 'O')
                    board[i][j] = 'X';
            }
        }
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < r && y >= 0 && y < c;
    }

    private boolean isBoard(int x, int y) {
        return x == 0 || x == r - 1 || y == 0 || y == c - 1;
    }


    @Test
    public void test() {
        char[][] arr = new char[][]{
                {'X', 'O', 'X', 'O', 'X', 'O'},
                {'O', 'X', 'O', 'X', 'O', 'X'},
                {'X', 'O', 'X', 'O', 'X', 'O'},
                {'O', 'X', 'O', 'X', 'O', 'X'}
        };
        solve(arr);

        System.out.println();
    }

    // Method 2: DFS
    // add by tina
    // 定义4个方向遍历的偏移量
    // 本体不需要使用used这样的数组，因为可以在原二维数组上标记
    // 对比第200题-岛屿个数

    public void solve2(char[][] board) {
        if (board == null || board.length == 0)
            return;
        r = board.length;
        c = board[0].length;

        //merge O's on left & right boarder
        for (int i = 0; i < r; i++) {
            if (board[i][0] == 'O') {
                dfs(board, i, 0);
            }

            if (board[i][c - 1] == 'O') {
                dfs(board, i, c - 1);
            }
        }

        //merge O's on top & bottom boarder
        for (int j = 0; j < c; j++) {
            if (board[0][j] == 'O') {
                dfs(board, 0, j);
            }

            if (board[r - 1][j] == 'O') {
                dfs(board, r - 1, j);
            }
        }

        //process the board
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
                else if (board[i][j] == '*') {//同一个位置，不会被扫2次
                    board[i][j] = 'O';
                }
            }
        }
    }

    public void dfs(char[][] board, int startx, int starty) { //, boolean[][] used
        board[startx][starty] = '*';

        for (int[] d : dr) {
            int newx = startx + d[0];
            int newy = starty + d[1];
            if (isValid(newx, newy) && board[newx][newy] == 'O') {//&& !used[newx][newy]
                dfs(board, newx, newy); //,used
            }
        }
    }

    /*
    Method3 : Union-Find
     */

    public void solve3(char[][] board) {
        if (board == null || board.length == 0)
            return;

        int rows = board.length;
        int cols = board[0].length;

        // 用一个虚拟节点, 边界上的O 的父节点都是这个虚拟节点
        QuickUnion qu = new QuickUnion(rows * cols + 1);

        //并差集虚拟节点
        int dummyNode = rows * cols;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    // 遇到O进行并查集操作合并
                    if (i == 0 || i == rows - 1 || j == 0 || j == cols - 1) {
                        // 边界上的O,把它和dummyNode 合并成一个连通区域.
                        qu.union(qu.node(cols, i, j), dummyNode);
                    }
                    else {
                        // 和上下左右合并成一个连通区域.
                        if (i > 0 && board[i - 1][j] == 'O')
                            qu.union(qu.node(cols, i, j), qu.node(cols, i - 1, j));
                        if (i < rows - 1 && board[i + 1][j] == 'O')
                            qu.union(qu.node(cols, i, j), qu.node(cols, i + 1, j));
                        if (j > 0 && board[i][j - 1] == 'O')
                            qu.union(qu.node(cols, i, j), qu.node(cols, i, j - 1));
                        if (j < cols - 1 && board[i][j + 1] == 'O')
                            qu.union(qu.node(cols, i, j), qu.node(cols, i, j + 1));
                    }
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (qu.connected(qu.node(cols, i, j), dummyNode)) {
                    // 和dummyNode 在一个连通区域的,那么就是O；
                    board[i][j] = 'O';
                }
                else {
                    board[i][j] = 'X';
                }
            }
        }
    }
}
