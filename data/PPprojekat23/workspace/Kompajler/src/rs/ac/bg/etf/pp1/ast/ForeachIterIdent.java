// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class ForeachIterIdent implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String iterName;

    public ForeachIterIdent (String iterName) {
        this.iterName=iterName;
    }

    public String getIterName() {
        return iterName;
    }

    public void setIterName(String iterName) {
        this.iterName=iterName;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForeachIterIdent(\n");

        buffer.append(" "+tab+iterName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForeachIterIdent]");
        return buffer.toString();
    }
}
