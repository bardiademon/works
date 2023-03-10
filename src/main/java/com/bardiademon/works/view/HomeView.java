package com.bardiademon.works.view;

import com.bardiademon.works.data.enums.ThemeType;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class HomeView extends JFrame
{
    public static ThemeType themeType;

    protected final String txtStartBtnStart = "Start of work", txtStopBtnStart = "Stop of work";

    protected JButton btnNew;
    protected JButton btnStart;
    protected JButton btnUpdate;
    protected JCheckBox chkClose;
    protected JLabel lblMoney;
    protected JLabel lblTime;
    protected JList<String> lstWorks;
    protected JTextField txtClosingTime;
    protected JTextField txtHourlyAmount;
    protected JTextField txtWorked;
    protected JTextField txtRegistrationTime;
    protected JTextField txtWorkName;
    protected JComboBox<String> groups;
    protected JPanel pnlTimeMoney;
    protected JLabel lblWorkName;
    protected JLabel lblHourlyAmount;
    protected JLabel lblRegistrationTime;
    protected JLabel lblClosingTime;
    protected JLabel lblWorked;

    protected HomeView() throws HeadlessException
    {
        setLookAndFeel();
        EventQueue.invokeLater(() ->
        {
            initComponents();
            setTheme();
        });
    }

    private void setLookAndFeel()
    {
        try
        {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        }
    }

    private void initComponents()
    {
        pnlTimeMoney = new JPanel();
        lblTime = new JLabel();
        lblMoney = new JLabel();
        final JScrollPane jScrollPane2 = new JScrollPane();
        lstWorks = new JList<>();
        lblWorkName = new JLabel();
        txtWorkName = new JTextField();
        lblHourlyAmount = new JLabel();
        txtHourlyAmount = new JTextField();
        txtRegistrationTime = new JTextField();
        lblRegistrationTime = new JLabel();
        txtClosingTime = new JTextField();
        lblClosingTime = new JLabel();
        chkClose = new JCheckBox();
        btnNew = new JButton();
        btnUpdate = new JButton();
        btnStart = new JButton();
        lblWorked = new JLabel();
        txtWorked = new JTextField();
        javax.swing.GroupLayout pnlTimeMoneyLayout = new javax.swing.GroupLayout(pnlTimeMoney);
        pnlTimeMoney.setLayout(pnlTimeMoneyLayout);
        pnlTimeMoneyLayout.setHorizontalGroup(
                pnlTimeMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlTimeMoneyLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlTimeMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblTime , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                        .addComponent(lblMoney , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))
                                .addContainerGap())
        );
        pnlTimeMoneyLayout.setVerticalGroup(
                pnlTimeMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlTimeMoneyLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTime , javax.swing.GroupLayout.PREFERRED_SIZE , 98 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMoney)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(lstWorks);

        lblWorkName.setText("Name");

        lblHourlyAmount.setText("Hourly Amount");

        lblRegistrationTime.setText("Registration time");

        lblClosingTime.setText("Closing time");

        chkClose.setText("Close");

        btnNew.setText("New");
        btnNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnStart.setFont(new java.awt.Font("Segoe UI" , Font.PLAIN , 24)); // NOI18N
        btnStart.setText(txtStartBtnStart);
        btnStart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblWorked.setText("Worked");

        groups = new JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Works");

        pnlTimeMoney.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0 , 0 , 0)));

        lblTime.setFont(new java.awt.Font("Segoe UI" , Font.PLAIN , 48)); // NOI18N
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTime.setText("00:00:00");

        lblMoney.setFont(new java.awt.Font("Segoe UI" , Font.PLAIN , 18)); // NOI18N
        lblMoney.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMoney.setText("$ 0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING , layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING , false)
                                        .addComponent(jScrollPane2 , javax.swing.GroupLayout.DEFAULT_SIZE , 237 , Short.MAX_VALUE)
                                        .addComponent(groups , 0 , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(pnlTimeMoney , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(lblRegistrationTime)
                                                                .addComponent(lblWorked)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(6 , 6 , 6)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(txtRegistrationTime , javax.swing.GroupLayout.PREFERRED_SIZE , 236 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(txtWorked , javax.swing.GroupLayout.PREFERRED_SIZE , 236 , javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(6 , 6 , 6)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(lblWorkName)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addGap(6 , 6 , 6)
                                                                                .addComponent(txtWorkName , javax.swing.GroupLayout.PREFERRED_SIZE , 236 , javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED , 68 , Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING , layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(lblHourlyAmount)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(6 , 6 , 6)
                                                                        .addComponent(txtHourlyAmount , javax.swing.GroupLayout.PREFERRED_SIZE , 236 , javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING , layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(lblClosingTime)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(6 , 6 , 6)
                                                                        .addComponent(txtClosingTime , javax.swing.GroupLayout.PREFERRED_SIZE , 236 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING , layout.createSequentialGroup()
                                                                        .addGap(191 , 191 , 191)
                                                                        .addComponent(chkClose)))))
                                        .addComponent(btnStart , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                        .addComponent(btnNew , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                        .addComponent(btnUpdate , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING , layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(groups , javax.swing.GroupLayout.PREFERRED_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED , javax.swing.GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)
                                                .addComponent(jScrollPane2 , javax.swing.GroupLayout.PREFERRED_SIZE , 437 , javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(pnlTimeMoney , javax.swing.GroupLayout.PREFERRED_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblWorkName)
                                                        .addComponent(lblHourlyAmount))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtWorkName , javax.swing.GroupLayout.PREFERRED_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtHourlyAmount , javax.swing.GroupLayout.PREFERRED_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18 , 18 , 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(lblRegistrationTime)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(txtRegistrationTime , javax.swing.GroupLayout.PREFERRED_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(lblClosingTime)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(txtClosingTime , javax.swing.GroupLayout.PREFERRED_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(chkClose)
                                                        .addComponent(lblWorked))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtWorked , javax.swing.GroupLayout.PREFERRED_SIZE , javax.swing.GroupLayout.DEFAULT_SIZE , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnUpdate , javax.swing.GroupLayout.PREFERRED_SIZE , 29 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnStart , javax.swing.GroupLayout.PREFERRED_SIZE , 57 , javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnNew , javax.swing.GroupLayout.PREFERRED_SIZE , 29 , javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        txtRegistrationTime.setEditable(false);
        txtClosingTime.setEditable(false);
        txtWorked.setEditable(false);

        pack();
        setOnClick();

        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void setOnClick()
    {
        btnStart.addActionListener(e -> onClickBtnStart());
        btnNew.addActionListener(e -> onClickBtnNew());
        btnUpdate.addActionListener(e -> onClickBtnUpdate());
        lstWorks.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                HomeView.this.onClickLstWork();
            }
        });

        groups.addPropertyChangeListener(evt -> HomeView.this.onClickGroups());
        groups.addActionListener(evt -> HomeView.this.onClickGroups());

        chkClose.addActionListener(e -> onClickChkClose());
    }

    protected abstract void onClickChkClose();

    protected abstract void onClickGroups();

    protected abstract void onClickLstWork();

    protected abstract void onClickBtnUpdate();

    protected abstract void onClickBtnStart();

    protected abstract void onClickBtnNew();

    private void setTheme()
    {
        disableBtnAnimation();

        if (themeType == ThemeType.DARK)
        {
            final Color background = Color.decode("#121212");
            final Color foreground = Color.decode("#FFFFFF");

            setTheme(background , foreground , background);
        }
        if (themeType == ThemeType.LIGHT)
        {
            final Color background = new Color(245 , 245 , 245);
            final Color foreground = Color.decode("#000000");

            setTheme(background , foreground , Color.white);
        }
    }

    private void setTheme(final Color background , final Color foreground , final Color backgroundTxtColor)
    {
        SwingUtilities.invokeLater(() ->
        {
            setColor(background , null , HomeView.this , getContentPane());
            setColor(background , foreground , btnStart , btnNew , btnUpdate);
            btnStart.setBorder(new StrokeBorder(new BasicStroke(0.2F)));
            btnNew.setBorder(new StrokeBorder(new BasicStroke(0.2F)));
            btnUpdate.setBorder(new StrokeBorder(new BasicStroke(0.2F)));

            setColor(background , foreground , lstWorks , groups);
            lstWorks.setSelectionForeground(foreground);
            lstWorks.setSelectionBackground(background.darker());

            setColor(background , null , pnlTimeMoney);

            setColor(null , foreground ,
                    lblTime , lblTime , lblMoney , lblClosingTime , lblWorked , lblRegistrationTime , lblWorkName , lblHourlyAmount , chkClose);

            pnlTimeMoney.setBorder(new LineBorder(foreground));

            setColor(backgroundTxtColor , foreground , txtClosingTime , txtRegistrationTime , txtWorked , txtWorkName , txtHourlyAmount);

            pack();
        });
    }

    private void setColor(final Color background , final Color foreground , final Component... components)
    {
        SwingUtilities.invokeLater(() ->
        {
            for (final Component component : components)
            {
                if (background != null) component.setBackground(background);
                if (foreground != null) component.setForeground(foreground);
            }
        });
    }

    private void disableBtnAnimation()
    {
        btnStart.setContentAreaFilled(false);
        btnStart.setFocusPainted(false);
        btnNew.setContentAreaFilled(false);
        btnNew.setFocusPainted(false);
        btnUpdate.setContentAreaFilled(false);
        btnUpdate.setFocusPainted(false);
    }
}
