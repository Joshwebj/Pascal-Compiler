package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.util.ArrayList;

class TypeDeclPart extends PascalSyntax {
	ArrayList<TypeDecl> decls = new ArrayList<TypeDecl>();
	
	public TypeDeclPart(int lnum) {
		super(lnum);
	}
	
	static TypeDeclPart parse(Scanner s) {
		enterParser("type decl part");
		
		s.skip(typeToken);
		
		TypeDeclPart tdp = new TypeDeclPart(s.curLineNum());

		while(s.curToken.kind == nameToken) {
			tdp.decls.add(TypeDecl.parse(s));
		}

		leaveParser("type decl part");
		return tdp;
	}

	@Override
	public String identify() {
		return "<type decl part> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("type ");
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
		for(TypeDecl td : decls){
			td.check(curScope, lib);
		}
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
	}
}
