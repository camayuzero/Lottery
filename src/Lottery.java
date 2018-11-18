import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;
import javax.swing.JCheckBox;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.AnnotatedWildcardType;
import javax.swing.ImageIcon;
import java.awt.Point;
import java.awt.Component;
import java.awt.Rectangle;

public class Lottery extends JFrame
{

    private JPanel contentPane;
    private JPanel panel_Operation;
    private JPanel panel_Number;
    private JPanel panel_Award;
    private JPanel panel_Click;
    private JScrollPane sP_SelectList;
    private JScrollPane sP_Message;
    private JButton btnQuickSelect;
    private JButton btnSelfSelect;
    private JButton btnAward;
    private JButton btnClickMe;
    private JButton btnAskForAward;
    private JButton btnAgain;
    private JLabel lblImageClick;
    private JLabel lblAwardNum01;
    private JLabel lblAwardNum02;
    private JLabel lblAwardNum03;
    private JLabel lblAwardNum04;
    private JLabel lblAwardNum05;
    private JLabel lblAwardNum06;
    private JLabel lblAwardNum07;
    private JLabel lblLotteryImage;
    private DefaultListModel<Object> dListModel;
    private JList listSelectNumbers;
    private JTextArea tA_Message;
    private JSpinner spinnerSets;
    private JCheckBox cbTheSameQuickSelect;
    private JProgressBar progressBar;

    private JButton[] arrayBtn = new JButton[49]; // 儲存動態產生的號碼btn的陣列
    private JLabel[] arrayMyNums; // 我的選號label陣列
    private JLabel[] arrayAwardNums; // 開獎號碼label陣列
    private HashSet<JButton> hsMySelectBtn = new HashSet<>(); // 暫時儲存自選的btn
    private HashSet<JButton> hsQuickSelectBtn = new HashSet<>(); // 暫時儲存快選的btn
    private HashSet<JButton> hsAwardBtn = new HashSet<>(); // 暫時儲存中獎的btn
    private HashSet<JButton> hsAnimationBtn = new HashSet<>(); // 儲存動畫用btn
    private ArrayList<HashSet<JButton>> alSelectNum = new ArrayList<>(); // 儲存每個選擇的號碼組。超級獎號抽取用。
    private int setsCount = 0; // 選號組數計數器
    private Random random = new Random(); // 亂數產生器
    private int chargeLevel = 0; // 充能計數器
    private String[] arraySelectJListElement = new String[7]; // 我的選號清單元素轉為陣列使用
    private JButton specialNum; // 特別號
    private int drawLottery = 0; // 0 未開獎。1 準備開獎。 2 開獎中。 3 已開獎
    private TreeMap<Integer, Integer> treemapIndexLevel = new TreeMap<>(); // 儲存各組中獎狀態
    private int askForAwardCount = 0; // 要獎金次數計數器
    private int progressBarValue = 0; // 要設定給progressBar的值
    private JButton animationBtn = new JButton();
    private int timer = 700; // 動畫間隔時間
    private int times = 0; // 動畫計數器
    private int timeLevel1 = 13; // 動畫加速階段
    private int timeLevel2 = 88; // 動畫減速階段
    private int timeLimit = 100; // 動畫最大次數

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    Lottery frame = new Lottery();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Lottery()
    {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 720);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        panel_Operation = new JPanel();
        panel_Operation.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        panel_Operation.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
                new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        contentPane.add(panel_Operation, BorderLayout.EAST);
        GridBagLayout gbl_panel_Operation = new GridBagLayout();
        gbl_panel_Operation.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        gbl_panel_Operation.columnWidths = new int[] { 0, 0, 0 };
        gbl_panel_Operation.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        panel_Operation.setLayout(gbl_panel_Operation);

        JLabel label_12 = new JLabel("");
        label_12.setMinimumSize(new Dimension(30, 30));
        label_12.setPreferredSize(new Dimension(30, 30));
        label_12.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_12 = new GridBagConstraints();
        gbc_label_12.insets = new Insets(0, 0, 5, 5);
        gbc_label_12.gridx = 0;
        gbc_label_12.gridy = 0;
        panel_Operation.add(label_12, gbc_label_12);

