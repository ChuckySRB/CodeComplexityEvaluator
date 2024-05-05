// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class ClassOnlyBraces extends ClassMethods {

    private MethodOrCstrDeclarations MethodOrCstrDeclarations;

    public ClassOnlyBraces (MethodOrCstrDeclarations MethodOrCstrDeclarations) {
        this.MethodOrCstrDeclarations=MethodOrCstrDeclarations;
        if(MethodOrCstrDeclarations!=null) MethodOrCstrDeclarations.setParent(this);
    }

    public MethodOrCstrDeclarations getMethodOrCstrDeclarations() {
        return MethodOrCstrDeclarations;
    }

    public void setMethodOrCstrDeclarations(MethodOrCstrDeclarations MethodOrCstrDeclarations) {
        this.MethodOrCstrDeclarations=MethodOrCstrDeclarations;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodOrCstrDeclarations!=null) MethodOrCstrDeclarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodOrCstrDeclarations!=null) MethodOrCstrDeclarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodOrCstrDeclarations!=null) MethodOrCstrDeclarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassOnlyBraces(\n");

        if(MethodOrCstrDeclarations!=null)
            buffer.append(MethodOrCstrDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassOnlyBraces]");
        return buffer.toString();
    }
}
