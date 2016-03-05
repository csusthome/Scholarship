package AdminPac.AddStudentRecPanel;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ConnectFactory.ConnecFactory;
import Item.ZhiyuItem;
/**
 * 这是智育项的panel，用于存放智育项。
 * @author U-anLA
 *
 */
public class ZhiyuPanel extends JPanel {

	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	private ArrayList<ZhiyuItem> arrayZhi = new ArrayList<ZhiyuItem>();
	private ArrayList<JTextField> arrayJtf = new ArrayList<JTextField>();

	private int length;
	
	public ArrayList<JTextField> getArrayJtf() {
		return arrayJtf;
	}

	public void setArrayJtf(ArrayList<JTextField> arrayJtf) {
		this.arrayJtf = arrayJtf;
	}

	public ArrayList<ZhiyuItem> getArrayZhi() {
		return arrayZhi;
	}

	public void setArrayZhi(ArrayList<ZhiyuItem> arrayZhi) {
		this.arrayZhi = arrayZhi;
	}

	public ZhiyuPanel() {
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
		for (int i = 0; i < arrayZhi.size(); i++) {

			JLabel jl = new JLabel(arrayZhi.get(i).getSubj_name());
			JTextField jtf = new JTextField();

			jl.setBounds(15, 15 + 30 * k, 205, 30);
			jtf.setBounds(220, 15 + 30 * k, 40, 30);

			arrayJtf.add(jtf);
			
			this.add(jl);
			this.add(jtf);
			
			
			
			k++;

		}
		this.length = k;
	}

	/**
	 * 获得数据。
	 */
	private void getData() {
		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			String sql = "SELECT * FROM zhiyuitem;";
			rs = stam.executeQuery(sql);

			while (rs.next()) {
				ZhiyuItem zi = new ZhiyuItem();
				zi.setSubj_name(rs.getString("subj_name"));
				zi.setSubj_num(rs.getString("subj_num"));
				zi.setSubj_score(rs.getFloat("subj_score"));
				zi.setTarget(rs.getInt("target"));
				arrayZhi.add(zi);
				System.out.println(rs.getString("subj_name"));

			}

		} catch (SQLException e) {
			System.out.println("--------数据库连接失败--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}

	}

}
