public class CompilerFrontendImpl extends CompilerFrontend {

    public CompilerFrontendImpl() {
        super();
    }

    public CompilerFrontendImpl(boolean debug_) {
        super(debug_);
    }

    /*
     * Initializes the local field "lex" to be equal to the desired lexer.
     */
    @Override
    protected void init_lexer() {
        LexerImpl lexer = new LexerImpl();

        // NUM: [0-9]*\.[0-9]+
        Automaton num = new AutomatonImpl();
        num.addState(0, true, false);
        num.addState(1, false, false);
        num.addState(2, false, true);
        for (char i = '0'; i <= '9'; i++) {
            num.addTransition(0, i, 0);
            num.addTransition(1, i, 2);
            num.addTransition(2, i, 2);
        }
        num.addTransition(0, '.', 1);
        lexer.add_automaton(TokenType.NUM, num);

        // PLUS: \+
        Automaton plus = new AutomatonImpl();
        plus.addState(0, true, false);
        plus.addState(1, false, true);
        plus.addTransition(0, '+', 1);
        lexer.add_automaton(TokenType.PLUS, plus);

        // MINUS: -
        Automaton minus = new AutomatonImpl();
        minus.addState(0, true, false);
        minus.addState(1, false, true);
        minus.addTransition(0, '-', 1);
        lexer.add_automaton(TokenType.MINUS, minus);

        // TIMES: \*
        Automaton times = new AutomatonImpl();
        times.addState(0, true, false);
        times.addState(1, false, true);
        times.addTransition(0, '*', 1);
        lexer.add_automaton(TokenType.TIMES, times);

        // DIV: /
        Automaton div = new AutomatonImpl();
        div.addState(0, true, false);
        div.addState(1, false, true);
        div.addTransition(0, '/', 1);
        lexer.add_automaton(TokenType.DIV, div);

        // LPAREN: \(
        Automaton lparen = new AutomatonImpl();
        lparen.addState(0, true, false);
        lparen.addState(1, false, true);
        lparen.addTransition(0, '(', 1);
        lexer.add_automaton(TokenType.LPAREN, lparen);

        // RPAREN: \)
        Automaton rparen = new AutomatonImpl();
        rparen.addState(0, true, false);
        rparen.addState(1, false, true);
        rparen.addTransition(0, ')', 1);
        lexer.add_automaton(TokenType.RPAREN, rparen);

        // WHITE_SPACE (' '|\n|\r|\t)*
        Automaton whiteSpace = new AutomatonImpl();
        whiteSpace.addState(0, true, true);
        whiteSpace.addTransition(0, ' ', 0);
        whiteSpace.addTransition(0, '\n', 0);
        whiteSpace.addTransition(0, '\r', 0);
        whiteSpace.addTransition(0, '\t', 0);
        lexer.add_automaton(TokenType.WHITE_SPACE, whiteSpace);

        this.lex = lexer;
    }
}
