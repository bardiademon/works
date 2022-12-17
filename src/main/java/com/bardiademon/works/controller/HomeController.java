package com.bardiademon.works.controller;

import com.bardiademon.works.data.model.Works;
import com.bardiademon.works.utils.Path;
import com.bardiademon.works.view.HomeView;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HomeController extends HomeView
{
    private final Map<String, Works> works = new HashMap<>();

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();

    public HomeController() throws HeadlessException
    {
        super();
        loadWorks();
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
                        final JSONObject item = jsonWorks.getJSONObject(key);

                        final String name = item.getString("name");
                        final int hourlyAmount = item.getInt("hourly_amount");
                        final long registerationTimeLong = item.getLong("registeration_time");
                        final long closingTimeLong = item.getLong("closing_time");
                        final LocalDateTime registerationTime = (new Timestamp(registerationTimeLong)).toLocalDateTime();
                        final LocalDateTime closingTime = (new Timestamp(closingTimeLong)).toLocalDateTime();
                        final long worked = item.getLong("worked");
                        final boolean close = item.getBoolean("close");

                        final Works works = Works.builder()
                                .name(name)
                                .hourlyAmount(hourlyAmount)
                                .registerationTime(registerationTime)
                                .closingTime(closingTime)
                                .worked(new Time(worked))
                                .close(close)
                                .build();

                        this.works.put(key , works);
                    }
                }
                setList();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }

        }).start();
    }

    private void setList()
    {
        SwingUtilities.invokeLater(() ->
        {
            comboBoxModel.removeAllElements();
            listModel.clear();

            super.groups.setModel(comboBoxModel);
            super.lstWorks.setModel(listModel);

            if (works.size() > 0)
            {
                works.forEach((groupName , works) ->
                {
                    comboBoxModel.addElement(groupName);
                    listModel.addElement(works.toString());
                });
            }
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
