// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class SingleVarDecl extends VarDecl {

    private SingleVar SingleVar;

    public SingleVarDecl (SingleVar SingleVar) {
        this.SingleVar=SingleVar;
        if(SingleVar!=null) SingleVar.setParent(this);
    }

    public SingleVar getSingleVar() {
        return SingleVar;
    }

    public void setSingleVar(SingleVar SingleVar) {
        this.SingleVar=SingleVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleVar!=null) SingleVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleVar!=null) SingleVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleVar!=null) SingleVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleVarDecl(\n");

        if(SingleVar!=null)
            buffer.append(SingleVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleVarDecl]");
        return buffer.toString();
    }
}
