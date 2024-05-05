// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class AdditionalDesiOnlyComma extends AdditionalDesignators {

    private MrtvaZapeta MrtvaZapeta;
    private AdditionalDesignators AdditionalDesignators;

    public AdditionalDesiOnlyComma (MrtvaZapeta MrtvaZapeta, AdditionalDesignators AdditionalDesignators) {
        this.MrtvaZapeta=MrtvaZapeta;
        if(MrtvaZapeta!=null) MrtvaZapeta.setParent(this);
        this.AdditionalDesignators=AdditionalDesignators;
        if(AdditionalDesignators!=null) AdditionalDesignators.setParent(this);
    }

    public MrtvaZapeta getMrtvaZapeta() {
        return MrtvaZapeta;
    }

    public void setMrtvaZapeta(MrtvaZapeta MrtvaZapeta) {
        this.MrtvaZapeta=MrtvaZapeta;
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
        if(MrtvaZapeta!=null) MrtvaZapeta.accept(visitor);
        if(AdditionalDesignators!=null) AdditionalDesignators.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MrtvaZapeta!=null) MrtvaZapeta.traverseTopDown(visitor);
        if(AdditionalDesignators!=null) AdditionalDesignators.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MrtvaZapeta!=null) MrtvaZapeta.traverseBottomUp(visitor);
        if(AdditionalDesignators!=null) AdditionalDesignators.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AdditionalDesiOnlyComma(\n");

        if(MrtvaZapeta!=null)
            buffer.append(MrtvaZapeta.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AdditionalDesignators!=null)
            buffer.append(AdditionalDesignators.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AdditionalDesiOnlyComma]");
        return buffer.toString();
    }
}
