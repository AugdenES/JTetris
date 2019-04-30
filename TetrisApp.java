import javax.swing.JFrame;
import javax.swing.JPanel;


public class TetrisApp extends JFrame{
    private GamePanel _gamePanel;
    
    public TetrisApp(){
     super("JTetris");
     this.setResizable(false);
     _gamePanel = new GamePanel(); 
     _gamePanel.setPreferredSize(new java.awt.Dimension(TetrisConstants.BLOCK_SIZE*TetrisConstants.BOARD_WIDTH+4, TetrisConstants.BLOCK_SIZE*TetrisConstants.BOARD_HEIGHT+24));
     this.setSize(TetrisConstants.BLOCK_SIZE*TetrisConstants.BOARD_WIDTH+4, TetrisConstants.BLOCK_SIZE*TetrisConstants.BOARD_HEIGHT+24);
     this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
     this.add(_gamePanel);  
     this.setVisible(true);
    }
    
    public static void main(String[] args){
        TetrisApp app = new TetrisApp();
    }
}