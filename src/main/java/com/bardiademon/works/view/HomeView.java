package com.bardiademon.works.view;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;

public abstract class HomeView extends JFrame
{
    protected JButton btnNew;
    protected JButton btnStart;
    protected JButton btnUpdate;
    protected JCheckBox chkClose;
    protected JLabel lblMoney;
    protected JLabel lblTime;
    protected JList<String> lstWorks;
    protected JTextField txtClosingTime;
    protected JTextField txtHourlyAmount;
    protected JTextField txtMinutes;
    protected JTextField txtRegistrationTime;
    protected JTextField txtWorkName;

    protected HomeView() throws HeadlessException
    {
        setLookAndFeel();
        EventQueue.invokeLater(this::initComponents);
    }

    private void setLookAndFeel()
    {
        try
        {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if ("Windows".equals(info.getName()))
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
        final JPanel pnlTimeMoney = new JPanel();
        lblTime = new JLabel();
        lblMoney = new JLabel();
        final JScrollPane jScrollPane2 = new JScrollPane();
        lstWorks = new JList<>();
        final JLabel lblWorkName = new JLabel();
        txtWorkName = new JTextField();
        final JLabel lblHourlyAmount = new JLabel();
        txtHourlyAmount = new JTextField();
        txtRegistrationTime = new JTextField();
        final JLabel lblRegistrationTime = new JLabel();
        txtClosingTime = new JTextField();
        final JLabel lblClosingTime = new JLabel();
        chkClose = new JCheckBox();
        btnNew = new JButton();
        btnUpdate = new JButton();
        btnStart = new JButton();
        final JLabel lblMinutes = new JLabel();
        txtMinutes = new JTextField();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Works");

        pnlTimeMoney.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0 , 0 , 0)));

        lblTime.setFont(new java.awt.Font("Segoe UI" , Font.PLAIN , 48)); // NOI18N
        lblTime.setHorizontalAlignment(SwingConstants.CENTER);
        lblTime.setText("00:00:00");

        lblMoney.setFont(new java.awt.Font("Segoe UI" , Font.PLAIN , 18)); // NOI18N
        lblMoney.setHorizontalAlignment(SwingConstants.CENTER);
        lblMoney.setText("$ 0");

        GroupLayout pnlTimeMoneyLayout = new GroupLayout(pnlTimeMoney);
        pnlTimeMoney.setLayout(pnlTimeMoneyLayout);
        pnlTimeMoneyLayout.setHorizontalGroup(pnlTimeMoneyLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(pnlTimeMoneyLayout.createSequentialGroup().addContainerGap().addGroup(pnlTimeMoneyLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblTime , GroupLayout.DEFAULT_SIZE , GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE).addComponent(lblMoney , GroupLayout.DEFAULT_SIZE , GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)).addContainerGap()));
        pnlTimeMoneyLayout.setVerticalGroup(pnlTimeMoneyLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(pnlTimeMoneyLayout.createSequentialGroup().addContainerGap().addComponent(lblTime , GroupLayout.PREFERRED_SIZE , 98 , GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(lblMoney).addContainerGap(GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)));

        jScrollPane2.setViewportView(lstWorks);

        lblWorkName.setText("Name");

        lblHourlyAmount.setText("Hourly Amount");

        lblRegistrationTime.setText(" Registration time");

        lblClosingTime.setText("Closing time");

        chkClose.setText("Close");

        btnNew.setText("New");
        btnNew.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnUpdate.setText("Update");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnStart.setFont(new java.awt.Font("Segoe UI" , Font.PLAIN , 24)); // NOI18N
        btnStart.setText("Start of work");
        btnStart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblMinutes.setText("Minutes");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING , layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane2 , GroupLayout.PREFERRED_SIZE , 237 , GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(pnlTimeMoney , GroupLayout.DEFAULT_SIZE , GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblRegistrationTime).addComponent(lblMinutes).addGroup(layout.createSequentialGroup().addGap(6 , 6 , 6).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(txtRegistrationTime , GroupLayout.PREFERRED_SIZE , 236 , GroupLayout.PREFERRED_SIZE).addComponent(txtMinutes , GroupLayout.PREFERRED_SIZE , 236 , GroupLayout.PREFERRED_SIZE)))).addGroup(layout.createSequentialGroup().addGap(6 , 6 , 6).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblWorkName).addGroup(layout.createSequentialGroup().addGap(6 , 6 , 6).addComponent(txtWorkName , GroupLayout.PREFERRED_SIZE , 236 , GroupLayout.PREFERRED_SIZE))))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED , 68 , Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING , layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblHourlyAmount).addGroup(layout.createSequentialGroup().addGap(6 , 6 , 6).addComponent(txtHourlyAmount , GroupLayout.PREFERRED_SIZE , 236 , GroupLayout.PREFERRED_SIZE))).addGroup(GroupLayout.Alignment.TRAILING , layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblClosingTime).addGroup(layout.createSequentialGroup().addGap(6 , 6 , 6).addComponent(txtClosingTime , GroupLayout.PREFERRED_SIZE , 236 , GroupLayout.PREFERRED_SIZE)).addGroup(GroupLayout.Alignment.TRAILING , layout.createSequentialGroup().addGap(191 , 191 , 191).addComponent(chkClose))))).addComponent(btnStart , GroupLayout.DEFAULT_SIZE , GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE).addComponent(btnNew , GroupLayout.DEFAULT_SIZE , GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE).addComponent(btnUpdate , GroupLayout.DEFAULT_SIZE , GroupLayout.DEFAULT_SIZE , Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(pnlTimeMoney , GroupLayout.PREFERRED_SIZE , GroupLayout.DEFAULT_SIZE , GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblWorkName).addComponent(lblHourlyAmount)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(txtWorkName , GroupLayout.PREFERRED_SIZE , GroupLayout.DEFAULT_SIZE , GroupLayout.PREFERRED_SIZE).addComponent(txtHourlyAmount , GroupLayout.PREFERRED_SIZE , GroupLayout.DEFAULT_SIZE , GroupLayout.PREFERRED_SIZE)).addGap(18 , 18 , 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(lblRegistrationTime).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(txtRegistrationTime , GroupLayout.PREFERRED_SIZE , GroupLayout.DEFAULT_SIZE , GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addComponent(lblClosingTime).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(txtClosingTime , GroupLayout.PREFERRED_SIZE , GroupLayout.DEFAULT_SIZE , GroupLayout.PREFERRED_SIZE))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(chkClose).addComponent(lblMinutes)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(txtMinutes , GroupLayout.PREFERRED_SIZE , GroupLayout.DEFAULT_SIZE , GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED , 20 , Short.MAX_VALUE).addComponent(btnUpdate).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(btnStart , GroupLayout.PREFERRED_SIZE , 57 , GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(btnNew)).addComponent(jScrollPane2)).addContainerGap()));

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

}
