package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.util.ArrayList;

class SimpleExpr extends PascalSyntax {
	ArrayList<Term> terms = new ArrayList<Term>();
	ArrayList<TermOpr> termOprs = new ArrayList<TermOpr>();
	PrefixOpr pre;
	
	public SimpleExpr(int lnum) {
		super(lnum);
	}

	static SimpleExpr parse(Scanner s){
		enterParser("simple expr");
		
		SimpleExpr se = new SimpleExpr(s.curLineNum());
		
		switch(s.curToken.kind){
		case addToken:
		case subtractToken:
			se.pre = PrefixOpr.parse(s);
		default: break;
		}
		
		while(true){
			se.terms.add(Term.parse(s));
			if(s.curToken.kind == addToken
					|| s.curToken.kind == subtractToken
					|| s.curToken.kind == orToken) {
				se.termOprs.add(TermOpr.parse(s));
			}else{
				break;
			}
		}
		
		leaveParser("simple expr");
		return se;
	}
	
	@Override
	public String identify() {
		return "<simple expr> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		if(pre != null){
			pre.prettyPrint();
		}
		for (int i = 0; i < terms.size(); i++) {
			terms.get(i).prettyPrint();
			if (i < terms.size() - 1) {
				Main.log.prettyPrint(" ");
				termOprs.get(i).prettyPrint();
				Main.log.prettyPrint(" ");
			}
		}
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		if(pre != null){
			pre.check(curScope, lib);
		}
		
		for(Term t: terms){
			t.check(curScope, lib);
		}	
		for(TermOpr to : termOprs){
			to.check(curScope, lib);
		}
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		for(int i = 0; i < terms.size(); i++) {
			terms.get(i).genCode(f);
			
			//Generate term operator only after the second term and upwards.
			if(i >= 1) {
				termOprs.get(i - 1).genCode(f);
			}	
			
			if(i < terms.size() - 1) {
				f.genInstr("", "pushl", "%eax", "");
			}
		}

		if(pre != null){
			pre.genCode(f);
		}
	}
}
