package AdminPac;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import AdminPac.AddStudentRecPanel.DeyuPanel;
import AdminPac.AddStudentRecPanel.TiyuPanel;
import AdminPac.AddStudentRecPanel.ZhiyuPanel;
import ConnectFactory.ConnecFactory;

/**
 * 这个是用来返回搜索页面的结果的窗体。
 * 其实跟前面添加学生窗体一模一样
 * @author U-anLA
 *
 */
public class ChangeStuItem extends JDialog implements ActionListener,
		ItemListener, DocumentListener {
	
	private JLabel jlSee = new JLabel();
	
	File file = new File("file/detail.xls");

	// 得到前一步的信息
	private SearchStu ss;
	private String stuNum;
	private AddStudentRec addStudentRec;

	JLabel jl1 = new JLabel("学生姓名:");
	JTextField jtfName = new JTextField();

	JLabel jl2 = new JLabel("学生学号:");
	JTextField jtfNum = new JTextField();

	JLabel jl3 = new JLabel("降级数:");

	JLabel[] jl = { new JLabel("智育项:"), new JLabel("德育项："), new JLabel("体育项："),
			new JLabel("四六级项：") };
	JButton jb = new JButton("提交");

	
	// 定义数据库相关变量
	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	//创建各个选项窗口。
	private ZhiyuPanel zhiyuPanel;
	private JScrollPane jspZhiyu;

	private DeyuPanel deyuPanel;
	private JScrollPane jspDeyu;

	private TiyuPanel tiyuPanel;
	private JScrollPane jspTiyu;

	//得到四六级项的信息
	private JRadioButton[] jrb = { new JRadioButton("没过四级"),
			new JRadioButton("大一过四级"), new JRadioButton("大二过四级"),
			new JRadioButton("大三过四级") };
	private ButtonGroup bg = new ButtonGroup();

	private JCheckBox jcb = new JCheckBox("已过六级");

	//定义班级选择框和降级选择框
	private JComboBox jcomb;
	private JComboBox jcombDown = new JComboBox();

	
	DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.

	
	/**
	 * 构造方法
	 * @param ss 前一个窗体的引用
	 * @param owner 父窗体
	 * @param stuNum 学生学号
	 */
	public ChangeStuItem(SearchStu ss, Window owner, String stuNum) {
		super(owner);
		this.stuNum = stuNum;
		this.ss = ss;
		//初始化窗体，设置各项
		initFrame();
		//设置item，并且把各个项目加进去。
		setItem("智育项", stuNum);
		setItem("德育项", stuNum);
		setItem("体育项", stuNum);
		setItem("四六级", stuNum);
		// 把他们加载设置后的后面，不然设置时也会触发。
		addListeners();
	}

	/**
	 * 添加事件
	 */
	private void addListeners() {
		// 将deyupanel的时间监听加进来。
		for (int i = 0; i < deyuPanel.getArrayJcb().size(); i++) {
			deyuPanel.getArrayJcb().get(i).addItemListener(this);
		}
		// 将体育panel加进来
		for (int i = 0; i < tiyuPanel.getArrayJcb().size(); i++) {
			tiyuPanel.getArrayJcb().get(i).addItemListener(this);
		}
		// 将四级项加进来。
		for (int i = 0; i < jrb.length; i++) {
			jrb[i].addItemListener(this);
		}
		// 将六级项加进来
		jcb.addItemListener(this);
		jcomb.addItemListener(this);
		jcombDown.addItemListener(this);
		jb.addActionListener(this);

		// 添加jtextfield的监听事件。
		Document docName = jtfName.getDocument();
		docName.addDocumentListener(this);

		Document docNum = jtfNum.getDocument();
		docNum.addDocumentListener(this);

		for (int i = 0; i < zhiyuPanel.getArrayJtf().size(); i++) {
			Document doc = zhiyuPanel.getArrayJtf().get(i).getDocument();
			doc.addDocumentListener(this);
		}
	}

	/**
	 * 初始化窗体。
	 */
	private void initFrame() {
		
		this.setLayout(null);

		// 以下方法得到最大化窗体。当然是尺寸上的，因为这个不能最大化窗体。
		this.setLocation(0, 0);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的Dimension对象
		int centerX = screenSize.width; // 计算频幕中间的像素
		int centerY = screenSize.height;
		this.setSize(centerX, centerY);

		this.setTitle(stuNum);
		addStudentRec = new AddStudentRec();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(ChangeStuItem.this.jb.isEnabled()){
					int i = JOptionPane.showConfirmDialog(ChangeStuItem.this, "您已更改记录未保存，确认要退出？（将无法更改）",
							"询问", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (i == JOptionPane.YES_OPTION) {
						ChangeStuItem.this.setVisible(false);
						ss.getJbtSearch().setEnabled(true);
						ss.getJcbNmu().setEnabled(true);

					}else{
						
						
					}
				}else{
					
					ChangeStuItem.this.setVisible(false);
					ss.getJbtSearch().setEnabled(true);
					ss.getJcbNmu().setEnabled(true);

				}
				

			}
		});

		for (int i = 0; i < 3; i++) {
			jcombDown.addItem(i);
		}

		this.setLayout(null);

		// 初始化班级combbox
		initJcomb();

		// 初始化姓名，学号，班级等基本信息。
		setBaseInfo(stuNum);

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

			jrb[i].setBounds(780, 540 + 30 * i, 100, 30);
			bg.add(jrb[i]);
			this.add(jrb[i]);
		}

		jcb.setBounds(780, 660, 100, 30);
		this.add(jcb);

		jb.setBounds(920, 625, 100, 30);
		this.add(jb);
		// 一开始，把提交按钮设为不可按，直到已经修改了，就设为true。
		jb.setEnabled(false);

		
		jlSee.setBounds(1050, 400, 100, 30);
		this.add(jlSee);
		//添加超链接
		setUrlLabel(jlSee,"查看评定细则",file);
		
		this.setVisible(true);

		
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
					JOptionPane.showMessageDialog(ChangeStuItem.this, "没有找到该文件", "错误", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					return;
				}
			}
		});
	}
	/**
	 * 这里重写processWindowEvent，就可以阻止其关闭了。也就是可以
	 * 在退出前，询问。
	 */
    protected void processWindowEvent(WindowEvent e) {  
        if (e.getID() == WindowEvent.WINDOW_CLOSING) { 
        	if(this.jb.isEnabled()){
        		if (JOptionPane.showConfirmDialog(this, "确实要关闭？(记录已更改，未保存。)", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {   
    				ChangeStuItem.this.setVisible(false);
    				ss.getJbtSearch().setEnabled(true);
    				ss.getJcbNmu().setEnabled(true);
    				ss.getJbtDelete().setEnabled(true);
                } else {  
                	
                }
        	}else{
        		ChangeStuItem.this.setVisible(false);
				ss.getJbtSearch().setEnabled(true);
				ss.getJcbNmu().setEnabled(true);
				ss.getJbtDelete().setEnabled(true);

        	}
              
        } else {  
            super.processWindowEvent(e);  
        }  
    } 
	

    /**
     * 添加各个事件。
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb) {
			boolean isOk = true;
			boolean isNameOk = true;
			// 检测学号是否达标

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

			for (int i = 0; i < zhiyuPanel.getArrayJtf().size(); i++) {
				if (!zhiyuPanel.getArrayJtf().get(i).getText().trim()
						.equals("")) {
					if (!this.checkPartentForScore(zhiyuPanel.getArrayJtf()
							.get(i).getText().trim())) {
						JOptionPane.showMessageDialog(this, "分数只能是三位数以下的整数或空",
								"错误", JOptionPane.ERROR_MESSAGE);
						isOk = false;
						return;
					}
				}
			}

			if (isOk == true) {
				int i = JOptionPane.showConfirmDialog(this, "您确认要修改这条记录么？",
						"询问", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (i == 0) {
					// 先级联删除
					String sqlDelete = "DELETE FROM stu_info WHERE stu_num = '"
							+ this.jtfNum.getText().trim() + "';";

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
						int delete = stam.executeUpdate(sqlDelete);
						if (delete >= 1) {
							//如果级联删除成功，就开始进行主要的插入操作。
							int target = stam.executeUpdate(sql1);
							if (target == 1) {

								Statement stam2 = conn.createStatement();

								float zhiyu = this.getZhiyuScore();
								float deyu = this.getDeyuScore();
								float tiyu = this.getTiyuScore();

								if (zhiyu  > 70.0f) {
									zhiyu = 70.0f;
								}
								if (deyu > 25.0f) {
									deyu = 25.0f;
								}
								if (tiyu > 5.0f) {
									tiyu = 5.0f;
								}
								String sql2 = "INSERT INTO statistic(stu_num,stu_deyu,stu_zhiyu,stu_tiyu,total) VALUES('"
										+ jtfNum.getText().trim()
										+ "','"
										+ deyu
										+ "','"
										+ zhiyu
										+ "','"
										+ tiyu
										+ "','" + (deyu + zhiyu + tiyu) + "');";

								System.out.println(sql2);
								int target2 = stam2.executeUpdate(sql2);
								if (target2 == 1) {
									JOptionPane.showMessageDialog(this, "操作成功",
											"恭喜", JOptionPane.OK_OPTION);
									this.jb.setEnabled(false);
								} else {
									JOptionPane.showMessageDialog(this, "操作失败",
											"提示", JOptionPane.ERROR_MESSAGE);
								}

							}

						}

					} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
						JOptionPane.showMessageDialog(this, "该学生记录已存在！", "错误",
								JOptionPane.ERROR_MESSAGE);
						return;
						// e.printStackTrace();
					} catch (SQLException ex) {
						System.out.println("--------数据库连接出错！！！！--------");
						ex.printStackTrace();
					} finally {
						ConnecFactory.closeConnection(conn, stam, rs);
					}

				}
			}

		}

	}

	/**
	 * 获得智育分数。
	 * @return
	 */
	public float getZhiyuScore() {
		float score = 0.0f;
		float num = 0;
		for (int i = 0; i < zhiyuPanel.getArrayJtf().size(); i++) {
			if (!zhiyuPanel.getArrayJtf().get(i).getText().trim().equals("")) {
				if (zhiyuPanel.getArrayZhi().get(i).getTarget() == 0) {
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
		
		float finalScore = (score / num) * 0.70f;
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
	 * 得到智育分数
	 * @return
	 */
	public float getDeyuScore() {
		float score = 0.0f;

		for (int i = 0; i < deyuPanel.getArrayJcb().size(); i++) {
			if (deyuPanel.getArrayJcb().get(i).isSelected()) {
				score = deyuPanel.getArrayDeyu().get(i).getDeyu_score() + score;

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
				score1 = Float.parseFloat(zhiyuPanel.getArrayJtf().get(i)
						.getText().trim())
						* zhiyuPanel.getArrayZhi().get(i).getSubj_score()
						+ score1;
				//将体育项学分加进去。
				num += zhiyuPanel.getArrayZhi().get(i).getSubj_score();

			}
		}

		if(num == 0.0f){
			return 0.0f;
		}
		score1 = (score1/num) * 0.7f;
		
		for (int i = 0; i < tiyuPanel.getArrayJcb().size(); i++) {
			if (tiyuPanel.getArrayJcb().get(i).isSelected()) {
				score2 = tiyuPanel.getArrayTiyu().get(i).getTiyu_score()
						+ score2;
				System.out.println(tiyuPanel.getArrayTiyu().get(i).getTiyu_score());
				this.insertItem("stu_tiyu_score", jtfNum.getText().trim(),
						"tiyu_num", tiyuPanel.getArrayTiyu().get(i)
								.getTiyu_num());
			}
		}

		//不用乘以0.3
//		score2 = score2 * 0.3f;
		finalScore = score1 + score2;
		finalScore = finalScore * 0.05f;
		
		finalScore = Float.parseFloat(decimalFormat.format(finalScore));
		
		
		return finalScore;
	}

	/**
	 * 插入项目，主要是德育，体育。
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
	 * 主要得到智育分数。
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
	 * 初始化combobox
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
	 * 插入学生信息。
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
	 * 判断密码格式的正确性。
	 * @param str
	 * @return
	 */
	public boolean checkPartentForMesg(String str) {
		String patternStr = "[0-9]{12}"; // 判断密码格式的正确性，只能是十二位数的数字。
		if (!str.matches(patternStr)) {
			return false;
		}
		return true;
	}

	/**
	 * 这个方法，是用来将从搜索处来的信息，将姓名学号等事先填好。
	 * 
	 * @param num
	 */
	public void setBaseInfo(String num) {
		String sql = "SELECT * FROM stu_info WHERE stu_num=" + num + ";";

		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			rs = stam.executeQuery(sql);
			while (rs.next()) {
				String stuName = rs.getString("stu_name");
				String stuNum = rs.getString("stu_num");
				String stuClass = rs.getString("stu_class");
				int down = rs.getInt("downgrade");

				this.jtfName.setText(stuName);
				this.jtfNum.setText(stuNum);
				this.jcomb.setSelectedItem(stuClass);
				this.jcombDown.setSelectedItem(down);
			}
		} catch (SQLException e) {
			System.out.println("------数据库连接出错480--------");
			e.printStackTrace();
		}
	}

	/**
	 * 设置各项分数，德育智育或者体育。
	 * 从而把某同学的各项成绩，各个选项，先加入到各个框里面。
	 * @param type
	 * @param num
	 */
	public void setItem(String type, String num) {

		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			if (type == "德育项") {
				// 填写德育项信息
				String sql = "SELECT * FROM stu_deyu_score WHERE stu_num="
						+ num + ";";
				rs = stam.executeQuery(sql);
				while (rs.next()) {
					String deyuNum = rs.getString("deyu_num");
					for (int i = 0; i < deyuPanel.getArrayJcb().size(); i++) {
						if (deyuPanel.getArrayDeyu().get(i).getDeyu_num()
								.equals(deyuNum)) {
							deyuPanel.getArrayJcb().get(i).setSelected(true);
						}
					}
				}
			} else if (type == "智育项") {
				// 填写智育项信息
				String sql = "SELECT * FROM stu_zhiyu_score WHERE stu_num="
						+ num + ";";
				rs = stam.executeQuery(sql);
				while (rs.next()) {
					String subjNum = rs.getString("subj_num");
					String score = rs.getString("stu_sub_score");
					for (int i = 0; i < zhiyuPanel.getArrayJtf().size(); i++) {
						if (zhiyuPanel.getArrayZhi().get(i).getSubj_num()
								.equals(subjNum)) {
							zhiyuPanel.getArrayJtf().get(i).setText(score + "");
						}
					}
				}
			} else if (type == "体育项") {
				// 填写体育项信息
				String sql = "SELECT * FROM stu_tiyu_score WHERE stu_num="
						+ num + ";";
				rs = stam.executeQuery(sql);
				while (rs.next()) {
					String tiyuNum = rs.getString("tiyu_num");
					for (int i = 0; i < tiyuPanel.getArrayJcb().size(); i++) {
						if (tiyuPanel.getArrayTiyu().get(i).getTiyu_num()
								.equals(tiyuNum)) {
							tiyuPanel.getArrayJcb().get(i).setSelected(true);
						}
					}
				}
			} else if (type == "四六级") {
				// 填写四六级项信息
				String sql = "SELECT * FROM stu_cet_score WHERE stu_num=" + num
						+ ";";
				rs = stam.executeQuery(sql);
				while (rs.next()) {
					String cet = rs.getString("type_name");
					if (cet.equals("0cet4")) {
						this.jrb[0].setSelected(true);
					}
					if (cet.equals("1cet4")) {
						this.jrb[1].setSelected(true);
					}
					if (cet.equals("2cet4")) {
						this.jrb[2].setSelected(true);
					}
					if (cet.equals("3cet4")) {
						this.jrb[3].setSelected(true);
					}
					if (cet.equals("cet6")) {
						jcb.setSelected(true);
					}

				}
			}
		} catch (SQLException e) {
			System.out.println("------数据库连接出错526--------");
			e.printStackTrace();
		}

	}

	/**
	 * 检测选择框的状态，如果被
	 * 改动了，就将确定框设为可按。
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		for (int i = 0; i < deyuPanel.getArrayJcb().size(); i++) {
			if (e.getSource() == deyuPanel.getArrayJcb().get(i)) {
				System.out.println("1");
				this.jb.setEnabled(true);
			}
		}
		for (int i = 0; i < tiyuPanel.getArrayJcb().size(); i++) {
			if (e.getSource() == tiyuPanel.getArrayJcb().get(i)) {
				System.out.println(2);
				this.jb.setEnabled(true);
			}
		}
		for (int i = 0; i < jrb.length; i++) {
			if (e.getSource() == jrb[i]) {
				System.out.println("3");
				this.jb.setEnabled(true);
			}
		}
		if (e.getSource() == jcb) {
			System.out.println("4");
			this.jb.setEnabled(true);
		}
		if (e.getSource() == jcomb) {
			System.out.println("5");
			this.jb.setEnabled(true);
		}
		if (e.getSource() == jcombDown) {
			System.out.println("6");
			this.jb.setEnabled(true);
		}
	}

	/**
	 * 重写这个方法，监听jtextfield，如果增加了，就会触发这个。
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		this.jb.setEnabled(true);
	}

	/**
	 * 重写这个方法，监听jtextfield，如果减少了。，就会触发这个。
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		this.jb.setEnabled(true);
	}

	/**
	 * 给出一个属性或一组属性更改的通知。
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		this.jb.setEnabled(true);
	}


	
}
