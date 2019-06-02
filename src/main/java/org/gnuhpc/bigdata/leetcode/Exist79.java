package org.gnuhpc.bigdata.leetcode;

public class Exist79 {
    public boolean exist(char[][] board, String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        boolean[][] isBeingVisited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                if (board[i][j] == word.charAt(0) &&
                        dfs(board, word, isBeingVisited, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, String word, boolean[][] isBeingVisited, int index, int row, int col) {
        if (index == word.length()) {  // reached the end of the word
            return true;
        }
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length
                || isBeingVisited[row][col] || word.charAt(index) != board[row][col]) {
            return false;
        }
        isBeingVisited[row][col] = true;
        if (dfs(board, word, isBeingVisited, index + 1, row - 1, col)) {
            return true;
        }
        if (dfs(board, word, isBeingVisited, index + 1, row + 1, col)) {
            return true;
        }
        if (dfs(board, word, isBeingVisited, index + 1, row, col - 1)) {
            return true;
        }
        if (dfs(board, word, isBeingVisited, index + 1, row, col + 1)) {
            return true;
        }
        isBeingVisited[row][col] = false;  // umark current point; it might be used in other routes
        return false;
    }

    // add by tina
    // 二维平面的坐标偏移量
    private int d[][]  = {{-1,0},{0,1},{1,0},{0,-1}};
    int m,n;

    public boolean exist2(char[][] board, String word) {

        m = board.length;
        if(m<1) return false;
        n = board[0].length;
        boolean[][] used= new boolean[m][n];
        for(int i = 0; i < m; i++){
            for(int j = 0 ; j < n; j++){
                if(searchWord(board, word, 0, i,j,used)) return true;
            }
        }

        return false;
    }

    // 寻找board[startx][starty]开始，word[index,...,word.length()-1]的字符串
    public boolean searchWord(char[][] board, String word,int index, int startx, int starty, boolean[][] used){

        //System.out.println(word.length());
        /*
        * 注意这个地方不能下面这样写，[['a']], "a"是反例
        *因为我们这里是先盼相等，再往四周开始搜索时，是先盼下标是否越界，再决定是否搜索的

        if (index == word.length()) {  // reached the end of the word
            return true;
        }

        */
        if( index == word.length()-1 ) return board[startx][starty] == word.charAt(index);

        if(board[startx][starty] == word.charAt(index)){
            //向坐标[startx,starty]四周发散寻找下一个有效char，四周的做法可以是枚举，也可以通过以下循环来实现
            used[startx][starty] = true;
            for(int i = 0;i<4;i++){
                int newx = startx + d[i][0];
                int newy = starty + d[i][1];
                if(inArea(newx, newy) && !used[newx][newy]){
                    if(searchWord(board,word,index+1,newx,newy,used)) return true;
                }
            }
            used[startx][starty] = false;
        }

        return false;
    }

    public boolean inArea(int x,int y){
        return (x >= 0 && x<m && y >= 0 && y < n );
    }
}
