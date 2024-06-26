// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class ListDesiStartWithComma extends ListDesignator {

    private AdditionalDesignators AdditionalDesignators;

    public ListDesiStartWithComma (AdditionalDesignators AdditionalDesignators) {
        this.AdditionalDesignators=AdditionalDesignators;
        if(AdditionalDesignators!=null) AdditionalDesignators.setParent(this);
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
        if(AdditionalDesignators!=null) AdditionalDesignators.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AdditionalDesignators!=null) AdditionalDesignators.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AdditionalDesignators!=null) AdditionalDesignators.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ListDesiStartWithComma(\n");

        if(AdditionalDesignators!=null)
            buffer.append(AdditionalDesignators.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ListDesiStartWithComma]");
        return buffer.toString();
    }
}
