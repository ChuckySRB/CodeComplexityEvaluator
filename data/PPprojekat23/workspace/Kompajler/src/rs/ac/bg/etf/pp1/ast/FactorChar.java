// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class FactorChar extends Factor {

    private char val;

    public FactorChar (char val) {
        this.val=val;
    }

    public char getVal() {
        return val;
    }

    public void setVal(char val) {
        this.val=val;
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
        buffer.append("FactorChar(\n");

        buffer.append(" "+tab+val);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorChar]");
        return buffer.toString();
    }
}
