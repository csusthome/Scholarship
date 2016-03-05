package AdminPac;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class AdminFrame extends JFrame {

	private DefaultMutableTreeNode dmtnRoot = new DefaultMutableTreeNode(
			new MyNode("功能列表", "0"));
	private DefaultMutableTreeNode dmtn1 = new DefaultMutableTreeNode(
			new MyNode("修改管理员密码", "1"));
	private DefaultMutableTreeNode dmtn2 = new DefaultMutableTreeNode(
			new MyNode("查看统计结果", "2"));
	private DefaultMutableTreeNode dmtn3 = new DefaultMutableTreeNode(
			new MyNode("增加学生记录", "3"));
	private DefaultMutableTreeNode dmtn4 = new DefaultMutableTreeNode(
			new MyNode("添加或删除项目级更改", "4"));
	private DefaultMutableTreeNode dmtn5 = new DefaultMutableTreeNode(
			new MyNode("更改学生分数", "5"));

	private DefaultMutableTreeNode dmtn7 = new DefaultMutableTreeNode(
			new MyNode("退出", "7"));


	private DefaultTreeModel dtm = new DefaultTreeModel(dmtnRoot); // 创建根节点
	private JTree jt = new JTree(dtm); // 创建树状列表空间
	private JScrollPane jspz = new JScrollPane(jt);
	private JPanel jpy = new JPanel(); // 创建面板
	private JSplitPane jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jspz,
			jpy); // 创建分割表格。


	
	private ChangePwd changePwd;
	private Result result;
	private AddStudentRec addStudentRec;
	private AddAndDeleteItems addAndDeleteItems;
	private SearchStu searchStu;

	CardLayout cl;
	
	//将登陆者传进来。
	private String adminName;

	public AdminFrame(String adminName) { // 构造函数
		this.adminName = adminName;
		initTree(); // 初始化树状列表空间
		initPanel(); // 创建各功能模块对象
		initJpy(); // 初始化面板
		initFrame(); // 初始化主窗体
		addListener(); // 为节点注册监听器
	}

	private void initFrame() {
		this.add(jsp1); // 将空间添加
		jsp1.setDividerLocation(200); // 设置分割窗体的位置，左边分割窗体的初始宽度
		jsp1.setDividerSize(4); // 设置分割窗体的宽度。
		Image image = new ImageIcon("file/admin.png").getImage(); // 加载图片
		this.setIconImage(image); // 设置窗体的图标
		this.setTitle("管理员  " + adminName + "  客户端");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的Dimension对象
		int centerX = screenSize.width / 2; // 计算频幕中间的像素
		int centerY = screenSize.height / 2;

		int w = 900;
		int h = 650;

		this.setBounds(centerX - w / 2, centerY - h / 2, w, h);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 这个是最大化，一开始就是最大化出现。
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * 用于将多个不同的子窗体添加到相应的
	 * 卡式布局中，并且相应的命名。
	 */
	private void initJpy() {
		jpy.setLayout(new CardLayout());
		cl = (CardLayout) jpy.getLayout();
		jpy.add(changePwd, "changePwd");
		jpy.add(result, "result");
		jpy.add(addStudentRec, "addStudentRec");
		jpy.add(addAndDeleteItems, "addAndDeleteItems");
		jpy.add(searchStu, "searchStu");

    
	}
	/**
	 * 用于给各个空间添加事件。
	 */
	private void addListener() {
		jt.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				DefaultMutableTreeNode dmtntemp = (DefaultMutableTreeNode) jt
						.getLastSelectedPathComponent();// 得到当前选中节点对象
				MyNode mynode = (MyNode) dmtntemp.getUserObject(); // 得到自定义节点对象
				String id = mynode.getId();
				if (id.equals("0")) {
					/* 欢迎界面 */

				} else if (id.equals("7")) {
					int i = JOptionPane.showConfirmDialog(jpy, "您确认要退出系统么？",
							"询问", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (i == 0) {
						System.exit(0);
					}
				} else if (id.equals("1")) {
					/* 修改管理员密码界面 */

					cl.show(jpy, "changePwd"); // 将更改密码页面显示出来。
					changePwd.setFocusable(true);

					System.out.println("点击了修改管理员界面");
				} else if (id.equals("2")) {
					/* 查看统计结果页面 */
					cl.show(jpy, "result");
					result.setFocusable(true);

				} else if (id.equals("3")) {
					/*增加学生记录 */
					cl.show(jpy, "addStudentRec");
					addStudentRec.setFocusable(true);

				} else if (id.equals("4")) {
					/* 添加或删除项目界面 */
					cl.show(jpy, "addAndDeleteItems");
					addAndDeleteItems.setFocusable(true);

				} else if (id.equals("5")) {
					/* 更改学生分数界面 */
					cl.show(jpy, "searchStu");
					searchStu.setFocusable(true);

				} 

			}
		});
		jt.setToggleClickCount(1); // 将展开节点的鼠标单击次数设为1；
	}
	/**
	 * 这个方法是用来初始化各个子窗体。
	 */
	private void initPanel() {
		changePwd = new ChangePwd();
		result = new Result();
		searchStu = new SearchStu();
		addAndDeleteItems = new AddAndDeleteItems();
		addStudentRec = new AddStudentRec();

	}
	/**
	 * 这个方法是用来将各个节点加进去。
	 */
	private void initTree() {
		//将各节点加进去。
		dmtnRoot.add(dmtn1);
		dmtnRoot.add(dmtn2);
		dmtnRoot.add(dmtn3);
		dmtnRoot.add(dmtn4);
		dmtnRoot.add(dmtn5);
		dmtnRoot.add(dmtn7);
	}

	/**
	 * 这个类封装了树节点的id和values，这样可以一一对应。
	 * @author U-anLA
	 *
	 */
	class MyNode {
		private String values; // 声明values属性
		private String id; // 用于确定节点的id属性

		public MyNode(String values, String id) {
			this.values = values;
			this.id = id;
		}

		public String toString() {
			return this.values;
		}

		public String getId() {
			return this.id;
		}
	}

//	public static void main(String[] args) {
//		AdminFrame af = new AdminFrame();
//		af.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
}
