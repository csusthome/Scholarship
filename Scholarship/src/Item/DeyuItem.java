package Item;

import java.text.DecimalFormat;

/**
 * deyuitem表的操作类。
 * @author U-anLA
 *
 */
public class DeyuItem {
	
	private String deyu_num;
	private String deyu_name;
	private float deyu_score;
	

	
	public DeyuItem(){
		
	}
	
	
	public DeyuItem(String deyu_num, String deyu_name, float deyu_score) {
		super();
		this.deyu_num = deyu_num;
		this.deyu_name = deyu_name;
		this.deyu_score = deyu_score;
	}
	public String getDeyu_num() {
		return deyu_num;
	}
	public void setDeyu_num(String deyu_num) {
		this.deyu_num = deyu_num;
	}
	public String getDeyu_name() {
		return deyu_name;
	}
	public void setDeyu_name(String deyu_name) {
		this.deyu_name = deyu_name;
	}
	public float getDeyu_score() {
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		float end = Float.parseFloat(decimalFormat.format(deyu_score));
		return end;
	}
	public void setDeyu_score(float deyu_score) {
		this.deyu_score = deyu_score;
	}


	@Override
	public String toString() {
		return "'" + deyu_num + "','" + deyu_name
				+ "'," + deyu_score;
	}
	
	
	
}
