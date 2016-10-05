package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.*;
import no.uio.ifi.pascal2100.scanner.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.util.ArrayList;

class Term extends PascalSyntax {
	ArrayList<Factor> fctrs = new ArrayList<Factor>();
	ArrayList<FactorOpr> fctrOprs = new ArrayList<FactorOpr>();
	
	public Term(int lnum) {
		super(lnum);
	}
	
	static Term parse(Scanner s){
		enterParser("term");
		
		Term t = new Term(s.curLineNum());
		
		while(true){
			t.fctrs.add(Factor.parse(s));
			
			if(s.curToken.kind == multiplyToken
					|| s.curToken.kind == divToken
					|| s.curToken.kind == modToken
					|| s.curToken.kind == andToken
					)
				t.fctrOprs.add(FactorOpr.parse(s));
			else
				break;		
		}

		leaveParser("term");
		return t;	
	}
	
	@Override
	public String identify() {
		return "<term> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		
		for (int i = 0; i < fctrs.size(); i++) {
			fctrs.get(i).prettyPrint();
			if (i < fctrs.size() - 1) {
				Main.log.prettyPrint(" ");	
				fctrOprs.get(i).prettyPrint();
				Main.log.prettyPrint(" ");	
			}
		}
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		for(Factor f : fctrs){
			f.check(curScope, lib);
		}	
		for(FactorOpr fo : fctrOprs){
			fo.check(curScope, lib);
		}
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		for (int i = 0; i < fctrs.size(); i++) {
			fctrs.get(i).genCode(f);

			//Generate factor operator only after the second term and upwards.
			if(i >= 1) {
				fctrOprs.get(i - 1).genCode(f);
			}	
			
			if (i < fctrs.size() - 1) {
				f.genInstr("", "pushl", "%eax", "");
			}
		}
	}
}
