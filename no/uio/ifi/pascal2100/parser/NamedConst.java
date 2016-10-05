package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class NamedConst extends Constant {
	String val;
	
	public NamedConst(int lnum){
		super(lnum);
	}
	
	static NamedConst parse(Scanner s){
		enterParser("named const");
		
		NamedConst nc = new NamedConst(s.curLineNum());
		nc.val = s.curToken.id;
		
		s.skip(nameToken);
		leaveParser("named const");
		return nc;	
	}

	@Override
	public String identify() {
		return "<named const> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint(val); 
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		f.genString(f.getLocalLabel(), val, ""); 
	}
}