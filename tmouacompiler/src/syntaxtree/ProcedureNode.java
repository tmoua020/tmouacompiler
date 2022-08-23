/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

import java.util.ArrayList;

/**
 *
 * @author timlymoua
 */
public class ProcedureNode extends StatementNode 
{

	private VariableNode variable = null;
	private ArrayList<ExpressionNode> expNode = new ArrayList<ExpressionNode>();
	   
	public void addExpNode(ExpressionNode input) 
	{
		expNode.add(input);
	}

	public void addAllExpressions(ArrayList<ExpressionNode> input) 
	{
		expNode.addAll(input);
	}

	public void setVariable(VariableNode input) 
	{
		this.variable = input;
	}

	public void setExpNode(ArrayList<ExpressionNode> input) 
	{
		this.expNode = input;
	}

	public ArrayList<ExpressionNode> getExpNode() 
	{
		return expNode;
	}

	public VariableNode getVariable() 
	{
		return this.variable;
	}

	/**
	 * Creates a String representation of this procedure node and its children.
	 * @param level The tree level 
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) 
	{
		String answer = this.indentation(level);
		answer += "Procedure: ";
		answer += this.variable + "\n";
		for (ExpressionNode exp : expNode) {
			answer += exp.indentedToString(level + 1);
		}
		return answer;
	}
}