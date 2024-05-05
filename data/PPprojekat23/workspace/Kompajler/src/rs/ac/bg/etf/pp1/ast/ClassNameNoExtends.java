// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class ClassNameNoExtends extends ClassName {

    private String className;

    public ClassNameNoExtends (String className) {
        this.className=className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className=className;
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
        buffer.append("ClassNameNoExtends(\n");

        buffer.append(" "+tab+className);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassNameNoExtends]");
        return buffer.toString();
    }
}
