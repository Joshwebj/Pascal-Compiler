package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;

class RelOpr extends Operator {
	Token oprT;
	
	public RelOpr(int lnum) {
		super(lnum);
	}
	
	static RelOpr parse(Scanner s) {
		enterParser("rel opr");
		
		RelOpr ro = new RelOpr(s.curLineNum());
		ro.oprT = s.curToken;

		s.readNextToken();
		leaveParser("rel opr");
		return ro;	
	}
	@Override
	public String identify() {
		return "<rel opr> on line " + lineNum;
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
		f.genInstr("", "popl", "%ecx", "");
		f.genInstr("", "cmpl", "%eax,%ecx", "");
		f.genInstr("", "movl", "$0,%eax", "");
		
		switch(oprT.kind){
		case equalToken:
			f.genInstr("", "sete", "%al", "Test ==");
			break;
		case notEqualToken:
			f.genInstr("", "setne", "%al", "Test <>");
			break;
		case lessToken:
			f.genInstr("", "setl", "%al", "Test <");
			break;
		case lessEqualToken:
			f.genInstr("", "setle", "%al", "Test <=");
			break;
		case greaterToken:
			f.genInstr("", "setg", "%al", "Test >");
			break;
		case greaterEqualToken:
			f.genInstr("", "setge", "%al", "Test >=");
			break;
		default: break;
		}
	}
}







