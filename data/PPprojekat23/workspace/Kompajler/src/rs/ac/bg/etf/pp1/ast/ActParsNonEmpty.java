// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class ActParsNonEmpty extends ActPars {

    private ActParList ActParList;

    public ActParsNonEmpty (ActParList ActParList) {
        this.ActParList=ActParList;
        if(ActParList!=null) ActParList.setParent(this);
    }

    public ActParList getActParList() {
        return ActParList;
    }

    public void setActParList(ActParList ActParList) {
        this.ActParList=ActParList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParList!=null) ActParList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParList!=null) ActParList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParList!=null) ActParList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParsNonEmpty(\n");

        if(ActParList!=null)
            buffer.append(ActParList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActParsNonEmpty]");
        return buffer.toString();
    }
}
