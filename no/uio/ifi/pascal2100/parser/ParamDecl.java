package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class ParamDecl extends PascalDecl {
	TypeName tyName;

	public ParamDecl(String id, int lNum) {
		super(id, lNum);
	}

	static ParamDecl parse(Scanner s) {
		enterParser("param decl");

		ParamDecl pd = new ParamDecl(s.curToken.id, s.curLineNum());
		pd.name = s.curToken.id;
		
		s.skip(nameToken);
		s.skip(colonToken);

		pd.tyName = TypeName.parse(s);

		leaveParser("param decl");
		return pd;
	}

	@Override
	public String identify() {
		if(lineNum == -1) return "<param decl> in the library";
		return "<param decl> " + name + " on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint(name + ": ");
		tyName.prettyPrint();
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		tyName.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {	
	}
}
