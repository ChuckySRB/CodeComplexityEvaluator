// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class ConstItemDecl extends ItemDeclarations {

    private ItemDeclarations ItemDeclarations;
    private ConstDeclarations ConstDeclarations;

    public ConstItemDecl (ItemDeclarations ItemDeclarations, ConstDeclarations ConstDeclarations) {
        this.ItemDeclarations=ItemDeclarations;
        if(ItemDeclarations!=null) ItemDeclarations.setParent(this);
        this.ConstDeclarations=ConstDeclarations;
        if(ConstDeclarations!=null) ConstDeclarations.setParent(this);
    }

    public ItemDeclarations getItemDeclarations() {
        return ItemDeclarations;
    }

    public void setItemDeclarations(ItemDeclarations ItemDeclarations) {
        this.ItemDeclarations=ItemDeclarations;
    }

    public ConstDeclarations getConstDeclarations() {
        return ConstDeclarations;
    }

    public void setConstDeclarations(ConstDeclarations ConstDeclarations) {
        this.ConstDeclarations=ConstDeclarations;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ItemDeclarations!=null) ItemDeclarations.accept(visitor);
        if(ConstDeclarations!=null) ConstDeclarations.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ItemDeclarations!=null) ItemDeclarations.traverseTopDown(visitor);
        if(ConstDeclarations!=null) ConstDeclarations.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ItemDeclarations!=null) ItemDeclarations.traverseBottomUp(visitor);
        if(ConstDeclarations!=null) ConstDeclarations.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstItemDecl(\n");

        if(ItemDeclarations!=null)
            buffer.append(ItemDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclarations!=null)
            buffer.append(ConstDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstItemDecl]");
        return buffer.toString();
    }
}
