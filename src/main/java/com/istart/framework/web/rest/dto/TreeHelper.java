package com.istart.framework.web.rest.dto;

import java.util.List;  
import java.util.Iterator;  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

/**
 * 树或者森林的帮助类:如果root为null则是森林，如果roots为空集合则为树
 * @author Administrator
 *
 */
public class TreeHelper {  
	/**
	 * 根节点(树)
	 */
    private TreeNode root;  
    /**
     * 根节点(森林)
     */
    private List<TreeNode> roots = new ArrayList<TreeNode>();
    /**
     * 所有的节点
     */
    private List<TreeNode> tempNodeList;  
    
    /**
     * 是否是一颗有效的树或者有效的森林
     */
    private boolean isValidTree = true;  
  
    public TreeHelper() {  
    }  
    
    /**
     * 根据节点结合构造一个完整的树或者一片森林 	
     * @param treeNodeList
     */
    public TreeHelper(List<TreeNode> treeNodeList) {  
        tempNodeList = treeNodeList;  
        generateTree();  
    }  
  
    public static TreeNode getTreeNodeById(TreeNode tree, Long id) {  
        if (tree == null)  
            return null;  
        TreeNode treeNode = tree.findTreeNodeById(id);  
        return treeNode;  
    }  
  
    /** generate a tree from the given treeNode or entity list */  
    public void generateTree() {  
    	Map<String,TreeNode> nodeMap = putNodesIntoMap();  
        putChildIntoParent(nodeMap);  
    }  
  
    /** 
     * put all the treeNodes into a hash table by its id as the key 
     *  同时找到根节点
     * @return hashmap that contains the treenodes 
     */  
    protected Map<String,TreeNode> putNodesIntoMap() {  
    	Long maxId = Long.MAX_VALUE;  
    	TreeNode tempNode = null;
    	
        Map<String,TreeNode> nodeMap = new HashMap<String, TreeNode>();  
        Iterator<TreeNode> it = tempNodeList.iterator();  
        while (it.hasNext()) {
            TreeNode treeNode = it.next();  
            Long parentId = treeNode.getParentId();  
            if (parentId == null) {
				this.roots.add(treeNode);
			}else if (parentId < maxId) {  
                maxId = parentId;  
                tempNode = treeNode;  
            }  
            String keyId = String.valueOf(treeNode.getId());  
            nodeMap.put(keyId, treeNode);  
        }  
        
        if (CollectionUtils.isEmpty(this.roots)) {
        	this.root = tempNode;
		}
        return nodeMap;  
    }  
  
    /** 
     * set the parent nodes point to the child nodes 
     *  
     * @param nodeMap 
     *            a hashmap that contains all the treenodes by its id as the key 
     */  
    protected void putChildIntoParent(Map<String,TreeNode> nodeMap) {  
        Iterator<TreeNode> it = nodeMap.values().iterator();  
        while (it.hasNext()) {  
            TreeNode treeNode = (TreeNode) it.next();  
            Long parentId = treeNode.getParentId();  
            String parentKeyId = String.valueOf(parentId);  
            if (nodeMap.containsKey(parentKeyId)) {  
                TreeNode parentNode = (TreeNode) nodeMap.get(parentKeyId);  
                if (parentNode == null) {  
                    this.isValidTree = false;  
                    return;  
                } else {  
                    parentNode.addChildNode(treeNode);  
                    // System.out.println("childId: " +treeNode.getSelfId()+" parentId: "+parentNode.getSelfId());  
                }  
            }  
        }  
    }  
  
    /** initialize the tempNodeList property */  
    protected void initTempNodeList() {  
        if (this.tempNodeList == null) {  
            this.tempNodeList = new ArrayList<TreeNode>();  
        }  
    }  
  
    /** add a tree node to the tempNodeList */  
    public void addTreeNode(TreeNode treeNode) {  
        initTempNodeList();  
        this.tempNodeList.add(treeNode);  
    }  
  
    /** 
     * insert a tree node to the tree generated already 
     *  
     * @return show the insert operation is ok or not 
     */  
    public boolean insertTreeNode(TreeNode treeNode) {  
        boolean insertFlag = root.insertJuniorNode(treeNode);  
        return insertFlag;  
    }  
  
