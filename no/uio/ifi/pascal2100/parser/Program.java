package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

/* <program> ::= ’program’ <name> ’;’ <block> ’.’ */

public class Program extends PascalDecl {
	Block progBlock;

	Program(String id, int lNum) {
		super(id, lNum);
	}

	static public Program parse(Scanner s) {
		enterParser("program");

		s.skip(programToken);
		s.test(nameToken);

		Program pr = new Program(s.curToken.id, s.curLineNum());

		s.readNextToken();
		s.skip(semicolonToken);

		pr.progBlock = Block.parse(s); // p.progblock.context = p;

		s.skip(dotToken);
		leaveParser("program");
		return pr;
	}

	@Override
	public String identify() {
		return "<program> " + name + " on line " + lineNum;
	}

	@Override
	public void prettyPrint() {
		Main.log.prettyPrintLn("Program " + name + ";");
		Main.log.prettyPrintLn("");
		progBlock.prettyPrint();
		Main.log.prettyPrintLn(".");
	}

	//Del 3
	@Override
	public void check(Block curScope, Library lib) {
		progBlock.check(curScope, lib);
	}
	
	// Del 4
	@Override
	public void genCode(CodeFile f) {
		String progLabel = "prog$" + f.getLabel(name).toLowerCase();
		int varCount = 0;
		
		//Count variables
		if(progBlock.vdp != null){
			varCount = progBlock.vdp.decls.size();
		}

		f.genInstr("", ".extern write_char", "", "");
		f.genInstr("", ".extern write_int", "", "");
		f.genInstr("", ".extern write_string", "", "");
		f.genInstr("", ".globl _main", "", "");
		f.genInstr("", ".globl main", "", "");

		f.genInstr("_main", "", "", "");
		f.genInstr("main", "call", progLabel, "Start program");
		f.genInstr("", "movl", "$0,%eax", "Set status 0 and");
		f.genInstr("", "ret", "", "terminate the program");
		
		//ProcDecl / funcDecl
		for(ProcDecl pd : progBlock.procdecls){
			pd.genCode(f);
		}	
		
		f.genInstr(progLabel, "", "", "");
		f.genInstr("", "enter", "$" + (32 + (4 * varCount)) + ",$1", "Start of " + name.toLowerCase());
		
		progBlock.genCode(f);
		
		f.genInstr("", "leave", "", "End of " + name.toLowerCase());
		f.genInstr("", "ret", "", "");
	}
}























