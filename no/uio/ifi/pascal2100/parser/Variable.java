package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;

class Variable extends Factor {
	Expression expr;
	String name;
	
	//Del 4
	int blockLevel;
	int blockIndex;
	PascalDecl ref;
	boolean isConst = false;
	boolean isFunc = false;  //For AssignStatm

	public Variable(int lnum) {
		super(lnum);
	}

	static Variable parse(Scanner s) {
		enterParser("variable");
		s.test(nameToken);

		Variable var = new Variable(s.curLineNum());
		var.name = s.curToken.id;

		s.readNextToken();

		if (s.curToken.kind == leftBracketToken) {
			s.readNextToken();
			var.expr = Expression.parse(s);
			s.skip(rightBracketToken);
		}

		leaveParser("variable");
		return var;
	}

	@Override
	public String identify() {
		return "<variable> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint(name);
		if(expr != null){
			Main.log.prettyPrint("[");
			expr.prettyPrint();
			Main.log.prettyPrint("]");
		}
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		PascalDecl d = curScope.findDecl(name, this);	
		if(expr != null){
			expr.check(curScope, lib);
		}
		
		ref = d;
		
		//Check if this is actually a function or a constant
		if(FuncDecl.class.isAssignableFrom(d.getClass())){
			isFunc = true;
		} else if(ConstDecl.class.isAssignableFrom(d.getClass()) ||
				EnumLiteral.class.isAssignableFrom(d.getClass())) {
			isConst = true;
		}
		
		//Del 4	
		blockLevel = d.declLevel;
		blockIndex = d.declOffset;
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		
		//If this is a constant, then there is no need to check the stack.
		if(isConst){
			ref.genCode(f);
			
		} else {
			
			if(expr != null){
				expr.genCode(f);
			}
	
			f.genInstr("", "movl", "-" + (4 * blockLevel) + "(%ebp),%edx", "");
			f.genInstr("", "movl", blockIndex + "(%edx),%eax", " var " + name);			
		}
	}
}


















