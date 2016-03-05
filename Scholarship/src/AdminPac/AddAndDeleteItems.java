package AdminPac;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ConnectFactory.ConnecFactory;
import Item.DeyuItem;
import Item.TiyuItem;
import Item.ZhiyuItem;
import ItemDao.DeyuItemDao;
import ItemDao.TiyuItemDao;
import ItemDao.ZhiyuItemDao;

/**
 * 这个类是用来添加或者删除，或者更改项目分数。
 * 
 */
public class AddAndDeleteItems extends JPanel implements ActionListener {

	private JLabel jls = new JLabel("操作：");
	private JLabel jl1 = new JLabel("请输入更改分数");
	private JLabel jlshow = new JLabel();

	private JLabel jl2 = new JLabel("添加项目：");
	private JLabel jl3 = new JLabel("项目名称：");
	private JLabel jl4 = new JLabel("项目代码：");
	private JLabel jl5 = new JLabel("项目学分：");

	// 输入框，项目学分，项目名称，项目编号。
	private JTextField jtfName = new JTextField();
	private JTextField jtfNum = new JTextField();
	private JTextField jtfScore = new JTextField();
	// 定义三个jcombox。
	private JComboBox jcbDo;
	private JComboBox jcbCate;
	private JComboBox jcbItem;
	private JComboBox jcbAdd;

	//添加确定，添加按钮。
	private JButton jbtDo = new JButton("确定");
	private JButton jbtAdd = new JButton("添加");

	// 分数输入框
	private JTextField jtfChangeScore = new JTextField();
	// 定义是否选择最后一个一个的监控变量。
	private boolean isSelect = false;
	
	private JCheckBox jcheckTiyu = new JCheckBox("体育课");
	
