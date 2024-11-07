
public class ParserImpl extends Parser {

    @Override
    public Expr do_parse() throws Exception {
        return parseT();
    }

    // Method to parse non-terminal `T`
    private Expr parseT() throws Exception {
        Expr left = parseF();  // Parse F
        if (peek(TokenType.PLUS, 0) || peek(TokenType.MINUS, 0)) {
            Token addOp = parseAddOp();  // Parse AddOp (PLUS or MINUS)
            Expr right = parseT();       // Recursively parse T

            if (addOp.ty == TokenType.PLUS) {
                return new PlusExpr(left, right);  // Create a PlusExpr if PLUS
            } else {
                return new MinusExpr(left, right); // Create a MinusExpr if MINUS
            }
        }
        return left;
    }

    // Method to parse non-terminal `F`
    private Expr parseF() throws Exception {
        Expr left = parseLit();  // Parse Lit
        if (peek(TokenType.TIMES, 0) || peek(TokenType.DIV, 0)) {
            Token mulOp = parseMulOp();  // Parse MulOp (TIMES or DIV)
            Expr right = parseF();       // Recursively parse F

            if (mulOp.ty == TokenType.TIMES) {
                return new TimesExpr(left, right); // Create a TimesExpr if TIMES
            } else {
                return new DivExpr(left, right);   // Create a DivExpr if DIV
            }
        }
        return left;
    }

    // Method to parse non-terminal `Lit`
    private Expr parseLit() throws Exception {
        if (peek(TokenType.NUM, 0)) {
            Token numToken = consume(TokenType.NUM); // Consume NUM token
            return new FloatExpr(Float.parseFloat(numToken.lexeme)); // Create FloatExpr
        } else if (peek(TokenType.LPAREN, 0)) {
            consume(TokenType.LPAREN);  // Consume LPAREN
            Expr expr = parseT();       // Parse T
            consume(TokenType.RPAREN);  // Consume RPAREN
            return expr;
        } else {
            throw new Exception("Parsing error: Expected a literal or '('");
        }
    }

    // Method to parse non-terminal `AddOp`
    private Token parseAddOp() throws Exception {
        if (peek(TokenType.PLUS, 0)) {
            return consume(TokenType.PLUS); // Consume and return PLUS token
        } else if (peek(TokenType.MINUS, 0)) {
            return consume(TokenType.MINUS); // Consume and return MINUS token
        } else {
            throw new Exception("Parsing error: Expected '+' or '-'");
        }
    }

    // Method to parse non-terminal `MulOp`
    private Token parseMulOp() throws Exception {
        if (peek(TokenType.TIMES, 0)) {
            return consume(TokenType.TIMES); // Consume and return TIMES token
        } else if (peek(TokenType.DIV, 0)) {
            return consume(TokenType.DIV);   // Consume and return DIV token
        } else {
            throw new Exception("Parsing error: Expected '*' or '/'");
        }
    }
}

