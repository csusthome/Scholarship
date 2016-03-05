package AdminPac;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ConnectFactory.ConnecFactory;

/**
 * �����������չʾ����ģ�Ҳ������������ġ�
 * @author U-anLA
 *
 */
public class Result1 extends JPanel implements ActionListener {

	JLabel jl = new JLabel("ͳ�ƽ�����£�");
	JButton jb = new JButton("���˱���Ϊexcel�ļ�");
	JButton jbtRefress = new JButton("ˢ��");
	JScrollPane jsp, jspAhead;
	Vector<String> v_head = new Vector<String>();
	Vector<Vector> v_data = new Vector<Vector>();

	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	private JTable jt;
	
	//����һ���ļ�ѡ����
	private JFileChooser jf = new JFileChooser();
	
	// ����excel������
	HSSFWorkbook workBook = new HSSFWorkbook();
	// ��һ�ű�
	HSSFSheet sheet = workBook.createSheet("��ѧ��ͳ�ƽ��");
	
	//����һ��combobox����ѡ��༶
	


	public Result1() {
		initData();   //��ʼ������
		initFrame();  //��ʼ������
		addListeners(); //Ϊ��Ӧ�Ŀؼ������Ӧ���¼���
	}

	private void addListeners() {
		jb.addActionListener(this);
		jbtRefress.addActionListener(this);
	}

	private void initFrame() {
		// ��������Ϊ�ղ���
		this.setLayout(null);

		jl.setBounds(25, 10, 100, 30);
		this.add(jl);
		
		jb.setBounds(30, 480, 200, 30);
		this.add(jb);
		
		jbtRefress.setBounds(730, 480, 100, 30);
		this.add(jbtRefress);

	}

