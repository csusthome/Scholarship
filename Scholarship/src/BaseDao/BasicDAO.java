package BaseDao;
import java.util.List;

/**
 * �����ƣ�������һ��<E>�ķ��ͽӿڣ��Ϳ���ʵ�������ʵ����ɾ�Ĳ��ˡ�
 */
/**
 * tb_book book
 * tb_student student
 * table�е������ֶ�Ҫ��vo���������Զ�Ӧ���ֶ�����������Ҫһ�£�
 * �ṩ��Ӧ��getset�������޲ι�����
 * @author U-anLA
 *
 */
public interface BasicDAO {
/**
 * @see ����id���Ҷ��󣬷��ش���Ķ���
 * @param clazz �����ز�ѯ���͵Ķ���
 * @param id
 * @return �ɹ�����instance
 *         ʧ�ܷ���null
 *         
 *         1��ͨ��class��getName����tb_ǰ׺���õ�����
 *         2������sql��䣺select * from tb_class.getName() where id = ?
 *         3������rs���Ѷ�����ֶ�����ͨ��class��set�����ӵ�instance����
 *         4������instance
 */
	Object get (Class clazz,String id);
	
	/**
	 * @see ��object�洢�����ݿ�
	 * @param object Ҫ�洢�Ķ���
	 * @return �ɹ�����true
	 *         ʧ�ܷ���false
	 *         
	 *         1��ͨ��class��getName����tb_ǰ׺���õ�����
	 *         2������sql��䣬insert into tb_class.getName()(object������������)
	 *                     values��object����������ֵ��
	 */
	boolean save(Object object);
	
	/**
	 * @see  ����object��idɾ������
	 * @param clazz
	 * @param id
	 * @return
	 */
	boolean delete(Class clazz,String id);
	
	/**
	 * @see ����sql���ִ�в�ѯ�����ذ�������Ķ����List����
	 * @param clazz
	 * @return
	 */
	List<? extends Object> findAll(Class clazz);
	
	/**
	 * @see ���±��е����ݲ���
	 * @param object
	 * @return
	 */
	boolean updateScore(Object object);
	

}






















