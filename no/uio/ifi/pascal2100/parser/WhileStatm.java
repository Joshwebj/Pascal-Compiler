package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

/* <while-statm> ::= ’while’ <expression> ’do’ <statement> */
/* Copied from the slides (Wednesday 16. septemper)*/

class WhileStatm extends Statement {
	Expression expr;
	Statement body;

	WhileStatm(int lNum) {
		super(lNum);
	}

	@Override
	public String identify() {
		return "<while-statm> on line " + lineNum;
	}

	static WhileStatm parse(Scanner s) {
		enterParser("while statm");

		WhileStatm ws = new WhileStatm(s.curLineNum());
		s.skip(whileToken);

		ws.expr = Expression.parse(s);
		s.skip(doToken);
		ws.body = Statement.parse(s);

		leaveParser("while statm");
		return ws;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("while ");
		expr.prettyPrint();
		Main.log.prettyPrint(" do ");
		Main.log.prettyIndent();
		body.prettyPrint();
		Main.log.prettyOutdent();
	}

	// Del 3
	@Override
	void check(Block curScope, Library lib) {
		expr.check(curScope, lib);
		body.check(curScope, lib);
	}

	// Del 4
	@Override
	void genCode(CodeFile f) {
		String startLabel = f.getLocalLabel(), 
				endLabel = f.getLocalLabel();
		
		f.genInstr(startLabel, "", "", "Start while-statement");
		
		expr.genCode(f);
		
		f.genInstr("", "cmpl", "$0,%eax", "");
		f.genInstr("", "je", endLabel, "");
		
		body.genCode(f);
		
		f.genInstr("", "jmp", startLabel, "");
		f.genInstr(endLabel, "", "", "End while-statement");
	}
}


















