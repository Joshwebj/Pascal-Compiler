package no.uio.ifi.pascal2100.parser;

import no.uio.ifi.pascal2100.main.CodeFile;

//Del 3
public class Library extends Block {

	public Library(int lnum) {
		super(lnum);
		
		//Create and add all pre-declared objects
		TypeDecl tdb = new TypeDecl("boolean", lnum);
		TypeDecl tdc = new TypeDecl("char", lnum);
		TypeDecl tdi = new TypeDecl("integer", lnum);
		ProcDecl pd = new ProcDecl("write", lnum);
		EnumLiteral elt = new EnumLiteral("true", lnum);
		EnumLiteral elf = new EnumLiteral("false", lnum);
		ConstDecl cd = new ConstDecl("eol", lnum);
		
		CharLiteral cl = new CharLiteral(lnum);
		cl.val = "\n";
		cd.con = cl;
	
		addDecl(tdb.name, tdb);
		addDecl(tdc.name, tdc);
		addDecl(tdi.name, tdi);
		addDecl(cd.name, cd);
		addDecl(pd.name, pd);
		addDecl(elt.name, elt);
		addDecl(elf.name, elf);
	}
	
	@Override
	public String identify() {
		return "the library";
	}
	
	// Del 4
	@Override
	public void genCode(CodeFile f) {
	}
}
