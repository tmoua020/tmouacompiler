/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codegeneration;

/**
 * Creates a code for EquationTree.
 * @author timlymoua
 */

import syntaxtree.TokenType;
import syntaxtree.ExpressionNode;
import syntaxtree.OperationNode;
import syntaxtree.ValueNode;

public class CodeGeneration {
    
    private int currentTRegister = 0;
    
    /**
     * Starts the code from the root node by writing the outline of the
     * assembly code, and telling the root node to write its answer into $s0.
     *
     * @param root The root node of the equation to be written
     * @return A String of the assembly code.
     */
    public String writeCodeForRoot( ExpressionNode root) 
    {
        StringBuilder code = new StringBuilder();
        code.append( ".data\n");
        code.append( "answer:   .word   0\n\n\n");
        code.append( ".text\n");
        code.append( "main:\n");
        
        String nodeCode = null;
        int tempTRegValue = this.currentTRegister;
        nodeCode = writeCode( root, "$s0");
        this.currentTRegister = tempTRegValue;
        code.append( nodeCode);
        code.append( "sw     $s0,   answer\n");
        code.append( "addi   $v0,   10\n");
        code.append( "syscall\n");
        return( code.toString());
    }
    
    /**
     * Writes code for the given node.
     * This generic write code takes any ExpressionNode, and then
     * recasts according to subclass type for dispatching.
     * @param node The node for which to write code.
     * @param reg The register in which to put the result.
     * @return 
     */
    public String writeCode( ExpressionNode node, String reg) {
        String nodeCode = null;
        if( node instanceof OperationNode) {
            nodeCode = writeCode( (OperationNode)node, reg);
        }
        else if( node instanceof ValueNode) {
            nodeCode = writeCode( (ValueNode)node, reg);
        }
        return( nodeCode);
    }
    
    /**
     * Writes code for an operations node.
     * The code is written by gathering the child nodes' answers into
     * a pair of registers, and then executing the op on those registers,
     * placing the result in the given result register.
     * @param opNode The operation node to perform.
     * @param resultRegister The register in which to put the result.
     * @return The code which executes this operation.
     */
    public String writeCode( OperationNode opNode, String resultRegister)
    {
        String code;
        ExpressionNode left = opNode.getLeft();
        String leftRegister = "$t" + currentTRegister++;
        code = writeCode( left, leftRegister);
        ExpressionNode right = opNode.getRight();
        String rightRegister = "$t" + currentTRegister++;
        code += writeCode( right, rightRegister);
        TokenType kindOfOp = opNode.getOperation();
        if( kindOfOp == TokenType.PLUS)
        {
            // add resultregister, left, right 
            code += "add    " + resultRegister + ",   " + leftRegister +
                    ",   " + rightRegister + "\n";
        }
        if( kindOfOp == TokenType.MINUS)
        {
            // add resultregister, left, right 
            code += "sub    " + resultRegister + ",   " + leftRegister +
                    ",   " + rightRegister + "\n";
        }
        if( kindOfOp == TokenType.MULTIPLY)
        {
            code += "mult   " + leftRegister + ",   " + rightRegister + "\n";
            code += "mflo   " + resultRegister + "\n";
        }
        this.currentTRegister -= 2;
        return( code);
    }
    
    /**
     * Writes code for a value node.
     * The code is written by executing an add immediate with the value
     * into the destination register.
     * Writes code that looks like  addi $reg, $zero, value
     * @param valNode The node containing the value.
     * @param resultRegister The register in which to put the value.
     * @return The code which executes this value node.
     */
    public String writeCode( ValueNode valNode, String resultRegister)
    {
        String value = valNode.getAttribute();
        String code = "addi   " + resultRegister + ",   $zero, " + value + "\n";
        //String code = "li " + resultRegister + ", " + value + "\n";
        return( code);
    }
    
}
