package a8;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

import javax.swing.JButton; 
import javax.swing.JLabel; 
import javax.swing.JPanel;


public class ConnectFourWidget  extends JPanel implements ActionListener, SpotListener {

	private enum Player {RED, BLACK};
	
	private JSpotBoard board;
	private JLabel message;
	private boolean gameOver;
	private Player currentPlayer;
	
	public ConnectFourWidget() {
		board = new JSpotBoard(7,6);
		message = new JLabel();
		
		setLayout(new BorderLayout());
		add(board, BorderLayout.CENTER);
		
		for(int i = 0 ; i < 7 ; i+=2) {
			for(int j = 0 ; j < 6 ; j++) {
				board.getSpotAt(i, j).setBackground(new Color(0.8f,0.8f,0.8f));
			}
		}
		for(int i = 1; i < 7 ; i+=2) {
			for(int j = 0 ; j < 6; j++) {
				board.getSpotAt(i, j).setBackground(new Color(0.5f,0.5f,0.5f));
			}
		}
		for(Spot s : board) {
			s.setSpotColor(Color.WHITE);
		}
		
		JPanel resetMessagePanel = new JPanel();
		resetMessagePanel.setLayout(new BorderLayout());
		
		/** Reset button, add ourselves as action listener**/
		JButton resetButton = new JButton ("Restart");
		resetButton.addActionListener(this);
		resetMessagePanel.add(resetButton, BorderLayout.EAST);
		resetMessagePanel.add(message, BorderLayout.CENTER);
		
		/** Add subpanel in the south area of layout**/
		add(resetMessagePanel, BorderLayout.SOUTH);
		
		board.addSpotListener(this);
		
		/** reset game **/
		resetGame();
	}
	
	private void resetGame() {
		for( Spot s: board) {
			s.clearSpot();
			s.setSpotColor(Color.WHITE);
		}
	
		
		message.setText("Welcome to Connect Four. Red to play first");
		
		gameOver = false;
		
		currentPlayer = Player.RED;
	}
	
	public void spotClicked(Spot spot) {
		if(gameOver) {
			return;
		}
		if(isColumnFull(spot.getSpotX())) {
			return ;
		}
		Color spotColor = null;
		String playerName = null;
		String nextPlayerName = null;
		
		if(currentPlayer == Player.RED) {
			spotColor = Color.RED;
			playerName = "Red";
			nextPlayerName = "Black";
			currentPlayer = Player.BLACK;
		}else {
			spotColor = Color.BLACK;
			playerName = "Black";
			nextPlayerName = "Red";
			currentPlayer=Player.RED;
		}
		
		int x = spot.getSpotX();
		
		for(int y = 5 ; y>=0 ; y--) {
			if(board.getSpotAt(x, y).isEmpty()) {
				board.getSpotAt(x, y).setSpot();
				board.getSpotAt(x, y).setSpotColor(spotColor);
				break;
			}
		}
		if(isGameWon(spotColor)) {
			message.setText(playerName + " has won");
			gameOver = true;
		}else if (isDraw()) {
			message.setText("The game is a draw.");
			gameOver = true;
		}else {
			message.setText(nextPlayerName + " to play");
		}
	}
	
	public void spotEntered(Spot s) {
		if(gameOver) {
			return;
		}
		int x = s.getSpotX();
		for(int y = 0 ; y < 6 ; y++) {
			if(board.getSpotAt(x, y).isEmpty()) {
				board.getSpotAt(x, y).highlightSpot();
			}
		}
	}
	
	public void spotExited(Spot spot) {
		int x = spot.getSpotX(); 
		
		for(int y=0; y<6; y++) {
		board.getSpotAt(x, y).unhighlightSpot(); }
	}
	
	public void actionPerformed(ActionEvent e) {
		resetGame(); 
	}
	
	private boolean isColumnFull(int x){
		for(int y=0; y<6; y++) {
		if(board.getSpotAt(x, y).isEmpty()) { 
			return false;
			} 
		}
		return true; 
		
	}
	
	
	private boolean isDraw() {
		for(Spot s : board) {
			
			if(s.isEmpty()) {
				return false;
		} 
				}
		return true; 
		
		}

	private boolean isGameWon(Color spotColor) {
		for(int x=0; x<7; x++) {
			for(int y=0; y<6; y++) { 
			if(isConnectFour(spotColor, x, y)) {
				return true;
			}
		}
		}
		return false;

	
	}
	
	private boolean isConnectFour(Color s, int x, int y) {
		int stk = 0;
		
		for(int i = 0 ; i <=3 ; i++) {
			if(y+3 >= 6) {
				break;
			}
			if(board.getSpotAt(x,  y+i).getSpotColor() == s) {
				stk ++;
			}else {
				stk = 0;
				break;
			}
		}
		if(stk == 4) {
			return true;
		}
		for(int i = 0 ; i <=3 ; i++) {
			if(x+3 >= 7) {
				break;
			}
			if(board.getSpotAt(x+ i,y).getSpotColor() == s) {
				stk ++;
			}else {
				stk = 0;
				break;
			}
		}
		if(stk == 4) {
			return true;
		}
		for(int i = 0 ; i <=3 ; i++) {
			if(x+3 >= 7 || y+3>=6) {
				break;
			}
			if(board.getSpotAt(x+i,y+i).getSpotColor() == s) {
				stk ++;
			}else {
				stk = 0;
				break;
			}
		}
		if(stk == 4) {
			return true;
		}
		for(int i = 0 ; i <=3 ; i++) {
			if(x-3<0 || y+3>=6) {
				break;
			}
			if(board.getSpotAt(x-i,y+i).getSpotColor() == s) {
				stk ++;
			}else {
				stk = 0;
				break;
			}
		}
		if(stk == 4) {
			return true;
		}
		return false;
		
		
		
		
		
		
		
	}
	
	
	
	
}