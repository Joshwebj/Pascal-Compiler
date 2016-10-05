package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.scanner.Scanner;

abstract class Operator extends PascalSyntax {

	public Operator(int lnum) {
		super(lnum);
	}

	static Operator parse(Scanner s) {
		enterParser("factor");
		Operator opr = null;
		switch (s.curToken.kind) {
		case multiplyToken:
		case divToken:
		case modToken:
		case andToken:
			opr = FactorOpr.parse(s); break;
			
		case addToken:
		case subtractToken:
			
			//Check if next token is a term 
			switch(s.nextToken.kind) {
			case nameToken:
			case leftParToken:
			case notToken:
			case intValToken:
			case stringValToken:
				opr = PrefixOpr.parse(s); break;
			default: 
				opr = TermOpr.parse(s); break;
			}break;
		
		case orToken:
			opr = TermOpr.parse(s); break;
			
		case equalToken:
		case notEqualToken:
		case lessToken:
		case lessEqualToken:
		case greaterToken:
		case greaterEqualToken:
			opr = RelOpr.parse(s); break;
			
		default: s.testError("operator"); break;
		}
		leaveParser("factor");
		return opr;
	}
}
