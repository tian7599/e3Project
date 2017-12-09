package cn.e3.utils;

import java.io.Serializable;

public class TreeNode implements Serializable {

	/**
	 * [{
	 * 	"id": 1;   节点id
	 * 	"text" : "Node1";  节点名称
	 * 	"state" : "closed";  节点状态  
	 * ( 如果 is_parent=1,表示节点有子节点,state=closed(可打开)
	 *  如果 is_parent=0,表示节点无子节点,state=open(已经打开,无法再打开) )
	 * 	}]
	 */
	private Long id;
	private String text;
	private String state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
