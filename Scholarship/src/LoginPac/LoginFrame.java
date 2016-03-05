package LoginPac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import AdminPac.AdminFrame;
import ConnectFactory.ConnecFactory;

/**
 * ������ϵͳ�ĵ�½���棬��Ҫ���� ����Ա��½����ͨ��Ա��½ �Լ��Ƿ��ס���룬�Ƿ��Զ���½������
 * 
 * @author U-anLA
 * 
 */
public class LoginFrame extends JFrame implements ActionListener {
	// �������ݿ���ر���
	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	private JLabel jl1, jl2, jl3;
	// �û��������
	private JTextField jtf;
	// ���������
	private JPasswordField jpf;
	// ��½��ť��������������
	private JButton jb = new JButton("��½");
	private JCheckBox[] jcb = { new JCheckBox("�Զ���¼"), new JCheckBox("��ס����") };

	private ButtonGroup bg = new ButtonGroup();
	// ����Ա���ڽ���
	private AdminFrame adminFrame;
	// ��ͨ�û����洰��

	// �������û��ĵ�¼��
	private String loginName;
	// �������û��ĵ�½����
	private String loginPwd;
	private boolean isLogin = false;
	//��userName��Ҳ���ǵ�¼�˴��ݹ�ȥ��
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * LoginFrame��½����Ĺ��췽��
	 */
	public LoginFrame() {

		initPanel();
		addActionLisener();

	}

	public JTextField getJtf() {
		return jtf;
	}

	public void setJtf(JTextField jtf) {
		this.jtf = jtf;
	}

	public JPasswordField getJpf() {
		return jpf;
	}

	public void setJpf(JPasswordField jpf) {
		this.jpf = jpf;
	}

	public JCheckBox[] getJcb() {
		return jcb;
	}

