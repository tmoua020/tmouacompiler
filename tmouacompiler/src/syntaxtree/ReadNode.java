/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

/**
 * ReadNode.
 * @author timlymoua
 */
public class ReadNode extends StatementNode 
{

	private VariableNode name;

	public ReadNode(VariableNode name) 
	{
		this.name = name;
	}

	public VariableNode getName() 
	{
		return name;
	}

	/**
	 * Creates a String representation of this read node and its children.
	 * @param level The tree level 
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) 
	{
		String answer = this.indentation(level);
		answer += "Read \n ";
		answer += name.indentedToString(level + 1);
		return answer;
	}

}