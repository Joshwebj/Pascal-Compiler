package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class CharLiteral extends Constant {
	String val;
	
	public CharLiteral(int lnum){
		super(lnum);
	}
	
	static CharLiteral parse(Scanner s){
		enterParser("char literal");
		
		CharLiteral cl = new CharLiteral(s.curLineNum());
		cl.val = s.curToken.strVal;
		
		s.skip(stringValToken);
		leaveParser("char literal");
		return cl;	
	}

	@Override
	public String identify() {
		return "<char literal> on line " + lineNum;
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
		int ascii_num = val.charAt(0);
		f.genInstr("", "movl", "$" + ascii_num + ",%eax", " char " + ascii_num);
	}
}
