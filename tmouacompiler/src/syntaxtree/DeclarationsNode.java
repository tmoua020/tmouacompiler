package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a set of declarations in an Almost C program.
 *
 * @author Timly Moua
 */
public class DeclarationsNode extends SyntaxTreeNode {

    private ArrayList<VariableNode> vars = new ArrayList<VariableNode>();

    /**
     * Adds a variable to this declaration.
     *
     * @param aVariable The variable node to add to this declaration.
     */
    public void addVariable(VariableNode aVariable) {
        vars.add(aVariable);
    }

    /**
     * Adds all variables declared in the declarations to declarations.
     *
     * @param declarations
     */
    public void addDeclarations(DeclarationsNode declarations) {
        vars.addAll(declarations.vars);
    }

    /**
     * Creates a String representation of this declarations node and its
     * children.
     *
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Declarations\n";
        for (VariableNode variable : vars) {
            answer += variable.indentedToString(level + 1);
        }
        return answer;
    }

    public Object getDeclarations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
