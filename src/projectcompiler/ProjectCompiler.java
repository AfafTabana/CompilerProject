
package projectcompiler;


import java.util.List;



public class ProjectCompiler {
    public static void main(String[] args) {
           String input = "while(x < 10 ) { x = x + 1 ; }";
           
           //if (x < 10 ) { x = x + 1 ; }
           // while(x < 10 ) { x = x + 1 ; }
           //x = 1;
           

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();
        
      
        
        for (int i =0 ; i<tokens.size();i++){
            
            Token token = tokens.get(i) ; 
            System.out.println(token.getLexeme() +"  Type : " + token.getType() ) ;
            
            
            
           
        }
        
      SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(tokens);

        try {
            
            syntaxAnalyzer.parse();
            System.out.println("Syntax analysis completed successfully.");
        } catch (SyntaxError e) {
            System.err.println("Syntax error: " + e.getMessage());
        }
    }
       
    }
      