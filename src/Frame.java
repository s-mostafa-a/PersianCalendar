import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.*;
import java.util.Calendar;
import java.util.Collection;
import java.util.Scanner;

/**
 * Created by ahmadi on 5/9/16.
 */
public class Frame extends JFrame {
    private int day;
    private int mon;
    JPanel right;
    JPanel left;
    JPanel center;
    JTextArea ro;
    JScrollPane roS;
    JTextArea yadavar;
    JTextArea yad;
    JScrollPane yadS;
    JScrollPane yadavarS;
    JPanel up;
    JPanel down;
    JLabel yearLabel;
    JButton leftArrow;
    JLabel monthLabel;
    JButton rightArrow;
    JPanel weekDays;
    JPanel days;
    private int startingDayF;
    private int nextMonth = 0;
    private int nextYear = 0;
    private int curYear;
    GridBagConstraints gbc = new GridBagConstraints();
    backAndForwardHandler BAFHandler = new backAndForwardHandler();
    private int GoalDay;
    private int GoalMonth;
    private int GoalYear;
    JPanel[] dayPanelList;
    JButton[] dayButton;
    dayHandler DHandler = new dayHandler();
    String[] Yaddasht = new String[12 * 31 + 1];
    String[] yadaavar = new String[12 * 31 + 1];
    String[] monasebat;
    JButton lastSelectedDay;
    JButton sourceOfDayHandler;
    private int thatMonthWhichWeAreIn;
    private int thatMonthWhichWeWereIn;
    private int thatYearWhichWeAreIn;
    JTextArea gha;
    JTextArea mil;
    Color leftLabelsColor = new Color(204, 0, 102);
    Color leftTextAreaColor = new Color(255, 153, 255);
    Color disableColor = new Color(96, 96, 96);
    JLabel ghamari;
    JLabel miladi;
    JLabel rooy;
    JLabel yaadavar;
    JLabel yaad;
    JButton OKForYaddasht;
    JButton addYadaavar;
    PopupMenu rclick;
    private int todayIsDay;
    private int todayIsMonth;
    private int todayIsYear;
    JMenuBar menu = new JMenuBar();
    public Frame(int theStartingDay, int month, int year) throws AWTException {
        super("تقویم پارسی");
        curYear = year;
        day = theStartingDay;
        mon = month;
        setSize(1000, 600);
        setVisible(true);
        setDefaultCloseOperation(ICONIFIED);
        setResizable(false);
        setLayout(new GridBagLayout());

        setJMenuBar(menu);
        menuJobs();

        menu.updateUI();
        startingDayF = theStartingDay;
        day = theStartingDay % 7;
        if (theStartingDay == 0)
            day = 7;
        mon = mon % 12;
        if (mon == 0)
            mon = 12;
        //left
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        left = new JPanel();
        left.setBackground(leftLabelsColor);
        add(left, gbc);
        //center
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        center = new JPanel();
        add(center, gbc);
        left.setLayout(new GridBagLayout());
        center.setLayout(new GridBagLayout());
        //right
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        right = new JPanel();
        add(right, gbc);
        BorderLayout rightsLayout = new BorderLayout();
        right.setLayout(rightsLayout);
        right.setBackground(leftLabelsColor);
        ///yaddasht



////////////////////////////////////////////////////////////////////////////////////////////////////////

        yaad = new JLabel("یادداشت", SwingConstants.CENTER);
        yaad.setBackground(leftLabelsColor);
        right.add(yaad, rightsLayout.NORTH);

        yad = new JTextArea();
        yadS = new JScrollPane(yad);
        yadS.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        yad.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        yad.setToolTipText("یادداشت های هر روز");
        right.add(yadS, rightsLayout.CENTER);
        right.updateUI();

        OKForYaddasht = new JButton("ثبت");
        right.add(OKForYaddasht, rightsLayout.SOUTH);
        OKForYaddasht.addActionListener(new OKHandler());

///////////////////////////////////////////////////////////////////////////////////////////////////////////

        //miladi
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JPanel j = new JPanel();
        gbc.fill = GridBagConstraints.BOTH;
        left.add(j, gbc);
        miladi = new JLabel("تاریخ میلادی");
        j.add(miladi);
        j.setBackground(leftLabelsColor);
        miladi.setBackground(leftLabelsColor);
        left.add(j, gbc);
        ////
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mil = new JTextArea();
        mil.setToolTipText("تاریخ روز در قالب میلادی");
        mil.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        mil.setEditable(false);
        JScrollPane milS = new JScrollPane(mil);
        milS.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        left.add(milS, gbc);
        mil.setBackground(leftTextAreaColor);
        //ghamari
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.CENTER;
        ghamari = new JLabel("تاریخ قمری");
        ghamari.setBackground(leftLabelsColor);
        left.add(ghamari, gbc);
        ////
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gha = new JTextArea();
        gha.setToolTipText("تاریخ روز در قالب هجری قمری");
        gha.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        gha.setEditable(false);
        JScrollPane ghaS = new JScrollPane(gha);
        ghaS.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        left.add(ghaS, gbc);
        gha.setBackground(leftTextAreaColor);
        //rooydad
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.CENTER;
        rooy = new JLabel("رویداد ها");
        rooy.setBackground(leftLabelsColor);
        left.add(rooy, gbc);
        ////
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 5.0;
        gbc.weighty = 5.0;
        gbc.fill = GridBagConstraints.BOTH;
        ro = new JTextArea();
        ro.setToolTipText("رویدادهای تاریخی امروز");
        ro.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        ro.setEditable(false);
        roS = new JScrollPane(ro);
        roS.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        left.add(roS, gbc);
        ro.setBackground(leftTextAreaColor);
        //yadaavar
        /////////////////////////////////////////////////////////////////////////////here should be edited
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.CENTER;
        yaadavar = new JLabel("یادآور");
        yaadavar.setBackground(leftLabelsColor);
        left.add(yaadavar, gbc);
        ////
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.weightx = 5.0;
        gbc.weighty = 5.0;
        gbc.fill = GridBagConstraints.BOTH;
        yadavar = new JTextArea();
        yadavar.setToolTipText("یادآور کارها");
        yadavar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        yadavarS = new JScrollPane(yadavar);
        yadavar.setEditable(false);
        yadavarS.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        left.add(yadavarS, gbc);
        ////
        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JPanel jpnl = new JPanel();
        addYadaavar = new JButton("اضافه کردن یادآور");
        addYadaavar.addActionListener(new addYadaavarHandler());
        left.add(addYadaavar, gbc);
        center.updateUI();
        left.updateUI();
        roS.updateUI();
        yadavarS.updateUI();
        yadavar.setBackground(leftTextAreaColor);
        //////////////////////////////////////////////////////////////////////////////////////
        //up
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        up = new JPanel();
        center.add(up, gbc);
        up.setLayout(new GridBagLayout());
        up.setBackground(new Color(255, 128, 0));
        //down
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 10.0;
        gbc.fill = GridBagConstraints.BOTH;
        down = new JPanel();
        center.add(down, gbc);
        down.setLayout(new GridBagLayout());
        //////////////////up
        //Year label
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.CENTER;
        yearLabel = new JLabel("Year");
        up.add(yearLabel, gbc);
        yearLabel.setToolTipText("سال");
        //<
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        leftArrow = new JButton("<");
        leftArrow.addActionListener(BAFHandler);
        up.add(leftArrow, gbc);
        leftArrow.setToolTipText("ماه بعد");
        //month label
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.CENTER;
        monthLabel = new JLabel();
        up.add(monthLabel, gbc);
        monthLabel.setToolTipText("ماه");
        //>
        gbc.gridx = 5;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        rightArrow = new JButton(">");
        rightArrow.addActionListener(BAFHandler);
        up.add(rightArrow, gbc);
        rightArrow.setToolTipText("ماه قبل");
        up.updateUI();
        /////////////down
        weekDays = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        down.add(weekDays, gbc);
        weekDays.setLayout(new GridBagLayout());
        weekDays.updateUI();
        weekDays.setBackground(new Color(255, 153, 51));
        JLabel[] weekDayList = new JLabel[7];
        for (int i = 0; i <= 6; i++) {
            gbc.gridx = 6 - i;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.CENTER;
            weekDayList[i] = new JLabel(i + "شنبه");
            weekDays.add(weekDayList[i], gbc);
        }
        weekDayList[0].setText("شنبه");
        weekDayList[6].setText("جمعه");
        draw(day, mon, curYear);
        thatMonthWhichWeAreIn = mon;
        thatYearWhichWeAreIn = curYear;
        rooydadReader();
        yaddashtReader();
        yadaavarReader();
        displayTray(day, mon, curYear);

    }


