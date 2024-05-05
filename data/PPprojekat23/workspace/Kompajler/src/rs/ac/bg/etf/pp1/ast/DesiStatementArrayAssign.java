// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class DesiStatementArrayAssign extends DesignatorStatement {

    private ListDesignator ListDesignator;
    private Designator Designator;

    public DesiStatementArrayAssign (ListDesignator ListDesignator, Designator Designator) {
        this.ListDesignator=ListDesignator;
        if(ListDesignator!=null) ListDesignator.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
    }

    public ListDesignator getListDesignator() {
        return ListDesignator;
    }

    public void setListDesignator(ListDesignator ListDesignator) {
        this.ListDesignator=ListDesignator;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ListDesignator!=null) ListDesignator.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ListDesignator!=null) ListDesignator.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ListDesignator!=null) ListDesignator.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesiStatementArrayAssign(\n");

        if(ListDesignator!=null)
            buffer.append(ListDesignator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesiStatementArrayAssign]");
        return buffer.toString();
    }
}
