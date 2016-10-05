package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;

class TermOpr extends Operator {
	Token oprT;
	
	public TermOpr(int lnum) {
		super(lnum);
	}
	
	static TermOpr parse(Scanner s) {
		enterParser("term opr");
		
		TermOpr to = new TermOpr(s.curLineNum());
		to.oprT = s.curToken;

		s.readNextToken();
		leaveParser("term opr");
		return to;	
	}
	@Override
	public String identify() {
		return "<term opr> on line " + lineNum;
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
		case addToken:
			f.genInstr("", "addl", "%ecx,%eax", " +"); 
			break;
		case subtractToken:
			f.genInstr("", "subl", "%ecx,%eax", " -"); 
			break;
		case orToken:
			f.genInstr("", "orl", "%ecx,%eax", " or"); 
			break;
		default: break;	
		}
	}
}





















