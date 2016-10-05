package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

class IfStatm extends Statement {
	Expression expr;
	Statement ifstatm, elsestatm;
	
	public IfStatm(int lNum) {
		super(lNum);
	}

	static IfStatm parse(Scanner s){
		enterParser("if statm");
		s.skip(ifToken);
		
		IfStatm is = new IfStatm(s.curLineNum());
		is.expr = Expression.parse(s);
		
		s.skip(thenToken);
		
		is.ifstatm = Statement.parse(s);
		
		if(s.curToken.kind == elseToken){
			s.readNextToken();
			is.elsestatm = Statement.parse(s);
		}
		
		leaveParser("if statm");
		return is;
	}
	
	@Override
	public String identify() {
		return "<if statm> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("if ");
		expr.prettyPrint();
		Main.log.prettyPrintLn(" then ");
		Main.log.prettyIndent();
		ifstatm.prettyPrint();
		Main.log.prettyOutdent();
		if(elsestatm != null){
			Main.log.prettyPrintLn("");
			Main.log.prettyPrintLn("else ");
			Main.log.prettyIndent();
			elsestatm.prettyPrint();
			Main.log.prettyOutdent();
		}
	}

	@Override
	void check(Block curScope, Library lib) {
		expr.check(curScope, lib);
		ifstatm.check(curScope, lib);
		if(elsestatm != null){
			elsestatm.check(curScope, lib);
		}
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		if (elsestatm == null) {
			String label1 = f.getLocalLabel();
			
			f.genInstr("", "", "", "Start if-statement");
			expr.genCode(f);
			f.genInstr("", "cmpl", "$0,%eax", "");
			f.genInstr("", "je", label1, "");
			ifstatm.genCode(f);
			f.genInstr(label1, "", "", "End if-statement");
			
		} else {
			String label1 = f.getLocalLabel(), 
					label2 = f.getLocalLabel();
			
			f.genInstr("", "", "", "Start if-statement");
			expr.genCode(f);
			f.genInstr("", "cmpl", "$0,%eax", "");
			f.genInstr("", "je", label1, "");
			ifstatm.genCode(f);
			f.genInstr("", "jmp", label2, "");
			f.genInstr(label1, "", "", "");
			elsestatm.genCode(f);
			f.genInstr(label2, "", "", "End if-statement");
		}
	}
}

















