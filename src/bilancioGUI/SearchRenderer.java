package bilancioGUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Renderer personalizzato della tabella, si occupa di evidenziare una data cella
 */
public class SearchRenderer extends DefaultTableCellRenderer{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        //impostazione colore di default
        Color defaultColor = Color.WHITE;
        
        //le celle della tabella vengono renderizzate come JLabel
        JLabel cell = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        //controlla se una cella è da evidenziare
        BilancioTableModel tableModel = (BilancioTableModel) table.getModel();
        if(tableModel.isHighlighted(row, col))
            cell.setBackground(Color.YELLOW);
        else
            if(!isSelected)
                cell.setBackground(defaultColor);

        //restituisce la JLabel renderizzata
        return cell;
    }
}
