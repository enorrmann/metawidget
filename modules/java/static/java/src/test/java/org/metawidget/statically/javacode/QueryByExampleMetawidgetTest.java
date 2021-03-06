// Metawidget
//
// This file is dual licensed under both the LGPL
// (http://www.gnu.org/licenses/lgpl-2.1.html) and the EPL
// (http://www.eclipse.org/org/documents/epl-v10.php). As a
// recipient of Metawidget, you may choose to receive it under either
// the LGPL or the EPL.
//
// Commercial licenses are also available. See http://metawidget.org
// for details.

package org.metawidget.statically.javacode;

import static org.metawidget.inspector.InspectionResultConstants.*;

import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.metawidget.statically.StaticWidget;
import org.metawidget.util.CollectionUtils;
import org.metawidget.util.WidgetBuilderUtils;
import org.metawidget.util.simple.StringUtils;
import org.metawidget.widgetbuilder.iface.WidgetBuilder;

/**
 * @author <a href="http://kennardconsulting.com">Richard Kennard</a>
 */

public class QueryByExampleMetawidgetTest
	extends TestCase {

	//
	// Public methods
	//

	public void testWidgetBuilder() {

		QueryByExampleWidgetBuilder widgetBuilder = new QueryByExampleWidgetBuilder();
		Map<String, String> attributes = CollectionUtils.newHashMap();
		attributes.put( NAME, "abc" );
		attributes.put( TYPE, String.class.getName() );
		StaticWidget widget = widgetBuilder.buildWidget( PROPERTY, attributes, new StaticJavaMetawidget() );

		assertEquals(
					"@SuppressWarnings( \"unchecked\" )\r\nString abc = this.search.getAbc();if (abc != null && !\"\".equals(abc)) { predicatesList.add(builder.equal(root.get(\"abc\"),abc)); }",
					widget.toString() );
	}

	public void testMetawidget() {

		StaticJavaMetawidget metawidget = new StaticJavaMetawidget();
		metawidget.setPath( Foo.class.getName() );
		metawidget.setWidgetBuilder( new QueryByExampleWidgetBuilder() );

		String result = "@SuppressWarnings( \"unchecked\" )\r\n" +
				"String abc = this.search.getAbc();\r\n" +
				"if (abc != null && !\"\".equals(abc)) {\r\n" +
				"\tpredicatesList.add(builder.equal(root.get(\"abc\"),abc));\r\n" +
				"}\r\n" +
				"// Search baz\r\n" +
				"Baz baz = this.search.getBaz();\r\n" +
				"if (baz != null) {\r\n" +
				"\tpredicatesList.add(builder.equal(root.get(\"baz\"),baz));\r\n" +
				"}\r\n" +
				"@SuppressWarnings( \"unchecked\" )\r\n" +
				"String def = this.search.getDef();\r\n" +
				"if (def != null && !\"\".equals(def)) {\r\n" +
				"\tpredicatesList.add(builder.equal(root.get(\"def\"),def));\r\n" +
				"}\r\n" +
				"@SuppressWarnings( \"unchecked\" )\r\n" +
				"String ghi = this.search.getGhi();\r\n" +
				"if (ghi != null && !\"\".equals(ghi)) {\r\n" +
				"\tpredicatesList.add(builder.equal(root.get(\"ghi\"),ghi));\r\n" +
				"}\r\n";

		StringWriter writer = new StringWriter();
		metawidget.write( writer, 0 );
		assertEquals( result, writer.toString() );

		Set<String> imports = metawidget.getImports();
		assertEquals( Baz.class.getName(), imports.iterator().next() );
		assertEquals( 1, imports.size() );
	}

	//
	// Inner class
	//

	public static class QueryByExampleWidgetBuilder
		implements WidgetBuilder<StaticJavaWidget, StaticJavaMetawidget> {

		//
		// Public methods
		//

		public StaticJavaWidget buildWidget( String elementName, Map<String, String> attributes, StaticJavaMetawidget metawidget ) {

			// Hidden

			if ( TRUE.equals( attributes.get( HIDDEN ) ) ) {
				return new StaticJavaStub();
			}

			Class<?> clazz = WidgetBuilderUtils.getActualClassOrType( attributes, null );

			// If no type, fail gracefully

			if ( clazz == null ) {
				return new StaticJavaStub();
			}

			// Lookup the Class

			String name = attributes.get( NAME );

			// Strings

			if ( String.class.equals( clazz ) ) {
				StaticJavaWidget toReturn = new StaticJavaStub();
				toReturn.getChildren().add( new JavaLiteral( "@SuppressWarnings( \"unchecked\" )\r\n" ));
				toReturn.getChildren().add( new JavaStatement( "String " + name + " = this.search.get" + StringUtils.capitalize( name ) + "()" ) );
				JavaStatement ifNotEmpty = new JavaStatement( "if (" + name + " != null && !\"\".equals(" + name + "))" );
				ifNotEmpty.getChildren().add( new JavaStatement( "predicatesList.add(builder.equal(root.get(\"" + name + "\")," + name + "))" ) );
				toReturn.getChildren().add( ifNotEmpty );
				return toReturn;
			}

			// Objects

			if ( Baz.class.equals( clazz ) ) {
				StaticJavaWidget toReturn = new StaticJavaStub();
				toReturn.getChildren().add( new JavaLiteral( "// Search " + name + "\r\n" ));
				JavaStatement getValue = new JavaStatement( clazz.getSimpleName() + " " + name + " = this.search.get" + StringUtils.capitalize( name ) + "()" );
				getValue.putImport( clazz.getName().toString() );
				toReturn.getChildren().add( getValue );
				JavaStatement ifNotEmpty = new JavaStatement( "if (" + name + " != null)" );
				ifNotEmpty.getChildren().add(
							new JavaStatement( "predicatesList.add(builder.equal(root.get(\"" + name + "\")," + name + "))" ) );
				toReturn.getChildren().add( ifNotEmpty );
				return toReturn;
			}

			// Do not recurse sub-entities for now

			if ( !ENTITY.equals( elementName ) ) {
				return new StaticJavaStub();
			}

			return null;
		}
	}

	public static class Foo {

		public String getGhi() {

			return null;
		}

		public void setGhi( @SuppressWarnings( "unused" ) String ghi ) {

			// Do nothing
		}

		public String getDef() {

			return null;
		}

		public void setDef( @SuppressWarnings( "unused" ) String def ) {

			// Do nothing
		}

		public Bar getBar() {

			return null;
		}

		public void setBar( @SuppressWarnings( "unused" ) String bar ) {

			// Do nothing
		}

		public Baz getBaz() {

			return null;
		}

		public void setBaz( @SuppressWarnings( "unused" ) String baz ) {

			// Do nothing
		}

		public String getAbc() {

			return null;
		}

		public void setAbc( @SuppressWarnings( "unused" ) String abc ) {

			// Do nothing
		}
	}

	public static class Bar {

		public String getIgnored() {

			return null;
		}

		public void setIgnored( @SuppressWarnings( "unused" ) String ignored ) {

			// Do nothing
		}
	}

	public static class Baz {

		public String getName() {

			return null;
		}

		public void setName( @SuppressWarnings( "unused" ) String name ) {

			// Do nothing
		}
	}
}
