package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class TypeName extends Type {
	String tName;
	PascalDecl ref;
	
	public TypeName(int lnum) {
		super(lnum);
	}

	static TypeName parse(Scanner s){
		enterParser("type name");
		
		TypeName tn = new TypeName(s.curLineNum());
		tn.tName = s.curToken.id;
		
		s.skip(nameToken);
		leaveParser("type name");
		return tn;	
	}
	
	@Override
	public String identify() {
		return "<type name> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint(tName); 
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		ref = curScope.findDecl(tName, this);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
	}
}
