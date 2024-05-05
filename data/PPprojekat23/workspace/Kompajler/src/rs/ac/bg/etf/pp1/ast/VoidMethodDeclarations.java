// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class VoidMethodDeclarations extends MethodDeclarations {

    private MethodDeclarations MethodDeclarations;
    private MethodName MethodName;
    private FormPars FormPars;
    private VarDeclarations VarDeclarations;
    private StatementList StatementList;

    public VoidMethodDeclarations (MethodDeclarations MethodDeclarations, MethodName MethodName, FormPars FormPars, VarDeclarations VarDeclarations, StatementList StatementList) {
        this.MethodDeclarations=MethodDeclarations;
        if(MethodDeclarations!=null) MethodDeclarations.setParent(this);
        this.MethodName=MethodName;
        if(MethodName!=null) MethodName.setParent(this);
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.VarDeclarations=VarDeclarations;
        if(VarDeclarations!=null) VarDeclarations.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethodDeclarations getMethodDeclarations() {
        return MethodDeclarations;
    }

    public void setMethodDeclarations(MethodDeclarations MethodDeclarations) {
        this.MethodDeclarations=MethodDeclarations;
    }

    public MethodName getMethodName() {
        return MethodName;
    }

    public void setMethodName(MethodName MethodName) {
        this.MethodName=MethodName;
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public VarDeclarations getVarDeclarations() {
        return VarDeclarations;
    }

    public void setVarDeclarations(VarDeclarations VarDeclarations) {
        this.VarDeclarations=VarDeclarations;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclarations!=null) MethodDeclarations.accept(visitor);
        if(MethodName!=null) MethodName.accept(visitor);
        if(FormPars!=null) FormPars.accept(visitor);
        if(VarDeclarations!=null) VarDeclarations.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclarations!=null) MethodDeclarations.traverseTopDown(visitor);
        if(MethodName!=null) MethodName.traverseTopDown(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(VarDeclarations!=null) VarDeclarations.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclarations!=null) MethodDeclarations.traverseBottomUp(visitor);
        if(MethodName!=null) MethodName.traverseBottomUp(visitor);
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(VarDeclarations!=null) VarDeclarations.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VoidMethodDeclarations(\n");

        if(MethodDeclarations!=null)
            buffer.append(MethodDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodName!=null)
            buffer.append(MethodName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclarations!=null)
            buffer.append(VarDeclarations.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VoidMethodDeclarations]");
        return buffer.toString();
    }
}
