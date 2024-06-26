// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public class DesiStatementAssign extends DesignatorStatement {

    private AssignDesignatorStatement AssignDesignatorStatement;

    public DesiStatementAssign (AssignDesignatorStatement AssignDesignatorStatement) {
        this.AssignDesignatorStatement=AssignDesignatorStatement;
        if(AssignDesignatorStatement!=null) AssignDesignatorStatement.setParent(this);
    }

    public AssignDesignatorStatement getAssignDesignatorStatement() {
        return AssignDesignatorStatement;
    }

    public void setAssignDesignatorStatement(AssignDesignatorStatement AssignDesignatorStatement) {
        this.AssignDesignatorStatement=AssignDesignatorStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AssignDesignatorStatement!=null) AssignDesignatorStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AssignDesignatorStatement!=null) AssignDesignatorStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AssignDesignatorStatement!=null) AssignDesignatorStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesiStatementAssign(\n");

        if(AssignDesignatorStatement!=null)
            buffer.append(AssignDesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesiStatementAssign]");
        return buffer.toString();
    }
}
