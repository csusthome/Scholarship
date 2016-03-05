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
 * 本类是系统的登陆界面，主要包含 管理员登陆与普通会员登陆 以及是否记住密码，是否自动登陆等作用
 * 
 * @author U-anLA
 * 
 */
public class LoginFrame extends JFrame implements ActionListener {
	// 定义数据库相关变量
	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	private JLabel jl1, jl2, jl3;
	// 用户名输入框
	private JTextField jtf;
	// 密码输入框
	private JPasswordField jpf;
	// 登陆按钮，管着两个窗口
	private JButton jb = new JButton("登陆");
	private JCheckBox[] jcb = { new JCheckBox("自动登录"), new JCheckBox("记住密码") };

	private ButtonGroup bg = new ButtonGroup();
	// 管理员窗口界面
	private AdminFrame adminFrame;
	// 普通用户界面窗口

	// 用来存用户的登录名
	private String loginName;
	// 用来存用户的登陆密码
	private String loginPwd;
	private boolean isLogin = false;
	//将userName，也就是登录人传递过去。
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * LoginFrame登陆窗体的构造方法
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
	 * 用来添加监听事件，
	 * 将所有的控件都添加进去。
	 */
	private void addActionLisener() {
		jpf.addActionListener(this);
		jb.addActionListener(this);

	}

	/**
	 * 登陆窗口的初始化界面窗口， 所有与窗口界面相关的操作都在这个方法里面
	 */
	private void initPanel() {

		//登陆时的界面图片。
		ImageIcon loginIcon = new ImageIcon("file/login.jpg");

		//将布局设为空。
		this.setLayout(null);
		this.setTitle("奖学金评定系统");
		
		
		
		//添加jlabel，将图片嵌入进去。
		jl1 = new JLabel(loginIcon);
		jl1.setBounds(0, 0, 400, 150);
		this.add(jl1);
		//添加登录名
		jl2 = new JLabel("登录名：");
		jl2.setBounds(30, 160, 60, 30);
		this.add(jl2);
		
		jl3 = new JLabel("    密码：");
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
		
		//将这些设置。
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(400, 300, 400, 300);

	}

