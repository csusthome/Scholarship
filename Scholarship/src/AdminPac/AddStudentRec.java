package AdminPac;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import AdminPac.AddStudentRecPanel.DeyuPanel;
import AdminPac.AddStudentRecPanel.TiyuPanel;
import AdminPac.AddStudentRecPanel.ZhiyuPanel;
import ConnectFactory.ConnecFactory;

/**
 * 这是添加学生信息记录的主窗体，  过两天一定要改！！！
 * 
 * @author U-anLA
 * 
 */
public class AddStudentRec extends JPanel implements ActionListener  {
	
	JLabel jlSee = new JLabel();

	JLabel jl1 = new JLabel("学生姓名:");
	JTextField jtfName = new JTextField();

	JLabel jl2 = new JLabel("学生学号:");
	JTextField jtfNum = new JTextField();

	JLabel jl3 = new JLabel("降级数:");

	JLabel[] jl = { new JLabel("智育项:"), new JLabel("德育项："), new JLabel("体育项："),
			new JLabel("四六级项：") };
	JButton jb = new JButton("提交");
	JButton jbReset = new JButton("重置");

	// 定义数据库相关变量
	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	
	
	//几个panel，用来显示几大分种的
	private ZhiyuPanel zhiyuPanel;
	private JScrollPane jspZhiyu;

	private DeyuPanel deyuPanel;
	private JScrollPane jspDeyu;

	private TiyuPanel tiyuPanel;
	private JScrollPane jspTiyu;
	
	File file = new File("file/detail.xls");

	DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//	String p=decimalFomat.format(price);//format 返回的是字符串
	
	
	//定义四六级选项，用来选择的。
	private JRadioButton[] jrb = { new JRadioButton("没过四级"),
			new JRadioButton("大一过四级"), new JRadioButton("大二过四级"),
			new JRadioButton("大三过四级") };
	//定义选择框，用来将四级选项选在一起。
	private ButtonGroup bg = new ButtonGroup();

	private JCheckBox jcb = new JCheckBox("已过六级");
	
	//定义班级combobox；
	private JComboBox jcomb;
	//定义降级数combobox；
	private JComboBox jcombDown = new JComboBox();

	/**
	 * 添加学生的构造方法。
	 */
	public AddStudentRec() {
		initFrame();
		addListeners();

	}

	/**
	 * 用于添加监听事件
	 */
	private void addListeners() {
		jb.addActionListener(this);
		jbReset.addActionListener(this);
	}

	
	/**
	 * 初始化窗体的方法
	 */
	private void initFrame() {

		for (int i = 0; i < 3; i++) {
			jcombDown.addItem(i);
		}

		this.setLayout(null);

		initJcomb();
		
		zhiyuPanel = new ZhiyuPanel();
		jspZhiyu = new JScrollPane(zhiyuPanel);

		deyuPanel = new DeyuPanel();
		jspDeyu = new JScrollPane(deyuPanel);

		tiyuPanel = new TiyuPanel();
		jspTiyu = new JScrollPane(tiyuPanel);

		jl1.setBounds(20, 20, 80, 30);
		this.add(jl1);

		jtfName.setBounds(100, 20, 150, 30);
		this.add(jtfName);

		jl2.setBounds(270, 20, 80, 30);
		this.add(jl2);

		jtfNum.setBounds(350, 20, 150, 30);
		this.add(jtfNum);

		jl3.setBounds(900, 540, 80, 30);
		this.add(jl3);

		jcombDown.setBounds(950, 540, 80, 30);
		this.add(jcombDown);

		jcomb.setBounds(520, 20, 200, 30);
		this.add(jcomb);

		for (int i = 0; i < jl.length; i++) {
			if (i != 3) {
				jl[i].setBounds(20 + i * 350, 50, 100, 30);
			} else {
				jl[i].setBounds(720, 540, 100, 30);
			}
			this.add(jl[i]);
		}

		jspZhiyu.setBounds(20, 80, 300, 580);
		this.add(jspZhiyu);

		jspDeyu.setBounds(370, 80, 300, 580);
		this.add(jspDeyu);

		jspTiyu.setBounds(720, 80, 300, 440);
		this.add(jspTiyu);

		for (int i = 0; i < jrb.length; i++) {
			if (i == 0) {
				jrb[i].setSelected(true);
			}
			jrb[i].setBounds(780, 540 + 30 * i, 100, 30);
			bg.add(jrb[i]);
			this.add(jrb[i]);
		}

		jcb.setBounds(780, 660, 100, 30);
		this.add(jcb);

		jb.setBounds(920, 625, 100, 30);
		this.add(jb);
		
		jbReset.setBounds(920, 665, 100, 30);
		this.add(jbReset);
		
		jlSee.setBounds(1050, 400, 100, 30);
		this.add(jlSee);
		//添加超链接
		setUrlLabel(jlSee,"查看评定细则",file);
		
	}
	

