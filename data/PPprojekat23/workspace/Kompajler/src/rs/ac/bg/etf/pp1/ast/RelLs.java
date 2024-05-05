// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class RelLs extends Relop {

    public RelLs () {
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
        buffer.append("RelLs(\n");

        buffer.append(tab);
        buffer.append(") [RelLs]");
        return buffer.toString();
    }
}
