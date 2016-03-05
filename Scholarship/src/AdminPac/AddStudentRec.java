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
 * �������ѧ����Ϣ��¼�������壬  ������һ��Ҫ�ģ�����
 * 
 * @author U-anLA
 * 
 */
public class AddStudentRec extends JPanel implements ActionListener  {
	
	JLabel jlSee = new JLabel();

	JLabel jl1 = new JLabel("ѧ������:");
	JTextField jtfName = new JTextField();

	JLabel jl2 = new JLabel("ѧ��ѧ��:");
	JTextField jtfNum = new JTextField();

	JLabel jl3 = new JLabel("������:");

	JLabel[] jl = { new JLabel("������:"), new JLabel("�����"), new JLabel("�����"),
			new JLabel("�������") };
	JButton jb = new JButton("�ύ");
	JButton jbReset = new JButton("����");

	// �������ݿ���ر���
	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	
	
	//����panel��������ʾ������ֵ�
	private ZhiyuPanel zhiyuPanel;
	private JScrollPane jspZhiyu;

	private DeyuPanel deyuPanel;
	private JScrollPane jspDeyu;

	private TiyuPanel tiyuPanel;
	private JScrollPane jspTiyu;
	
	File file = new File("file/detail.xls");

	DecimalFormat decimalFormat=new DecimalFormat(".00");//���췽�����ַ���ʽ�������С������2λ,����0����.
//	String p=decimalFomat.format(price);//format ���ص����ַ���
	
	
	//����������ѡ�����ѡ��ġ�
	private JRadioButton[] jrb = { new JRadioButton("û���ļ�"),
			new JRadioButton("��һ���ļ�"), new JRadioButton("������ļ�"),
			new JRadioButton("�������ļ�") };
	//����ѡ����������ļ�ѡ��ѡ��һ��
	private ButtonGroup bg = new ButtonGroup();

	private JCheckBox jcb = new JCheckBox("�ѹ�����");
	
	//����༶combobox��
	private JComboBox jcomb;
	//���彵����combobox��
	private JComboBox jcombDown = new JComboBox();

	/**
	 * ���ѧ���Ĺ��췽����
	 */
	public AddStudentRec() {
		initFrame();
		addListeners();

	}

