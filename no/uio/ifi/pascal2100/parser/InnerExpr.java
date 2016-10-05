package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class InnerExpr extends Factor {
	Expression expr;	
	
	public InnerExpr(int lnum) {
		super(lnum);
	}

	static InnerExpr parse(Scanner s){
		enterParser("inner expr");
		s.skip(leftParToken);
		
		InnerExpr ie = new InnerExpr(s.curLineNum());
		ie.expr = Expression.parse(s);	

		s.skip(rightParToken);
		leaveParser("inner expr");
		return ie;	
	}
	
	@Override
	public String identify() {
		return "<inner expr> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("(");
		expr.prettyPrint();
		Main.log.prettyPrint(")");
	}

	@Override
	void check(Block curScope, Library lib) {
		expr.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		expr.genCode(f);
	}
}
