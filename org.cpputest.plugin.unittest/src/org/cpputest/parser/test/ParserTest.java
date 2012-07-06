package org.cpputest.parser.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.cpputest.parser.CTokenTranslator;
import org.cpputest.parser.Tokenizer;
import org.cpputest.parser.YieldParser;
import org.cpputest.parser.parts.CppPart;
import org.junit.Test;

public class ParserTest {

	@Test
	public void testFunction() {
		List<CppPart> parts = parse("int fun(){}");
		assertEquals(5, parts.size());
		assertEquals(CppPart.StartNewFunction("int", 0), parts.get(0));
		assertEquals(CppPart.AddToFunctionName("fun"), parts.get(1));
		assertEquals(CppPart.PartOfLongFunctionName("("), parts.get(2));
		assertEquals(CppPart.EndOfFunctionSignature(")"), parts.get(3));
		assertEquals(CppPart.EndOfFunction(), parts.get(4));
	}
	private List<CppPart> parse(String string) {
		final ArrayList<CppPart> list = new ArrayList<CppPart>();
		YieldParser yp = new YieldParser() {
			@Override
			public void yield(CppPart fun) {
				list.add(fun);
			}
		};
		
		CTokenTranslator trans = new CTokenTranslator(yp);
		Tokenizer tokenizer = new Tokenizer();
		tokenizer.generateTokensFromSourceCode(string, trans);
		
		return list;
	}

}