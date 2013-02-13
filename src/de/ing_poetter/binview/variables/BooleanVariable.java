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
public class BooleanVariable extends Variable
{
    /** create Variable from saved Format.
     * @param curVar
     *
     */
    public BooleanVariable(final Element variable)
    {
        super(variable);
        numBits = 1;
    }

    private BooleanVariable(final String name, final int numbits)
    {
        super();
        this.Name = name;
        this.numBits = 1;
    }

    @Override
    public String getTypeName()
    {
        return "bool";
    }

    @Override
    public String describeValue(final boolean[] data, final int requestedNameLength)
    {
        final StringBuilder sb = new StringBuilder();
        sb.append(Name);
        final int spaces = requestedNameLength -sb.length();
        for(int i = 0; i < spaces; i++)
        {
            sb.append(" ");
        }
        if(true == data[0])
        {
            return sb.toString() + ": true";
        }
        else
        {
            return sb.toString() + ": false";
        }
    }

    public static Variable getVariable(final String name)
    {
        if(true == isNameValid(name))
        {
            return new BooleanVariable(name, 1);
        }
        return null;
    }

    @Override
    public int getDescriptionLength()
    {
        return Name.length();
    }
}
