package ConnectFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
/**
 * �������ר�õ����ӹ�����ÿ��Ҫ�������ݿ�ʱ��������������Ϳ�����
 * 
 * @author U-anLA
 *
 */
public class ConnecFactory {

	//mysql���ݿ��url·��
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/scholarship1";
	//�û���
	private static final String USER = "root";
	//����
	private static final String PASSWORD = "123456";
	//�õ�����
	private static final String DRIVER = "com.mysql.jdbc.Driver";
/**
 * @see ��ȡ���ݿ�����
 * @return ����һ�����ݿ�����
 */
	public static Connection getConnection() {
		try {
			//��������
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL,USER,PASSWORD);

		} catch(com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException e){
			JOptionPane.showMessageDialog(null, "���ݿ�����ʧ��", "����", JOptionPane.ERROR_MESSAGE);
			System.out.println("̫�����ݿ����ӣ�ʧ�ܣ�");
			return null;
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("û���ҵ���Ӧ��jdbc���������������");
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("����hair���ݿ�ʧ�ܣ����������");
			return null;
		}
	}
/**
 * @see �ر����ݿ����Ӷ��󣬼���Ӧ�Ĺܵ�
 * @param conn ���ݿ�����
 * @param stam ��������ִ��sql��
 * @param rs �������select��������ڴ�
 */
	public static void closeConnection(Connection conn, Statement stam,
			ResultSet rs) {

		//����Ϊ�գ���ر�
		try {
			if (conn != null) {
				conn.close();
			}
			if(stam != null){
				stam.close();
			}
			if(rs != null){
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("���ݿ����ӹر�ʧ��");
		}

	}
}
