package com.bardiademon.works.controller;

import com.bardiademon.works.data.model.Works;
import com.bardiademon.works.utils.Path;
import com.bardiademon.works.view.HomeView;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

            if (selectedWork.isClose())
                selectedWork.setClosingTime(LocalDateTime.now());

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
            if (selectedIndex >= 0)
            {
                final String selectedItem = (String) super.groups.getSelectedItem();
                if (selectedItem != null && !selectedItem.isEmpty())
                {
                    selectedWorkIndex = selectedIndex;
                    final List<Works> works = this.works.get(selectedItem);
                    final Works work = works.get(selectedIndex);
                    setWork(work);
                }
            }
        });
    }

    private void setWork(final Works work)
    {
        SwingUtilities.invokeLater(() ->
        {
            txtWorkName.setText(work.getName());
            txtHourlyAmount.setText(String.valueOf(work.getHourlyAmount()));
            txtClosingTime.setText(work.getClosingTime().toString());
            txtRegistrationTime.setText(work.getRegisterationTime().toString());
            txtWorked.setText(work.getWorked().toString());
            chkClose.setSelected(work.isClose());
            lblTime.setText(work.getWorked().toString());

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
                            final List<Works> works = new ArrayList<>();
                            for (final Object jsonObj : jsonArrayItem)
                            {
                                final JSONObject item = (JSONObject) jsonObj;

                                final String name = item.getString(Works.JsonKey.NAME);
                                final int hourlyAmount = item.getInt(Works.JsonKey.HOURLY_AMOUNT);
                                final long registerationTimeLong = item.getLong(Works.JsonKey.REGISTERATION_TIME);
                                final long closingTimeLong = item.getLong(Works.JsonKey.CLOSING_TIME);
                                final LocalDateTime registerationTime = (new Timestamp(registerationTimeLong)).toLocalDateTime();
                                final LocalDateTime closingTime = (new Timestamp(closingTimeLong)).toLocalDateTime();
                                final long worked = item.getLong(Works.JsonKey.WORKED);
                                final boolean close = item.getBoolean(Works.JsonKey.CLOSE);

                                final Works work = Works.builder()
                                        .name(name)
                                        .hourlyAmount(hourlyAmount)
                                        .registerationTime(registerationTime)
                                        .closingTime(closingTime)
                                        .worked(new Time(worked))
                                        .close(close)
                                        .build();

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
            for (final Works work : works) listModel.addElement(work.toString());
        });

    }

    @Override
    protected void onClickBtnUpdate()
    {
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

                selectedWork.setWorked(Time.valueOf(timeText));

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

    }

    private void setTimer(final Time worked)
    {
        final LocalTime localTime = worked.toLocalTime();

        final int[] hour = {localTime.getHour()};
        final int[] min = {localTime.getMinute()};
        final int[] sec = {localTime.getSecond()};

        if (timer != null) timer.cancel();

        timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                sec[0]++;
                if (sec[0] >= 60)
                {
                    sec[0] = 0;

                    min[0]++;
                    update = true;

                    if (min[0] >= 60)
                    {
                        min[0] = 0;

                        hour[0]++;
                    }
                }

                updateTime(hour[0] , min[0] , sec[0]);
            }
        } , 1000 , 1000);
    }

    private void updateTime(final int hour , final int min , final int sec)
    {
        SwingUtilities.invokeLater(() ->
        {
            final Time time = Time.valueOf(String.format("%d:%d:%d" , hour , min , sec));
            lblTime.setText(time.toString());
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

            update = true;
        }
    }

    private void update()
    {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                if (update)
                {
                    update = false;

                    final HomeController _this = HomeController.this;
                    if (_this.works.size() > 0)
                    {
                        final JSONObject worksJson = new JSONObject();
                        for (final Map.Entry<String, List<Works>> entry : _this.works.entrySet())
                        {
                            String key = entry.getKey();
                            List<Works> itemsList = entry.getValue();
                            final JSONArray items = new JSONArray();
                            for (final Works work : itemsList)
                            {
                                final JSONObject item = new JSONObject();
                                item.put(Works.JsonKey.NAME , work.getName());
                                item.put(Works.JsonKey.WORKED , work.getWorked().getTime());
                                item.put(Works.JsonKey.HOURLY_AMOUNT , work.getHourlyAmount());
                                item.put(Works.JsonKey.REGISTERATION_TIME , Timestamp.valueOf(work.getRegisterationTime()).getTime());
                                item.put(Works.JsonKey.CLOSING_TIME , Timestamp.valueOf(work.getClosingTime()).getTime());
                                item.put(Works.JsonKey.CLOSE , work.isClose());

                                items.put(item);
                            }
                            worksJson.put(key , items);
                        }

                        try
                        {
                            Files.write(new File(Path.WORKS_JSON).toPath() , worksJson.toString().getBytes(StandardCharsets.UTF_8));
                        }
                        catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        } , 1 , 1000);
    }
}
