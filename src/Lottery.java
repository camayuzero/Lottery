import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
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
    private JLabel lblImageClick;
    private JLabel lblAwardNum01;
    private JLabel lblAwardNum02;
    private JLabel lblAwardNum03;
    private JLabel lblAwardNum04;
    private JLabel lblAwardNum05;
    private JLabel lblAwardNum06;
    private JLabel lblAwardNum07;
    private DefaultListModel<Object> dListModel;
    private JList listSelectNumbers;
    private JTextArea tA_Message;
    private JSpinner spinnerSets;
    private JCheckBox cbTheSameQuickSelect;

    private JButton[] arrayBtn = new JButton[49]; // 儲存動態產生的號碼btn的陣列
    private JLabel[] arrayMyNums; // 我的選號label陣列
    private JLabel[] arrayAwardNums; // 開獎號碼label陣列
    private HashSet<JButton> hsMySelectBtn = new HashSet<>(); // 暫時儲存自選的btn
    private HashSet<JButton> hsQuickSelectBtn = new HashSet<>(); // 暫時儲存快選的btn
    private HashSet<JButton> hsAwardBtn = new HashSet<>(); // 暫時儲存中獎的btn
    private ArrayList<HashSet<JButton>> alSelectNum = new ArrayList<>(); // 儲存每個選擇的號碼組。超級獎號抽取用。
    private int setsCount = 0; // 選號組數計數器
    private Random random = new Random(); // 亂數產生器
    private int chargeLevel = 4; // 充能計數器

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
        gbc_btnQuickSelect.gridy = 3;
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

        JLabel label_9 = new JLabel("");
        label_9.setMinimumSize(new Dimension(30, 30));
        label_9.setPreferredSize(new Dimension(30, 30));
        label_9.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_9 = new GridBagConstraints();
        gbc_label_9.insets = new Insets(0, 0, 5, 5);
        gbc_label_9.gridx = 0;
        gbc_label_9.gridy = 6;
        panel_Operation.add(label_9, gbc_label_9);

        cbTheSameQuickSelect = new JCheckBox("\u540C\u865F\u5FEB\u9078");
        cbTheSameQuickSelect.setFocusPainted(false);
        cbTheSameQuickSelect.setFont(new Font("微軟正黑體", Font.PLAIN, 14));
        GridBagConstraints gbc_cbTheSameQuickSelect = new GridBagConstraints();
        gbc_cbTheSameQuickSelect.anchor = GridBagConstraints.SOUTHWEST;
        gbc_cbTheSameQuickSelect.insets = new Insets(0, 0, 5, 5);
        gbc_cbTheSameQuickSelect.gridx = 1;
        gbc_cbTheSameQuickSelect.gridy = 6;
        panel_Operation.add(cbTheSameQuickSelect, gbc_cbTheSameQuickSelect);

        JLabel label_10 = new JLabel("");
        label_10.setMinimumSize(new Dimension(30, 30));
        label_10.setPreferredSize(new Dimension(30, 30));
        label_10.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_10 = new GridBagConstraints();
        gbc_label_10.insets = new Insets(0, 0, 5, 5);
        gbc_label_10.gridx = 0;
        gbc_label_10.gridy = 7;
        panel_Operation.add(label_10, gbc_label_10);

        JLabel label = new JLabel("\u591A\u7D44");
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(new LineBorder(Color.BLACK));
        label.setBackground(new Color(255, 255, 153));
        label.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.fill = GridBagConstraints.HORIZONTAL;
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.gridx = 1;
        gbc_label.gridy = 7;
        panel_Operation.add(label, gbc_label);

        JLabel label_11 = new JLabel("");
        label_11.setMinimumSize(new Dimension(30, 30));
        label_11.setPreferredSize(new Dimension(30, 30));
        label_11.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_11 = new GridBagConstraints();
        gbc_label_11.insets = new Insets(0, 0, 5, 5);
        gbc_label_11.gridx = 0;
        gbc_label_11.gridy = 8;
        panel_Operation.add(label_11, gbc_label_11);

        spinnerSets = new JSpinner();
        spinnerSets.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
        spinnerSets.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_spinnerSets = new GridBagConstraints();
        gbc_spinnerSets.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinnerSets.insets = new Insets(0, 0, 5, 5);
        gbc_spinnerSets.gridx = 1;
        gbc_spinnerSets.gridy = 8;
        panel_Operation.add(spinnerSets, gbc_spinnerSets);

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
                switch (chargeLevel)
                {
                case 1:
                    hsAwardBtn.addAll(
                            (HashSet<JButton>) GetSuperNums(alSelectNum.get(random.nextInt(alSelectNum.size())), 3)); // 隨機選擇一組已選的號碼組，並取得其中3個btn
                    break;
                case 2:
                    hsAwardBtn.addAll(
                            (HashSet<JButton>) GetSuperNums(alSelectNum.get(random.nextInt(alSelectNum.size())), 4)); // 隨機選擇一組已選的號碼組，並取得其中3個btn
                    break;
                case 3:
                    hsAwardBtn.addAll(
                            (HashSet<JButton>) GetSuperNums(alSelectNum.get(random.nextInt(alSelectNum.size())), 5)); // 隨機選擇一組已選的號碼組，並取得其中3個btn
                    break;
                case 4:
                    hsAwardBtn.addAll(
                            (HashSet<JButton>) GetSuperNums(alSelectNum.get(random.nextInt(alSelectNum.size())), 6)); // 隨機選擇一組已選的號碼組，並取得其中3個btn
                    break;
                }

                while (hsAwardBtn.size() < 6)
                {
                    hsAwardBtn.add(arrayBtn[random.nextInt(49)]); // 在號碼btn陣列中隨機取得索引0~48的btn，並加入獎號暫存HashSet。
                }
                ArrayList<Integer> numbers = SelectNumToArrayList(hsAwardBtn); // 排序及取得整數ArrayList
                numbers.add(getSpecialNum(hsAwardBtn)); // 取得並加入特別號
                // 將開獎號碼放入開獎號碼Label區
                for (int i = 0; i < numbers.size(); i++)
                {
                    arrayAwardNums[i].setText(String.valueOf(numbers.toArray()[i]));
                }
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
        lblImageClick.setOpaque(true);
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
        gbc_label_24.insets = new Insets(0, 0, 5, 5);
        gbc_label_24.gridx = 4;
        gbc_label_24.gridy = 2;
        panel_Click.add(label_24, gbc_label_24);

        JLabel label_25 = new JLabel("");
        label_25.setPreferredSize(new Dimension(30, 30));
        label_25.setMinimumSize(new Dimension(30, 30));
        label_25.setFont(new Font("微軟正黑體", Font.PLAIN, 20));
        GridBagConstraints gbc_label_25 = new GridBagConstraints();
        gbc_label_25.insets = new Insets(0, 0, 5, 5);
        gbc_label_25.gridx = 0;
        gbc_label_25.gridy = 3;
        panel_Click.add(label_25, gbc_label_25);

        btnClickMe = new JButton("\u6309\u6211");
        btnClickMe.setFocusPainted(false);
        GridBagConstraints gbc_btnClickMe = new GridBagConstraints();
        gbc_btnClickMe.insets = new Insets(0, 0, 5, 5);
        gbc_btnClickMe.fill = GridBagConstraints.BOTH;
        gbc_btnClickMe.gridheight = 3;
        gbc_btnClickMe.gridwidth = 3;
        gbc_btnClickMe.gridx = 1;
        gbc_btnClickMe.gridy = 3;
        panel_Click.add(btnClickMe, gbc_btnClickMe);
        btnClickMe.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

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

        JProgressBar progressBar = new JProgressBar();
        GridBagConstraints gbc_progressBar = new GridBagConstraints();
        gbc_progressBar.fill = GridBagConstraints.BOTH;
        gbc_progressBar.gridwidth = 3;
        gbc_progressBar.insets = new Insets(0, 0, 0, 5);
        gbc_progressBar.gridx = 1;
        gbc_progressBar.gridy = 6;
        panel_Click.add(progressBar, gbc_progressBar);

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
        tA_Message.setFont(new Font("微軟正黑體", Font.PLAIN, 18));
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
        btnAskForAward.setFocusPainted(false);
        btnAskForAward.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            }
        });
        btnAskForAward.setVisible(false);
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

        for (int i = 1; i <= 49; i++)
        {
            arrayBtn[i - 1] = CreateBtnNum(i);
            panel_Number.add(arrayBtn[i - 1]);
        }

        arrayMyNums = new JLabel[] { lblMyNum01, lblMyNum02, lblMyNum03, lblMyNum04, lblMyNum05, lblMyNum06 };
        arrayAwardNums = new JLabel[] { lblAwardNum01, lblAwardNum02, lblAwardNum03, lblAwardNum04, lblAwardNum05,
                lblAwardNum06, lblAwardNum07 };

    }

    // 動態產生號碼按鈕
    protected JButton CreateBtnNum(int i)
    {
        JButton btnNewButton = new JButton(String.format("%02d", i));
        btnNewButton.setName(String.valueOf(i));
        btnNewButton.setMargin(new Insets(20, 20, 20, 20));
        btnNewButton.setPreferredSize(new Dimension(95, 50));
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
                    ChangeSelectBtnColor(btnNewButton);
                } else
                {
                    RevertSelectBtnColor(btnNewButton);
                    hsMySelectBtn.remove(btnNewButton);
                }
            }
        });
        return btnNewButton;
    }

    // 變更按鈕顏色及外觀
    protected void ChangeSelectBtnColor(JButton jbtn)
    {
        jbtn.setForeground(Color.RED);
        jbtn.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.RED, Color.RED, Color.RED, Color.RED));
//      btnNewButton.setBackground(Color.PINK);                   
//      btnNewButton.setFont(new Font("微軟正黑體", Font.BOLD, 28));
    }

    // 恢復按鈕顏色及外觀
    protected void RevertSelectBtnColor(JButton jbtn)
    {
        jbtn.setForeground(Color.BLACK);
        jbtn.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
//      btnNewButton.setBackground(new Color(240,240,240));  
//      btnNewButton.setFont(new Font("微軟正黑體", Font.BOLD, 20));  
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
            RevertSelectBtnColor(((JButton) o));
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
        JButton specialNum;
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

}
