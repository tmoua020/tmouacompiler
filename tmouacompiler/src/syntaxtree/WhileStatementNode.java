/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntaxtree;

/**
 * A node created for the While statement.
 * That does Boolean Expression.
 * @author timlymoua
 */
public class WhileStatementNode extends StatementNode{
    
    private ExpressionNode test;
    private StatementNode doStatement;
    
    //Gets the test
    public ExpressionNode getTest(){
        return test;
    }
    //Test each iteration
    public void setTest(ExpressionNode test){
        this.test = test;
    }
    //Statement excutes each iteration of while loop.
    public void setDoStatement(StatementNode doStatement){
        this.doStatement = doStatement;
    }
     @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "While:\n";
        answer += this.test.indentedToString(level + 1);
        answer += this.indentation(level) + "Do:\n" + this.doStatement.indentedToString(level + 1);
        return answer;
    }
    
}
