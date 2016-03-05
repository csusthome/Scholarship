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
 * �������������ӻ���ɾ�������߸�����Ŀ������
 * 
 */
public class AddAndDeleteItems extends JPanel implements ActionListener {

	private JLabel jls = new JLabel("������");
	private JLabel jl1 = new JLabel("��������ķ���");
	private JLabel jlshow = new JLabel();

	private JLabel jl2 = new JLabel("�����Ŀ��");
	private JLabel jl3 = new JLabel("��Ŀ���ƣ�");
	private JLabel jl4 = new JLabel("��Ŀ���룺");
	private JLabel jl5 = new JLabel("��Ŀѧ�֣�");

	// �������Ŀѧ�֣���Ŀ���ƣ���Ŀ��š�
	private JTextField jtfName = new JTextField();
	private JTextField jtfNum = new JTextField();
	private JTextField jtfScore = new JTextField();
	// ��������jcombox��
	private JComboBox jcbDo;
	private JComboBox jcbCate;
	private JComboBox jcbItem;
	private JComboBox jcbAdd;

	//���ȷ������Ӱ�ť��
	private JButton jbtDo = new JButton("ȷ��");
	private JButton jbtAdd = new JButton("���");

	// ���������
	private JTextField jtfChangeScore = new JTextField();
	// �����Ƿ�ѡ�����һ��һ���ļ�ر�����
	private boolean isSelect = false;
	
	private JCheckBox jcheckTiyu = new JCheckBox("������");
	
