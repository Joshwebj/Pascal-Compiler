package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class FuncDecl extends ProcDecl {
	TypeName tyName;
	
	public FuncDecl(String id, int lNum) {
		super(id, lNum);
	}
	
	static FuncDecl parse(Scanner s){
		enterParser("func decl");
		s.skip(functionToken);
		
		FuncDecl fd = new FuncDecl(s.curToken.id, s.curLineNum());
		
		s.skip(nameToken);
		
		if(s.curToken.kind == leftParToken) {
			fd.pdl = ParamDeclList.parse(s);
		}
		
		s.skip(colonToken);
		
		fd.tyName = TypeName.parse(s);
		
		s.skip(semicolonToken);
		
		fd.pdBlock = Block.parse(s);
		
		s.skip(semicolonToken);
		leaveParser("func decl");
		return fd;
	}

	@Override
	public String identify() {
		return "<func decl> " + name + " on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("function " + name + " ");
		if(pdl != null) pdl.prettyPrint();
		Main.log.prettyPrint(": ");
		tyName.prettyPrint();
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
		tyName.check(curScope, lib);
		pdBlock.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		progProcFuncName = "func$" + f.getLabel(name).toLowerCase();	
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
		
		f.genInstr("", "movl", "-32(%ebp),%eax", "Get return value");
		f.genInstr("", "leave", "", "End of " + name.toLowerCase());
		f.genInstr("", "ret", "", "");
	}
}