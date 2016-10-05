package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class ArrayType extends Type {
	Type type, ofType;
	
	public ArrayType(int lnum) {
		super(lnum);
	}

	static ArrayType parse(Scanner s){
		enterParser("array type");
		s.skip(arrayToken);
		s.skip(leftBracketToken);
		
		ArrayType at = new ArrayType(s.curLineNum());
		at.type = Type.parse(s);
		
		s.skip(rightBracketToken);
		s.skip(ofToken);
		
		at.ofType = Type.parse(s);

		leaveParser("array type");
		return at;	
	}
	
	@Override
	public String identify() {
		return "<array type> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("array[");
		type.prettyPrint();
		Main.log.prettyPrint("] of ");
		ofType.prettyPrint();

	}

	@Override
	void check(Block curScope, Library lib) {
		type.check(curScope, lib);
		ofType.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		//NOT IMPLEMENTED
	}
}