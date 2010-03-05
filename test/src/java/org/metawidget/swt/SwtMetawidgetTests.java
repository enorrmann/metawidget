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

package org.metawidget.swt;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.metawidget.swt.layout.FillLayoutTest;
import org.metawidget.swt.layout.RowLayoutTest;
import org.metawidget.swt.widgetbuilder.SwtWidgetBuilderTest;
import org.metawidget.swt.widgetprocessor.binding.reflection.ReflectionBindingProcessorTest;

/**
 * @author Richard Kennard
 */

public class SwtMetawidgetTests
	extends TestCase
{
	//
	// Public statics
	//

	public static Test suite()
	{
		TestSuite suite = new TestSuite( "SwtMetawidget Tests" );
		suite.addTestSuite( FillLayoutTest.class );
		suite.addTestSuite( ReflectionBindingProcessorTest.class );
		suite.addTestSuite( RowLayoutTest.class );
		suite.addTestSuite( SwtAllWidgetsTest.class );
		suite.addTestSuite( SwtWidgetBuilderTest.class );

		return suite;
	}

	public final static Shell	TEST_SHELL	= new Shell( new Display(), SWT.NONE );
}