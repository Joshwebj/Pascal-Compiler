package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

abstract class Type extends Factor{
  
  public Type(int lnum) {
		super(lnum);
	}

static Type parse(Scanner s) {
	enterParser("type");
	
	Type tp = null;

	switch(s.curToken.kind){
	  case nameToken:
		  if(s.nextToken.kind == rangeToken){
			  tp = RangeType.parse(s); break;
		  }else{
			  tp = TypeName.parse(s); 
		  }break;
	  case stringValToken:
	  case intValToken:
		  tp = RangeType.parse(s); break;
	  case leftParToken:
		  tp = EnumType.parse(s); break;
	  case arrayToken:
		  tp = ArrayType.parse(s); break;
	  default: 
		  s.testError("type"); break;
	}
	
	leaveParser("type");
	return tp;
  }
}