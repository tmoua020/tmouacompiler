package syntaxtree;

import scanner.TokenType;

/**
 * A special node that is part of the ExpressionNode for "not", PLUS, MINUS
 * @author timlymoua
 */
public class UnaryOperationNode extends ExpressionNode{

    private ExpressionNode expression;
    private TokenType operation;
    
    
    public UnaryOperationNode(TokenType operation) {
       this.operation = operation;
    }
    
    public ExpressionNode getExpression(){
        return expression;
    }
    
 
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
    
    
    public TokenType getOperation(){
        return operation;
    }
    
    public void setOperartion(TokenType operation){
        this.operation = operation;
    }
    
    //Returns the operation as a Sgring
    @Override
    public String toString(){
        return operation.toString();
    }
    
    //Prints out the node with correct indentation on the tree.
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Unary Operation: " + this.operation + "\n";
        answer += expression.indentedToString(level + 1);
        return (answer);
    }
    
    //Checks if both UnaryOperationNode are equal
    @Override
    public boolean equals(Object o) {
        boolean answer = false;
        if (o instanceof UnaryOperationNode) {
            UnaryOperationNode other = (UnaryOperationNode) o;
            if ((this.operation == other.operation) && (this.expression.equals(other.expression))) answer = true;
        }
        return answer;
    }
    

}