    /** 
     * adapt the entities to the corresponding treeNode 
     *  
     * @param entityList 
     *            list that contains the entities 
     *@return the list containg the corresponding treeNodes of the entities 
     */  
//    public static List<TreeNode> changeEnititiesToTreeNodes(List entityList) {  
//    	ShopCategory orgEntity = new ShopCategory();  
//        List<TreeNode> tempNodeList = new ArrayList<TreeNode>();  
//        TreeNode treeNode;  
//  
//        Iterator it = entityList.iterator();  
//        while (it.hasNext()) {  
//            orgEntity = (ShopCategory) it.next();  
//            treeNode = new TreeNode();  
//            treeNode.setData(orgEntity);  
//            treeNode.setParentId(orgEntity.getParentid());  
//            treeNode.setId(orgEntity.getId());  
//            treeNode.setLable(orgEntity.getName());  
//            tempNodeList.add(treeNode);  
//        }  
//        return tempNodeList;  
//    }  
  
    public boolean isValidTree() {  
        return this.isValidTree;  
    }  
  
    public TreeNode getRoot() {  
        return root;  
    }  
  
    public List<TreeNode> getRoots() {
		return roots;
	}

	public void setRoots(List<TreeNode> roots) {
		this.roots = roots;
	}

	public void setRoot(TreeNode root) {  
        this.root = root;  
    }  
  
    public List<TreeNode> getTempNodeList() {  
        return tempNodeList;  
    }  
  
    public void setTempNodeList(List<TreeNode> tempNodeList) {  
        this.tempNodeList = tempNodeList;  
    }  
    
//    public static void main(String[] args) {
//		
//		List<TreeEntity> organizationEntitys = new ArrayList<TreeEntity>();
//		
//		TreeEntity organizationEntity1 = new TreeEntity();
//		organizationEntity1.setOrgId(1L);
//		organizationEntity1.setParentId(null);
//		organizationEntity1.setOrgName("售前");
//		organizationEntitys.add(organizationEntity1);
//		
//		TreeEntity organizationEntity2 = new TreeEntity();
//		organizationEntity2.setOrgId(2L);
//		organizationEntity2.setParentId(1L);
//		organizationEntity2.setOrgName("尺码等商品性质");
//		organizationEntitys.add(organizationEntity2);
//		
//		TreeEntity organizationEntity3 = new TreeEntity();
//		organizationEntity3.setOrgId(3L);
//		organizationEntity3.setParentId(2L);
//		organizationEntity3.setOrgName("尺码标准");
//		organizationEntitys.add(organizationEntity3);
//		
//		TreeEntity organizationEntity4 = new TreeEntity();
//		organizationEntity4.setOrgId(4L);
//		organizationEntity4.setParentId(2L);
//		organizationEntity4.setOrgName("询问尺码");
//		organizationEntitys.add(organizationEntity4);
//		
//		TreeEntity organizationEntity19 = new TreeEntity();
//		organizationEntity19.setOrgId(19L);
//		organizationEntity19.setParentId(1L);
//		organizationEntity19.setOrgName("快递相关");
//		organizationEntitys.add(organizationEntity19);
//		
//		TreeEntity organizationEntity20 = new TreeEntity();
//		organizationEntity20.setOrgId(20L);
//		organizationEntity20.setParentId(19L);
//		organizationEntity20.setOrgName("是否包邮");
//		organizationEntitys.add(organizationEntity20);
//		
//		//根节点2
//		TreeEntity organizationEntity52 = new TreeEntity();
//		organizationEntity52.setOrgId(52L);
//		organizationEntity52.setParentId(null);
//		organizationEntity52.setOrgName("售后");
//		organizationEntitys.add(organizationEntity52);
//		
//		TreeEntity organizationEntity53 = new TreeEntity();
//		organizationEntity53.setOrgId(53L);
//		organizationEntity53.setParentId(52L);
//		organizationEntity53.setOrgName("退换货相关");
//		organizationEntitys.add(organizationEntity53);
//		
////		List<TreeNode> treeNodes = TreeHelper.changeEnititiesToTreeNodes(organizationEntitys);
//		
//		List<TreeNode> treeNodes = null;
//		TreeHelper helper = new TreeHelper(treeNodes);
//		
//		List<TreeNode> roots = helper.getRoots();
//		for (TreeNode treeNode : roots) {
//			List<TreeNode> treeNodes2 = treeNode.getJuniors();
//			System.out.println(treeNodes2.size());
//		}
//	}
  
}  
