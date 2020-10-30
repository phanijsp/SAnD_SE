
import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.sql.Connection;


import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JCheckBoxMenuItem;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import javax.swing.border.EtchedBorder;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainUI {

    private JFrame frame;
    SE_RequestListener se_REquestListener = null;
    private JTextField ip_textField;
    private JTextField username_textField;
    private JTextField password_textField;
    private JTextField db_name_textField;
    private JTextField port_textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        new ConnectionManager( "localhost:3306", "vlad", "@Aditya4334", "keywords");
        SE_RequestListener se_REquestListener = new SE_RequestListener();
        se_REquestListener.start();


    }

    /**
     * Create the application.
     */

}
