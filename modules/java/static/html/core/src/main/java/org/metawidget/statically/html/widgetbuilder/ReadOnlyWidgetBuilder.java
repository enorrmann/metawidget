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

package org.metawidget.statically.html.widgetbuilder;

import static org.metawidget.inspector.InspectionResultConstants.*;

import java.awt.Color;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.metawidget.statically.StaticXmlMetawidget;
import org.metawidget.statically.StaticXmlStub;
import org.metawidget.statically.StaticXmlWidget;
import org.metawidget.statically.layout.SimpleLayout;
import org.metawidget.util.ClassUtils;
import org.metawidget.util.CollectionUtils;
import org.metawidget.util.WidgetBuilderUtils;
import org.metawidget.widgetbuilder.iface.WidgetBuilder;

/**
 * @author <a href="http://kennardconsulting.com">Richard Kennard</a>
 */

public class ReadOnlyWidgetBuilder
	implements WidgetBuilder<StaticXmlWidget, StaticXmlMetawidget> {

	//
	// Public methods
	//

	public StaticXmlWidget buildWidget( String elementName, Map<String, String> attributes, StaticXmlMetawidget metawidget ) {

		// Not read-only?

		if ( !WidgetBuilderUtils.isReadOnly( attributes ) ) {
			return null;
		}

		// Hidden

		if ( TRUE.equals( attributes.get( HIDDEN ) ) ) {
			return new StaticXmlStub();
		}

		// Masked (return a couple of nested Stubs, so that we DO still render a label)

		if ( TRUE.equals( attributes.get( MASKED ) ) ) {
			StaticXmlStub staticStub = new StaticXmlStub();
			staticStub.getChildren().add( new HtmlOutput() );
			return staticStub;
		}

		// Action

		if ( ACTION.equals( elementName ) ) {
			return new StaticXmlStub();
		}

		// Spring Lookup?

		// Lookups

		String lookup = attributes.get( LOOKUP );

		if ( lookup != null && !"".equals( lookup ) ) {
			String lookupLabels = attributes.get( LOOKUP_LABELS );

			if ( lookupLabels == null ) {
				return new HtmlOutput();
			}

			// Special support for read-only lookups with labels

			List<String> labels = CollectionUtils.fromString( lookupLabels );

			if ( labels.isEmpty() ) {
				return new HtmlOutput();
			}

			return new HtmlOutput();
		}

		// Lookup the class

		Class<?> clazz = WidgetBuilderUtils.getActualClassOrType( attributes, String.class );

		if ( clazz != null ) {
			// Primitives

			if ( clazz.isPrimitive() ) {
				return new HtmlOutput();
			}

			// Object primitives

			if ( ClassUtils.isPrimitiveWrapper( clazz ) ) {
				return new HtmlOutput();
			}

			// Dates

			if ( Date.class.isAssignableFrom( clazz ) ) {
				return new HtmlOutput();
			}

			// Colors

			if ( Color.class.equals( clazz ) ) {
				return new HtmlOutput();
			}

			// Strings

			if ( String.class.equals( clazz ) ) {
				return new HtmlOutput();
			}

			// Collections that will be supported by HtmlWidgetBuilder

			if ( List.class.isAssignableFrom( clazz ) || clazz.isArray() ) {
				return null;
			}

			// Other Collections

			if ( Collection.class.isAssignableFrom( clazz ) ) {
				return new HtmlOutput();
			}
		}

		// Not simple, but don't expand

		if ( TRUE.equals( attributes.get( DONT_EXPAND ) ) || metawidget.getLayout() instanceof SimpleLayout ) {
			return new HtmlOutput();
		}

		// Nested Metawidget

		return null;
	}
}
