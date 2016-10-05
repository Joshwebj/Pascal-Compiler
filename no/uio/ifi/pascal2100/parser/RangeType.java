package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class RangeType extends Type {
	Constant con1, con2;
	
	public RangeType(int lnum) {
		super(lnum);
	}

	static RangeType parse(Scanner s){
		enterParser("range type");
		
		RangeType rt = new RangeType(s.curLineNum());
		rt.con1 = Constant.parse(s);
		
		s.skip(rangeToken);
		
		rt.con2 = Constant.parse(s);
		
		leaveParser("range type");
		return rt;	
	}
	
	@Override
	public String identify() {
		return "<range type> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		con1.prettyPrint();
		Main.log.prettyPrint(" .. "); 
		con2.prettyPrint();
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		con1.check(curScope, lib);
		con2.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		con1.genCode(f);
		con2.genCode(f);
	}
}