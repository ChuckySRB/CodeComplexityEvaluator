// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class ClassItemDecl extends ItemDeclarations {

    private ItemDeclarations ItemDeclarations;
    private ClassDecl ClassDecl;

    public ClassItemDecl (ItemDeclarations ItemDeclarations, ClassDecl ClassDecl) {
        this.ItemDeclarations=ItemDeclarations;
        if(ItemDeclarations!=null) ItemDeclarations.setParent(this);
        this.ClassDecl=ClassDecl;
        if(ClassDecl!=null) ClassDecl.setParent(this);
    }

    public ItemDeclarations getItemDeclarations() {
        return ItemDeclarations;
    }

    public void setItemDeclarations(ItemDeclarations ItemDeclarations) {
        this.ItemDeclarations=ItemDeclarations;
    }

    public ClassDecl getClassDecl() {
        return ClassDecl;
    }

    public void setClassDecl(ClassDecl ClassDecl) {
        this.ClassDecl=ClassDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ItemDeclarations!=null) ItemDeclarations.accept(visitor);
        if(ClassDecl!=null) ClassDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ItemDeclarations!=null) ItemDeclarations.traverseTopDown(visitor);
        if(ClassDecl!=null) ClassDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ItemDeclarations!=null) ItemDeclarations.traverseBottomUp(visitor);
        if(ClassDecl!=null) ClassDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassItemDecl(\n");

        if(ItemDeclarations!=null)
            buffer.append(ItemDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassDecl!=null)
            buffer.append(ClassDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassItemDecl]");
        return buffer.toString();
    }
}
