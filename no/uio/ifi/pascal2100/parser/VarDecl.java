package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class VarDecl extends PascalDecl {
	Type type;
	
	VarDecl(String id, int lNum) {
		super(id, lNum);
	}

	static public VarDecl parse(Scanner s) {
		enterParser("var decl");
		s.test(nameToken);
		
		VarDecl vd = new VarDecl(s.curToken.id, s.curLineNum());
		
		s.readNextToken();
		s.skip(colonToken);
		
		vd.type = Type.parse(s);
		
		s.skip(semicolonToken);
		leaveParser("var decl");
		return vd;
	}

	@Override
	public String identify() {
		return "<var decl> " + name + " on line " + lineNum;
	}

	@Override
	public void prettyPrint() {
		Main.log.prettyPrint(name + ": ");
		type.prettyPrint();
		Main.log.prettyPrint("; ");
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		type.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
	}
}



