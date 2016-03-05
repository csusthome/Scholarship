package ItemDao;

import java.util.List;

import BaseDao.BasicDaoImpl;
import Item.ZhiyuItem;
/**
 * zhiyuitemµÄ²Ù×÷Àà¡£
 * @author U-anLA
 *
 */
public class ZhiyuItemDao extends BasicDaoImpl{
	
	public boolean remove(String id){
		return this.delete(ZhiyuItem.class, id);
	}
	
	public boolean add(ZhiyuItem zhiyuItem){
		return this.save(zhiyuItem);
	}
	
	public ZhiyuItem findByid(String id){
		Object obj = this.get(ZhiyuItem.class, id);
		ZhiyuItem zhiyuItem = (ZhiyuItem) obj;
		return zhiyuItem;
	}
	
	public List<ZhiyuItem> findAllBy(){
		List list =this.findAll(ZhiyuItem.class);
		return list;
	}
	
	public boolean setByNum(ZhiyuItem zhiyuItem){
		return this.updateScore(zhiyuItem);
	}
	
}
