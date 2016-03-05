package BaseDao;

/**
 * �����쳣����
 * DAO������쳣�������׳�
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
