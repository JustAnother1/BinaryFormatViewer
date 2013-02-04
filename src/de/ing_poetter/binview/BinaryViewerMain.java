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
package de.ing_poetter.binview;

import java.io.IOException;

import javax.swing.SwingUtilities;

/**
 * @author Lars P&ouml;tter
 * (<a href=mailto:Lars_Poetter@gmx.de>Lars_Poetter@gmx.de</a>)
 */
public class BinaryViewerMain
{
    private BinaryFormat format = null;

    /**
     *
     */
    public BinaryViewerMain()
    {
    }

    /**
     * @param args
     */
    public static void main(final String[] args)
    {
        final BinaryViewerMain bv = new BinaryViewerMain();

        // If no commandline parameter then gui
        if(args.length < 1)
        {
            bv.startGUI();
        }
        else
        {
            boolean useStandardIn = true;
            String source = "";
            // source, target and filter from parameters
            for(int i = 0; i < args.length; i++)
            {
                if(true == args[i].startsWith("-f"))
                {
                    i++;
                    bv.loadFormat(args[i]);
                }
                else if(true == args[i].startsWith("\""))
                {
                    useStandardIn = false;
                    if(true == args[i].endsWith("\""))
                    {
                        source = args[i].substring(1, args[i].length() -2);
                    }
                    else
                    {
                        source = args[i].substring(1);
                        i++;
                        if( i < args.length)
                        {
                            do{
                                if(false == args[i].endsWith("\""))
                                {
                                    source = source + " " + args[i];
                                }
                                else
                                {
                                    source = source + " " + args[i].substring(0, args[i].length() -2);
                                }
                                i++;
                            }while((i < args.length) && (false == args[i].endsWith("\"")));
                        }
                    }
                }
                else if(true == args[i].startsWith("-h"))
                {
                    System.out.println("Usage:");
                    System.out.println("-h : this help");
                    System.out.println("-f formatdefinitionFile: use the format in formatdefinitionFile");
                    System.out.println("-o : output to this file (default: Standard out)");
                    System.out.println("\" data\" : Binaray data to view (default: Standard In)");
                    return;
                }
            }
            if(true == useStandardIn)
            {
                final StringBuilder sb = new StringBuilder();
                int c;
                try
                {
                    do{
                        c = System.in.read();
                        if(-1 != c)
                        {
                            sb.append((char) c);
                        }
                    }while(c != -1);
                }
                catch (final IOException e)
                {
                    e.printStackTrace();
                }
                source = sb.toString();
            }
            System.out.println(bv.formatTheString(source));
        }
    }

    public String formatTheString(final String source)
    {
        if(null != format)
        {
            return format.aplyTo(source);
        }
        else
        {
            // nothing to be done if there is no Format,...
            return null;
        }
    }

    private void loadFormat(final String fileName)
    {
        format = BinaryFormat.loadFromFile(fileName);
    }

    private void startGUI()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    new BinaryViewWindow();
                }
                catch(final Exception e)
                {
                    e.printStackTrace();
                    // We sometimes get a "Cannot write XdndAware property"
                    // exception in Java
                    // 1.6.0_22 in Linux. Seems to be a java bug related to the
                    // text areas.

                    // Just retry and hope for the best.
                    if(e.getMessage().equals("Cannot write XdndAware property"))
                    {
                        new BinaryViewWindow();
                    }
                }
            }
        });
    }

}