	/**
	 * ������Ӽ����¼�
	 */
	private void addListeners() {
		jb.addActionListener(this);
		jbReset.addActionListener(this);
	}

	
	/**
	 * ��ʼ������ķ���
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
		//��ӳ�����
		setUrlLabel(jlSee,"�鿴����ϸ��",file);
		
	}
	

	/**
	 * ��������������ó����ӵġ�
	 * @param jl
	 * @param text
	 * @param f
	 */
	public void setUrlLabel(JLabel jl,String text,final File f){
		jl.setText(text);
		jl.setForeground(Color.BLUE);
		//����Ϊ�������
		jl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jl.addMouseListener(new MouseAdapter() {
			
			//��д����¼�������
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().open(f);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(AddStudentRec.this, "û���ҵ����ļ�", "����", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					return;
				}
			}
		});
	}

	/**
	 * ��д�ģ��¼�������
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb) {
			boolean isOk = true;
			boolean isNameOk = true;
			// ���ѧ���Ƿ���

			//�жϻ�����Ϣ�����Ƿ�Ϊ��
			if (this.jtfName.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "����������", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (this.jtfNum.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(this, "������ѧ��", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!this.checkPartentForMesg(jtfNum.getText().trim())) {
				JOptionPane.showMessageDialog(this, "ѧ��ֻ����12λ�Ĵ�����", "����",
						JOptionPane.ERROR_MESSAGE);
				isNameOk = false;
				return;
			}
			//����ѭ������������������Ƿ�������Ϲ��
			//��zhiyupanel�л�ø�������
			for (int i = 0; i < zhiyuPanel.getArrayJtf().size(); i++) {
				if (!zhiyuPanel.getArrayJtf().get(i).getText().trim()
						.equals("")) {
					if (!this.checkPartentForScore(zhiyuPanel.getArrayJtf()
							.get(i).getText().trim())) {
						JOptionPane.showMessageDialog(this, "����ֻ������λ�����µ��������",
								"����", JOptionPane.ERROR_MESSAGE);
						//�����һ�����ԣ����˳�������Ϊ��ok
						isOk = false;
						return;
					}
				}
			}

			//���������
			if (isOk == true) {
				int i = JOptionPane.showConfirmDialog(this, "��ȷ��Ҫ����������¼ô��",
						"ѯ��", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (i == 0) {
					//����sql��䡣
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
						//���ѧ����Ϣ�Ѿ��ɹ������ȥ����ִ�н������Ĺ�����
						if (target == 1) {
							
//							conn = ConnecFactory.getConnection();
							Statement stam2 = conn.createStatement();

							//����Ԥ��Ϊ0��
							float zhiyu = 0.0f;
							float deyu = 0.0f;
							float tiyu = 0.0f;
							
							//ͨ��������ø��������
							zhiyu = this.getZhiyuScore();
							deyu = this.getDeyuScore();
							tiyu = this.getTiyuScore();
							
							//�������������������ֵ����ֻ��Ϊ���
							if (zhiyu  > 70.0f) {
								zhiyu = 70.0f;
							}
							if (deyu > 25.0f) {
								deyu = 25.0f;
							}
							if (tiyu > 5.0f) {
								tiyu = 5.0f;
							}
							
							//����sql��䣬���������ɼ����뵽���ݱ���
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
							
							
							//�������ĳɹ���ʧ����������Ӧ����ʾ��
							if (target2 == 1) {
								JOptionPane.showMessageDialog(this, "�����ɹ�",
										"��ϲ", JOptionPane.OK_OPTION);
							} else {
								JOptionPane.showMessageDialog(this, "����ʧ��",
										"����", JOptionPane.ERROR_MESSAGE);

							}

						}
					} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
						JOptionPane.showMessageDialog(this, "��ѧ����¼�Ѵ��ڣ�", "����",
								JOptionPane.ERROR_MESSAGE);
						return;
						// e.printStackTrace();
					} catch (SQLException ex) {
						System.out.println("-------------347-------------");
						System.out.println("--------���ݿ����ӳ���������--------");
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
	 * �����ݿ����ڻ������������
	 * @return �����ķ�����
	 */
	public float getZhiyuScore() {
		float finalScore = 0.0f;
		float score = 0.0f;
		float num = 0.0f;
		for (int i = 0; i < zhiyuPanel.getArrayJtf().size(); i++) {
			if (!zhiyuPanel.getArrayJtf().get(i).getText().trim().equals("")) {
				if(zhiyuPanel.getArrayZhi().get(i).getTarget() == 0){
				//���������
					score = Float.parseFloat(zhiyuPanel.getArrayJtf().get(i)
							.getText().trim())
							* zhiyuPanel.getArrayZhi().get(i).getSubj_score()
							+ score;
					num += zhiyuPanel.getArrayZhi().get(i).getSubj_score();
				}
				
				// ��Ҫ�������ݡ�

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
		//������������������������Ϣ���ȥ��
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
	 * ��õ�������
	 * @return
	 */
	public float getDeyuScore() {
		float score = 0.0f;

		for (int i = 0; i < deyuPanel.getArrayJcb().size(); i++) {
			if (deyuPanel.getArrayJcb().get(i).isSelected()) {
				//��������������
				score = deyuPanel.getArrayDeyu().get(i).getDeyu_score() + score;
				//�����ݲ����ȥ��
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
	 * �õ�����������
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
				
				//��������������
				score1 = Float.parseFloat(zhiyuPanel.getArrayJtf().get(i)
						.getText().trim())
						* zhiyuPanel.getArrayZhi().get(i).getSubj_score()
						+ score1;
				//�ٽ���������ӽ�ȥ��
				num += zhiyuPanel.getArrayZhi().get(i).getSubj_score();

			}
		}
		if(num == 0.0f){
			return 0.0f;
		}
		score1 = (score1/num) * 0.7f;
		//������������ȥ��
		for (int i = 0; i < tiyuPanel.getArrayJcb().size(); i++) {
			if (tiyuPanel.getArrayJcb().get(i).isSelected()) {
				score2 = tiyuPanel.getArrayTiyu().get(i).getTiyu_score() + score2;
				this.insertItem("stu_tiyu_score", jtfNum.getText().trim(),
						"tiyu_num", tiyuPanel.getArrayTiyu().get(i)
								.getTiyu_num());
			}
		}
		//���ó���0.3
//		score2 = score2*0.3f;
		finalScore = score1 + score2;
		
		finalScore *= 0.05f;
		
		finalScore = Float.parseFloat(decimalFormat.format(finalScore));
		
		
		return finalScore;
	}

	
	/**
	 * ���ڲ���������������ĸ��
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
				System.out.println("****222222222222����ɹ�*****");
			}
		} catch (SQLException e) {
			System.out.println("--------���ݿ����ӳ���������--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}

	}

	/**
	 * ���ڲ���������ĸ������
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
				System.out.println("****����ɹ�*****");
			}
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(this, "��ѧ����¼�Ѵ��ڣ�", "����",
					JOptionPane.ERROR_MESSAGE);
			return;
			// e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("--------���ݿ����ӳ���������--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}

	}

	/**
	 * ���ڳ�ʼ���༶combobox��
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
			System.out.println("--------���ݿ����ӳ���������--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}
	}

	
	/**
	 * ���ڲ���ѧ���������ݲ��롣
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
				System.out.println("****����ɹ�*****");
			}
		} catch (SQLException e) {
			System.out.println("--------���ݿ����ӳ���������--------");
			e.printStackTrace();
		} finally {
			ConnecFactory.closeConnection(conn, stam, rs);
		}
	}

	/**
	 * �ⷽ����������������ĺϷ��Եģ�
	 * 
	 * @param str
	 *            ������ַ���
	 * @return ���ؽ��������
	 */
	public boolean checkPartentForScore(String str) {
		String patternStr = "[0-9]{0,2}"; // �ж������ʽ����ȷ�ԣ�ֻ������λ�������֡�
		if (!str.matches(patternStr)) {
			return false;
		}
		return true;
	}

	/**
	 * ���ѧ�ŵ���ȷ�ԣ�
	 * @param str
	 * @return
	 */
	public boolean checkPartentForMesg(String str) {
		String patternStr = "[0-9]{12}"; // �ж�ѧ�Ÿ�ʽ����ȷ�ԣ�ֻ����ʮ��λ�������֡�
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
