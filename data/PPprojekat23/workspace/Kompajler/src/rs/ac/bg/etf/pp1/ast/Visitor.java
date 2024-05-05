// generated with ast extension for cup
// version 0.8
// 7/1/2023 0:16:16


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Mulop Mulop);
    public void visit(ListDesignator ListDesignator);
    public void visit(SingleConst SingleConst);
    public void visit(SingleVar SingleVar);
    public void visit(Relop Relop);
    public void visit(ActParList ActParList);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(VarNonEmpty VarNonEmpty);
    public void visit(StatementList StatementList);
    public void visit(ClassName ClassName);
    public void visit(Addop Addop);
    public void visit(Factor Factor);
    public void visit(CondTerm CondTerm);
    public void visit(Designator Designator);
    public void visit(Term Term);
    public void visit(Condition Condition);
    public void visit(AssignDesignatorStatement AssignDesignatorStatement);
    public void visit(ExprList ExprList);
    public void visit(Expr Expr);
    public void visit(ActPars ActPars);
    public void visit(AdditionalDesignators AdditionalDesignators);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(FormParamList FormParamList);
    public void visit(MethodOrCstrDeclarations MethodOrCstrDeclarations);
    public void visit(ItemDeclarations ItemDeclarations);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(ConstDecl ConstDecl);
    public void visit(CondFact CondFact);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(FormPars FormPars);
    public void visit(ClassMethods ClassMethods);
    public void visit(FormParam FormParam);
    public void visit(Mod Mod);
    public void visit(Division Division);
    public void visit(Multiplication Multiplication);
    public void visit(Subtraction Subtraction);
    public void visit(Addition Addition);
    public void visit(RelLse RelLse);
    public void visit(RelLs RelLs);
    public void visit(RelGre RelGre);
    public void visit(RelGr RelGr);
    public void visit(RelNeq RelNeq);
    public void visit(RelEq RelEq);
    public void visit(Assignop Assignop);
    public void visit(DesiArray DesiArray);
    public void visit(DesiDot DesiDot);
    public void visit(DesiOnlyIdent DesiOnlyIdent);
    public void visit(FactorNewCstr FactorNewCstr);
    public void visit(FactorNewArray FactorNewArray);
    public void visit(FactorExprInParen FactorExprInParen);
    public void visit(FactorBool FactorBool);
    public void visit(FactorChar FactorChar);
    public void visit(FactorNum FactorNum);
    public void visit(FactorDesi FactorDesi);
    public void visit(FactorFuncCall FactorFuncCall);
    public void visit(MultipleTerm MultipleTerm);
    public void visit(SingleTerm SingleTerm);
    public void visit(ExprMultipleTerms ExprMultipleTerms);
    public void visit(PosExpr PosExpr);
    public void visit(NegExpr NegExpr);
    public void visit(MultipleActPars MultipleActPars);
    public void visit(SingleActPar SingleActPar);
    public void visit(NoActPars NoActPars);
    public void visit(ActParsNonEmpty ActParsNonEmpty);
    public void visit(CondFactRel CondFactRel);
    public void visit(CondFactExpr CondFactExpr);
    public void visit(CondTermConjunction CondTermConjunction);
    public void visit(SingleCondTerm SingleCondTerm);
    public void visit(ConditionDisjunction ConditionDisjunction);
    public void visit(SingleCondition SingleCondition);
    public void visit(MrtvaZapeta MrtvaZapeta);
    public void visit(NoAdditionalDesi NoAdditionalDesi);
    public void visit(AdditionalDesiWithDesi AdditionalDesiWithDesi);
    public void visit(AdditionalDesiOnlyComma AdditionalDesiOnlyComma);
    public void visit(ListDesiStartWithDesi ListDesiStartWithDesi);
    public void visit(ListDesiStartWithComma ListDesiStartWithComma);
    public void visit(ErrorAssignDesignatorStmt ErrorAssignDesignatorStmt);
    public void visit(AssignDesignatorStmt AssignDesignatorStmt);
    public void visit(DesiStatementArrayAssign DesiStatementArrayAssign);
    public void visit(DesiStatementDec DesiStatementDec);
    public void visit(DesiStatementInc DesiStatementInc);
    public void visit(DesiStatementActPars DesiStatementActPars);
    public void visit(DesiStatementAssign DesiStatementAssign);
    public void visit(ForeachIterIdent ForeachIterIdent);
    public void visit(WhileHeader WhileHeader);
    public void visit(ElseHeader ElseHeader);
    public void visit(IfHeader IfHeader);
    public void visit(Empty Empty);
    public void visit(MultipleStatements MultipleStatements);
    public void visit(ForeachStatement ForeachStatement);
    public void visit(PrintStatementWidth PrintStatementWidth);
    public void visit(PrintStatementNoWidth PrintStatementNoWidth);
    public void visit(ReadStatement ReadStatement);
    public void visit(ReturnStatementNonEmpty ReturnStatementNonEmpty);
    public void visit(ReturnStatementEmpty ReturnStatementEmpty);
    public void visit(ContinueStatement ContinueStatement);
    public void visit(BreakStatement BreakStatement);
    public void visit(WhileStatement WhileStatement);
    public void visit(IfElseStatement IfElseStatement);
    public void visit(IfStatement IfStatement);
    public void visit(StatementDesiStatement StatementDesiStatement);
    public void visit(NoStatements NoStatements);
    public void visit(StatementListNonEmpty StatementListNonEmpty);
    public void visit(Type Type);
    public void visit(FormParamNoArray FormParamNoArray);
    public void visit(FormParamArray FormParamArray);
    public void visit(SingleFormalParam SingleFormalParam);
    public void visit(FormalParamList FormalParamList);
    public void visit(NoFormPars NoFormPars);
    public void visit(FormParsNonEmpty FormParsNonEmpty);
    public void visit(MethodName MethodName);
    public void visit(NoMethodDeclarations NoMethodDeclarations);
    public void visit(VoidMethodDeclarations VoidMethodDeclarations);
    public void visit(TypeMethodDeclarations TypeMethodDeclarations);
    public void visit(FunType FunType);
    public void visit(NoCstrOrMethodDecl NoCstrOrMethodDecl);
    public void visit(VoidMethodDecl VoidMethodDecl);
    public void visit(CstrDecl CstrDecl);
    public void visit(TypeMethodDecl TypeMethodDecl);
    public void visit(ClassNoCstrNoMethods ClassNoCstrNoMethods);
    public void visit(ClassOnlyBraces ClassOnlyBraces);
    public void visit(ClassBody ClassBody);
    public void visit(ClassNameNoExtends ClassNameNoExtends);
    public void visit(ClassNameExtends ClassNameExtends);
    public void visit(ClassDecl ClassDecl);
    public void visit(VarNoArray VarNoArray);
    public void visit(VarArray VarArray);
    public void visit(SingleVarDecl SingleVarDecl);
    public void visit(MultipleVarDecl MultipleVarDecl);
    public void visit(NoVarDeclarations NoVarDeclarations);
    public void visit(MultipleVarDeclarations MultipleVarDeclarations);
    public void visit(VarNonEmptyDecl VarNonEmptyDecl);
    public void visit(BoolConst BoolConst);
    public void visit(CharConst CharConst);
    public void visit(NumConst NumConst);
    public void visit(SingleConstDecl SingleConstDecl);
    public void visit(MultipleConstDecl MultipleConstDecl);
    public void visit(ConstDeclarations ConstDeclarations);
    public void visit(NoItemDecl NoItemDecl);
    public void visit(ClassItemDecl ClassItemDecl);
    public void visit(VarItemDecl VarItemDecl);
    public void visit(ConstItemDecl ConstItemDecl);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}