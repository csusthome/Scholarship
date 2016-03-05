package Item;

import java.text.DecimalFormat;

/**
 * �����������javabean�����䡣
 * @author U-anLA
 *
 */
public class ZhiyuItem {
	
	private String subj_num;
	private String subj_name;
	private float subj_score;
	private float finalScore;
	private int target;
	

	
	public ZhiyuItem(){
		
	}
	
	public ZhiyuItem(String subj_num, String subj_name, float finalScore,
			float subj_score,int target) {
		super();
		this.subj_num = subj_num;
		this.subj_name = subj_name;
		this.finalScore = finalScore;
		this.subj_score = subj_score;
		this.target = target;
	}
	
	
	
	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public String getSubj_num() {
		return subj_num;
	}
	public void setSubj_num(String subj_num) {
		this.subj_num = subj_num;
	}
	public String getSubj_name() {
		return subj_name;
	}
	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}
	public float getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(float finalScore) {
		this.finalScore = finalScore;
	}
	public float getSubj_score() {
		DecimalFormat decimalFormat=new DecimalFormat(".00");//���췽�����ַ���ʽ�������С������2λ,����0����.
		float end = Float.parseFloat(decimalFormat.format(subj_score));
		return end;
	}
	public void setSubj_score(float subj_score) {
		this.subj_score = subj_score;
	}

	@Override
	public String toString() {
		return "'" + subj_num + "','" + subj_name
				+ "'," + subj_score + "," + target;
	}
	

	
	

	
	
}
