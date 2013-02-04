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

import java.util.Scanner;

/**
 * @author Lars P&ouml;tter
 * (<a href=mailto:Lars_Poetter@gmx.de>Lars_Poetter@gmx.de</a>)
 */
public abstract class Variable
{
    protected String Name;
    protected int numBits;
    protected Scanner sc;

    protected Variable()
    {
    }

    protected Variable(final String line)
    {
        sc = new Scanner(line);
        sc.useDelimiter(",");
        sc.next(); // ignore type
        Name = sc.next();
        numBits = sc.nextInt();
    }

    public String save()
    {
        String extra = getExtraConfiguration();
        if(null == extra)
        {
            extra = "";
        }
        return getTypeName() + "," + Name + "," + numBits + extra;
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

    protected String getExtraConfiguration()
    {
        return null;
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
