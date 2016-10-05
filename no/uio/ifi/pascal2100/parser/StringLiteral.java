package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class StringLiteral extends Constant {
	String val;
	
	public StringLiteral(int lnum){
		super(lnum);
	}
	
	static StringLiteral parse(Scanner s){
		enterParser("string literal");
		s.test(stringValToken);
		
		StringLiteral sl = new StringLiteral(s.curLineNum());
		
		sl.val = s.curToken.strVal;
		s.readNextToken();
		
		leaveParser("string literal");
		return sl;	
	}

	@Override
	public String identify() {
		return "<string literal> on line " + lineNum;
	}

	@Override
	void prettyPrint() {	
		Main.log.prettyPrint("'");
		Main.log.prettyPrint(val);	
		Main.log.prettyPrint("'");	
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

