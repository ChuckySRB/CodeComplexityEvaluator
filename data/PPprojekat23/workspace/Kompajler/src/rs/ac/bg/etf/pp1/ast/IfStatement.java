// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class IfStatement extends Statement {

    private IfHeader IfHeader;
    private Condition Condition;
    private Empty Empty;
    private Statement Statement;

    public IfStatement (IfHeader IfHeader, Condition Condition, Empty Empty, Statement Statement) {
        this.IfHeader=IfHeader;
        if(IfHeader!=null) IfHeader.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.Empty=Empty;
        if(Empty!=null) Empty.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public IfHeader getIfHeader() {
        return IfHeader;
    }

    public void setIfHeader(IfHeader IfHeader) {
        this.IfHeader=IfHeader;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public Empty getEmpty() {
        return Empty;
    }

    public void setEmpty(Empty Empty) {
        this.Empty=Empty;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfHeader!=null) IfHeader.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(Empty!=null) Empty.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfHeader!=null) IfHeader.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(Empty!=null) Empty.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfHeader!=null) IfHeader.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(Empty!=null) Empty.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfStatement(\n");

        if(IfHeader!=null)
            buffer.append(IfHeader.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Empty!=null)
            buffer.append(Empty.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfStatement]");
        return buffer.toString();
    }
}