	private void initData() {
		int i = 1;
		this.createZeroRow();
		
		if (v_data != null) {
			v_data.clear();
		}
		if (v_head != null) {
			v_head.clear();
		}
		v_head.add("���");
		v_head.add("ѧ��");
		v_head.add("����");
		v_head.add("������");
		v_head.add("������");
		v_head.add("������");
		v_head.add("�ܼ�");
		v_head.add("���͵���");

		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			String sql = "SELECT stu_info.`stu_num`,stu_info.`stu_name`,Statistic.`stu_deyu`,Statistic.`stu_zhiyu`,Statistic.`stu_tiyu`,total.`total_score`,stu_info.`downgrade` FROM stu_info,Statistic,total WHERE stu_info.`stu_num`=Statistic.`stu_num` AND statistic.`stu_num`=total.`stu_num` ORDER BY(total.`total_score`) ASC; ";
			rs = stam.executeQuery(sql);
			while (rs.next()) {
				
				//�������
				/*
				 * �ڶ�ȡ���ݿ��е����ݵ�ͬʱ�����������ݴ����ڴ棬 ��ʱ���ڽ�������Ӳ��
				 */
				// ������
				HSSFRow row = sheet.createRow(i);
				// �������ӣ�ÿһ�еĸ���
				HSSFCell cell0 = row.createCell(0);
				HSSFCell cell1 = row.createCell(1);
				HSSFCell cell2 = row.createCell(2);
				HSSFCell cell3 = row.createCell(3);
				HSSFCell cell4 = row.createCell(4);
				HSSFCell cell5 = row.createCell(5);
				HSSFCell cell6 = row.createCell(6);
				HSSFCell cell7 = row.createCell(7);

				// ���ø����д�����ݣ���Ϊstring����
				cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
				//�����п�    ע������Ǹ��ĵ�λ�ܴ�ͨ��Ϊx*256
				//sheet.setColumnWidth(2, 6000);
				
				/*�����ڸ�ʽ���ַ�������
				 * �������ȿ��Զ��������գ��ֿ��Զ���ʱ����
				 * Ҳ�Ͳ�������Ĵ���
				 * */


				
					// ��������
					cell0.setCellValue(i);
					cell1.setCellValue(rs.getString("stu_num"));
					cell2.setCellValue(rs.getString("stu_name"));
					cell3.setCellValue(rs.getString("stu_deyu"));
					cell4.setCellValue(rs.getString("stu_zhiyu"));
					cell5.setCellValue(rs.getString("stu_tiyu"));
					cell6.setCellValue(rs.getString("total_score"));
					cell7.setCellValue(rs.getString("downgrade"));
				

				
				Vector v = new Vector();
				v.add(i);
				v.add(rs.getString("stu_num"));
				v.add(rs.getString("stu_name"));
				v.add(rs.getString("stu_deyu"));
				v.add(rs.getString("stu_zhiyu"));
				v.add(rs.getString("stu_tiyu"));
				v.add(rs.getString("total_score"));
				v.add(rs.getString("downgrade"));
				v_data.add(v);
				i++;
			}

			jt = new JTable(v_data, v_head);

			jsp = new JScrollPane(jt);
			jsp.setBounds(30, 60, 800, 400);
			this.add(jsp);

		} catch (SQLException e) {
			System.out.println("--------���ݿ����ӳ���--------");
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {


		if (e.getSource() == jb) {
			// ����������
			FileNameExtensionFilter filter = new FileNameExtensionFilter("XLS",
					"xls");
			// ��ʼ����
			jf.setFileFilter(filter);

			File file;
			if (jf.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				file = jf.getSelectedFile();
				System.out.println(file.getName());
				// ����������ļ��Ƿ����
				if (file.exists()) {
					int copy = JOptionPane.showConfirmDialog(this, "�Ƿ񸲸ǵ�ǰ�ļ���",
							"����", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (copy == JOptionPane.YES_OPTION) {
						jf.approveSelection();
					}else if(copy == JOptionPane.NO_OPTION){
						return;
					}
				} else {
					jf.approveSelection();
				}

				/*
				 * ����Ҫ���ļ���д��һЩ�����ſ��ԣ� ��Ȼ�Ļ�����Ȼconsole������Դ�ӡ���ļ����ƣ�
				 * ����ȴ������Ӳ���о���·�����ҵ���Ӧ���ļ�
				 */
				try {
					FileOutputStream fos = new FileOutputStream(file);
					workBook.write(fos);
					fos.flush();
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}else if(e.getSource() == jbtRefress){
			this.initData();
		}

	}
/**
 * �˺�����ר������������excel�еı�ͷ
 * Ҳ���ǵ�һ��
 */
	public void createZeroRow() {
		/*
		 * �˴�Ϊ��ӡ�ܱ�� д��excel�е���䡣
		 */
		// ��������n�е���
		HSSFRow row = sheet.createRow(0);
		// ������Ԫ��
		HSSFCell cell0 = row.createCell(0);
		HSSFCell cell1 = row.createCell(1);
		HSSFCell cell2 = row.createCell(2);
		HSSFCell cell3 = row.createCell(3);
		HSSFCell cell4 = row.createCell(4);
		HSSFCell cell5 = row.createCell(5);
		HSSFCell cell6 = row.createCell(6);
		HSSFCell cell7 = row.createCell(7);

		// ���õ�Ԫ������ֵ�ĸ�ʽ
		cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell7.setCellType(HSSFCell.CELL_TYPE_STRING);

		// ���õ����ڸ�ĳ��ȣ���sheet�к������
		sheet.setColumnWidth(1, 6000);

		cell0.setCellValue("���");
		cell1.setCellValue("ѧ��");
		cell2.setCellValue("����");
		cell3.setCellValue("������");
		cell4.setCellValue("������");
		cell5.setCellValue("������");
		cell6.setCellValue("�ܷ�");
		cell7.setCellValue("���͵���");

	}


	public static void main(String[] args) {
		JFrame jf = new JFrame();
		Result1 r = new Result1();
		jf.setLocation(400, 300);
		jf.setSize(800, 600);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(r);
		jf.setVisible(true);
	}

}