    /**
     *  sets the day of today
     *  and also draws todays calendar
     */
    /////////////////////////////////////////////////////////////////////////////////////////////get the day
    public void TodayIs(int day, int month, int year){
        todayIsDay = day;
        todayIsMonth = month;
        todayIsYear = year;
        drawTodaysCalender(todayIsDay, todayIsMonth, todayIsYear);
    }

//// TODO: 5/27/16  
    /**
     *  does the whole jobs about menu bar
     */
    private void menuJobs() {
        Font f = new Font("Arial", Font.PLAIN, 14);
        menuHandler handler = new menuHandler();
        menu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JMenu insert = new JMenu("درج");
        insert.setMnemonic('n');
        insert.setDisplayedMnemonicIndex(0);
        insert.setFont(f);
        insert.setToolTipText("<html><p align=right>اضافه کن");

        JMenu edit = new JMenu("ویرایش");
        edit.setMnemonic(',');
        edit.setDisplayedMnemonicIndex(0);
        edit.setFont(f);
        edit.setToolTipText("<html><p align=right>" + "ویرایش کن");

        menu.add(edit);
        menu.add(insert);


        JMenuItem addEvent = new JMenuItem("اضافه کردن یادآور");
        addEvent.setMnemonic('v');
        addEvent.setDisplayedMnemonicIndex(11);
        addEvent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        addEvent.setFont(f);
        addEvent.addActionListener(handler);
        addEvent.setToolTipText("<html><p align=right>" + "یادآور اضافه کن");

        JMenuItem addNote = new JMenuItem("اضافه کردن یادداشت");
        addNote.setMnemonic('d');
        addNote.setDisplayedMnemonicIndex(11);
        addNote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        addNote.setFont(f);
        addNote.addActionListener(handler);
        addNote.setToolTipText("<html><p align=right>" + "یادداشت اضافه کن");



        JMenuItem editNote = new JMenuItem("ویرایش یادداشت ها");
        editNote.setMnemonic('d');
        editNote.setDisplayedMnemonicIndex(7);
        editNote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        editNote.setFont(f);
        editNote.addActionListener(handler);
        editNote.setToolTipText("<html><p align=right>" + "یادداشت را ویرایش کن");

        JMenuItem copyDate = new JMenuItem("کپی کردن تاریخ");
        copyDate.setMnemonic('j');
        copyDate.setDisplayedMnemonicIndex(10);
        copyDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
        copyDate.setFont(f);
        copyDate.addActionListener(handler);
        copyDate.setToolTipText("<html><p align=right>" + "تاریخ روز را کپی کن");

        JMenuItem copyOccasion = new JMenuItem("کپی کردن رویداد");
        copyOccasion.setMnemonic('l');
        copyOccasion.setDisplayedMnemonicIndex(10);
        copyOccasion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        copyOccasion.setFont(f);
        copyOccasion.addActionListener(handler);
        copyOccasion.setToolTipText("<html><p align=right>" + "رویداد روز را کپی کن");

        insert.add(addEvent);
        insert.add(addNote);
        edit.add(editNote);
        edit.add(copyDate);
        edit.add(copyOccasion);

    }








