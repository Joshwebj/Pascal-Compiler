package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.util.ArrayList;

//Copy from Exercises week 39 2015

class StatmList extends PascalSyntax {
	ArrayList<Statement> statements = new ArrayList<Statement>();

	StatmList(int lNum) {
		super(lNum);
	}

	static StatmList parse(Scanner s) {
		enterParser("statm list");

		StatmList sl = new StatmList(s.curLineNum());
		while (true) {
			sl.statements.add(Statement.parse(s));
			if (s.curToken.kind != semicolonToken){
				break;
			}
			s.readNextToken();
		}

		leaveParser("statm list");
		return sl;
	}

	@Override
	public String identify() {
		return "<statm list> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		for (int i = 0; i < statements.size(); i++) {
			statements.get(i).prettyPrint();
			if (i < statements.size() - 1) {
				Main.log.prettyPrintLn(";");
			}
		}
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		for (Statement s : statements) {
			s.check(curScope, lib);
		}
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		for (Statement s : statements) {
			s.genCode(f);
		}
	}
}



