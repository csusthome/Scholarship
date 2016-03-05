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
 * 这个类是用来展示结果的，也就是排名结果的。
 * @author U-anLA
 *
 */
public class Result1 extends JPanel implements ActionListener {

	JLabel jl = new JLabel("统计结果如下：");
	JButton jb = new JButton("将此表导出为excel文件");
	JButton jbtRefress = new JButton("刷新");
	JScrollPane jsp, jspAhead;
	Vector<String> v_head = new Vector<String>();
	Vector<Vector> v_data = new Vector<Vector>();

	private Connection conn;
	private Statement stam;
	private ResultSet rs;

	private JTable jt;
	
	//创建一个文件选择类
	private JFileChooser jf = new JFileChooser();
	
	// 创建excel工作簿
	HSSFWorkbook workBook = new HSSFWorkbook();
	// 建一张表
	HSSFSheet sheet = workBook.createSheet("奖学金统计结果");
	
	//定义一个combobox，来选择班级
	


	public Result1() {
		initData();   //初始化数据
		initFrame();  //初始化窗体
		addListeners(); //为相应的控件添加相应的事件。
	}

	private void addListeners() {
		jb.addActionListener(this);
		jbtRefress.addActionListener(this);
	}

	private void initFrame() {
		// 将布局设为空布局
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
		v_head.add("序号");
		v_head.add("学号");
		v_head.add("姓名");
		v_head.add("德育分");
		v_head.add("智育分");
		v_head.add("体育分");
		v_head.add("总计");
		v_head.add("降低档次");

		try {
			conn = ConnecFactory.getConnection();
			stam = conn.createStatement();
			String sql = "SELECT stu_info.`stu_num`,stu_info.`stu_name`,Statistic.`stu_deyu`,Statistic.`stu_zhiyu`,Statistic.`stu_tiyu`,total.`total_score`,stu_info.`downgrade` FROM stu_info,Statistic,total WHERE stu_info.`stu_num`=Statistic.`stu_num` AND statistic.`stu_num`=total.`stu_num` ORDER BY(total.`total_score`) ASC; ";
			rs = stam.executeQuery(sql);
			while (rs.next()) {
				
				//创建表格
				/*
				 * 在读取数据库中的数据的同时，将表中数据存入内存， 到时候，在将它存入硬盘
				 */
				// 创建行
				HSSFRow row = sheet.createRow(i);
				// 创建格子，每一行的格子
				HSSFCell cell0 = row.createCell(0);
				HSSFCell cell1 = row.createCell(1);
				HSSFCell cell2 = row.createCell(2);
				HSSFCell cell3 = row.createCell(3);
				HSSFCell cell4 = row.createCell(4);
				HSSFCell cell5 = row.createCell(5);
				HSSFCell cell6 = row.createCell(6);
				HSSFCell cell7 = row.createCell(7);

				// 设置格子中存的内容，设为string类型
				cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
				//设置行宽    注意后面那个的单位很大通常为x*256
				//sheet.setColumnWidth(2, 6000);
				
				/*将日期格式用字符串读入
				 * 这样，既可以读到年月日，又可以读到时分秒
				 * 也就不用下面的处理
				 * */


				
					// 插入后面的
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
			System.out.println("--------数据库连接出错--------");
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {


		if (e.getSource() == jb) {
			// 建立过滤器
			FileNameExtensionFilter filter = new FileNameExtensionFilter("XLS",
					"xls");
			// 开始过滤
			jf.setFileFilter(filter);

			File file;
			if (jf.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				file = jf.getSelectedFile();
				System.out.println(file.getName());
				// 检查所命名文件是否存在
				if (file.exists()) {
					int copy = JOptionPane.showConfirmDialog(this, "是否覆盖当前文件？",
							"保存", JOptionPane.YES_NO_OPTION,
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
				 * 必须要在文件里写上一些东西才可以， 不然的话，虽然console里面可以打印出文件名称，
				 * 但是却不能在硬盘中绝对路径中找到相应的文件
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
 * 此函数，专门用来插入在excel中的表头
 * 也就是第一行
 */
	public void createZeroRow() {
		/*
		 * 此处为打印总表的 写入excel中的语句。
		 */
		// 建立到第n行的行
		HSSFRow row = sheet.createRow(0);
		// 创建单元格
		HSSFCell cell0 = row.createCell(0);
		HSSFCell cell1 = row.createCell(1);
		HSSFCell cell2 = row.createCell(2);
		HSSFCell cell3 = row.createCell(3);
		HSSFCell cell4 = row.createCell(4);
		HSSFCell cell5 = row.createCell(5);
		HSSFCell cell6 = row.createCell(6);
		HSSFCell cell7 = row.createCell(7);

		// 设置单元格内数值的格式
		cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell7.setCellType(HSSFCell.CELL_TYPE_STRING);

		// 设置第日期格的长度，在sheet中宏观设置
		sheet.setColumnWidth(1, 6000);

		cell0.setCellValue("序号");
		cell1.setCellValue("学号");
		cell2.setCellValue("姓名");
		cell3.setCellValue("德育分");
		cell4.setCellValue("智育分");
		cell5.setCellValue("体育分");
		cell6.setCellValue("总分");
		cell7.setCellValue("降低档次");

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