	//定义数据库操作变量
	private Connection conn;
	private Statement stam;
	private ResultSet rs;
	
	
	/**
	 * 构造方法。
	 */
	public AddAndDeleteItems() {
		initFrame();
		addListeners();

	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public JComboBox getJcbCate() {
		return jcbCate;
	}

	public void setJcbCate(JComboBox jcbCate) {
		this.jcbCate = jcbCate;
	}

	public JComboBox getJcbItem() {
		return jcbItem;
	}

	public void setJcbItem(JComboBox jcbItem) {
		this.jcbItem = jcbItem;
	}

	/**
	 * 用于添加各种事件。
	 */
	private void addListeners() {
		jcbItem.addActionListener(this);
		jcbCate.addActionListener(this);
		jcbDo.addActionListener(this);
		jcbAdd.addActionListener(this);
		jbtDo.addActionListener(this);
		jbtAdd.addActionListener(this);
	}

	/**
	 * 用于生成各个主窗体。
	 */
	private void initFrame() {

		this.setLayout(null);

		jls.setBounds(30, 30, 100, 30);
		this.add(jls);

		jcbDo = new JComboBox();
		jcbDo.addItem("删除项目");
		jcbDo.addItem("更改项目");
		jcbDo.setBounds(30, 60, 100, 30);
		this.add(jcbDo);

		jcbCate = new JComboBox();
		jcbCate.addItem("德育项");
		jcbCate.addItem("智育项");
		jcbCate.addItem("体育项");
		jcbCate.setBounds(131, 60, 100, 30);
		this.add(jcbCate);

		jcbItem = new JComboBox();
		jcbItem.setBounds(232, 60, 400, 30);
		this.add(jcbItem);

		jlshow.setBounds(450, 60, 300, 30);
		this.add(jlshow);

		jl1.setBounds(30, 110, 200, 30);
		this.add(jl1);

		jbtDo.setBounds(332, 150, 100, 30);
		this.add(jbtDo);

		jtfChangeScore.setBounds(30, 150, 100, 30);
		this.add(jtfChangeScore);

		if (isSelect == false) {
			jtfChangeScore.setEditable(false);
		}

		jl2.setBounds(30, 280, 200, 30);
		this.add(jl2);

		jcbAdd = new JComboBox();
		jcbAdd.setBounds(30, 320, 100, 30);
		jcbAdd.addItem("德育项");
		jcbAdd.addItem("智育项");
		jcbAdd.addItem("体育项");
		this.add(jcbAdd);

		jl3.setBounds(150, 320, 100, 30);
		this.add(jl3);
		jtfName.setBounds(230, 320, 180, 30);
		this.add(jtfName);

		jl4.setBounds(150, 370, 100, 30);
		this.add(jl4);
		jtfNum.setBounds(230, 370, 180, 30);
		this.add(jtfNum);

		jl5.setBounds(150, 420, 100, 30);
		this.add(jl5);
		jtfScore.setBounds(230, 420, 180, 30);
		this.add(jtfScore);

		jbtAdd.setBounds(450, 460, 100, 30);
		this.add(jbtAdd);
		
		jcheckTiyu.setBounds(450, 400, 100, 30);
		this.add(jcheckTiyu);
		jcheckTiyu.setEnabled(false);

	}

	/**
	 * 用于添加各种事件。
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		//种类项，德育智育或体育，然后各自联动
		if (e.getSource() == jcbCate) {

			if (jcbCate.getSelectedItem().toString().trim().equals("德育项")) {

				this.setItems(jcbItem, "德育项");
			}
			if (jcbCate.getSelectedItem().toString().trim().equals("体育项")) {

				this.setItems(jcbItem, "体育项");
			}
			if (jcbCate.getSelectedItem().toString().trim().equals("智育项")) {

				this.setItems(jcbItem, "智育项");
			}
		} else if (e.getSource() == jcbDo) {

			//更改操作。
			if (jcbDo.getSelectedItem().toString().trim().equals("更改项目")) {
				if (jcbItem != null) {
					this.jtfChangeScore.setEditable(true);
				}
			}

			//删除操作
			if (jcbDo.getSelectedItem().toString().trim().equals("删除项目")) {
				this.jtfChangeScore.setEditable(false);
			}
		} else if (e.getSource() == jcbItem) {
			// 此处先将选中的项目分解出来，也就是分解出他们的项目代码。
			// 然后再进行更改、删除操作。

			// 要检测jcbItem的getselectedItem是否为空，因为如果不这样，
			// 当我从新选择时，这个的selecteditem就会为null，而不是jcbItem。
			if (jcbItem.getSelectedItem() != null) {
				StringBuffer str = new StringBuffer();
				str.append(jcbItem.getSelectedItem().toString().trim());
				// 通过截取固定的长度字符串，得到项目代码。
				String num = str
						.subSequence(str.length() - 6, str.length() - 1)
						.toString();
				// System.out.println(num);
				// System.out.println(str);
				float score = this.getScore(num, jcbCate.getSelectedItem()
						.toString().trim());
				// 这样的转换方法，将float转化为string。
				jtfChangeScore.setText(score + "");
			}

		} else if (e.getSource() == jbtDo) {

			// 此处为删除或者更改项目分数的操作

			if (jcbItem.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(this, "请选择相应的子项", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// 得到name，和num。
			StringBuffer str = new StringBuffer();
			str.append(jcbItem.getSelectedItem());
			String itemName = str.substring(0, str.length() - 7).toString();
			String itemNum = str.substring(str.length() - 6, str.length() - 1);


			if (jcbDo.getSelectedItem().toString().trim().equals("更改项目")) {
				// 检测格式
				if (!checkPatternStr(jtfChangeScore.getText().trim())) {
					JOptionPane.showMessageDialog(this, "分数只能是100以下的数，且最多一位小数",
							"错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// 具体操作。分三种情况。
				//每一项都把更改的同学的更改的分数重新计算，重新插入。
				// 德育项
				if (jcbCate.getSelectedItem().toString().trim().equals("德育项")) {
					float finalDeyu = 0.0f;
					DeyuItem di = new DeyuItem();
					di.setDeyu_name(itemName);
					di.setDeyu_num(itemNum);
					di.setDeyu_score(Float.parseFloat(jtfChangeScore.getText()
							.trim()));
					DeyuItemDao did = new DeyuItemDao();
					if (did.setByNum(di)) {
						//这里面，改相应表内的相应记录。
						List stuList = new ArrayList();
						stuList = this.getStuNum("stu_deyu_score","deyu_num", itemNum);
						for(int i = 0;i < stuList.size();i++){
							finalDeyu = 0.0f;
							List itemList = this.getItemNumByStudent("stu_deyu_score", stuList.get(i).toString());
							for(int j = 0;j < itemList.size();j++){
								finalDeyu = finalDeyu + this.getItemScoreByItemNum("deyuitem", "deyu_score", "deyu_num", itemList.get(j).toString());
					
							}
							//这一个同学的新德育总分。
							finalDeyu = finalDeyu*0.25f;
							this.updateNewScore("stu_deyu", finalDeyu, stuList.get(i).toString());
							
						}
						
						JOptionPane.showMessageDialog(this, "操作成功", "提示",
								JOptionPane.YES_OPTION);
						return;
					} else {
						JOptionPane.showMessageDialog(this, "操作失败", "提示",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				// 智育项
				if (jcbCate.getSelectedItem().toString().trim().equals("智育项")) {
					float finalZhiyu = 0.0f;
					ZhiyuItem zi = new ZhiyuItem();
					zi.setSubj_name(itemName);
					zi.setSubj_num(itemNum);
					zi.setSubj_score(Float.parseFloat(jtfChangeScore.getText()
							.trim()));
					ZhiyuItemDao zid = new ZhiyuItemDao();
					if (zid.setByNum(zi)) {
						
						
						//这里面，改相应表内的相应记录。
						List stuList = new ArrayList();
						stuList = this.getStuNum("stu_zhiyu_score","subj_num", itemNum);
						for(int i = 0;i < stuList.size();i++){
							//得到多少位学生
							finalZhiyu = 0.0f;
							float n = 0.0f;
							List itemList = this.getItemNumByStudent("stu_zhiyu_score", stuList.get(i).toString());
							for(int j = 0;j < itemList.size();j++){
								//得到每位学生的每门智育课
								
									for(int z = 0;z < this.getZhiyuScore().size();z++){
										//得到每门智育课的分数，并且相乘
										if(stuList.get(i).equals(this.getZhiyuScore().get(z).getSubj_num())&&itemList.get(j).equals(this.getZhiyuScore().get(z).getSubj_name())&&!this.getPENum().contains(itemList.get(j).toString())){
											finalZhiyu += this.getZhiyuScore().get(i).getFinalScore()*this.getItemScoreByItemNum("zhiyuitem", "subj_score", "subj_num", itemList.get(j).toString());
											n += this.getItemScoreByItemNum("zhiyuitem", "subj_score", "subj_num", itemList.get(j).toString());
											break;
										}
									}
								


							}
							
							//作用于每位学生。
							finalZhiyu = finalZhiyu/n;
							//这一个同学的新德育总分。
							finalZhiyu = finalZhiyu*0.70f;
							if(finalZhiyu >= 70.0f ){
								finalZhiyu = 70.0f;
							}
							
							//加上四六级的
							finalZhiyu += this.getCetByStuNum(stuList.get(i).toString());
							this.updateNewScore("stu_zhiyu", finalZhiyu, stuList.get(i).toString());

							
						}
						
						
						
						JOptionPane.showMessageDialog(this, "操作成功", "提示",
								JOptionPane.YES_OPTION);
						return;
					} else {
						JOptionPane.showMessageDialog(this, "操作失败", "提示",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				// 体育项
				if (jcbCate.getSelectedItem().toString().trim().equals("体育项")) {
					float finalTiyu = 0.0f;
					float itemTiyu = 0.0f;
					float corseTiyu = 0.0f;
					
					TiyuItem ti = new TiyuItem();
					ti.setTiyu_name(itemName);
					ti.setTiyu_num(itemNum);
					ti.setTiyu_score(Float.parseFloat(jtfChangeScore.getText()
							.trim()));
					TiyuItemDao tid = new TiyuItemDao();

					if (tid.setByNum(ti)) {
						
						//在这里添加修改体育项的功能
						
						
						//这里面，改相应表内的相应记录。
						List stuList = new ArrayList();
						stuList = this.getStuNum("stu_zhiyu_score","subj_num", itemNum);
						for(int i = 0;i < stuList.size();i++){
							//得到多少位学生
							corseTiyu = 0.0f;
							float n = 0.0f;
							List itemList = this.getItemNumByStudent("stu_zhiyu_score", stuList.get(i).toString());
							for(int j = 0;j < itemList.size();j++){
								//得到每位学生的每门智育课,并且从中选出每个同学的体育课。
								
									for(int z = 0;z < this.getZhiyuScore().size();z++){
										//得到每门智育课的分数，并且相乘
										if(stuList.get(i).equals(this.getZhiyuScore().get(z).getSubj_num())&&itemList.get(j).equals(this.getZhiyuScore().get(z).getSubj_name())&&this.getPENum().contains(itemList.get(j).toString())){
											corseTiyu += this.getZhiyuScore().get(i).getFinalScore()*this.getItemScoreByItemNum("zhiyuitem", "subj_score", "subj_num", itemList.get(j).toString());
											n += this.getItemScoreByItemNum("zhiyuitem", "subj_score", "subj_num", itemList.get(j).toString());
											break;
										}
									}
								


							}
							
							//作用于每位学生。
							corseTiyu = corseTiyu/n;
							//这一个同学的体育总分。
							corseTiyu = corseTiyu*0.70f;

							
							//加上体育项的级的
							
								List itemListTi = this.getItemNumByStudent("stu_tiyu_score", stuList.get(i).toString());
								for(int j = 0;j < itemList.size();j++){
									itemTiyu = itemTiyu + this.getItemScoreByItemNum("tiyuitem", "tiyu_score", "tiyu_num", itemList.get(j).toString());
						
								}
								
								finalTiyu = corseTiyu + itemTiyu;
								//这一个同学的新德育总分。
								finalTiyu = finalTiyu*0.05f;
								if(finalTiyu >= 5.0f){
									finalTiyu = 5.0f;
								}
								this.updateNewScore("stu_tiyu", finalTiyu, stuList.get(i).toString());


							
						}
						
						
						
						
						
						
						
						JOptionPane.showMessageDialog(this, "操作成功", "提示",
								JOptionPane.YES_OPTION);
						return;
					} else {
						JOptionPane.showMessageDialog(this, "操作失败", "提示",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			} else if (jcbDo.getSelectedItem().toString().trim().equals("删除项目")) {
				// 此处为删除项目板块。
				// 询问
				int i = JOptionPane
						.showConfirmDialog(this, "您确认删除这个项目？(会删除所有相关的信息！)",
								"询问", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (i == 0) {
					// 确认操作
					// 德育项操作
					if (jcbCate.getSelectedItem().toString().trim()
							.equals("德育项")) {
						DeyuItemDao did = new DeyuItemDao();
						if (did.remove(itemNum)) {
							
							//更改相应的分数。
							//得到要更改的所有同学的学号
							List list = this.getStuNum("stu_deyu_score", "deyu_num", itemNum);
							
							for(int w = 0;w < list.size();w++){
								float initScore = this.getOneFinalScoreByStuNum("stu_deyu", list.get(w).toString());
								float deleteItemScore = this.getItemScoreByItemNum("deyuitem", "deyu_score", "deyu_num", itemNum);
								initScore /= 0.25f;
								initScore -= deleteItemScore;
								initScore *= 0.25f;
								this.updateNewScore("deyu_score", initScore, list.get(w).toString());
								
							}
							
							
							
							
							
							JOptionPane.showMessageDialog(this, "删除成功", "提示",
									JOptionPane.YES_OPTION);
							this.setItems(jcbItem, "德育项");
						} else {
							JOptionPane.showMessageDialog(this, "删除失败", "提示",
									JOptionPane.ERROR_MESSAGE);
						}
					}

					// 智育项操作
					if (jcbCate.getSelectedItem().toString().trim()
							.equals("智育项")) {
						ZhiyuItemDao zid = new ZhiyuItemDao();
						if (zid.remove(itemNum)) {
							
							//更改相应的分数。
							//得到要更改的所有同学的学号
							List list = this.getStuNum("stu_zhiyu_score", "zhiyu_num", itemNum);
							
							for(int w = 0;w < list.size();w++){
								float initScore = this.getOneFinalScoreByStuNum("stu_zhiyu", list.get(w).toString());
								float deleteItemScore = this.getItemScoreByItemNum("zhiyuitem", "subj_score", "subj_num", itemNum);
								if(this.getPENum().contains(itemNum)){
									break;
								}
								float corseScore = this.getFinalScoreByItemAndStu(itemNum, list.get(w).toString());
								float totalZhiyu = initScore - this.getCetByStuNum(list.get(w).toString());
								totalZhiyu /= 0.70f;
								totalZhiyu -= deleteItemScore*corseScore;
								totalZhiyu *= 0.70f;
								totalZhiyu += this.getCetByStuNum(list.get(w).toString());
								this.updateNewScore("zhiyu_score", totalZhiyu, list.get(w).toString());
								
							}
							
							
							
							
							JOptionPane.showMessageDialog(this, "删除成功", "提示",
									JOptionPane.YES_OPTION);
							this.setItems(jcbItem, "智育项");
						} else {
							JOptionPane.showMessageDialog(this, "删除失败", "提示",
									JOptionPane.ERROR_MESSAGE);
						}
					}

					// 体育项操作
					if (jcbCate.getSelectedItem().toString().trim()
							.equals("体育项")) {
						TiyuItemDao tid = new TiyuItemDao();
						if (tid.remove(itemNum)) {
							
							
							
							//更改相应的分数。
							//得到要更改的所有同学的学号
							List list = this.getStuNum("stu_tiyu_score", "tiyu_num", itemNum);
							
							for(int w = 0;w < list.size();w++){
								float initScore = this.getOneFinalScoreByStuNum("stu_tiyu", list.get(w).toString());
								float deleteItemScore = this.getItemScoreByItemNum("tiyuitem", "tiyu_score", "tiyu_num", itemNum);
								initScore /= 0.05f;
								
								//还要减去体育课的成绩。,不用减去了，因为最后是加上的。
								
								
								
								
								
								
								
								
								
								initScore -= deleteItemScore;
								initScore *= 0.05f;
								this.updateNewScore("deyu_score", initScore, list.get(w).toString());
								
							}
							
							
							
							
							
							JOptionPane.showMessageDialog(this, "删除成功", "提示",
									JOptionPane.YES_OPTION);
							this.setItems(jcbItem, "体育项");
						} else {
							JOptionPane.showMessageDialog(this, "删除失败", "提示",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}
			}

		} else if (e.getSource() == jbtAdd) {

			// 此处为添加项目的操作。
			String itemName = jtfName.getText().trim();
			String itemNum = jtfNum.getText().trim();
			String itemScore = jtfScore.getText().trim();
			int target;
			if(jcheckTiyu.isSelected()){
				target = 1;
			}else{
				target = 0;
			}
			
			//检查各项是否达标
			
			if (itemName.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入所添加的项目名称！", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (itemNum.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入所添加的项目代码！", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (itemScore.equals("")) {
				JOptionPane.showMessageDialog(this, "请输入所添加的项目分数！", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!this.checkNum(itemNum)) {
				System.out.println(itemNum);
				JOptionPane.showMessageDialog(this, "项目代码只能是5位数的数字！", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!this.checkPatternStr(itemScore)) {
				JOptionPane.showMessageDialog(this,
						"项目分数只能是100以内的整数或小数，且最多一位小数！", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (itemName.length() > 16) {
				JOptionPane.showMessageDialog(this, "项目名称最多16位字符！", "错误",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int i = JOptionPane.showConfirmDialog(this, "确认添加此条项目记录？", "询问",
					JOptionPane.YES_NO_OPTION);
			if (i == 0) {
				if (jcbAdd.getSelectedItem().toString().trim().equals("德育项")) {
					// 此处为添加德育项。
					
					if(!this.checkDuplicate("德育项", itemNum)){
						return;
					}
					
					
					DeyuItem di = new DeyuItem();
					di.setDeyu_name(itemName);
					di.setDeyu_num(itemNum);
					di.setDeyu_score(Float.parseFloat(itemScore));
					DeyuItemDao did = new DeyuItemDao();
					if (did.add(di)) {
						JOptionPane.showMessageDialog(this, "添加记录成功", "恭喜",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					} else {
						JOptionPane.showMessageDialog(this, "添加记录失败", "错误",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

				}

				if (jcbAdd.getSelectedItem().toString().trim().equals("智育项")) {
					// 此处为添加德育项。
					
					if(!this.checkDuplicate("智育项", itemNum)){
						return;
					}
					ZhiyuItem zi = new ZhiyuItem();
					zi.setSubj_name(itemName);
					zi.setSubj_num(itemNum);
					zi.setSubj_score(Float.parseFloat(itemScore));
					zi.setTarget(target);
					ZhiyuItemDao zid = new ZhiyuItemDao();
					if(zid.add(zi)){
						JOptionPane.showMessageDialog(this, "添加记录成功", "恭喜",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}else{
						JOptionPane.showMessageDialog(this, "添加记录失败", "错误",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				if (jcbAdd.getSelectedItem().toString().trim().equals("体育项")) {
					
					if(!this.checkDuplicate("体育项", itemNum)){
						return;
					}
					// 此处为添加德育项。
					TiyuItem ti = new TiyuItem();
					ti.setTiyu_name(itemName);
					ti.setTiyu_num(itemNum);
					ti.setTiyu_score(Float.parseFloat(itemScore));
					TiyuItemDao tid = new TiyuItemDao();
					if(tid.add(ti)){
						JOptionPane.showMessageDialog(this, "添加记录成功", "恭喜",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}else{
						JOptionPane.showMessageDialog(this, "添加记录失败", "错误",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}

		}else if(e.getSource() == jcbAdd){
			//然后给添加体育项操作。
			if(jcbAdd.getSelectedItem().toString().trim().equals("智育项")){
				jcheckTiyu.setEnabled(true);
			}else{
				jcheckTiyu.setEnabled(false);
			}
		}

	}
	
	/**
	 * 检查是否重复的学号项目
	 * @param str
	 * @param itemNum
	 * @return
	 */
	private boolean checkDuplicate(String str,String itemNum){
		List list = this.getItem(str);
		for(int j = 0;j < list.size();j++){
			if(str.equals("德育项")){
				DeyuItem di = (DeyuItem)list.get(j);
				if(di.getDeyu_num().equals(itemNum)){
					JOptionPane.showMessageDialog(this, "重复的项目代码", "出错", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}else if(str.equals("体育项")){
				TiyuItem ti = (TiyuItem)list.get(j);
				if(ti.getTiyu_num().equals(itemNum)){
					JOptionPane.showMessageDialog(this, "重复的项目代码", "出错", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}else if(str.equals("智育项")){
				ZhiyuItem zi = (ZhiyuItem)list.get(j);
				if(zi.getSubj_num().equals(itemNum)){
					JOptionPane.showMessageDialog(this, "重复的项目代码", "出错", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}

		}
		return true;
	}
	
	/**
	 * 得到各项的项目。
	 * @param str
	 * @return
	 */
	private List getItem(String str){
		if(str.equals("德育项")){
			return new DeyuItemDao().findAllBy();
		}
		if(str.equals("智育项")){
			return new ZhiyuItemDao().findAllBy();
		}
		if(str.equals("体育项")){
			return new TiyuItemDao().findAllBy();
		}
		return null;
	}

	/**
	 * 此方法用于检测添加的项目代码是否符合规格， 也就是要求只能是6位数的数字。
	 * 
	 * @param str
	 * @return
	 */
	private boolean checkNum(String str) {
		String pattern = "[0-9]{5}";
		if (!str.matches(pattern)) {
			return false;
		}
		return true;
	}

	/**
	 * 用于检测更改或输入的分数，是否符合字符串要求。
	 * 要求：100以内的，且最多只能一位小数。
	 * @param str
	 * @return
	 */
	private boolean checkPatternStr(String str) {
		// 分四种不同的情况
		String pattern1 = "[0-9]{2}";
		String pattern2 = "[0-9]{2}+\\.[0-9]{1}";
		String pattern3 = "[0-9]{1}";
		String pattern4 = "[0-9]{1}+\\.[0-9]{1}";
		if (str.matches(pattern1)) {
			return true;
		}
		if (str.matches(pattern2)) {
			return true;
		}
		if (str.matches(pattern3)) {
			return true;
		}
		if (str.matches(pattern4)) {
			return true;
		}
		return false;

	}

	/**
	 * 得到某一项的分数。
	 * @param num
	 * @param type
	 * @return
	 */
	private float getScore(String num, String type) {

		if (type == "德育项") {
			DeyuItem di = new DeyuItem();
			DeyuItemDao did = new DeyuItemDao();
			di = did.findByid(num);
			return di.getDeyu_score();
		} else if (type == "体育项") {
			TiyuItem ti = new TiyuItem();
			TiyuItemDao tid = new TiyuItemDao();
			ti = tid.findByid(num);
			return ti.getTiyu_score();
		} else if (type == "智育项") {
			ZhiyuItem zi = new ZhiyuItem();
			ZhiyuItemDao zid = new ZhiyuItemDao();
			zi = zid.findByid(num);
			return zi.getSubj_score();
		} else {
			return 0.0f;
		}

	}
	/**
	 * 设置各项，也就是联动菜单。
	 * @param jcb
	 * @param cate
	 */
	private void setItems(JComboBox jcb, String cate) {
		jcb.removeAllItems();

		if (cate.equals("德育项")) {

			DeyuItem di = new DeyuItem();
			DeyuItemDao did = new DeyuItemDao();
			List list = did.findAll(di.getClass());
			for (int i = 0; i < list.size(); i++) {
				// 得到一个德育项
				DeyuItem deyu = (DeyuItem) list.get(i);
				// 将德育项的两个字段拼装起来
				String item = deyu.getDeyu_name() + "(" + deyu.getDeyu_num()
						+ ")";
				jcb.addItem(item);
			}
		}

		if (cate.equals("智育项")) {

			ZhiyuItem zi = new ZhiyuItem();
			ZhiyuItemDao zid = new ZhiyuItemDao();
			List list = zid.findAll(zi.getClass());
			for (int i = 0; i < list.size(); i++) {
				// 得到一个智育项
				ZhiyuItem zhiyu = (ZhiyuItem) list.get(i);
				// 将德育项的两个字段拼装起来
				String item = zhiyu.getSubj_name() + "(" + zhiyu.getSubj_num()
						+ ")";
				jcb.addItem(item);
			}

		}

		if (cate.equals("体育项")) {

			TiyuItem ti = new TiyuItem();
			TiyuItemDao tid = new TiyuItemDao();
			List list = tid.findAll(ti.getClass());
			for (int i = 0; i < list.size(); i++) {
				// 得到一个智育项
				TiyuItem tiyu = (TiyuItem) list.get(i);
				// 将德育项的两个字段拼装起来
				String item = tiyu.getTiyu_name() + "(" + tiyu.getTiyu_num()
						+ ")";
				jcb.addItem(item);
			}

		}
	}
	
	/**
	 * 要更改某一项，则相应的学生列表项都会要改变。
	 * @param tableName
	 * @param fieldName
	 * @param Num
	 * @return
	 */
	private List getStuNum(String tableName,String fieldName,String Num){
		List list = new ArrayList();
		String sqlGetStu = "SELECT stu_num FROM " + tableName + " WHERE " + fieldName + "='" + Num + "';";
		conn = ConnecFactory.getConnection();
		try {
			stam = conn.createStatement();
			rs = stam.executeQuery(sqlGetStu);
			while(rs.next()){
				String stu = rs.getString("stu_num");
				list.add(stu);
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		} finally{
			ConnecFactory.closeConnection(conn, stam, rs);
		}
		return list;
	}
	
	/**
	 * 得到要更改的学生的所有项目号。
	 * @param tableName
	 * @param stuNum
	 * @return
	 */
	private List getItemNumByStudent(String tableName,String stuNum){
		List list = new ArrayList();
		String sqlGetStu = "SELECT * FROM " + tableName + " WHERE stu_num='" + stuNum + "';";
		conn = ConnecFactory.getConnection();
		try {
			stam = conn.createStatement();
			rs = stam.executeQuery(sqlGetStu);
			while(rs.next()){
				String deyuNum = rs.getString("deyu_num");
				list.add(deyuNum);
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			ConnecFactory.closeConnection(conn, stam, rs);
		}
		return list;
	}
	
	/**
	 * 由某一项，得到它的分数。
	 * @param tableName
	 * @param fieldName
	 * @param itemName
	 * @param itemNum
	 * @return
	 */
	private float getItemScoreByItemNum(String tableName,String fieldName,String itemName,String itemNum){
		float score = 0.0f;
		String sqlGetStu = "SELECT " + fieldName + " FROM " + tableName + " WHERE " + itemName + "='" + itemNum + "';";
		conn = ConnecFactory.getConnection();
		try {
			stam = conn.createStatement();
			rs = stam.executeQuery(sqlGetStu);
			while(rs.next()){
				score = rs.getFloat(fieldName);
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			ConnecFactory.closeConnection(conn, stam, rs);
		}
		return score;
	}
	/**
	 * 这个类是用来改变新成绩总分的。
	 * @param fieldName
	 * @param score
	 * @return
	 */
	private boolean updateNewScore(String fieldName,float score,String stuNum){
		String sqlGetStu = "UPDATE statistic SET " + fieldName + "=" + score + " WHERE stu_num='" + stuNum + "';";
		conn = ConnecFactory.getConnection();
		try {
			stam = conn.createStatement();
			int i = stam.executeUpdate(sqlGetStu);
			if(i == 0){
				JOptionPane.showMessageDialog(this, "操作失败741");
				return false;
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			ConnecFactory.closeConnection(conn, stam, rs);
		}
		return true;
	}
	/**
	 * 得到智育分，也就是stu_zhiyu_score，不过，使用zhiyuitem来存放的。
	 * @return
	 */
	private List<ZhiyuItem> getZhiyuScore(){
		List<ZhiyuItem> list = new ArrayList<ZhiyuItem>();
		
		String sqlGetStu = "SELECT * FROM stu_zhiyu_score;";
		conn = ConnecFactory.getConnection();
		try {
			stam = conn.createStatement();
			rs = stam.executeQuery(sqlGetStu);
			while(rs.next()){
				ZhiyuItem zi = new ZhiyuItem();
				String stuNum = rs.getString("stu_num");
				String subjName = rs.getString("subj_num");
				float subjScore = rs.getFloat("stu_sub_score");
				zi.setSubj_name(subjName);
				zi.setSubj_num(stuNum);
				zi.setFinalScore(subjScore);
				list.add(zi);
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			ConnecFactory.closeConnection(conn, stam, rs);
		}
		return list;
	}
	
	/**
	 * 得到体育项的课程编号。
	 * @return
	 */
	private List<String> getPENum(){
		List<String> list = new ArrayList<String>();
		String sqlGetStu = "SELECT * FROM zhiyuitem;";
		conn = ConnecFactory.getConnection();
		try {
			stam = conn.createStatement();
			rs = stam.executeQuery(sqlGetStu);
			while(rs.next()){
				String subjNum = rs.getString("subj_num");
				int target = rs.getInt("target");
				if(target == 1){
					list.add(subjNum);
				}
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			ConnecFactory.closeConnection(conn, stam, rs);
		}
		return list;
	}
	/**
	 * 待写
	 * @return
	 */
	private float getCetByStuNum(String stuNum){
		float score = 0.0f;
		String sqlGetStu = "SELECT * FROM stu_cet_score WHERE stu_num=" + stuNum + ";";
		conn = ConnecFactory.getConnection();
		try {
			stam = conn.createStatement();
			rs = stam.executeQuery(sqlGetStu);
			while(rs.next()){
				String type_name = rs.getString("type_name");
				if(type_name.equals("0cet4")){
					score += 0.0f;
				}
				if(type_name.equals("1cet4")){
					score += 1.5f;
				}
				if(type_name.equals("2cet4")){
					score += 1.0f;
				}
				if(type_name.equals("3cet4")){
					score += 0.5f;
				}
				if(type_name.equals("cet6")){
					score += 2.0f;
				}
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			ConnecFactory.closeConnection(conn, stam, rs);
		}
		return score;
	}
	
	
	
	
	/**
	 * 这个方法是用来得到某一位同学的德育或者智育或者体育分的。
	 * @param fieldName
	 * @param stuNum
	 * @return
	 */
	private float getOneFinalScoreByStuNum(String fieldName,String stuNum){
		float score = 0.0f;
		String sqlGetStu = "SELECT " + fieldName + " FROM statistic WHERE stu_num=" + stuNum + ";";
		conn = ConnecFactory.getConnection();
		try {
			stam = conn.createStatement();
			rs = stam.executeQuery(sqlGetStu);
			while(rs.next()){
				score = rs.getFloat(fieldName);
				
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			ConnecFactory.closeConnection(conn, stam, rs);
		}
		
		return score;
	}
	

	/**
	 * 得到某一项智育成绩的最后分数。
	 * @param itemNum
	 * @param stuNum
	 * @return
	 */
	private float getFinalScoreByItemAndStu(String itemNum,String stuNum){
		float score = 0.0f;
		String sqlGetStu = "SELECT stu_sub_score FROM stu_zhiyu_score WHERE stu_num=" + stuNum + " AND subj_num=" + itemNum + ";";
		conn = ConnecFactory.getConnection();
		try {
			stam = conn.createStatement();
			rs = stam.executeQuery(sqlGetStu);
			while(rs.next()){
				score = rs.getFloat("stu_sub_score");
				
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}finally{
			ConnecFactory.closeConnection(conn, stam, rs);
		}
		
		return score;
	}
	

	public static void main(String[] args) {
		JFrame jf = new JFrame();
		AddAndDeleteItems ai = new AddAndDeleteItems();
		jf.setLocation(200, 200);
		jf.setSize(500, 500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(ai);
		jf.setVisible(true);
	}

}
