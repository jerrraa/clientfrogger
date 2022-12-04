
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelData extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JButton upload, display, restart, quit;
	private int score;
	private FroggerClient game;
	private Database database;
	private JFrame dataframe = new JFrame("YOU WIN");    
	public PanelData() {
		this.score = 0;
	}
	public void GeneratePanel() {
		upload = new JButton("Upload Score");
		display = new JButton("Show Score");
		restart = new JButton("Restart");
		quit = new JButton("Quit"); 
	        JPanel panel =new JPanel();  
	        	panel.setBounds(0,0,250,250);    
	        	panel.setBackground(Color.gray);  
	        	dataframe.add(panel);  
	        	dataframe.setSize(250,250);   
	        	dataframe.setLayout(null);    
	        	dataframe.setLocationRelativeTo(null);  
	        	dataframe.setVisible(true);    
	        	panel.add(display);
	        	panel.add(upload);
	        	panel.add(restart);
	        	panel.add(quit);
	        upload.setFocusable(false);
	        upload.addActionListener(this);
	        display.setFocusable(false);
	        display.addActionListener(this);
	        restart.setFocusable(false);
	        restart.addActionListener(this);
	        quit.setFocusable(false);
	        quit.addActionListener(this);
	}  
	public void ResetFunction(FroggerClient game) {
		this.game = game;
	}
	public void SetNewScore(int userscore) {
		this.score = userscore;
	}
	public int GetNewScore() {
		return score;
	}
	private void DisplayUserData(ResultSet rs) throws SQLException {
		  JFrame dataframe = new JFrame("USER DATA");    
	        JPanel panel =new JPanel();  
	        JTable table = new JTable();
	        DefaultTableModel model = new DefaultTableModel();
	        String[] column = {"ID", "NAME", "SCORE"};
	        model.setColumnIdentifiers(column);
	        table.setModel(model);
	        panel.setBounds(0,0,300,800);    
	        panel.setBackground(Color.white);  
		 while ( rs.next() ) 
		 {
			String id = rs.getString("id");
			String name = rs.getString("name");
			String score = rs.getString("score");
			model.addRow(new Object[]{id, name, score});
		 }
		 	panel.add(table);
	        dataframe.add(panel);  
	        dataframe.setSize(300,800);   
	        dataframe.setLayout(null);    
	        dataframe.setLocationRelativeTo(null);  
	        dataframe.setVisible(true);    
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == upload) {
			String username = JOptionPane.showInputDialog("Please insert your name!");
			if (username.isEmpty()) {
				System.out.println("invalid input");
			} else {
				//upload user & score to database
				database = new Database();
				JOptionPane.showMessageDialog(null, "Uploaded!");
				database.UploadDatabase(username, this.GetNewScore());
			}
		}
		if (e.getSource() == display) {   
			Connection conn = null;
			Statement stmt = null;
			try {
				Class.forName("org.sqlite.JDBC");
				String dbURL = "jdbc:sqlite:score.db";
				conn = DriverManager.getConnection(dbURL);
				if (conn!=null) {
					conn.setAutoCommit(false);
					stmt = conn.createStatement();
					String sql = "SELECT * FROM userscore";
					ResultSet rs = stmt.executeQuery(sql);
					DisplayUserData(rs);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource() == restart) {
			//game.Restartgame();
			dataframe.dispose();
		}
		if (e.getSource() == quit) {
			this.game.setVisible(false);
			this.game.dispose();
			dataframe.dispose();
		}
		
	}
}
