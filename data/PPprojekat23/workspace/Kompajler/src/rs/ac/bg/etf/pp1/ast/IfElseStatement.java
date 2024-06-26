// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class IfElseStatement extends Statement {

    private IfHeader IfHeader;
    private Condition Condition;
    private Empty Empty;
    private Statement Statement;
    private ElseHeader ElseHeader;
    private Statement Statement1;

    public IfElseStatement (IfHeader IfHeader, Condition Condition, Empty Empty, Statement Statement, ElseHeader ElseHeader, Statement Statement1) {
        this.IfHeader=IfHeader;
        if(IfHeader!=null) IfHeader.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.Empty=Empty;
        if(Empty!=null) Empty.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ElseHeader=ElseHeader;
        if(ElseHeader!=null) ElseHeader.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
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

    public ElseHeader getElseHeader() {
        return ElseHeader;
    }

    public void setElseHeader(ElseHeader ElseHeader) {
        this.ElseHeader=ElseHeader;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfHeader!=null) IfHeader.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(Empty!=null) Empty.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ElseHeader!=null) ElseHeader.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfHeader!=null) IfHeader.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(Empty!=null) Empty.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ElseHeader!=null) ElseHeader.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfHeader!=null) IfHeader.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(Empty!=null) Empty.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ElseHeader!=null) ElseHeader.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfElseStatement(\n");

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

        if(ElseHeader!=null)
            buffer.append(ElseHeader.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfElseStatement]");
        return buffer.toString();
    }
}
