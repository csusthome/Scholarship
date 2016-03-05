package ItemDao;

import java.util.List;

import BaseDao.BasicDaoImpl;
import Item.DeyuItem;
/**
 * deyuitem表的操作类。继承通用类basicdaoimpl
 * @author U-anLA
 *
 */
public class DeyuItemDao extends BasicDaoImpl{
	public boolean remove(String id){
		return this.delete(DeyuItem.class, id);
	}
	
	public boolean add(DeyuItem deyuItem){
		return this.save(deyuItem);
	}
	
	public DeyuItem findByid(String id){
		Object obj = this.get(DeyuItem.class, id);
		DeyuItem deyuItem = (DeyuItem) obj;
		return deyuItem;
	}
	
	public List<DeyuItem> findAllBy(){
		List list =this.findAll(DeyuItem.class);
		return list;
	}
	
	public boolean setByNum(DeyuItem deyuItem){
		return this.updateScore(deyuItem);
	}
	
	public static void main(String[] args){
		
		DeyuItemDao did = new DeyuItemDao();
		DeyuItem di = new DeyuItem();
//		List list = did.findAll(di.getClass());
//		System.out.println(list);
		
//		di.setDeyu_score(3.0f);
//		di.setDeyu_num("14002");
//		if(did.setByNum(di)){
//			System.out.println("------------ok-------");
//		}
		
		
//		di = did.findByid("14002");
//		System.out.println(di.toString());
		
//		di.setDeyu_name("学生会5类");
//		di.setDeyu_num("14008");
//		di.setDeyu_score(1.5f);
//		did.add(di);
		
//		did.remove("14008");
		
		
	}
	
	
}
