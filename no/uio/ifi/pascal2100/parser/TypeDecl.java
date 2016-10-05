package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class TypeDecl extends PascalDecl {
	TypeName tyName;
	Type type;
	
	TypeDecl(String id, int lNum) {
		super(id, lNum);
	}

	static public TypeDecl parse(Scanner s) {
		enterParser("type decl");
		s.test(nameToken);
		
		TypeDecl td = new TypeDecl(s.curToken.id, s.curLineNum());
		td.tyName = TypeName.parse(s);
		
		s.skip(equalToken);
		
		td.type = Type.parse(s);
		
		s.skip(semicolonToken);
		leaveParser("type decl");
		return td;
	}

	@Override
	public String identify() {
		if(lineNum == -1) return "<type decl> in the library";
		return "<type decl> " + name + " on line " + lineNum;
	}

	@Override
	public void prettyPrint() {
		tyName.prettyPrint();
		Main.log.prettyPrint(" = ");
		type.prettyPrint();
		Main.log.prettyPrint("; ");
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		tyName.check(curScope, lib);
		type.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
	}
}













