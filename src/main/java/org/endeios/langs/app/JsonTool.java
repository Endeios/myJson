package org.endeios.langs.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map.Entry;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.endeios.json.JSONLexer;
import org.endeios.json.JSONParser;
import org.endeios.json.JSONParser.ObjectContext;
import org.endeios.langs.ExtractInterfaceVisitor;

public class JsonTool {

	public static void main(String[] args) throws IOException {
		System.out.println("Begin - ");
		String inputFile = null;
        if ( args.length>0 ) inputFile = args[0];
        InputStream is = System.in;
        if ( inputFile!=null ) is = new FileInputStream(inputFile);
        ANTLRInputStream input = new ANTLRInputStream(is);
        JSONLexer lexer = new JSONLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JSONParser parser = new JSONParser(tokens);
//        List<Token> moreTokens = tokens.getTokens();
//        for (Token token : moreTokens) {
//			System.out.println(token);
//		}
        ParseTree tree = parser.object();
        ExtractInterfaceVisitor visitor = new ExtractInterfaceVisitor();
        System.out.println("Visinting tree");
        Entry<String, Object> a = visitor.visit(tree);
        System.out.println(a);
	}

}
