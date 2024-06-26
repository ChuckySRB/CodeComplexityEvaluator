package rs.ac.bg.etf.pp1;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {
	private final static Struct boolType = Tab.find("bool").getType();
	
	boolean errorDetected = false;
	int nVars;
	boolean validScopeOpened = true;

	List<String> valNames = new ArrayList<String>();
	List<Integer> valVals= new ArrayList<Integer>();
	List<Integer> valLines = new ArrayList<Integer>();
	List<Integer> valTypes = new ArrayList<Integer>();

	Obj currentClass = null;
	
	Obj currentMethod = null;
	Struct currentMethodType = Tab.noType;
	int fpCounter = 0;
	private Stack<Integer> calledFpCounters = new Stack<Integer>();
	private Stack<Obj> calledMethods = new Stack<Obj>();
	
	boolean inLoop = false;
	
	Struct arrayAssignType = Tab.noType;
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

//	public void report_info(String message, SyntaxNode info) {
//		StringBuilder msg = new StringBuilder(message);
//		int line = (info == null) ? 0 : info.getLine();
//		if (line != 0)
//			msg.append(" na liniji ").append(line);
//		log.info(msg.toString());
//	}
	
	public void report_info(String message, SyntaxNode info, Obj o) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		DumpSymbolTableVisitor dstv = new DumpSymbolTableVisitor();
		if(o != null) {
			dstv.visitObjNode(o);
			log.info(msg.toString() + "\n" + dstv.getOutput());
		} else {
			log.info(msg.toString());
		}
	}
	
	//PROGRAM
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}
	public void visit(Program program) {
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}	
	
	//CONSTANTS
	public void visit(ConstDeclarations constDeclarations) {
		Struct declType = constDeclarations.getType().struct;
		while(!valLines.isEmpty()) {
			int currTypeKind = valTypes.remove(0);
			String currConstName= valNames.remove(0);
			int currConstLine = valLines.remove(0);
			int val = valVals.remove(0);
			if(declType.getKind() != currTypeKind) {
				report_error("Pokusana pogresna dodela konstanti \"" + currConstName +
						"\" na liniji " + currConstLine, null);
			} else {
				Obj cnstNode = Tab.insert(Obj.Con, currConstName, declType);
				cnstNode.setAdr(val);
				report_info("Deklarisana simbolicka konstanta \"" + currConstName + "\" na liniji " + currConstLine, null, cnstNode);
			}
		}
	}
	public void visit(SingleConstDecl singleConstDecl) {
		if(!already_defined(singleConstDecl.getConstName(), singleConstDecl.getLine())) {
			valNames.add(singleConstDecl.getConstName());
			valLines.add(singleConstDecl.getLine());
		} else {
			valTypes.remove(valTypes.size()-1);
			valVals.remove(valVals.size()-1);
		}
	}	
	public void visit(MultipleConstDecl multipleConstDecl) {
		if(!already_defined(multipleConstDecl.getConstName(), multipleConstDecl.getLine())) {
			valNames.add(multipleConstDecl.getConstName());
			valLines.add(multipleConstDecl.getLine());
		} else {
			valTypes.remove(valTypes.size()-1);
			valVals.remove(valVals.size()-1);
		}
	}
	public void visit(NumConst numConst) {
		log.info(valTypes.size());
		valTypes.add(Struct.Int);
		valVals.add(numConst.getConstVal());
		log.info(valTypes.size());
	}
	public void visit(CharConst charConst) {
		valTypes.add(Struct.Char);
		valVals.add((int) charConst.getConstVal());
	}
	public void visit(BoolConst boolConst) {
		valTypes.add(Struct.Bool);
		valVals.add(boolConst.getConstVal() ? 1 : 0);
	}
	
	//VARIABLES
	public void visit(MultipleVarDeclarations multipleVarDeclarations) {
		Struct declType = multipleVarDeclarations.getType().struct;
		while(!valNames.isEmpty()) {
			String varName = valNames.remove(0);
			int isArray = valTypes.remove(0);
			int line = valLines.remove(0);
			if(isArray == 1) {
				if(currentClass != null && currentMethod == null)
					Tab.insert(Obj.Fld, varName, new Struct(Struct.Array, declType));
				else {
					Obj o = Tab.insert(Obj.Var, varName, new Struct(Struct.Array, declType));
					//da bismo razlikovali formalne agrumente od lokalnih promenljivih
					o.setFpPos(-1);
				report_info("Deklarisan lokalni niz \"" + varName + "\" na liniji " + line, null, o);
				}
			}
			else {
				if(currentClass != null && currentMethod == null)
					Tab.insert(Obj.Fld, varName, declType);
				else {
					Obj o = Tab.insert(Obj.Var, varName, declType);
					//da bismo razlikovali formalne agrumente od lokalnih promenljivih
					o.setFpPos(-1);
				report_info("Deklarisana lokalna promenljiva \"" + varName + "\" na liniji " + line, null, o);
				}
			}
			
		}
	}
	public void visit(VarNonEmptyDecl varNonEmptyDecl) {
		Struct declType = varNonEmptyDecl.getType().struct;
		while(!valNames.isEmpty()) {
			String varName = valNames.remove(0);
			int isArray = valTypes.remove(0);
			int line = valLines.remove(0);
			if(isArray == 1) {
				Obj o = Tab.insert(Obj.Var, varName, new Struct(Struct.Array, declType));
				report_info("Deklarisan globalni niz \"" + varName + "\" na liniji " + line, null, o);
			}
			else {
				Obj o = Tab.insert(Obj.Var, varName, declType);
				report_info("Deklarisana globalna promenljiva \"" + varName + "\" na liniji " + line, null, o);
			}
			
		}
	}	
	public void visit(VarArray varArray) {
		if(validScopeOpened && !already_defined(varArray.getVarName(), varArray.getLine())) {
			valNames.add(varArray.getVarName());
			valLines.add(varArray.getLine());
			valTypes.add(1);
		}
	}
	public void visit(VarNoArray varNoArray) {
		if(validScopeOpened && !already_defined(varNoArray.getVarName(), varNoArray.getLine())) {
			valNames.add(varNoArray.getVarName());
			valLines.add(varNoArray.getLine());
			valTypes.add(0);
		}
	}
  
	//CLASSES
	public void visit(ClassDecl classDecl) {
		if(currentClass != null) {
			Tab.chainLocalSymbols(currentClass.getType());
			Tab.closeScope();
			currentClass = null;
		}
	}	
	public void visit(ClassNameNoExtends classNameNoExtends) {
		if(already_defined(classNameNoExtends.getClassName(), classNameNoExtends.getLine())) {
			validScopeOpened = false;
			return;
		}
		currentClass = Tab.insert(Obj.Type, classNameNoExtends.getClassName(), new Struct(Struct.Class));
		//classNameNoExtends.obj = currentClass;
		Tab.openScope();
		validScopeOpened = true;
		//report_info("Deklarisana klasa " + classNameNoExtends.getClassName(), classNameNoExtends);
	}	
	public void visit(ClassNameExtends classNameExtends) {
		if(already_defined(classNameExtends.getClassName(), classNameExtends.getLine())) {
			validScopeOpened = false;
			return;
		}
		Obj typeNode = Tab.find(classNameExtends.getType().getName());
		if(typeNode == Tab.noObj) {
			validScopeOpened = false;
			report_error("Greska na liniji " + classNameExtends.getLine() +
					": Ne postoji nadklasa " + typeNode.getName() + " u tabeli simbola!", null);
		} else {
			if(Obj.Type == typeNode.getKind()) {
				Struct overClass = typeNode.getType();
				currentClass =  Tab.insert(Obj.Type, classNameExtends.getClassName(), new Struct(Struct.Class, overClass));
				classNameExtends.obj = currentClass;
				Tab.openScope();
				validScopeOpened = true;
				//report_info("Deklarisana klasa " + classNameExtends.getClassName(), classNameExtends);
			} else {
				validScopeOpened = false;
				report_error("Greska na liniji " + classNameExtends.getLine() +
						": Ime " + typeNode.getName() + " ne predstavlja klasu!", null);
			}
		}
	}
	
	//METHODS AND CONSTRUCTORS	
	public void visit(FunType funType) {
		if(funType.getType().struct == Tab.noType) {
			validScopeOpened = false;
			currentMethodType = Tab.noType;
			report_error("Greska na liniji " + funType.getLine() + ": Povratni tip metode" +
					"ne postoji u tabeli simbola!", funType);
		} else {
			currentMethodType = funType.getType().struct;
		}
	}	
	public void visit(MethodName methodName) {
		if(currentClass == null || !methodName.getFunName().equals(currentClass.getName())) {
			if(already_defined(methodName.getFunName(), methodName.getLine())) {
				validScopeOpened = false;
				return;
			}
			currentMethod = Tab.insert(Obj.Meth, methodName.getFunName(), currentMethodType);
			methodName.obj = currentMethod;
			Tab.openScope();
			fpCounter = 0;
			validScopeOpened = true;
		} else {
			//currentMethod = Tab.insert(Obj.Meth, methodName.getFunName(), currentClass.getType());
			currentMethod = Tab.insert(Obj.Meth, methodName.getFunName(), Tab.charType);
			methodName.obj = currentMethod;
			Tab.openScope();
			fpCounter = 0;
			validScopeOpened = true;
		}
		
	}	
	public void closeMethod() {
		if(currentMethod != null) {	
			currentMethod.setLevel(fpCounter);
			fpCounter = 0;
			Tab.chainLocalSymbols(currentMethod);
			//report_info("form pars: " + currentMethod.getLevel() + "; total vars: " + currentMethod.getLocalSymbols().size(), null);
			Tab.closeScope();
			currentMethod = null;
			currentMethodType = Tab.noType;
		}
	}	
	public void visit(CstrDecl cstrDecl) {
		closeMethod();
	}	
	public void visit(VoidMethodDecl voidMethodDecl) {
		closeMethod();
	}	
	public void visit(TypeMethodDecl typeMethodDecl) {
		closeMethod();
	}	
	public void visit(TypeMethodDeclarations typeMethodDeclarations) {
		closeMethod();
	}	
	public void visit(VoidMethodDeclarations voidMethodDeclarations) {
		closeMethod();
	}	
	public void visit(FormParamArray formParamArray) {
		if(validScopeOpened && !already_defined(formParamArray.getParamName(), formParamArray.getLine())) {
			Obj obj = Tab.insert(Obj.Var, formParamArray.getParamName(), new Struct(Struct.Array, formParamArray.getType().struct));
			obj.setFpPos(fpCounter++);
			report_info("Deklarisan formalni argument \"" + formParamArray.getParamName() + "\" na liniji "
					+ formParamArray.getLine(), null, obj);
		}
	}	
	public void visit(FormParamNoArray formParamNoArray) {
		if(validScopeOpened && !already_defined(formParamNoArray.getParamName(), formParamNoArray.getLine())) {
			Obj obj = Tab.insert(Obj.Var, formParamNoArray.getParamName(), formParamNoArray.getType().struct);
			obj.setFpPos(fpCounter++);
			report_info("Deklarisan formalni argument \"" + formParamNoArray.getParamName() + "\" na liniji "
					+ formParamNoArray.getLine(), null, obj);
		}
	}
	public void visit(SingleActPar singleActPar) {
		Obj calledMethod = calledMethods.peek();
		int calledFpCounter = calledFpCounters.peek();
		if(calledFpCounter >= calledMethod.getLevel()) {
			report_error("Greska na liniji " + singleActPar.getLine() +
					": " + calledMethod.getName() + " prima " + calledMethod.getLevel() + " parametara!", null);
			return;
		}
		calledMethod.getLocalSymbols().forEach(obj -> {
			if(obj.getFpPos() == calledFpCounters.peek()) {
				if(!singleActPar.getExpr().struct.assignableTo(obj.getType())) {
					report_error("Greska na liniji " + singleActPar.getLine() +
							": Pogresan tip argumenta na poziciji " + calledFpCounters.peek() + " pri pozivu metode" + calledMethod.getName()+ "!", null);
				}
			}
		});
		calledFpCounters.pop();
		calledFpCounter++;
		calledFpCounters.push(calledFpCounter);
	}
	public void visit(MultipleActPars multipleActPars) {
		Obj calledMethod = calledMethods.peek();
		int calledFpCounter = calledFpCounters.peek();
		if(calledFpCounter >= calledMethod.getLevel()) {
			report_error("Greska na liniji " + multipleActPars.getLine() +
					": " + calledMethod.getName() + " prima " + calledMethod.getLevel() + " parametara!", null);
			return;
		}
		calledMethod.getLocalSymbols().forEach(obj -> {
			if(obj.getFpPos() == calledFpCounters.peek()) {
				if(!multipleActPars.getExpr().struct.assignableTo(obj.getType())) {
					report_error("Greska na liniji " + multipleActPars.getLine() +
							": Pogresan tip argumenta na poziciji " + calledFpCounters.peek() + " pri pozivu metode" + calledMethod.getName()+ "!", null);
				}
			}
		});
		calledFpCounters.pop();
		calledFpCounter++;
		calledFpCounters.push(calledFpCounter);
	}
	
	
	//TYPES
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getName());
		if(typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getName() + " u tabeli simbola!", null);
			type.struct = Tab.noType;
		} else {
			if(Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime "+ type.getName() + " ne predstavlja tip!", type);
				type.struct = Tab.noType;
			}
		}
	}
	
	
	//DESIGNATORS
	public void visit(DesiOnlyIdent desiOnlyIdent) {
		desiOnlyIdent.obj = Tab.find(desiOnlyIdent.getDesiName());
		if(desiOnlyIdent.obj.equals(Tab.noObj)) {
			report_error("Greska na liniji " + desiOnlyIdent.getLine() +
					": Nije pronadjena promenljiva" + desiOnlyIdent.getDesiName() + " u tabeli simbola!", null);
			return;
		}
		if(desiOnlyIdent.obj.getKind() == Obj.Meth) {
			calledMethods.push(desiOnlyIdent.obj);
			calledFpCounters.push(0);
		} else if(desiOnlyIdent.obj.getKind() == Obj.Var && desiOnlyIdent.obj.getFpPos() != -1 && desiOnlyIdent.obj.getLevel() > 0) {
			report_info("Koriscenje formalnog argumenta \"" + desiOnlyIdent.getDesiName() + 
					"\" na liniji " + desiOnlyIdent.getLine() + ".", null, desiOnlyIdent.obj);
		}
	}
	public void visit(DesiDot desiDot) {
		Obj cl = desiDot.getDesignator().obj;
		desiDot.obj = Tab.noObj;
		if(cl.getType().getKind() == Struct.Class) {
			cl.getType().getMembers().forEach(obj -> {
				if(obj.getName().equals(desiDot.getAttrName())) desiDot.obj = obj;
			});
			if(desiDot.obj.equals(Tab.noObj)) {
				report_error("Greska na liniji " + desiDot.getLine() +
						": Klasa " + cl.getName() + " nema objekat " + desiDot.getAttrName(), null);
			} else if(desiDot.obj.getKind() == Obj.Meth) {
				calledMethods.push(desiDot.obj);
			}
		} else {
			report_error("Greska na liniji " + desiDot.getLine() +
				": " + cl.getName() + " nije objekat klase!", null);
		}
	}
	public void visit(DesiArray desiArray) {
		desiArray.obj = new Obj(Obj.Elem, "", desiArray.getDesignator().obj.getType().getElemType());
		Obj arr = desiArray.getDesignator().obj;
		if(arr.getType().getKind() != Struct.Array) {
			report_error("Greska na liniji " + desiArray.getLine() +
					": " + arr.getName() + " mora biti niz!", null);
		}
		if(!desiArray.getExpr().struct.equals(Tab.intType)) {
			report_error("Greska na liniji " + desiArray.getLine() +
					": Indeks niza " + arr.getName() + " mora biti tipa int!", null);
		} else {
			report_info("Pristup nizu \"" + arr.getName() + "\" na liniji " + desiArray.getLine() + ".", null, arr);
		}
	}
	
	
	//STATEMENTS
	public void visit(AssignDesignatorStmt assignDesignatorStmt) {
		if(assignDesignatorStmt.getDesignator().obj.getKind() != Obj.Var &&
				assignDesignatorStmt.getDesignator().obj.getKind() != Obj.Fld &&
				assignDesignatorStmt.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Greska na liniji " + assignDesignatorStmt.getLine() + 
					": Na levoj strani dodele se mora nalaziti promenljiva, element niza ili polje klase", null);
		} else {
			if(!assignDesignatorStmt.getExpr().struct.assignableTo(assignDesignatorStmt.getDesignator().obj.getType()))
	    		report_error("Greska na liniji " + assignDesignatorStmt.getLine() + 
	    				": Nekompatibilni tipovi u dodeli vrednosti!", null);
		}
	}
	public void visit(DesiStatementActPars desiStatementActPars) {
		if(desiStatementActPars.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("Greska na liniji " + desiStatementActPars.getLine() + 
    				": " + desiStatementActPars.getDesignator().obj.getName() + " ne predstavlja ime metode!", null);
			return;
		}
		Obj calledMethod = calledMethods.peek();
		if(calledFpCounters.peek() < calledMethod.getLevel()) {
			report_error("Greska na liniji " + desiStatementActPars.getLine() +
					": " + calledMethod.getName() + " prima " + calledMethod.getLevel() + " parametara!", null);
			return;
		}
		calledMethods.pop();
		calledFpCounters.pop();
		report_info("Poziv funkcije \"" + calledMethod.getName()  + "\" na liniji " + 
				desiStatementActPars.getLine() + ".", null, calledMethod);
	}
	public void visit(DesiStatementInc desiStatementInc) {
		if(desiStatementInc.getDesignator().obj.getKind() != Obj.Var &&
				desiStatementInc.getDesignator().obj.getKind() != Obj.Fld &&
						desiStatementInc.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Greska na liniji " + desiStatementInc.getLine() + 
					": Na levoj strani dodele se mora nalaziti promenljiva, element niza ili polje klase", null);
		} else {
			if(!desiStatementInc.getDesignator().obj.getType().equals(Tab.intType))
	    		report_error("Greska na liniji " + desiStatementInc.getLine() + 
	    				": Nekompatibilni tipovi u dodeli vrednosti!", null);
		}
	}
	public void visit(DesiStatementDec desiStatementDec) {
		if(desiStatementDec.getDesignator().obj.getKind() != Obj.Var &&
				desiStatementDec.getDesignator().obj.getKind() != Obj.Fld &&
						desiStatementDec.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Greska na liniji " + desiStatementDec.getLine() + 
					": Na levoj strani dodele se mora nalaziti promenljiva, element niza ili polje klase", null);
		} else {
			if(!desiStatementDec.getDesignator().obj.getType().equals(Tab.intType))
	    		report_error("Greska na liniji " + desiStatementDec.getLine() + 
	    				": Nekompatibilni tipovi u dodeli vrednosti!", null);
		}
	}
	public void visit(ReturnStatementEmpty returnStatementEmpty) {
		if(!currentMethod.getType().equals(Tab.noType)) {
			report_error("Greska na liniji " + returnStatementEmpty.getLine() +
					": Funkcija " + currentMethod.getName() + " vraca pogresan tip!", null);
		}
	}
	public void visit(ReturnStatementNonEmpty returnStatementNonEmpty) {
		if(!currentMethod.getType().equals(returnStatementNonEmpty.getExpr().struct)) {
			report_error("Greska na liniji " + returnStatementNonEmpty.getLine() +
					": Funkcija " + currentMethod.getName() + " vraca pogresan tip!", null);
		}
	}
	public void visit(DesiStatementArrayAssign desiStatementArrayAssign) {
		if(desiStatementArrayAssign.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_error("Greska na liniji " + desiStatementArrayAssign.getLine() + 
					": Desna strana dodele mora biti niz!", null);
		}
		if(!desiStatementArrayAssign.getDesignator().obj.getType().getElemType().assignableTo(arrayAssignType)) {
			report_error("Greska na liniji " + desiStatementArrayAssign.getLine() + 
    				": Nekompatibilni tipovi u dodeli vrednosti!", null);
		}
		arrayAssignType = Tab.noType;
	}
	public void visit(ListDesiStartWithDesi listDesiStartWithDesi) {
		if(listDesiStartWithDesi.getDesignator().obj.getKind() != Obj.Var &&
				listDesiStartWithDesi.getDesignator().obj.getKind() != Obj.Fld &&
						listDesiStartWithDesi.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Greska na liniji " + listDesiStartWithDesi.getLine() + 
					": Na levoj strani dodele se mora nalaziti promenljiva, element niza ili polje klase", null);
		} else {
			if(arrayAssignType.equals(Tab.noType)) {
				arrayAssignType = listDesiStartWithDesi.getDesignator().obj.getType();
			} else {
				if(!arrayAssignType.equals(listDesiStartWithDesi.getDesignator().obj.getType()))
		    		report_error("Greska na liniji " + listDesiStartWithDesi.getLine() + 
		    				": Nekompatibilni tipovi u dodeli vrednosti!", null);
			}
		}
	}
	public void visit(AdditionalDesiWithDesi additionalDesiWithDesi) {
		if(additionalDesiWithDesi.getDesignator().obj.getKind() != Obj.Var &&
				additionalDesiWithDesi.getDesignator().obj.getKind() != Obj.Fld &&
						additionalDesiWithDesi.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Greska na liniji " + additionalDesiWithDesi.getLine() + 
					": Na levoj strani dodele se mora nalaziti promenljiva, element niza ili polje klase", null);
		} else {
			if(arrayAssignType.equals(Tab.noType)) {
				arrayAssignType = additionalDesiWithDesi.getDesignator().obj.getType();
			} else {
				if(!arrayAssignType.equals(additionalDesiWithDesi.getDesignator().obj.getType()))
		    		report_error("Greska na liniji " + additionalDesiWithDesi.getLine() + 
		    				": Nekompatibilni tipovi u dodeli vrednosti!", null);
			}
		}
	}
	
	
	//CONTROL FLOW
	public void visit(IfStatement ifStatement) {
		if(!ifStatement.getCondition().struct.equals(boolType)) {
			report_error("Greska na liniji " + ifStatement.getLine() + 
    				": Uslov if naredbe mora biti tipa bool!", null);
		}
	}

	public void visit(IfElseStatement ifElseStatement) {
		if(!ifElseStatement.getCondition().struct.equals(boolType)) {
			report_error("Greska na liniji " + ifElseStatement.getLine() + 
    				": Uslov if naredbe mora biti tipa bool!", null);
		}
	}

	public void visit(WhileStatement whileStatement) {
		inLoop = false;
	}

	public void visit(BreakStatement breakStatement) {
		if(!inLoop) {
			report_error("Greska na liniji " + breakStatement.getLine() + 
    				": Naredba break ne sme da se poziva van petlje!", null);
		}
	}

	public void visit(ContinueStatement continueStatement) {
		if(!inLoop) {
			report_error("Greska na liniji " + continueStatement.getLine() + 
    				": Naredba break ne sme da se poziva van petlje!", null);
		}
	}
	public void visit(ForeachStatement foreachStatement) {
		inLoop = false;
		if(foreachStatement.getDesignator().obj.getType().getKind() != Struct.Array){
			report_error("Greska na liniji " + foreachStatement.getLine() + 
    				": Naredba break ne sme da se poziva van petlje!", null);
		}	
	}
	public void visit(ForeachIterIdent foreachIterIdent) {
		inLoop = true;
		Obj iter = Tab.find(foreachIterIdent.getIterName());
		foreachIterIdent.obj = iter;
		ForeachStatement fs = (ForeachStatement)foreachIterIdent.getParent();
		if(iter == Tab.noObj || fs.getDesignator().obj.getType().getElemType() != iter.getType()){
			report_error("Greska na liniji " + fs.getLine() + ": Promenljiva " + 
					iter.getName() + " mora biti definisana i po tipu odgovarati nizu " + fs.getDesignator().obj.getName() + "!", null);
		}
	}

	//CONDITIONS
	public void visit(SingleCondition singleCondition) {
		singleCondition.struct = singleCondition.getCondTerm().struct;
		SyntaxNode parent = singleCondition.getParent();
		if(parent.getClass() == WhileStatement.class) {
			inLoop = true;
		}
		if(!singleCondition.struct.equals(boolType)) {
			report_error("Greska na liniji " + singleCondition.getLine() + 
					": Uslov mora biti tipa bool!", null);
		}
	}
	public void visit(ConditionDisjunction conditionDisjunction) {
		conditionDisjunction.struct = boolType;
		SyntaxNode parent = conditionDisjunction.getParent();
		if(parent.getClass() == WhileStatement.class) {
			inLoop = true;
		}
		if(!conditionDisjunction.getCondition().struct.equals(boolType) 
				|| !conditionDisjunction.getCondTerm().struct.equals(boolType)) {
			report_error("Greska na liniji " + conditionDisjunction.getLine() + 
					": Uslov mora biti tipa bool!", null);
		}
	}
	public void visit(SingleCondTerm singleCondTerm) {
		singleCondTerm.struct = singleCondTerm.getCondFact().struct;
	}
	public void visit(CondTermConjunction condTermConjunction) {
		condTermConjunction.struct = boolType;
	}
	public void visit(CondFactExpr condFactExpr) {
		condFactExpr.struct = condFactExpr.getExpr().struct;
	}
	public void visit(CondFactRel condFactRel) {
		if(!condFactRel.getExpr().struct.compatibleWith(condFactRel.getExpr1().struct)) {
			report_error("Greska na liniji " + condFactRel.getLine() +
					": Vrednosti u if uslovu nisu kompatibilne!", null);
		} else {
			if(condFactRel.getExpr().struct.isRefType() || condFactRel.getExpr1().struct.isRefType()) {
				if(RelEq.class != condFactRel.getRelop().getClass() && RelNeq.class != condFactRel.getRelop().getClass() ) {
					report_error("Greska na liniji " + condFactRel.getLine() +
							": Klase i nizovi se mogu porediti samo operatorima (==) i (!=) ", null);
				}
			}
		}
		condFactRel.struct = boolType;
	}
	
	
	//FACTORS
	public void visit(FactorFuncCall factorFuncCall) {
		if(calledFpCounters.peek() < calledMethods.peek().getLevel()) {
			report_error("Greska na liniji " + factorFuncCall.getLine() +
					": " + calledMethods.peek().getName() + " prima " + calledMethods.peek().getLevel() + " parametara!", null);
		}
		calledMethods.pop();
		calledFpCounters.pop();
		Obj func = factorFuncCall.getDesignator().obj;
		if(func.getKind() != Obj.Meth) {
			report_error("Greska na liniji " + factorFuncCall.getLine() + 
					": " + func.getName() + " nije funkcija!", null);
			factorFuncCall.struct = Tab.noType;
		} else {
			if(func.getType().equals(Tab.noType)) {
				report_error("Greska na liniji " + factorFuncCall.getLine() + 
						": Funkcija" + func.getName() + " nema povratni tip!", null);
				factorFuncCall.struct = Tab.noType;
			} else {
				factorFuncCall.struct = func.getType();
			}
		}
		report_info("Poziv funkcije \"" + func.getName() + "\" na liniji " + 
				factorFuncCall.getLine() + ".", null, func);
	}
	public void visit(FactorDesi factorDesi) {
		factorDesi.struct = factorDesi.getDesignator().obj.getType();
	}
	public void visit(FactorNum factorNum) {
		factorNum.struct = Tab.intType;
	}
	public void visit(FactorChar factorChar) {
		factorChar.struct = Tab.charType;
	}
	public void visit(FactorBool factorBool) {
		factorBool.struct = boolType;
	}
	public void visit(FactorExprInParen factorExprInParen) {
		factorExprInParen.struct = factorExprInParen.getExpr().struct;
	}
	public void visit(FactorNewArray factorNewArray) {
		if(!factorNewArray.getExpr().struct.equals(Tab.intType)) {
			report_error("Greska na liniji " + factorNewArray.getLine() +
					": Izraz mora biti tipa int!", null);
		}
		Obj tip = Tab.find(factorNewArray.getType().getName());
		if(tip.equals(Tab.noObj)) {
			factorNewArray.struct = new Struct(Struct.Array, Tab.noType);
			report_error("Greska na liniji " + factorNewArray.getLine() +
					": Tip " + factorNewArray.getType().getName() + "nije definisan!", null);
		} else {
			factorNewArray.struct = new Struct(Struct.Array, tip.getType());
		}
		
	}
	public void visit(FactorNewCstr factorNewCstr) {
		//TODO
	}
	
	
	//TERMS
	public void visit(SingleTerm singleTerm) {
		singleTerm.struct = singleTerm.getFactor().struct;
	}
	public void visit(MultipleTerm multipleTerm) {
		if(multipleTerm.getTerm().struct != Tab.intType || 
				multipleTerm.getFactor().struct != Tab.intType ) {
			report_error("Greska na liniji " + multipleTerm.getLine() +
					": Izraz mora biti tipa int!", null);
			multipleTerm.struct = Tab.noType;
		} else {			
			multipleTerm.struct = multipleTerm.getFactor().struct;
		}
	}
	
	
	//EXPRESSIONS
	public void visit(NegExpr negExpr) {
		if(negExpr.getTerm().struct != Tab.intType) {
			report_error("Greska na liniji " + negExpr.getLine() +
					": Izraz mora biti tipa int!", null);
			negExpr.struct = Tab.noType;
		} else {			
			negExpr.struct = negExpr.getTerm().struct;
		}
	}
	public void visit(PosExpr posExpr) {
		posExpr.struct = posExpr.getTerm().struct;
	}
