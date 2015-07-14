package vista;
import java.awt.Component;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractCellEditor;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
 
public class DateCellEditor extends AbstractCellEditor implements
    TableCellEditor
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
int selectedRow;
  int selectedColumn;
  JTable currentTable;
  Date currentDate;
  JSpinner spinner;
 
  protected static final String EDIT = "edit";
 
  public DateCellEditor()
  {
    Calendar calendar = Calendar.getInstance();
    Date initDate = calendar.getTime();
    System.out.println("INIT DATE++" + initDate);
    calendar.add(Calendar.YEAR, -100);
    Date earliestDate = calendar.getTime();
    calendar.add(Calendar.YEAR, 200);
    System.out.println("EARLIEST DATE++" + earliestDate);
    Date latestDate = calendar.getTime();
    System.out.println("LATEST DATE++" + latestDate);
    SpinnerDateModel dateModel = new SpinnerDateModel(initDate, earliestDate,
        latestDate, Calendar.YEAR);// ignored for user input
    spinner = new JSpinner(dateModel);
 
    spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));
  }
 
  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column)
  {
    spinner.setValue(value);
    currentTable = table;
    selectedRow = row;
    selectedColumn = column;
    return spinner;
  }
 
  public Object getCellEditorValue()
  {
    return spinner.getValue();
  }
 
  // from Sun tutorials:
  //http://java.sun.com/docs/books/tutorial/uiswing/components/table.html#editrender 
  static class DateRenderer extends DefaultTableCellRenderer
  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DateFormat formatter;
 
    public DateRenderer()
    {
      super();
    }
 
    public void setValue(Object value)
    {
      if (formatter == null)
      {
        formatter = DateFormat.getDateInstance();
      }
      setText((value == null) ? "" : formatter.format(value));
    }
  }
 
   
 
  private static void createAndShowUI()
  {
    Date[][] dates =
    {
        {new Date(Calendar.getInstance().getTimeInMillis())}
    };
    String[] titles =
    {
      "Date"
    };
    JTable table = new JTable(dates, titles);
    table.setDefaultRenderer(Object.class, new DateRenderer());
    table.setDefaultEditor(Object.class, new DateCellEditor());
     
    JFrame frame = new JFrame("DateCellEditor");
    frame.getContentPane().add(new JScrollPane(table));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
 
  public static void main(String[] args)
  {
    java.awt.EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        createAndShowUI();
      }
    });
  }
 
}