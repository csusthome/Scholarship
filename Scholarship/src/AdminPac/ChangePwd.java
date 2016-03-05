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

	//定义数据库操作相关变量
	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	private JLabel[] jlArray = { new JLabel("用户名"), new JLabel("原始密码"),
			new JLabel("新密码"), new JLabel("确认新密码") };
	//定义用户名
	private JTextField tfName = new JTextField();
	//分别定义三个密码框。原密码，新密码，检测新密码。
	private JPasswordField[] jpfArray = { new JPasswordField(),
			new JPasswordField(), new JPasswordField() };
	//定义重置和
	private JButton[] jbArray = { new JButton("确认"), new JButton("重置") };

	/**
	 * 构造方法。
	 */
	public ChangePwd() {
		initFrame();
		addListener();
	}

	/**
	 * 添加各种控件的事件。
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
	 * 初始化窗体代码
	 */
	private void initFrame() {
		//将布局设定为空。
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
		this.add(jbArray[0]); // 添加确认按钮
		jbArray[1].setBounds(170, 230, 100, 30);
		this.add(jbArray[1]); // 添加重置按钮
	}

	public void actionPerformed(ActionEvent e) {
		String patternStr = "[0-9a-zA-Z]{5,12}"; // 判断密码格式的正确性，0~9，a~z，A~Z
		String userName = tfName.getText().trim();


		/*
		 * 下面的这种表达方式，可以得到密码 并且可以用正则表达式来判断。
		 */
		String userPwd = jpfArray[0].getText().trim();
		String newUserPwd = jpfArray[1].getText().trim();
		String newUserPwd1 = jpfArray[2].getText().trim();

		//按回车，一次向下获得焦点。
		if (e.getSource() == tfName) {
			jpfArray[0].requestFocus(true);
		} else if (e.getSource() == jpfArray[0]) {
			jpfArray[1].requestFocus();
		} else if (e.getSource() == jpfArray[1]) {
			jpfArray[2].requestFocus();
		} else if (e.getSource() == jpfArray[2]) {
			jbArray[0].requestFocus(); // 让光标一次往下一个空间聚焦
		} else if (e.getSource() == jbArray[1]) { // 选项框重置按钮
			for (int i = 0; i < jpfArray.length; i++) {
				jpfArray[i].setText("");
			}
			tfName.setText("");
		} else if (e.getSource() == jbArray[0]) {

			if (userName.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入用户名", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (userPwd.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入原始密码", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (newUserPwd.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入新密码", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!newUserPwd.matches(patternStr)) {
				JOptionPane.showMessageDialog(this, "新密码只能是6~12位的字母或者数字", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (!newUserPwd1.equals(newUserPwd)) {
				JOptionPane.showMessageDialog(this, "确认密码与新密码不符", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				//数据库连接，并检测
				 conn = ConnecFactory.getConnection();
				 stam = conn.createStatement();
				 //一条sql语句检测并更新。
				String sql = "UPDATE user_form SET user_pwd ='" + newUserPwd + "'"
						+ "WHERE user_name = '" + userName + "'"
						+ "AND user_pwd ='" + userPwd + "'"; // 拼装sql语句
				int i = stam.executeUpdate(sql); // 执行更新
				if (i == 0) {
					JOptionPane.showMessageDialog(this,
							"修改失败，请检查您的用户名或者密码是否正确", "错误",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else if (i == 1) {
					JOptionPane.showMessageDialog(this, "密码修改成功", "错误",
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
