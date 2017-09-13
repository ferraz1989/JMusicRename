package jMusicRename;

import java.io.File;

import javax.swing.JFileChooser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Main {

  protected Shell shell;
  private Text textFolder;

  /**
   * @wbp.nonvisual location=167,207
   */

  /**
   * @wbp.nonvisual location=160,237
   */

  /**
   * @wbp.nonvisual location=150,137
   */

  /**
   * Launch the application.
   * 
   * @param args
   */
  public static void main(String[] args) {
    try {
      Main window = new Main();
      window.open();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Open the window.
   */
  public void open() {
    Display display = Display.getDefault();
    createContents();
    shell.open();
    shell.layout();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
  }

  /**
   * Create contents of the window.
   */
  protected void createContents() {
    shell = new Shell();
    shell.setSize(450, 300);
    shell.setText("SWT Application");

    Button btnFolder = new Button(shell, SWT.NONE);

    btnFolder.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseDown(MouseEvent e) {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int status = chooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
          File selectedFile = chooser.getSelectedFile();
          String path = selectedFile.getPath();
          textFolder.setText(path);
        }
      }

    });
    btnFolder.setBounds(409, 45, 31, 29);

    textFolder = new Text(shell, SWT.BORDER);
    textFolder.setEnabled(false);
    textFolder.setEditable(false);
    textFolder.setBounds(10, 45, 393, 29);

    Button btnProcess = new Button(shell, SWT.NONE);
    btnProcess.addSelectionListener(new SelectionAdapter() {
      @Override
      public void widgetSelected(SelectionEvent e) {
        Control.renameMusic(textFolder.getText());
      }
    });
    btnProcess.setBounds(153, 145, 131, 29);
    btnProcess.setText("Rename music");

  }
}
