public abstract class Tetrimino 
{
    protected SmartRectangle _block1, _block2, _block3, _block4;
    protected int _x, _y;
    protected java.awt.Color _color;


    public Tetrimino (java.awt.Color color){
        _color = color;
        _block1 = new SmartRectangle(_color);
        _block1.setSize(TetrisConstants.BLOCK_SIZE,TetrisConstants.BLOCK_SIZE);
        _block2 = new SmartRectangle(_color);
        _block2.setSize(TetrisConstants.BLOCK_SIZE,TetrisConstants.BLOCK_SIZE);
        _block3 = new SmartRectangle(_color);
        _block3.setSize(TetrisConstants.BLOCK_SIZE,TetrisConstants.BLOCK_SIZE);
        _block4 = new SmartRectangle(_color);
        _block4.setSize(TetrisConstants.BLOCK_SIZE,TetrisConstants.BLOCK_SIZE);
        _block1.setBorderColor(java.awt.Color.WHITE);
        _block2.setBorderColor(java.awt.Color.WHITE);
        _block3.setBorderColor(java.awt.Color.WHITE);
        _block4.setBorderColor(java.awt.Color.WHITE);
    }
    public void draw(java.awt.Graphics2D aBrush)
    {
        _block1.draw(aBrush);
        _block2.draw(aBrush);
        _block3.draw(aBrush);
        _block4.draw(aBrush);
    }
    public void fill(java.awt.Graphics2D aBrush)
    {
        _block1.fill(aBrush);
        _block2.fill(aBrush);
        _block3.fill(aBrush);
        _block4.fill(aBrush);
    }
    
    public void setX(int x)
    {
        this._x = x;
    }
    public void setY(int y)
    {
        this._y = y;
    }
    public int getX(int i)
    {
        switch(i) {
            case 1: return (int) _block1.getX();
            case 2: return (int) _block2.getX();
            case 3: return (int) _block3.getX();
            case 4: return (int) _block4.getX();
        }
        return 0;
    }
    public int getY(int i)
    {
        switch(i) {
            case 1: return (int) _block1.getY();
            case 2: return (int) _block2.getY();
            case 3: return (int) _block3.getY();
            case 4: return (int) _block4.getY();
        }
        return 0;
    }
    public SmartRectangle getBlockAt(int i) {
        switch(i) {
            case 1: return _block1;
            case 2: return _block2;
            case 3: return _block3;
            case 4: return _block4;
        }
        return null;
    }
    
    /**
     * Sets the location of the composite Tetrimino object
     *
     * @param  x  the x-coordinate in the JPanel to which to move this object
     * @param  y  the y-coordinate in the JPanel to which to move this object
     * @return    Nothing
     */
    public abstract void setLocation(int x, int y);
    public boolean moveLeft()
    {
        int newX1, newY1, newX2, newY2, newX3, newY3, newX4, newY4;
        newX1 = (int)_block1.getX()-TetrisConstants.BLOCK_SIZE;
        newY1 = (int)_block1.getY();
        newX2 = (int)_block2.getX()-TetrisConstants.BLOCK_SIZE;
        newY2 = (int)_block2.getY();
        newX3 = (int)_block3.getX()-TetrisConstants.BLOCK_SIZE;
        newY3 = (int)_block3.getY();
        newX4 = (int)_block4.getX()-TetrisConstants.BLOCK_SIZE;
        newY4 = (int)_block4.getY();
                    
        _block1.setLocation(newX1, newY1);
        _block2.setLocation(newX2, newY2);
        _block3.setLocation(newX3, newY3);
        _block4.setLocation(newX4, newY4);
        return true;
    }
    public boolean moveRight()
    {
        int newX1, newY1, newX2, newY2, newX3, newY3, newX4, newY4;
        newX1 = (int)_block1.getX()+TetrisConstants.BLOCK_SIZE;
        newY1 = (int)_block1.getY();
        newX2 = (int)_block2.getX()+TetrisConstants.BLOCK_SIZE;
        newY2 = (int)_block2.getY();
        newX3 = (int)_block3.getX()+TetrisConstants.BLOCK_SIZE;
        newY3 = (int)_block3.getY();
        newX4 = (int)_block4.getX()+TetrisConstants.BLOCK_SIZE;
        newY4 = (int)_block4.getY();
        
        _block1.setLocation(newX1, newY1);
        _block2.setLocation(newX2, newY2);
        _block3.setLocation(newX3, newY3);
        _block4.setLocation(newX4, newY4);
        return true;
    }
    public boolean moveDown()
    {
        int newX1, newY1, newX2, newY2, newX3, newY3, newX4, newY4;
        newX1 = (int)_block1.getX();
        newY1 = (int)_block1.getY()+TetrisConstants.BLOCK_SIZE;
        newX2 = (int)_block2.getX();
        newY2 = (int)_block2.getY()+TetrisConstants.BLOCK_SIZE;
        newX3 = (int)_block3.getX();
        newY3 = (int)_block3.getY()+TetrisConstants.BLOCK_SIZE;
        newX4 = (int)_block4.getX();
        newY4 = (int)_block4.getY()+TetrisConstants.BLOCK_SIZE;

        _block1.setLocation(newX1, newY1);
        _block2.setLocation(newX2, newY2);
        _block3.setLocation(newX3, newY3);
        _block4.setLocation(newX4, newY4);
        return true;
    }
    /**
     * Attempts to move the Tetrimino object up on the game board. This is not a valid move in Tetris. 
     *
     * @return  boolean  Always returns false since this move is not allowed
     */
    public final boolean moveUp(){return false;}
    public boolean turnLeft()
    {
        int newX1, newY1, newX2, newY2, newX3, newY3, newX4, newY4;
        
        newX1 = (int)_block1.getX();
        newY1 = (int)_block1.getY();
        newX2 = (int)(newX1 + newY1 - _block2.getY());
        newY2 = (int)(newY1 - newX1 + _block2.getX());
        newX3 = (int)(newX1 + newY1 - _block3.getY());
        newY3 = (int)(newY1 - newX1 + _block3.getX());
        newX4 = (int)(newX1 + newY1 - _block4.getY());
        newY4 = (int)(newY1 - newX1 + _block4.getX());
        
        _block2.setLocation(newX2, newY2);
        _block3.setLocation(newX3, newY3);
        _block4.setLocation(newX4, newY4);
        return true;
    }
    /**
     * Attempts to rotate the Tetrimino object clockwise on the game board. This method checks the GamePanel
     *
     * @return  boolean  Always returns false since this move is not allowed
     */
    public boolean turnRight()
    {
        int newX1, newY1, newX2, newY2, newX3, newY3, newX4, newY4;
        
        newX1 = (int)_block1.getX();
        newY1 = (int)_block1.getY();
        newX2 = (int)(newX1 - newY1 + _block2.getY());
        newY2 = (int)(newY1 + newX1 - _block2.getX());
        newX3 = (int)(newX1 - newY1 + _block3.getY());
        newY3 = (int)(newY1 + newX1 - _block3.getX());
        newX4 = (int)(newX1 - newY1 + _block4.getY());
        newY4 = (int)(newY1 + newX1 - _block4.getX());
        
        _block2.setLocation(newX2, newY2);
        _block3.setLocation(newX3, newY3);
        _block4.setLocation(newX4, newY4);
        return true;
    }
}