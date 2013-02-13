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
public class IntegerVariable extends Variable
{
    private final boolean signed;

    /**
     * @param curVar
     *
     */
    public IntegerVariable(final Element variable)
    {
        super(variable);
        signed = Boolean.parseBoolean(variable.getChildText("signed"));
    }

    private IntegerVariable(final String name, final int bitNum, final boolean signed)
    {
        super();
        this.Name = name;
        this.numBits = bitNum;
        this.signed = signed;
    }

    @Override
    public String getTypeName()
    {
        return "int";
    }

    @Override
    public String describeValue(final boolean[] data, final int requestedNameLength)
    {
        long val = 0;
        for(int i = 0; i < data.length; i++)
        {
            if(true == data[i])
            {
                val = val + (1 <<((numBits-1) -i));
            }
            // else 0 * something = nothing
        }
        if(true == signed)
        {
            // TODO
        }
        final StringBuffer sb = CreateNameSecription();
        final int spaces = requestedNameLength - sb.length();
        for(int i = 0; i < spaces; i++)
        {
            sb.append(" ");
        }
        return sb.toString() + ": " + val;
    }

    @Override
    protected Element[] getExtraConfiguration()
    {
        final Element[] res = new Element[1];
        final Element signEle = new Element("signed");
        signEle.setText("" + signed);
        res[0] = signEle;
        return res;
    }

    public static Variable getVariable(final String name,final int bitNum, final boolean isSigned)
    {
        if(false == isNameValid(name))
        {
            return null;
        }
        System.out.println("Creating Int Variable with " + bitNum + " bits and signed of " + isSigned + " !");
        return new IntegerVariable(name, bitNum, isSigned);
    }

    private StringBuffer CreateNameSecription()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append(Name);
        sb.append(" (" + numBits + "bit ");
        if(true == signed)
        {
            sb.append("signed)");
        }
        else
        {
            sb.append("unsigned)");
        }
        return sb;
    }

    @Override
    public int getDescriptionLength()
    {
        final StringBuffer sb = CreateNameSecription();
        return sb.length();
    }

}
