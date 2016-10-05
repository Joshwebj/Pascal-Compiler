package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;

class FactorOpr extends Operator {
	Token oprT;
	
	public FactorOpr(int lnum) {
		super(lnum);
	}
	
	static FactorOpr parse(Scanner s) {
		enterParser("factor opr");
		
		FactorOpr fo = new FactorOpr(s.curLineNum());
		fo.oprT = s.curToken;

		s.readNextToken();
		leaveParser("factor opr");
		return fo;	
	}
	@Override
	public String identify() {
		return "<factor opr> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint(oprT.kind.toString()); 
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {		
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		f.genInstr("", "movl", "%eax,%ecx", ""); 
		f.genInstr("", "popl", "%eax", ""); 
		
		switch(oprT.kind){
		case multiplyToken:
			f.genInstr("", "imull", "%ecx,%eax", " *"); 
			break;			
		case andToken:
			f.genInstr("", "andl", "%ecx,%eax", " and"); 
			break;
		case modToken:
			f.genInstr("", "cdq", "", ""); 
			f.genInstr("", "idivl", "%ecx", ""); 
			f.genInstr("", "movl", "%edx,%eax", " mod"); 
			break;
		case divToken:
			f.genInstr("", "cdq", "", ""); 
			f.genInstr("", "idivl", "%ecx", " /"); 
			break;
		default: break;	
		}
	}
}
