// Metawidget
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

package org.metawidget.widgetbuilder.impl;

import static org.metawidget.inspector.InspectionResultConstants.*;

import java.util.Map;

import junit.framework.TestCase;

import org.metawidget.util.CollectionUtils;
import org.metawidget.widgetbuilder.iface.WidgetBuilderException;
import org.metawidget.widgetbuilder.impl.BaseWidgetBuilder;

/**
 * @author Richard Kennard
 */

public class BaseWidgetBuilderTest
	extends TestCase
{
	//
	// Public methods
	//

	public void testBaseWidgetBuilder()
		throws Exception
	{
		DummyWidgetBuilder widgetBuilder = new DummyWidgetBuilder();
		Map<String, String> attributes = CollectionUtils.newHashMap();

		// buildActiveWidget

		try
		{
			widgetBuilder.buildWidget( null, attributes, null );
			assertTrue( false );
		}
		catch ( WidgetBuilderException e )
		{
			assertTrue( "buildActiveWidget called".equals( e.getCause().getMessage() ) );
		}

		// buildReadOnlyWidget

		attributes.put( READ_ONLY, TRUE );
		assertTrue( null == widgetBuilder.buildWidget( null, attributes, null ));
		attributes.remove( READ_ONLY );

		attributes.put( NO_SETTER, TRUE );
		assertTrue( null == widgetBuilder.buildWidget( null, attributes, null ));
	}

	//
	// Inner class
	//

	class DummyWidgetBuilder
		extends BaseWidgetBuilder<Object, Object>
	{
		//
		// Protected methods
		//

		@Override
		protected Object buildActiveWidget( String elementName, Map<String, String> attributes, Object metawidget )
			throws Exception
		{
			throw new RuntimeException( "buildActiveWidget called" );
		}
	}
}