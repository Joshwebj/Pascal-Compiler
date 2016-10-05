package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.util.ArrayList;
import java.util.HashMap;

class Block extends PascalSyntax {
	ArrayList<ProcDecl> procdecls = new ArrayList<ProcDecl>();
	ConstDeclPart cdp;
	TypeDeclPart tdp;
	VarDeclPart vdp;
	StatmList sl;
	
	//Del 3
	Block outerScope;
	HashMap<String,PascalDecl> decls = new HashMap<String,PascalDecl>();
	
	//Del 4
	int varCount; //Number of variables
	int paramCount; //Number of parameter variables
	int level = 0;
	
	public Block(int lnum) {
		super(lnum);
		varCount = 0;
	}

	static Block parse(Scanner s) {
		enterParser("block");

		Block bl = new Block(s.curLineNum());

		if(s.curToken.kind == constToken){
			bl.cdp = ConstDeclPart.parse(s);
		}
		
		if(s.curToken.kind == typeToken){
			bl.tdp = TypeDeclPart.parse(s);
		}
		
		if(s.curToken.kind == varToken){
			bl.vdp = VarDeclPart.parse(s);
		}
	
		fpLoop: while(true){
			switch(s.curToken.kind){
			case functionToken:
				bl.procdecls.add(FuncDecl.parse(s)); break;
			case procedureToken:
				bl.procdecls.add(ProcDecl.parse(s)); break;
			default: break fpLoop;
			}
		}

		s.skip(beginToken);
		
		bl.sl = StatmList.parse(s);
		
		s.skip(endToken);
		
		leaveParser("block");
		return bl;

	}
	
	@Override
	public String identify() {
		return "<block> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		if(cdp != null) cdp.prettyPrint();
		if(tdp != null) tdp.prettyPrint();
		if(vdp != null) vdp.prettyPrint();
		
		for (int i = 0; i < procdecls.size(); i++) {
			procdecls.get(i).prettyPrint();
		}
		Main.log.prettyPrintLn("\nbegin");
		Main.log.prettyIndent();
		sl.prettyPrint();
		Main.log.prettyOutdent();
		Main.log.prettyPrint("\nend");

	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
	
		//Initialize outerScope
		outerScope = curScope;
		level = outerScope.level + 1;
		
		//ConstDeclPart
		if (cdp != null) {
			for (ConstDecl cd : cdp.decls) {
				addDecl(cd.name, cd);
			}
			cdp.check(this, lib);
		}
		
		//TypeDeclPart
		if (tdp != null) {
			for (TypeDecl td : tdp.decls) {
				addDecl(td.name, td);
			}
			tdp.check(this, lib);
		}
		
		//VarDeclType
		if (vdp != null) {
			for (VarDecl vd : vdp.decls) {
				addDecl(vd.name, vd);
				varCount++;
			}
			vdp.check(this, lib);
		}
		
		//ProcDecl / funcDecl
		for(ProcDecl pd : procdecls){
			addDecl(pd.name, pd);
			
			// Param decl list
			if (pd.pdl != null) {
				for (ParamDecl prd : pd.pdl.pardecls) {
					pd.pdBlock.addDecl(prd.name, prd);
					pd.pdBlock.paramCount++;
				}
			}
			pd.check(this, lib);
		}	
		sl.check(this, lib);
	}
	
	void addDecl(String id, PascalDecl d) {	
		if (decls.containsKey(id.toLowerCase())) {
			d.error(id + " declared twice in same block!");
		}

		//Del 4
		//Set parameter or variable offsets
		if (ParamDecl.class.isAssignableFrom(d.getClass())) {
			d.declOffset = 4 + ((paramCount + 1) * 4);	
		} else {
			d.declOffset = -(32 + ((varCount + 1) * 4));	
		}
		decls.put(id.toLowerCase(), d);
	}
	
	PascalDecl findDecl(String id, PascalSyntax where) {
		PascalDecl d = decls.get(id.toLowerCase());
		if (d != null) {
			Main.log.noteBinding(id, where, d);
			d.declLevel = level;
			
			if(ProcDecl.class.isAssignableFrom(d.getClass()) 
					|| FuncDecl.class.isAssignableFrom(d.getClass())) {
				d.declLevel++;
			}
			
			return d;
		}

		
		if (outerScope != null) {
			return outerScope.findDecl(id, where);
		}
		where.error("Name " + id + " is unknown!");
		return null; 
	}
	
	// Del 4
	@Override
	public void genCode(CodeFile f) {

		//Statement list
		sl.genCode(f);
	}
}




















