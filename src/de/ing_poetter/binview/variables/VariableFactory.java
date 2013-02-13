/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version 2
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see <http://www.gnu.org/licenses/>
 *
 */
package de.ing_poetter.binview.variables;

import org.jdom2.Element;

/**
 * @author Lars P&ouml;tter
 * (<a href=mailto:Lars_Poetter@gmx.de>Lars_Poetter@gmx.de</a>)
 */
public class VariableFactory
{
    /**
     *
     */
    private VariableFactory()
    {
    }

    public static Variable createVariableFrom(final Element curVar)
    {
        if(null == curVar)
        {
            return null;
        }
        final String type = curVar.getName();
        if(true == "bool".equals(type))
        {
            return new BooleanVariable(curVar);
        }
        else if(true == "int".equals(type))
        {
            return new IntegerVariable(curVar);
        }
        else
        {
            return null;
        }
    }

}
