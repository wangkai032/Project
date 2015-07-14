package com.board.model;



//¹ê§@Serializable,¼gJAVABean
public class BoardVO implements java.io.Serializable {

	private int b_id;
	private int m_id;
	private String b_name;
	private int c_id;
	private String b_description;
	private String b_authority;
	
//	public BoardVO(int b_id, int m_id, String b_name, int c_id, 
//			String b_description, String b_authority){
//		
//	}
	public int getB_id() {
		return b_id;
	}

	public void setB_id(int b_id) {
		this.b_id = b_id;
	}

	public int getM_id() {
		return m_id;
	}

	public void setM_id(int m_id) {
		this.m_id = m_id;
	}

	public String getB_name() {
		return b_name;
	}

	public void setB_name(String b_name) {
		this.b_name = b_name;
	}

	public int getC_id() {
		return c_id;
	}

	public void setC_id(int c_id) {
		this.c_id = c_id;
	}

	public String getB_description() {
		return b_description;
	}

	public void setB_description(String b_description) {
		this.b_description = b_description;
	}

	public String getB_authority() {
		return b_authority;
	}

	public void setB_authority(String b_authority) {
		this.b_authority = b_authority;
	}
	
	
	

}//end of class
