/**
 * 
 */
package com.banry.pscm.web.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.banry.pscm.web.mvc.model.TreeNode;

/**
 * @author Xu Dingkui
 * @date 2018年1月2日
 */
public class TreeUtil {

	public static List<TreeNode> buildListToTree(List<TreeNode> dirs) {
        List<TreeNode> roots = findRoots(dirs);
        List<TreeNode> notRoots = (List<TreeNode>) CollectionUtils
                .subtract(dirs, roots);
        for (TreeNode root : roots) {
            root.setChildren(findChildren(root, notRoots));
        }
        return roots;
    }


    public static List<TreeNode> findRoots(List<TreeNode> allTreeNodes) {
        List<TreeNode> results = new ArrayList<TreeNode>();
        for (TreeNode treeNode : allTreeNodes) {
            boolean isRoot = true;
            for (TreeNode comparedOne : allTreeNodes) {
                if (treeNode.getpId().equals(comparedOne.getId())) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                treeNode.setDivlevel(treeNode.getDivlevel());;
                results.add(treeNode);
                treeNode.setpId("");
            }
        }
        return results;
    }


    @SuppressWarnings("unchecked")
    private static List<TreeNode> findChildren(TreeNode root, List<TreeNode> allTreeNodes) {
        List<TreeNode> children = new ArrayList<TreeNode>();


        for (TreeNode comparedOne : allTreeNodes) {
            if (comparedOne.getpId().equals(root.getId())) {
//                comparedOne.setParent(root);
                comparedOne.setDivlevel(comparedOne.getDivlevel());
                children.add(comparedOne);
            }
        }
        List<TreeNode> notChildren = (List<TreeNode>) CollectionUtils.subtract(allTreeNodes, children);
        if (notChildren.size() > 0) {
	        for (TreeNode child : children) {
	            List<TreeNode> tmpChildren = findChildren(child, notChildren);
	            if (tmpChildren.size() == 0) {
	            	break;
	            }
	//            if (tmpChildren == null || tmpChildren.size() < 1) {
	//                child.setIsLeaf(true);
	//            } else {
	//                child.setLeaf(false);
	//            }
	            child.setChildren(tmpChildren);
	        }
        }
        return children;
    }
}