    /////////////////////////////////////////////////////////////////////////////////////////////draw

    /**
     * draws the incoming month
     * @param theStartingDay    should be a number between 1 to 7 declaring the starting of that month is in which day of weak
     * @param month     the month which we want to draw
     * @param year      the year which that month is in
     */
    public void draw(int theStartingDay, int month, int year) {

        thatMonthWhichWeAreIn = month;
        thatMonthWhichWeAreIn += 12;
        thatMonthWhichWeAreIn %= 12;
        if (thatMonthWhichWeAreIn == 0)
            thatMonthWhichWeAreIn = 12;
        thatYearWhichWeAreIn = year;
        if (thatYearWhichWeAreIn != 1395)
            yad.setEditable(false);
        else
            yad.setEditable(true);
        if (month < 0) {
            month = -1 * month;
            month = month % 12;
            month = 12 - month;
        }
        else {
            month = month % 12;
            if (month == 0)
                month = 12;
        }
        if (theStartingDay < 0) {
            theStartingDay = -1 * theStartingDay;
            theStartingDay = theStartingDay % 7;
            theStartingDay = 7 - theStartingDay;
        }
        else {
            theStartingDay = theStartingDay % 7;
            if (theStartingDay == 0)
                theStartingDay = 7;
        }
        if (days != null) {
            days.setEnabled(false);
            days.setVisible(false);
            down.remove(days);
        }
        days = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 6.0;
        gbc.weighty = 6.0;
        gbc.fill = GridBagConstraints.BOTH;
        down.add(days, gbc);
        days.setLayout(new GridBagLayout());
        days.updateUI();
        monthLabel.setText(getMonthName(month));
        yearLabel.setText(year + "");
        dayPanelList = new JPanel[42];
        for (int i = 0; i <= 5; i++) {
            for (int j = 1; j <= 7; j++) {
                gbc.gridx = 8 - j;
                gbc.gridy = i;
                gbc.weightx = 1.0;
                gbc.weighty = 1.0;
                gbc.fill = GridBagConstraints.BOTH;//////
                dayPanelList[i * 7 + j - 1] = new JPanel();
                days.add(dayPanelList[i * 7 + j - 1], gbc);
                dayPanelList[i * 7 + j - 1].setLayout(new GridBagLayout());
                dayPanelList[i * 7 + j - 1].setBackground(new Color(255, 178, 102));
                dayPanelList[i * 7 + j - 1].updateUI();
            }
        }
        days.updateUI();
        down.updateUI();
        int dayNum = 31;
        if ((isKabise(year) == false) && (month == 12))
            dayNum = 29;
        else if ((month > 6) || ((isKabise(year) == true) && (month == 12)))
            dayNum = 30;
        dayButton = new JButton[dayNum];
        for (int i = 1; i <= dayNum; i++) {
            dayButton[i - 1] = new JButton(i + "");
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            dayPanelList[theStartingDay + i - 2].add(dayButton[i - 1], gbc);
            dayButton[i - 1].addActionListener(DHandler);
            Transferer t = new Transferer(i);
            dayButton[i - 1].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if(SwingUtilities.isRightMouseButton(e)){


                        JPopupMenu menu = new JPopupMenu();
                        // Create JMenuItems
                        JMenuItem addEvent = new JMenuItem("اضافه کردن یادآور");
                        addEvent.setMnemonic('v');
                        addEvent.setDisplayedMnemonicIndex(11);
                        addEvent.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
                        addEvent.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dayHandler d = new dayHandler();
                                ActionEvent act = new ActionEvent(dayButton[t.get() - 1], 1, "");
                                d.actionPerformed(act);
                                ActionEvent ev = new ActionEvent(addYadaavar, 10, "");
                                addYadaavarHandler a = new addYadaavarHandler();
                                a.actionPerformed(ev);
                            }
                        });
                        addEvent.setToolTipText("<html><p align=right>" + "یادآور اضافه کن");

