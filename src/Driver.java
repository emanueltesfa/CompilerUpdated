import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.*;

public class Driver {
    public static void main(String[] args) throws Exception {
        /* Step 1: Scanner/Tokenizer/Lexer */

        // create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(System.in);

        // create a lexer that feeds off of input CharStream
        LittleLexer lexer = new LittleLexer(input);

        /*Step 2: Parser*/
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        LittleParser parser = new LittleParser(tokens);

        /*Step 3 Symbol Table*/
        ParseTree tree = parser.program(); // begin parsing at program rule

        // Create a generic parse tree walker that can trigger callbacks
        ParseTreeWalker walker = new ParseTreeWalker();

        ASTBuilder ast = new ASTBuilder();

        // Walk the tree created during the parse, trigger callbacks
        walker.walk(ast, tree);

        /*Print AST*/
        //ast.printAST();

        //Generate IR Code after performing walk
        ast.IRCodeFactory();

        //Generate tiny code after IR Code conversion
        ast.tinyCodeFactory();
    }

}
