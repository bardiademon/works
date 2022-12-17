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
import java.util.*;
import java.util.List;

public class HomeController extends HomeView
{
    private final Map<String, List<Works>> works = new HashMap<>();

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

    public HomeController() throws HeadlessException
    {
        super();
        loadWorks();
    }

    @Override
    protected void onClickGroups()
    {
        SwingUtilities.invokeLater(() ->
        {
            final String selectedItem = (String) super.groups.getSelectedItem();
            if (selectedItem != null && !selectedItem.isEmpty())
            {
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
        });
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

                                final String name = item.getString("name");
                                final int hourlyAmount = item.getInt("hourly_amount");
                                final long registerationTimeLong = item.getLong("registeration_time");
                                final long closingTimeLong = item.getLong("closing_time");
                                final LocalDateTime registerationTime = (new Timestamp(registerationTimeLong)).toLocalDateTime();
                                final LocalDateTime closingTime = (new Timestamp(closingTimeLong)).toLocalDateTime();
                                final long worked = item.getLong("worked");
                                final boolean close = item.getBoolean("close");

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

    }

    @Override
    protected void onClickBtnStart()
    {

    }

    @Override
    protected void onClickBtnNew()
    {

    }
}
