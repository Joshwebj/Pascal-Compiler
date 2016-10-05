package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.util.ArrayList;

class FuncCall extends Factor {
	ArrayList<Expression> exprs = new ArrayList<Expression>();
	String name;
	
	//Del 4
	PascalDecl ref;
	
	public FuncCall(int lnum) {
		super(lnum);
	}

	static FuncCall parse(Scanner s){
		enterParser("func call");
		
		FuncCall fc = new FuncCall(s.curLineNum());
		fc.name = s.curToken.id;
		
		s.skip(nameToken);
		s.skip(leftParToken);
		
		while(true){
			fc.exprs.add(Expression.parse(s));
			
			if(s.curToken.kind == commaToken)
				s.readNextToken();
			else
				break;		
		}

		s.skip(rightParToken);
		leaveParser("func call");
		return fc;	
	}
	
	@Override
	public String identify() {
		return "<func call> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint(name);
		Main.log.prettyPrint("(");
		for (int i = 0; i < exprs.size(); i++) {
			exprs.get(i).prettyPrint();
			if (i < exprs.size() - 1)
				Main.log.prettyPrint(", ");
		}
		Main.log.prettyPrint(")");
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		ref = curScope.findDecl(name, this);	
		for(Expression e : exprs){
			e.check(curScope, lib);
		}	
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		for(int i = exprs.size() - 1; i >= 0; i--) {
			exprs.get(i).genCode(f); 
			f.genInstr("", "pushl", "%eax", "");
		}
		f.genInstr("", "call", ref.progProcFuncName, "");
		f.genInstr("", "addl", "$" + (4 * exprs.size()) + ",%esp", "");
	}
}





