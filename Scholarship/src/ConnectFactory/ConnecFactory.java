package ConnectFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
/**
 * 这个类是专用的连接工厂，每次要连接数据库时调用这个方法，就可以了
 * 
 * @author U-anLA
 *
 */
public class ConnecFactory {

	//mysql数据库的url路径
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/scholarship1";
	//用户名
	private static final String USER = "root";
	//密码
	private static final String PASSWORD = "123456";
	//得到驱动
	private static final String DRIVER = "com.mysql.jdbc.Driver";
/**
 * @see 获取数据库连接
 * @return 返回一个数据库连接
 */
	public static Connection getConnection() {
		try {
			//加载驱动
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL,USER,PASSWORD);

		} catch(com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException e){
			JOptionPane.showMessageDialog(null, "数据库连接失败", "错误", JOptionPane.ERROR_MESSAGE);
			System.out.println("太多数据库连接，失败！");
			return null;
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("没有找到相应的jdbc驱动，请检查后重试");
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("连接hair数据库失败，请检查后重试");
			return null;
		}
	}
/**
 * @see 关闭数据库连接对象，及相应的管道
 * @param conn 数据库连接
 * @param stam 处理，用来执行sql的
 * @param rs 结果集，select后可以用在次
 */
	public static void closeConnection(Connection conn, Statement stam,
			ResultSet rs) {

		//若不为空，则关闭
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
			System.out.println("数据库连接关闭失败");
		}

	}
}