	public void setJcb(JCheckBox[] jcb) {
		this.jcb = jcb;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * ������Ӽ����¼���
	 * �����еĿؼ�����ӽ�ȥ��
	 */
	private void addActionLisener() {
		jpf.addActionListener(this);
		jb.addActionListener(this);

	}

	/**
	 * ��½���ڵĳ�ʼ�����洰�ڣ� �����봰�ڽ�����صĲ������������������
	 */
	private void initPanel() {

		//��½ʱ�Ľ���ͼƬ��
		ImageIcon loginIcon = new ImageIcon("file/login.jpg");

		//��������Ϊ�ա�
		this.setLayout(null);
		this.setTitle("��ѧ������ϵͳ");
		
		
		
		//���jlabel����ͼƬǶ���ȥ��
		jl1 = new JLabel(loginIcon);
		jl1.setBounds(0, 0, 400, 150);
		this.add(jl1);
		//��ӵ�¼��
		jl2 = new JLabel("��¼����");
		jl2.setBounds(30, 160, 60, 30);
		this.add(jl2);
		
		jl3 = new JLabel("    ���룺");
		jl3.setBounds(30, 180, 60, 30);
		this.add(jl3);

		jtf = new JTextField();
		jtf.setBounds(100, 165, 140, 20);
		this.add(jtf);

		jpf = new JPasswordField();
		jpf.setBounds(100, 190, 140, 20);
		this.add(jpf);

		jb.setBounds(290, 175, 80, 30);
		this.add(jb);

		for (int i = 0; i < 2; i++) {
			jcb[i].setBounds(100 + i * 100, 220, 80, 30);
			jcb[i].setSelected(false);
			this.add(jcb[i]);
		}
		
		//����Щ���á�
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(400, 300, 400, 300);

	}

	/**
	 * ���¼�����д�ķ�������Ҫ��jtf�ĺ�jbt��action����
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String putName = jtf.getText().trim(); // �õ�������
		String putPwd = jpf.getText().trim(); // �õ���������
		String getPwd = new String(); // �����洢Ҫ��õ���Ӧ������
		int isRemenber = 0;
		int isAutoLogin = 0;
		// �ж��¼�Դ����һ��
		if (e.getSource() == jb || e.getSource() == jpf) {
			/* ��½��ť */
			if (putName.equals("")) { // û�������û���
				JOptionPane.showMessageDialog(this, "�������¼��������", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (putPwd.equals("")) { // û�������û���
				JOptionPane.showMessageDialog(this, "�������¼��������", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			// �ж��Ƿ��Զ���½
			if (jcb[0].isSelected()) {
				isAutoLogin = 1;
			}
			// �ж��Ƿ��ס����
			if (jcb[1].isSelected()) {
				isRemenber = 1;
			}

			try {
				conn = ConnecFactory.getConnection(); // ���þ�̬�����õ�����
				stam = conn.createStatement(); // �õ�statment
				// ����һ���������������select����еõ�AdminPwd

				String sql = "SELECT user_pwd FROM user_form WHERE user_name = '"
						+ putName + "'"; // ƴ��sql���
				System.out.println(sql);
				rs = stam.executeQuery(sql);
				while (rs.next()) {
					getPwd = rs.getString("user_pwd"); // �õ�adminPwd
				}

				if (putPwd.equals(getPwd)) {
					/* ��½�ɹ�����ʾ����Ա������ */

					String sqlUpdate = "UPDATE loginInfo SET loginName = '"
							+ jtf.getText().trim() + "',loginPwd = '"
							+ jpf.getText().trim() + "',isRemenber = "
							+ isRemenber + ",isAutoLogin = " + isAutoLogin
							+ " WHERE id = 1"; // ע�⣬����whereǰ��Ҫ����һ���ո񣡣�����
					// ���ü�ر��������Ƿ��Ѿ��ı��˵�һ��Ҳ���Ǵ��ֵ
					int n3 = stam.executeUpdate(sqlUpdate);
					if (n3 == 0) {
						JOptionPane.showMessageDialog(this, "���ݿ����");
						//this.setVisible(false);
						System.exit(0);
					}
					// ��������������������ݿ���Ѱ��
					Thread.sleep(1000);

					// �����½�ɹ���������ʾ���
					JOptionPane.showMessageDialog(this, "��ӭ:" + putName, "��ӭ",
							JOptionPane.YES_NO_CANCEL_OPTION);
					// �����ֵõ�
					// ������½������Ϊ���ɼ�
					this.setVisible(false);
					// new������Ա���壬������Ϊ�ɼ�
					adminFrame = new AdminFrame(this.jtf.getText().trim());

				} else {
					// �����½ʧ�ܣ�����ʾ����
					JOptionPane.showMessageDialog(this, "������û��������������", "����",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("-----------��½����-----------");
			} catch (InterruptedException e2) {

				e2.printStackTrace();
			}

		}
	}

	/***
	 * ���������ڵ�½ǰ�ļ�⣬���ǿ��Ƿ��Զ���½���Ƿ��Ǽ�ס����
	 * 
	 * 
	 */
	public void check() {

		// ������int���͵�ֵ������boolֵ������¼�Ƿ�Ϊ����Ա���Ƿ��ס���룬�Ƿ��Զ���½
		int isWho = 0;
		int isRemenber = 0;
		int isAutoLogin = 0;
		// �õ����ݿ�����
		conn = ConnecFactory.getConnection();
		try {
			stam = conn.createStatement();
			// ƴװsql���
			String sql = "SELECT * FROM loginInfo;";
			// ִ��sql���
			rs = stam.executeQuery(sql);
			while (rs.next()) {
				// �ֱ�õ��������Ե�ֵ
				int id = rs.getInt("id");
				loginName = rs.getString("loginName");
				loginPwd = rs.getString("loginPwd");
				isRemenber = rs.getInt("isRemenber");
				isAutoLogin = rs.getInt("isAutoLogin");
			}
		} catch (SQLException e) {
			System.out.println("-----------���ݿ����ӳ���---------");
			e.printStackTrace();
		}

		if (isRemenber == 0) {
			// ���û��ѡ���ס���룬��ɶҲ��������ֱ��Ĭ��ѡ�ϡ�����Ա��
		} else if (isRemenber == 1) {
			// ����Ѿ�ѡ���˼�ס���룬������һ��һ������
			// ���Ƚ��ô�Ĺ�����д���ֹ����
			jtf.setText(loginName);
			jpf.setText(loginPwd);

			jcb[1].setSelected(true);
			/**
			 * ��һ�������Ƿ�ѡ���Զ���½�������ѡ�ˣ� �͵�½
			 */
			if (isAutoLogin == 1) {
				/**
				 * �˴�Ϊ�Զ���½��ť�������ѡ�˹���Ա ����ù���Ա�Զ���½�ķ����� ���򣬵�����ͨ��Ա�Զ���½�ķ���
				 */
				
					// ����Ա�Զ���½�¼�
					/**
					 * ����ߵ��������˵����һ�εĶ��ˣ����ԾͲ����ٵ������ݿ�������sql�����
					 */
					// ͬ������Ϊ���ɱ༭
					jtf.setEditable(false);
					jpf.setEditable(false);
					jb.setEnabled(false);
					//���ҽ��Զ���½����
					jcb[0].setSelected(true);
					jcb[1].setEnabled(false);
					// �õ��û�����������һ������
					userName = jtf.getText();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
					//����ڵ�½��ʱ����׶Σ��û����Զ���½ȡ���ˣ��ǾͲ����½�ˣ��ͻ�����ȥ��
					if(!jcb[0].isSelected()){
						// ͬ������Ϊ���α༭
						jtf.setEditable(true);
						jpf.setEditable(true);
						jb.setEnabled(true);
						jcb[1].setEnabled(true);
						return;
					}
					// �����½�ɹ��������ʾ���
					JOptionPane.showMessageDialog(this, "��ӭʹ��", "��ӭ",
							JOptionPane.INFORMATION_MESSAGE);
					
					
					
					// ������������Ϊ���ɼ�
					this.setVisible(false);
					// new������Ա����
					adminFrame = new AdminFrame(this.jtf.getText().trim());

				
			}
		}

	}

	/**
	 * ���������Ŀ��main������������������
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LoginFrame lp = new LoginFrame();
		//�ȵ��ü�飬��Ԥѡ��ӽ�ȥ��
		lp.check();


	}

}
