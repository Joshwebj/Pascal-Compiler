package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class ConstDecl extends PascalDecl {
	Constant con;

	public ConstDecl(String id, int lNum) {
		super(id, lNum);
	}

	static ConstDecl parse(Scanner s) {
		enterParser("const decl");

		ConstDecl cd = new ConstDecl(s.curToken.id, s.curLineNum());

		s.skip(nameToken);
		s.skip(equalToken);

		cd.con = Constant.parse(s);

		s.skip(semicolonToken);
		leaveParser("const decl");
		return cd;
	}

	@Override
	public String identify() {
		if(lineNum == -1) return "<const decl> in the library";
		return "<const decl> " + name + " on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint(name + " = ");
		con.prettyPrint();
		Main.log.prettyPrint("; ");
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		con.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		con.genCode(f);
	}
}