	/**
	 * 对事件的重写的方法，主要是jtf的和jbt的action方法
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String putName = jtf.getText().trim(); // 得到输入名
		String putPwd = jpf.getText().trim(); // 得到输入密码
		String getPwd = new String(); // 用来存储要获得的响应的密码
		int isRemenber = 0;
		int isAutoLogin = 0;
		// 判断事件源是哪一个
		if (e.getSource() == jb || e.getSource() == jpf) {
			/* 登陆按钮 */
			if (putName.equals("")) { // 没有输入用户名
				JOptionPane.showMessageDialog(this, "请输入登录名或密码", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (putPwd.equals("")) { // 没有输入用户名
				JOptionPane.showMessageDialog(this, "请输入登录名或密码", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			// 判断是否自动登陆
			if (jcb[0].isSelected()) {
				isAutoLogin = 1;
			}
			// 判断是否记住密码
			if (jcb[1].isSelected()) {
				isRemenber = 1;
			}

			try {
				conn = ConnecFactory.getConnection(); // 利用静态方法得到链接
				stam = conn.createStatement(); // 得到statment
				// 创建一个结果集，用来从select语句中得到AdminPwd

				String sql = "SELECT user_pwd FROM user_form WHERE user_name = '"
						+ putName + "'"; // 拼接sql语句
				System.out.println(sql);
				rs = stam.executeQuery(sql);
				while (rs.next()) {
					getPwd = rs.getString("user_pwd"); // 得到adminPwd
				}

				if (putPwd.equals(getPwd)) {
					/* 登陆成功，显示管理员主界面 */

					String sqlUpdate = "UPDATE loginInfo SET loginName = '"
							+ jtf.getText().trim() + "',loginPwd = '"
							+ jpf.getText().trim() + "',isRemenber = "
							+ isRemenber + ",isAutoLogin = " + isAutoLogin
							+ " WHERE id = 1"; // 注意，这里where前面要加上一个空格！！！！
					// 设置监控变量，看是否已经改变了第一行也就是存的值
					int n3 = stam.executeUpdate(sqlUpdate);
					if (n3 == 0) {
						JOptionPane.showMessageDialog(this, "数据库出错！");
						//this.setVisible(false);
						System.exit(0);
					}
					// 用来制造假象，想事在数据库中寻找
					Thread.sleep(1000);

					// 如果登陆成功，给出提示语句
					JOptionPane.showMessageDialog(this, "欢迎:" + putName, "欢迎",
							JOptionPane.YES_NO_CANCEL_OPTION);
					// 将名字得到
					// 将本登陆窗体设为不可见
					this.setVisible(false);
					// new出管理员窗体，并且设为可见
					adminFrame = new AdminFrame(this.jtf.getText().trim());

				} else {
					// 如果登陆失败，则提示错误
					JOptionPane.showMessageDialog(this, "输入的用户名或者密码错误", "错误",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("-----------登陆出错-----------");
			} catch (InterruptedException e2) {

				e2.printStackTrace();
			}

		}
	}

	/***
	 * 本方法用于登陆前的检测，就是看是否自动登陆，是否是记住密码
	 * 
	 * 
	 */
	public void check() {

		// 用三个int类型的值来代替bool值，来记录是否为管理员，是否记住密码，是否自动登陆
		int isWho = 0;
		int isRemenber = 0;
		int isAutoLogin = 0;
		// 得到数据库连接
		conn = ConnecFactory.getConnection();
		try {
			stam = conn.createStatement();
			// 拼装sql语句
			String sql = "SELECT * FROM loginInfo;";
			// 执行sql语句
			rs = stam.executeQuery(sql);
			while (rs.next()) {
				// 分别得到各个属性的值
				int id = rs.getInt("id");
				loginName = rs.getString("loginName");
				loginPwd = rs.getString("loginPwd");
				isRemenber = rs.getInt("isRemenber");
				isAutoLogin = rs.getInt("isAutoLogin");
			}
		} catch (SQLException e) {
			System.out.println("-----------数据库连接出错---------");
			e.printStackTrace();
		}

		if (isRemenber == 0) {
			// 如果没有选择记住密码，则啥也不用做，直接默认选上“管理员”
		} else if (isRemenber == 1) {
			// 如果已经选择了记住密码，则往下一步一步进行
			// 首先将该打的勾，该写的字够打好
			jtf.setText(loginName);
			jpf.setText(loginPwd);

			jcb[1].setSelected(true);
			/**
			 * 下一步，看是否勾选了自动登陆，如果勾选了， 就登陆
			 */
			if (isAutoLogin == 1) {
				/**
				 * 此处为自动登陆按钮，如果勾选了管理员 则调用管理员自动登陆的方法， 否则，调用普通会员自动登陆的方法
				 */
				
					// 管理员自动登陆事件
					/**
					 * 如果走到了这里，就说明上一次的对了，所以就不用再调用数据库来查找sql语句了
					 */
					// 同样设置为不可编辑
					jtf.setEditable(false);
					jpf.setEditable(false);
					jb.setEnabled(false);
					//并且将自动登陆悬赏
					jcb[0].setSelected(true);
					jcb[1].setEnabled(false);
					// 得到用户名，传给下一个窗体
					userName = jtf.getText();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
					//如果在登陆延时这个阶段，用户吧自动登陆取消了，那就不会登陆了，就会跳出去。
					if(!jcb[0].isSelected()){
						// 同样设置为补课编辑
						jtf.setEditable(true);
						jpf.setEditable(true);
						jb.setEnabled(true);
						jcb[1].setEnabled(true);
						return;
					}
					// 如果登陆成功，打出提示语句
					JOptionPane.showMessageDialog(this, "欢迎使用", "欢迎",
							JOptionPane.INFORMATION_MESSAGE);
					
					
					
					// 将本窗体设置为不可见
					this.setVisible(false);
					// new出管理员窗体
					adminFrame = new AdminFrame(this.jtf.getText().trim());

				
			}
		}

	}

	/**
	 * 这个整个项目的main方法，用于启动程序
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LoginFrame lp = new LoginFrame();
		//先调用检查，将预选项都加进去。
		lp.check();


	}

}
