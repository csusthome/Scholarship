package BaseDao;
import java.util.List;

/**
 * 待改善，将传入一个<E>的泛型接口，就可以实现零代码实现增删改查了。
 */
/**
 * tb_book book
 * tb_student student
 * table中的所有字段要和vo的所有属性对应，字段名和属性名要一致，
 * 提供相应的getset方法，无参构造器
 * @author U-anLA
 *
 */
public interface BasicDAO {
/**
 * @see 根据id查找对象，返回传入的对象
 * @param clazz ：返回查询类型的对象
 * @param id
 * @return 成功返回instance
 *         失败返回null
 *         
 *         1、通过class的getName加上tb_前缀，拿到表名
 *         2、发送sql语句：select * from tb_class.getName() where id = ?
 *         3、处理rs：把对象的字段属性通过class的set方法加到instance里面
 *         4、返回instance
 */
	Object get (Class clazz,String id);
	
	/**
	 * @see 将object存储到数据库
	 * @param object 要存储的对象
	 * @return 成功返回true
	 *         失败返回false
	 *         
	 *         1、通过class的getName加上tb_前缀，拿到表名
	 *         2、发送sql语句，insert into tb_class.getName()(object的所有属性名)
	 *                     values（object的所有属性值）
	 */
	boolean save(Object object);
	
	/**
	 * @see  根据object和id删除数据
	 * @param clazz
	 * @param id
	 * @return
	 */
	boolean delete(Class clazz,String id);
	
	/**
	 * @see 根据sql语句执行查询，返回包含传入的对象的List集合
	 * @param clazz
	 * @return
	 */
	List<? extends Object> findAll(Class clazz);
	
	/**
	 * @see 更新表中的数据操作
	 * @param object
	 * @return
	 */
	boolean updateScore(Object object);
	

}






















