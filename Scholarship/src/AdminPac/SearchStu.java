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
 * �������������Ѱ��Ӧѧ���ģ�Ȼ�󷵻���Ѱ�����
 * @author U-anLA
 *
 */
public class SearchStu extends JPanel implements ActionListener{

	private JButton jbtDelete = new JButton("ɾ��");
	private JLabel jl1 = new JLabel("��ѡ��ѧ��ѧ�ţ�");
	private JComboBox jcbNmu;
	private JButton jbtSearch = new JButton("����");
	
	//�������ݿ����������
	private Connection conn;
	private ResultSet rs;
	private Statement stam;
	
	//����ѧ��ȫ�ֱ��������ڴ��ݸ���һ�����塣
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
	 * ���췽��
	 */
	public SearchStu(){
		initFrame();
		addListeners();
	}
	
	/**
	 * ������Ӹ����¼���
	 */
	private void addListeners() {
		jcbNmu.addActionListener(this);
		jbtSearch.addActionListener(this);
		jbtDelete.addActionListener(this);
	}

	/**
	 * ���ڳ�ʼ������塣
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
	 * ������Ӹ����¼���
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
			//���Ǽ�������Ĵ����¼���

			//���ţ���ѡ����string���ݹ�ȥ�����ң�new���µĴ��ڡ�

			//�����ķ��������У���Ϊ����Ұ�ǰһ�����ڹ��ˣ����Ͳ����ڼ������ˡ�
			this.jbtSearch.setEnabled(false);
			this.jcbNmu.setEnabled(false);
			this.jbtDelete.setEnabled(false);
			ChangeStuItem csi = new ChangeStuItem(this,SwingUtilities.getWindowAncestor(this),stuNum);
			
			if(csi.isVisible() == false){
				this.jbtSearch.setEnabled(true);
				this.jbtDelete.setEnabled(true);
			}

			
		}else if(e.getSource() == jbtDelete){
			
			if(JOptionPane.showConfirmDialog(this, "ȷ��Ҫɾ������ͬѧ", "ѯ��", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				
				String sql = "DELETE FROM stu_info WHERE stu_num=" + stuNum + ";";
				conn = ConnecFactory.getConnection();
				try {
					stam = conn.createStatement();
					int n = stam.executeUpdate(sql);
					if(n >= 1){
						JOptionPane.showMessageDialog(this, "ɾ���ɹ�");
						this.setItem(jcbNmu);
					}
				} catch (SQLException e1) {
					System.out.println("--------------���ݿ�����ʧ��--------------");
					e1.printStackTrace();
				} finally{
					ConnecFactory.closeConnection(conn, stam, rs);
				}
			}
			
		}
		
	}
	
	/**
	 * ÿ��ѧ����������һ�������б�����档
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
			System.out.println("----------------���ݿ�����ʧ��--------------");
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
