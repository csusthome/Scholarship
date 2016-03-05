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
 * ���ǵ������jpanel�������ŵ�����
 * @author U-anLA
 *
 */
public class DeyuPanel extends JPanel{
	
	//�������ݿ����ӱ�����
	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	//����������items���顣
	private ArrayList<DeyuItem> arrayDeyu = new ArrayList<DeyuItem>();
	private ArrayList<JCheckBox> arrayJcb = new ArrayList<JCheckBox>();
	

	//������ʾ�Ӵ��ڳ��ȱ���
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
	 * ��ʼ������������塣
	 */
	public DeyuPanel() {
		
		getData();
		initJpanel();
		this.setPreferredSize(new Dimension(300 , (this.length + 1)*30));
	}

	/**
	 * ��ʼ�����塣
	 */
	private void initJpanel() {
		
		this.setLayout(null);
		int k = 0; // ���ڼ���
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
	 * ������ݣ�Ҳ���ǻ�õ������ѡ�Ȼ����ʾ������
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
			System.out.println("--------���ݿ�����ʧ��--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}

	}

}
