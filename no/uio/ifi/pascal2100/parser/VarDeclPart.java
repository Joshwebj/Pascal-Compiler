package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.util.ArrayList;

class VarDeclPart extends PascalSyntax {
	ArrayList<VarDecl> decls = new ArrayList<VarDecl>();
	
	public VarDeclPart(int lnum) {
		super(lnum);
	}
	
	static VarDeclPart parse(Scanner s) {
		enterParser("var decl part");
		s.skip(varToken);
		
		VarDeclPart vdp = new VarDeclPart(s.curLineNum());
		while(true) {
			vdp.decls.add(VarDecl.parse(s));
			if (s.curToken.kind != nameToken)
				break;
		}

		leaveParser("var decl part");
		return vdp;
	}

	@Override
	public String identify() {
		return "<var decl part> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("var ");
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
		for(VarDecl vd : decls){
			vd.check(curScope, lib);
		}
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
	}
}