	/**
	 * 这个类是用来设置超链接的。
	 * @param jl
	 * @param text
	 * @param f
	 */
	public void setUrlLabel(JLabel jl,String text,final File f){
		jl.setText(text);
		jl.setForeground(Color.BLUE);
		//设置为手型鼠标
		jl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jl.addMouseListener(new MouseAdapter() {
			
			//重写点击事件方法。
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().open(f);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(AddStudentRec.this, "没有找到该文件", "错误", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					return;
				}
			}
		});
	}

	/**
	 * 重写的，事件方法。
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb) {
			boolean isOk = true;
			boolean isNameOk = true;
			// 检测学号是否达标

			//判断基本信息输入是否为空
			if (this.jtfName.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "请输入姓名", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (this.jtfNum.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "请输入学号", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!this.checkPartentForMesg(jtfNum.getText().trim())) {
				JOptionPane.showMessageDialog(this, "学号只能是12位的纯数字", "错误",
						JOptionPane.ERROR_MESSAGE);
				isNameOk = false;
				return;
			}
			//以下循环用来检测各个输入框是否输入符合规格。
			//从zhiyupanel中获得各个分数
			for (int i = 0; i < zhiyuPanel.getArrayJtf().size(); i++) {
				if (!zhiyuPanel.getArrayJtf().get(i).getText().trim()
						.equals("")) {
					if (!this.checkPartentForScore(zhiyuPanel.getArrayJtf()
							.get(i).getText().trim())) {
						JOptionPane.showMessageDialog(this, "分数只能是三位数以下的整数或空",
								"错误", JOptionPane.ERROR_MESSAGE);
						//如果有一个不对，就退出。并设为不ok
						isOk = false;
						return;
					}
				}
			}

			//如果各项都达标
			if (isOk == true) {
				int i = JOptionPane.showConfirmDialog(this, "您确认要增加这条记录么？",
						"询问", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (i == 0) {
					//构造sql语句。
					String sql1 = "INSERT INTO stu_info (stu_name,stu_class,stu_num,downgrade) VALUES('"
							+ jtfName.getText().trim()
							+ "','"
							+ jcomb.getSelectedItem()
							+ "','"
							+ jtfNum.getText().trim()
							+ "',"
							+ jcombDown.getSelectedItem().toString().trim()
							+ ");";
					try {
						conn = ConnecFactory.getConnection();
						stam = conn.createStatement();
						int target = stam.executeUpdate(sql1);
						//如果学生信息已经成功插入进去，就执行接下来的工作。
						if (target == 1) {
							
//							conn = ConnecFactory.getConnection();
							Statement stam2 = conn.createStatement();

							//将其预设为0；
							float zhiyu = 0.0f;
							float deyu = 0.0f;
							float tiyu = 0.0f;
							
							//通过方法获得各项分数。
							zhiyu = this.getZhiyuScore();
							deyu = this.getDeyuScore();
							tiyu = this.getTiyuScore();
							
							//如果各项分数大于其最大值，就只能为最大
							if (zhiyu  > 70.0f) {
								zhiyu = 70.0f;
							}
							if (deyu > 25.0f) {
								deyu = 25.0f;
							}
							if (tiyu > 5.0f) {
								tiyu = 5.0f;
							}
							
							//定义sql语句，用来将最后成绩插入到数据表中
							String sql2 = "INSERT INTO statistic(stu_num,stu_deyu,stu_zhiyu,stu_tiyu,total) VALUES('"
									+ jtfNum.getText().trim()
									+ "',"
									+ deyu
									+ ","
									+ zhiyu
									+ ","
									+ tiyu
									+ ","
									+ (deyu + zhiyu + tiyu) + ");";

							System.out.println(sql2);
							int target2 = stam2.executeUpdate(sql2);
							
							
							//给插入后的成功或失败来给出相应的提示。
							if (target2 == 1) {
								JOptionPane.showMessageDialog(this, "操作成功",
										"恭喜", JOptionPane.OK_OPTION);
							} else {
								JOptionPane.showMessageDialog(this, "操作失败",
										"错误", JOptionPane.ERROR_MESSAGE);

							}

						}
					} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
						JOptionPane.showMessageDialog(this, "该学生记录已存在！", "错误",
								JOptionPane.ERROR_MESSAGE);
						return;
						// e.printStackTrace();
					} catch (SQLException ex) {
						System.out.println("-------------347-------------");
						System.out.println("--------数据库连接出错！！！！--------");
						ex.printStackTrace();
					} finally {
						ConnecFactory.closeConnection(conn, stam, rs);
					}

				}
			}

		}else if(e.getSource() == jbReset){
			this.jtfName.setText("");
			this.jtfNum.setText("");
			this.jcomb.setSelectedIndex(0);
			for(int i = 0;i < jrb.length;i++){
				jrb[i].setSelected(false);
			}
			this.jcombDown.setSelectedIndex(0);
			for(int i = 0;i < zhiyuPanel.getArrayJtf().size();i++){
				zhiyuPanel.getArrayJtf().get(i).setText("");
			}
			for(int i = 0;i < deyuPanel.getArrayJcb().size();i++){
				deyuPanel.getArrayJcb().get(i).setSelected(false);
			}
			for(int i = 0;i < tiyuPanel.getArrayJcb().size();i++){
				tiyuPanel.getArrayJcb().get(i).setSelected(false);
			}
		}

	}

	/**
	 * 此数据库用于获得智育分数。
	 * @return 计算后的分数。
	 */
	public float getZhiyuScore() {
		float finalScore = 0.0f;
		float score = 0.0f;
		float num = 0.0f;
		for (int i = 0; i < zhiyuPanel.getArrayJtf().size(); i++) {
			if (!zhiyuPanel.getArrayJtf().get(i).getText().trim().equals("")) {
				if(zhiyuPanel.getArrayZhi().get(i).getTarget() == 0){
				//计算分数。
					score = Float.parseFloat(zhiyuPanel.getArrayJtf().get(i)
							.getText().trim())
							* zhiyuPanel.getArrayZhi().get(i).getSubj_score()
							+ score;
					num += zhiyuPanel.getArrayZhi().get(i).getSubj_score();
				}
				
				// 还要插入数据。

				this.insertItem(
						"stu_zhiyu_score",
						jtfNum.getText().trim(),
						zhiyuPanel.getArrayZhi().get(i).getSubj_num(),
						Float.parseFloat(zhiyuPanel.getArrayJtf().get(i)
								.getText().trim()));

			}
		}
		if(num == 0){
			return 0.0f;
		}
		finalScore = (score / num) * 0.70f;
		//添加四六级项。并把四六级项信息插进去。
		if (jrb[0].isSelected()) {
			this.insertItem("stu_cet_score", this.jtfNum.getText().toString()
					.trim(), "type_name", "0cet4");
			finalScore = finalScore + 0.0f;
		}
		if (jrb[1].isSelected()) {
			this.insertItem("stu_cet_score", this.jtfNum.getText().toString()
					.trim(), "type_name", "1cet4");

			finalScore = finalScore + 1.5f;
		}
		if (jrb[2].isSelected()) {
			this.insertItem("stu_cet_score", this.jtfNum.getText().toString()
					.trim(), "type_name", "2cet4");

			finalScore = finalScore + 1.0f;
		}
		if (jrb[3].isSelected()) {
			this.insertItem("stu_cet_score", this.jtfNum.getText().toString()
					.trim(), "type_name", "3cet4");

			finalScore = finalScore + 0.5f;
		}
		if (jcb.isSelected()) {
			this.insertItem("stu_cet_score", this.jtfNum.getText().toString()
					.trim(), "type_name", "cet6");

			finalScore = finalScore + 2.0f;
		}

		
		
		finalScore = Float.parseFloat(decimalFormat.format(finalScore));
		return finalScore;
	}

	
	/**
	 * 获得德育分数
	 * @return
	 */
	public float getDeyuScore() {
		float score = 0.0f;

		for (int i = 0; i < deyuPanel.getArrayJcb().size(); i++) {
			if (deyuPanel.getArrayJcb().get(i).isSelected()) {
				//计算各项德育分数
				score = deyuPanel.getArrayDeyu().get(i).getDeyu_score() + score;
				//将数据插入进去。
				this.insertItem("stu_deyu_score", jtfNum.getText().trim(),
						"deyu_num", deyuPanel.getArrayDeyu().get(i)
								.getDeyu_num());

			}
		}

		float finalScore = score*0.25f;
		
		finalScore = Float.parseFloat(decimalFormat.format(finalScore));
	
		return finalScore;
	}

	
	/**
	 * 得到体育分数。
	 * @return
	 */
	public float getTiyuScore() {
		float num = 0.0f;
		float finalScore = 0.0f;
		float score1 = 0.0f;
		float score2 = 0.0f;
		for (int i = 0; i < zhiyuPanel.getArrayJtf().size(); i++) {
			if (!zhiyuPanel.getArrayJtf().get(i).getText().trim().equals("")
					&& zhiyuPanel.getArrayZhi().get(i).getTarget() == 1) {
				
				//计算体育分数。
				score1 = Float.parseFloat(zhiyuPanel.getArrayJtf().get(i)
						.getText().trim())
						* zhiyuPanel.getArrayZhi().get(i).getSubj_score()
						+ score1;
				//再将体育项给加进去。
				num += zhiyuPanel.getArrayZhi().get(i).getSubj_score();

			}
		}
		if(num == 0.0f){
			return 0.0f;
		}
		score1 = (score1/num) * 0.7f;
		//将体育项插入进去。
		for (int i = 0; i < tiyuPanel.getArrayJcb().size(); i++) {
			if (tiyuPanel.getArrayJcb().get(i).isSelected()) {
				score2 = tiyuPanel.getArrayTiyu().get(i).getTiyu_score() + score2;
				this.insertItem("stu_tiyu_score", jtfNum.getText().trim(),
						"tiyu_num", tiyuPanel.getArrayTiyu().get(i)
								.getTiyu_num());
			}
		}
		//不用乘以0.3
//		score2 = score2*0.3f;
		finalScore = score1 + score2;
		
		finalScore *= 0.05f;
		
		finalScore = Float.parseFloat(decimalFormat.format(finalScore));
		
		
		return finalScore;
	}

	
	/**
	 * 用于插入德育项和体育项的各项。
	 * @param tableName
	 * @param stu_num
	 * @param colume_name
	 * @param item_num
	 */
	public void insertItem(String tableName, String stu_num,
			String colume_name, String item_num) {
		String sql = "INSERT INTO " + tableName + " (stu_num," + colume_name
				+ ") VALUES('" + stu_num + "','" + item_num + "');";
		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			int i = stam.executeUpdate(sql);
			if (i == 1) {
				System.out.println("****222222222222插入成功*****");
			}
		} catch (SQLException e) {
			System.out.println("--------数据库连接出错！！！！--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}

	}

	/**
	 * 用于插入智育项的各项各项
	 * @param tableName
	 * @param stu_num
	 * @param item_num
	 * @param score
	 */
	public void insertItem(String tableName, String stu_num, String item_num,
			float score) {

		String sql = "INSERT INTO " + tableName
				+ " (stu_num,subj_num,stu_sub_score) VALUES('" + stu_num
				+ "','" + item_num + "'," + score + ");";
		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			int i = stam.executeUpdate(sql);
			if (i == 1) {
				System.out.println("****插入成功*****");
			}
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(this, "该学生记录已存在！", "错误",
					JOptionPane.ERROR_MESSAGE);
			return;
			// e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("--------数据库连接出错！！！！--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}

	}

	/**
	 * 用于初始化班级combobox。
	 */
	public void initJcomb() {
		jcomb = new JComboBox();
		String sql = "SELECT * FROM class_name;";
		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			rs = stam.executeQuery(sql);
			while (rs.next()) {

				jcomb.addItem(rs.getString("stu_class"));
			}
		} catch (SQLException e) {
			System.out.println("--------数据库连接出错！！！！--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}
	}

	
	/**
	 * 用于插入学生表，将数据插入。
	 * @param name
	 * @param className
	 * @param num
	 * @param n
	 */
	public void insertStu(String name, String className, String num, int n) {
		String sql = "INSERT INTO stu_info (stu_name,stu_class,stu_num,downgrade) VALUES('"
				+ name + "','" + className + "','" + num + "'," + n + ");";
		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			int i = stam.executeUpdate(sql);
			if (i == 1) {
				System.out.println("****插入成功*****");
			}
		} catch (SQLException e) {
			System.out.println("--------数据库连接出错！！！！--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}
	}

	/**
	 * 这方法是用来检测输入框的合法性的，
	 * 
	 * @param str
	 *            待检测字符串
	 * @return 返回结果，真或假
	 */
	public boolean checkPartentForScore(String str) {
		String patternStr = "[0-9]{0,2}"; // 判断密码格式的正确性，只能是两位数的数字。
		if (!str.matches(patternStr)) {
			return false;
		}
		return true;
	}

	/**
	 * 检查学号的正确性，
	 * @param str
	 * @return
	 */
	public boolean checkPartentForMesg(String str) {
		String patternStr = "[0-9]{12}"; // 判断学号格式的正确性，只能是十二位数的数字。
		if (!str.matches(patternStr)) {
			return false;
		}
		return true;
	}

	
	
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		AddStudentRec asr = new AddStudentRec();
		jf.setLocation(40, 30);
		jf.setSize(800, 600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(asr);
		jf.setVisible(true);
	}
}
