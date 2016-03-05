package BaseDao;

/**
 * 处理异常规则
 * DAO里面的异常会向上抛出
 * @author U-anLA
 *
 */
public class DataAccessException extends RuntimeException {

	public DataAccessException(){
		super();
	}
	
	public DataAccessException(String message,Throwable cause){
		super(message,cause);
	}
	
	public DataAccessException(String message){
		super(message);
	}
	
	public DataAccessException(Throwable cause){
		super(cause);
	}
}
