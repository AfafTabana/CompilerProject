
package projectcompiler;


import java.util.ArrayList;
import java.util.List;

public class Token {
    
    private TokenType type;
    private String lexeme;
    

    public Token(TokenType type, String lexeme) {
        this.type = type;
        this.lexeme = lexeme;
    }
    
    public TokenType getType() {
        return type;
    }
    
    public String getLexeme() {
        return lexeme;
    }
    
    enum TokenType {
    IDENTIFIER,
    NUMERICCONSTANT,
    OPERATORS,
    SPECIALCHARACTER,
    KEYWORD,
    COMMENTS,
    EOF,
    }
    
    
}
