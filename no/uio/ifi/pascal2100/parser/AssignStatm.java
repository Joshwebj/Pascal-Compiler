package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class AssignStatm extends Statement {
	Variable var;
	Expression expr;

	public AssignStatm(int lNum) {
		super(lNum);
	}

	static AssignStatm parse(Scanner s){
		enterParser("assign statm");
		s.test(nameToken);
		
		AssignStatm as = new AssignStatm(s.curLineNum());
		as.var = Variable.parse(s);
		
		s.skip(assignToken);
		
		as.expr = Expression.parse(s);
		leaveParser("assign statm");
		return as;
	}
	
	@Override
	public String identify() {
		return "<assign statm> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		var.prettyPrint();
		Main.log.prettyPrint(" := ");
		expr.prettyPrint();
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		var.check(curScope, lib);
		expr.check(curScope, lib);
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		expr.genCode(f);
		
		//Get variable type. Saved in Variable class.
		if(var.isFunc) {
			f.genInstr("", "movl", "%eax,-32(%ebp)", var.name + " :=");
		} else {
			f.genInstr("", "movl", "-" + (4 * var.blockLevel) + "(%ebp),%edx", "");
			f.genInstr("", "movl", "%eax," + var.blockIndex + "(%edx)", var.name + " :=");
		}

		//Arrays not implemented
	
	}
}





