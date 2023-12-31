package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author voyagingCoder
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Move only one column North
     * @param column
     */
    private boolean oneColumnNorth(int column){
        int BoardSize = board.size();
        boolean changed = false;
        // TODO: 单列向上移动的逻辑
        for(int i = BoardSize-1; i >= 0; i--){//i为当前进行处理的位置
            if (board.tile(column, i) == null){
                //当前为空，把下一个tile挪到当前位置
                for (int j = i - 1; j >= 0; j--) {
                    if (board.tile(column, j) != null) {
                        Tile t = board.tile(column, j);
                        board.move(column, i, t);
                        changed = true;
                        break;
                    }
                }
            }
            //当前不为空，找到下一个不为空的位置，相同则merge，不同则break
            for (int j = i - 1; j >= 0; j--) {
                if (board.tile(column, j) != null) {
                    if (board.tile(column, j).value() == board.tile(column, i).value()) {
                        Tile t = board.tile(column, j);
                        board.move(column, i, t);
                        changed = true;
                        score += board.tile(column, i).value();
                    }
                    break;
                }
            }
        }
        return changed;
    }

    /** Move only one column South
     * @param column
     */
    private boolean oneColumnSouth(int column){
        int BoardSize = board.size();
        boolean changed = false;
        // TODO: 单列向下移动的逻辑
        for(int i = 0; i < BoardSize; i++){//i为当前进行处理的位置
            if (board.tile(column, i) == null){
                //当前为空，把下一个tile挪到当前位置
                for (int j = i + 1; j < BoardSize; j++) {
                    if (board.tile(column, j) != null) {
                        Tile t = board.tile(column, j);
                        board.move(column, i, t);
                        changed = true;
                        break;
                    }
                }
            }
            //当前不为空，找到下一个不为空的位置，相同则merge，不同则break
            for (int j = i + 1; j < BoardSize; j++) {
                if (board.tile(column, j) != null) {
                    if (board.tile(column, j).value() == board.tile(column, i).value()) {
                        Tile t = board.tile(column, j);
                        board.move(column, i, t);
                        changed = true;
                        score += board.tile(column, i).value();
                    }
                    break;
                }
            }
        }
        return changed;
    }

    /** Move only one row East
     * @param row
     */
    private boolean oneRowEast(int row){
        int BoardSize = board.size();
        boolean changed = false;
        // TODO: 单行向右移动的逻辑
        for(int i = BoardSize-1; i >= 0; i--){//i为当前进行处理的位置
            if (board.tile(i, row) == null){
                //当前为空，把下一个tile挪到当前位置
                for (int j = i - 1; j >= 0; j--) {
                    if (board.tile(j, row) != null) {
                        Tile t = board.tile(j, row);
                        board.move(i, row, t);
                        changed = true;
                        break;
                    }
                }
            }
            //当前不为空，找到下一个不为空的位置，相同则merge，不同则break
            for (int j = i - 1; j >= 0; j--) {
                if (board.tile(j, row) != null) {
                    if (board.tile(j, row).value() == board.tile(i, row).value()) {
                        Tile t = board.tile(j, row);
                        board.move(i, row, t);
                        changed = true;
                        score += board.tile(i, row).value();
                    }
                    break;
                }
            }
        }
        return changed;
    }

    /** Move only one row West
     * @param row
     */
    private boolean oneRowWest(int row){
        int BoardSize = board.size();
        boolean changed = false;
        // TODO: 单行向左移动的逻辑
        for(int i = 0; i < BoardSize; i++){//i为当前进行处理的位置
            if (board.tile(i, row) == null){
                //当前为空，把下一个tile挪到当前位置
                for (int j = i + 1; j < BoardSize; j++) {
                    if (board.tile(j, row) != null) {
                        Tile t = board.tile(j, row);
                        board.move(i, row, t);
                        changed = true;
                        break;
                    }
                }
            }
            //当前不为空，找到下一个不为空的位置，相同则merge，不同则break
            for (int j = i + 1; j < BoardSize; j++) {
                if (board.tile(j, row) != null) {
                    if (board.tile(j, row).value() == board.tile(i, row).value()) {
                        Tile t = board.tile(j, row);
                        board.move(i, row, t);
                        changed = true;
                        score += board.tile(i, row).value();
                    }
                    break;
                }
            }
        }
        return changed;
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        int BoardSize = board.size();
        switch (side) {
            case NORTH:
                for(int i =0; i < BoardSize; i++) {
                    boolean colChanged = oneColumnNorth(i);
                    if(colChanged) changed = true;
                }
                break;
            case SOUTH:
                for(int i =0; i < BoardSize; i++) {
                    boolean colChanged = oneColumnSouth(i);
                    if(colChanged) changed = true;
                }
                break;
            case WEST:
                for(int i =0; i < BoardSize; i++) {
                    boolean colChanged = oneRowWest(i);
                    if(colChanged) changed = true;
                }
                break;
            case EAST:
                for(int i =0; i < BoardSize; i++) {
                    boolean colChanged = oneRowEast(i);
                    if(colChanged) changed = true;
                }
                break;
        }

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Deal with only one row or column so to be called for many times
     */
    // TODO: Add a function here

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        int size_board = b.size();
        for(int i=0; i<size_board; i++)
        {
            for(int j=0; j<size_board; j++)
            {
                if(b.tile(i,j)==null)
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        // TODO: Fill in this function.
        int size_board = b.size();
        for(int i=0; i<size_board; i++)
        {
            for(int j=0; j<size_board; j++)
            {
                // 获取对应的tile的值
                if(b.tile(i,j)==null)
                    continue;
                else if(b.tile(i,j).value()==MAX_PIECE)
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        if(emptySpaceExists(b))
            return true;
        int size_board = b.size();
        //相邻相同的情况：行相同
        for(int i=0; i<size_board; i++)
        {
            for(int j=0; j<size_board-1; j++)
            {
                if(b.tile(i,j).value()==b.tile(i,j+1).value())
                    return true;
            }
        }
        //相邻相同的情况：列相同
        for(int i=0; i<size_board-1; i++)
        {
            for(int j=0; j<size_board; j++)
            {
                if(b.tile(i,j).value()==b.tile(i+1,j).value())
                    return true;
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
