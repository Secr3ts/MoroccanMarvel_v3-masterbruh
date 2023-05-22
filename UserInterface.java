import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import javax.swing.JButton;

/**
 * This class implements a simple graphical user interface with a 
 * text entry area, a text output area and an optional image.
 * 
 * @author Michael Kolling
 * @version 1.0 (Jan 2003) DB edited (2023)
 */
public class UserInterface implements ActionListener
{
    private GameEngine aEngine;
    private JFrame     aMyFrame;
    private JTextField aEntryField;
    private JTextArea  aLog;
    private JLabel     aImage;
    private JButton    aButtonUp;
    private JButton    aButtonDown;
    private JButton    aButtonWest;
    private JButton    aButtonNorth;
    private JButton    aButtonEast;
    private JButton    aButtonSouth;
    private JButton    aButtonQuit;
    private JButton    aButtonHelp;
    private JPanel     aPanelEast;

    /**
     * Construct a UserInterface. As a parameter, a Game Engine
     * (an object processing and executing the game commands) is
     * needed.
     * 
     * @param pGameEngine  The GameEngine object implementing the game logic.
     */
    public UserInterface( final GameEngine pGameEngine )
    {
        this.aEngine = pGameEngine;
        this.createGUI();
    } // UserInterface(.)

    /**
     * Print out some text into the text area.
     */
    public void print( final String pText )
    {
        this.aLog.append( pText );
        this.aLog.setCaretPosition( this.aLog.getDocument().getLength() );
        System.out.print( pText);
    } // print(.)

    /**
     * Print out some text into the text area, followed by a line break.
     */
    public void println( final String pText )
    {
        this.print( pText + "\n" );
        System.out.println( pText);
    } // println(.)

    /**
     * Show an image file in the interface.
     */
    public void showImage( final String pImageName )
    {
        String vImagePath = "" + pImageName; // to change the directory
        URL vImageURL = this.getClass().getClassLoader().getResource( vImagePath );
        if ( vImageURL == null )
            System.out.println( "Image not found : " + vImagePath );
        else {
            ImageIcon vIcon = new ImageIcon( vImageURL);
            Image image = vIcon.getImage(); 
            image = image.getScaledInstance(512, 512, Image.SCALE_SMOOTH);
            vIcon = new ImageIcon(image);
            this.aImage.setIcon( vIcon );
            this.aMyFrame.pack();
        }
    } // showImage()

    /**
     * Enable or disable input in the entry field.
     */
    public void enable( final boolean pOnOff )
    {
        this.aEntryField.setEditable( pOnOff ); // enable/disable
        if ( pOnOff ) { // enable
            this.aEntryField.getCaret().setBlinkRate( 500 ); // cursor blink
            this.aEntryField.addActionListener( this ); // reacts to entry
        }
        else { // disable
            this.aEntryField.getCaret().setBlinkRate( 0 ); // cursor won't blink
            this.aEntryField.removeActionListener( this ); // won't react to entry
        }
    } // enable(.)

    /**
     * Set up graphical user interface.
     */
    private void createGUI()
    {
        this.aPanelEast = new JPanel();
        this.aPanelEast.setLayout( new GridLayout( 3, 3 ) );
        this.aMyFrame = new JFrame( "Moroccan Marvel" ); // Game title
        this.aEntryField = new JTextField( 34 );

        /**
         * Buttons
         */
        this.aButtonUp = new JButton( "UP" );
        this.aButtonUp.addActionListener(this);
        this.aPanelEast.add(this.aButtonUp);
        this.aButtonDown = new JButton( "DOWN" );
        this.aButtonDown.addActionListener(this);
        this.aPanelEast.add(this.aButtonDown);
        this.aButtonWest = new JButton( "WEST" );
        this.aButtonWest.addActionListener(this);
        this.aPanelEast.add(this.aButtonWest);
        this.aButtonNorth = new JButton( "NORTH" );
        this.aButtonNorth.addActionListener(this);
        this.aPanelEast.add(this.aButtonNorth);
        this.aButtonEast = new JButton( "EAST" );
        this.aButtonEast.addActionListener(this);
        this.aPanelEast.add(this.aButtonEast);
        this.aButtonSouth = new JButton( "SOUTH" );
        this.aButtonSouth.addActionListener(this);
        this.aPanelEast.add(this.aButtonSouth);
        this.aButtonQuit = new JButton( "QUIT" );
        this.aButtonQuit.addActionListener(this);
        this.aPanelEast.add(this.aButtonQuit);
        this.aButtonQuit.setBackground(Color.red);
        this.aButtonHelp = new JButton( "HELP" );
        this.aButtonHelp.addActionListener(this);
        this.aPanelEast.add(this.aButtonHelp);
        this.aButtonHelp.setBackground(Color.green);
        // Buttons

        this.aLog = new JTextArea();
        this.aLog.setEditable( false );
        JScrollPane vListScroller = new JScrollPane( this.aLog );
        vListScroller.setPreferredSize( new Dimension(200, 200) );
        vListScroller.setMinimumSize( new Dimension(100,100) );

        this.aImage = new JLabel();

        JPanel vPanel = new JPanel();
        vPanel.setLayout( new BorderLayout() ); // ==> only five places
        vPanel.add( this.aImage, BorderLayout.NORTH );
        vPanel.add( vListScroller, BorderLayout.CENTER );
        vPanel.add( this.aEntryField, BorderLayout.SOUTH );
        vPanel.add(this.aPanelEast, BorderLayout.EAST);

        this.aMyFrame.getContentPane().add( vPanel, BorderLayout.CENTER );

        // add some event listeners to some components
        this.aEntryField.addActionListener( this );
        this.aButtonQuit.addActionListener( new ActionListener() {
            public void actionPerformed(  ActionEvent e ) { System.exit( 0 ); }
        } );

        // to end program when window is closed
        this.aMyFrame.addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) { System.exit(0); }
            
        } );

        this.aMyFrame.pack();
        this.aMyFrame.setVisible( true );
        this.aEntryField.requestFocus();
    } // createGUI()

    /**
     * Actionlistener interface for entry textfield.
     * @param pE the action performed
     */
    @Override public void actionPerformed( final ActionEvent pE ) 
    {
        if(pE.getSource() == this.aButtonUp){
            this.aEngine.interpretCommand("go up");
        }else if(pE.getSource() == this.aButtonDown){
            this.aEngine.interpretCommand("go down");
        }else if(pE.getSource() == this.aButtonWest){
            this.aEngine.interpretCommand("go west");
        }else if(pE.getSource() == this.aButtonNorth){
            this.aEngine.interpretCommand("go north");
        }else if(pE.getSource() == this.aButtonEast){
            this.aEngine.interpretCommand("go east");
        }else if(pE.getSource() == this.aButtonSouth){
            this.aEngine.interpretCommand("go south");
        }else if(pE.getSource() == this.aButtonQuit){
            this.aEngine.interpretCommand("quit");
        }else if(pE.getSource() == this.aButtonHelp){
            this.aEngine.interpretCommand("help");
        }else{
        this.processCommand(); // never suppress this line
        } 
    } // actionPerformed()

    /**
     * A command has been entered in the entry field.  
     * Read the command and do whatever is necessary to process it.
     */
    private void processCommand()
    {
        String vInput = this.aEntryField.getText();
        this.aEntryField.setText( "" );

        this.aEngine.interpretCommand( vInput );
    } // processCommand()
} // UserInterface 