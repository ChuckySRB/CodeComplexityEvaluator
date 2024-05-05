package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class Compiler {
	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(Compiler.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/test301.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			
			//Leksicka i sintaksna analiza
			Yylex lexer = new Yylex(br);
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();
	        
	        if(!lexer.lexErrorDetected && !p.errorDetected) {
		        Program prog = (Program)(s.value); 
				// ispis sintaksnog stabla
				log.info(prog.toString(""));
				log.info("===================================");
	
				//Semanticka analiza
				Tab.init();
				Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", new Struct(Struct.Bool)));
				SemanticAnalyzer sa = new SemanticAnalyzer();
				prog.traverseBottomUp(sa);
				log.info("===================================");
				Tab.dump();
						//tsdump(new MyDumpSymbolTableVisitor());
				
				//Generisanje koda
				if(sa.passed()) {
					File objFile = new File("test/program.obj");
					if(objFile.exists()) objFile.delete();
					
					CodeGenerator cg = new CodeGenerator();
					prog.traverseBottomUp(cg);
					Code.dataSize = sa.nVars;
					Code.mainPc = cg.getMainPc();
					Code.write(new FileOutputStream(objFile));
					//poruka o (ne)uspesnom parsiranju
					log.info("Parsiranje je uspesno zavrseno!");
				} else {
					log.info("Parsiranje nije uspesno zavrseno: Detektovana greska tokom semanticke analize!");
				}
	        } else {
	        	log.info("Parsiranje nije uspesno zavrseno!");
	        }
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	public static void tsdump(SymbolTableVisitor stv) {
		System.out.println("=====================SYMBOL TABLE DUMP=========================");
		if (stv == null)
			stv = new DumpSymbolTableVisitor();
		for (Scope s = Tab.currentScope; s != null; s = s.getOuter()) {
			s.accept(stv);
		}
		System.out.println(stv.getOutput());
	}
	
}
