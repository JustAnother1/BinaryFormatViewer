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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/**
 * @author Lars P&ouml;tter
 * (<a href=mailto:Lars_Poetter@gmx.de>Lars_Poetter@gmx.de</a>)
 */
public class BinaryViewWindow extends JFrame implements ActionListener, DocumentListener, MainWindow
{
    private final static String AC_CREATE_FORMAT = "createFormat";
    private final static String AC_LOAD_FORMAT = "loadFormat";

    private BinaryFormat format = null;
    private final JTextArea dataOut = new JTextArea(10, 50);
    private final Font font = new Font("Monospaced", Font.PLAIN, 14);
    private final JTextArea dataIn = new JTextArea(4, 35);
    private final JFileChooser fc = new JFileChooser();
    final JButton createFormat = new JButton("create Format");

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public BinaryViewWindow()
    {
        this.setTitle("Binary View");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.PAGE_AXIS));
        root.add(createInputArea());
        root.add(createOutputArea());
        this.add(root);
        this.pack();
        this.setVisible(true);

        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        dataOut.setFont(font);
    }

    private JPanel createControlArea()
    {
        final JPanel control = new JPanel();
        control.setLayout(new BoxLayout(control, BoxLayout.PAGE_AXIS));

        createFormat.setActionCommand(AC_CREATE_FORMAT);
        createFormat.addActionListener(this);
        createFormat.setEnabled(true);
        createFormat.setAlignmentX(RIGHT_ALIGNMENT);
        control.add(createFormat);

        final JButton loadFormat = new JButton("    load Format");
        loadFormat.setActionCommand(AC_LOAD_FORMAT);
        loadFormat.addActionListener(this);
        loadFormat.setEnabled(true);
        loadFormat.setAlignmentX(RIGHT_ALIGNMENT);
        control.add(loadFormat);

        return control;
    }

    private JPanel createInputArea()
    {
        final JPanel input = new JPanel();
        input.setLayout(new FlowLayout());

        dataIn.append("add Binary Data in Hex Form (00 FF FAFA)");
        dataIn.getDocument().addDocumentListener(this);
        final JScrollPane inScrollPane = new JScrollPane(dataIn);

        input.add(inScrollPane);
        input.add(createControlArea());
        return input;
    }

    private JPanel createOutputArea()
    {
        final JPanel output = new JPanel(new BorderLayout());
        final JScrollPane outScrollPane = new JScrollPane(dataOut);
        dataOut.setEditable(false);
        output.add(outScrollPane, BorderLayout.CENTER);
        return output;
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        if(true == AC_CREATE_FORMAT.equals(e.getActionCommand()))
        {
            // show window to create new Format.
            @SuppressWarnings("unused")
            final CreateFormatWindow cfw = new CreateFormatWindow(this, format);
        }
        else if(true == AC_LOAD_FORMAT.equals(e.getActionCommand()))
        {
            // show File chooser dialog to select Format File
            final int returnVal = fc.showOpenDialog(this);
            if(JFileChooser.APPROVE_OPTION == returnVal)
            {
                final File f = fc.getSelectedFile();
                format = BinaryFormat.loadFromFile(f);
            }
            // try to format the data now
            formatData();
        }
        // else ignore event
    }

    private void formatData()
    {
        if(null == format)
        {
            return;
        }
        // else ...
        final String userData = dataIn.getText();
        final String res = format.aplyTo(userData);
        dataOut.setText(res);
    }

    @Override
    public void insertUpdate(final DocumentEvent e)
    {
        formatData();
    }

    @Override
    public void removeUpdate(final DocumentEvent e)
    {
        formatData();
    }

    @Override
    public void changedUpdate(final DocumentEvent e)
    {
        formatData();
    }

    @Override
    public void setFormat(final BinaryFormat format)
    {
        this.format = format;
        createFormat.setText("edit Format");
    }
}
