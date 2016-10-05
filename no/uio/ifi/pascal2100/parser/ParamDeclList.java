package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.util.ArrayList;

class ParamDeclList extends PascalSyntax {
	ArrayList<ParamDecl> pardecls = new ArrayList<ParamDecl>();
	
	public ParamDeclList(int lnum) {
		super(lnum);
	}

	static ParamDeclList parse(Scanner s){
		enterParser("param decl list");
		s.skip(leftParToken);
		
		ParamDeclList pdl = new ParamDeclList(s.curLineNum());
		
		while(s.curToken.kind != rightParToken){
			pdl.pardecls.add(ParamDecl.parse(s));
			
			if(s.curToken.kind == semicolonToken){
				s.readNextToken();	
			}else{
				break;	
			}
		}

		s.readNextToken();
		leaveParser("param decl list");
		return pdl;	
	}
	
	@Override
	public String identify() {
		return "<param decl list> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("(");
		for (int i = 0; i < pardecls.size(); i++) {
			pardecls.get(i).prettyPrint();
			if (i < pardecls.size() - 1){
				Main.log.prettyPrint(", ");
			}
		}
		Main.log.prettyPrint(")");
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		for(ParamDecl pd : pardecls){
			pd.check(curScope, lib);
		}
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
	}
}