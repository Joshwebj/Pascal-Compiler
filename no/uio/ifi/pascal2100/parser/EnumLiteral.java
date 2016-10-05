package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class EnumLiteral extends PascalDecl {
	int num;
	
	public EnumLiteral(String id, int lNum) {
		super(id, lNum);
	}

	static EnumLiteral parse(Scanner s) {
		enterParser("enum literal");
		s.test(nameToken);
		
		EnumLiteral el = new EnumLiteral(s.curToken.id, s.curLineNum());
				
		s.skip(nameToken);
		leaveParser("enum literal");
		return el;
	}
	
	@Override
	public String identify() {
		if(lineNum == -1) return "<enum literal> in the library";
		return "<enum literal> " + name + " on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("name");
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		if(name.equals("true")) {
			f.genInstr("", "movl", "$1,%eax", " enum value true (=1)");
		}else if(name.equals("false")) {
			f.genInstr("", "movl", "$0,%eax", " enum value false (=0)");
		} else {
			f.genInstr("", "movl", "$" + num +",%eax", " enum value " + name + " (=" + num +")");
		}
	}
}
