package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class ProcDecl extends PascalDecl {
	ParamDeclList pdl;
	Block pdBlock;
	
	public ProcDecl(String id, int lNum) {
		super(id, lNum);
	}
	
	static ProcDecl parse(Scanner s){
		enterParser("proc decl");
		s.skip(procedureToken);
		
		ProcDecl pd = new ProcDecl(s.curToken.id, s.curLineNum());
		
		s.skip(nameToken);
		
		if(s.curToken.kind == leftParToken) {
			pd.pdl = ParamDeclList.parse(s);
		}
		
		s.skip(semicolonToken);
		
		pd.pdBlock = Block.parse(s);
		
		s.skip(semicolonToken);
		leaveParser("proc decl");
		return pd;
	}

	@Override
	public String identify() {
		if(lineNum == -1) return "<proc decl> in the library";
		return "<proc decl> " + name + " on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("procedure " + name);
		if(pdl != null){
			pdl.prettyPrint();
		}
		Main.log.prettyPrintLn(";");
		pdBlock.prettyPrint();
		Main.log.prettyPrintLn(";");
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {	
		if(pdl != null){
			pdl.check(curScope, lib);
		}
		pdBlock.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		progProcFuncName = "proc$" + f.getLabel(name).toLowerCase();
		int varCount = 0;
		
		//Count variables in block
		if(pdBlock.vdp != null){
			varCount = pdBlock.vdp.decls.size();
		}

		//ProcDecl / funcDecl
		for(ProcDecl pd : pdBlock.procdecls){
			pd.genCode(f);
		}	
		
		f.genInstr(progProcFuncName, "", "", "");
		f.genInstr("", "enter", "$" + (32 + (4 * varCount)) + ",$" + declLevel , "Start of " + name.toLowerCase());
		
		pdBlock.genCode(f);
		
		f.genInstr("", "leave", "", "End of " + name.toLowerCase());
		f.genInstr("", "ret", "", "");
	}
}







