/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

/**
 * Write command.
 * @author timlymoua
 */
public class WriteNode extends StatementNode 
{

	private ExpressionNode content;

	public WriteNode(ExpressionNode content) 
	{
		this.content = content;
	}

	public ExpressionNode getContent() 
	{
		return content;
	}

	/**
	 * Creates a String representation of this write node and its children
	 * @param level  The level on the tree 
	 * @return A String representing this node.
	 */
	@Override
	public String indentedToString(int level) 
	{
		String answer = this.indentation(level);
		answer += "Write\n";
		answer += this.content.indentedToString(level + 1);
		return answer;
	}
}