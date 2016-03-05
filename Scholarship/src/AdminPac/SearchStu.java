package AdminPac;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ConnectFactory.ConnecFactory;

/**
 * 这个类是用来搜寻相应学生的，然后返回搜寻结果。
 * @author U-anLA
 *
 */
public class SearchStu extends JPanel implements ActionListener{

	private JButton jbtDelete = new JButton("删除");
	private JLabel jl1 = new JLabel("请选择学生学号：");
	private JComboBox jcbNmu;
	private JButton jbtSearch = new JButton("检索");
	
	//定义数据库操作变量。
	private Connection conn;
	private ResultSet rs;
	private Statement stam;
	
	//定义学号全局变量，用于传递给下一个窗体。
	private String stuNum;
	
	
	
	
	
	public JButton getJbtDelete() {
		return jbtDelete;
	}

	public void setJbtDelete(JButton jbtDelete) {
		this.jbtDelete = jbtDelete;
	}

	public JComboBox getJcbNmu() {
		return jcbNmu;
	}

	public void setJcbNmu(JComboBox jcbNmu) {
		this.jcbNmu = jcbNmu;
	}

	public JButton getJbtSearch() {
		return jbtSearch;
	}

	public void setJbtSearch(JButton jbtSearch) {
		this.jbtSearch = jbtSearch;
	}

	/**
	 * 构造方法
	 */
	public SearchStu(){
		initFrame();
		addListeners();
	}
	
	/**
	 * 用于添加各项事件。
	 */
	private void addListeners() {
		jcbNmu.addActionListener(this);
		jbtSearch.addActionListener(this);
		jbtDelete.addActionListener(this);
	}

	/**
	 * 用于初始化各项窗体。
	 */
	private void initFrame() {
		this.setLayout(null);
		jl1.setBounds(30, 30, 150, 30);
		this.add(jl1);
		
		jcbNmu = new JComboBox();
		this.setItem(jcbNmu);
		jcbNmu.setBounds(160, 30, 250, 30);
		this.add(jcbNmu);
		
		jbtSearch.setBounds(430, 30, 100, 30);
		this.add(jbtSearch);
		
		jbtDelete.setBounds(555, 30, 100, 30);
		this.add(jbtDelete);
	}

	/**
	 * 用于添加各项事件。
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		StringBuffer str = new StringBuffer();
		if(jcbNmu.getSelectedItem() == null){
			return;
		}
		str.append(jcbNmu.getSelectedItem().toString().trim());
		stuNum = str.substring(str.length() - 13, str.length() - 1);
		if(e.getSource() == jbtSearch){
			//这是检索处理的处理事件。

			//接着，把选到的string传递过去，并且，new出新的窗口。

			//这样的方法，不行，因为如果我把前一个窗口关了，但就不能在继续按了。
			this.jbtSearch.setEnabled(false);
			this.jcbNmu.setEnabled(false);
			this.jbtDelete.setEnabled(false);
			ChangeStuItem csi = new ChangeStuItem(this,SwingUtilities.getWindowAncestor(this),stuNum);
			
			if(csi.isVisible() == false){
				this.jbtSearch.setEnabled(true);
				this.jbtDelete.setEnabled(true);
			}

			
		}else if(e.getSource() == jbtDelete){
			
			if(JOptionPane.showConfirmDialog(this, "确认要删除这名同学", "询问", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				
				String sql = "DELETE FROM stu_info WHERE stu_num=" + stuNum + ";";
				conn = ConnecFactory.getConnection();
				try {
					stam = conn.createStatement();
					int n = stam.executeUpdate(sql);
					if(n >= 1){
						JOptionPane.showMessageDialog(this, "删除成功");
						this.setItem(jcbNmu);
					}
				} catch (SQLException e1) {
					System.out.println("--------------数据库连接失败--------------");
					e1.printStackTrace();
				} finally{
					ConnecFactory.closeConnection(conn, stam, rs);
				}
			}
			
		}
		
	}
	
	/**
	 * 每个学生都设置在一个下拉列表框里面。
	 * @param jcb
	 */
	private void setItem(JComboBox jcb){
		jcb.removeAllItems();
		String sql = "SELECT * FROM stu_info;";
		
		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			rs = stam.executeQuery(sql);
			while(rs.next()){
				String name = rs.getString("stu_name");
				String num = rs.getString("stu_num");
				String item = name + "(" + num + ")";
				jcb.addItem(item);
			}
		} catch (SQLException e) {
			System.out.println("----------------数据库连接失败--------------");
			e.printStackTrace();
		} finally{
			ConnecFactory.closeConnection(conn, stam, rs);
		}
	}
	public static void main(String[] args){
		JFrame jf = new JFrame();
		SearchStu cs = new SearchStu();
		jf.setLocation(100, 100);
		jf.setSize(600,600);
		jf.add(cs);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}

}
