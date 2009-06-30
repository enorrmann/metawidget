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

package org.metawidget.faces.component.html;

import java.util.Map;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.metawidget.faces.component.UIMetawidget;

/**
 * Metawidget for Java Server Faces environments.
 *
 * @author Richard Kennard
 */

public class HtmlMetawidget
	extends UIMetawidget
{
	//
	// Private members
	//

	private boolean	mCreateHiddenFields;

	private String	mStyle;

	private String	mStyleClass;

	//
	// Public methods
	//

	/**
	 * Whether to create hidden HTML input fields for hidden values.
	 * <p>
	 * Defaults to <code>false</code>, as passing values via
	 * <code>&lt;input type="hidden"&gt;</code> tags is a potential security risk: they can be
	 * modified by malicious clients before being returned to the server.
	 */

	public void setCreateHiddenFields( boolean createHiddenFields )
	{
		mCreateHiddenFields = createHiddenFields;
	}

	public boolean isCreateHiddenFields()
	{
		return mCreateHiddenFields;
	}

	public String getStyle()
	{
		return mStyle;
	}

	public void setStyle( String style )
	{
		mStyle = style;
	}

	public String getStyleClass()
	{
		return mStyleClass;
	}

	public void setStyleClass( String styleClass )
	{
		mStyleClass = styleClass;
	}

	@Override
	public Object saveState( FacesContext context )
	{
		Object values[] = new Object[4];
		values[0] = super.saveState( context );
		values[1] = mCreateHiddenFields;
		values[2] = mStyle;
		values[3] = mStyleClass;

		return values;
	}

	@Override
	public void restoreState( FacesContext context, Object state )
	{
		Object values[] = (Object[]) state;
		super.restoreState( context, values[0] );

		mCreateHiddenFields = (Boolean) values[1];
		mStyle = (String) values[2];
		mStyleClass = (String) values[3];
	}

	//
	// Protected methods
	//

	@Override
	protected String getDefaultConfiguration()
	{
		return "org/metawidget/faces/component/html/metawidget-html-default.xml";
	}

	@Override
	protected UIComponent afterBuildWidget( UIComponent component, Map<String, String> attributes )
		throws Exception
	{
		// Apply CSS attributes

		if ( component != null )
		{
			// Note: this applies the styles to UIStubs too. In practice, this seemed to give
			// more 'expected' behaviour than drilling into the UIStubs and applying the styles
			// to all their subcomponents

			Map<String, Object> componentAttributes = component.getAttributes();

			if ( mStyle != null && !componentAttributes.containsKey( "style" ) )
				componentAttributes.put( "style", mStyle );

			if ( mStyleClass != null && !componentAttributes.containsKey( "styleClass" ) )
				componentAttributes.put( "styleClass", mStyleClass );
		}

		return super.afterBuildWidget( component, attributes );
	}

	/**
	 * Create a sub-Metawidget.
	 * <p>
	 * Usually, clients will want to create a sub-Metawidget using the same subclass as themselves.
	 * To be 'proper' in JSF, though, we should go via <code>application.createComponent</code>.
	 * Unfortunately a UIComponent does not know its own component name, so subclasses must override
	 * this method.
	 */

	@Override
	protected HtmlMetawidget buildNestedMetawidget( Map<String, String> attributes )
	{
		Application application = getFacesContext().getApplication();
		return (HtmlMetawidget) application.createComponent( "org.metawidget.HtmlMetawidget" );
	}

	/**
	 * Overriden to carry the hidden fields flag and other attributes into the nested Metawidget.
	 */

	@Override
	protected void initNestedMetawidget( UIMetawidget metawidget, Map<String, String> attributes )
		throws Exception
	{
		super.initNestedMetawidget( metawidget, attributes );

		// Attributes

		HtmlMetawidget htmlMetawidget = (HtmlMetawidget) metawidget;

		htmlMetawidget.setCreateHiddenFields( mCreateHiddenFields );
		htmlMetawidget.setStyle( mStyle );
		htmlMetawidget.setStyleClass( mStyle );
	}
}