                        JMenuItem addNote = new JMenuItem("اضافه کردن یادداشت");
                        addNote.setMnemonic('d');
                        addNote.setDisplayedMnemonicIndex(11);
                        addNote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
                        addNote.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dayHandler d = new dayHandler();
                                ActionEvent act = new ActionEvent(dayButton[t.get() - 1], 1, "");
                                d.actionPerformed(act);
                                yad.requestFocus();
                                yad.setText("");
                            }
                        });
                        addNote.setToolTipText("<html><p align=right>" + "یادداشت اضافه کن");



                        JMenuItem editNote = new JMenuItem("ویرایش یادداشت ها");
                        editNote.setMnemonic('d');
                        editNote.setDisplayedMnemonicIndex(7);
                        editNote.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
                        editNote.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dayHandler d = new dayHandler();
                                ActionEvent act = new ActionEvent(dayButton[t.get() - 1], 1, "");
                                d.actionPerformed(act);
                                yad.requestFocus();
                            }
                        });
                        editNote.setToolTipText("<html><p align=right>" + "یادداشت را ویرایش کن");

                        JMenuItem copyDate = new JMenuItem("کپی کردن تاریخ");
                        copyDate.setMnemonic('j');
                        copyDate.setDisplayedMnemonicIndex(10);
                        copyDate.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
                        copyDate.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dayHandler d = new dayHandler();
                                ActionEvent act = new ActionEvent(dayButton[t.get() - 1], 1, "");
                                d.actionPerformed(act);
                                StringSelection stringSelection = new StringSelection(thatYearWhichWeAreIn + "/" + thatMonthWhichWeAreIn + "/" + t.get());
                                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                                clpbrd.setContents(stringSelection, null);
                            }
                        });
                        copyDate.setToolTipText("<html><p align=right>" + "تاریخ روز را کپی کن");

                        JMenuItem copyOccasion = new JMenuItem("کپی کردن رویداد");
                        copyOccasion.setMnemonic('l');
                        copyOccasion.setDisplayedMnemonicIndex(10);
                        copyOccasion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
                        copyOccasion.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dayHandler d = new dayHandler();
                                ActionEvent act = new ActionEvent(dayButton[t.get() - 1], 1, "");
                                d.actionPerformed(act);
                                StringSelection stringSelection = new StringSelection(ro.getText());
                                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                                clpbrd.setContents(stringSelection, null);
                            }
                        });
                        copyOccasion.setToolTipText("<html><p align=right>" + "رویداد روز را کپی کن");

                        // Add JMenuItems to JPopupMenu
                        menu.add(addEvent);
                        menu.add(addNote);
                        menu.add(editNote);
                        menu.add(copyDate);
                        menu.add(copyOccasion);
                        menu.show(e.getComponent(), e.getX(), e.getY());

                        // Get the location of the point 'on the screen'
                    }
                }
            });
            dayPanelList[theStartingDay + i - 2].updateUI();
            if ((year == GoalYear)&&(month == GoalMonth)&&(i  == GoalDay))
                dayButton[i - 1].setSelected(true);
        }
        for (int i = 0; i < 42; i++)
            dayPanelList[i].updateUI();
        days.setOpaque(true);
        days.updateUI();
    }

    /////////////////////////////////////////////////////////////////////////////////////go today

    /**
     * draw actually the month wich we are in and shows the day wich we are in
     * @param day   which day of month we are in
     * @param month     which month we are in
     * @param year      which year we are in
     */
    public void drawTodaysCalender(int day, int month, int year) {
        GoalDay = day;
        GoalMonth = month;
        GoalYear = year;
        if ((month > 12) || (month < 1))
            System.err.println("Invalid month!");
        if (curYear + nextYear == year) {
            while (month > (mon + nextMonth)) {
                ActionEvent act = new ActionEvent(leftArrow, 1, "");
                BAFHandler.actionPerformed(act);
            }
            while (month < (mon + nextMonth)) {
                ActionEvent act = new ActionEvent(rightArrow, 2, "");
                BAFHandler.actionPerformed(act);
            }
            dayButton[day - 1].setSelected(true);
        }
        else if (curYear + nextYear < year) {
            ActionEvent act = new ActionEvent(leftArrow, 1, "");
            while (year > curYear + nextYear) {
                BAFHandler.actionPerformed(act);
            }
            BAFHandler.actionPerformed(act);
            while (month > (mon + nextMonth)) {
                BAFHandler.actionPerformed(act);
            }
        }
        else {
            ActionEvent act = new ActionEvent(rightArrow, 2, "");
            while (year <= curYear + nextYear) {
                BAFHandler.actionPerformed(act);
            }
            drawTodaysCalender(day, month, year);
        }
        ActionEvent e = new ActionEvent(new JButton(day + ""), 1 , day + "");
        e.setSource(new JButton(day + ""));
        DHandler.actionPerformed(e);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////kabise

    /**
     * indicates if incoming shamsi year is kabise or not
     * @param year  year
     * @return boolean
     */
    public boolean isKabise(int year) {
        if (((year % 33) == 1) || ((year % 33) == 5) || ((year % 33) == 9) || ((year % 33) == 13) || ((year % 33) == 17) || ((year % 33) == 22) || ((year % 33) == 26) || ((year % 33) == 30))
            return true;
        else
            return false;
    }

    /**
     * this plays th tray
     * @param year  year which we are in
     * @param month     month which we are in
     * @param day       day which we are in
     * @throws AWTException
     */
    private void displayTray(int year, int month, int day) throws AWTException {
        try{
            rclick = new PopupMenu("Popup");
            SystemTray tray = SystemTray.getSystemTray();
            ImageIcon icon;
            icon = new ImageIcon("image.jpg");
            Image image = icon.getImage();
            TrayIcon trayIcon = new TrayIcon(image, "Tray Demo" ,rclick);
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Persian Calendar");
            TMListener tml = new TMListener();
            trayIcon.addMouseListener(tml);
            tray.add(trayIcon);
            trayIcon.displayMessage("Persian Calendar Started", "Click Here To See", TrayIcon.MessageType.INFO);
        }
        catch(AWTException awtException){
            awtException.printStackTrace();
        }
    }








///////////////////////////////////////////////////////////////////////////////////////////////rooydad reader

    /**
     * reads the monasebats of a day from the file
     */
    public void rooydadReader(){
        int month = 1;
        int day = 1;
        int i = 0;
        monasebat = new String[12 * 31];
        while(month != 13) {
            String temp = "";
            try {

                FileReader fileReader = new FileReader(String.format("src/Data/Monasebat/%d/Day (%d).txt",month,day));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String r;
                while ((r = bufferedReader.readLine()) != null) {
                    Scanner scan = new Scanner(r);
                    while (scan.hasNext()){
                        temp += scan.next();
                        temp += " ";
                    }
                    temp += "\n";
                }
                monasebat[i] = new String(temp);
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file");
            }
            day++;
            if (day == 32){
                day = 1;
                month ++;
            }
            i++;
        }

    }




////////////////////////////////////////////////////////////////////////////////////////yaddasht writer

    /**
     * writes the existing yaddashts in array on the file
     */
    public void yaddashtWriter(){
        int month = 1;
        int day = 1;
        int i = 1;
        while(month != 13) {
            String temp = "";
            try {
                File file = new File(String.format("src/Data/Yaddasht/%d/Day (%d).txt",month, day));
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                if (Yaddasht[i] != null)
                    writer.write(Yaddasht[i]);
                writer.flush();
                writer.close();
            } catch (FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file");
            }
            day++;
            if (day == 32){
                day = 1;
                month ++;
            }
            i++;
        }

    }





/////////////////////////////////////////////////////////////////////////yadavar writer


    /**
     * writes the existing yadaavars in array on the file
     */
    public void yadaavarWriter(){
        int month = 1;
        int day = 1;
        int i = 1;
        while(month != 13) {
            String temp = "";
            try {
                File file = new File(String.format("src/Data/Yadaavar/%d/Day (%d).txt",month, day));
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                if (yadaavar[i] != null)
                    writer.write(yadaavar[i]);
                writer.flush();
                writer.close();
            } catch (FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file");
            }
            day++;
            if (day == 32){
                day = 1;
                month ++;
            }
            i++;
        }

    }










/////////////////////////////////////////////////////////////////////////yadaavar reader


    /**
     * reads the yadaavaris of a day from the file
     */
    public void yadaavarReader(){
        int month = 1;
        int day = 1;
        int i = 1;
        while(month != 13) {
            String temp = "";
            try {

                FileReader fileReader = new FileReader(String.format("src/Data/Yadaavar/%d/Day (%d).txt",month,day));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String r;
                while ((r = bufferedReader.readLine()) != null) {
                    Scanner scan = new Scanner(r);
                    while (scan.hasNext()){
                        temp += scan.next();
                        temp += " ";
                    }
                    temp += "\n";
                }
                yadaavar[i] = new String(temp);
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file");
            }
            day++;
            if (day == 32){
                day = 1;
                month ++;
            }
            i++;
        }

    }

/////////////////////////////////////////////////////////////////////////yaddasht reader

    /**
     * reads the yaddashts of a day from the file
     */
    public void yaddashtReader(){
        int month = 1;
        int day = 1;
        int i = 1;
        while(month != 13) {
            String temp = "";
            try {

                FileReader fileReader = new FileReader(String.format("src/Data/Yaddasht/%d/Day (%d).txt",month,day));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String r;
                while ((r = bufferedReader.readLine()) != null) {
                    Scanner scan = new Scanner(r);
                    while (scan.hasNext()){
                        temp += scan.next();
                        temp += " ";
                    }
                    temp += "\n";
                }
                Yaddasht[i] = new String(temp);
                bufferedReader.close();
            } catch (FileNotFoundException ex) {
                System.out.println(
                        "Unable to open file");
            } catch (IOException ex) {
                System.out.println(
                        "Error reading file");
            }
            day++;
            if (day == 32){
                day = 1;
                month ++;
            }
            i++;
        }
        //////////////////////////////////////////////////////////////////inja avvalin alarm todo


    }


    /**
     * returns the hours between entering date with now
     * @param hour
     * @param day
     * @param month
     * @return
     */
    public int fasleZamani(int hour,int day, int month){
        Calendar c = Calendar.getInstance();
        int mon = todayIsMonth;
        int d = todayIsDay;
        if(mon < month)
            return -1;
        if (mon == month){
            if (day < d)
                return -1;
            else if (day == d) {
                if (hour < c.get(Calendar.HOUR))
                    return -1;
                else
                    return hour - c.get(Calendar.HOUR);
            }
            return (day - d) * 24 + hour - c.get(Calendar.HOUR);
        }
        if (month <= 7)
            return (month - mon) * 744 + (day - d) * 24 + hour - c.get(Calendar.HOUR);
        else
            return  (month - 7) * 720 + (7 - mon) * 744 + (day - d) * 24 + hour - c.get(Calendar.HOUR);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////handler

//// TODO: 5/27/16  


    /**
     * handler of menu bar
     */
    private class menuHandler implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JMenuItem m = (JMenuItem)e.getSource();
            
            
            if (m.getText() == "اضافه کردن یادآور"){
                drawTodaysCalender(todayIsDay ,todayIsMonth , todayIsYear);
                ActionEvent ev = new ActionEvent(addYadaavar, 10, "");
                addYadaavarHandler a = new addYadaavarHandler();
                a.actionPerformed(ev);
            }
            else if(m.getText() == "اضافه کردن یادداشت"){
                drawTodaysCalender(todayIsDay ,todayIsMonth , todayIsYear);
                yad.requestFocus();
                yad.setText("");
            }
            else if (m.getText() == "ویرایش یادداشت ها"){
                drawTodaysCalender(todayIsDay ,todayIsMonth , todayIsYear);
                yad.requestFocus();
            }
            else if (m.getText() == "کپی کردن تاریخ"){
                StringSelection stringSelection = new StringSelection(todayIsYear + "/" + todayIsMonth + "/" + todayIsDay);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
            else if (m.getText() == "کپی کردن رویداد"){
                StringSelection stringSelection = new StringSelection(ro.getText());
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
        }
    }









    //////////////////////////////////////////////////////////////////////////////////////////////////////handler
    /**
     * handler of going to other months
     */
    private class backAndForwardHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            sourceOfDayHandler = null;
            ro.setText("");
            gha.setText("");
            JButton source = (JButton) e.getSource();
            int month;
            if (source.getText() == ">") {
                month = mon + nextMonth - 1;
                startingDayF = startingDayF - getNumOfDay(month);
                if (((month == 0) || (month == 12)) && (isKabise(curYear + nextYear - 1)))
                    startingDayF--;
                nextMonth--;
                if (nextMonth == -12)
                    nextMonth = 0;
                if (month % 12 == 0)
                    nextYear--;
            }
            else {
                month = mon + nextMonth + 1;
                startingDayF = startingDayF + getNumOfDay(month - 1);
                if (((month == 13) || (month == 1)) && (isKabise(curYear + nextYear)))
                    startingDayF++;
                nextMonth++;
                if (nextMonth == 12)
                    nextMonth = 0;
                if (month % 12 == 1)
                    nextYear++;
            }

            draw(startingDayF, month, curYear + nextYear);
            if(curYear + nextYear != 1395){
                yad.setBackground(Color.GRAY);
                gha.setBackground(Color.GRAY);
                ro.setBackground(Color.GRAY);
                left.setBackground(disableColor);
                yadavar.setBackground(Color.GRAY);
                right.setBackground(disableColor);
            }
            else{
                right.setBackground(leftLabelsColor);
                yad.setBackground(leftTextAreaColor);
                gha.setBackground(leftTextAreaColor);
                ro.setBackground(leftTextAreaColor);
                left.setBackground(leftLabelsColor);
                yadavar.setBackground(leftTextAreaColor);
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////handler

    /**
     * handler of ok of yaddasht
     */
    private class OKHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (thatYearWhichWeAreIn == 1395) {
                if (sourceOfDayHandler != null) {
                    Yaddasht[(thatMonthWhichWeWereIn - 1) * 31 + Integer.parseInt(sourceOfDayHandler.getText())] = yad.getText();
                }
                yaddashtWriter();
            }
        }
    }



    /////////////////////////////////////////////////////////////////////////////////////////////handler


    /**
     * handler of ok of yadaavar
     */
    private class  addYadaavarHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (sourceOfDayHandler == null){
                JOptionPane.showMessageDialog(null, "ابتدا یک روز را انتخاب کنید!");
            }
            else {
                String yadaavarsMatn;
                String yadaavarsHour;
                setEnabled(false);
                Color c = new Color(51, 153, 255);
                JFrame inputBox = new JFrame("تعریف یادآور جدید");
                inputBox.setBackground(c);
                inputBox.setVisible(true);
                inputBox.setLayout(new GridBagLayout());
                inputBox.setSize(300, 200);
                inputBox.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                JLabel hour = new JLabel("ساعت یادآوری:");
                JPanel JPForList = new JPanel();
                DefaultListModel deList = new DefaultListModel();
                for(int i = 0; i <= 23; i++)
                    deList.addElement("" + i);
                JList listOfHours = new JList(deList);
                listOfHours.setVisibleRowCount(5);
                JScrollPane scroll = new JScrollPane(listOfHours);
                JPForList.add(scroll);
                JLabel pm = new JLabel("متن یادآور:");
                JTextField text = new JTextField();
                text.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                JButton OK = new JButton("ثبت");
                JButton cancel = new JButton("انصراف");
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        inputBox.dispose();
                        setEnabled(true);
                    }
                });
                OK.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(thatYearWhichWeAreIn != 1395)
                            ;
                        else{
                                writeByIndex( listOfHours.getSelectedIndex(),text.getText());
                                if (listOfHours.getSelectedIndex() != -1) {
                                    inputBox.dispose();
                                    setEnabled(true);
                            }

                        }
                    }
                });
                yadaavarWriter();
                gbc.gridx = 2;
                gbc.gridy = 2;
                gbc.fill = gbc.fill = GridBagConstraints.CENTER;
                inputBox.add(JPForList, gbc);
                gbc.gridx = 3;
                gbc.gridy = 2;
                gbc.weighty = 5;
                inputBox.add(hour, gbc);
                gbc.gridx = 2;
                gbc.gridy = 3;
                gbc.fill = gbc.fill = GridBagConstraints.BOTH;
                inputBox.add(text, gbc);
                gbc.gridx = 3;
                gbc.gridy = 3;
                gbc.fill = gbc.fill = GridBagConstraints.CENTER;
                inputBox.add(pm, gbc);
                gbc.gridx = 2;
                gbc.gridy = 4;
                inputBox.add(cancel, gbc);
                gbc.gridx = 3;
                gbc.gridy = 4;
                inputBox.add(OK, gbc);
                JPanel j = new JPanel();
                gbc.gridx = 4;
                gbc.gridy = 4;
                inputBox.add(j, gbc);
            }
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////Write by index


    /**
     * writes yadaavars one on one
     * @param yH
     * @param yM
     */
    public void writeByIndex(int yH, String yM){
        if (sourceOfDayHandler != null) {
            yadaavar[(thatMonthWhichWeWereIn - 1) * 31 + Integer.parseInt(sourceOfDayHandler.getText())] = yadaavar[(thatMonthWhichWeWereIn - 1) * 31 + Integer.parseInt(sourceOfDayHandler.getText())] + "\n" + yM + " " + yH;
            yadavar.setText(yadaavar[(thatMonthWhichWeWereIn - 1) * 31 + Integer.parseInt(sourceOfDayHandler.getText())]);
        }
        //////////////////////////////////////////////////////////////////////////////inja dovomin alarm todo
        yadaavarWriter();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////handler

    /**
     * handler of day buttons
     */
    private class dayHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Mobaddel mob = new Mobaddel();
            JButton d = (JButton) e.getSource();
            mob.PersianToGregorian(thatYearWhichWeAreIn, thatMonthWhichWeAreIn, Integer.parseInt(d.getText()));
            mil.setText(mob.getDay() + " " + getGeorMonth(mob.getMonth()) + " " + mob.getYear());

            if ((thatYearWhichWeAreIn != 1395)) {
                ;
            }
            else {
                if (sourceOfDayHandler != null)
                    lastSelectedDay = sourceOfDayHandler;
                sourceOfDayHandler = (JButton) e.getSource();
                gha.setText(getGhamari(Integer.parseInt(sourceOfDayHandler.getText()), thatMonthWhichWeAreIn));

                // yadavar

                if (yadaavar[(thatMonthWhichWeAreIn - 1) * 31 + Integer.parseInt(sourceOfDayHandler.getText())] != null)
                    yadavar.setText(yadaavar[(thatMonthWhichWeAreIn - 1) * 31 + Integer.parseInt(sourceOfDayHandler.getText())]);
                else
                    yadavar.setText("");
               // yaddasht


                if (Yaddasht[(thatMonthWhichWeAreIn - 1) * 31 + Integer.parseInt(sourceOfDayHandler.getText())] != null)
                    yad.setText(Yaddasht[(thatMonthWhichWeAreIn - 1) * 31 + Integer.parseInt(sourceOfDayHandler.getText())]);
                else
                    yad.setText("");
                thatMonthWhichWeWereIn = thatMonthWhichWeAreIn;

                int a = day + startingDayF;
                if (a < 0) {
                    a = a % 7;
                    a = -1 * a;
                    a = 7 - a;
                    a = a % 7;
                } else
                    a = a % 7;
                for (int i = 0; i < 42; i++)
                    dayPanelList[i].setBackground(new Color(255, 178, 102));
                if (a % 7 == 6)
                    a = -1;
                dayPanelList[a + Integer.parseInt(sourceOfDayHandler.getText())].setBackground(leftLabelsColor);
                if(thatYearWhichWeAreIn == 1395){
                    ro.setText(monasebat[(thatMonthWhichWeAreIn - 1) * 31 + Integer.parseInt(sourceOfDayHandler.getText()) ]);
                }
            }

        }

    }




