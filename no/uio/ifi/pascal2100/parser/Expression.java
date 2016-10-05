package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;
import no.uio.ifi.pascal2100.main.Main;
import no.uio.ifi.pascal2100.scanner.Scanner;

class Expression extends PascalSyntax {
	SimpleExpr se, seo; //simple expr and simple expr optional
	Operator opr;
	
	Constant typeRef;
	
	public Expression(int lnum) {
		super(lnum);
	}

	static Expression parse(Scanner s){
		enterParser("expression");
		
		Expression ex = new Expression(s.curLineNum());
		
		ex.se = SimpleExpr.parse(s);
		
		switch(s.curToken.kind){
		case equalToken:
		case notEqualToken:
		case lessToken:
		case lessEqualToken:
		case greaterToken:
		case greaterEqualToken:
			ex.opr = RelOpr.parse(s); 
			ex.seo = SimpleExpr.parse(s);
			break;
		default:
			break;
		}
		
		leaveParser("expression");
		return ex;
	}
	
	@Override
	public String identify() {
		return "<expression> on line " + lineNum;
	}

	@Override
	void prettyPrint() {
		se.prettyPrint();
		if(opr != null){
			Main.log.prettyPrint(" ");
			opr.prettyPrint();
			Main.log.prettyPrint(" ");
			seo.prettyPrint();
		}
	}

	//Del 3
	@Override
	void check(Block curScope, Library lib) {
		se.check(curScope, lib);

		if(opr != null){
			opr.check(curScope, lib);	
			seo.check(curScope, lib);
		}
	}
	
	// Del 4
	@Override
	void genCode(CodeFile f) {
		se.genCode(f);

		if(opr != null){
			f.genInstr("", "pushl", "%eax", "");
			seo.genCode(f);
			opr.genCode(f);	
		}
	}
	
	//Evaluates the type of this expression
	public Constant eval() {
		
		//Only checks the first factor in the first 
		//simple expression in an Expression
		PascalSyntax p = se.terms.get(0).fctrs.get(0);
			
		//Factors
		if(FuncCall.class.isAssignableFrom(p.getClass())) {
			FuncCall f = (FuncCall)p;		
			p = f.ref;
		}
		
		if(StringLiteral.class.isAssignableFrom(p.getClass())){
			StringLiteral s = (StringLiteral)p;
			return s;
		}
		
		if(CharLiteral.class.isAssignableFrom(p.getClass())){
			CharLiteral c = (CharLiteral)p;
			return c;
		}
		
		if(NumericLiteral.class.isAssignableFrom(p.getClass())){
			NumericLiteral n = (NumericLiteral)p;
			return n;
		}
		
		if(Negation.class.isAssignableFrom(p.getClass())){
			return new NumericLiteral(0);
		}
		
		if(InnerExpr.class.isAssignableFrom(p.getClass())){
			InnerExpr e = (InnerExpr)p;
			return e.expr.eval();
		}
		
		
		//Variables
		if(Variable.class.isAssignableFrom(p.getClass())) {
			Variable v = (Variable)p;		
			
			p = v.ref;
			
			if (v.expr != null) {
				return v.expr.eval();
			}
					
			//Parameter Declarations
			if(ParamDecl.class.isAssignableFrom(p.getClass())){
				ParamDecl d = (ParamDecl)p;
				
				switch(d.tyName.tName) {
				case "integer": return new NumericLiteral(0);
				case "char": return new CharLiteral(0);
				default: break; 
				}
				
				TypeDecl t = (TypeDecl)d.tyName.ref;
				switch(getType(t.type)) {
				case "integer": return new NumericLiteral(0);
				case "char": return new CharLiteral(0);
				default: return null; 
				}
			}
			
			//Function Declarations
			if(FuncDecl.class.isAssignableFrom(p.getClass())){
				FuncDecl d = (FuncDecl)p;
	
				switch(d.tyName.tName) {
				case "integer": return new NumericLiteral(0);
				case "char": return new CharLiteral(0);
				default: break; 
				}
				
				TypeDecl t = (TypeDecl)d.tyName.ref;
				switch(getType(t.type)) {
				case "integer": return new NumericLiteral(0);
				case "char": return new CharLiteral(0);
				default: return null; 
				}
			}

			//Variable Declarations
			if(VarDecl.class.isAssignableFrom(p.getClass())){
				VarDecl d = (VarDecl)p;

				switch(getType(d.type)) {
				case "integer": return new NumericLiteral(0);
				case "char": return new CharLiteral(0);
				default: return null; 
				}
			}
			
			//Constant Declarations
			if(ConstDecl.class.isAssignableFrom(p.getClass())){
				ConstDecl d = (ConstDecl)p;
				
				if(StringLiteral.class.isAssignableFrom(d.con.getClass())){
					StringLiteral s = (StringLiteral)d.con;
					return s;
				}
				
				if(NumericLiteral.class.isAssignableFrom(d.con.getClass())){
					NumericLiteral n = (NumericLiteral)d.con;
					return n;
				}
				
				if(CharLiteral.class.isAssignableFrom(d.con.getClass())){
					CharLiteral c = (CharLiteral)d.con;
					return c;
				}
			}	
		}
		
		System.out.println("ERROR: Unimplemented in eval: " + p.identify());
		System.exit(0);
		return null;
	}

	//Helper function for eval
	public String getType(Type t) {

		if (TypeName.class.isAssignableFrom(t.getClass())) {
			TypeName n = (TypeName) t;

			switch (n.tName.toLowerCase()) {
			case "char":
				return "char";
			case "boolean":
			case "integer":
				return "integer";
			default:
				TypeDecl d = (TypeDecl) n.ref;
				return getType(d.type);
			}
		}

		if (RangeType.class.isAssignableFrom(t.getClass())) {
			return "integer";
		}

		if (EnumType.class.isAssignableFrom(t.getClass())) {
			return "integer";
		}

		// ARRAY TYPE NOT IMPLEMENTED
		return "";
	}
}











