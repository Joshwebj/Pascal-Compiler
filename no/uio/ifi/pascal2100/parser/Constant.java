package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.scanner.Scanner;

abstract class Constant extends Factor{
  
  public Constant(int lnum) {
		super(lnum);
	}

  static Constant parse(Scanner s) {
	  enterParser("constant");
	
	  Constant ct = null;

	  switch(s.curToken.kind){
	  case nameToken:	
		  ct = NamedConst.parse(s); break;
	  case intValToken:	
		  ct = NumericLiteral.parse(s); break;
	  case stringValToken:
		  if(s.curToken.strVal.length() == 1)
			  ct = CharLiteral.parse(s); 
		  else
			  ct = StringLiteral.parse(s); 
		  break;  
	  default: break;
	}
	
	leaveParser("constant");
	return ct;
  }
}
