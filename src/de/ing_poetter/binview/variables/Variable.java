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
public abstract class Variable
{
    protected String Name;
    protected int numBits;

    protected Variable()
    {
    }

    protected Variable(final Element variable)
    {
        Name = variable.getChildText("Name");
        numBits = Integer.parseInt(variable.getChildText("numberOfBits"));
    }

    public Element save()
    {
        final Element res = new Element(getTypeName());
        // Name
        final Element nameEle = new Element("Name");
        nameEle.setText(Name);
        res.addContent(nameEle);
        // Number of Bits
        final Element numBitsEle = new Element("numberOfBits");
        numBitsEle.setText("" + numBits);
        res.addContent(numBitsEle);
        // Extra Configuration
        final Element[] extra = getExtraConfiguration();
        for(int i = 0; i < extra.length; i++)
        {
            res.addContent(extra[i]);
        }
        return res;
    }

    public int getNumberBits()
    {
        return numBits;
    }

    public void setName(final String Name)
    {
        this.Name = Name;
    }

    public String getName()
    {
        return Name;
    }

    public abstract String getTypeName();

    protected Element[] getExtraConfiguration()
    {
        return new Element[0];
    }

    public abstract String describeValue(boolean[] data, int requestedNameLength);

    protected static boolean isNameValid(final String name)
    {
        if(null == name)
        {
            return false;
        }
        if(1 > name.length())
        {
            return false;
        }
        return true;
    }

    public abstract int getDescriptionLength();
}
