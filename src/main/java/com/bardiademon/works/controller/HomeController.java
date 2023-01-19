package com.bardiademon.works.controller;

import com.bardiademon.works.data.model.Works;
import com.bardiademon.works.utils.Path;
import com.bardiademon.works.utils.Time;
import com.bardiademon.works.view.HomeView;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class HomeController extends HomeView
{
    private Timer timer;

    private boolean update = false;

    private Works selectedWork;
    private String selectedGroupName;
    private int selectedWorkIndex;

    private boolean startOfWork;

    private final Map<String, List<Works>> works = new HashMap<>();

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

    private boolean generalCalculation = false;

    public HomeController() throws HeadlessException
    {
        super();
        loadWorks();
        update();
    }

    @Override
    protected void onClickChkClose()
    {
        if (checkSelectedWork())
        {
            if (!selectedWork.isClose() && startOfWork) onClickBtnStart();

            selectedWork.setClose(chkClose.isSelected());

            if (selectedWork.isClose()) selectedWork.setClosingTime(LocalDateTime.now());

            updateCurrentWork();
        }
    }

    @Override
    protected void onClickGroups()
    {
        SwingUtilities.invokeLater(() ->
        {
            final String selectedItem = (String) super.groups.getSelectedItem();
            if (selectedItem != null && !selectedItem.isEmpty())
            {
                selectedGroupName = selectedItem;
                final List<Works> works = this.works.get(selectedItem);
                setList(works);
            }
        });

    }

    @Override
    protected void onClickLstWork()
    {
        SwingUtilities.invokeLater(() ->
        {
            final int selectedIndex = lstWorks.getSelectedIndex();

            if (selectedIndex == 0)
            {
                generalCalculation = true;
                generalCalculation();
                return;
            }

            generalCalculation = false;

            if (selectedIndex > 0)
            {
                final String selectedItem = (String) super.groups.getSelectedItem();
                if (selectedItem != null && !selectedItem.isEmpty())
                {
                    selectedWorkIndex = selectedIndex - 1;
                    final List<Works> works = this.works.get(selectedItem);
                    final Works work = works.get(selectedWorkIndex);
                    setWork(work);
                }
            }
        });
    }

    private void setWork(final Works work)
    {
        SwingUtilities.invokeLater(() ->
        {
            if (work != null)
            {
                txtWorkName.setText(work.getName());
                txtHourlyAmount.setText(String.valueOf(work.getHourlyAmount()));
                txtClosingTime.setText(work.getClosingTime().toString());
                txtRegistrationTime.setText(work.getRegisterationTime().toString());
                txtWorked.setText(work.getWorked().toString());
                chkClose.setSelected(work.isClose());
                lblTime.setText(work.getWorked().toString());
                setMoney();
            }
            else
            {
                txtWorkName.setText("");
                txtHourlyAmount.setText("");
                txtClosingTime.setText("");
                txtRegistrationTime.setText("");
                txtWorked.setText("");
                chkClose.setSelected(false);
                lblTime.setText("");
                lblMoney.setText("");
            }


            if (timer != null) timer.cancel();
        });
        this.selectedWork = work;
    }

    private void loadWorks()
    {
        new Thread(() ->
        {
            final String path = Path.WORKS_JSON;

            final File worksFile = new File(path);

            if (!worksFile.exists())
            {
                try
                {
                    Files.write(worksFile.toPath() , "{}".getBytes(StandardCharsets.UTF_8));
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }

            try
            {
                final String worksStr = new String(Files.readAllBytes(worksFile.toPath()) , StandardCharsets.UTF_8);

                final JSONObject jsonWorks = new JSONObject(worksStr);

                if (jsonWorks.length() > 0)
                {
                    final Set<String> keys = jsonWorks.keySet();
                    for (final String key : keys)
                    {
                        final JSONArray jsonArrayItem = jsonWorks.getJSONArray(key);

                        if (jsonArrayItem.length() > 0)
                        {
                            listModel.clear();

                            final List<Works> works = new ArrayList<>();

                            for (final Object jsonObj : jsonArrayItem)
                            {
                                final JSONObject item = (JSONObject) jsonObj;

                                final String name = item.getString(Works.JsonKey.NAME);
                                final int hourlyAmount = item.getInt(Works.JsonKey.HOURLY_AMOUNT);
                                final long registrationTimeLong = item.getLong(Works.JsonKey.REGISTRATION_TIME);
                                final long closingTimeLong = item.getLong(Works.JsonKey.CLOSING_TIME);
                                final LocalDateTime registrationTime = (new Timestamp(registrationTimeLong)).toLocalDateTime();
                                final LocalDateTime closingTime = (new Timestamp(closingTimeLong)).toLocalDateTime();
                                final long worked = item.getLong(Works.JsonKey.WORKED);
                                final boolean close = item.getBoolean(Works.JsonKey.CLOSE);

                                final Works work = Works.builder()
                                        .name(name)
                                        .hourlyAmount(hourlyAmount)
                                        .registerationTime(registrationTime)
                                        .closingTime(closingTime)
                                        .worked(Time.of(worked))
                                        .close(close).build();

                                works.add(work);
                            }

                            this.works.put(key , works);
                        }
                    }
                }
                setGroups();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }

        }).start();
    }

    private void setGroups()
    {
        SwingUtilities.invokeLater(() ->
        {
            comboBoxModel.removeAllElements();
            listModel.clear();

            super.groups.setModel(comboBoxModel);
            super.lstWorks.setModel(listModel);

            if (works.size() > 0)
            {
                works.forEach((groupName , works) -> comboBoxModel.addElement(groupName));
            }
        });
    }

    private void setList(final List<Works> works)
    {
        SwingUtilities.invokeLater(() ->
        {
            listModel.clear();
            listModel.addElement("General calculation");
            for (final Works work : works) listModel.addElement(work.toString());
        });

    }

    @Override
    protected void onClickBtnUpdate()
    {
        if (generalCalculation) return;

        if (checkSelectedWork())
        {
            final String name = txtWorkName.getText();
            final String hourlyAmountStr = txtHourlyAmount.getText();

            if (notEmpty(name) && notEmpty(hourlyAmountStr))
            {
                int hourlyAmount;
                try
                {
                    hourlyAmount = Integer.parseInt(hourlyAmountStr);
                }
                catch (Exception e)
                {
                    showMessage("Error" , "Please enter a number Hourly Amount" , JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (hasName(name))
                {
                    showMessage("Error" , "The name {" + name + "} is repeated" , JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    selectedWork.setName(name);
                    selectedWork.setHourlyAmount(hourlyAmount);

                    showMessage("Success" , "Updated" , JOptionPane.INFORMATION_MESSAGE);

                    updateCurrentWork();
                }
            }
            else showMessage("Error" , "Empty name or Hourly Amount" , JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean hasName(final String name)
    {
        if (notEmpty(name))
        {
            for (final Map.Entry<String, List<Works>> entry : this.works.entrySet())
            {
                final List<Works> works = entry.getValue();
                for (final Works work : works) if (work.getName().equals(name)) return true;
            }
        }

        return false;
    }

    private boolean notEmpty(final String value)
    {
        return value != null && !value.isEmpty();
    }

    @Override
    protected void onClickBtnStart()
    {
        if (generalCalculation) return;

        if (checkSelectedWork())
        {
            if (selectedWork.isClose())
            {
                showMessage("Error" , "This work is closed" , JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (startOfWork)
            {
                SwingUtilities.invokeLater(() -> btnStart.setText(txtStartBtnStart));
                startOfWork = false;
                if (timer != null) timer.cancel();

                final String timeText = lblTime.getText();

                selectedWork.setWorked(Time.of(timeText));

                updateCurrentWork();
            }
            else
            {
                SwingUtilities.invokeLater(() -> btnStart.setText(txtStopBtnStart));
                startOfWork = true;
                final Time worked = selectedWork.getWorked();
                setTimer(worked);
            }
        }
    }

    private boolean checkSelectedWork()
    {
        if (selectedWork != null) return true;
        else
        {
            showMessage("Error" , "Not selected work" , JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void showMessage(final String title , final String message , final int type)
    {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null , message , title , type));
    }

    @Override
    protected void onClickBtnNew()
    {
        SwingUtilities.invokeLater(() ->
        {
            final String groupname = getValue("Group name" , "Please enter group name");

            if (notEmpty(groupname))
            {
                boolean existsGroupname = false;
                for (int i = 0, len = comboBoxModel.getSize(); i < len; i++)
                {
                    if (groupname.equals(comboBoxModel.getElementAt(i)))
                    {
                        final int selected = JOptionPane.showConfirmDialog(null , "This group name is exists! Want to add to this group?" , groupname + "is exists" , JOptionPane.YES_NO_CANCEL_OPTION , JOptionPane.QUESTION_MESSAGE , null);

                        switch (selected)
                        {
                            case 0: // Yes
                                existsGroupname = true;
                                break;
                            case 1: // No
                            case 2: // Cancel
                                return;
                        }
                    }
                }


                String name;
                while (true)
                {
                    name = getValue("Name" , "Please enter name");
                    if (notEmpty(name)) break;
                    else showMessage("Error name" , "The name cannot be empty" , JOptionPane.ERROR_MESSAGE);
                }

                int hourlyAmount;
                while (true)
                {
                    final String hourlyAmountStr = getValue("Hourly amount" , "Please enter hourly amount.");
                    if (notEmpty(hourlyAmountStr))
                    {
                        if (hourlyAmountStr.matches("[0-9]*"))
                        {
                            hourlyAmount = Integer.parseInt(hourlyAmountStr);
                            break;
                        }
                        else
                            showMessage("Error hourly amount" , "Please enter only numbers" , JOptionPane.ERROR_MESSAGE);
                    }
                    else
                        showMessage("Error hourly amount" , "The hourly amount cannot be empty" , JOptionPane.ERROR_MESSAGE);
                }

                final int addingNewItemOptionSelected = JOptionPane.showConfirmDialog(null , String.format("Do you want to add this {Name: %s, Hourly amount: %d}?" , name , hourlyAmount) , "Adding new item" , JOptionPane.YES_NO_OPTION , JOptionPane.QUESTION_MESSAGE , null);

                if (addingNewItemOptionSelected == 0 /* 0 = yes */)
                {
                    final Works work = Works.builder().name(name).hourlyAmount(hourlyAmount).registerationTime(LocalDateTime.now()).close(false).build();

                    final List<Works> works = existsGroupname ? this.works.get(groupname) : new ArrayList<>();

                    works.add(work);
                    this.works.put(groupname , works);

                    showMessage("Added" , "A new item was added in group " + groupname , JOptionPane.INFORMATION_MESSAGE);

                    clear();
                    doUpdate(this::loadWorks);
                }
            }
        });
    }

    private String getValue(final String title , final String message)
    {
        return (String) JOptionPane.showInputDialog(null , message , title , JOptionPane.INFORMATION_MESSAGE , null , null , null);
    }

    private void setTimer(final Time worked)
    {
        if (timer != null) timer.cancel();

        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                worked.plus();
                updateTime(worked);
            }
        } , 1000 , 1000);
    }

    private void updateTime(final Time worked)
    {
        SwingUtilities.invokeLater(() ->
        {
            lblTime.setText(worked.toString());
            selectedWork.setWorked(worked);
            setMoney();
        });
    }

    private void updateCurrentWork()
    {
        if (selectedWork != null && selectedGroupName != null && selectedWorkIndex >= 0)
        {
            final List<Works> works = this.works.get(selectedGroupName);
            works.set(selectedWorkIndex , selectedWork);
            this.works.put(selectedGroupName , works);

            setWork(selectedWork);
            doUpdate(null);
        }
    }

    private void clear()
    {
        if (startOfWork) onClickBtnStart();
        selectedWork = null;
        selectedWorkIndex = -1;
        selectedGroupName = null;
        update = false;
        setWork(null);
    }

    private void update()
    {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                doUpdate(null);
            }
        } , 1 , 60000);
    }

    private void doUpdate(final DoUpdate doUpdate)
    {
        new Thread(() ->
        {
            while (update)
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
            }
            System.gc();

            if (this.works.size() > 0)
            {
                update = true;
                final JSONObject worksJson = new JSONObject();
                for (final Map.Entry<String, List<Works>> entry : this.works.entrySet())
                {
                    final String key = entry.getKey();
                    final List<Works> itemsList = entry.getValue();

                    final JSONArray items = new JSONArray();
                    for (final Works work : itemsList)
                    {
                        final JSONObject item = new JSONObject();
                        item.put(Works.JsonKey.NAME , work.getName());
                        item.put(Works.JsonKey.WORKED , work.getWorked().toLong());
                        item.put(Works.JsonKey.HOURLY_AMOUNT , work.getHourlyAmount());
                        item.put(Works.JsonKey.REGISTRATION_TIME , Timestamp.valueOf(work.getRegisterationTime()).getTime());
                        item.put(Works.JsonKey.CLOSING_TIME , Timestamp.valueOf(work.getClosingTime()).getTime());
                        item.put(Works.JsonKey.CLOSE , work.isClose());

                        items.put(item);
                    }
                    worksJson.put(key , items);
                }

                try
                {
                    Files.write(new File(Path.WORKS_JSON).toPath() , worksJson.toString().getBytes(StandardCharsets.UTF_8));
                    if (doUpdate != null) doUpdate.updated();
                }
                catch (IOException e)
                {
                    update = false;
                    System.gc();
                    throw new RuntimeException(e);
                }

            }
            System.gc();
            update = false;
        }).start();
    }

    private interface DoUpdate
    {
        void updated();
    }

    private void setMoney()
    {
        if (checkSelectedWork())
        {
            final float money = calculateMoney(selectedWork.getWorked() , selectedWork.getHourlyAmount());
            SwingUtilities.invokeLater(() -> lblMoney.setText(moneyToString(money)));
        }
        else SwingUtilities.invokeLater(() -> lblMoney.setText("0"));
    }

    public static float calculateMoney(final Time worked , final int hourlyAmount)
    {
        if (hourlyAmount > 0)
        {
            final int hour = worked.getHour();
            final short minute = worked.getMinute();
            final short second = worked.getSecond();

            float money = 0;

            final double minMoney = (hourlyAmount / 60F);
            final double secMoney = (minMoney / 60F);

            if (hour > 0) money += hourlyAmount * hour;
            if (minute > 0) money += minMoney * minute;
            if (second > 0) money += secMoney * second;

            return money;
        }

        return 0;
    }

    public static String moneyToString(float money)
    {
        return NumberFormat.getCurrencyInstance(new Locale("ir" , "IR")).format(money);
    }

    private void generalCalculation()
    {
        if (works != null && works.size() > 0)
        {
            if (selectedGroupName != null && !selectedGroupName.isEmpty() && works.containsKey(selectedGroupName))
            {
                final List<Works> works = this.works.get(selectedGroupName);

                int totalMoney = 0;
                Time time = null;
                for (final Works work : works)
                {
                    final Time worked = work.getWorked();
                    time = worked.plus(time);
                    totalMoney += calculateMoney(worked , work.getHourlyAmount());
                }

                final int finalTotalMoney = totalMoney;
                final String timeToString = time != null ? time.toString() : "00:00:00";
                SwingUtilities.invokeLater(() ->
                {
                    txtWorkName.setText("");
                    txtClosingTime.setText("");
                    txtRegistrationTime.setText("");
                    txtHourlyAmount.setText("");
                    txtWorked.setText(timeToString);
                    lblTime.setText(timeToString);
                    lblMoney.setText(moneyToString(finalTotalMoney));
                });

            }
            else
                showMessage("General calculation" , "Not found works with groupname: " + selectedGroupName , JOptionPane.ERROR_MESSAGE);
        }
        else showMessage("General calculation" , "Works is empty" , JOptionPane.ERROR_MESSAGE);
    }

}
