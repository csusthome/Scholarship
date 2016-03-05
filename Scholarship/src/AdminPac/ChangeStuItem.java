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
 * �����������������ҳ��Ľ���Ĵ��塣
 * ��ʵ��ǰ�����ѧ������һģһ��
 * @author U-anLA
 *
 */
public class ChangeStuItem extends JDialog implements ActionListener,
		ItemListener, DocumentListener {
	
	private JLabel jlSee = new JLabel();
	
	File file = new File("file/detail.xls");

	// �õ�ǰһ������Ϣ
	private SearchStu ss;
	private String stuNum;
	private AddStudentRec addStudentRec;

	JLabel jl1 = new JLabel("ѧ������:");
	JTextField jtfName = new JTextField();

	JLabel jl2 = new JLabel("ѧ��ѧ��:");
	JTextField jtfNum = new JTextField();

	JLabel jl3 = new JLabel("������:");

	JLabel[] jl = { new JLabel("������:"), new JLabel("�����"), new JLabel("�����"),
			new JLabel("�������") };
	JButton jb = new JButton("�ύ");

	
	// �������ݿ���ر���
	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	//��������ѡ��ڡ�
	private ZhiyuPanel zhiyuPanel;
	private JScrollPane jspZhiyu;

	private DeyuPanel deyuPanel;
	private JScrollPane jspDeyu;

	private TiyuPanel tiyuPanel;
	private JScrollPane jspTiyu;

	//�õ������������Ϣ
	private JRadioButton[] jrb = { new JRadioButton("û���ļ�"),
			new JRadioButton("��һ���ļ�"), new JRadioButton("������ļ�"),
			new JRadioButton("�������ļ�") };
	private ButtonGroup bg = new ButtonGroup();

	private JCheckBox jcb = new JCheckBox("�ѹ�����");

	//����༶ѡ���ͽ���ѡ���
	private JComboBox jcomb;
	private JComboBox jcombDown = new JComboBox();

	
	DecimalFormat decimalFormat=new DecimalFormat(".00");//���췽�����ַ���ʽ�������С������2λ,����0����.

	
	/**
	 * ���췽��
	 * @param ss ǰһ�����������
	 * @param owner ������
	 * @param stuNum ѧ��ѧ��
	 */
	public ChangeStuItem(SearchStu ss, Window owner, String stuNum) {
		super(owner);
		this.stuNum = stuNum;
		this.ss = ss;
		//��ʼ�����壬���ø���
		initFrame();
		//����item�����ҰѸ�����Ŀ�ӽ�ȥ��
		setItem("������", stuNum);
		setItem("������", stuNum);
		setItem("������", stuNum);
		setItem("������", stuNum);
		// �����Ǽ������ú�ĺ��棬��Ȼ����ʱҲ�ᴥ����
		addListeners();
	}

	/**
	 * ����¼�
	 */
	private void addListeners() {
		// ��deyupanel��ʱ������ӽ�����
		for (int i = 0; i < deyuPanel.getArrayJcb().size(); i++) {
			deyuPanel.getArrayJcb().get(i).addItemListener(this);
		}
		// ������panel�ӽ���
		for (int i = 0; i < tiyuPanel.getArrayJcb().size(); i++) {
			tiyuPanel.getArrayJcb().get(i).addItemListener(this);
		}
		// ���ļ���ӽ�����
		for (int i = 0; i < jrb.length; i++) {
			jrb[i].addItemListener(this);
		}
		// ��������ӽ���
		jcb.addItemListener(this);
		jcomb.addItemListener(this);
		jcombDown.addItemListener(this);
		jb.addActionListener(this);

		// ���jtextfield�ļ����¼���
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
	 * ��ʼ�����塣
	 */
	private void initFrame() {
		
		this.setLayout(null);

		// ���·����õ���󻯴��塣��Ȼ�ǳߴ��ϵģ���Ϊ���������󻯴��塣
		this.setLocation(0, 0);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // �õ���Ļ��Dimension����
		int centerX = screenSize.width; // ����ƵĻ�м������
		int centerY = screenSize.height;
		this.setSize(centerX, centerY);

		this.setTitle(stuNum);
		addStudentRec = new AddStudentRec();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(ChangeStuItem.this.jb.isEnabled()){
					int i = JOptionPane.showConfirmDialog(ChangeStuItem.this, "���Ѹ��ļ�¼δ���棬ȷ��Ҫ�˳��������޷����ģ�",
							"ѯ��", JOptionPane.YES_NO_OPTION,
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

		// ��ʼ���༶combbox
		initJcomb();

		// ��ʼ��������ѧ�ţ��༶�Ȼ�����Ϣ��
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
		// һ��ʼ�����ύ��ť��Ϊ���ɰ���ֱ���Ѿ��޸��ˣ�����Ϊtrue��
		jb.setEnabled(false);

		
		jlSee.setBounds(1050, 400, 100, 30);
		this.add(jlSee);
		//��ӳ�����
		setUrlLabel(jlSee,"�鿴����ϸ��",file);
		
		this.setVisible(true);

		
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
					JOptionPane.showMessageDialog(ChangeStuItem.this, "û���ҵ����ļ�", "����", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
					return;
				}
			}
		});
	}
	/**
	 * ������дprocessWindowEvent���Ϳ�����ֹ��ر��ˡ�Ҳ���ǿ���
	 * ���˳�ǰ��ѯ�ʡ�
	 */
    protected void processWindowEvent(WindowEvent e) {  
        if (e.getID() == WindowEvent.WINDOW_CLOSING) { 
        	if(this.jb.isEnabled()){
        		if (JOptionPane.showConfirmDialog(this, "ȷʵҪ�رգ�(��¼�Ѹ��ģ�δ���档)", "ȷ��", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {   
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
     * ��Ӹ����¼���
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jb) {
			boolean isOk = true;
			boolean isNameOk = true;
			// ���ѧ���Ƿ���

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

			for (int i = 0; i < zhiyuPanel.getArrayJtf().size(); i++) {
				if (!zhiyuPanel.getArrayJtf().get(i).getText().trim()
						.equals("")) {
					if (!this.checkPartentForScore(zhiyuPanel.getArrayJtf()
							.get(i).getText().trim())) {
						JOptionPane.showMessageDialog(this, "����ֻ������λ�����µ��������",
								"����", JOptionPane.ERROR_MESSAGE);
						isOk = false;
						return;
					}
				}
			}

			if (isOk == true) {
				int i = JOptionPane.showConfirmDialog(this, "��ȷ��Ҫ�޸�������¼ô��",
						"ѯ��", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (i == 0) {
					// �ȼ���ɾ��
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
							//�������ɾ���ɹ����Ϳ�ʼ������Ҫ�Ĳ��������
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
									JOptionPane.showMessageDialog(this, "�����ɹ�",
											"��ϲ", JOptionPane.OK_OPTION);
									this.jb.setEnabled(false);
								} else {
									JOptionPane.showMessageDialog(this, "����ʧ��",
											"��ʾ", JOptionPane.ERROR_MESSAGE);
								}

							}

						}

					} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
						JOptionPane.showMessageDialog(this, "��ѧ����¼�Ѵ��ڣ�", "����",
								JOptionPane.ERROR_MESSAGE);
						return;
						// e.printStackTrace();
					} catch (SQLException ex) {
						System.out.println("--------���ݿ����ӳ���������--------");
						ex.printStackTrace();
					} finally {
						ConnecFactory.closeConnection(conn, stam, rs);
					}

				}
			}

		}

	}

	/**
	 * �������������
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
	 * �õ���������
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
				score1 = Float.parseFloat(zhiyuPanel.getArrayJtf().get(i)
						.getText().trim())
						* zhiyuPanel.getArrayZhi().get(i).getSubj_score()
						+ score1;
				//��������ѧ�ּӽ�ȥ��
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

		//���ó���0.3
//		score2 = score2 * 0.3f;
		finalScore = score1 + score2;
		finalScore = finalScore * 0.05f;
		
		finalScore = Float.parseFloat(decimalFormat.format(finalScore));
		
		
		return finalScore;
	}

	/**
	 * ������Ŀ����Ҫ�ǵ�����������
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
	 * ��Ҫ�õ�����������
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
	 * ��ʼ��combobox
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
	 * ����ѧ����Ϣ��
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
	 * �ж������ʽ����ȷ�ԡ�
	 * @param str
	 * @return
	 */
	public boolean checkPartentForMesg(String str) {
		String patternStr = "[0-9]{12}"; // �ж������ʽ����ȷ�ԣ�ֻ����ʮ��λ�������֡�
		if (!str.matches(patternStr)) {
			return false;
		}
		return true;
	}

	/**
	 * �������������������������������Ϣ��������ѧ�ŵ�������á�
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
			System.out.println("------���ݿ����ӳ���480--------");
			e.printStackTrace();
		}
	}

	/**
	 * ���ø��������������������������
	 * �Ӷ���ĳͬѧ�ĸ���ɼ�������ѡ��ȼ��뵽���������档
	 * @param type
	 * @param num
	 */
	public void setItem(String type, String num) {

		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			if (type == "������") {
				// ��д��������Ϣ
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
			} else if (type == "������") {
				// ��д��������Ϣ
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
			} else if (type == "������") {
				// ��д��������Ϣ
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
			} else if (type == "������") {
				// ��д����������Ϣ
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
			System.out.println("------���ݿ����ӳ���526--------");
			e.printStackTrace();
		}

	}

	/**
	 * ���ѡ����״̬�������
	 * �Ķ��ˣ��ͽ�ȷ������Ϊ�ɰ���
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
	 * ��д�������������jtextfield����������ˣ��ͻᴥ�������
	 */
	@Override
	public void insertUpdate(DocumentEvent e) {
		this.jb.setEnabled(true);
	}

	/**
	 * ��д�������������jtextfield����������ˡ����ͻᴥ�������
	 */
	@Override
	public void removeUpdate(DocumentEvent e) {
		this.jb.setEnabled(true);
	}

	/**
	 * ����һ�����Ի�һ�����Ը��ĵ�֪ͨ��
	 */
	@Override
	public void changedUpdate(DocumentEvent e) {
		this.jb.setEnabled(true);
	}


	
}
