// Generated from C:/Users/My LENOVO/IdeaProjects/COP4620/src\Little.g4 by ANTLR 4.9
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Little extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		COMMENT=1, WS=2, KEYWORDS=3, IDENTIFIER=4, OPERATORS=5, INTLITERAL=6, 
		FLOATLITERAL=7, STRINGLITERAL=8;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"COMMENT", "WS", "KEYWORDS", "IDENTIFIER", "OPERATORS", "INTLITERAL", 
			"FLOATLITERAL", "STRINGLITERAL"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "COMMENT", "WS", "KEYWORDS", "IDENTIFIER", "OPERATORS", "INTLITERAL", 
			"FLOATLITERAL", "STRINGLITERAL"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public Little(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Little.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\n\u00b6\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2"+
		"\3\2\7\2\30\n\2\f\2\16\2\33\13\2\3\2\5\2\36\n\2\3\2\3\2\3\2\3\2\3\3\6"+
		"\3%\n\3\r\3\16\3&\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\u0088\n\4\3\5\3\5"+
		"\7\5\u008c\n\5\f\5\16\5\u008f\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\5\6\u009b\n\6\3\7\6\7\u009e\n\7\r\7\16\7\u009f\3\b\7\b\u00a3\n\b"+
		"\f\b\16\b\u00a6\13\b\3\b\3\b\6\b\u00aa\n\b\r\b\16\b\u00ab\3\t\3\t\7\t"+
		"\u00b0\n\t\f\t\16\t\u00b3\13\t\3\t\3\t\2\2\n\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\3\2\n\4\2\f\f\17\17\5\2\13\f\17\17\"\"\4\2C\\c|\5\2\62;C\\"+
		"c|\6\2,-//\61\61??\6\2*+..=>@@\3\2\62;\3\2$$\2\u00d3\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\3\23\3\2\2\2\5$\3\2\2\2\7\u0087\3\2\2\2\t\u0089\3\2\2\2\13"+
		"\u009a\3\2\2\2\r\u009d\3\2\2\2\17\u00a4\3\2\2\2\21\u00ad\3\2\2\2\23\24"+
		"\7/\2\2\24\25\7/\2\2\25\31\3\2\2\2\26\30\n\2\2\2\27\26\3\2\2\2\30\33\3"+
		"\2\2\2\31\27\3\2\2\2\31\32\3\2\2\2\32\35\3\2\2\2\33\31\3\2\2\2\34\36\7"+
		"\17\2\2\35\34\3\2\2\2\35\36\3\2\2\2\36\37\3\2\2\2\37 \7\f\2\2 !\3\2\2"+
		"\2!\"\b\2\2\2\"\4\3\2\2\2#%\t\3\2\2$#\3\2\2\2%&\3\2\2\2&$\3\2\2\2&\'\3"+
		"\2\2\2\'(\3\2\2\2()\b\3\2\2)\6\3\2\2\2*+\7R\2\2+,\7T\2\2,-\7Q\2\2-.\7"+
		"I\2\2./\7T\2\2/\60\7C\2\2\60\u0088\7O\2\2\61\62\7D\2\2\62\63\7G\2\2\63"+
		"\64\7I\2\2\64\65\7K\2\2\65\u0088\7P\2\2\66\67\7G\2\2\678\7P\2\28\u0088"+
		"\7F\2\29:\7H\2\2:;\7W\2\2;<\7P\2\2<=\7E\2\2=>\7V\2\2>?\7K\2\2?@\7Q\2\2"+
		"@\u0088\7P\2\2AB\7T\2\2BC\7G\2\2CD\7C\2\2D\u0088\7F\2\2EF\7Y\2\2FG\7T"+
		"\2\2GH\7K\2\2HI\7V\2\2I\u0088\7G\2\2JK\7K\2\2K\u0088\7H\2\2LM\7G\2\2M"+
		"N\7N\2\2NO\7U\2\2O\u0088\7G\2\2PQ\7G\2\2QR\7P\2\2RS\7F\2\2ST\7K\2\2T\u0088"+
		"\7H\2\2UV\7Y\2\2VW\7J\2\2WX\7K\2\2XY\7N\2\2Y\u0088\7G\2\2Z[\7G\2\2[\\"+
		"\7P\2\2\\]\7F\2\2]^\7Y\2\2^_\7J\2\2_`\7K\2\2`a\7N\2\2a\u0088\7G\2\2bc"+
		"\7E\2\2cd\7Q\2\2de\7P\2\2ef\7V\2\2fg\7K\2\2gh\7P\2\2hi\7W\2\2i\u0088\7"+
		"G\2\2jk\7D\2\2kl\7T\2\2lm\7G\2\2mn\7C\2\2n\u0088\7M\2\2op\7T\2\2pq\7G"+
		"\2\2qr\7V\2\2rs\7W\2\2st\7T\2\2t\u0088\7P\2\2uv\7K\2\2vw\7P\2\2w\u0088"+
		"\7V\2\2xy\7X\2\2yz\7Q\2\2z{\7K\2\2{\u0088\7F\2\2|}\7U\2\2}~\7V\2\2~\177"+
		"\7T\2\2\177\u0080\7K\2\2\u0080\u0081\7P\2\2\u0081\u0088\7I\2\2\u0082\u0083"+
		"\7H\2\2\u0083\u0084\7N\2\2\u0084\u0085\7Q\2\2\u0085\u0086\7C\2\2\u0086"+
		"\u0088\7V\2\2\u0087*\3\2\2\2\u0087\61\3\2\2\2\u0087\66\3\2\2\2\u00879"+
		"\3\2\2\2\u0087A\3\2\2\2\u0087E\3\2\2\2\u0087J\3\2\2\2\u0087L\3\2\2\2\u0087"+
		"P\3\2\2\2\u0087U\3\2\2\2\u0087Z\3\2\2\2\u0087b\3\2\2\2\u0087j\3\2\2\2"+
		"\u0087o\3\2\2\2\u0087u\3\2\2\2\u0087x\3\2\2\2\u0087|\3\2\2\2\u0087\u0082"+
		"\3\2\2\2\u0088\b\3\2\2\2\u0089\u008d\t\4\2\2\u008a\u008c\t\5\2\2\u008b"+
		"\u008a\3\2\2\2\u008c\u008f\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2"+
		"\2\2\u008e\n\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0091\7<\2\2\u0091\u009b"+
		"\7?\2\2\u0092\u009b\t\6\2\2\u0093\u0094\7#\2\2\u0094\u009b\7?\2\2\u0095"+
		"\u009b\t\7\2\2\u0096\u0097\7>\2\2\u0097\u009b\7?\2\2\u0098\u0099\7@\2"+
		"\2\u0099\u009b\7?\2\2\u009a\u0090\3\2\2\2\u009a\u0092\3\2\2\2\u009a\u0093"+
		"\3\2\2\2\u009a\u0095\3\2\2\2\u009a\u0096\3\2\2\2\u009a\u0098\3\2\2\2\u009b"+
		"\f\3\2\2\2\u009c\u009e\t\b\2\2\u009d\u009c\3\2\2\2\u009e\u009f\3\2\2\2"+
		"\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\16\3\2\2\2\u00a1\u00a3"+
		"\t\b\2\2\u00a2\u00a1\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4"+
		"\u00a5\3\2\2\2\u00a5\u00a7\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a7\u00a9\7\60"+
		"\2\2\u00a8\u00aa\t\b\2\2\u00a9\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab"+
		"\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\20\3\2\2\2\u00ad\u00b1\7$\2\2"+
		"\u00ae\u00b0\n\t\2\2\u00af\u00ae\3\2\2\2\u00b0\u00b3\3\2\2\2\u00b1\u00af"+
		"\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b4\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b4"+
		"\u00b5\7$\2\2\u00b5\22\3\2\2\2\r\2\31\35&\u0087\u008d\u009a\u009f\u00a4"+
		"\u00ab\u00b1\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}