package com.javaBase.string;

import gudusoft.gsqlparser.nodes.IExpressionVisitor;
import gudusoft.gsqlparser.nodes.TParseTreeNode;

import java.util.ArrayList;
import java.util.List;

public class ExprVisitor implements IExpressionVisitor {
    private List<String> name=new ArrayList<>();
    public boolean exprVisit(TParseTreeNode pNode, boolean isLeafNode){
        String sign = "";
        if (isLeafNode){
            sign ="*";
        }
        this.name.add(pNode.toString());
        System.out.println(this.name.size());
        System.out.println(pNode.toString()+"-----------");


        return true;
    };

    public List<String> getName() {
        return name;
    }
}
