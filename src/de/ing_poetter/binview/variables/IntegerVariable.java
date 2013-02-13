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
    public final static String SIGNED_ELEMENT_NAME = "signed";
    private final boolean signed;
    public final static String VALUE_PRESENTATION_ELEMENT_NAME = "valuePresentation";
    private String valuePresenation;

    /**
     * @param curVar
     *
     */
    public IntegerVariable(final Element variable)
    {
        super(variable);
        signed = Boolean.parseBoolean(variable.getChildText(SIGNED_ELEMENT_NAME));
        valuePresenation = variable.getChildText(VALUE_PRESENTATION_ELEMENT_NAME);
        checkVariablePresentation();
    }

    private IntegerVariable(final String name,
                             final int bitNum,
                             final boolean signed,
                             final String valuePresentation)
    {
        super();
        this.Name = name;
        this.numBits = bitNum;
        this.signed = signed;
        this.valuePresenation = valuePresentation;
        checkVariablePresentation();
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
        final StringBuffer sb = createNameDescription();
        final int spaces = requestedNameLength - sb.length();
        for(int i = 0; i < spaces; i++)
        {
            sb.append(" ");
        }
        return sb.toString() + ": " +String.format(valuePresenation, val);
        // return sb.toString() + ": " + val;
    }

    @Override
    protected Element[] getExtraConfiguration()
    {
        final Element[] res = new Element[2];
        final Element signEle = new Element(SIGNED_ELEMENT_NAME);
        signEle.setText("" + signed);
        res[0] = signEle;

        final Element descrEle = new Element(VALUE_PRESENTATION_ELEMENT_NAME);
        descrEle.setText(valuePresenation);
        res[1] = descrEle;
        return res;
    }

    public static Variable getVariable(final String name,
                                        final int bitNum,
                                        final boolean isSigned,
                                        final String valuePresentation)
    {
        if(false == isNameValid(name))
        {
            return null;
        }
        return new IntegerVariable(name, bitNum, isSigned, valuePresentation);
    }

    private void checkVariablePresentation()
    {
        if(null == valuePresenation)
        {
            valuePresenation = "%d";
        }
        else
        {
            if(2 > valuePresenation.length())
            {
                valuePresenation = "%d";
            }
        }
    }
    private StringBuffer createNameDescription()
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
        final StringBuffer sb = createNameDescription();
        return sb.length();
    }

}