//	public void visit(ExprSingleTerm exprSingleTerm) {
//		exprSingleTerm.struct = exprSingleTerm.getTerm().struct;
//	}
	public void visit(ExprMultipleTerms exprMultipleTerms) {
		if(exprMultipleTerms.getExpr().struct != Tab.intType || 
				exprMultipleTerms.getTerm().struct != Tab.intType ) {
			report_error("Greska na liniji " + exprMultipleTerms.getLine() +
					": Izraz mora biti tipa int!", null);
			exprMultipleTerms.struct = Tab.noType;
		} else {			
			exprMultipleTerms.struct = exprMultipleTerms.getTerm().struct;
		}
	}
	
	
	//IO
	public void visit(ReadStatement readStatement) {
		if(readStatement.getDesignator().obj.getKind() != Obj.Var &&
				readStatement.getDesignator().obj.getKind() != Obj.Fld &&
						readStatement.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Greska na liniji " + readStatement.getLine() + 
					": Argument funkcije \"read\" mora biti promenljiva, element niza ili polje klase", null);
		} else {
			if(!readStatement.getDesignator().obj.getType().equals(Tab.intType) &&
				!readStatement.getDesignator().obj.getType().equals(Tab.charType) &&
					!readStatement.getDesignator().obj.getType().equals(boolType)) {
				report_error("Greska na liniji " + readStatement.getLine() + 
						": Argument funkcije \"read\" mora biti primitivnog tipa", null);
			}
		}
	}
	public void visit(PrintStatementWidth printStatementWidth) {
		if(!printStatementWidth.getExpr().struct.equals(Tab.intType) &&
				!printStatementWidth.getExpr().struct.equals(Tab.charType) &&
					!printStatementWidth.getExpr().struct.equals(boolType)) {
				report_error("Greska na liniji " + printStatementWidth.getLine() + 
						": Argument funkcije \"print\" mora biti primitivnog tipa", null);
			}
	}
	public void visit(PrintStatementNoWidth printStatementNoWidth) {
		if(!printStatementNoWidth.getExpr().struct.equals(Tab.intType) &&
				!printStatementNoWidth.getExpr().struct.equals(Tab.charType) &&
					!printStatementNoWidth.getExpr().struct.equals(boolType)) {
				report_error("Greska na liniji " + printStatementNoWidth.getLine() + 
						": Argument funkcije \"print\" mora biti primitivnog tipa", null);
			}
	}
  
  
//    
//    public void visit(ReturnExpr returnExpr) {
//    	returnFound = true;
//    	Struct currMethType = currentMethod.getType();
//    	if(!currMethType.compatibleWith(returnExpr.getExpr().struct)) {
//    		report_error("Greska na liniji " + returnExpr.getLine() + " : tip izraza u return naredbi se ne slaze sa tipom povratne vrednosti funkcije" + currentMethod.getName(), null);
//    	}
//    }


	public boolean passed() {
		return !errorDetected;
	}
	
	public boolean already_defined(String symName, int line) {
		if(Tab.currentScope.findSymbol(symName) != null) {
			report_error("Greska na liniji " + line + ": " + symName + 
					" je vec definisana u trenutnom opsegu", null);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean already_defined_no_msg(String symName) {
		return Tab.currentScope.findSymbol(symName) != null;
	}
}
