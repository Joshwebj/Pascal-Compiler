package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.scanner.*;

abstract class Factor extends PascalSyntax {

	public Factor(int lnum) {
		super(lnum);
	}

	static Factor parse(Scanner s) {
		enterParser("factor");
		Factor fa = null;
		switch (s.curToken.kind) {
		case nameToken:
			switch (s.nextToken.kind) {
			case leftParToken:
				fa = FuncCall.parse(s); break;
			case leftBracketToken:
				fa = Variable.parse(s); break;
			default: 
				fa = Variable.parse(s); break;
			}break;
			
		case intValToken:
		case stringValToken:
			fa = Constant.parse(s); break;
		case leftParToken:
			fa = InnerExpr.parse(s); break;
		case notToken:
			fa = Negation.parse(s); break;
		default:
			s.testError("value"); break;
		}
		leaveParser("factor");
		return fa;
	}
}
