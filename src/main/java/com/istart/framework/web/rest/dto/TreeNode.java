package com.istart.framework.web.rest.dto;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import org.apache.commons.collections.CollectionUtils;
 
public class TreeNode implements Serializable {
	
	private static final long serialVersionUID = -8400977253965254172L;
	/**
	 * 节点ID
	 */
    private Long id;
    /**
     * 父节点ID
     */
	private Long parentId;
    /**
     * 节点文本
     */
    protected String lable;
    /**
     * 节点附属信息
     */
    protected Object data;
    /**
     * 父节点
     */
    protected TreeNode parentNode;
    /**
     * 子节点
     */
    protected List<TreeNode> childList = new ArrayList<TreeNode>();
 
    public TreeNode() {
    	
    }
 
    public TreeNode(TreeNode parentNode) {
        this.getParentNode();
    }
    
    /**
     * 是否是叶子节点
     * @return
     */
    public boolean isLeaf() {
       if(CollectionUtils.isEmpty(childList)){
    	   return true;
       }else {
		return false;
       }
    }
 
    /**
     * 插入一个子节点到当前节点中
     * @param treeNode
     */
    public void addChildNode(TreeNode treeNode) {
        if (treeNode.getParentId() == null) {
			treeNode.setParentId(this.id);
		}
        childList.add(treeNode);
    }
 
    /**
     * 返回当前节点的父辈节点集合 
     * @return
     */
    public List<TreeNode> getElders() {
        List<TreeNode> elderList = new ArrayList<TreeNode>();
        TreeNode parentNode = this.getParentNode();
        if (parentNode == null) {
            return elderList;
        } else {
            elderList.add(parentNode);
            elderList.addAll(parentNode.getElders());
            return elderList;
        }
    }
 
    /**
     * 返回当前节点的晚辈集合
     * @return
     */
    public List<TreeNode> getJuniors() {
        List<TreeNode> juniorList = new ArrayList<TreeNode>();
        List<TreeNode> childList = this.getChildList();
        if (childList == null) {
            return juniorList;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode junior = childList.get(i);
                juniorList.add(junior);
                juniorList.addAll(junior.getJuniors());
            }
            return juniorList;
        }
    }
 
    /**
     * 返回当前节点的孩子集合
     * @return
     */
    public List<TreeNode> getChildList() {
        return childList;
    }
 
    /**
     * 删除节点和它下面的晚辈
     */
    public void deleteNode() {
        TreeNode parentNode = this.getParentNode();
        Long id = this.getId();
 
        if (parentNode != null) {
            parentNode.deleteChildNode(id);
        }
    }
 
    /**
     * 删除当前节点的某个子节点
     * @param childId
     */
    public void deleteChildNode(Long childId) {
        List<TreeNode> childList = this.getChildList();
        int childNumber = childList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childList.get(i);
            if (child.getId() == childId) {
                childList.remove(i);
                return;
            }
        }
    }
 
    /**
     * 动态的插入一个新的节点到当前树中
     * @param treeNode
     * @return
     */
    public boolean insertJuniorNode(TreeNode treeNode) {
    	Long juniorParentId = treeNode.getParentId();
        if (this.parentId == juniorParentId) {
            addChildNode(treeNode);
            return true;
        } else {
            List<TreeNode> childList = this.getChildList();
            int childNumber = childList.size();
            boolean insertFlag;
 
            for (int i = 0; i < childNumber; i++) {
                TreeNode childNode = childList.get(i);
                insertFlag = childNode.insertJuniorNode(treeNode);
                if (insertFlag == true)
                    return true;
            }
            return false;
        }
    }
 
    /**
     * 找到一颗树中某个节点
     * @param id
     * @return
     */
    public TreeNode findTreeNodeById(Long id) {
        if (this.id == id)
            return this;
        if (childList.isEmpty() || childList == null) {
            return null;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode child = childList.get(i);
                TreeNode resultNode = child.findTreeNodeById(id);
                if (resultNode != null) {
                    return resultNode;
                }
            }
            return null;
        }
    }
 
    /* 遍历一棵树，层次遍历 */
    public void traverse() {
        if (id < 0)
            return;
        print(this.id);
        if (childList == null || childList.isEmpty())
            return;
        int childNumber = childList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childList.get(i);
            child.traverse();
        }
    }
 
    public void print(Long content) {
        System.out.println(content);
    }
 
    public void print(int content) {
        System.out.println(String.valueOf(content));
    }
 
    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }
 
    public Long getParentId() {
        return parentId;
    }
 
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public TreeNode getParentNode() {
        return parentNode;
    }
 
    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }
 
    public String getLable() {
        return lable;
    }
 
    public void setLable(String lable) {
        this.lable = lable;
    }
 
    public Object getData() {
        return data;
    }
 
    public void setData(Object data) {
        this.data = data;
    }
}