////////////////////////////////////////////////////////////////////////////////////////////////tray handler
//// TODO: 5/27/16
    /**
     * handler of tray
     */
    private class TMListener implements MouseListener {
        public TMListener () {
            MenuItem item;
            item = new MenuItem("نمایش پنجره برنامه");
            //item.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            //item.setFont(new Font("Arabic Typesetting", Font.PLAIN, 30));
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(true);
                }
            });
            rclick.add(item);
            item = new MenuItem("درج امروز");
            //item.setFont(new Font("Arabic Typesetting", Font.PLAIN, 30));
            //item.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    drawTodaysCalender(todayIsDay ,todayIsMonth , todayIsYear);
                }
            });
            rclick.add(item);
            item = new MenuItem("افزودن یادآور");
            //item.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            //item.setFont(new Font("Arabic Typesetting", Font.PLAIN, 30));
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    drawTodaysCalender(todayIsDay ,todayIsMonth , todayIsYear);
                    ActionEvent ev = new ActionEvent(addYadaavar, 10, "");
                    addYadaavarHandler a = new addYadaavarHandler();
                    a.actionPerformed(ev);
                }
            });
            rclick.add(item);
            item = new MenuItem("ویرایش یادداشت");
            //item.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            //item.setFont(new Font("Arabic Typesetting", Font.PLAIN, 30));
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    drawTodaysCalender(todayIsDay ,todayIsMonth , todayIsYear);
                    yad.requestFocus();
                }
            });
            rclick.add(item);
            item = new MenuItem("خروج از برنامه");
            //item.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            //item.setFont(new Font("Arabic Typesetting", Font.PLAIN, 30));
            item.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    System.exit(0);
                }
            });
            rclick.add(item);
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                if (isVisible())
                    ;
                else
                    setVisible(true);
                if (getState() == Frame.NORMAL)
                    setState(Frame.ICONIFIED);
                else
                    setState(Frame.NORMAL);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }








