import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.Color;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener
{
    private PieceProxy _piece;
    private Timer _timer, _timer2;
    private Random _generator;
    private GamePanel _gamePanel;
    
    private KeyUpListener _upKey;
    private KeyDownListener _downKey;
    private KeyLeftListener _leftKey;
    private KeyRightListener _rightKey;
    private KeyPListener _pauseKey;
    private KeySpaceListener _spaceKey;
    private SmartRectangle[][] _board = new SmartRectangle[TetrisConstants.BOARD_HEIGHT][TetrisConstants.BOARD_WIDTH];
    private boolean _gameOver = false;
    
    /**
     * Constructor for objects of class GamePanel
     */
    public GamePanel()
    {
        this.setBackground(Color.DARK_GRAY);
        this.setSize(new Dimension(TetrisConstants.BLOCK_SIZE*(TetrisConstants.BOARD_WIDTH), TetrisConstants.BLOCK_SIZE*(TetrisConstants.BOARD_HEIGHT)));
        this.setPreferredSize(new Dimension(TetrisConstants.BLOCK_SIZE*(TetrisConstants.BOARD_WIDTH), TetrisConstants.BLOCK_SIZE*(TetrisConstants.BOARD_HEIGHT)));

        _upKey = new KeyUpListener(this);
        _downKey = new KeyDownListener(this);
        _leftKey = new KeyLeftListener(this);
        _rightKey = new KeyRightListener(this);
        _pauseKey = new KeyPListener(this);
        _spaceKey = new KeySpaceListener(this);

        _generator = new Random();
        
        _piece = new PieceProxy();
        _piece.setPiece(tetriminoFactory());
        
        for (int x = 0; x < TetrisConstants.BOARD_HEIGHT; x++) {
            for (int y = 0; y < TetrisConstants.BOARD_WIDTH; y++) {
                _board[x][y] = null;
            }
        }
        
        _timer = new Timer(500, this);
        _timer.start();

    }
    
    public Tetrimino tetriminoFactory()
    /** 
     * This method implements the factory method design pattern to build new tetriminos during Tetris game play.
     */
    {
        Tetrimino newPiece;
        int randomNumber;
        
        int x = (TetrisConstants.BOARD_WIDTH/2)*TetrisConstants.BLOCK_SIZE+2;
        int y = 0;
        randomNumber = (int) (Math.floor(Math.random()*7)+1);
        switch(randomNumber) {
            case 1: newPiece = new Z(x,y);     break;
            case 2: newPiece = new S(x,y);     break;
            case 3: newPiece = new L(x,y);     break;
            case 4: newPiece = new J(x,y);     break;
            case 5: newPiece = new O(x,y);     break;
            case 6: newPiece = new I(x,y);     break;
            default: newPiece = new T(x,y);    break;
        }
        return newPiece;
    }
    
    public void paintComponent (java.awt.Graphics aBrush) 
    {
        super.paintComponent(aBrush);
        java.awt.Graphics2D betterBrush = (java.awt.Graphics2D)aBrush;
        
        _piece.fill(betterBrush);
        _piece.draw(betterBrush);
        for(SmartRectangle[] arr : _board) {
            for(SmartRectangle r : arr) {
                if(r != null) {
                    r.fill(betterBrush);
                    r.draw(betterBrush);
                }
            }
        }
    }
    /**
     * This method takes two integers representing the column and row of a cell on the game board a component rectangle into which a
     * tetrimino wishes to move. This can be prevented by either the cell being off of the game board (not a valid cell) or by the
     * cell being occupied by another SmartRectangle.
     * 
     * @param c The column of the cell in question on the game board.
     * @param r The row of the cell in question on the game board.
     * @return boolean This function returns whether the component rectangle can be moved into this cell.
     */
    public boolean canMove(int c, int r)
    {
        return isValid(c, r) && isFree(c, r);
    }
    
    /**
     * This method takes two integers representing the column and row of a cell on the game board a component rectangle into which a
     * tetrimino wishes to move. This method returns a boolean indicating whether the cell on the game board is empty.
     * 
     * @param c The column of the cell in question on the game board.
     * @param r The row of the cell in question on the game board.
     * @return boolean This function returns whether the cell on the game board is free.
     */    
    private boolean isFree(int c, int r)
    {
        return _board[r][c] == null;
    }
    /**
     * This method takes two integers representing the column and row of a cell on the game board a component rectangle into which a
     * tetrimino wishes to move. This function checks to see if the cell at (c, r) is a valid location on the game board.
     * 
     * @param c The column of the cell in question on the game board.
     * @param r The row of the cell in question on the game board.
     * @return boolean This function returns whether the location (c, r) is within the bounds of the game board.
     */
    private boolean isValid(int c, int r)
    {
        return r >= 0 && c >= 0 && r < TetrisConstants.BOARD_HEIGHT && c < TetrisConstants.BOARD_WIDTH;
    }
     /**
     * This method takes two integers representing the column and row of a cell on the game board a component rectangle into which a
     * tetrimino wishes to move. This can be prevented by either the cell being off of the game board (not a valid cell) or by the
     * cell being occupied by another SmartRectangle.
     * 
     * @param r The SmartRectangle to add to the game board.
     * @return Nothing
     */   
    public void addToBoard(SmartRectangle rect)
    {
        int c = (int) rect.getX()/TetrisConstants.BLOCK_SIZE;
        int r = (int) rect.getY()/TetrisConstants.BLOCK_SIZE;
        _board[r][c] = rect;
    }
    /**
     * This method takes one integer representing the row of cells on the game board to move down on the screen after a full 
     * row of squares has been removed.
     * 
     * @param row The row in question on the game board.
     * @return Nothing
     */
    private void moveBlocksDown(int row)
    {
        for (int c=0; c<TetrisConstants.BOARD_WIDTH; c++) {
            
            SmartRectangle newRect = _board[row-1][c];
            _board[row][c] = null;
            _board[row-1][c] = null;
            
            if(newRect != null) {
                newRect.setLocation(newRect.getX(), newRect.getY()+TetrisConstants.BLOCK_SIZE);
            }
            
            _board[row][c] = newRect;
            
            
        }
        
    }
        
    /**
     * This method checks each row of the game board to see if it is full of rectangles and should be removed. It calls
     * moveBlocksDown to adjust the game board after the removal of a row.
     * 
     * @return Nothing
     */
    private void checkRows(){
        
        for(int r = TetrisConstants.BOARD_HEIGHT-1; r >= 0; r--) {
            
            int checksum = 0;
            for(int c = 0; c < TetrisConstants.BOARD_WIDTH; c++) {
                
                if(_board[r][c] != null) {
                    checksum++;
                }

            }
            if(checksum == TetrisConstants.BOARD_WIDTH) {
                for(int row = r; row > 0; row--) {
                    moveBlocksDown(row);
                }
                r++;
            }
            
        }
                
    }
    /**
     * This method checks to see if the game has ended.
     * 
     * @return boolean This function returns whether the game is over or not.
     */
    private boolean checkEndGame()
    {
        for(int i = 0; i < TetrisConstants.BOARD_WIDTH; i++) {
            if(_board[0][i] != null) {
                _gameOver = true;
                break;
            }
        }
        return _gameOver;
        
        
        
    }
    /**
     * Checks a list of coordinates (px) and tests if the corresponding cell is valid
     * If all coordinates pass, return true. Otherwise return false
     */
    public boolean isTrue(int direction) {
        for(int i = 1; i <= 4; i++)
        {
            int x = (int)Math.round(_piece.getX(i) / TetrisConstants.BLOCK_SIZE);
            int y = (int)Math.round(_piece.getY(i) / TetrisConstants.BLOCK_SIZE);
            switch(direction)
            {
                case 0: // if left side
                    x--;
                    break;
                case 1: // if right side
                    x++;
                    break;
                case 2: // if down side
                    y++;
                    break;
            }          
            if(!canMove(x, y))
            {
                return false;
            }
        }

        return true;
    }
    private void showEndGame() {
        
        JOptionPane.showMessageDialog(null, "Game over.");
        _timer.stop();
        _upKey.setEnabled(false);
        _downKey.setEnabled(false);
        _leftKey.setEnabled(false);
        _rightKey.setEnabled(false);
        _pauseKey.setEnabled(false);
        _spaceKey.setEnabled(false);
        TetrisApp app = new TetrisApp();
    }
    
    public void actionPerformed(ActionEvent e)
    {
        doMoveDown();
    }
    
    
    public boolean canMoveCheck(int horizontal, int vertical) {
        return this.canMove((this._piece.getX(1)/TetrisConstants.BLOCK_SIZE)+horizontal, (this._piece.getY(1)/TetrisConstants.BLOCK_SIZE)+vertical) &&
                this.canMove((this._piece.getX(2)/TetrisConstants.BLOCK_SIZE)+horizontal, (this._piece.getY(2)/TetrisConstants.BLOCK_SIZE)+vertical) &&
                this.canMove((this._piece.getX(3)/TetrisConstants.BLOCK_SIZE)+horizontal, (this._piece.getY(3)/TetrisConstants.BLOCK_SIZE)+vertical) &&
                this.canMove((this._piece.getX(4)/TetrisConstants.BLOCK_SIZE)+horizontal, (this._piece.getY(4)/TetrisConstants.BLOCK_SIZE)+vertical);
    }
    
    public void doMoveDown() {
        if(canMoveCheck(0, 1)) {
            this._piece.moveDown();
            repaint();
        } else {
            for(int i=1; i<=4; i++) {
                addToBoard(_piece.getBlockAt(i));
            }
            checkRows();
            if(checkEndGame()) {
                showEndGame();
            } else {
                Tetrimino rand = tetriminoFactory();
                _piece.setPiece(rand);
                repaint();
            }
        }
    }
    
    private class KeyUpListener extends KeyInteractor 
    {
        public KeyUpListener(JPanel p)
        {
            super(p,KeyEvent.VK_UP);
        }
        
        public  void actionPerformed (ActionEvent e) {
            if (_timer.isRunning()){
                _piece.turnRight(); // Try to turn right
                if(isTrue(10))
                    repaint(); // if valid then repaint
                else{
                    _piece.turnLeft(); // If not valid then return back to the same piece
                    repaint(); // repaint
                    }
                }
            }
    }
    private class KeyDownListener extends KeyInteractor 
    {
        public KeyDownListener(JPanel p)
        {
            super(p,KeyEvent.VK_DOWN);
        }
        
        public  void actionPerformed (ActionEvent e) {
            doMoveDown();
        }
    } 
    private class KeyLeftListener extends KeyInteractor 
    {
        public KeyLeftListener(JPanel p)
        {
            super(p,KeyEvent.VK_LEFT);
        }
        
        public  void actionPerformed (ActionEvent e) {
            if(canMoveCheck(-1, 0)) {
                _piece.moveLeft();
                repaint();
            }
        }
    } 
    private class KeyRightListener extends KeyInteractor 
    {
        public KeyRightListener(JPanel p)
        {
            super(p,KeyEvent.VK_RIGHT);
        }
        
        public  void actionPerformed (ActionEvent e) {
            if(canMoveCheck(1, 0)) {
                _piece.moveRight();
                repaint();
            }
        }
    }
    private class KeyPListener extends KeyInteractor 
    {
        public KeyPListener(JPanel p)
        {
            super(p,KeyEvent.VK_P);
        }
        
        public  void actionPerformed (ActionEvent e) {
            if(_timer.isRunning()){
                _timer.stop();
            }
            else
                _timer.start();
        }
    }
    private class KeySpaceListener extends KeyInteractor 
    {
        public KeySpaceListener(JPanel p)
        {
            super(p,KeyEvent.VK_SPACE);
        }
        
        public  void actionPerformed (ActionEvent e) {
            while(canMoveCheck(0, 1)) {
                _piece.moveDown();
            }
            for(int i=1; i<=4; i++) {
                addToBoard(_piece.getBlockAt(i));
            }
            checkRows();
            if(checkEndGame()) {
                showEndGame();
            } else {
                Tetrimino rand = tetriminoFactory();
                _piece.setPiece(rand);
                repaint();
            }
        }
    }
}
