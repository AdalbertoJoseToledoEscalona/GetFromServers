package vista;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import modelo.utils.beans.SpinnerEditor;
import modelo.utils.beans.SpinnerRenderer;
   
public class JSpinnerTableExample extends JFrame {
  
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public JSpinnerTableExample(){
    super("JSpinnerTable Example");
     
    SpinnerNumberModel spinnerModel1 = new SpinnerNumberModel(10.0, -500.0, 500.0, .5);
    SpinnerDateModel spinnerModel2 = new SpinnerDateModel();
  
    DefaultTableModel dtm = new DefaultTableModel();
    dtm.setDataVector(new Object[][]{{ spinnerModel1, "JSpinner1"},
                                     { spinnerModel2, "JSpinner2" }},
                      new Object[]{"JSpinner","String"});
                      
    JTable table = new JTable(dtm);
    table.getColumn("JSpinner").setCellRenderer(new SpinnerRenderer());
    table.getColumn("JSpinner").setCellEditor(new SpinnerEditor());
  
    table.setRowHeight(20);
    JScrollPane scroll = new JScrollPane(table);
    getContentPane().add(scroll);
  
    setSize( 400, 100 );
    setVisible(true);
  }
  
  public static void main(String[] args) {
    JSpinnerTableExample frame = new JSpinnerTableExample();
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
  }
}
  
/*class SpinnerRenderer 
}*/
   
/*class SpinnerEditor 
}*/