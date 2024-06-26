// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class VarItemDecl extends ItemDeclarations {

    private ItemDeclarations ItemDeclarations;
    private VarNonEmpty VarNonEmpty;

    public VarItemDecl (ItemDeclarations ItemDeclarations, VarNonEmpty VarNonEmpty) {
        this.ItemDeclarations=ItemDeclarations;
        if(ItemDeclarations!=null) ItemDeclarations.setParent(this);
        this.VarNonEmpty=VarNonEmpty;
        if(VarNonEmpty!=null) VarNonEmpty.setParent(this);
    }

    public ItemDeclarations getItemDeclarations() {
        return ItemDeclarations;
    }

    public void setItemDeclarations(ItemDeclarations ItemDeclarations) {
        this.ItemDeclarations=ItemDeclarations;
    }

    public VarNonEmpty getVarNonEmpty() {
        return VarNonEmpty;
    }

    public void setVarNonEmpty(VarNonEmpty VarNonEmpty) {
        this.VarNonEmpty=VarNonEmpty;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ItemDeclarations!=null) ItemDeclarations.accept(visitor);
        if(VarNonEmpty!=null) VarNonEmpty.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ItemDeclarations!=null) ItemDeclarations.traverseTopDown(visitor);
        if(VarNonEmpty!=null) VarNonEmpty.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ItemDeclarations!=null) ItemDeclarations.traverseBottomUp(visitor);
        if(VarNonEmpty!=null) VarNonEmpty.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarItemDecl(\n");

        if(ItemDeclarations!=null)
            buffer.append(ItemDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarNonEmpty!=null)
            buffer.append(VarNonEmpty.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarItemDecl]");
        return buffer.toString();
    }
}
