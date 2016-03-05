package BaseDao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.PresentationDirection;

import ConnectFactory.ConnecFactory;

public class BasicDaoImpl implements BasicDAO{
	/**
	 * 回调接口
	 * @author U-anLA
	 *
	 * @param <E>
	 */
	protected interface Callback<E>{
		/**
		 * 回调方法，只处理实际的DAO操作，不必关心conn，pstm和rs的资源释放等问题
		 * @param conn
		 * @param pstm
		 * @param rs
		 * @return
		 * @throws Throwable
		 */
		E doInCallback(Connection conn,PreparedStatement pstm,ResultSet rs)
		throws Throwable;
	}
    /**
     * 1、拿到表名
     * 2、拼装sql
     * 3、处理结果集
     */
	@Override
	public Object get(final Class clazz, final String id) {
		
		String simpleName = clazz.getSimpleName();
	//	System.out.println("simpleName -> " + simpleName);
		final StringBuffer sql = new StringBuffer();
		Field[] fields = clazz.getDeclaredFields();
		sql.append(" select ").append(fields[0].getName());
		for(int i = 1; i < fields.length;i++){
			if(!fields[i].getName().equals("finalScore")){
			sql.append(",").append(fields[i].getName());
			}
		}
		sql.append(" from ").append(simpleName);
		sql.append(" where ").append(fields[0].getName()).append(" = ? ");

	//	System.out.println("SQL -> " + sql.toString());
		
		return template(new Callback<Object>() {

			@Override
			public Object doInCallback(Connection conn, PreparedStatement pstm,
					ResultSet rs) throws Throwable {
				pstm = conn.prepareStatement(sql.toString());
				pstm.setObject(1, id);
				rs = pstm.executeQuery();
				//封装成对象返回
				Object object = clazz.newInstance();
				if(rs.next()){
					//要得到rs返回的所有字段和信息
					ResultSetMetaData rsmd = rs.getMetaData();
					//注意这里从一开始，且小于等于！
					for(int i = 1;i <= rsmd.getColumnCount();i++){
						//循环拿到列名id
						String column = rsmd.getColumnName(i);
				//		System.out.println("column - > " + column);
						//通过id拿到id字段对象
						Field f = clazz.getDeclaredField(column);
						if(!f.isAccessible()){
							f.setAccessible(true);
						}
						f.set(object,rs.getObject(column));
					}
				}
				return object;
			}
		});
	}
    /**
     * 也就是插入语句
     */
	@Override
	public boolean save(final Object object) {
		Class clazz = object.getClass();
		String simpleName = clazz.getSimpleName();
	//	System.out.println("simpleName ->" + simpleName);
		final StringBuffer sql = new StringBuffer();
		sql.append(" insert into ").append(simpleName).append(" (");
		//需要获取所有属性（字段）
		final Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			//因为id是自动增长的，所以要过滤掉id
			if(!field.getName().equals("finalScore")){
				sql.append(field.getName()).append(",");
			}
		}
		//移除尾端的“，”
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")").append(" values(");
		
		for(int i = 0;i < fields.length;i++){
			sql.append("?,");
			//如果是智育分，要区别对待。
			if(simpleName.equals("ZhiyuItem")){
				if(i == fields.length -2){
					break;
				}
			}
		}
		

		
		
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		System.out.println("sql -> " + sql.toString());
		
		return template(new Callback<Boolean>() {

			@Override
			public Boolean doInCallback(Connection conn,
					PreparedStatement pstm, ResultSet rs) throws Throwable {
				pstm = conn.prepareStatement(sql.toString());
				int index = 1;
				for(Field field : fields){
					if(!field.getName().equals("finalScore")){
						if(!field.isAccessible()){
							field.setAccessible(true);
						}
						pstm.setObject(index, field.get(object));
						index ++;
					}
				}
				int row = pstm.executeUpdate();
				
				return row != 0 ? true : false ;
			}
		});
	}

	/**
	 * 1、获得表名，
	 * 2、构建sql
	 * 3、执行
	 */
	@Override
	public boolean delete(final Class clazz, final String id) {
		String simpleName = clazz.getSimpleName();
	//	System.out.println("simpleName -> " + simpleName);
		Field[] fields = clazz.getDeclaredFields();
		//由于等会要在匿名类中使用这个sql，所以只能携程final类型
		final StringBuffer sql = new StringBuffer();
		sql.append(" delete from ").append(simpleName);
		sql.append(" where ").append(fields[0].getName()).append(" = ? ");
		//sql.append(" where id = ?");
		System.out.println("SQL -> " + sql.toString());
		return template(new Callback<Boolean>() {

			@Override
			public Boolean doInCallback(Connection conn,
					PreparedStatement pstm, ResultSet rs) throws Throwable {
				pstm = conn.prepareStatement(sql.toString());
				pstm.setObject(1, id);
				int row = pstm.executeUpdate();
				return row !=0 ? true : false;
			}
		});
	}

	@Override
	public List<? extends Object> findAll(final Class clazz) {
		String simpleName = clazz.getSimpleName();
	//	System.out.println("simpleName -> " + simpleName);
		final StringBuffer sql = new StringBuffer();
		Field[] fields = clazz.getDeclaredFields();
		sql.append(" select ").append(fields[0].getName());
		for(int i = 1; i < fields.length;i++){
			if(!fields[i].getName().equals("finalScore")){
				sql.append(",").append(fields[i].getName());

			}
		}
		sql.append(" from ").append(simpleName);
	//	System.out.println("SQL -> " + sql.toString());
		
		return template(new Callback<List<? extends Object>>() {

			@Override
			public List<? extends Object> doInCallback(Connection conn, PreparedStatement pstm,
					ResultSet rs) throws Throwable {
				pstm = conn.prepareStatement(sql.toString());
				rs = pstm.executeQuery();
				//封装成对象集合返回
				List list = new ArrayList();
				ResultSetMetaData rsmd = rs.getMetaData();
				while(rs.next()){
					Object object = clazz.newInstance();
					//要得到rs返回的所有字段和信息
					
					//注意这里从一开始，且小于等于！
					for(int i = 1;i <= rsmd.getColumnCount();i++){
						//循环拿到列名id
						String column = rsmd.getColumnName(i);
				//		System.out.println("column - > " + column);
						//通过id拿到id字段对象
						Field f = clazz.getDeclaredField(column);
						if(!f.isAccessible()){
							f.setAccessible(true);
						}
						f.set(object,rs.getObject(column));
					}
					list.add(object);
				}
				return list;
			}
		});
	}

	@Override
	public boolean updateScore(final Object object) {
		Class clazz = object.getClass();
		String simpleName = clazz.getSimpleName();
	//	System.out.println("simpleName ->" + simpleName);
		final StringBuffer sql = new StringBuffer();
		sql.append(" update ").append(simpleName).append(" set ");
		//需要获取所有属性（字段）
		final Field[] fields = clazz.getDeclaredFields();
		sql.append(fields[2].getName()).append(" = ? ");
		sql.append("where ").append(fields[0].getName()).append(" = ?");

		System.out.println("sql -> " + sql.toString());
		
		return template(new Callback<Boolean>() {

			@Override
			public Boolean doInCallback(Connection conn,
					PreparedStatement pstm, ResultSet rs) throws Throwable {
				pstm = conn.prepareStatement(sql.toString());
		
				
				
				if(!fields[2].isAccessible()){
					fields[2].setAccessible(true);
				}
				if(!fields[0].isAccessible()){
					fields[0].setAccessible(true);
				}
				
				pstm.setObject(1, fields[2].get(object));
				pstm.setObject(2, fields[0].get(object));
		
				int row = pstm.executeUpdate();
				
				return row != 0 ? true : false ;
			}
		});
	}
	
	/**
	 * 数据操作的模板方法
	 * @param callback
	 * @return 返回回调方法的返回值
	 * @throws DataAccessException
	 *         所有回调方法都会被包装成此运行时异常，该异常extends RuntimeException
	 *         
	 *         现阶段这个方法就是帮我们做了获取链接和关闭连接的动作，之后hair可以处理事务操作
	 *         
	 */       
	protected <E> E template(Callback<E> callback) throws DataAccessException {
		//获取数据资源
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		conn = ConnecFactory.getConnection();
		try{
			//执行真正的CRUD，留给用户实现
			return callback.doInCallback(conn, pstm, rs);
		}catch (Throwable t){
			throw new DataAccessException(t);
		}finally{
			ConnecFactory.closeConnection(conn, pstm, rs);
		}
		
	}
	

}













