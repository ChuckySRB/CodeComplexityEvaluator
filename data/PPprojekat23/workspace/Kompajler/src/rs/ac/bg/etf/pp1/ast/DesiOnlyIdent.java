// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class DesiOnlyIdent extends Designator {

    private String desiName;

    public DesiOnlyIdent (String desiName) {
        this.desiName=desiName;
    }

    public String getDesiName() {
        return desiName;
    }

    public void setDesiName(String desiName) {
        this.desiName=desiName;
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
        buffer.append("DesiOnlyIdent(\n");

        buffer.append(" "+tab+desiName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesiOnlyIdent]");
        return buffer.toString();
    }
}
