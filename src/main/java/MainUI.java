
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
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainUI window = new MainUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainUI() {
        try {
            UIManager.setLookAndFeel( new FlatDarculaLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setBounds(500, 100, 690, 450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 690, 450);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JButton btnStartSandse = new JButton("Start SAnD_SE");
        btnStartSandse.setBounds(504, 398, 174, 40);
        panel.add(btnStartSandse);

        JLabel label = new JLabel(new ImageIcon(this.getClass().getResource("/close.png")));
        label.setAlignmentY(0.0f);
        label.setBounds(664, 8, 18, 18);
        panel.add(label);

        JLabel lblSand = new JLabel("SAnD");
        lblSand.setFont(new Font("Font Awesome 5 Brands Regular", Font.PLAIN, 24));
        lblSand.setHorizontalAlignment(SwingConstants.CENTER);
        lblSand.setBounds(310, 8, 70, 28);
        panel.add(lblSand);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(new Color(86, 86, 87), 2, true));
        panel_1.setBounds(14, 40, 227, 181);
        panel.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblMysql = new JLabel("MySQL");
        lblMysql.setVerticalAlignment(SwingConstants.TOP);
        lblMysql.setHorizontalAlignment(SwingConstants.LEFT);
        lblMysql.setBounds(12, 12, 70, 28);
        panel_1.add(lblMysql);
        lblMysql.setFont(new Font("Dialog", Font.BOLD, 15));

        JLabel lblMysqlAddress = new JLabel("Ip :");
        lblMysqlAddress.setBounds(12, 40, 81, 25);
        panel_1.add(lblMysqlAddress);

        ip_textField = new JTextField();
        ip_textField.setText("34.71.19.156:3306");
        ip_textField.setBounds(100, 40, 115, 25);
        panel_1.add(ip_textField);
        ip_textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("Username :");
        lblNewLabel.setBounds(12, 75, 81, 25);
        panel_1.add(lblNewLabel);

        username_textField = new JTextField();
        username_textField.setBounds(100, 75, 115, 25);
        panel_1.add(username_textField);
        username_textField.setColumns(10);

        JLabel lblMysqlPassword = new JLabel("Password :");
        lblMysqlPassword.setBounds(12, 110, 81, 25);
        panel_1.add(lblMysqlPassword);

        password_textField = new JTextField();
        password_textField.setBounds(100, 110, 115, 25);
        panel_1.add(password_textField);
        password_textField.setColumns(10);

        JLabel lblDbName = new JLabel("Db Name:");
        lblDbName.setBounds(12, 145, 81, 25);
        panel_1.add(lblDbName);

        db_name_textField = new JTextField();
        db_name_textField.setBounds(100, 145, 115, 25);
        panel_1.add(db_name_textField);
        db_name_textField.setColumns(10);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new LineBorder(new Color(86, 86, 87), 2, true));
        panel_2.setBounds(253, 40, 227, 77);
        panel.add(panel_2);
        panel_2.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("SE Port");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
        lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 15));
        lblNewLabel_1.setBounds(12, 12, 70, 28);
        panel_2.add(lblNewLabel_1);

        JLabel lblTcpPort = new JLabel("Tcp port:");
        lblTcpPort.setBounds(12, 40, 81, 25);
        panel_2.add(lblTcpPort);

        port_textField = new JTextField();
        port_textField.setBounds(100, 40, 115, 25);
        panel_2.add(port_textField);
        port_textField.setColumns(10);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new LineBorder(new Color(86, 86, 87), 2, true));
        panel_3.setBounds(492, 38, 182, 112);
        panel.add(panel_3);
        panel_3.setLayout(null);

        JLabel lblSystemStats = new JLabel("Stats");
        lblSystemStats.setFont(new Font("Dialog", Font.BOLD, 15));
        lblSystemStats.setVerticalAlignment(SwingConstants.TOP);
        lblSystemStats.setHorizontalAlignment(SwingConstants.LEFT);
        lblSystemStats.setBounds(12, 12, 70, 28);
        panel_3.add(lblSystemStats);

        JLabel lblCpu = new JLabel("Cpu usage:");
        lblCpu.setBounds(12, 40, 81, 25);
        panel_3.add(lblCpu);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(100, 40, 70, 25);
        panel_3.add(progressBar);

        JLabel lblRamUsage = new JLabel("Memory:");
        lblRamUsage.setBounds(12, 75, 81, 25);
        panel_3.add(lblRamUsage);

        JProgressBar progressBar_1 = new JProgressBar();
        progressBar_1.setBounds(100, 75, 70, 25);
        panel_3.add(progressBar_1);

        JPanel panel_4 = new JPanel();
        panel_4.setBorder(new LineBorder(new Color(86, 86, 87), 2, true));
        panel_4.setBounds(14, 233, 466, 205);
        panel.add(panel_4);
        panel_4.setLayout(null);

        JLabel lblLog = new JLabel("Log");
        lblLog.setVerticalAlignment(SwingConstants.TOP);
        lblLog.setHorizontalAlignment(SwingConstants.LEFT);
        lblLog.setFont(new Font("Dialog", Font.BOLD, 15));
        lblLog.setBounds(12, 12, 70, 28);
        panel_4.add(lblLog);

        JTextArea textArea = new JTextArea();
        textArea.setBorder(null);
        textArea.setLineWrap(true);
        textArea.setBounds(3, 40, 460, 162);
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        scrollPane.setAlignmentY(0.0f);
        scrollPane.setAlignmentX(0.0f);
        scrollPane.setFocusable(false);
        scrollPane.setRequestFocusEnabled(false);
        scrollPane.setBounds(3, 40, 460, 162);
        scrollPane.getHorizontalScrollBar().setBorder(null);
        scrollPane.getVerticalScrollBar().setBorder(null);
        panel_4.add(scrollPane);

        label.addMouseListener((MouseListener) new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent arg0) {
                // TODO Auto-generated method stub
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub

            }

        });

        btnStartSandse.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if(btnStartSandse.getText().equals("Start SAnD_SE")) {
                    String ip_address = ip_textField.getText();
                    String username = username_textField.getText();
                    String password = password_textField.getText();
                    String dbName = db_name_textField.getText();

                    if(ip_address.length()>0 && username.length()>0 & password.length()>0 & dbName.length()>0) {
                        new ConnectionManager(textArea, ip_address, username, password, dbName);
                        se_REquestListener = new SE_RequestListener(textArea, btnStartSandse);
                        se_REquestListener.start();
                    }else {
                        textArea.append("\nInvalid mysql server info!");
                    }

                }else {
                    if(se_REquestListener!=null) {
                        se_REquestListener.stopListening();
                    }
                }
            }
        });
    }
}
