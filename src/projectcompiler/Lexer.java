
package projectcompiler;

import java.util.ArrayList;
import java.util.List;
import projectcompiler.Token.TokenType;


public class Lexer {
    
    private String input;
    private int position;

    public Lexer(String input) {
        this.input = input;
        this.position = 0;
    }
    
    

public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();   
        
        
            while (position < input.length()) {
            char currentChar = input.charAt(position);
            
             if (Character.isDigit(currentChar)) {
                tokens.add(tokenizeNumber());
            } else if (Character.isLetter(currentChar)) {
                tokens.add(tokenizeIdentifier());
               
               } else if ((currentChar == '/')){
                    if ((input.charAt(position+1) == '/') || (input.charAt(position+1) == '*') ){
                   tokens.add(tokenizecomment());
                    }else {
                        String lex = Character.toString(currentChar) ; 
                tokens.add(new Token(TokenType.OPERATORS, lex ));
               
                position++ ;
                    }
               
            } else if (currentChar == '+' || currentChar == '-' || currentChar == '*'||  currentChar == '=' || currentChar == '<' || currentChar == '>' ) {
                String lex = Character.toString(currentChar) ; 
                tokens.add(new Token(TokenType.OPERATORS, lex )); 
                position++ ;
                
            } else if (currentChar == '(' || currentChar == ')' || currentChar == ':' || currentChar == ';'|| currentChar == '{'|| currentChar == '}') {
                String lex = Character.toString(currentChar) ; 
                tokens.add(new Token(TokenType.SPECIALCHARACTER , lex));
                position++;
            
            } else {
               
                position++;
            }
            }
      tokens.add(new Token(TokenType.EOF , "")) ;
        return tokens;
    
}
             
        private Token tokenizeNumber() {
           
            
           
        StringBuilder buffer = new StringBuilder();

        while (position < input.length() && Character.isDigit(input.charAt(position))) {
           
            buffer.append(input.charAt(position));
            position++;
        }

        return new Token(TokenType.NUMERICCONSTANT, buffer.toString());
    }
        
     private Token tokenizeIdentifier() {
        StringBuilder buffer = new StringBuilder();

        while (position < input.length() && (Character.isLetter(input.charAt(position))))
                 {
            buffer.append(input.charAt(position));
            position++;
        }
 if ("int".equals(buffer.toString()) || "while".equals(buffer.toString()) || "if".equals(buffer.toString()) || "for".equals(buffer.toString()) || "float".equals(buffer.toString()) || "else".equals(buffer.toString())  ){ 
             
        return new Token(TokenType.KEYWORD, buffer.toString());
     }else{
         return  new Token(TokenType.IDENTIFIER, buffer.toString());
         }
    }
     
     
     private Token tokenizecomment() {
        StringBuilder buffer = new StringBuilder();

        while(position < input.length() && input.charAt(position) == '/' ) 
                 {
                if (input.charAt(position + 1 ) == '/' || input.charAt(position+ 1 ) == '*' ) {
            buffer.append(input.charAt(position)).append(input.charAt(position  + 1));
            
                }
              position++;   
                
        }
        
        if ("//".equals(buffer.toString()) ){
            while (position < input.length()&& input.charAt(position) != '\n' ){
               buffer.append(input.charAt(position));
            position++;  
            } 
        }
      
        else if ("/*".equals(buffer.toString())){
         
            while (position < input.length()&& (input.charAt(position++) != '*' && input.charAt(position++) != '/' ) ){
                 buffer.append(input.charAt(position));
                  position++; 
            }
        }
           
       return  new Token(TokenType.COMMENTS, buffer.toString());
         
    }
}
     


    

    



