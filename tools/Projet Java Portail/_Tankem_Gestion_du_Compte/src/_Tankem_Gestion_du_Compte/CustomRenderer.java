package _Tankem_Gestion_du_Compte;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class CustomRenderer extends DefaultTableCellRenderer 
{
private static final long serialVersionUID = 6703872492730589499L;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if(table.getValueAt(row, 1) == "WIN"){
            cellComponent.setBackground(Color.GREEN);
        } else if (table.getValueAt(row, 1) == "LOSE"){
            cellComponent.setBackground(Color.RED);
        }
        else if (table.getValueAt(row, 1) == "NULL"){
            cellComponent.setBackground(Color.GRAY);
        }
        return cellComponent;
    }
}
