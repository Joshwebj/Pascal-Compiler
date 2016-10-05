package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class NumericLiteral extends Constant {
	int val;
	
	public NumericLiteral(int lnum){
		super(lnum);
	}
	
	static NumericLiteral parse(Scanner s){
		enterParser("number literal");
		
		NumericLiteral nl = new NumericLiteral(s.curLineNum());
		nl.val = s.curToken.intVal;
		
		s.skip(intValToken);
		leaveParser("number literal");
		return nl;	
	}

	@Override
	public String identify() {
		return "<number literal> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("" + val); 
	}

	@Override
	void check(Block curScope, Library lib) {
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		f.genInstr("", "movl", "$" + val + ",%eax", " int " + val);
	}
}
