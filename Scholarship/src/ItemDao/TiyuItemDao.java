package ItemDao;

import java.util.List;

import BaseDao.BasicDaoImpl;
import Item.TiyuItem;

/**
 * tiyuitem表的操作类。继承通用类basicdaoimpl
 * @author U-anLA
 *
 */
public class TiyuItemDao extends BasicDaoImpl{
	public boolean remove(String id){
		return this.delete(TiyuItem.class, id);
	}
	
	public boolean add(TiyuItem tiyuItem){
		return this.save(tiyuItem);
	}
	
	public TiyuItem findByid(String id){
		Object obj = this.get(TiyuItem.class, id);
		TiyuItem tiyuItem = (TiyuItem) obj;
		return tiyuItem;
	}
	
	public List<TiyuItem> findAllBy(){
		List list =this.findAll(TiyuItem.class);
		return list;
	}
	
	public boolean setByNum(TiyuItem tiyuItem){
		return this.updateScore(tiyuItem);
	}
}