/////////////////////////////////////////////////////////////////

    /**
     * gets the ghamari date of entering day and month
     * @param day
     * @param month
     * @return
     */
    public String getGhamari(int day, int month) {
        if (month == 1) {
            if (day <= 20)
                return (day + 9) + " " + "جمادیه الثانی" + " " + 1437;
            else
                return (day - 20) + " " + "رجب" + " " + 1437;
        } else if (month == 2) {
            if (day < 19)
                return (day + 11) + " " + "رجب" + " " + 1437;
            else
                return (day - 18) + " " + "شعبان" + " " + 1437;
        } else if (month == 3) {
            if (day <= 17)
                return (day + 13) + " " + "شعبان" + " " + 1437;
            else
                return (day - 17) + " " + "رمضان" + " " + 1437;
        } else if (month == 4) {
            if (day <= 15)
                return (day + 14) + " " + "رمضان" + " " + 1437;
            else
                return (day - 15) + " " + "شوال" + " " + 1437;
        } else if (month == 5) {
            if (day <= 13)
                return (day + 16) + " " + "شوال" + " " + 1437;
            else
                return (day - 13) + " " + "ذوالقعده" + " " + 1437;
        } else if (month == 6) {
            if (day <= 12)
                return (day + 18) + " " + "ذوالقعده" + " " + 1437;
            else
                return (day - 12) + " " + "ذوالحجه" + " " + 1437;
        } else if (month == 7) {
            if (day <= 11)
                return (day + 19) + " " + "ذوالحجه" + " " + 1437;
            else
                return (day - 11) + " " + "محرم" + " " +1438;
        } else if (month == 8) {
            if (day <= 10)
                return  (day + 19) + " " + "محرم" + " " +1438;
            else
                return  (day - 10) + " " + "صفر" + " " + 1438;
        } else if (month == 9) {
            if (day <= 10)
                return (day + 20) + " " + "صفر" + " " + 1438;
            else
                return (day - 10) + " " + "ربیع الاول" + " " + 1438;
        } else if (month == 10) {
            if (day <= 10)
                return (day + 20) + " " + "ربیع الاول" + " " + 1438;
            else
                return (day - 10) + " " + "ربیع الثانی" + " " + 1438;
        } else if (month == 11) {
            if (day <= 10)
                return (day + 20) + " " + "ربیع الثانی" + " " + 1438;
            else
                return (day - 10) + " " + "جمادی الاولی" + " " + 1438;
        } else {
            if (day <= 10)
                return (day + 20) + " " + "جمادی الاولی" + " " + 1438;
            else
                return (day - 10) + " " + "جمادی الثانی" + " " + 1438;
        }
    }

    ///////////////////////////////////////////////////////////////

    /**
     * gets the entering months name in georgian
     * @param m
     * @return
     */
    public String getGeorMonth(int m) {
        if (m == 1)
            return "ژانویه";
        else if (m == 2)
            return "فوریه";
        else if (m == 3)
            return "مارس";
        else if (m == 4)
            return "آوریل";
        else if (m == 5)
            return "مه";
        else if (m == 6)
            return "ژوئن";
        else if (m == 7)
            return "ژوئیه";
        else if (m == 8)
            return "اوت";
        else if (m == 9)
            return "سپتامبر";
        else if (m ==10)
            return "اکتبر";
        else if (m == 11)
            return "نوامبر";
        else
            return "دسامبر";


    }
    ////////////////////////////////////////////////////////////////get month name

    /**
     * gets the month name in shamsi
     * @param month
     * @return
     */
    public String getMonthName(int month) {
        if (month < 0) {
            month = -1 * month;
            month = month % 12;
            month = 12 - month;
        } else {
            month = month % 12;
            if (month == 0)
                month = 12;
        }
        if (month == 1)
            return "فروردین";
        else if (month == 2)
            return "اردیبهشت";
        else if (month == 3)
            return "خرداد";
        else if (month == 4)
            return "تیر";
        else if (month == 5)
            return "مرداد";
        else if (month == 6)
            return "شهریور";
        else if (month == 7)
            return "مهر";
        else if (month == 8)
            return "آبان";
        else if (month == 9)
            return "آذر";
        else if (month == 10)
            return "دی";
        else if (month == 11)
            return "بهمن";
        else
            return "اسفند";
    }
    ///////////////////////////////////////////////////////////get number of days of a month

    /**
     * gets the number of day of a month
     * @param month
     * @return
     */
    public int getNumOfDay(int month) {
        if (month < 0) {
            month = -1 * month;
            month = month % 12;
            month = 12 - month;
        } else {
            month = month % 12;
            if (month == 0)
                month = 12;
        }

        if (month < 7)
            return 31;
        else if ((month < 12) && (month >= 7))
            return 30;
        else
            return 29;
    }

}