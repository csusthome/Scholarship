package Item;

import java.text.DecimalFormat;

/**
 * tiyuitemµÄ²Ù×÷Àà¡£
 * @author U-anLA
 *
 */
public class TiyuItem {
	
	private String tiyu_num;
	private String tiyu_name;
	private float tiyu_score;
	

	
	public TiyuItem(){
		
	}
	
	public TiyuItem(String tiyu_num, String tiyu_name, float tiyu_score) {
		super();
		this.tiyu_num = tiyu_num;
		this.tiyu_name = tiyu_name;
		this.tiyu_score = tiyu_score;
	}
	public String getTiyu_num() {
		return tiyu_num;
	}
	public void setTiyu_num(String tiyu_num) {
		this.tiyu_num = tiyu_num;
	}
	public String getTiyu_name() {
		return tiyu_name;
	}
	public void setTiyu_name(String tiyu_name) {
		this.tiyu_name = tiyu_name;
	}
	public float getTiyu_score() {
		DecimalFormat decimalFormat = new DecimalFormat(".00");
		float end = Float.parseFloat(decimalFormat.format(tiyu_score));
		return end;
	}
	public void setTiyu_score(float tiyu_score) {
		this.tiyu_score = tiyu_score;
	}

	@Override
	public String toString() {
		return "'" + tiyu_num + "','" + tiyu_name
				+ "'," + tiyu_score;
	}
	
	
	
}
