package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.util.ArrayList;

class ConstDeclPart extends PascalSyntax {
	ArrayList<ConstDecl> decls = new ArrayList<ConstDecl>();
	
	public ConstDeclPart(int lnum) {
		super(lnum);
	}
	
	static ConstDeclPart parse(Scanner s) {
		enterParser("const decl part");

		s.skip(constToken);
		
		ConstDeclPart cdp = new ConstDeclPart(s.curLineNum());
		while(true) {
			cdp.decls.add(ConstDecl.parse(s));
			if (s.curToken.kind != nameToken)
				break;
		}

		leaveParser("const decl part");
		return cdp;
	}

	@Override
	public String identify() {
		return "<const decl part> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("const ");
		for (int i = 0; i < decls.size(); i++) {
			decls.get(i).prettyPrint();
			
			//For too many declarations make lines too long
			if(i % 5 == 0 && i != 0){
				Main.log.prettyPrint("\n    ");
			}
		}
		Main.log.prettyPrintLn("");
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		for(ConstDecl d : decls){
			d.check(curScope, lib);
		}		
	}

	// Del 4
	@Override
	void genCode(CodeFile f) {
		for(ConstDecl d : decls){
			d.genCode(f);
		}
	}
}
