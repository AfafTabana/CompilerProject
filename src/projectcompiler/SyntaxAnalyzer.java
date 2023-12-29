
package projectcompiler;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import projectcompiler.Token.TokenType;
import static projectcompiler.Token.TokenType.EOF;


public class SyntaxAnalyzer {
    private final List<Token> tokens;
    private int position;

    public SyntaxAnalyzer(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    public void parse() throws SyntaxError {
        program();
        match(TokenType.EOF, "");
    }

    private void program() throws SyntaxError {
        while (position < tokens.size()) {
            statement();
        }
    }

    private void statement() throws SyntaxError {
        Token currentToken = getCurrentToken();

        switch (currentToken.getType()) {
            case IDENTIFIER:
                assignmentStatement() ; 
                break;
            case KEYWORD:
                switch (currentToken.getLexeme()) {
                    case "if":
                        ifStatement();
                        break;
                    case "while":
                        whileStatement();
                        break;
                    case "for":
                        forStatement();
                        break;
                    default:
                        throw new SyntaxError("Invalid keyword");
                }
                break;
     
        }
    }

    private void assignmentStatement() throws SyntaxError {
           // Grammar: identifier '=' expression ';'
        match(TokenType.IDENTIFIER, "");
        match(TokenType.OPERATORS, "=");
        expression();
        match(TokenType.SPECIALCHARACTER, ";");
         if (getCurrentToken().getType().equals(EOF)){
            System.out.print("build Sucessfully...Valid Statment ");
            return ; 
        }
    }

    private void ifStatement() throws SyntaxError {
        // Grammar: 'if' '(' condition ')' '{' program '}' ('else' '{' program '}')?
        match(TokenType.KEYWORD, "if");
        match(TokenType.SPECIALCHARACTER, "(");
        condition();
        match(TokenType.SPECIALCHARACTER, ")");
        match(TokenType.SPECIALCHARACTER, "{");
        statement();
        match(TokenType.SPECIALCHARACTER, "}");

        if (getCurrentToken().getLexeme().equals("else")) {
            match(TokenType.KEYWORD, "else");
            match(TokenType.SPECIALCHARACTER, "{");
            statement();
            match(TokenType.SPECIALCHARACTER, "}");
        }else if (getCurrentToken().getType().equals(EOF)){
            System.out.print("build Sucessfully...Valid Statment ");
        }
    }

    private void whileStatement() throws SyntaxError {
        // Grammar: 'while' '(' condition ')' '{' program '}'
        match(TokenType.KEYWORD, "while");
        match(TokenType.SPECIALCHARACTER, "(");
        condition();
        match(TokenType.SPECIALCHARACTER, ")");
        match(TokenType.SPECIALCHARACTER, "{");
        statement();
        match(TokenType.SPECIALCHARACTER, "}");
        if (getCurrentToken().getType().equals(EOF)){
            System.out.print("build Sucessfully...Valid Statment ");
            return ; 
        }
    }

    private void forStatement() throws SyntaxError {
        // Grammar: 'for' '(' assignmentStatement ';' condition ';' assignmentStatement ')' '{' program '}'
        match(TokenType.KEYWORD, "for");
        match(TokenType.SPECIALCHARACTER, "(");
        assignmentStatement();
        match(TokenType.SPECIALCHARACTER, ";");
        condition();
        match(TokenType.SPECIALCHARACTER, ";");
        assignmentStatement();
        match(TokenType.SPECIALCHARACTER, ")");
        match(TokenType.SPECIALCHARACTER, "{");
        statement();
        match(TokenType.SPECIALCHARACTER, "}");
        
         if (getCurrentToken().getType().equals(EOF)){
            System.out.print("build Sucessfully...Valid Statment ");
            return ; 
        }
    }

    private void condition() throws SyntaxError {
        expression();
        match(TokenType.OPERATORS, "<");
        expression();
    }

    private void expression() throws SyntaxError {
        oneterm();
        while (getCurrentToken().getType() == TokenType.OPERATORS && (getCurrentToken().getLexeme().equals("+") || getCurrentToken().getLexeme().equals("-"))) {
            match(TokenType.OPERATORS, getCurrentToken().getLexeme());
            oneterm();
        }
    }

    private void oneterm() throws SyntaxError {
        fact();
        while (getCurrentToken().getType() == TokenType.OPERATORS && (getCurrentToken().getLexeme().equals("*") || getCurrentToken().getLexeme().equals("/"))) {
            match(TokenType.OPERATORS, getCurrentToken().getLexeme());
            fact();
        }
    }

    private void fact() throws SyntaxError {
        if (getCurrentToken().getType() == TokenType.IDENTIFIER || getCurrentToken().getType() == TokenType.NUMERICCONSTANT) {
            match(getCurrentToken().getType(), "");
        } else {
            throw new SyntaxError("Invalid factor");
        }
    }

 
    private Token getCurrentToken() {
        return tokens.get(position);
    }

    private void add() {
        position++;
    }

    private void match(TokenType expectedType, String expectedLexeme) throws SyntaxError {
        Token currentToken = getCurrentToken();
        if (currentToken.getType() == expectedType && (expectedLexeme.equals(expectedLexeme))) {
            System.out.println (currentToken.getLexeme() + " is valid ") ;
            add();
        } else {
            throw new SyntaxError("Unexpected token: " + currentToken.getLexeme());
        }
    }
}


 