	//�������ݿ��������
	private Connection conn;
	private Statement stam;
	private ResultSet rs;
	
	
	/**
	 * ���췽����
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
	 * ������Ӹ����¼���
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
	 * �������ɸ��������塣
	 */
	private void initFrame() {

		this.setLayout(null);

		jls.setBounds(30, 30, 100, 30);
		this.add(jls);

		jcbDo = new JComboBox();
		jcbDo.addItem("ɾ����Ŀ");
		jcbDo.addItem("������Ŀ");
		jcbDo.setBounds(30, 60, 100, 30);
		this.add(jcbDo);

		jcbCate = new JComboBox();
		jcbCate.addItem("������");
		jcbCate.addItem("������");
		jcbCate.addItem("������");
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
		jcbAdd.addItem("������");
		jcbAdd.addItem("������");
		jcbAdd.addItem("������");
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
	 * ������Ӹ����¼���
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		//���������������������Ȼ���������
		if (e.getSource() == jcbCate) {

			if (jcbCate.getSelectedItem().toString().trim().equals("������")) {

				this.setItems(jcbItem, "������");
			}
			if (jcbCate.getSelectedItem().toString().trim().equals("������")) {

				this.setItems(jcbItem, "������");
			}
			if (jcbCate.getSelectedItem().toString().trim().equals("������")) {

				this.setItems(jcbItem, "������");
			}
		} else if (e.getSource() == jcbDo) {

			//���Ĳ�����
			if (jcbDo.getSelectedItem().toString().trim().equals("������Ŀ")) {
				if (jcbItem != null) {
					this.jtfChangeScore.setEditable(true);
				}
			}

			//ɾ������
			if (jcbDo.getSelectedItem().toString().trim().equals("ɾ����Ŀ")) {
				this.jtfChangeScore.setEditable(false);
			}
		} else if (e.getSource() == jcbItem) {
			// �˴��Ƚ�ѡ�е���Ŀ�ֽ������Ҳ���Ƿֽ�����ǵ���Ŀ���롣
			// Ȼ���ٽ��и��ġ�ɾ��������

			// Ҫ���jcbItem��getselectedItem�Ƿ�Ϊ�գ���Ϊ�����������
			// ���Ҵ���ѡ��ʱ�������selecteditem�ͻ�Ϊnull��������jcbItem��
			if (jcbItem.getSelectedItem() != null) {
				StringBuffer str = new StringBuffer();
				str.append(jcbItem.getSelectedItem().toString().trim());
				// ͨ����ȡ�̶��ĳ����ַ������õ���Ŀ���롣
				String num = str
						.subSequence(str.length() - 6, str.length() - 1)
						.toString();
				// System.out.println(num);
				// System.out.println(str);
				float score = this.getScore(num, jcbCate.getSelectedItem()
						.toString().trim());
				// ������ת����������floatת��Ϊstring��
				jtfChangeScore.setText(score + "");
			}

		} else if (e.getSource() == jbtDo) {

			// �˴�Ϊɾ�����߸�����Ŀ�����Ĳ���

			if (jcbItem.getSelectedItem() == null) {
				JOptionPane.showMessageDialog(this, "��ѡ����Ӧ������", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// �õ�name����num��
			StringBuffer str = new StringBuffer();
			str.append(jcbItem.getSelectedItem());
			String itemName = str.substring(0, str.length() - 7).toString();
			String itemNum = str.substring(str.length() - 6, str.length() - 1);


			if (jcbDo.getSelectedItem().toString().trim().equals("������Ŀ")) {
				// ����ʽ
				if (!checkPatternStr(jtfChangeScore.getText().trim())) {
					JOptionPane.showMessageDialog(this, "����ֻ����100���µ����������һλС��",
							"����", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// ��������������������
				//ÿһ��Ѹ��ĵ�ͬѧ�ĸ��ĵķ������¼��㣬���²��롣
				// ������
				if (jcbCate.getSelectedItem().toString().trim().equals("������")) {
					float finalDeyu = 0.0f;
					DeyuItem di = new DeyuItem();
					di.setDeyu_name(itemName);
					di.setDeyu_num(itemNum);
					di.setDeyu_score(Float.parseFloat(jtfChangeScore.getText()
							.trim()));
					DeyuItemDao did = new DeyuItemDao();
					if (did.setByNum(di)) {
						//�����棬����Ӧ���ڵ���Ӧ��¼��
						List stuList = new ArrayList();
						stuList = this.getStuNum("stu_deyu_score","deyu_num", itemNum);
						for(int i = 0;i < stuList.size();i++){
							finalDeyu = 0.0f;
							List itemList = this.getItemNumByStudent("stu_deyu_score", stuList.get(i).toString());
							for(int j = 0;j < itemList.size();j++){
								finalDeyu = finalDeyu + this.getItemScoreByItemNum("deyuitem", "deyu_score", "deyu_num", itemList.get(j).toString());
					
							}
							//��һ��ͬѧ���µ����ܷ֡�
							finalDeyu = finalDeyu*0.25f;
							this.updateNewScore("stu_deyu", finalDeyu, stuList.get(i).toString());
							
						}
						
						JOptionPane.showMessageDialog(this, "�����ɹ�", "��ʾ",
								JOptionPane.YES_OPTION);
						return;
					} else {
						JOptionPane.showMessageDialog(this, "����ʧ��", "��ʾ",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				// ������
				if (jcbCate.getSelectedItem().toString().trim().equals("������")) {
					float finalZhiyu = 0.0f;
					ZhiyuItem zi = new ZhiyuItem();
					zi.setSubj_name(itemName);
					zi.setSubj_num(itemNum);
					zi.setSubj_score(Float.parseFloat(jtfChangeScore.getText()
							.trim()));
					ZhiyuItemDao zid = new ZhiyuItemDao();
					if (zid.setByNum(zi)) {
						
						
						//�����棬����Ӧ���ڵ���Ӧ��¼��
						List stuList = new ArrayList();
						stuList = this.getStuNum("stu_zhiyu_score","subj_num", itemNum);
						for(int i = 0;i < stuList.size();i++){
							//�õ�����λѧ��
							finalZhiyu = 0.0f;
							float n = 0.0f;
							List itemList = this.getItemNumByStudent("stu_zhiyu_score", stuList.get(i).toString());
							for(int j = 0;j < itemList.size();j++){
								//�õ�ÿλѧ����ÿ��������
								
									for(int z = 0;z < this.getZhiyuScore().size();z++){
										//�õ�ÿ�������εķ������������
										if(stuList.get(i).equals(this.getZhiyuScore().get(z).getSubj_num())&&itemList.get(j).equals(this.getZhiyuScore().get(z).getSubj_name())&&!this.getPENum().contains(itemList.get(j).toString())){
											finalZhiyu += this.getZhiyuScore().get(i).getFinalScore()*this.getItemScoreByItemNum("zhiyuitem", "subj_score", "subj_num", itemList.get(j).toString());
											n += this.getItemScoreByItemNum("zhiyuitem", "subj_score", "subj_num", itemList.get(j).toString());
											break;
										}
									}
								


							}
							
							//������ÿλѧ����
							finalZhiyu = finalZhiyu/n;
							//��һ��ͬѧ���µ����ܷ֡�
							finalZhiyu = finalZhiyu*0.70f;
							if(finalZhiyu >= 70.0f ){
								finalZhiyu = 70.0f;
							}
							
							//������������
							finalZhiyu += this.getCetByStuNum(stuList.get(i).toString());
							this.updateNewScore("stu_zhiyu", finalZhiyu, stuList.get(i).toString());

							
						}
						
						
						
						JOptionPane.showMessageDialog(this, "�����ɹ�", "��ʾ",
								JOptionPane.YES_OPTION);
						return;
					} else {
						JOptionPane.showMessageDialog(this, "����ʧ��", "��ʾ",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				// ������
				if (jcbCate.getSelectedItem().toString().trim().equals("������")) {
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
						
						//����������޸�������Ĺ���
						
						
						//�����棬����Ӧ���ڵ���Ӧ��¼��
						List stuList = new ArrayList();
						stuList = this.getStuNum("stu_zhiyu_score","subj_num", itemNum);
						for(int i = 0;i < stuList.size();i++){
							//�õ�����λѧ��
							corseTiyu = 0.0f;
							float n = 0.0f;
							List itemList = this.getItemNumByStudent("stu_zhiyu_score", stuList.get(i).toString());
							for(int j = 0;j < itemList.size();j++){
								//�õ�ÿλѧ����ÿ��������,���Ҵ���ѡ��ÿ��ͬѧ�������Ρ�
								
									for(int z = 0;z < this.getZhiyuScore().size();z++){
										//�õ�ÿ�������εķ������������
										if(stuList.get(i).equals(this.getZhiyuScore().get(z).getSubj_num())&&itemList.get(j).equals(this.getZhiyuScore().get(z).getSubj_name())&&this.getPENum().contains(itemList.get(j).toString())){
											corseTiyu += this.getZhiyuScore().get(i).getFinalScore()*this.getItemScoreByItemNum("zhiyuitem", "subj_score", "subj_num", itemList.get(j).toString());
											n += this.getItemScoreByItemNum("zhiyuitem", "subj_score", "subj_num", itemList.get(j).toString());
											break;
										}
									}
								


							}
							
							//������ÿλѧ����
							corseTiyu = corseTiyu/n;
							//��һ��ͬѧ�������ܷ֡�
							corseTiyu = corseTiyu*0.70f;

							
							//����������ļ���
							
								List itemListTi = this.getItemNumByStudent("stu_tiyu_score", stuList.get(i).toString());
								for(int j = 0;j < itemList.size();j++){
									itemTiyu = itemTiyu + this.getItemScoreByItemNum("tiyuitem", "tiyu_score", "tiyu_num", itemList.get(j).toString());
						
								}
								
								finalTiyu = corseTiyu + itemTiyu;
								//��һ��ͬѧ���µ����ܷ֡�
								finalTiyu = finalTiyu*0.05f;
								if(finalTiyu >= 5.0f){
									finalTiyu = 5.0f;
								}
								this.updateNewScore("stu_tiyu", finalTiyu, stuList.get(i).toString());


							
						}
						
						
						
						
						
						
						
						JOptionPane.showMessageDialog(this, "�����ɹ�", "��ʾ",
								JOptionPane.YES_OPTION);
						return;
					} else {
						JOptionPane.showMessageDialog(this, "����ʧ��", "��ʾ",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			} else if (jcbDo.getSelectedItem().toString().trim().equals("ɾ����Ŀ")) {
				// �˴�Ϊɾ����Ŀ��顣
				// ѯ��
				int i = JOptionPane
						.showConfirmDialog(this, "��ȷ��ɾ�������Ŀ��(��ɾ��������ص���Ϣ��)",
								"ѯ��", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
				if (i == 0) {
					// ȷ�ϲ���
					// ���������
					if (jcbCate.getSelectedItem().toString().trim()
							.equals("������")) {
						DeyuItemDao did = new DeyuItemDao();
						if (did.remove(itemNum)) {
							
							//������Ӧ�ķ�����
							//�õ�Ҫ���ĵ�����ͬѧ��ѧ��
							List list = this.getStuNum("stu_deyu_score", "deyu_num", itemNum);
							
							for(int w = 0;w < list.size();w++){
								float initScore = this.getOneFinalScoreByStuNum("stu_deyu", list.get(w).toString());
								float deleteItemScore = this.getItemScoreByItemNum("deyuitem", "deyu_score", "deyu_num", itemNum);
								initScore /= 0.25f;
								initScore -= deleteItemScore;
								initScore *= 0.25f;
								this.updateNewScore("deyu_score", initScore, list.get(w).toString());
								
							}
							
							
							
							
							
							JOptionPane.showMessageDialog(this, "ɾ���ɹ�", "��ʾ",
									JOptionPane.YES_OPTION);
							this.setItems(jcbItem, "������");
						} else {
							JOptionPane.showMessageDialog(this, "ɾ��ʧ��", "��ʾ",
									JOptionPane.ERROR_MESSAGE);
						}
					}

					// ���������
					if (jcbCate.getSelectedItem().toString().trim()
							.equals("������")) {
						ZhiyuItemDao zid = new ZhiyuItemDao();
						if (zid.remove(itemNum)) {
							
							//������Ӧ�ķ�����
							//�õ�Ҫ���ĵ�����ͬѧ��ѧ��
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
							
							
							
							
							JOptionPane.showMessageDialog(this, "ɾ���ɹ�", "��ʾ",
									JOptionPane.YES_OPTION);
							this.setItems(jcbItem, "������");
						} else {
							JOptionPane.showMessageDialog(this, "ɾ��ʧ��", "��ʾ",
									JOptionPane.ERROR_MESSAGE);
						}
					}

					// ���������
					if (jcbCate.getSelectedItem().toString().trim()
							.equals("������")) {
						TiyuItemDao tid = new TiyuItemDao();
						if (tid.remove(itemNum)) {
							
							
							
							//������Ӧ�ķ�����
							//�õ�Ҫ���ĵ�����ͬѧ��ѧ��
							List list = this.getStuNum("stu_tiyu_score", "tiyu_num", itemNum);
							
							for(int w = 0;w < list.size();w++){
								float initScore = this.getOneFinalScoreByStuNum("stu_tiyu", list.get(w).toString());
								float deleteItemScore = this.getItemScoreByItemNum("tiyuitem", "tiyu_score", "tiyu_num", itemNum);
								initScore /= 0.05f;
								
								//��Ҫ��ȥ�����εĳɼ���,���ü�ȥ�ˣ���Ϊ����Ǽ��ϵġ�
								
								
								
								
								
								
								
								
								
								initScore -= deleteItemScore;
								initScore *= 0.05f;
								this.updateNewScore("deyu_score", initScore, list.get(w).toString());
								
							}
							
							
							
							
							
							JOptionPane.showMessageDialog(this, "ɾ���ɹ�", "��ʾ",
									JOptionPane.YES_OPTION);
							this.setItems(jcbItem, "������");
						} else {
							JOptionPane.showMessageDialog(this, "ɾ��ʧ��", "��ʾ",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}
			}

		} else if (e.getSource() == jbtAdd) {

			// �˴�Ϊ�����Ŀ�Ĳ�����
			String itemName = jtfName.getText().trim();
			String itemNum = jtfNum.getText().trim();
			String itemScore = jtfScore.getText().trim();
			int target;
			if(jcheckTiyu.isSelected()){
				target = 1;
			}else{
				target = 0;
			}
			
			//�������Ƿ���
			
			if (itemName.equals("")) {
				JOptionPane.showMessageDialog(this, "����������ӵ���Ŀ���ƣ�", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (itemNum.equals("")) {
				JOptionPane.showMessageDialog(this, "����������ӵ���Ŀ���룡", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (itemScore.equals("")) {
				JOptionPane.showMessageDialog(this, "����������ӵ���Ŀ������", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!this.checkNum(itemNum)) {
				System.out.println(itemNum);
				JOptionPane.showMessageDialog(this, "��Ŀ����ֻ����5λ�������֣�", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!this.checkPatternStr(itemScore)) {
				JOptionPane.showMessageDialog(this,
						"��Ŀ����ֻ����100���ڵ�������С���������һλС����", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (itemName.length() > 16) {
				JOptionPane.showMessageDialog(this, "��Ŀ�������16λ�ַ���", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			int i = JOptionPane.showConfirmDialog(this, "ȷ����Ӵ�����Ŀ��¼��", "ѯ��",
					JOptionPane.YES_NO_OPTION);
			if (i == 0) {
				if (jcbAdd.getSelectedItem().toString().trim().equals("������")) {
					// �˴�Ϊ��ӵ����
					
					if(!this.checkDuplicate("������", itemNum)){
						return;
					}
					
					
					DeyuItem di = new DeyuItem();
					di.setDeyu_name(itemName);
					di.setDeyu_num(itemNum);
					di.setDeyu_score(Float.parseFloat(itemScore));
					DeyuItemDao did = new DeyuItemDao();
					if (did.add(di)) {
						JOptionPane.showMessageDialog(this, "��Ӽ�¼�ɹ�", "��ϲ",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					} else {
						JOptionPane.showMessageDialog(this, "��Ӽ�¼ʧ��", "����",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

				}

				if (jcbAdd.getSelectedItem().toString().trim().equals("������")) {
					// �˴�Ϊ��ӵ����
					
					if(!this.checkDuplicate("������", itemNum)){
						return;
					}
					ZhiyuItem zi = new ZhiyuItem();
					zi.setSubj_name(itemName);
					zi.setSubj_num(itemNum);
					zi.setSubj_score(Float.parseFloat(itemScore));
					zi.setTarget(target);
					ZhiyuItemDao zid = new ZhiyuItemDao();
					if(zid.add(zi)){
						JOptionPane.showMessageDialog(this, "��Ӽ�¼�ɹ�", "��ϲ",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}else{
						JOptionPane.showMessageDialog(this, "��Ӽ�¼ʧ��", "����",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				if (jcbAdd.getSelectedItem().toString().trim().equals("������")) {
					
					if(!this.checkDuplicate("������", itemNum)){
						return;
					}
					// �˴�Ϊ��ӵ����
					TiyuItem ti = new TiyuItem();
					ti.setTiyu_name(itemName);
					ti.setTiyu_num(itemNum);
					ti.setTiyu_score(Float.parseFloat(itemScore));
					TiyuItemDao tid = new TiyuItemDao();
					if(tid.add(ti)){
						JOptionPane.showMessageDialog(this, "��Ӽ�¼�ɹ�", "��ϲ",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}else{
						JOptionPane.showMessageDialog(this, "��Ӽ�¼ʧ��", "����",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}

		}else if(e.getSource() == jcbAdd){
			//Ȼ�����������������
			if(jcbAdd.getSelectedItem().toString().trim().equals("������")){
				jcheckTiyu.setEnabled(true);
			}else{
				jcheckTiyu.setEnabled(false);
			}
		}

	}
	
	/**
	 * ����Ƿ��ظ���ѧ����Ŀ
	 * @param str
	 * @param itemNum
	 * @return
	 */
	private boolean checkDuplicate(String str,String itemNum){
		List list = this.getItem(str);
		for(int j = 0;j < list.size();j++){
			if(str.equals("������")){
				DeyuItem di = (DeyuItem)list.get(j);
				if(di.getDeyu_num().equals(itemNum)){
					JOptionPane.showMessageDialog(this, "�ظ�����Ŀ����", "����", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}else if(str.equals("������")){
				TiyuItem ti = (TiyuItem)list.get(j);
				if(ti.getTiyu_num().equals(itemNum)){
					JOptionPane.showMessageDialog(this, "�ظ�����Ŀ����", "����", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}else if(str.equals("������")){
				ZhiyuItem zi = (ZhiyuItem)list.get(j);
				if(zi.getSubj_num().equals(itemNum)){
					JOptionPane.showMessageDialog(this, "�ظ�����Ŀ����", "����", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}

		}
		return true;
	}
	
	/**
	 * �õ��������Ŀ��
	 * @param str
	 * @return
	 */
	private List getItem(String str){
		if(str.equals("������")){
			return new DeyuItemDao().findAllBy();
		}
		if(str.equals("������")){
			return new ZhiyuItemDao().findAllBy();
		}
		if(str.equals("������")){
			return new TiyuItemDao().findAllBy();
		}
		return null;
	}

	/**
	 * �˷������ڼ����ӵ���Ŀ�����Ƿ���Ϲ�� Ҳ����Ҫ��ֻ����6λ�������֡�
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
	 * ���ڼ����Ļ�����ķ������Ƿ�����ַ���Ҫ��
	 * Ҫ��100���ڵģ������ֻ��һλС����
	 * @param str
	 * @return
	 */
	private boolean checkPatternStr(String str) {
		// �����ֲ�ͬ�����
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
	 * �õ�ĳһ��ķ�����
	 * @param num
	 * @param type
	 * @return
	 */
	private float getScore(String num, String type) {

		if (type == "������") {
			DeyuItem di = new DeyuItem();
			DeyuItemDao did = new DeyuItemDao();
			di = did.findByid(num);
			return di.getDeyu_score();
		} else if (type == "������") {
			TiyuItem ti = new TiyuItem();
			TiyuItemDao tid = new TiyuItemDao();
			ti = tid.findByid(num);
			return ti.getTiyu_score();
		} else if (type == "������") {
			ZhiyuItem zi = new ZhiyuItem();
			ZhiyuItemDao zid = new ZhiyuItemDao();
			zi = zid.findByid(num);
			return zi.getSubj_score();
		} else {
			return 0.0f;
		}

	}
	/**
	 * ���ø��Ҳ���������˵���
	 * @param jcb
	 * @param cate
	 */
	private void setItems(JComboBox jcb, String cate) {
		jcb.removeAllItems();

		if (cate.equals("������")) {

			DeyuItem di = new DeyuItem();
			DeyuItemDao did = new DeyuItemDao();
			List list = did.findAll(di.getClass());
			for (int i = 0; i < list.size(); i++) {
				// �õ�һ��������
				DeyuItem deyu = (DeyuItem) list.get(i);
				// ��������������ֶ�ƴװ����
				String item = deyu.getDeyu_name() + "(" + deyu.getDeyu_num()
						+ ")";
				jcb.addItem(item);
			}
		}

		if (cate.equals("������")) {

			ZhiyuItem zi = new ZhiyuItem();
			ZhiyuItemDao zid = new ZhiyuItemDao();
			List list = zid.findAll(zi.getClass());
			for (int i = 0; i < list.size(); i++) {
				// �õ�һ��������
				ZhiyuItem zhiyu = (ZhiyuItem) list.get(i);
				// ��������������ֶ�ƴװ����
				String item = zhiyu.getSubj_name() + "(" + zhiyu.getSubj_num()
						+ ")";
				jcb.addItem(item);
			}

		}

		if (cate.equals("������")) {

			TiyuItem ti = new TiyuItem();
			TiyuItemDao tid = new TiyuItemDao();
			List list = tid.findAll(ti.getClass());
			for (int i = 0; i < list.size(); i++) {
				// �õ�һ��������
				TiyuItem tiyu = (TiyuItem) list.get(i);
				// ��������������ֶ�ƴװ����
				String item = tiyu.getTiyu_name() + "(" + tiyu.getTiyu_num()
						+ ")";
				jcb.addItem(item);
			}

		}
	}
	
	/**
	 * Ҫ����ĳһ�����Ӧ��ѧ���б����Ҫ�ı䡣
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
	 * �õ�Ҫ���ĵ�ѧ����������Ŀ�š�
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
	 * ��ĳһ��õ����ķ�����
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
	 * ������������ı��³ɼ��ֵܷġ�
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
				JOptionPane.showMessageDialog(this, "����ʧ��741");
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
	 * �õ������֣�Ҳ����stu_zhiyu_score��������ʹ��zhiyuitem����ŵġ�
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
	 * �õ�������Ŀγ̱�š�
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
	 * ��д
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
	 * ��������������õ�ĳһλͬѧ�ĵ��������������������ֵġ�
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
	 * �õ�ĳһ�������ɼ�����������
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
