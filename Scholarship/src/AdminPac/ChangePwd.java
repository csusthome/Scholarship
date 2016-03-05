package AdminPac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import ConnectFactory.ConnecFactory;

public class ChangePwd extends JPanel implements ActionListener {

	//�������ݿ������ر���
	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	private JLabel[] jlArray = { new JLabel("�û���"), new JLabel("ԭʼ����"),
			new JLabel("������"), new JLabel("ȷ��������") };
	//�����û���
	private JTextField tfName = new JTextField();
	//�ֱ������������ԭ���룬�����룬��������롣
	private JPasswordField[] jpfArray = { new JPasswordField(),
			new JPasswordField(), new JPasswordField() };
	//�������ú�
	private JButton[] jbArray = { new JButton("ȷ��"), new JButton("����") };

	/**
	 * ���췽����
	 */
	public ChangePwd() {
		initFrame();
		addListener();
	}

	/**
	 * ��Ӹ��ֿؼ����¼���
	 */
	private void addListener() {
		tfName.addActionListener(this);
		for (int i = 0; i < 3; i++) {
			jpfArray[i].addActionListener(this);
		}
		for (int i = 0; i < 2; i++) {
			jbArray[i].addActionListener(this);
		}
	}

	/**
	 * ��ʼ���������
	 */
	private void initFrame() {
		//�������趨Ϊ�ա�
		this.setLayout(null);
		for (int i = 0; i < jlArray.length; i++) {
			jlArray[i].setBounds(30, 20 + 50 * i, 150, 30);
			this.add(jlArray[i]);
			if (i == 0) {
				tfName.setBounds(130, 20 + 50 * i, 150, 30);
				this.add(tfName);
			} else {
				jpfArray[i - 1].setBounds(130, 20 + 50 * i, 150, 30);
				this.add(jpfArray[i - 1]);
			}
		}
		jbArray[0].setBounds(40, 230, 100, 30);
		this.add(jbArray[0]); // ���ȷ�ϰ�ť
		jbArray[1].setBounds(170, 230, 100, 30);
		this.add(jbArray[1]); // ������ð�ť
	}

	public void actionPerformed(ActionEvent e) {
		String patternStr = "[0-9a-zA-Z]{5,12}"; // �ж������ʽ����ȷ�ԣ�0~9��a~z��A~Z
		String userName = tfName.getText().trim();


		/*
		 * ��������ֱ�﷽ʽ�����Եõ����� ���ҿ�����������ʽ���жϡ�
		 */
		String userPwd = jpfArray[0].getText().trim();
		String newUserPwd = jpfArray[1].getText().trim();
		String newUserPwd1 = jpfArray[2].getText().trim();

		//���س���һ�����»�ý��㡣
		if (e.getSource() == tfName) {
			jpfArray[0].requestFocus(true);
		} else if (e.getSource() == jpfArray[0]) {
			jpfArray[1].requestFocus();
		} else if (e.getSource() == jpfArray[1]) {
			jpfArray[2].requestFocus();
		} else if (e.getSource() == jpfArray[2]) {
			jbArray[0].requestFocus(); // �ù��һ������һ���ռ�۽�
		} else if (e.getSource() == jbArray[1]) { // ѡ������ð�ť
			for (int i = 0; i < jpfArray.length; i++) {
				jpfArray[i].setText("");
			}
			tfName.setText("");
		} else if (e.getSource() == jbArray[0]) {

			if (userName.equals("")) {
				JOptionPane.showMessageDialog(this, "�������û���", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (userPwd.equals("")) {
				JOptionPane.showMessageDialog(this, "������ԭʼ����", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (newUserPwd.equals("")) {
				JOptionPane.showMessageDialog(this, "������������", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!newUserPwd.matches(patternStr)) {
				JOptionPane.showMessageDialog(this, "������ֻ����6~12λ����ĸ��������", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!newUserPwd1.equals(newUserPwd)) {
				JOptionPane.showMessageDialog(this, "ȷ�������������벻��", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				//���ݿ����ӣ������
				 conn = ConnecFactory.getConnection();
				 stam = conn.createStatement();
				 //һ��sql����Ⲣ���¡�
				String sql = "UPDATE user_form SET user_pwd ='" + newUserPwd + "'"
						+ "WHERE user_name = '" + userName + "'"
						+ "AND user_pwd ='" + userPwd + "'"; // ƴװsql���
				int i = stam.executeUpdate(sql); // ִ�и���
				if (i == 0) {
					JOptionPane.showMessageDialog(this,
							"�޸�ʧ�ܣ����������û������������Ƿ���ȷ", "����",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (i == 1) {
					JOptionPane.showMessageDialog(this, "�����޸ĳɹ�", "����",
							JOptionPane.ERROR_MESSAGE);
					return;
				}


			} catch (Exception ex) {
				ex.printStackTrace();
			} finally{
				ConnecFactory.closeConnection(conn, stam, rs);
			}
		}

	}
	
//	public static void main(String[] args){
//		JFrame jf = new JFrame();
//		ChangePwd cp = new ChangePwd();
//		jf.setLocation(400, 300);
//		jf.setSize(400,300);
//		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		jf.add(cp);
//		jf.setVisible(true);
//
//	}

}
