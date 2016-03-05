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
	 * �ص��ӿ�
	 * @author U-anLA
	 *
	 * @param <E>
	 */
	protected interface Callback<E>{
		/**
		 * �ص�������ֻ����ʵ�ʵ�DAO���������ع���conn��pstm��rs����Դ�ͷŵ�����
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
     * 1���õ�����
     * 2��ƴװsql
     * 3����������
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
				//��װ�ɶ��󷵻�
				Object object = clazz.newInstance();
				if(rs.next()){
					//Ҫ�õ�rs���ص������ֶκ���Ϣ
					ResultSetMetaData rsmd = rs.getMetaData();
					//ע�������һ��ʼ����С�ڵ��ڣ�
					for(int i = 1;i <= rsmd.getColumnCount();i++){
						//ѭ���õ�����id
						String column = rsmd.getColumnName(i);
				//		System.out.println("column - > " + column);
						//ͨ��id�õ�id�ֶζ���
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
     * Ҳ���ǲ������
     */
	@Override
	public boolean save(final Object object) {
		Class clazz = object.getClass();
		String simpleName = clazz.getSimpleName();
	//	System.out.println("simpleName ->" + simpleName);
		final StringBuffer sql = new StringBuffer();
		sql.append(" insert into ").append(simpleName).append(" (");
		//��Ҫ��ȡ�������ԣ��ֶΣ�
		final Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			//��Ϊid���Զ������ģ�����Ҫ���˵�id
			if(!field.getName().equals("finalScore")){
				sql.append(field.getName()).append(",");
			}
		}
		//�Ƴ�β�˵ġ�����
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")").append(" values(");
		
		for(int i = 0;i < fields.length;i++){
			sql.append("?,");
			//����������֣�Ҫ����Դ���
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
	 * 1����ñ�����
	 * 2������sql
	 * 3��ִ��
	 */
	@Override
	public boolean delete(final Class clazz, final String id) {
		String simpleName = clazz.getSimpleName();
	//	System.out.println("simpleName -> " + simpleName);
		Field[] fields = clazz.getDeclaredFields();
		//���ڵȻ�Ҫ����������ʹ�����sql������ֻ��Я��final����
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
				//��װ�ɶ��󼯺Ϸ���
				List list = new ArrayList();
				ResultSetMetaData rsmd = rs.getMetaData();
				while(rs.next()){
					Object object = clazz.newInstance();
					//Ҫ�õ�rs���ص������ֶκ���Ϣ
					
					//ע�������һ��ʼ����С�ڵ��ڣ�
					for(int i = 1;i <= rsmd.getColumnCount();i++){
						//ѭ���õ�����id
						String column = rsmd.getColumnName(i);
				//		System.out.println("column - > " + column);
						//ͨ��id�õ�id�ֶζ���
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
		//��Ҫ��ȡ�������ԣ��ֶΣ�
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
	 * ���ݲ�����ģ�巽��
	 * @param callback
	 * @return ���ػص������ķ���ֵ
	 * @throws DataAccessException
	 *         ���лص��������ᱻ��װ�ɴ�����ʱ�쳣�����쳣extends RuntimeException
	 *         
	 *         �ֽ׶�����������ǰ��������˻�ȡ���Ӻ͹ر����ӵĶ�����֮��hair���Դ����������
	 *         
	 */       
	protected <E> E template(Callback<E> callback) throws DataAccessException {
		//��ȡ������Դ
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		conn = ConnecFactory.getConnection();
		try{
			//ִ��������CRUD�������û�ʵ��
			return callback.doInCallback(conn, pstm, rs);
		}catch (Throwable t){
			throw new DataAccessException(t);
		}finally{
			ConnecFactory.closeConnection(conn, pstm, rs);
		}
		
	}
	

}













