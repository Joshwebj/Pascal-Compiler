package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.util.ArrayList;

class ProcCallStatm extends Statement {
	ArrayList<Expression> exprs = new ArrayList<Expression>();
	String name;
	
	//Del 4
	ProcDecl procRef;
	
	public ProcCallStatm(int lnum) {
		super(lnum);
	}

	static ProcCallStatm parse(Scanner s){
		enterParser("proc call statm");
		
		ProcCallStatm pcs = new ProcCallStatm(s.curLineNum());
		pcs.name = s.curToken.id;
		
		s.skip(nameToken);
		
		if(s.curToken.kind == leftParToken) {
			s.readNextToken();

			while (true) {
				pcs.exprs.add(Expression.parse(s));
				if (s.curToken.kind == commaToken) {
					s.skip(commaToken);
				} else {
					break;
				}
			}
			s.skip(rightParToken);
		}

		leaveParser("proc call statm");
		return pcs;	
	}
	
	@Override
	public String identify() {
		return "<proc call statm> on line " + lineNum;
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
		PascalDecl d = curScope.findDecl(name,this);
		for(Expression e : exprs){
			e.check(curScope, lib);
		}
		procRef = (ProcDecl)d;
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		
		//Procedure call write
		if(name.equals("write")){
			for (int i = 0; i < exprs.size(); i++) {
				Expression e = exprs.get(i);
				e.genCode(f);
				
				Constant exprEval = e.eval();
				
				if(NumericLiteral.class.isAssignableFrom(exprEval.getClass())
						|| EnumLiteral.class.isAssignableFrom(exprEval.getClass())) {
					f.genInstr("", "pushl", "%eax", "Push param #" + (i + 1) + ".");
					f.genInstr("", "call", "write_int", "");
				} else if (CharLiteral.class.isAssignableFrom(exprEval.getClass())) {
					f.genInstr("", "pushl", "%eax", "Push param #" + (i + 1) + ".");
					f.genInstr("", "call", "write_char", "");
				} else if (StringLiteral.class.isAssignableFrom(exprEval.getClass())) {	
					StringLiteral s = (StringLiteral)exprEval;
					String label = f.getLocalLabel();
					f.genString(label, s.val, ""); 
					f.genInstr("", "leal", label + ",%eax", "Addr(\"" + s.val + "\")");
					f.genInstr("", "pushl", "%eax", "Push param #" + (i + 1) + ".");
					f.genInstr("", "call", "write_string", "");
				} else {
					System.out.println("ERROR: Unknown Constant in ProcCallStatm: " + exprEval.identify());
					System.exit(0);
				}
				
				f.genInstr("", "addl", "$4,%esp", "Pop parameter.");
			}
			
		} else {
			
			for(int i = exprs.size() - 1; i >= 0; i--) {
				exprs.get(i).genCode(f); 
				f.genInstr("", "pushl", "%eax", "Push param #" + (i + 1));
			}
			f.genInstr("", "call",  procRef.progProcFuncName, "");
			
			//Nothing to pop if no parameters
			if(exprs.size() != 0) {
				f.genInstr("", "addl", "$" + (4 * exprs.size()) + ",%esp", "Pop parameters.");
			}
		}
	}
}




