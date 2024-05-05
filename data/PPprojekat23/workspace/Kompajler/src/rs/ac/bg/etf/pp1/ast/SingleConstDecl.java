// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class SingleConstDecl extends ConstDecl {

    private String constName;
    private SingleConst SingleConst;

    public SingleConstDecl (String constName, SingleConst SingleConst) {
        this.constName=constName;
        this.SingleConst=SingleConst;
        if(SingleConst!=null) SingleConst.setParent(this);
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public SingleConst getSingleConst() {
        return SingleConst;
    }

    public void setSingleConst(SingleConst SingleConst) {
        this.SingleConst=SingleConst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleConst!=null) SingleConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleConst!=null) SingleConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleConst!=null) SingleConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleConstDecl(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(SingleConst!=null)
            buffer.append(SingleConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleConstDecl]");
        return buffer.toString();
    }
}
