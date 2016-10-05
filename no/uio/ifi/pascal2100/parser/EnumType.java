package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.util.ArrayList;

class EnumType extends Type {
	ArrayList<EnumLiteral> enums = new ArrayList<EnumLiteral>();
	
	public EnumType(int lnum) {
		super(lnum);
	}

	static EnumType parse(Scanner s){
		enterParser("enum type");
		s.skip(leftParToken);
		
		EnumType en = new EnumType(s.curLineNum());
		
		while(true){
			en.enums.add(EnumLiteral.parse(s));
			s.skip(nameToken);
			
			if(s.curToken.kind == commaToken)
				s.readNextToken();
			else
				break;		
		}

		s.skip(rightParToken);
		leaveParser("enum type");
		return en;	
	}
	
	@Override
	public String identify() {
		return "<enum type> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		Main.log.prettyPrint("(");
		for (int i = 0; i < enums.size(); i++) {
			enums.get(i).prettyPrint();
			if (i < enums.size() - 1)
				Main.log.prettyPrint(", ");
		}
		Main.log.prettyPrint(")");
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		for (int i = 0; i < enums.size(); i++) {
			enums.get(i).check(curScope, lib);
			enums.get(i).num = i;  //Enumerate
		}
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
	}
}