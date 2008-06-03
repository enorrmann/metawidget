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

package org.metawidget.inspector.spring;

import java.util.Map;

import org.metawidget.inspector.impl.AbstractPropertyInspector;
import org.metawidget.inspector.impl.AbstractPropertyInspectorConfig;
import org.metawidget.inspector.impl.propertystyle.Property;
import org.metawidget.util.CollectionUtils;

/**
 * Inspects annotations defined by Metawidget's Spring support (declared in this same package).
 *
 * @author Richard Kennard
 */

public class SpringAnnotationInspector
	extends AbstractPropertyInspector
{
	//
	//
	// Constructor
	//
	//

	public SpringAnnotationInspector()
	{
		super( new AbstractPropertyInspectorConfig() );
	}

	public SpringAnnotationInspector( AbstractPropertyInspectorConfig config )
	{
		super( config );
	}

	//
	//
	// Protected methods
	//
	//

	@Override
	protected Map<String, String> inspect( Property property, Object toInspect )
		throws Exception
	{
		Map<String, String> attributes = CollectionUtils.newHashMap();

		// SpringLookup

		UiSpringLookup springLookup = property.getAnnotation( UiSpringLookup.class );

		if ( springLookup != null )
		{
			if ( !springLookup.onlyIfNull() || property.read( toInspect ) == null )
			{
				attributes.put( "spring-lookup", springLookup.value() );
			}
		}

		return attributes;
	}
}
