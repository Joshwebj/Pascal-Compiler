package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class CompoundStatm extends Statement {
	StatmList sl;
	
	public CompoundStatm(int lNum) {
		super(lNum);
	}

	static CompoundStatm parse(Scanner s){
		enterParser("compound statm");
		s.skip(beginToken);
		
		CompoundStatm cs = new CompoundStatm(s.curLineNum());
		cs.sl = StatmList.parse(s);
		
		s.skip(endToken);
		leaveParser("compound statm");
		return cs;
	}
	
	@Override
	public String identify() {
		return "<compound statm> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrintLn("");
		Main.log.prettyPrintLn("begin ");
		Main.log.prettyIndent();
		sl.prettyPrint();
		Main.log.prettyOutdent();
		Main.log.prettyPrintLn("");
		Main.log.prettyPrint("end");
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		sl.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		sl.genCode(f); 
	}
}