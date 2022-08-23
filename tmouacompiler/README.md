This is a program hard coded in C language using the JFlex tool to help create a Scanner.
The scanner will be able to capture and return certain keywords and symbols as described in the grammar.


Project name is tmouacompiler

- Inside the src folder contains the packages needed for functioning compiler. (compiler, parser, scanner, symboltable)

- Inside the scanner package it contains:
1. TokenType.java
2. Token.java
3. MyScanner.java
4. Lookuptable.java
5. Tester.java
6. jflex jar.

- Inside the parser package it contains a recognizer that holds all the production rules for a micro C program.
1. recognizer.java
2. Parser.java

- Inside the SymbolTable package it should store information about identifiers
1. SymbolTable.java

- Inside the test package it should contain a Test for MyScanner, Recognizer, SymbolTable, SyntaxTree, Parser, SemanticAnalysis, CodeGeneration.

- Inside the compiler package, it contains Main.java where it reads in a C file.
1. Main.java

- Inside the SyntaxTree package it contains the Nodes.
1. AssignmentStatementNode
2. CompoundStatementNode
3. DataType
4. DeclarationsNode
5. ExpressionNode
6. FunctionNode
7. FunctionsNode
8. IfStatementNode
9. OperationNode
10. ProgramNode
11. StatementNode
12. SyntaxTreeNode
13. TokenType
14. ValueNode
15. VariableNode
16. WriteNode
17. ReadNode
18. WhileStatementNode
19. ProcedureNode

- Inside the semantic package contains:
1. SemanticAnalysis.java

- Inside the codegeneration package contains:
1. CodeGeneration.java
