package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.scanner.*;

class EmptyStatm extends Statement{
	
	EmptyStatm(int lNum) {
		super(lNum);
	}

	@Override
	public String identify() {
		return "<empty statm> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
	}

	static EmptyStatm parse(Scanner s) {
		enterParser("empty statm");

		EmptyStatm ess = new EmptyStatm(s.curLineNum());

		leaveParser("empty statm");
		return ess;
	}
	
	//Del 3
	@Override
	void check(Block curScope, Library lib) {
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
	}
}