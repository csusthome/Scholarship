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
import javax.swing.JPanel;

import ConnectFactory.ConnecFactory;
import Item.TiyuItem;
/**
 * 这是体育项的jpanel，又来放体育项
 * @author U-anLA
 *
 */
public class TiyuPanel extends JPanel{
	private Connection conn;
	private Statement stam;
	private ResultSet rs;


	private ArrayList<TiyuItem> arrayTiyu = new ArrayList<TiyuItem>();
	private ArrayList<JCheckBox> arrayJcb = new ArrayList<JCheckBox>();

	private int length;
	

	public ArrayList<TiyuItem> getArrayTiyu() {
		return arrayTiyu;
	}

	public void setArrayTiyu(ArrayList<TiyuItem> arrayTiyu) {
		this.arrayTiyu = arrayTiyu;
	}

	public ArrayList<JCheckBox> getArrayJcb() {
		return arrayJcb;
	}

	public void setArrayJcb(ArrayList<JCheckBox> arrayJcb) {
		this.arrayJcb = arrayJcb;
	}

	public TiyuPanel() {
		getData();
		initJpanel();
		this.setPreferredSize(new Dimension(300 , (this.length + 1)*30));
	}

	private void initJpanel() {
		this.setLayout(null);
		int k = 0; // 用于计数
		for (int i = 0; i < arrayTiyu.size(); i++) {
			
			JCheckBox jcb = new JCheckBox();
			jcb.setText(arrayTiyu.get(i).getTiyu_name());
			
			jcb.setBounds(15 , 15 + 30 * k, 280, 30);
			
			arrayJcb.add(jcb);
			this.add(jcb);
			k++;

		}
		this.length = k;
	}

	private void getData() {
		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			String sql = "SELECT * FROM tiyuitem;";
			rs = stam.executeQuery(sql);

			while (rs.next()) {
				TiyuItem ti = new TiyuItem();
				ti.setTiyu_name(rs.getString("tiyu_name"));
				ti.setTiyu_num(rs.getString("tiyu_num"));
				ti.setTiyu_score(rs.getFloat("tiyu_score"));
				arrayTiyu.add(ti);
				System.out.println(rs.getString("tiyu_name"));
			}

		} catch (SQLException e) {
			System.out.println("--------数据库连接失败--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}

	}

}