        btnSelfSelect = new JButton("\u81EA\u9078\u78BA\u8A8D");
        btnSelfSelect.setFocusPainted(false);
        btnSelfSelect.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (hsMySelectBtn.size() < 6)
                {
                    JOptionPane.showMessageDialog(null, "請選擇 6 個號碼", "未完成選號", JOptionPane.INFORMATION_MESSAGE);
                } else
                {
                    HashSet<JButton> hsMySelectBtnCopy = (HashSet<JButton>) hsMySelectBtn.clone();
                    alSelectNum.add(hsMySelectBtnCopy); // 存入ArrayList
                    for (int i = 0; i < (Integer) spinnerSets.getValue(); i++)
                    {
                        dListModel.addElement(SelectNumToString(hsMySelectBtnCopy)); // 依照組數將號碼加入JList
                    }
                    SelectBtnClear(hsMySelectBtn); // 清除選取的號碼
                    spinnerSets.setValue(1);
                }
            }
        });
        btnSelfSelect.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSelfSelect.setPreferredSize(new Dimension(130, 40));
        btnSelfSelect.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        GridBagConstraints gbc_btnSelfSelect = new GridBagConstraints();
        gbc_btnSelfSelect.gridheight = 2;
        gbc_btnSelfSelect.fill = GridBagConstraints.BOTH;
        gbc_btnSelfSelect.insets = new Insets(0, 0, 5, 5);
        gbc_btnSelfSelect.gridx = 1;
        gbc_btnSelfSelect.gridy = 0;
        panel_Operation.add(btnSelfSelect, gbc_btnSelfSelect);

        JLabel label_13 = new JLabel("");
        label_13.setMinimumSize(new Dimension(30, 30));
        label_13.setPreferredSize(new Dimension(30, 30));
        label_13.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_13 = new GridBagConstraints();
        gbc_label_13.insets = new Insets(0, 0, 5, 0);
        gbc_label_13.gridx = 2;
        gbc_label_13.gridy = 0;
        panel_Operation.add(label_13, gbc_label_13);

        JLabel label_3 = new JLabel("");
        label_3.setMinimumSize(new Dimension(30, 30));
        label_3.setPreferredSize(new Dimension(30, 30));
        label_3.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_3 = new GridBagConstraints();
        gbc_label_3.insets = new Insets(0, 0, 5, 5);
        gbc_label_3.gridx = 0;
        gbc_label_3.gridy = 1;
        panel_Operation.add(label_3, gbc_label_3);

        JLabel label_5 = new JLabel("");
        label_5.setMinimumSize(new Dimension(30, 30));
        label_5.setPreferredSize(new Dimension(30, 30));
        label_5.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_5 = new GridBagConstraints();
        gbc_label_5.insets = new Insets(0, 0, 5, 5);
        gbc_label_5.gridx = 0;
        gbc_label_5.gridy = 2;
        panel_Operation.add(label_5, gbc_label_5);

        JLabel label_6 = new JLabel("");
        label_6.setMinimumSize(new Dimension(30, 30));
        label_6.setPreferredSize(new Dimension(30, 30));
        label_6.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_6 = new GridBagConstraints();
        gbc_label_6.insets = new Insets(0, 0, 5, 5);
        gbc_label_6.gridx = 0;
        gbc_label_6.gridy = 3;
        panel_Operation.add(label_6, gbc_label_6);

        btnQuickSelect = new JButton("\u5FEB\u9078");
        btnQuickSelect.setFocusPainted(false);
        btnQuickSelect.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // 勾選同號快選時，加入清單的號碼一樣
                if (cbTheSameQuickSelect.isSelected())
                {
                    hsQuickSelectBtn.addAll(hsMySelectBtn); // 先加入自選的號碼
                    while (hsQuickSelectBtn.size() < 6)
                    {
                        hsQuickSelectBtn.add(arrayBtn[random.nextInt(49)]); // 在號碼btn陣列中隨機取得索引0~48的btn，並加入快選暫存HashSet。
                    }
                    // 依照組數將號碼加入JList
                    for (int i = 0; i < (Integer) spinnerSets.getValue(); i++)
                    {
                        HashSet<JButton> hsQuickSelectBtnCopy = (HashSet<JButton>) hsQuickSelectBtn.clone();
                        alSelectNum.add(hsQuickSelectBtnCopy); // 存入ArrayList
                        dListModel.addElement(SelectNumToString(hsQuickSelectBtnCopy)); // 轉成字串並加入選號清單Jlist
                    }
                    SelectBtnClear(hsQuickSelectBtn); // 清除快選的號碼
                    SelectBtnClear(hsMySelectBtn); // 清除自選的號碼
                } else
                { // 未勾選同號快選時，加入清單的號碼每次均為隨機
                  // 依照組數將號碼加入JList
                    for (int i = 0; i < (Integer) spinnerSets.getValue(); i++)
                    {
                        hsQuickSelectBtn.addAll(hsMySelectBtn); // 先加入自選的號碼

                        while (hsQuickSelectBtn.size() < 6)
                        {
                            hsQuickSelectBtn.add(arrayBtn[random.nextInt(49)]); // 在號碼btn陣列中隨機取得索引0~48的btn，並加入快選暫存HashSet。
                        }

                        HashSet<JButton> hsQuickSelectBtnCopy = (HashSet<JButton>) hsQuickSelectBtn.clone();
                        alSelectNum.add(hsQuickSelectBtnCopy); // 存入ArrayList
                        dListModel.addElement(SelectNumToString(hsQuickSelectBtnCopy)); // 轉成字串並加入選號清單Jlist
                        SelectBtnClear(hsQuickSelectBtn); // 清除快選的號碼
                    }
                    SelectBtnClear(hsMySelectBtn); // 清除自選的號碼
                }
                spinnerSets.setValue(1);
            }
        });
        btnQuickSelect.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnQuickSelect.setPreferredSize(new Dimension(130, 40));
        btnQuickSelect.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        GridBagConstraints gbc_btnQuickSelect = new GridBagConstraints();
        gbc_btnQuickSelect.gridheight = 2;
        gbc_btnQuickSelect.fill = GridBagConstraints.BOTH;
        gbc_btnQuickSelect.insets = new Insets(0, 0, 5, 5);
        gbc_btnQuickSelect.gridx = 1;
        gbc_btnQuickSelect.gridy = 2;
        panel_Operation.add(btnQuickSelect, gbc_btnQuickSelect);

        JLabel label_7 = new JLabel("");
        label_7.setMinimumSize(new Dimension(30, 30));
        label_7.setPreferredSize(new Dimension(30, 30));
        label_7.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_7 = new GridBagConstraints();
        gbc_label_7.insets = new Insets(0, 0, 5, 5);
        gbc_label_7.anchor = GridBagConstraints.EAST;
        gbc_label_7.gridx = 0;
        gbc_label_7.gridy = 4;
        panel_Operation.add(label_7, gbc_label_7);

        JLabel label_8 = new JLabel("");
        label_8.setMinimumSize(new Dimension(30, 30));
        label_8.setPreferredSize(new Dimension(30, 30));
        label_8.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_8 = new GridBagConstraints();
        gbc_label_8.insets = new Insets(0, 0, 5, 5);
        gbc_label_8.gridx = 0;
        gbc_label_8.gridy = 5;
        panel_Operation.add(label_8, gbc_label_8);

        cbTheSameQuickSelect = new JCheckBox("\u540C\u865F\u5FEB\u9078");
        cbTheSameQuickSelect.setFocusPainted(false);
        cbTheSameQuickSelect.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        GridBagConstraints gbc_cbTheSameQuickSelect = new GridBagConstraints();
        gbc_cbTheSameQuickSelect.anchor = GridBagConstraints.SOUTHWEST;
        gbc_cbTheSameQuickSelect.insets = new Insets(0, 0, 5, 5);
        gbc_cbTheSameQuickSelect.gridx = 1;
        gbc_cbTheSameQuickSelect.gridy = 5;
        panel_Operation.add(cbTheSameQuickSelect, gbc_cbTheSameQuickSelect);

        JLabel label_9 = new JLabel("");
        label_9.setMinimumSize(new Dimension(30, 30));
        label_9.setPreferredSize(new Dimension(30, 30));
        label_9.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_9 = new GridBagConstraints();
        gbc_label_9.insets = new Insets(0, 0, 5, 5);
        gbc_label_9.gridx = 0;
        gbc_label_9.gridy = 6;
        panel_Operation.add(label_9, gbc_label_9);

        JLabel lblMuiltSets = new JLabel("\u591A\u7D44");
        lblMuiltSets.setOpaque(true);
        lblMuiltSets.setHorizontalAlignment(SwingConstants.CENTER);
        lblMuiltSets.setBorder(new LineBorder(Color.BLACK));
        lblMuiltSets.setBackground(new Color(255, 255, 153));
        lblMuiltSets.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        GridBagConstraints gbc_lblMuiltSets = new GridBagConstraints();
        gbc_lblMuiltSets.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblMuiltSets.insets = new Insets(0, 0, 5, 5);
        gbc_lblMuiltSets.gridx = 1;
        gbc_lblMuiltSets.gridy = 6;
        panel_Operation.add(lblMuiltSets, gbc_lblMuiltSets);

        JLabel label_10 = new JLabel("");
        label_10.setMinimumSize(new Dimension(30, 30));
        label_10.setPreferredSize(new Dimension(30, 30));
        label_10.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_10 = new GridBagConstraints();
        gbc_label_10.insets = new Insets(0, 0, 5, 5);
        gbc_label_10.gridx = 0;
        gbc_label_10.gridy = 7;
        panel_Operation.add(label_10, gbc_label_10);

        spinnerSets = new JSpinner();
        spinnerSets.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
        spinnerSets.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_spinnerSets = new GridBagConstraints();
        gbc_spinnerSets.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinnerSets.insets = new Insets(0, 0, 5, 5);
        gbc_spinnerSets.gridx = 1;
        gbc_spinnerSets.gridy = 7;
        panel_Operation.add(spinnerSets, gbc_spinnerSets);

        JLabel label_11 = new JLabel("");
        label_11.setMinimumSize(new Dimension(30, 30));
        label_11.setPreferredSize(new Dimension(30, 30));
        label_11.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_11 = new GridBagConstraints();
        gbc_label_11.insets = new Insets(0, 0, 5, 5);
        gbc_label_11.gridx = 0;
        gbc_label_11.gridy = 8;
        panel_Operation.add(label_11, gbc_label_11);

        btnAgain = new JButton("\u518D\u73A9\u4E00\u6B21");
        btnAgain.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                SetEnabledToTrue(); // 開啟btn功能，並清除先前資料
            }
        });
        btnAgain.setPreferredSize(new Dimension(130, 40));
        btnAgain.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        btnAgain.setFocusPainted(false);
        GridBagConstraints gbc_btnAgain = new GridBagConstraints();
        gbc_btnAgain.gridheight = 2;
        gbc_btnAgain.fill = GridBagConstraints.BOTH;
        gbc_btnAgain.insets = new Insets(0, 0, 5, 5);
        gbc_btnAgain.gridx = 1;
        gbc_btnAgain.gridy = 8;
        panel_Operation.add(btnAgain, gbc_btnAgain);

        JLabel label_31 = new JLabel("");
        label_31.setPreferredSize(new Dimension(30, 30));
        label_31.setMinimumSize(new Dimension(30, 30));
        label_31.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_31 = new GridBagConstraints();
        gbc_label_31.insets = new Insets(0, 0, 0, 5);
        gbc_label_31.gridx = 0;
        gbc_label_31.gridy = 9;
        panel_Operation.add(label_31, gbc_label_31);

        panel_Award = new JPanel();
        panel_Award.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        contentPane.add(panel_Award, BorderLayout.SOUTH);
        GridBagLayout gbl_panel_Award = new GridBagLayout();
        gbl_panel_Award.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        gbl_panel_Award.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        gbl_panel_Award.rowHeights = new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1 };
        gbl_panel_Award.columnWidths = new int[] { 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
                25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25 };
        panel_Award.setLayout(gbl_panel_Award);

        JLabel label_14 = new JLabel("");
        label_14.setPreferredSize(new Dimension(30, 30));
        label_14.setMinimumSize(new Dimension(30, 30));
        label_14.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_14 = new GridBagConstraints();
        gbc_label_14.insets = new Insets(0, 0, 5, 5);
        gbc_label_14.gridx = 0;
        gbc_label_14.gridy = 0;
        panel_Award.add(label_14, gbc_label_14);

        JLabel label_15 = new JLabel("");
        label_15.setPreferredSize(new Dimension(30, 30));
        label_15.setMinimumSize(new Dimension(30, 30));
        label_15.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_15 = new GridBagConstraints();
        gbc_label_15.insets = new Insets(0, 0, 5, 0);
        gbc_label_15.gridx = 41;
        gbc_label_15.gridy = 0;
        panel_Award.add(label_15, gbc_label_15);

        JLabel label_16 = new JLabel("");
        label_16.setPreferredSize(new Dimension(30, 30));
        label_16.setMinimumSize(new Dimension(30, 30));
        label_16.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_16 = new GridBagConstraints();
        gbc_label_16.insets = new Insets(0, 0, 5, 5);
        gbc_label_16.gridx = 0;
        gbc_label_16.gridy = 1;
        panel_Award.add(label_16, gbc_label_16);

        JLabel label_4 = new JLabel("\u6211\u7684\u9078\u865F\u6E05\u55AE");
        label_4.setBackground(new Color(204, 255, 153));
        label_4.setOpaque(true);
        label_4.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        label_4.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_4 = new GridBagConstraints();
        gbc_label_4.gridwidth = 7;
        gbc_label_4.insets = new Insets(0, 0, 5, 5);
        gbc_label_4.gridx = 2;
        gbc_label_4.gridy = 1;
        panel_Award.add(label_4, gbc_label_4);

        JLabel label_1 = new JLabel("\u6211\u7684\u9078\u865F");
        label_1.setOpaque(true);
        label_1.setBackground(new Color(204, 255, 153));
        label_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        label_1.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.gridwidth = 3;
        gbc_label_1.insets = new Insets(0, 0, 5, 5);
        gbc_label_1.gridx = 11;
        gbc_label_1.gridy = 1;
        panel_Award.add(label_1, gbc_label_1);

        JLabel lblMyNum01 = new JLabel("");
        lblMyNum01.setHorizontalTextPosition(SwingConstants.CENTER);
        lblMyNum01.setHorizontalAlignment(SwingConstants.CENTER);
        lblMyNum01.setOpaque(true);
        lblMyNum01.setMinimumSize(new Dimension(50, 50));
        lblMyNum01.setPreferredSize(new Dimension(50, 50));
        lblMyNum01.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblMyNum01.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblMyNum01.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblMyNum01 = new GridBagConstraints();
        gbc_lblMyNum01.gridheight = 2;
        gbc_lblMyNum01.gridwidth = 2;
        gbc_lblMyNum01.insets = new Insets(0, 0, 5, 5);
        gbc_lblMyNum01.gridx = 14;
        gbc_lblMyNum01.gridy = 1;
        panel_Award.add(lblMyNum01, gbc_lblMyNum01);

        JLabel lblMyNum02 = new JLabel("");
        lblMyNum02.setHorizontalTextPosition(SwingConstants.CENTER);
        lblMyNum02.setHorizontalAlignment(SwingConstants.CENTER);
        lblMyNum02.setOpaque(true);
        lblMyNum02.setPreferredSize(new Dimension(50, 50));
        lblMyNum02.setMinimumSize(new Dimension(50, 50));
        lblMyNum02.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblMyNum02.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblMyNum02.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblMyNum02 = new GridBagConstraints();
        gbc_lblMyNum02.gridwidth = 2;
        gbc_lblMyNum02.gridheight = 2;
        gbc_lblMyNum02.insets = new Insets(0, 0, 5, 5);
        gbc_lblMyNum02.gridx = 16;
        gbc_lblMyNum02.gridy = 1;
        panel_Award.add(lblMyNum02, gbc_lblMyNum02);

        JLabel lblMyNum03 = new JLabel("");
        lblMyNum03.setHorizontalTextPosition(SwingConstants.CENTER);
        lblMyNum03.setHorizontalAlignment(SwingConstants.CENTER);
        lblMyNum03.setOpaque(true);
        lblMyNum03.setPreferredSize(new Dimension(50, 50));
        lblMyNum03.setMinimumSize(new Dimension(50, 50));
        lblMyNum03.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblMyNum03.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblMyNum03.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblMyNum03 = new GridBagConstraints();
        gbc_lblMyNum03.gridwidth = 2;
        gbc_lblMyNum03.gridheight = 2;
        gbc_lblMyNum03.insets = new Insets(0, 0, 5, 5);
        gbc_lblMyNum03.gridx = 18;
        gbc_lblMyNum03.gridy = 1;
        panel_Award.add(lblMyNum03, gbc_lblMyNum03);

        JLabel lblMyNum04 = new JLabel("");
        lblMyNum04.setHorizontalTextPosition(SwingConstants.CENTER);
        lblMyNum04.setHorizontalAlignment(SwingConstants.CENTER);
        lblMyNum04.setOpaque(true);
        lblMyNum04.setPreferredSize(new Dimension(50, 50));
        lblMyNum04.setMinimumSize(new Dimension(50, 50));
        lblMyNum04.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblMyNum04.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblMyNum04.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblMyNum04 = new GridBagConstraints();
        gbc_lblMyNum04.gridwidth = 2;
        gbc_lblMyNum04.gridheight = 2;
        gbc_lblMyNum04.insets = new Insets(0, 0, 5, 5);
        gbc_lblMyNum04.gridx = 20;
        gbc_lblMyNum04.gridy = 1;
        panel_Award.add(lblMyNum04, gbc_lblMyNum04);

        JLabel lblMyNum05 = new JLabel("");
        lblMyNum05.setHorizontalTextPosition(SwingConstants.CENTER);
        lblMyNum05.setHorizontalAlignment(SwingConstants.CENTER);
        lblMyNum05.setOpaque(true);
        lblMyNum05.setPreferredSize(new Dimension(50, 50));
        lblMyNum05.setMinimumSize(new Dimension(50, 50));
        lblMyNum05.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblMyNum05.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblMyNum05.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblMyNum05 = new GridBagConstraints();
        gbc_lblMyNum05.gridwidth = 2;
        gbc_lblMyNum05.gridheight = 2;
        gbc_lblMyNum05.insets = new Insets(0, 0, 5, 5);
        gbc_lblMyNum05.gridx = 22;
        gbc_lblMyNum05.gridy = 1;
        panel_Award.add(lblMyNum05, gbc_lblMyNum05);

        JLabel lblMyNum06 = new JLabel("");
        lblMyNum06.setHorizontalTextPosition(SwingConstants.CENTER);
        lblMyNum06.setHorizontalAlignment(SwingConstants.CENTER);
        lblMyNum06.setOpaque(true);
        lblMyNum06.setPreferredSize(new Dimension(50, 50));
        lblMyNum06.setMinimumSize(new Dimension(50, 50));
        lblMyNum06.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblMyNum06.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblMyNum06.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblMyNum06 = new GridBagConstraints();
        gbc_lblMyNum06.gridwidth = 2;
        gbc_lblMyNum06.gridheight = 2;
        gbc_lblMyNum06.insets = new Insets(0, 0, 5, 5);
        gbc_lblMyNum06.gridx = 24;
        gbc_lblMyNum06.gridy = 1;
        panel_Award.add(lblMyNum06, gbc_lblMyNum06);

        btnAward = new JButton("\u958B\u734E");
        btnAward.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                panel_Click.setVisible(true);
                SetEnabledToFalse(); // 關閉btn功能
                drawLottery = 1;
            }
        });
        btnAward.setFocusPainted(false);
        btnAward.setFont(new Font("微軟正黑體", Font.BOLD, 22));
        GridBagConstraints gbc_btnAward = new GridBagConstraints();
        gbc_btnAward.fill = GridBagConstraints.BOTH;
        gbc_btnAward.gridheight = 3;
        gbc_btnAward.gridwidth = 3;
        gbc_btnAward.insets = new Insets(0, 0, 5, 5);
        gbc_btnAward.gridx = 32;
        gbc_btnAward.gridy = 1;
        panel_Award.add(btnAward, gbc_btnAward);

        panel_Click = new JPanel();
        panel_Click.setVisible(false);
        panel_Click.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null),
                new BevelBorder(BevelBorder.RAISED, null, null, null, null)));
        GridBagConstraints gbc_panel_Click = new GridBagConstraints();
        gbc_panel_Click.fill = GridBagConstraints.BOTH;
        gbc_panel_Click.gridheight = 7;
        gbc_panel_Click.gridwidth = 5;
        gbc_panel_Click.insets = new Insets(0, 0, 5, 5);
        gbc_panel_Click.gridx = 36;
        gbc_panel_Click.gridy = 1;
        panel_Award.add(panel_Click, gbc_panel_Click);
        GridBagLayout gbl_panel_Click = new GridBagLayout();
        gbl_panel_Click.columnWidths = new int[] { 25, 25, 25, 25, 25 };
        gbl_panel_Click.rowHeights = new int[] { 1, 1, 1, 1, 1, 1, 1 };
        gbl_panel_Click.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
        gbl_panel_Click.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
        panel_Click.setLayout(gbl_panel_Click);

        JLabel label_27 = new JLabel("");
        label_27.setPreferredSize(new Dimension(30, 30));
        label_27.setMinimumSize(new Dimension(30, 30));
        label_27.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_27 = new GridBagConstraints();
        gbc_label_27.insets = new Insets(0, 0, 5, 5);
        gbc_label_27.gridx = 0;
        gbc_label_27.gridy = 0;
        panel_Click.add(label_27, gbc_label_27);

        lblImageClick = new JLabel("");
        lblImageClick.setBackground(Color.RED);
        lblImageClick.setPreferredSize(new Dimension(30, 60));
        lblImageClick.setMinimumSize(new Dimension(30, 60));
        lblImageClick.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_lblImageClick = new GridBagConstraints();
        gbc_lblImageClick.fill = GridBagConstraints.BOTH;
        gbc_lblImageClick.gridheight = 2;
        gbc_lblImageClick.gridwidth = 3;
        gbc_lblImageClick.insets = new Insets(0, 0, 5, 5);
        gbc_lblImageClick.gridx = 1;
        gbc_lblImageClick.gridy = 0;
        panel_Click.add(lblImageClick, gbc_lblImageClick);

        JLabel label_32 = new JLabel("");
        label_32.setPreferredSize(new Dimension(30, 30));
        label_32.setMinimumSize(new Dimension(30, 30));
        label_32.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_32 = new GridBagConstraints();
        gbc_label_32.insets = new Insets(0, 0, 5, 5);
        gbc_label_32.gridx = 0;
        gbc_label_32.gridy = 1;
        panel_Click.add(label_32, gbc_label_32);

        JLabel label_23 = new JLabel("");
        label_23.setPreferredSize(new Dimension(30, 30));
        label_23.setMinimumSize(new Dimension(30, 30));
        label_23.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_23 = new GridBagConstraints();
        gbc_label_23.insets = new Insets(0, 0, 5, 5);
        gbc_label_23.gridx = 0;
        gbc_label_23.gridy = 2;
        panel_Click.add(label_23, gbc_label_23);

        JLabel label_24 = new JLabel("");
        label_24.setPreferredSize(new Dimension(30, 30));
        label_24.setMinimumSize(new Dimension(30, 30));
        label_24.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_24 = new GridBagConstraints();
        gbc_label_24.insets = new Insets(0, 0, 5, 0);
        gbc_label_24.gridx = 4;
        gbc_label_24.gridy = 2;
        panel_Click.add(label_24, gbc_label_24);

        progressBar = new JProgressBar();
        progressBar.setForeground(Color.RED);
        progressBar.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        GridBagConstraints gbc_progressBar = new GridBagConstraints();
        gbc_progressBar.fill = GridBagConstraints.BOTH;
        gbc_progressBar.gridwidth = 3;
        gbc_progressBar.insets = new Insets(0, 0, 5, 5);
        gbc_progressBar.gridx = 1;
        gbc_progressBar.gridy = 3;
        panel_Click.add(progressBar, gbc_progressBar);

        JLabel label_30 = new JLabel("");
        label_30.setPreferredSize(new Dimension(30, 30));
        label_30.setMinimumSize(new Dimension(30, 30));
        label_30.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_30 = new GridBagConstraints();
        gbc_label_30.insets = new Insets(0, 0, 5, 5);
        gbc_label_30.gridx = 0;
        gbc_label_30.gridy = 4;
        panel_Click.add(label_30, gbc_label_30);

        btnClickMe = new JButton("\u6309\u6211");
        btnClickMe.setFocusPainted(false);
        GridBagConstraints gbc_btnClickMe = new GridBagConstraints();
        gbc_btnClickMe.insets = new Insets(0, 0, 0, 5);
        gbc_btnClickMe.fill = GridBagConstraints.BOTH;
        gbc_btnClickMe.gridheight = 3;
        gbc_btnClickMe.gridwidth = 3;
        gbc_btnClickMe.gridx = 1;
        gbc_btnClickMe.gridy = 4;
        panel_Click.add(btnClickMe, gbc_btnClickMe);
        btnClickMe.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (1 == drawLottery)
                {
                    Thread AnimationThread = new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            // 開獎btn動畫
                            for (times = 1; times <= timeLimit; times++)
                            {
                                while (hsAnimationBtn.size() < 7)
                                {
                                    animationBtn = arrayBtn[random.nextInt(49)];
                                    if (hsAnimationBtn.add(animationBtn))
                                    {                                        
                                        ChangeBtnBorderByChargeLevel(animationBtn);
                                    }
                                }
                                try
                                {
                                    if (times <= timeLevel1)
                                    {
                                        timer -= 50;
                                        Thread.sleep(timer);
                                    } else if (times >= timeLevel2)
                                    {
                                        timer += 50;
                                        Thread.sleep(timer);
                                    } else
                                    {
                                        Thread.sleep(timer);
                                    }

                                } catch (InterruptedException e)
                                {
                                    e.printStackTrace();
                                }
                                SelectBtnClear(hsAnimationBtn);
                                hsAnimationBtn.clear();
                                if (times >= timeLevel2)
                                {
                                    panel_Click.setVisible(false);
                                }
                            }
                            // 動畫跑完後開獎
                            DrawLottery();
                            drawLottery = 3;
                        }
                    });
                    drawLottery = 2;
                    AnimationThread.start();
                } else if (2 == drawLottery)
                {
                    progressBarValue++;
                    progressBar.setValue(progressBarValue);// 變更進度條狀態
                    if (progressBarValue == 100)
                    {
                        chargeLevel = 4;
                        progressBar.setForeground(Color.YELLOW);
                    } else if (progressBarValue == 75)
                    {
                        chargeLevel = 3;
                        timeLevel2 += 50; // 動畫減速階段
                        timeLimit += 50; // 動畫最大次數
                        progressBar.setForeground(Color.MAGENTA);
                    } else if (progressBarValue == 50)
                    {
                        chargeLevel = 2;
                        timeLevel2 += 50; // 動畫減速階段
                        timeLimit += 50; // 動畫最大次數
                        progressBar.setForeground(Color.CYAN);
                    } else if (progressBarValue == 25)
                    {
                        chargeLevel = 1;
                        timeLevel2 += 50; // 動畫減速階段
                        timeLimit += 50; // 動畫最大次數
                        progressBar.setForeground(Color.GREEN);
                    }
                }
            }
        });
        btnClickMe.setFont(new Font("微軟正黑體", Font.BOLD, 22));

        JLabel label_26 = new JLabel("");
        label_26.setPreferredSize(new Dimension(30, 30));
        label_26.setMinimumSize(new Dimension(30, 30));
        label_26.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_26 = new GridBagConstraints();
        gbc_label_26.insets = new Insets(0, 0, 5, 5);
        gbc_label_26.gridx = 0;
        gbc_label_26.gridy = 5;
        panel_Click.add(label_26, gbc_label_26);

        JLabel label_25 = new JLabel("");
        label_25.setPreferredSize(new Dimension(30, 30));
        label_25.setMinimumSize(new Dimension(30, 30));
        label_25.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_25 = new GridBagConstraints();
        gbc_label_25.insets = new Insets(0, 0, 0, 5);
        gbc_label_25.gridx = 0;
        gbc_label_25.gridy = 6;
        panel_Click.add(label_25, gbc_label_25);

        JLabel label_17 = new JLabel("");
        label_17.setPreferredSize(new Dimension(30, 30));
        label_17.setMinimumSize(new Dimension(30, 30));
        label_17.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_17 = new GridBagConstraints();
        gbc_label_17.insets = new Insets(0, 0, 5, 5);
        gbc_label_17.gridx = 0;
        gbc_label_17.gridy = 2;
        panel_Award.add(label_17, gbc_label_17);

        sP_SelectList = new JScrollPane();
        sP_SelectList.setMinimumSize(new Dimension(100, 150));
        sP_SelectList.setMaximumSize(new Dimension(100, 150));
        sP_SelectList.setPreferredSize(new Dimension(100, 150));
        GridBagConstraints gbc_sP_SelectList = new GridBagConstraints();
        gbc_sP_SelectList.gridheight = 6;
        gbc_sP_SelectList.gridwidth = 9;
        gbc_sP_SelectList.insets = new Insets(0, 0, 5, 5);
        gbc_sP_SelectList.fill = GridBagConstraints.BOTH;
        gbc_sP_SelectList.gridx = 1;
        gbc_sP_SelectList.gridy = 2;
        panel_Award.add(sP_SelectList, gbc_sP_SelectList);

        // 建立模組DefaultListModel，並在JList初始化時作為其參數模組
        dListModel = new DefaultListModel<>();
        listSelectNumbers = new JList(dListModel);
        listSelectNumbers.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                // 每次選擇，getValueIsAdjusting()都會傳回 true (選擇開始) 與 false (選擇結束)
                // ，一般使用最後的回傳值，故要使用的狀態為false。
                if (!e.getValueIsAdjusting())
                {
                    // 有選到元素
                    if (!listSelectNumbers.isSelectionEmpty())
                    {
                        arraySelectJListElement = ((String) listSelectNumbers.getSelectedValue()).split(" ");// 將選到的元素字串分割成陣列
                        for (int i = 1; i < arraySelectJListElement.length; i++)
                        {
                            arrayMyNums[i - 1].setText(arraySelectJListElement[i]);
                        }
                        // 若已開獎才進行獎號比對判斷
                        if (3 == drawLottery)
                        {
                            JListAward(listSelectNumbers.getSelectedIndex());
                        }
                    }
                }
            }
        });
        listSelectNumbers.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
        listSelectNumbers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sP_SelectList.setViewportView(listSelectNumbers);
        listSelectNumbers.setBorder(new LineBorder(new Color(0, 0, 0), 2));

        JLabel label_29 = new JLabel("");
        label_29.setPreferredSize(new Dimension(30, 30));
        label_29.setMinimumSize(new Dimension(30, 30));
        label_29.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_29 = new GridBagConstraints();
        gbc_label_29.insets = new Insets(0, 0, 5, 5);
        gbc_label_29.gridx = 0;
        gbc_label_29.gridy = 3;
        panel_Award.add(label_29, gbc_label_29);

        JLabel label_2 = new JLabel("\u958B\u734E\u865F\u78BC");
        label_2.setBackground(Color.YELLOW);
        label_2.setOpaque(true);
        label_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        label_2.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_2 = new GridBagConstraints();
        gbc_label_2.gridwidth = 3;
        gbc_label_2.insets = new Insets(0, 0, 5, 5);
        gbc_label_2.gridx = 11;
        gbc_label_2.gridy = 3;
        panel_Award.add(label_2, gbc_label_2);

        lblAwardNum01 = new JLabel("");
        lblAwardNum01.setHorizontalTextPosition(SwingConstants.CENTER);
        lblAwardNum01.setHorizontalAlignment(SwingConstants.CENTER);
        lblAwardNum01.setOpaque(true);
        lblAwardNum01.setPreferredSize(new Dimension(50, 50));
        lblAwardNum01.setMinimumSize(new Dimension(50, 50));
        lblAwardNum01.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblAwardNum01.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblAwardNum01.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblAwardNum01 = new GridBagConstraints();
        gbc_lblAwardNum01.gridwidth = 2;
        gbc_lblAwardNum01.gridheight = 2;
        gbc_lblAwardNum01.insets = new Insets(0, 0, 5, 5);
        gbc_lblAwardNum01.gridx = 14;
        gbc_lblAwardNum01.gridy = 3;
        panel_Award.add(lblAwardNum01, gbc_lblAwardNum01);

        lblAwardNum02 = new JLabel("");
        lblAwardNum02.setHorizontalTextPosition(SwingConstants.CENTER);
        lblAwardNum02.setHorizontalAlignment(SwingConstants.CENTER);
        lblAwardNum02.setOpaque(true);
        lblAwardNum02.setPreferredSize(new Dimension(50, 50));
        lblAwardNum02.setMinimumSize(new Dimension(50, 50));
        lblAwardNum02.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblAwardNum02.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblAwardNum02.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblAwardNum02 = new GridBagConstraints();
        gbc_lblAwardNum02.gridwidth = 2;
        gbc_lblAwardNum02.gridheight = 2;
        gbc_lblAwardNum02.insets = new Insets(0, 0, 5, 5);
        gbc_lblAwardNum02.gridx = 16;
        gbc_lblAwardNum02.gridy = 3;
        panel_Award.add(lblAwardNum02, gbc_lblAwardNum02);

        lblAwardNum03 = new JLabel("");
        lblAwardNum03.setHorizontalTextPosition(SwingConstants.CENTER);
        lblAwardNum03.setHorizontalAlignment(SwingConstants.CENTER);
        lblAwardNum03.setOpaque(true);
        lblAwardNum03.setPreferredSize(new Dimension(50, 50));
        lblAwardNum03.setMinimumSize(new Dimension(50, 50));
        lblAwardNum03.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblAwardNum03.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblAwardNum03.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblAwardNum03 = new GridBagConstraints();
        gbc_lblAwardNum03.gridwidth = 2;
        gbc_lblAwardNum03.gridheight = 2;
        gbc_lblAwardNum03.insets = new Insets(0, 0, 5, 5);
        gbc_lblAwardNum03.gridx = 18;
        gbc_lblAwardNum03.gridy = 3;
        panel_Award.add(lblAwardNum03, gbc_lblAwardNum03);

        lblAwardNum04 = new JLabel("");
        lblAwardNum04.setHorizontalTextPosition(SwingConstants.CENTER);
        lblAwardNum04.setHorizontalAlignment(SwingConstants.CENTER);
        lblAwardNum04.setOpaque(true);
        lblAwardNum04.setPreferredSize(new Dimension(50, 50));
        lblAwardNum04.setMinimumSize(new Dimension(50, 50));
        lblAwardNum04.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblAwardNum04.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblAwardNum04.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblAwardNum04 = new GridBagConstraints();
        gbc_lblAwardNum04.gridwidth = 2;
        gbc_lblAwardNum04.gridheight = 2;
        gbc_lblAwardNum04.insets = new Insets(0, 0, 5, 5);
        gbc_lblAwardNum04.gridx = 20;
        gbc_lblAwardNum04.gridy = 3;
        panel_Award.add(lblAwardNum04, gbc_lblAwardNum04);

        lblAwardNum05 = new JLabel("");
        lblAwardNum05.setHorizontalTextPosition(SwingConstants.CENTER);
        lblAwardNum05.setHorizontalAlignment(SwingConstants.CENTER);
        lblAwardNum05.setOpaque(true);
        lblAwardNum05.setPreferredSize(new Dimension(50, 50));
        lblAwardNum05.setMinimumSize(new Dimension(50, 50));
        lblAwardNum05.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblAwardNum05.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblAwardNum05.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblAwardNum05 = new GridBagConstraints();
        gbc_lblAwardNum05.gridwidth = 2;
        gbc_lblAwardNum05.gridheight = 2;
        gbc_lblAwardNum05.insets = new Insets(0, 0, 5, 5);
        gbc_lblAwardNum05.gridx = 22;
        gbc_lblAwardNum05.gridy = 3;
        panel_Award.add(lblAwardNum05, gbc_lblAwardNum05);

        lblAwardNum06 = new JLabel("");
        lblAwardNum06.setHorizontalTextPosition(SwingConstants.CENTER);
        lblAwardNum06.setHorizontalAlignment(SwingConstants.CENTER);
        lblAwardNum06.setOpaque(true);
        lblAwardNum06.setPreferredSize(new Dimension(50, 50));
        lblAwardNum06.setMinimumSize(new Dimension(50, 50));
        lblAwardNum06.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblAwardNum06.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblAwardNum06.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblAwardNum06 = new GridBagConstraints();
        gbc_lblAwardNum06.gridwidth = 2;
        gbc_lblAwardNum06.gridheight = 2;
        gbc_lblAwardNum06.insets = new Insets(0, 0, 5, 5);
        gbc_lblAwardNum06.gridx = 24;
        gbc_lblAwardNum06.gridy = 3;
        panel_Award.add(lblAwardNum06, gbc_lblAwardNum06);

        JLabel label_28 = new JLabel("\u7279\u5225\u865F");
        label_28.setOpaque(true);
        label_28.setBackground(Color.YELLOW);
        label_28.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        label_28.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        GridBagConstraints gbc_label_28 = new GridBagConstraints();
        gbc_label_28.gridwidth = 3;
        gbc_label_28.insets = new Insets(0, 0, 5, 5);
        gbc_label_28.gridx = 26;
        gbc_label_28.gridy = 3;
        panel_Award.add(label_28, gbc_label_28);

        lblAwardNum07 = new JLabel("");
        lblAwardNum07.setHorizontalTextPosition(SwingConstants.CENTER);
        lblAwardNum07.setHorizontalAlignment(SwingConstants.CENTER);
        lblAwardNum07.setOpaque(true);
        lblAwardNum07.setPreferredSize(new Dimension(50, 50));
        lblAwardNum07.setMinimumSize(new Dimension(50, 50));
        lblAwardNum07.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        lblAwardNum07.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, null, null, null));
        lblAwardNum07.setBackground(Color.WHITE);
        GridBagConstraints gbc_lblAwardNum07 = new GridBagConstraints();
        gbc_lblAwardNum07.gridwidth = 2;
        gbc_lblAwardNum07.gridheight = 2;
        gbc_lblAwardNum07.insets = new Insets(0, 0, 5, 5);
        gbc_lblAwardNum07.gridx = 29;
        gbc_lblAwardNum07.gridy = 3;
        panel_Award.add(lblAwardNum07, gbc_lblAwardNum07);

        JLabel label_18 = new JLabel("");
        label_18.setPreferredSize(new Dimension(30, 30));
        label_18.setMinimumSize(new Dimension(30, 30));
        label_18.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_18 = new GridBagConstraints();
        gbc_label_18.insets = new Insets(0, 0, 5, 5);
        gbc_label_18.gridx = 0;
        gbc_label_18.gridy = 4;
        panel_Award.add(label_18, gbc_label_18);

        JLabel label_19 = new JLabel("");
        label_19.setPreferredSize(new Dimension(30, 30));
        label_19.setMinimumSize(new Dimension(30, 30));
        label_19.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_19 = new GridBagConstraints();
        gbc_label_19.insets = new Insets(0, 0, 5, 5);
        gbc_label_19.gridx = 0;
        gbc_label_19.gridy = 5;
        panel_Award.add(label_19, gbc_label_19);

        sP_Message = new JScrollPane();
        sP_Message.setMinimumSize(new Dimension(400, 50));
        sP_Message.setMaximumSize(new Dimension(400, 50));
        sP_Message.setPreferredSize(new Dimension(400, 50));
        GridBagConstraints gbc_sP_Message = new GridBagConstraints();
        gbc_sP_Message.gridheight = 3;
        gbc_sP_Message.gridwidth = 20;
        gbc_sP_Message.insets = new Insets(0, 0, 5, 5);
        gbc_sP_Message.fill = GridBagConstraints.BOTH;
        gbc_sP_Message.gridx = 11;
        gbc_sP_Message.gridy = 5;
        panel_Award.add(sP_Message, gbc_sP_Message);

        tA_Message = new JTextArea();
        tA_Message.setMinimumSize(new Dimension(1, 1));
        sP_Message.setViewportView(tA_Message);
        tA_Message.setFont(new Font("微軟正黑體", Font.PLAIN, 16));
        tA_Message.setEditable(false);

        JLabel label_20 = new JLabel("");
        label_20.setPreferredSize(new Dimension(30, 30));
        label_20.setMinimumSize(new Dimension(30, 30));
        label_20.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_20 = new GridBagConstraints();
        gbc_label_20.insets = new Insets(0, 0, 5, 5);
        gbc_label_20.gridx = 0;
        gbc_label_20.gridy = 6;
        panel_Award.add(label_20, gbc_label_20);

        JLabel label_21 = new JLabel("");
        label_21.setPreferredSize(new Dimension(30, 30));
        label_21.setMinimumSize(new Dimension(30, 30));
        label_21.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_21 = new GridBagConstraints();
        gbc_label_21.insets = new Insets(0, 0, 5, 5);
        gbc_label_21.gridx = 0;
        gbc_label_21.gridy = 7;
        panel_Award.add(label_21, gbc_label_21);

        btnAskForAward = new JButton("\u8981\u734E\u91D1");
        btnAskForAward.setVisible(false);
        btnAskForAward.setFocusPainted(false);
        btnAskForAward.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                askForAwardCount++;
                String strMessage = "";
                String strTitle = "";
                switch (askForAwardCount)
                {
                case 1:
                    strMessage = "很抱歉，此遊戲僅供純娛樂使用。並未實際提供獎金。";
                    strTitle = "廠商表示 : ";
                    break;
                case 2:
                    strMessage = "什麼!?你說我們騙人?不不不，我們有標明[*圖片僅供參考，請以實際內容為準]，您一定是沒看清楚!";
                    strTitle = "廠商表示 : ";
                    break;
                case 3:
                    strMessage = "夠了喔...要是彩卷公司的大樂透真的給你這樣玩，再佛心的公司都會倒好嗎!!";
                    strTitle = "廠商表示 : ";
                    break;
                default:
                    strMessage = "今日已結束營業...";
                    strTitle = "公告 : ";
                    btnAgain.setEnabled(false);
                    spinnerSets.setEnabled(false);
                    cbTheSameQuickSelect.setEnabled(false);
                    lblMuiltSets.setEnabled(false);
                    for (JButton jb : arrayBtn)
                    {
                        ChangeBtnBorderToNull(jb);
                    }
                    for (JButton j : arrayBtn)
                    {
                        j.setEnabled(false);
                    }
                }
                JTextArea jtextArea = new JTextArea(strMessage);
                jtextArea.setFont(new Font(null, 0, 16));
                jtextArea.setBackground(new Color(240, 240, 240));
                JOptionPane.showMessageDialog(null, jtextArea, strTitle, JOptionPane.PLAIN_MESSAGE);
            }
        });
        btnAskForAward.setForeground(Color.WHITE);
        btnAskForAward.setBackground(Color.RED);
        btnAskForAward.setFont(new Font("微軟正黑體", Font.PLAIN, 12));
        GridBagConstraints gbc_btnAskForAward = new GridBagConstraints();
        gbc_btnAskForAward.gridwidth = 3;
        gbc_btnAskForAward.insets = new Insets(0, 0, 5, 5);
        gbc_btnAskForAward.gridx = 31;
        gbc_btnAskForAward.gridy = 7;
        panel_Award.add(btnAskForAward, gbc_btnAskForAward);

        JLabel label_22 = new JLabel("");
        label_22.setPreferredSize(new Dimension(30, 30));
        label_22.setMinimumSize(new Dimension(30, 30));
        label_22.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_22 = new GridBagConstraints();
        gbc_label_22.insets = new Insets(0, 0, 0, 5);
        gbc_label_22.gridx = 0;
        gbc_label_22.gridy = 8;
        panel_Award.add(label_22, gbc_label_22);

        panel_Number = new JPanel();
        panel_Number.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
                new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        contentPane.add(panel_Number, BorderLayout.CENTER);
        panel_Number.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        BufferedImage bufferedImage = new BufferedImage(230, 350, BufferedImage.TYPE_INT_ARGB); // 建立BufferedImage
        try
        {
            BufferedImage bufImgIn = ImageIO.read(new FileInputStream("images\\LotteryAward.png"));
            Graphics2D graphics2d = bufferedImage.createGraphics(); // 建立Graphics2D，以便控制匯入的圖片
            graphics2d.drawImage(bufImgIn, 5, 0, 220, 340, null); // 重新繪製載入的圖片給bufferedImage
        } catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }

        JPanel panel_GameImage = new JPanel();
        panel_GameImage.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
                new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
        panel_GameImage.setSize(new Dimension(230, 350));
        panel_GameImage.setPreferredSize(new Dimension(230, 350));
        contentPane.add(panel_GameImage, BorderLayout.WEST);

        lblLotteryImage = new JLabel("");
        lblLotteryImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLotteryImage.setHorizontalTextPosition(SwingConstants.CENTER);
        lblLotteryImage.setHorizontalAlignment(SwingConstants.CENTER);

        panel_GameImage.add(lblLotteryImage);

        lblLotteryImage.setIcon(new ImageIcon(bufferedImage));

        for (int i = 1; i <= 49; i++)
        {
            arrayBtn[i - 1] = CreateBtnNum(i);
            panel_Number.add(arrayBtn[i - 1]);
        }

        arrayMyNums = new JLabel[] { lblMyNum01, lblMyNum02, lblMyNum03, lblMyNum04, lblMyNum05, lblMyNum06 };
        arrayAwardNums = new JLabel[] { lblAwardNum01, lblAwardNum02, lblAwardNum03, lblAwardNum04, lblAwardNum05,
                lblAwardNum06, lblAwardNum07 };

    }

    // 開獎
    protected void DrawLottery()
    {
        switch (chargeLevel)
        {
        case 1:
            hsAwardBtn.addAll((HashSet<JButton>) GetSuperNums(alSelectNum.get(random.nextInt(alSelectNum.size())), 3)); // 隨機選擇一組已選的號碼組，並取得其中3個btn
            break;
        case 2:
            hsAwardBtn.addAll((HashSet<JButton>) GetSuperNums(alSelectNum.get(random.nextInt(alSelectNum.size())), 4)); // 隨機選擇一組已選的號碼組，並取得其中3個btn
            break;
        case 3:
            hsAwardBtn.addAll((HashSet<JButton>) GetSuperNums(alSelectNum.get(random.nextInt(alSelectNum.size())), 5)); // 隨機選擇一組已選的號碼組，並取得其中3個btn
            break;
        case 4:
            hsAwardBtn.addAll((HashSet<JButton>) GetSuperNums(alSelectNum.get(random.nextInt(alSelectNum.size())), 6)); // 隨機選擇一組已選的號碼組，並取得其中3個btn
            break;
        }

        while (hsAwardBtn.size() < 6)
        {
            hsAwardBtn.add(arrayBtn[random.nextInt(49)]); // 在號碼btn陣列中隨機取得索引0~48的btn，並加入獎號暫存HashSet。
        }
        ArrayList<Integer> numbers = SelectNumToArrayList(hsAwardBtn); // 排序及取得整數ArrayList
        numbers.add(getSpecialNum(hsAwardBtn)); // 取得並加入特別號
        for (JButton jb : hsAwardBtn)
        {
            ChangeBtnBorderByChargeLevel(jb);            
        }
        ChangeBtnBorderByChargeLevel(specialNum);        
        // 將開獎號碼放入開獎號碼Label區
        for (int i = 0; i < numbers.size(); i++)
        {
            arrayAwardNums[i].setText(String.valueOf(numbers.toArray()[i]));
        }

        CreateAwardList(); // 產生中獎清單
    }

    // 產生中獎清單
    protected void CreateAwardList()
    {
        String strAwardMessage = ""; // 最後要顯示的訊息
        String strNumTemp = ""; // 每組對中的號碼暫存器
        Boolean isAward = false; // 是否有對中號碼
        Boolean isSpecialNum = false; // 是否有對中特別號
        Boolean isFirstPrize = false;
        int topIndex = 0; // 中最多號碼的index
        int numCountTemp = 0; // 暫存中了幾個號碼
        int numCount = 0; // 計算中了幾個號碼
        Boolean isSpecialTemp = false; // 暫存中最多號碼的那組，是否有特別號

        for (int i = 0; i < alSelectNum.size(); i++)
        {
            // 比對中獎號碼
            for (JButton jb : hsAwardBtn)
            {
                if (alSelectNum.get(i).contains(jb))
                {
                    strNumTemp += " " + jb.getName();
                    isAward = true;
                    numCount++;
                }
            }
            // 比對特別號
            if (numCount >= 2 && alSelectNum.get(i).contains(specialNum))
            {
                strNumTemp += " 特別號 : " + specialNum.getName();
                isSpecialNum = true;
                numCount++;
            }
            // 比對完該組號碼，判斷中獎號碼數，並組成字串
            if (true == isAward && numCount >= 3)
            {
                switch (numCount)
                {
                case 3:
                    if (true == isSpecialNum)
                    {
                        treemapIndexLevel.put(i, 1);
                        strAwardMessage += "第" + (i + 1) + "組的號碼 : 恭喜您對中了" + " 柒獎! " + "\r\n        獲得獎金 : NT$" + "400"
                                + "元\r\n        中獎號碼 : " + strNumTemp + "\r\n";
                    } else
                    {
                        treemapIndexLevel.put(i, 1);
                        strAwardMessage += "第" + (i + 1) + "組的號碼 : 恭喜您對中了" + " 普獎! " + "\r\n        獲得獎金 : NT$" + "400"
                                + "元\r\n        中獎號碼 : " + strNumTemp + "\r\n";
                    }
                    break;
                case 4:
                    if (true == isSpecialNum)
                    {
                        treemapIndexLevel.put(i, 1);
                        strAwardMessage += "第" + (i + 1) + "組的號碼 : 恭喜您對中了" + " 陸獎! " + "\r\n        獲得獎金 : NT$"
                                + "1,000" + "元\r\n        中獎號碼 : " + strNumTemp + "\r\n";
                    } else
                    {
                        treemapIndexLevel.put(i, 2);
                        strAwardMessage += "第" + (i + 1) + "組的號碼 : 恭喜您對中了" + " 伍獎! " + "\r\n        獲得獎金 : NT$"
                                + "2,000" + "元\r\n        中獎號碼 : " + strNumTemp + "\r\n";
                    }
                    break;
                case 5:
                    if (true == isSpecialNum)
                    {
                        treemapIndexLevel.put(i, 2);
                        strAwardMessage += "第" + (i + 1) + "組的號碼 : 恭喜您對中了" + " 肆獎! " + "\r\n        獲得獎金 : NT$"
                                + "6,000" + "元\r\n        中獎號碼 : " + strNumTemp + "\r\n";
                    } else
                    {
                        treemapIndexLevel.put(i, 3);
                        strAwardMessage += "第" + (i + 1) + "組的號碼 : 恭喜您對中了" + " 參獎! " + "\r\n        獲得獎金 : NT$"
                                + "100,000" + "元\r\n        中獎號碼 : " + strNumTemp + "\r\n";
                    }
                    break;
                case 6:
                    if (true == isSpecialNum)
                    {
                        treemapIndexLevel.put(i, 3);
                        strAwardMessage += "第" + (i + 1) + "組的號碼 : 恭喜您對中了" + " 貳獎! " + "\r\n        獲得獎金 : NT$"
                                + "300,000" + "元\r\n        中獎號碼 : " + strNumTemp + "\r\n";
                    } else
                    {
                        treemapIndexLevel.put(i, 4);
                        strAwardMessage += "第" + (i + 1) + "組的號碼 : 恭喜您對中了" + " 頭獎!\r\n        中獎號碼為 : " + strNumTemp
                                + "\r\n";
                        isFirstPrize = true;
                    }
                    break;
                }
            } else
            {
                strAwardMessage += "第" + (i + 1) + "組的號碼 : 很可惜沒有中獎\r\n";
                treemapIndexLevel.put(i, 0);
            }

            if (numCountTemp < numCount)
            {
                numCountTemp = numCount;
                topIndex = i;
            } else if (numCountTemp == numCount)
            {
                if (isSpecialNum == isSpecialTemp)
                {
                    // 不須變更
                } else if (false == isSpecialNum && true == isSpecialTemp)
                {
                    // 若新中獎組別沒有中特別號，則變更
                    numCountTemp = numCount;
                    topIndex = i;
                    isSpecialTemp = false;
                }
            }

            strNumTemp = "";
            isAward = false;
            isSpecialNum = false;
            numCount = 0;
        }
        if (isFirstPrize)
        {
            JOptionPane.showMessageDialog(null, "恭喜您對中頭獎!!", "頭獎!!", JOptionPane.INFORMATION_MESSAGE);
            btnAskForAward.setVisible(true);
        }
        if ("" == strAwardMessage)
        {
            strAwardMessage += "沒有號碼可以對獎...";
        }

        listSelectNumbers.setSelectedIndex(topIndex);
        JListAward(topIndex);
        tA_Message.setText(strAwardMessage);
        JTextArea jtextArea = new JTextArea(strAwardMessage);
        JScrollPane jscrollPane = new JScrollPane(jtextArea);
        jscrollPane.setPreferredSize(new Dimension(300, 300));
        JOptionPane.showMessageDialog(null, jscrollPane, "對獎結果", JOptionPane.PLAIN_MESSAGE);
    }

    // 重新開始
    protected void SetEnabledToTrue()
    {
        btnAward.setEnabled(true);
        btnSelfSelect.setEnabled(true);
        btnQuickSelect.setEnabled(true);
        for (JButton j : arrayBtn)
        {
            j.setEnabled(true);
        }
        dListModel.removeAllElements();
        hsMySelectBtn.clear();
        hsQuickSelectBtn.clear();
        hsAwardBtn.clear();
        alSelectNum.clear();
        setsCount = 0;
        chargeLevel = 0;
        drawLottery = 0;
        progressBarValue = 0;
        progressBar.setValue(0);
        progressBar.setForeground(Color.PINK);
        panel_Click.setVisible(false);
        treemapIndexLevel.clear();
        tA_Message.setText("");
        timer = 700; // 動畫間隔時間
        times = 0; // 動畫計數器
        timeLevel1 = 13; // 動畫加速階段
        timeLevel2 = 88; // 動畫減速階段
        timeLimit = 100; // 動畫最大次數
        btnAskForAward.setVisible(false);
        for (JButton jb : arrayBtn)
        {
            ChangeBtnBorderToNull(jb);
        }
        for (JLabel j : arrayMyNums)
        {
            j.setText("");
        }
        for (JLabel j : arrayAwardNums)
        {
            j.setText("");
        }
        ChangeAllLblBorderToWhite();
    }

    // 設定btn無法點擊
    protected void SetEnabledToFalse()
    {
        btnAward.setEnabled(false);
        btnSelfSelect.setEnabled(false);
        btnQuickSelect.setEnabled(false);
//        for (JButton j : arrayBtn)
//        {
//            j.setEnabled(false);
//        }
    }

    // 動態產生號碼按鈕
    protected JButton CreateBtnNum(int i)
    {
        JButton btnNewButton = new JButton(String.format("%02d", i));
        btnNewButton.setName(String.valueOf(i));
        btnNewButton.setMargin(new Insets(20, 20, 20, 20));
        btnNewButton.setPreferredSize(new Dimension(75, 50));
        btnNewButton.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNewButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        btnNewButton.setFocusPainted(false);
        btnNewButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if ((hsMySelectBtn.size() < 6) && (true == hsMySelectBtn.add(btnNewButton)))
                {
                    ChangeBtnBorderToRed(btnNewButton);
                } else
                {
                    ChangeBtnBorderToNull(btnNewButton);
                    hsMySelectBtn.remove(btnNewButton);
                }
            }
        });
        return btnNewButton;
    }

    // 將選擇的號碼排序並轉為整數ArrayList
    protected ArrayList<Integer> SelectNumToArrayList(Iterable iterable)
    {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (Object o : iterable)
        {
            numbers.add(Integer.parseInt(((JButton) o).getName()));
        }
        numbers.sort(null);
        return numbers;
    }

    // 將選擇的號碼排序並轉為字串
    protected String SelectNumToString(Iterable iterable)
    {
        String strNumbers = "第" + (++setsCount) + "組:";
        ArrayList<Integer> numbers = SelectNumToArrayList(iterable);
        // 取出號碼組成字串
        for (int i : numbers)
        {
            strNumbers += " " + i;
        }
        return strNumbers;
    }

    // 清除選取的按鈕
    protected void SelectBtnClear(Iterable iterable)
    {
        for (Object o : iterable)
        {
            ChangeBtnBorderToNull(((JButton) o));
        }
        ((HashSet) iterable).clear();
    }

    // 依充能階段取得超級獎號
    protected HashSet GetSuperNums(Iterable iterable, int getQuantity)
    {
        HashSet<JButton> superNums = new HashSet<>();
        int i = 0;
        for (Object o : iterable)
        {
            superNums.add((JButton) o);
            if (++i >= getQuantity)
            {
                break;
            }
        }

        return superNums;
    }

    // 取得特別號
    protected Integer getSpecialNum(HashSet<JButton> hsAwardBtn)
    {
        Boolean isHave = true;
        do
        {
            specialNum = arrayBtn[random.nextInt(49)];
            if (!hsAwardBtn.contains(specialNum))
            {
                isHave = false;
            }
        } while (isHave);

        return Integer.valueOf(specialNum.getName());
    }

    // SelectJList的號碼比對
    protected void JListAward(int selectIndex)
    {
        ChangeAllLblBorderToWhite();

        for (int i = 0; i < arrayAwardNums.length; i++)
        {
            if (!arrayAwardNums[i].getText().isEmpty())
            {
                for (JLabel j2 : arrayMyNums)
                {
                    if (!j2.getText().isEmpty())
                    {
                        if (j2.getText().equals(arrayAwardNums[i].getText()))
                        {
                            if (i == arrayAwardNums.length - 1)
                            {
                                ChangeLblColorToOrange(arrayAwardNums[i], j2); // 對中特別號
                            } else
                            {
                                // 對中其他號碼，根據對中的號碼數變更顏色
                                switch (treemapIndexLevel.get(selectIndex))
                                {
                                case 1:
                                    ChangeLblColorToGreen(arrayAwardNums[i], j2);
                                    break;
                                case 2:
                                    ChangeLblColorToCYAN(arrayAwardNums[i], j2);
                                    break;
                                case 3:
                                    ChangeLblColorToMAGENTA(arrayAwardNums[i], j2);
                                    break;
                                case 4:
                                    ChangeLblColorToRainbow(arrayAwardNums[i], j2);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 變更Label外觀顏色:預設值
    protected void ChangeAllLblBorderToWhite()
    {
        for (JLabel j1 : arrayAwardNums)
        {
            j1.setForeground(Color.black);
            j1.setBackground(Color.white);
        }
        for (JLabel j2 : arrayMyNums)
        {
            j2.setForeground(Color.black);
            j2.setBackground(Color.white);
        }
    }

    // 變更Label外觀顏色:綠色
    protected void ChangeLblColorToGreen(JLabel j1, JLabel j2)
    {
        j1.setForeground(Color.red);
        j1.setBackground(new Color(153, 255, 51));
        j2.setForeground(Color.red);
        j2.setBackground(new Color(153, 255, 51));
    }

    // 變更Label外觀顏色:青色
    protected void ChangeLblColorToCYAN(JLabel j1, JLabel j2)
    {
        j1.setForeground(Color.red);
        j1.setBackground(Color.CYAN);
        j2.setForeground(Color.red);
        j2.setBackground(Color.CYAN);
    }

    // 變更Label外觀顏色:洋紅色
    protected void ChangeLblColorToMAGENTA(JLabel j1, JLabel j2)
    {
        j1.setForeground(Color.red);
        j1.setBackground(Color.MAGENTA);
        j2.setForeground(Color.red);
        j2.setBackground(Color.MAGENTA);
    }

    // 變更Label外觀顏色:綠色
    protected void ChangeLblColorToRainbow(JLabel j1, JLabel j2)
    {
        j1.setForeground(Color.red);
        j1.setBackground(Color.yellow);
        j2.setForeground(Color.red);
        j2.setBackground(Color.yellow);
    }

    // 變更Label外觀顏色:橘色。
    protected void ChangeLblColorToOrange(JLabel j1, JLabel j2)
    {
        j1.setForeground(Color.black);
        j1.setBackground(Color.ORANGE);
        j2.setForeground(Color.black);
        j2.setBackground(Color.ORANGE);
    }

 // 變更按鈕外觀顏色:紅色
    protected void ChangeBtnBorderByChargeLevel(JButton jbtn)
    {
        switch (chargeLevel)
        {
        case 0:
            ChangeBtnBorderToRed(jbtn);
            break;
        case 1:
            ChangeBtnBorderToGreen(jbtn);
            break;
        case 2:
            ChangeBtnBorderToCYAN(jbtn);
            break;
        case 3:
            ChangeBtnBorderToMAGENTA(jbtn);
            break;
        case 4:
            ChangeBtnBorderToRainbow(jbtn);
            break;
        default:
            ChangeBtnBorderToNull(jbtn);
        }
    }
    
    // 變更按鈕外觀顏色:紅色
    protected void ChangeBtnBorderToRed(JButton jbtn)
    {
        jbtn.setForeground(Color.RED);
        jbtn.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.RED, Color.RED, Color.RED, Color.RED));
//      btnNewButton.setBackground(Color.PINK);                   
//      btnNewButton.setFont(new Font("微軟正黑體", Font.BOLD, 28));
    }

    // 變更按鈕外觀顏色:綠色
    protected void ChangeBtnBorderToGreen(JButton jbtn)
    {
        jbtn.setForeground(Color.GREEN);
        jbtn.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN));
//      btnNewButton.setBackground(Color.PINK);                   
//      btnNewButton.setFont(new Font("微軟正黑體", Font.BOLD, 28));
    }

    // 變更按鈕外觀顏色:青色
    protected void ChangeBtnBorderToCYAN(JButton jbtn)
    {
        jbtn.setForeground(Color.CYAN);
        jbtn.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN));
//      btnNewButton.setBackground(Color.PINK);                   
//      btnNewButton.setFont(new Font("微軟正黑體", Font.BOLD, 28));
    }

    // 變更按鈕外觀顏色:洋紅色
    protected void ChangeBtnBorderToMAGENTA(JButton jbtn)
    {
        jbtn.setForeground(Color.MAGENTA);
        jbtn.setBorder(
                new BevelBorder(BevelBorder.LOWERED, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA, Color.MAGENTA));
//      btnNewButton.setBackground(Color.PINK);                   
//      btnNewButton.setFont(new Font("微軟正黑體", Font.BOLD, 28));
    }

    // 變更按鈕外觀顏色:彩色
    protected void ChangeBtnBorderToRainbow(JButton jbtn)
    {
        jbtn.setForeground(Color.YELLOW);
        jbtn.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW));
//      btnNewButton.setBackground(Color.PINK);                   
//      btnNewButton.setFont(new Font("微軟正黑體", Font.BOLD, 28));
    }

    // 變更按鈕外觀顏色:無
    protected void ChangeBtnBorderToNull(JButton jbtn)
    {
        jbtn.setForeground(Color.BLACK);
        jbtn.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
//      btnNewButton.setBackground(new Color(240,240,240));  
//      btnNewButton.setFont(new Font("微軟正黑體", Font.BOLD, 20));  
    }

}
