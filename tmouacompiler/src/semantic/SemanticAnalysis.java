/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import java.util.ArrayList;
import java.util.HashMap;
import symboltable.SymbolTable;
import parser.*;
import syntaxtree.*;

/**
 * In the Semantic Analysis, it has a function that is able to check variable 
 * expressions that has been declared. And a Function that is able to assign DataTypes,
 * to every expressionNode on the indentedtoString. Another Function to check
 * type-matching.
 * @author timlymoua
 */
public class SemanticAnalysis {
    
    ProgramNode pNode = null;
    SymbolTable sTable = null;
    
    private HashMap<String, DataType> varTypes = new HashMap<String, DataType>();
    
    public SemanticAnalysis(ProgramNode proNode, SymbolTable sTable)
    {
        this.pNode = pNode;
        this.sTable = sTable;
    }
    
    public ProgramNode analyze(){
        
        ArrayList<VariableNode> varNodes = (ArrayList<VariableNode>) pNode.getVariables().getDeclarations();
        for (int i = 0; i < varNodes.size(); i++){
            varTypes.put(varNodes.get(i).getName(), varNodes.get(i).getType());
        }
        
        CompoundStatementNode mcstatNode = pNode.getMain();
        assignTypeToExp(mcstatNode);
        
        for(int i = 0; i < pNode.getFunctions().getSubProgs().size(); i++){
            CompoundStatementNode subCompStatNode = pNode.getFunctions().getSubProgs().get(i).getMain();
            assignTypeToExp(subCompStatNode);
        }
        return pNode;
    }
    
    /**
     * Verifys the variable in the program are declared before use.
     */
    private void verifyVariableDeclarations(){
        
        ArrayList<String> varsDeclaredNames = new ArrayList<String>();
        for (int i = 0; i < pNode.getVariables().getDeclarations().size(); i++){
            varsDeclaredNames.add(pNode.getVariables().getDeclarations().get(i).getName());
        }
        
        ArrayList<String> varsUsedNames = pNode.getAllVarNames();
        
        for (int i = 0; i < varsUsedNames.size(); i++){
            if (! varsDeclaredNames.contains(varsUsedNames.get(i))){
                System.out.println("Error: The Variable" + varsUsedNames.get(i) + "was never declared");
                System.out.println("Resolve the Variable Name, declare the variable first");
            }
        }
        
    }

    private void assignTypeToExp(CompoundStatementNode compStatNode) {
        
        ArrayList<StatementNode> statementList = compStatNode.getStateNodes();
        for (StatementNode currentStatement : statementList){
            if (currentStatement instanceof AssignmentStatementNode){
                setExpTypes(((AssignmentStatementNode) currentStatement).getExpression());
                
                if ((varTypes.get(((AssignmentStatementNode) currentStatement).getLvalue().getName()) != null))
                    ((AssignmentStatementNode) currentStatement).getLvalue().setType(varTypes.get(((AssignmentStatementNode) currentStatement).getLvalue().getName()));
            }
            else if (currentStatement instanceof WhileStatementNode)
            {
                setExpTypes(((WhileStatementNode) currentStatement).getTest());
            }
            else if (currentStatement instanceof ProcedureNode){
                ArrayList<ExpressionNode> myExpressionNodes = ((ProcedureNode) currentStatement).getExpNode();
                for (ExpressionNode exp : myExpressionNodes)
                    setExpTypes(exp);
            }
            else if (currentStatement instanceof IfStatementNode)
            {
                setExpTypes(((IfStatementNode) currentStatement).getTest());
                StatementNode thenStat = (((IfStatementNode) currentStatement).getThenStatement());
                StatementNode elseStat = (((IfStatementNode) currentStatement).getElseStatement());
                
                CompoundStatementNode ifThenComStat = new CompoundStatementNode();
                
                ifThenComStat.addStatement(thenStat);
                ifThenComStat.addStatement(elseStat);
                
                assignTypeToExp(ifThenComStat);
            }
            
            else if (currentStatement instanceof CompoundStatementNode){
                assignTypeToExp(((CompoundStatementNode) currentStatement));
                
            }
            else if (currentStatement instanceof WriteNode)
            {
                ExpressionNode writeExp = (((WriteNode) currentStatement).getContent());
                
                if (writeExp instanceof VariableNode)
                    ((WriteNode) currentStatement).getContent().setType(varTypes.get(((VariableNode) writeExp).getName()));
                else if (writeExp instanceof OperationNode)
                {
                    setExpTypes((((WriteNode) currentStatement).getContent()));
                } 
            }
            else if (currentStatement instanceof ReadNode){
                ((ReadNode) currentStatement).getName();
                ((ReadNode) currentStatement).getName().setType(varTypes.get(((ReadNode) currentStatement).getName().getName()));
            }
            
        }
    }

    /**
     * Sets the expression types for the expression.
     * @param test 
     */
    private void setExpTypes(ExpressionNode test) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
