package org.endeios.langs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.endeios.json.JSONBaseVisitor;
import org.endeios.json.JSONParser.AddObjectContext;
import org.endeios.json.JSONParser.AttributeContext;
import org.endeios.json.JSONParser.ObjectContext;
import org.endeios.json.JSONParser.SimpleAssignContext;

public class ExtractInterfaceVisitor extends
		JSONBaseVisitor<Entry<String, Object>> {
	Map<String, Object> result = new HashMap<String, Object>();
	Map<String, Object> current;

	class MyEntry implements Entry<String, Object> {

		private String key;
		private Object Value;

		public MyEntry(String key, Object value) {
			this.key = key;
			this.Value = value;
		}

		public String getKey() {
			return key;
		}

		public Object getValue() {
			return this.Value;
		}

		public Object setValue(Object value) {
			this.Value = value;
			return Value;
		}
		@Override
		public String toString() {
			return java.text.MessageFormat.format("[ {0} : {1}]", key,Value.toString());
		}
	}

	@Override
	public Entry<String, Object> visitObject(ObjectContext ctx) {
		// System.out.println("Object here");
		HashMap<String, Object> a = new HashMap<String, Object>();
		List<AttributeContext> attrs = ctx.attribute();
		for (AttributeContext attributeContext : attrs) {
			Entry<String, Object> aRes = visit(attributeContext);
			a.put(aRes.getKey(), aRes.getValue());
		}
		return new MyEntry("object", a);
	}

	@Override
	public Entry<String, Object> visitSimpleAssign(SimpleAssignContext ctx) {
		String key = ctx.ID(0).getText();
		String value = ctx.ID(1).getText();
		// System.out.println(key+" --> "+value);
		return new MyEntry(key, value);
	}

	@Override
	public Entry<String, Object> visitAddObject(AddObjectContext ctx) {
		// TODO Auto-generated method stub
		MyEntry e = new MyEntry(ctx.ID().getText(), visitObject(ctx.object()));
		// System.out.println(e.getValue());
		return e;
	}

}
