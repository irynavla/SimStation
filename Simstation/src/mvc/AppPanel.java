package mvc;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


// AppPanel is the MVC controller
public class AppPanel extends JPanel implements Subscriber, ActionListener  {


    protected Model model;
    protected AppFactory factory;
    protected View view;
    protected JPanel controlPanel;
    private JFrame frame;
    public static int FRAME_WIDTH = 800;
    public static int FRAME_HEIGHT = 600;


    public AppPanel(AppFactory factory) {


        // initialize fields here
        this.factory = factory;
        model = this.factory.makeModel();
        view = this.factory.makeView(model);
        controlPanel = new ControlPanel();
        this.setLayout(new GridLayout());
        this.add(controlPanel);
        this.add(view,BorderLayout.CENTER);
        model.subscribe(this);
        frame = new SafeFrame();
        Container cp = frame.getContentPane();
        cp.add(this);
        frame.setJMenuBar(createMenuBar());
        frame.setTitle(factory.getTitle());
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }


    public void display() { frame.setVisible(true); }


    public void update() {repaint();}


    public Model getModel() { return model; }


    // called by file/open and file/new
    public void setModel(Model newModel) {
        this.model.unsubscribe(this);
        this.model = newModel;
        this.model.subscribe(this);
        // view must also unsubscribe then resubscribe:
        view.setModel(this.model);
        model.changed();
    }
//    public void setModel(Model m) {
//        model = m;
//        view.setModel(m);
//        model.changed();
//    }


    protected JMenuBar createMenuBar() {
        JMenuBar result = new JMenuBar();
        // add file, edit, and help menus
        JMenu fileMenu =
                Utilities.makeMenu("File", new String[] {"New",  "Save", "SaveAs", "Open", "Quit"}, this);
        result.add(fileMenu);


        JMenu editMenu =
                Utilities.makeMenu("Edit", factory.getEditCommands(), this);
        result.add(editMenu);


        JMenu helpMenu =
                Utilities.makeMenu("Help", new String[] {"About", "Help"}, this);
        result.add(helpMenu);


        return result;
    }


    public void actionPerformed(ActionEvent e) {
        String cmmd = e.getActionCommand();
        System.out.println(cmmd);
        try {
            switch (cmmd) {
                case "Save" -> {
                    try {
                        Utilities.save(model, true);
                    } catch (Exception ex) {
                        Utilities.inform("ERROR: Failed to save file.");
                    }
                }
                case "Open" -> {
                    try {
                        Model newModel = Utilities.open(model);
                        if (newModel != null) setModel(newModel);
                    } catch (Exception ex) {
                        Utilities.inform("ERROR: Didn't load any file, potentially due to an error.");
                    }
                }
                case "New" -> {
                    Utilities.saveChanges(model);
                    setModel(factory.makeModel());


                    // needed cuz setModel sets to true:
                    model.setUnsavedChanges(false);
                }
                case "Quit" -> System.exit(0);
                case "About" -> Utilities.inform(factory.about());
                case "Help" -> Utilities.inform(factory.getHelp());
                default -> factory.makeEditCommand(model, cmmd, this).execute();
            }
        } catch (Exception ex) {
            Utilities.error(ex);
        }
    }


    public class ControlPanel extends JPanel {
        public ControlPanel() {


        }
    }


    protected void handleException(Exception e) {
        Utilities.error(e);
    }


}
