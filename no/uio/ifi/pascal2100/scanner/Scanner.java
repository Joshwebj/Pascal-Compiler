package no.uio.ifi.pascal2100.scanner;

import no.uio.ifi.pascal2100.main.*;
import static no.uio.ifi.pascal2100.scanner.TokenKind.*;

import java.io.*;

public class Scanner {
  public Token curToken = null, nextToken = null;

  private LineNumberReader sourceFile = null;
  private String sourceFileName, sourceLine = "";
  private int sourcePos = 0;

  public Scanner(String fileName) {
	sourceFileName = fileName;
	try {
	  sourceFile = new LineNumberReader(new FileReader(fileName));
	} catch (FileNotFoundException e) {
	  Main.error("Cannot read " + fileName + "!");
	}

	readNextToken();
	readNextToken();
  }

  public String identify() {
	return "Scanner reading " + sourceFileName;
  }

  public int curLineNum() {
	return curToken.lineNum;
  }

  private void error(String message) {
	Main.error("Scanner error on line " + curLineNum() + ": " + message);
  }

  
  /**
   * Reads the next token in a .pas file and updates curToken and
   * nextToken global variables. All tokens are logged if the option
   * is chosen.
   * 
   * First checks if the current read line is empty or the end of 
   * line is reached. The next line will be read if so, unless the
   * file has been closed. In this case an e-o-f is created.
   * 
   * All whitespaces are ignored.
   * 
   * Non-letter and digit characters are read as single tokens.
   * Exceptions are /*, .., :=, >=, <= and <> 
   * This assumes that letters/digits can not be in the same
   * token as symbols.
   * 
   * All comments are disregarded (/* and { tokens)
   * 
   * After a whole token has been read, its kind is determined and 
   * a Token object is created.
   * - The TokenKind of Tokens containing only symbols are determined
   * using a custom function in the TokenKind enum: getKind().
   * - Tokens starting with ' are always text tokens.
   * - Tokens only containing digits are assumed to be number tokens.
   * - The rest are determined when using the first Token constructor:
   * Token(String s, int lNum)
   */
  
