package AdminPac.AddStudentRecPanel;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ConnectFactory.ConnecFactory;
import Item.DeyuItem;
/**
 * 这是德育项的jpanel，用来放德育项
 * @author U-anLA
 *
 */
public class DeyuPanel extends JPanel{
	
	//定义数据库连接变量。
	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	//定义输入框和items数组。
	private ArrayList<DeyuItem> arrayDeyu = new ArrayList<DeyuItem>();
	private ArrayList<JCheckBox> arrayJcb = new ArrayList<JCheckBox>();
	

	//定义显示子窗口长度变量
	private int length;


	public ArrayList<DeyuItem> getArrayDeyu() {
		return arrayDeyu;
	}

	public void setArrayDeyu(ArrayList<DeyuItem> arrayDeyu) {
		this.arrayDeyu = arrayDeyu;
	}

	public ArrayList<JCheckBox> getArrayJcb() {
		return arrayJcb;
	}

	public void setArrayJcb(ArrayList<JCheckBox> arrayJcb) {
		this.arrayJcb = arrayJcb;
	}


	/**
	 * 初始化构造德育窗体。
	 */
	public DeyuPanel() {
		
		getData();
		initJpanel();
		this.setPreferredSize(new Dimension(300 , (this.length + 1)*30));
	}

	/**
	 * 初始化窗体。
	 */
	private void initJpanel() {
		
		this.setLayout(null);
		int k = 0; // 用于计数
		for (int i = 0; i < arrayDeyu.size(); i++) {

			JCheckBox jcb = new JCheckBox();
			jcb.setText(arrayDeyu.get(i).getDeyu_name());
			
			jcb.setBounds(15 , 15 + 30 * k, 280, 30);
			
			
			arrayJcb.add(jcb);
			this.add(jcb);

			k++;

		}
		this.length = k;
	}

	/**
	 * 获得数据，也就是获得德育项的选项，然后显示出来。
	 */
	private void getData() {
		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			String sql = "SELECT * FROM deyuitem;";
			rs = stam.executeQuery(sql);

			while (rs.next()) {
				DeyuItem di = new DeyuItem();
				di.setDeyu_name(rs.getString("deyu_name"));
				di.setDeyu_num(rs.getString("deyu_num"));
				di.setDeyu_score(rs.getFloat("deyu_score"));
				arrayDeyu.add(di);
				System.out.println(rs.getString("deyu_name"));
			}

		} catch (SQLException e) {
			System.out.println("--------数据库连接失败--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}

	}

}
