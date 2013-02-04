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

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import de.ing_poetter.binview.FormatWindow;

/**
 * @author Lars P&ouml;tter
 * (<a href=mailto:Lars_Poetter@gmx.de>Lars_Poetter@gmx.de</a>)
 */
public class CreateVariableWindow extends JFrame implements ActionListener
{
    private final FormatWindow fw;
    private final static String AC_CLOSE = "close";
    private final static String AC_ADD = "add";

    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JTextField NameField = new JTextField(20);
    final JTextField bitNumValue = new JTextField(20);
    final JTextField signValue = new JTextField(20);

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param fw
     *
     */
    public CreateVariableWindow(final FormatWindow fw)
    {
        this.fw = fw;
        this.setTitle("Binary View - create new Variable");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.PAGE_AXIS));
        root.add(createVariableSpecificArea());
        root.add(createButtonArea());
        this.add(root);
        this.pack();
        this.setVisible(true);
    }

    private  JPanel createVariableSpecificArea()
    {
        final JPanel root  = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.PAGE_AXIS));
        final JPanel VariableName = new JPanel(new FlowLayout());
        final JLabel NameLabel = new JLabel("Variable Name : ");
        VariableName.add(NameLabel);
        VariableName.add(NameField);
        root.add(VariableName);
        tabbedPane.addTab("bool", null, getBoolPane(), "true/false");
        tabbedPane.addTab("int", null, getIntPane(), "..-3,-2,-1,0,1,2,3..");
        root.add(tabbedPane);
        return root;
    }

    private Component getIntPane()
    {
        final JPanel res = new JPanel();
        res.setLayout(new BoxLayout(res, BoxLayout.PAGE_AXIS));

        final JPanel setBits = new JPanel(new FlowLayout());
        final JLabel bitsLabel = new JLabel("number bits");
        setBits.add(bitsLabel);
        setBits.add(bitNumValue);
        res.add(setBits);

        final JPanel setSign = new JPanel(new FlowLayout());
        final JLabel signLabel = new JLabel("signed");
        setSign.add(signLabel);
        setSign.add(signValue);
        res.add(setSign);

        return res;
    }

    private Component getBoolPane()
    {
        final JPanel res = new JPanel();
        res.setLayout(new BoxLayout(res, BoxLayout.PAGE_AXIS));
        return res;
    }

    private int getIntValue(final String number)
    {
        int res = 0;
        try
        {
            res = Integer.decode(number);
        }
        catch(final NumberFormatException e)
        {
            System.out.println("NumberFormatException when converting : -" + number + "-");
        }
        return res;
    }


    public Variable cretaeVariableFromCurrentActiveSettings()
    {
        final int idx = tabbedPane.getSelectedIndex();
        Variable v = null;
        switch(idx)
        {
        case 0: v = BooleanVariable.getVariable(NameField.getText());
            break;
        case 1: v = IntegerVariable.getVariable(NameField.getText(),
                                                getIntValue(bitNumValue.getText()),
                                                Boolean.parseBoolean(signValue.getText()));
            break;
        }
        return v;
    }

    private JPanel createButtonArea()
    {
        final JPanel control = new JPanel();
        control.setLayout(new BoxLayout(control, BoxLayout.LINE_AXIS));

        final JButton loadFormat = new JButton("close");
        loadFormat.setActionCommand(AC_CLOSE);
        loadFormat.addActionListener(this);
        loadFormat.setEnabled(true);
        control.add(loadFormat);

        final JButton saveFormat = new JButton("add");
        saveFormat.setActionCommand(AC_ADD);
        saveFormat.addActionListener(this);
        saveFormat.setEnabled(true);
        control.add(saveFormat);


        return control;
    }

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        if(true == AC_CLOSE.equals(e.getActionCommand()))
        {
            this.setVisible(false);
            this.dispose();
        }
        else if(true == AC_ADD.equals(e.getActionCommand()))
        {
            final Variable v = cretaeVariableFromCurrentActiveSettings();
            if(null != v)
            {
                fw.addVariable(v);
            }
        }
    }

}