  public void readNextToken() {
	// Del 1 her
	String tokenStr = "";
	boolean isInt = true;
	char c, b;

	// Ignores empty lines and checks if end of line is reached
	while(sourceLine.equals(" ") || sourcePos >= sourceLine.length() - 1){
	  readNextLine();
	  sourcePos = 0;

	  // If the source file is closed, create and e-o-f token
	  if(sourceFile == null) {
		curToken = nextToken;
		nextToken = new Token(TokenKind.eofToken, getFileLineNum());
		Main.log.noteToken(nextToken);
		return;
	  }
	}

	// Ignore all blank spaces
	while(Character.isWhitespace(sourceLine.charAt(sourcePos))){
		sourcePos++;
		if(sourcePos >= sourceLine.length()){
			readNextLine();
			sourcePos = 0;
		}
	}

	// Reads the source line until a whitespace or end of line is reached
	while(!Character.isWhitespace(sourceLine.charAt(sourcePos)) 
		&& sourcePos < sourceLine.length() - 1){
	  c = sourceLine.charAt(sourcePos); 
	  
	  // All non-letter/non-digits are isolated
	  if(tokenStr.length() != 0) {
		b = tokenStr.charAt(tokenStr.length() - 1);
		if(!isLetterOrDigit(c) && isLetterOrDigit(b)) {
		  break;	  
		}else if(!isLetterOrDigit(b)) {
		  break;
		}
	  }
	  
	  // If any non-digit charaters are addded to the token, the
	  // token no longer qualifies as an integer
	  if(isInt){
		if(!isDigit(c)) isInt = false;  
	  }
  
	  tokenStr += c;
	  sourcePos++;
	}

	// Ignores comments (/*...*/)
	if(tokenStr.equals("/")) {
	  if((sourcePos) <= sourceLine.length() - 1 
		  && sourceLine.charAt(sourcePos) == '*') {
		while(sourceFile != null && !sourceLine.contains("*/")){
		  readNextLine();
		}
		
		if(sourceFile != null){
		  sourcePos = sourceLine.lastIndexOf("*/") + 2;
		}
		readNextToken();	
		return;
	  }

	// Ignores comments ({...})
	}else if(tokenStr.equals("{")){
	  while(sourceFile != null && !sourceLine.contains("}")){
		readNextLine();
	  }
	  
	  if(sourceFile != null){
		sourcePos = sourceLine.indexOf("}") + 1;
	  }
	  readNextToken();
	  return;

	  // String tokens handled here
	  /*
	}else if(tokenStr.equals("'") || tokenStr.equals("’")) {
	  c = 0;
	  
	  while(sourceFile != null && c != '\'' && c != '’'){
		if((sourcePos) >= sourceLine.length() - 1) {
		  readNextLine();
		  sourcePos = 0;
		}

		c = sourceLine.charAt(sourcePos);
		tokenStr += c;
		sourcePos++;
	  }*/
	  
	}else if(tokenStr.equals("'")) {
		  c = 0;
		  
		  while(sourceFile != null && c != '\''){
			if((sourcePos) >= sourceLine.length() - 1) {
			  readNextLine();
			  sourcePos = 0;
			}

			c = sourceLine.charAt(sourcePos);
			tokenStr += c;
			sourcePos++;
		  }
	  
	  //Handles .., :=, >=, <= and <> symbols
	}else if(tokenStr.equals(".") && (sourcePos < sourceLine.length() - 1)){
	  if(sourceLine.charAt(sourcePos) == '.'){
		tokenStr += sourceLine.charAt(sourcePos);
		sourcePos++;
	  }
	}else if(tokenStr.equals(":") && (sourcePos < sourceLine.length() - 1)){
	  if(sourceLine.charAt(sourcePos) == '='){
		tokenStr += sourceLine.charAt(sourcePos);
		sourcePos++;
	  }
	}else if(tokenStr.equals(">") && (sourcePos < sourceLine.length() - 1)){
	  if(sourceLine.charAt(sourcePos) == '='){
		tokenStr += sourceLine.charAt(sourcePos);
		sourcePos++;
	  }
	}else if(tokenStr.equals("<") && (sourcePos < sourceLine.length() - 1)){
	  if(sourceLine.charAt(sourcePos) == '='){
		tokenStr += sourceLine.charAt(sourcePos);
		sourcePos++;
	  }else if(sourceLine.charAt(sourcePos) == '>'){
		tokenStr += sourceLine.charAt(sourcePos);
		sourcePos++;
	  }
	}

	curToken = nextToken;

	//Token kinds handled here
	if(!isLetterOrDigit(tokenStr.charAt(0))) {
	  
	  //Handles text tokens
	//if(tokenStr.charAt(0) == '\'' || tokenStr.charAt(0) ==  '’') {
	  if(tokenStr.charAt(0) == '\'') {
		tokenStr = tokenStr.replace("'", "");
		tokenStr = tokenStr.replace("’", "");
		nextToken = new Token("wut", tokenStr, getFileLineNum());

		//Handles non-letter/non-digit tokens
	  }else{
		TokenKind tk = TokenKind.getKind(tokenStr);
		if(tk != null) {
		  nextToken = new Token(tk, getFileLineNum());
		}else{
		  error("Invalid TokenKind returned:" +  tokenStr);
		  System.exit(-1);
		}
	  }
	  
	  //Handles number Tokens
	}else if(isInt) {

	  //Whether the token only contains digits or not is checked earlier.
	  try{
		nextToken = new Token(Integer.parseInt(tokenStr), getFileLineNum());
		
	  }catch(NumberFormatException e){
		
		//Should never get here
		error("ERROR: Token should only have had digits: " + tokenStr);
		System.exit(-1);
	  }  
	}else{
	  nextToken = new Token(tokenStr, getFileLineNum());
	}

	Main.log.noteToken(nextToken);
  }


  
  private void readNextLine() {
	if (sourceFile != null) {
	  try {
		sourceLine = sourceFile.readLine();
		if (sourceLine == null) {
		  sourceFile.close();
		  sourceFile = null;
		  sourceLine = "";
		} else {
		  sourceLine += " ";
		}
		sourcePos = 0;
	  } catch (IOException e) {
		Main.error("Scanner error: unspecified I/O error!");
	  }
	}
	if (sourceFile != null)
	  Main.log.noteSourceLine(getFileLineNum(), sourceLine);
  }

  private int getFileLineNum() {
	return (sourceFile != null ? sourceFile.getLineNumber() : 0);
  }

  // Character test utilities:

  private boolean isLetterAZ(char c) {
	return 'A' <= c && c <= 'Z' || 'a' <= c && c <= 'z';
  }

  private boolean isDigit(char c) {
	return '0' <= c && c <= '9';
  }
  
  private boolean isLetterOrDigit(char c){
 	return(isLetterAZ(c) || isDigit(c));
  }

  // Parser tests:

  public void test(TokenKind t) {
	if (curToken.kind != t)
	  testError(t.toString());
  }

  public void testError(String message) {
	Main.error(curLineNum(), "Expected a " + message + " but found a " + curToken.kind + "!");
  }

  public void skip(TokenKind t) {
	test(t);
	readNextToken();
  }
}
