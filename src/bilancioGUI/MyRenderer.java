package bilancioGUI;

import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.JLabel;
import javax.swing.table.TableCellRenderer;

public class MyRenderer extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        Color defaultColor = Color.WHITE;
        //Cells are by default rendered as a JLabel.
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        //Get the status for the current row.
        BilancioTableModel tableModel = (BilancioTableModel) table.getModel();
        if(tableModel.isHighlighted(row, col))
            l.setBackground(Color.GREEN);
        else
            l.setBackground(defaultColor);

        //Return the JLabel which renders the cell.
        return l;

    }
}
