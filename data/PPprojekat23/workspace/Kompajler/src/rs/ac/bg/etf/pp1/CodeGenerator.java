package rs.ac.bg.etf.pp1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	
	private final static Struct boolType = Tab.find("bool").getType();
	
	private int mainPc;
	
	public int getMainPc() {
		return mainPc;
	}
	
	private boolean returnFound = false;
	
	
	
	private Stack<Integer> fixupAddr = new Stack<Integer>();
	private Stack<Integer> disjunctionAddr = new Stack<Integer>();
	private Stack<AddrLvl> conjunctionAddr = new Stack<AddrLvl>();
	private Stack<Integer> loopTop = new Stack<Integer>();
	private Stack<AddrLvl> breakAddr = new Stack<AddrLvl>();
	private Stack<Integer> foreachAddr = new Stack<Integer>();
	class AddrLvl{
		public AddrLvl(int addr, int level) {
			this.addr = addr;
			this.level = level;
		}
		int addr;
		int level;
	}
	private int condLvl = 0;
	private int loopLvl = 0;
	
	private int arrayAssignCnt = 0;
	private Stack<ArrAssignInd> arrAssignStack = new Stack<ArrAssignInd>();
	class ArrAssignInd{
		public ArrAssignInd(Obj o, int i) {
			this.obj = o;
			this.ind = i;
		}
		Obj obj;
		int ind;
	}
	
	//FACTORS
	public void visit(FactorDesi factorDesi) {
		//NE UCITAVA SE OVDE, VEC U FUNKCIJI: public void visit(DesiOnlyIdent desiOnlyIdent)
	}
	public void visit(FactorNum factorNum) {
		Obj con = Tab.insert(Obj.Con, "$", factorNum.struct);
		con.setLevel(0);
		con.setAdr(factorNum.getVal());
		Code.load(con);
	}
	public void visit(FactorChar factorChar) {
		Obj con = Tab.insert(Obj.Con, "$", factorChar.struct);
		con.setLevel(0);
		con.setAdr(factorChar.getVal());
		Code.load(con);
	}
	public void visit(FactorBool factorBool) {
		Obj con = Tab.insert(Obj.Con, "$", factorBool.struct);
		con.setLevel(0);
		con.setAdr(factorBool.getVal() ? 1:0);
		Code.load(con);
	}
	public void visit(FactorExprInParen factorExprInParen) {
		//NISTA
	}
	public void visit(FactorNewArray factorNewArray) {
		Code.put(Code.newarray);
		if(factorNewArray.struct.equals(Tab.charType)) {
			Code.put(0);
		} else {			
			Code.put(1);
		}
	}
	public void visit(FactorNewCstr factorNewCstr) {
		//TODO
	}
	
	//TERMS
	public void visit(MultipleTerm multipleTerm) {
		 if(Multiplication.class == multipleTerm.getMulop().getClass()) {
			 Code.put(Code.mul);
		 } else if (Division.class == multipleTerm.getMulop().getClass()) {
			 Code.put(Code.div);
		 } else if (Mod.class == multipleTerm.getMulop().getClass()) {
			 Code.put(Code.rem);
		 }
	}
	
	//EXPRESSIONS
	public void visit(NegExpr negExpr) {
		Code.put(Code.neg);
	}
	public void visit(ExprMultipleTerms exprMultipleTerms) {
		if(Addition.class == exprMultipleTerms.getAddop().getClass()) {
			Code.put(Code.add);
		} else if(Subtraction.class == exprMultipleTerms.getAddop().getClass()) {
			Code.put(Code.sub);
		}
	}
	
	//DESIGNATORS
	public void visit(DesiOnlyIdent desiOnlyIdent) {
		//TODO
		SyntaxNode parent = desiOnlyIdent.getParent();
		if(AssignDesignatorStmt.class != parent.getClass() &&
				ListDesiStartWithDesi.class != parent.getClass() &&
				AdditionalDesiWithDesi.class != parent.getClass() &&
				DesiStatementArrayAssign.class != parent.getClass() &&
				ReadStatement.class != parent.getClass() &&
				desiOnlyIdent.obj.getKind() != Obj.Meth) {
			Code.load(desiOnlyIdent.obj);
		} else if (ListDesiStartWithDesi.class == parent.getClass()) {
			arrAssignStack.push(new ArrAssignInd(desiOnlyIdent.obj, arrayAssignCnt));
		} else if(AdditionalDesiWithDesi.class == parent.getClass()) {
			arrayAssignCnt++;
			arrAssignStack.push(new ArrAssignInd(desiOnlyIdent.obj, arrayAssignCnt));
		
		}
	}
	public void visit(DesiDot desiDot) {
		//TODO
	}
	public void visit(DesiArray desiArray) {
		//TODO
		SyntaxNode parent = desiArray.getParent();
		if(AssignDesignatorStmt.class != parent.getClass() &&
				ListDesiStartWithDesi.class != parent.getClass() &&
				AdditionalDesiWithDesi.class != parent.getClass() &&
				DesiStatementArrayAssign.class != parent.getClass() &&
				ReadStatement.class != parent.getClass()) {
			Code.load(desiArray.obj);
		}  else if (ListDesiStartWithDesi.class == parent.getClass()) {
			arrAssignStack.push(new ArrAssignInd(desiArray.obj, arrayAssignCnt));
		} else if(AdditionalDesiWithDesi.class == parent.getClass()) {
			arrayAssignCnt++;
			arrAssignStack.push(new ArrAssignInd(desiArray.obj, arrayAssignCnt));
		}
	}
	
	//DESIGNATOR STATEMENTS
	public void visit(AssignDesignatorStmt assignDesignatorStmt) {
		Code.store(assignDesignatorStmt.getDesignator().obj);
	}
	public void visit(DesiStatementInc desiStatementInc) {
		Code.put(Code.const_1);
		Code.put(Code.add);
		Code.store(desiStatementInc.getDesignator().obj);
	}
	public void visit(DesiStatementDec desiStatementDec) {
		Code.put(Code.const_1);
		Code.put(Code.sub);
		Code.store(desiStatementDec.getDesignator().obj);
	}
	public void visit(DesiStatementArrayAssign desiStatementArrayAssign) {
		Obj arr = desiStatementArrayAssign.getDesignator().obj;
		while(!arrAssignStack.isEmpty()) {
			ArrAssignInd aai = arrAssignStack.pop();
			Code.load(arr);
			Code.loadConst(aai.ind);
			Code.load(new Obj(Obj.Elem, "", aai.obj.getType()));
			Code.store(aai.obj);
		}
		arrayAssignCnt = 0;
	}
	public void visit(MrtvaZapeta mrtvaZapeta) {
		arrayAssignCnt++;
	}
	
	
	//IO
	public void visit(ReadStatement readStatement) {
		if(readStatement.getDesignator().obj.getType().equals(Tab.intType)) {
			Code.put(Code.read);
		} else {
			Code.put(Code.bread);
		}
		Code.store(readStatement.getDesignator().obj);			
	}
	public void visit(PrintStatementNoWidth printStatementNoWidth) {
		if(printStatementNoWidth.getExpr().struct == Tab.intType) {
			Code.loadConst(10);
			Code.put(Code.print);
		} else if(printStatementNoWidth.getExpr().struct == Tab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	public void visit(PrintStatementWidth printStatementWidth) {
		if(printStatementWidth.getExpr().struct == Tab.intType) {
			Code.loadConst(printStatementWidth.getWidth());
			Code.put(Code.print);
		} else if(printStatementWidth.getExpr().struct == Tab.charType) {
			Code.loadConst(printStatementWidth.getWidth());
			Code.put(Code.bprint);
		} else {
			Code.loadConst(printStatementWidth.getWidth());
			Code.put(Code.bprint);
		}
	}
	
	//METHODS AND CONSTRUCTORS
	public void visit(MethodName methodName) {
		if("main".equalsIgnoreCase(methodName.getFunName())) {
			mainPc = Code.pc;
		}
		methodName.obj.setAdr(Code.pc);
		
		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel());
		Code.put(methodName.obj.getLevel() + methodName.obj.getLocalSymbols().size());
	}
	private void closeMethod() {
		if(!returnFound) {
			Code.put(Code.trap);
			Code.put(17);
		}
		returnFound = false;
	}
	public void visit(CstrDecl cstrDecl) {
		closeMethod();
	}
	public void visit(TypeMethodDecl typeMethodDecl) {
		closeMethod();
	}
	public void visit(VoidMethodDecl voidMethodDecl) {
		if(!returnFound) {
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
		returnFound = false;
	}
	public void visit(TypeMethodDeclarations typeMethodDeclarations) {
		closeMethod();
	}
	public void visit(VoidMethodDeclarations voidMethodDeclarations) {
		if(!returnFound) {
			Code.put(Code.exit);
			Code.put(Code.return_);
		}
		returnFound = false;
	}
	public void visit(FactorFuncCall factorFuncCall) {
		if(factorFuncCall.getDesignator().obj.getName().equals("ord")) {
			//Code.put(Code.return_);
		} else if(factorFuncCall.getDesignator().obj.getName().equals("chr")) {
			//Code.put(Code.return_);
		} else if(factorFuncCall.getDesignator().obj.getName().equals("len")) {
			//TODO
			Code.put(Code.arraylength);
		} else {
			Obj functionObj = factorFuncCall.getDesignator().obj;
			int offset = functionObj.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);
		}
	}
	public void visit(DesiStatementActPars desiStatementActPars) {
		Obj functionObj = desiStatementActPars.getDesignator().obj;
		int offset = functionObj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		//skidamo povratnu vrednost sa steka, posto nije deo izraza
		if(!desiStatementActPars.getDesignator().obj.getType().equals(Tab.noType)) {
			Code.put(Code.pop);
		}
	}
	public void visit(ReturnStatementEmpty returnStatementEmpty) {
		returnFound = true;
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	public void visit(ReturnStatementNonEmpty returnStatementNonEmpty) {
		returnFound = true; 
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	//CONTROL FLOW
	public void visit(IfStatement ifStatement) {
		Code.fixup(fixupAddr.pop());
		while(!conjunctionAddr.empty()) {
			if(conjunctionAddr.peek().level == condLvl) {
				Code.fixup(conjunctionAddr.pop().addr);
			}
			else break;
		}
		condLvl--;
		
	}
	public void visit(IfElseStatement ifElseStatement) {
		Code.fixup(fixupAddr.pop());
		condLvl--;
	}
	public void visit(IfHeader ifHeader) {
		condLvl++;
	}
	public void visit(ElseHeader elseHeader) {
		Code.putJump(0);
		Code.fixup(fixupAddr.pop());
		fixupAddr.push(Code.pc-2);
		while(!conjunctionAddr.empty()) {
			if(conjunctionAddr.peek().level == condLvl) {
				Code.fixup(conjunctionAddr.pop().addr);
			}
			else break;
		}
	}
	public void visit(Empty empty) {
		while(!disjunctionAddr.empty()) {
			Code.fixup(disjunctionAddr.pop());
		}
	}
	public void visit(WhileStatement whileStatement) {
		Code.putJump(loopTop.pop());
		Code.fixup(fixupAddr.pop());
		while(!conjunctionAddr.empty()) {
			if(conjunctionAddr.peek().level == condLvl) {
				Code.fixup(conjunctionAddr.pop().addr);
			}
			else break;
		}
		while(!breakAddr.empty()) {
			if(breakAddr.peek().level == loopLvl) {
				Code.fixup(breakAddr.pop().addr);
			}
			else break;
		}
		condLvl--;
		loopLvl--;
	}
	public void visit(WhileHeader whileHeader) {
		condLvl++;
		loopLvl++;
		loopTop.push(Code.pc);
	}
	public void visit(BreakStatement breakStatement) {
		Code.putJump(0);
		breakAddr.push(new AddrLvl(Code.pc-2, loopLvl));
		
	}
	public void visit(ContinueStatement continueStatement) {
		Code.putJump(loopTop.peek());
	}
	public void visit(ForeachStatement foreachStatement) {
		Code.loadConst(1);
		Code.put(Code.add);
		Code.putJump(loopTop.pop());
		Code.fixup(foreachAddr.pop());
		while(!breakAddr.empty()) {
			if(breakAddr.peek().level == loopLvl) {
				Code.fixup(breakAddr.pop().addr);
			}
			else break;
		}
		Code.put(Code.pop);
		Code.put(Code.pop);
		loopLvl--;
	}
	public void visit(ForeachIterIdent foreachIterIdent) {
		loopLvl++;
		Code.loadConst(0);
		loopTop.push(Code.pc);
		Code.put(Code.dup2);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.arraylength);
		Code.putFalseJump(Code.lt, 0);
		foreachAddr.push(Code.pc-2);
		Code.put(Code.dup2);
		Code.load(new Obj(Obj.Elem, "", ((ForeachStatement)foreachIterIdent.getParent()).getDesignator().obj.getType()));
		Code.store(foreachIterIdent.obj);
	}

	//CONDITIONS
	public void visit(ConditionDisjunction conditionDisjunction) {
		SyntaxNode parent = conditionDisjunction.getParent();
		if(ConditionDisjunction.class == parent.getClass()) {
			Code.pc -= 3;
			int invopcode = Code.get(Code.pc) - Code.jcc;
			Code.putFalseJump(invopcode, 0);
			fixupAddr.pop();
			disjunctionAddr.push(Code.pc-2);
			while(!conjunctionAddr.empty()) {
				if(conjunctionAddr.peek().level == condLvl) {
					Code.fixup(conjunctionAddr.pop().addr);
				}
				else break;
			}
		}
	}
	public void visit(SingleCondition singleCondition) {
		SyntaxNode parent = singleCondition.getParent();
		if(ConditionDisjunction.class == parent.getClass()) {
			Code.pc -= 3;
			int invopcode = Code.get(Code.pc) - Code.jcc;
			Code.putFalseJump(invopcode, 0);
			fixupAddr.pop();
			disjunctionAddr.push(Code.pc-2);
			while(!conjunctionAddr.empty()) {
				if(conjunctionAddr.peek().level == condLvl) {
					Code.fixup(conjunctionAddr.pop().addr);
				}
				else break;
			}
		}
	}
	public void visit(CondTermConjunction condTermConjunction) {
		SyntaxNode parent = condTermConjunction.getParent();
		if(CondTermConjunction.class == parent.getClass()) {
			fixupAddr.pop();
			conjunctionAddr.push(new AddrLvl(Code.pc-2, condLvl));
		}
	}
	public void visit(SingleCondTerm singleCondTerm) {
		SyntaxNode parent = singleCondTerm.getParent();
		if(CondTermConjunction.class == parent.getClass()) {
			fixupAddr.pop();
			conjunctionAddr.push(new AddrLvl(Code.pc-2, condLvl));
		}
	}
	public void visit(CondFactRel condFactRel) {
		if(RelEq.class == condFactRel.getRelop().getClass()) {
			Code.putFalseJump(Code.eq, 0);
		} else if(RelNeq.class == condFactRel.getRelop().getClass()) {
			Code.putFalseJump(Code.ne, 0);
		} else if(RelGr.class == condFactRel.getRelop().getClass()) {
			Code.putFalseJump(Code.gt, 0);
		} else if(RelGre.class == condFactRel.getRelop().getClass()) {
			Code.putFalseJump(Code.ge, 0);
		} else if(RelLs.class == condFactRel.getRelop().getClass()) {
			Code.putFalseJump(Code.lt, 0);
		} else { //RelLse
			Code.putFalseJump(Code.le, 0);
		}
		fixupAddr.push(Code.pc-2);
	}
	public void visit(CondFactExpr condFactExpr) {
		Code.loadConst(0);
		Code.putFalseJump(Code.gt, 0);
		fixupAddr.push(Code.pc-2);
	}
	
	
}

