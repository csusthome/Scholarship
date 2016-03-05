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
			new MyNode("�����б�", "0"));
	private DefaultMutableTreeNode dmtn1 = new DefaultMutableTreeNode(
			new MyNode("�޸Ĺ���Ա����", "1"));
	private DefaultMutableTreeNode dmtn2 = new DefaultMutableTreeNode(
			new MyNode("�鿴ͳ�ƽ��", "2"));
	private DefaultMutableTreeNode dmtn3 = new DefaultMutableTreeNode(
			new MyNode("����ѧ����¼", "3"));
	private DefaultMutableTreeNode dmtn4 = new DefaultMutableTreeNode(
			new MyNode("��ӻ�ɾ����Ŀ������", "4"));
	private DefaultMutableTreeNode dmtn5 = new DefaultMutableTreeNode(
			new MyNode("����ѧ������", "5"));

	private DefaultMutableTreeNode dmtn7 = new DefaultMutableTreeNode(
			new MyNode("�˳�", "7"));


	private DefaultTreeModel dtm = new DefaultTreeModel(dmtnRoot); // �������ڵ�
	private JTree jt = new JTree(dtm); // ������״�б�ռ�
	private JScrollPane jspz = new JScrollPane(jt);
	private JPanel jpy = new JPanel(); // �������
	private JSplitPane jsp1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jspz,
			jpy); // �����ָ���


	
	private ChangePwd changePwd;
	private Result result;
	private AddStudentRec addStudentRec;
	private AddAndDeleteItems addAndDeleteItems;
	private SearchStu searchStu;

	CardLayout cl;
	
	//����½�ߴ�������
	private String adminName;

	public AdminFrame(String adminName) { // ���캯��
		this.adminName = adminName;
		initTree(); // ��ʼ����״�б�ռ�
		initPanel(); // ����������ģ�����
		initJpy(); // ��ʼ�����
		initFrame(); // ��ʼ��������
		addListener(); // Ϊ�ڵ�ע�������
	}

	private void initFrame() {
		this.add(jsp1); // ���ռ����
		jsp1.setDividerLocation(200); // ���÷ָ���λ�ã���߷ָ��ĳ�ʼ���
		jsp1.setDividerSize(4); // ���÷ָ��Ŀ�ȡ�
		Image image = new ImageIcon("file/admin.png").getImage(); // ����ͼƬ
		this.setIconImage(image); // ���ô����ͼ��
		this.setTitle("����Ա  " + adminName + "  �ͻ���");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // �õ���Ļ��Dimension����
		int centerX = screenSize.width / 2; // ����ƵĻ�м������
		int centerY = screenSize.height / 2;

		int w = 900;
		int h = 650;

		this.setBounds(centerX - w / 2, centerY - h / 2, w, h);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// �������󻯣�һ��ʼ������󻯳��֡�
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * ���ڽ������ͬ���Ӵ�����ӵ���Ӧ��
	 * ��ʽ�����У�������Ӧ��������
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
	 * ���ڸ������ռ�����¼���
	 */
	private void addListener() {
		jt.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				DefaultMutableTreeNode dmtntemp = (DefaultMutableTreeNode) jt
						.getLastSelectedPathComponent();// �õ���ǰѡ�нڵ����
				MyNode mynode = (MyNode) dmtntemp.getUserObject(); // �õ��Զ���ڵ����
				String id = mynode.getId();
				if (id.equals("0")) {
					/* ��ӭ���� */

				} else if (id.equals("7")) {
					int i = JOptionPane.showConfirmDialog(jpy, "��ȷ��Ҫ�˳�ϵͳô��",
							"ѯ��", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (i == 0) {
						System.exit(0);
					}
				} else if (id.equals("1")) {
					/* �޸Ĺ���Ա������� */

					cl.show(jpy, "changePwd"); // ����������ҳ����ʾ������
					changePwd.setFocusable(true);

					System.out.println("������޸Ĺ���Ա����");
				} else if (id.equals("2")) {
					/* �鿴ͳ�ƽ��ҳ�� */
					cl.show(jpy, "result");
					result.setFocusable(true);

				} else if (id.equals("3")) {
					/*����ѧ����¼ */
					cl.show(jpy, "addStudentRec");
					addStudentRec.setFocusable(true);

				} else if (id.equals("4")) {
					/* ��ӻ�ɾ����Ŀ���� */
					cl.show(jpy, "addAndDeleteItems");
					addAndDeleteItems.setFocusable(true);

				} else if (id.equals("5")) {
					/* ����ѧ���������� */
					cl.show(jpy, "searchStu");
					searchStu.setFocusable(true);

				} 

			}
		});
		jt.setToggleClickCount(1); // ��չ���ڵ����굥��������Ϊ1��
	}
	/**
	 * ���������������ʼ�������Ӵ��塣
	 */
	private void initPanel() {
		changePwd = new ChangePwd();
		result = new Result();
		searchStu = new SearchStu();
		addAndDeleteItems = new AddAndDeleteItems();
		addStudentRec = new AddStudentRec();

	}
	/**
	 * ��������������������ڵ�ӽ�ȥ��
	 */
	private void initTree() {
		//�����ڵ�ӽ�ȥ��
		dmtnRoot.add(dmtn1);
		dmtnRoot.add(dmtn2);
		dmtnRoot.add(dmtn3);
		dmtnRoot.add(dmtn4);
		dmtnRoot.add(dmtn5);
		dmtnRoot.add(dmtn7);
	}

	/**
	 * ������װ�����ڵ��id��values����������һһ��Ӧ��
	 * @author U-anLA
	 *
	 */
	class MyNode {
		private String values; // ����values����
		private String id; // ����ȷ���ڵ��id����

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
