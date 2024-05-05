package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{
	boolean lexErrorDetected;

	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}
%}

%init{
	lexErrorDetected = false;
%init}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" "    |
"\b"   |
"\t"   |
"\r\n" |
"\f"   { }

"program" 	{return new_symbol(sym.PROG, yytext());}
"break"  		{return new_symbol(sym.BREAK, yytext());}
"class"  		{return new_symbol(sym.CLASS, yytext());}
"else" 			{return new_symbol(sym.ELSE, yytext());}
"const" 		{return new_symbol(sym.CONST, yytext());}
"if" 				{return new_symbol(sym.IF, yytext());}
"while" 		{return new_symbol(sym.WHILE, yytext());}
"new" 			{return new_symbol(sym.NEW, yytext());}
"print"   		{return new_symbol(sym.PRINT, yytext());}
"read" 		{return new_symbol(sym.READ, yytext());}
"return"  		{return new_symbol(sym.RETURN, yytext());}
"void" 			{return new_symbol(sym.VOID, yytext());}
"extends"	{return new_symbol(sym.EXTENDS, yytext());}
"continue"	{return new_symbol(sym.CONTINUE, yytext());}
"foreach"	{return new_symbol(sym.FOREACH, yytext());}

"+"      {return new_symbol(sym.PLUS, yytext());}
"-"		{return new_symbol(sym.MINUS, yytext());}
"*"		{return new_symbol(sym.TIMES, yytext());}
"/"		{return new_symbol(sym.DIV, yytext());}
"%"		{return new_symbol(sym.MOD, yytext());}
"=="    {return new_symbol(sym.EQ, yytext());}
"!="		{return new_symbol(sym.NEQ, yytext());}
">"		{return new_symbol(sym.GR, yytext());}
">="	{return new_symbol(sym.GRE, yytext());}
"<"		{return new_symbol(sym.LS, yytext());}
"<="	{return new_symbol(sym.LSE, yytext());}
"&&"	{return new_symbol(sym.AND, yytext());}
"||"		{return new_symbol(sym.OR, yytext());}
"="		{return new_symbol(sym.ASSIGN, yytext());}
"++"	{return new_symbol(sym.INC, yytext());}
"--"		{return new_symbol(sym.DEC, yytext());}
";" 	  	{return new_symbol(sym.SEMI, yytext());}
"," 	  	{return new_symbol(sym.COMMA, yytext());}
"."			{return new_symbol(sym.DOT, yytext());}
"("		{return new_symbol(sym.LPAREN, yytext());}
")"		{return new_symbol(sym.RPAREN, yytext());}
"["		{return new_symbol(sym.LBRACKET, yytext());}
"]"		{return new_symbol(sym.RBRACKET, yytext());}
"{" 		{return new_symbol(sym.LBRACE, yytext());}
"}" 	 	{return new_symbol(sym.RBRACE, yytext());}
"=>"	{return new_symbol(sym.ARROW, yytext());}

"//"             					{ yybegin(COMMENT); }
<COMMENT> .      		{ yybegin(COMMENT); }
<COMMENT> "\r\n" 	{ yybegin(YYINITIAL); }

[0-9]+                 { return new_symbol(sym.NUM, new Integer(yytext())); }
'.'					{ return new_symbol(sym.CHAR, yytext().toCharArray()[1]); }
"true" | "false"		{ return new_symbol(sym.BOOL, new Boolean(yytext())); }	

([a-z]|[A-Z])[a-z|A-Z|0-9|_]* { return new_symbol(sym.IDENT, yytext()); }

. { 
	System.err.println("Leksicka greska (" + yytext() + ") u liniji " + (yyline+1) + ", na poziciji " + (yycolumn+1)); 
	lexErrorDetected = true;
}