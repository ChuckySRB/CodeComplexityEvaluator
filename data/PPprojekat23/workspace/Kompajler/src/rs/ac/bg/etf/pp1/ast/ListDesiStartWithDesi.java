// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class ListDesiStartWithDesi extends ListDesignator {

    private Designator Designator;
    private AdditionalDesignators AdditionalDesignators;

    public ListDesiStartWithDesi (Designator Designator, AdditionalDesignators AdditionalDesignators) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.AdditionalDesignators=AdditionalDesignators;
        if(AdditionalDesignators!=null) AdditionalDesignators.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public AdditionalDesignators getAdditionalDesignators() {
        return AdditionalDesignators;
    }

    public void setAdditionalDesignators(AdditionalDesignators AdditionalDesignators) {
        this.AdditionalDesignators=AdditionalDesignators;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(AdditionalDesignators!=null) AdditionalDesignators.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(AdditionalDesignators!=null) AdditionalDesignators.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(AdditionalDesignators!=null) AdditionalDesignators.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ListDesiStartWithDesi(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AdditionalDesignators!=null)
            buffer.append(AdditionalDesignators.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ListDesiStartWithDesi]");
        return buffer.toString();
    }
}
