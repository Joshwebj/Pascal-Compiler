package no.uio.ifi.pascal2100.parser;

import static no.uio.ifi.pascal2100.scanner.TokenKind.subtractToken;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;

class PrefixOpr extends Operator {
	Token oprT;
	
	public PrefixOpr(int lnum) {
		super(lnum);
	}
	
	static PrefixOpr parse(Scanner s) {
		enterParser("prefix opr");
		
		PrefixOpr fo = new PrefixOpr(s.curLineNum());
		fo.oprT = s.curToken;

		s.readNextToken();
		leaveParser("prefix opr");
		return fo;	
	}
	@Override
	public String identify() {
		return "<prefix opr> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint(oprT.kind.toString()); 
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		if(oprT.kind == subtractToken){
			f.genInstr("", "negl", "%eax", " - (prefix)");
		}
	}
}