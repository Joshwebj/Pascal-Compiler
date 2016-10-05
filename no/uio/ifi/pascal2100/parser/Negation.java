package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class Negation extends Factor {
	Factor fctr;	
	
	public Negation(int lnum) {
		super(lnum);
	}

	static Negation parse(Scanner s){
		enterParser("negation");
		s.skip(notToken);
		
		Negation n = new Negation(s.curLineNum());
		n.fctr = Factor.parse(s);	

		leaveParser("negation");
		return n;	
	}
	
	@Override
	public String identify() {
		return "<negation> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("not ");
		fctr.prettyPrint();
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		fctr.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		fctr.genCode(f);
		f.genInstr("", "xorl", "$0x1,%eax", " not");
	}
}